package com.github.supavitax.itemlorestats.Misc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SpigotStatCapWarning {
    private FileConfiguration PlayerDataConfig;
    String serverDir = (new File("")).getAbsolutePath();

    public boolean spigotConfigExists() {
        return (new File(this.serverDir + File.separator + "spigot.yml")).isFile();
    }

    public void updateSpigotValues() {
        if (this.spigotConfigExists()) {
            try {
                this.PlayerDataConfig = new YamlConfiguration();
                this.PlayerDataConfig.load(new File(this.serverDir + File.separator + "spigot.yml"));
                this.PlayerDataConfig.set("settings.attribute.maxHealth.max", 2000000.0D);
                this.PlayerDataConfig.set("settings.attribute.movementSpeed.max", 2000000.0D);
                this.PlayerDataConfig.set("settings.attribute.attackDamage.max", 2000000.0D);
                this.PlayerDataConfig.save(new File(this.serverDir + File.separator + "spigot.yml"));
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        }

    }
}
