package com.github.supavitax.itemlorestats.Misc;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Updater {

    private Plugin plugin;
    private Updater.UpdateType type;
    private String versionName;
    private String versionLink;
    private String versionType;
    private String versionGameVersion;
    private boolean announce;
    private URL url;
    private File file;
    private Thread thread;
    private int id = -1;
    private String apiKey = null;
    private static final String TITLE_VALUE = "name";
    private static final String LINK_VALUE = "downloadUrl";
    private static final String TYPE_VALUE = "releaseType";
    private static final String VERSION_VALUE = "gameVersion";
    private static final String QUERY = "/servermods/files?projectIds=";
    private static final String HOST = "https://api.curseforge.com";
    private static final String USER_AGENT = "Updater (by Gravity)";
    private static final String delimiter = "^v|[\\s_-]v";
    private static final String[] NO_UPDATE_TAG = new String[]{"-DEV", "-PRE", "-SNAPSHOT"};
    private static final int BYTE_SIZE = 1024;
    private final YamlConfiguration config = new YamlConfiguration();
    private String updateFolder;
    private Updater.UpdateResult result;


    public Updater(Plugin plugin, int id, File file, Updater.UpdateType type, boolean announce) {
        this.result = Updater.UpdateResult.SUCCESS;
        this.plugin = plugin;
        this.type = type;
        this.announce = announce;
        this.file = file;
        this.id = id;
        this.updateFolder = ItemLoreStats.plugin.getServer().getUpdateFolder();
        File pluginFile = plugin.getDataFolder().getParentFile();
        File updaterFile = new File(pluginFile, "Updater");
        File updaterConfigFile = new File(updaterFile, "config.yml");
        this.config.options().header("This configuration file affects all plugins using the Updater system (version 2+ - http://forums.bukkit.org/threads/96681/ )\nIf you wish to use your API key, read http://wiki.bukkit.org/ServerMods_API and place it below.\nSome updating systems will not adhere to the disabled value, but these may be turned off in their plugin\'s configuration.");
        this.config.addDefault("api-key", "PUT_API_KEY_HERE");
        this.config.addDefault("disable", Boolean.valueOf(false));
        if (!updaterFile.exists()) {
            updaterFile.mkdir();
        }

        boolean createFile = !updaterConfigFile.exists();

        try {
            if (createFile) {
                updaterConfigFile.createNewFile();
                this.config.options().copyDefaults(true);
                this.config.save(updaterConfigFile);
            } else {
                this.config.load(updaterConfigFile);
            }
        } catch (Exception var13) {
            if (createFile) {
                plugin.getLogger().severe("The updater could not create configuration at " + updaterFile.getAbsolutePath());
            } else {
                plugin.getLogger().severe("The updater could not load configuration at " + updaterFile.getAbsolutePath());
            }

            plugin.getLogger().log(Level.SEVERE, (String) null, var13);
        }

        if (this.config.getBoolean("disable")) {
            this.result = Updater.UpdateResult.DISABLED;
        } else {
            String key = this.config.getString("api-key");
            if (key.equalsIgnoreCase("PUT_API_KEY_HERE") || key.equals("")) {
                key = null;
            }

            this.apiKey = key;

            try {
                this.url = new URL("https://api.curseforge.com/servermods/files?projectIds=" + id);
            } catch (MalformedURLException var12) {
                plugin.getLogger().log(Level.SEVERE, "The project ID provided for updating, " + id + " is invalid.", var12);
                this.result = Updater.UpdateResult.FAIL_BADID;
            }

            this.thread = new Thread(new Updater.UpdateRunnable((Updater.UpdateRunnable) null));
            this.thread.start();
        }
    }

    public Updater.UpdateResult getResult() {
        this.waitForThread();
        return this.result;
    }

    public Updater.ReleaseType getLatestType() {
        this.waitForThread();
        if (this.versionType != null) {
            Updater.ReleaseType[] var4;
            int var3 = (var4 = Updater.ReleaseType.values()).length;

            for (int var2 = 0; var2 < var3; ++var2) {
                Updater.ReleaseType type = var4[var2];
                if (this.versionType.equals(type.name().toLowerCase())) {
                    return type;
                }
            }
        }

        return null;
    }

    public String getLatestGameVersion() {
        this.waitForThread();
        return this.versionGameVersion;
    }

    public String getLatestName() {
        this.waitForThread();
        return this.versionName;
    }

    public String getLatestFileLink() {
        this.waitForThread();
        return this.versionLink;
    }

    private void waitForThread() {
        if (this.thread != null && this.thread.isAlive()) {
            try {
                this.thread.join();
            } catch (InterruptedException var2) {
                this.plugin.getLogger().log(Level.SEVERE, (String) null, var2);
            }
        }

    }

    private void saveFile(File folder, String file, String link) {
        if (!folder.exists()) {
            folder.mkdir();
        }

        BufferedInputStream in = null;
        FileOutputStream fout = null;

        try {
            URL ex = new URL(link);
            int fileLength = ex.openConnection().getContentLength();
            in = new BufferedInputStream(ex.openStream());
            fout = new FileOutputStream(folder.getAbsolutePath() + File.separator + file);
            byte[] data = new byte[1024];
            if (this.announce) {
                this.plugin.getLogger().info("About to download a new update: " + this.versionName);
            }

            long downloaded = 0L;

            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                downloaded += (long) count;
                fout.write(data, 0, count);
                int dFile = (int) (downloaded * 100L / (long) fileLength);
                if (this.announce && dFile % 10 == 0) {
                    this.plugin.getLogger().info("Downloading update: " + dFile + "% of " + fileLength + " bytes.");
                }
            }

            File[] var15;
            int var14 = (var15 = (new File(this.plugin.getDataFolder().getParent(), this.updateFolder)).listFiles()).length;

            File var26;
            for (int var13 = 0; var13 < var14; ++var13) {
                var26 = var15[var13];
                if (var26.getName().endsWith(".zip")) {
                    var26.delete();
                }
            }

            var26 = new File(folder.getAbsolutePath() + File.separator + file);
            if (var26.getName().endsWith(".zip")) {
                this.unzip(var26.getCanonicalPath());
            }

            if (this.announce) {
                this.plugin.getLogger().info("Finished updating.");
            }
        } catch (Exception var24) {
            this.plugin.getLogger().warning("The auto-updater tried to download a new update, but was unsuccessful.");
            this.result = Updater.UpdateResult.FAIL_DOWNLOAD;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

                if (fout != null) {
                    fout.close();
                }
            } catch (Exception var23) {
                ;
            }

        }

    }

    private void unzip(String file) {
        try {
            File e = new File(file);
            String zipPath = file.substring(0, file.length() - 4);
            ZipFile zipFile = new ZipFile(e);
            Enumeration e1 = zipFile.entries();

            while (e1.hasMoreElements()) {
                ZipEntry dFile = (ZipEntry) e1.nextElement();
                File destinationFilePath = new File(zipPath, dFile.getName());
                destinationFilePath.getParentFile().mkdirs();
                if (!dFile.isDirectory()) {
                    BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(dFile));
                    byte[] oFile = new byte[1024];
                    FileOutputStream contents = new FileOutputStream(destinationFilePath);
                    BufferedOutputStream cFile = new BufferedOutputStream(contents, 1024);

                    int b;
                    while ((b = bis.read(oFile, 0, 1024)) != -1) {
                        cFile.write(oFile, 0, b);
                    }

                    cFile.flush();
                    cFile.close();
                    bis.close();
                    String name = destinationFilePath.getName();
                    if (name.endsWith(".jar") && this.pluginFile(name)) {
                        destinationFilePath.renameTo(new File(this.plugin.getDataFolder().getParent(), this.updateFolder + File.separator + name));
                    }

                    dFile = null;
                    destinationFilePath = null;
                }
            }

            e1 = null;
            zipFile.close();
            zipFile = null;
            File[] var25;
            int var24 = (var25 = (new File(zipPath)).listFiles()).length;

            for (int var23 = 0; var23 < var24; ++var23) {
                File var22 = var25[var23];
                if (var22.isDirectory() && this.pluginFile(var22.getName())) {
                    File var26 = new File(this.plugin.getDataFolder().getParent(), var22.getName());
                    File[] var27 = var26.listFiles();
                    File[] var15;
                    int var14 = (var15 = var22.listFiles()).length;

                    for (int var29 = 0; var29 < var14; ++var29) {
                        File var28 = var15[var29];
                        boolean found = false;
                        File[] var20 = var27;
                        int var19 = var27.length;

                        for (int var18 = 0; var18 < var19; ++var18) {
                            File xFile = var20[var18];
                            if (xFile.getName().equals(var28.getName())) {
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            var28.renameTo(new File(var26.getCanonicalFile() + File.separator + var28.getName()));
                        } else {
                            var28.delete();
                        }
                    }
                }

                var22.delete();
            }

            (new File(zipPath)).delete();
            e.delete();
        } catch (IOException var21) {
            this.plugin.getLogger().log(Level.SEVERE, "The auto-updater tried to unzip a new update file, but was unsuccessful.", var21);
            this.result = Updater.UpdateResult.FAIL_DOWNLOAD;
        }

        (new File(file)).delete();
    }

    private boolean pluginFile(String name) {
        File[] var5;
        int var4 = (var5 = (new File("plugins")).listFiles()).length;

        for (int var3 = 0; var3 < var4; ++var3) {
            File file = var5[var3];
            if (file.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    private boolean versionCheck(String title) {
        if (this.type != Updater.UpdateType.NO_VERSION_CHECK) {
            String localVersion = this.plugin.getDescription().getVersion();
            String authorInfo;
            if (title.split("^v|[\\s_-]v").length != 2) {
                authorInfo = this.plugin.getDescription().getAuthors().size() == 0 ? "" : " (" + (String) this.plugin.getDescription().getAuthors().get(0) + ")";
                this.plugin.getLogger().warning("The author of this plugin" + authorInfo + " has misconfigured their Auto Update system");
                this.plugin.getLogger().warning("File versions should follow the format \'PluginName vVERSION\'");
                this.plugin.getLogger().warning("Please notify the author of this error.");
                this.result = Updater.UpdateResult.FAIL_NOVERSION;
                return false;
            }

            authorInfo = title.split("^v|[\\s_-]v")[1].split(" ")[0];
            if (this.hasTag(localVersion) || !this.shouldUpdate(localVersion, authorInfo)) {
                this.result = Updater.UpdateResult.NO_UPDATE;
                return false;
            }
        }

        return true;
    }

    public boolean shouldUpdate(String localVersion, String remoteVersion) {
        return !localVersion.equalsIgnoreCase(remoteVersion);
    }

    private boolean hasTag(String version) {
        String[] var5 = NO_UPDATE_TAG;
        int var4 = NO_UPDATE_TAG.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            String string = var5[var3];
            if (version.contains(string)) {
                return true;
            }
        }

        return false;
    }

    private boolean read() {
        try {
            URLConnection e = this.url.openConnection();
            e.setConnectTimeout(5000);
            if (this.apiKey != null) {
                e.addRequestProperty("X-API-Key", this.apiKey);
            }

            e.addRequestProperty("User-Agent", "Updater (by Gravity)");
            e.setDoOutput(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(e.getInputStream()));
            String response = reader.readLine();
            JSONArray array = (JSONArray) JSONValue.parse(response);
            if (array.size() == 0) {
                this.plugin.getLogger().warning("The updater could not find any files for the project id " + this.id);
                this.result = Updater.UpdateResult.FAIL_BADID;
                return false;
            } else {
                this.versionName = (String) ((JSONObject) array.get(array.size() - 1)).get("name");
                this.versionLink = (String) ((JSONObject) array.get(array.size() - 1)).get("downloadUrl");
                this.versionType = (String) ((JSONObject) array.get(array.size() - 1)).get("releaseType");
                this.versionGameVersion = (String) ((JSONObject) array.get(array.size() - 1)).get("gameVersion");
                return true;
            }
        } catch (IOException var5) {
            if (var5.getMessage().contains("HTTP response code: 403")) {
                this.plugin.getLogger().severe("dev.bukkit.org rejected the API key provided in plugins/Updater/config.yml");
                this.plugin.getLogger().severe("Please double-check your configuration to ensure it is correct.");
                this.result = Updater.UpdateResult.FAIL_APIKEY;
            } else {
                this.plugin.getLogger().severe("The updater could not contact dev.bukkit.org for updating.");
                this.plugin.getLogger().severe("If you have not recently modified your configuration and this is the first time you are seeing this message, the site may be experiencing temporary downtime.");
                this.result = Updater.UpdateResult.FAIL_DBO;
            }

            this.plugin.getLogger().log(Level.SEVERE, (String) null, var5);
            return false;
        }
    }

    public static enum ReleaseType {

        ALPHA("ALPHA", 0),
        BETA("BETA", 1),
        RELEASE("RELEASE", 2);
        // $FF: synthetic field
        private static final Updater.ReleaseType[] ENUM$VALUES = new Updater.ReleaseType[]{ALPHA, BETA, RELEASE};


        private ReleaseType(String var1, int var2) {
        }
    }

    public static enum UpdateResult {

        SUCCESS("SUCCESS", 0),
        NO_UPDATE("NO_UPDATE", 1),
        DISABLED("DISABLED", 2),
        FAIL_DOWNLOAD("FAIL_DOWNLOAD", 3),
        FAIL_DBO("FAIL_DBO", 4),
        FAIL_NOVERSION("FAIL_NOVERSION", 5),
        FAIL_BADID("FAIL_BADID", 6),
        FAIL_APIKEY("FAIL_APIKEY", 7),
        UPDATE_AVAILABLE("UPDATE_AVAILABLE", 8);
        // $FF: synthetic field
        private static final Updater.UpdateResult[] ENUM$VALUES = new Updater.UpdateResult[]{SUCCESS, NO_UPDATE, DISABLED, FAIL_DOWNLOAD, FAIL_DBO, FAIL_NOVERSION, FAIL_BADID, FAIL_APIKEY, UPDATE_AVAILABLE};


        private UpdateResult(String var1, int var2) {
        }
    }

    private class UpdateRunnable implements Runnable {

        private UpdateRunnable() {
        }

        public void run() {
            if (Updater.this.url != null && Updater.this.read() && Updater.this.versionCheck(Updater.this.versionName)) {
                if (Updater.this.versionLink != null && Updater.this.type != Updater.UpdateType.NO_DOWNLOAD) {
                    String name = Updater.this.file.getName();
                    if (Updater.this.versionLink.endsWith(".zip")) {
                        String[] split = Updater.this.versionLink.split("/");
                        name = split[split.length - 1];
                    }

                    Updater.this.saveFile(new File(Updater.this.plugin.getDataFolder().getParent(), Updater.this.updateFolder), name, Updater.this.versionLink);
                } else {
                    Updater.this.result = Updater.UpdateResult.UPDATE_AVAILABLE;
                }
            }

        }

        // $FF: synthetic method
        UpdateRunnable(Updater.UpdateRunnable var2) {
            this();
        }
    }

    public static enum UpdateType {

        DEFAULT("DEFAULT", 0),
        NO_VERSION_CHECK("NO_VERSION_CHECK", 1),
        NO_DOWNLOAD("NO_DOWNLOAD", 2);
        // $FF: synthetic field
        private static final Updater.UpdateType[] ENUM$VALUES = new Updater.UpdateType[]{DEFAULT, NO_VERSION_CHECK, NO_DOWNLOAD};


        private UpdateType(String var1, int var2) {
        }
    }
}
