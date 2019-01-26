package com.github.supavitax.itemlorestats.ItemGeneration;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_Random;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.logging.Level;

public class Suffix {

    Util_Random random = new Util_Random();


    public String get(FileConfiguration configFile, String entity, String dropChance) {
        if (configFile.getString(dropChance + ".suffix").equalsIgnoreCase("Random")) {
            List getListPrefix = ItemLoreStats.plugin.getConfig().getStringList("suffix.random");
            String selectPrefix = (String) getListPrefix.get(this.random.random(getListPrefix.size()) - 1);
            return selectPrefix;
        } else if (!configFile.getString(dropChance + ".suffix").equalsIgnoreCase("Stat")) {
            return configFile.getString(dropChance + ".suffix");
        } else {
            ItemLoreStats.plugin.getLogger().log(Level.SEVERE, "Unable to load suffix for " + configFile.getName());
            return "Error";
        }
    }
}
