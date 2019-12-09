package org.bukkit;

import com.avaje.ebean.config.ServerConfig;
import org.bukkit.BanList.Type;
import org.bukkit.Warning.WarningState;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageRecipient;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.CachedServerIcon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.logging.Logger;

public interface Server extends PluginMessageRecipient {
    String BROADCAST_CHANNEL_ADMINISTRATIVE = "bukkit.broadcast.admin";
    String BROADCAST_CHANNEL_USERS = "bukkit.broadcast.user";

    String getName();

    String getVersion();

    String getBukkitVersion();

    Collection<? extends Player> getOnlinePlayers();

    int getMaxPlayers();

    int getPort();

    int getViewDistance();

    String getIp();

    String getServerName();

    String getServerId();

    String getWorldType();

    boolean getGenerateStructures();

    boolean getAllowEnd();

    boolean getAllowNether();

    boolean hasWhitelist();

    void setWhitelist(boolean var1);

    Set<OfflinePlayer> getWhitelistedPlayers();

    void reloadWhitelist();

    int broadcastMessage(String var1);

    String getUpdateFolder();

    File getUpdateFolderFile();

    long getConnectionThrottle();

    int getTicksPerAnimalSpawns();

    int getTicksPerMonsterSpawns();

    Player getPlayer(String var1);

    Player getPlayerExact(String var1);

    List<Player> matchPlayer(String var1);

    Player getPlayer(UUID var1);

    PluginManager getPluginManager();

    BukkitScheduler getScheduler();

    ServicesManager getServicesManager();

    List<World> getWorlds();

    World createWorld(WorldCreator var1);

    boolean unloadWorld(String var1, boolean var2);

    boolean unloadWorld(World var1, boolean var2);

    World getWorld(String var1);

    World getWorld(UUID var1);

    /**
     * @deprecated
     */
    @Deprecated
    MapView getMap(short var1);

    MapView createMap(World var1);

    void reload();

    Logger getLogger();

    PluginCommand getPluginCommand(String var1);

    void savePlayers();

    boolean dispatchCommand(CommandSender var1, String var2) throws CommandException;

    void configureDbConfig(ServerConfig var1);

    boolean addRecipe(Recipe var1);

    List<Recipe> getRecipesFor(ItemStack var1);

    Iterator<Recipe> recipeIterator();

    void clearRecipes();

    void resetRecipes();

    Map<String, String[]> getCommandAliases();

    int getSpawnRadius();

    void setSpawnRadius(int var1);

    boolean getOnlineMode();

    boolean getAllowFlight();

    boolean isHardcore();

    boolean useExactLoginLocation();

    void shutdown();

    int broadcast(String var1, String var2);

    /**
     * @deprecated
     */
    @Deprecated
    OfflinePlayer getOfflinePlayer(String var1);

    OfflinePlayer getOfflinePlayer(UUID var1);

    Set<String> getIPBans();

    void banIP(String var1);

    void unbanIP(String var1);

    Set<OfflinePlayer> getBannedPlayers();

    BanList getBanList(Type var1);

    Set<OfflinePlayer> getOperators();

    GameMode getDefaultGameMode();

    void setDefaultGameMode(GameMode var1);

    ConsoleCommandSender getConsoleSender();

    File getWorldContainer();

    OfflinePlayer[] getOfflinePlayers();

    Messenger getMessenger();

    HelpMap getHelpMap();

    Inventory createInventory(InventoryHolder var1, InventoryType var2);

    Inventory createInventory(InventoryHolder var1, InventoryType var2, String var3);

    Inventory createInventory(InventoryHolder var1, int var2) throws IllegalArgumentException;

    Inventory createInventory(InventoryHolder var1, int var2, String var3) throws IllegalArgumentException;

    int getMonsterSpawnLimit();

    int getAnimalSpawnLimit();

    int getWaterAnimalSpawnLimit();

    int getAmbientSpawnLimit();

    boolean isPrimaryThread();

    String getMotd();

    String getShutdownMessage();

    WarningState getWarningState();

    ItemFactory getItemFactory();

    ScoreboardManager getScoreboardManager();

    CachedServerIcon getServerIcon();

    CachedServerIcon loadServerIcon(File var1) throws IllegalArgumentException, Exception;

    CachedServerIcon loadServerIcon(BufferedImage var1) throws IllegalArgumentException, Exception;

    void setIdleTimeout(int var1);

    int getIdleTimeout();
}
