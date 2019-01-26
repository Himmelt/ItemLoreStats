package com.github.supavitax.itemlorestats.ItemGeneration;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_Format;
import com.github.supavitax.itemlorestats.Util.Util_Random;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Level;

public class StatRanges {

    Util_Colours util_Colours = new Util_Colours();
    Util_Random util_Random = new Util_Random();
    Util_Format util_Format = new Util_Format();


    public double getRandomStatFromRange(double minStat, double maxStat) {
        double stat = 0.0D;
        stat = this.util_Random.randomDoubleRange(minStat, maxStat);
        return stat;
    }

    public String getRandomRange(FileConfiguration configFile, int playerLevel, int mobLevel, String statType, String dropChance) {
        String stat = this.util_Colours.removeTooltipColour(configFile.getString(dropChance + ".properties." + statType));
        double minStat = 0.0D;
        double maxStat = 0.0D;
        double level = 0.0D;

        try {
            if (stat.contains("-") && stat.contains("+")) {
                if (stat.contains("player")) {
                    level = Double.valueOf((double) playerLevel).doubleValue();
                    if (stat.split("-player")[0].contains("-")) {
                        minStat = this.getRandomStatFromRange(level - Double.parseDouble(stat.split("-")[1]), level - Double.parseDouble(stat.split("-")[0]));
                        maxStat = this.getRandomStatFromRange(minStat, level + Double.parseDouble(stat.split("\\+")[1].split("-")[1]));
                    } else {
                        minStat = this.getRandomStatFromRange(level - Double.parseDouble(stat.split("-")[0]), level);
                        maxStat = this.getRandomStatFromRange(minStat, level + Double.parseDouble(stat.split("\\+")[1]));
                    }
                } else if (stat.contains("mob")) {
                    level = Double.valueOf((double) mobLevel).doubleValue();
                    if (stat.split("-mob")[0].contains("-")) {
                        minStat = this.getRandomStatFromRange(level - Double.parseDouble(stat.split("-")[1]), level - Double.parseDouble(stat.split("-")[0]));
                        maxStat = this.getRandomStatFromRange(minStat, level + Double.parseDouble(stat.split("\\+")[1].split("-")[1]));
                    } else {
                        minStat = this.getRandomStatFromRange(level - Double.parseDouble(stat.split("-")[0]), level);
                        maxStat = this.getRandomStatFromRange(minStat, level + Double.parseDouble(stat.split("\\+")[1]));
                    }
                }
            } else {
                minStat = this.getRandomStatFromRange(Double.parseDouble(stat.split("-")[0]), Double.parseDouble(stat.split("-")[1]));
                maxStat = this.getRandomStatFromRange(minStat, Double.parseDouble(stat.split("-")[1]));
            }

            if (level - minStat < 1.0D) {
                minStat = 1.0D;
            } else {
                minStat = level - minStat;
            }

            double e = Double.parseDouble(this.util_Random.formattedRandomRange(minStat, maxStat));
            double selectedMaxValue = Double.parseDouble(this.util_Random.formattedRandomRange(e, maxStat));
            double multipliedMinStat = e + e * ItemLoreStats.plugin.getConfig().getDouble("npcDroppedStatMultiplier." + statType);
            double multipliedMaxStat = selectedMaxValue + selectedMaxValue * ItemLoreStats.plugin.getConfig().getDouble("npcDroppedStatMultiplier." + statType);
            return statType.equals("damage") ? this.util_Format.formatString(multipliedMinStat) + "-" + this.util_Format.formatString(multipliedMaxStat) : this.util_Format.formatString(e);
        } catch (Exception var21) {
            var21.printStackTrace();
            ItemLoreStats.plugin.getLogger().log(Level.SEVERE, "Unable to generate " + statType + " stat on item generation");
            return "ERROR";
        }
    }
}
