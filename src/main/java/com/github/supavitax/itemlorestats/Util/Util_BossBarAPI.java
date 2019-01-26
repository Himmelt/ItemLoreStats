package com.github.supavitax.itemlorestats.Util;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBarAPI;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Util_BossBarAPI {

    Util_Colours util_Colours = new Util_Colours();
    ItemLoreStats main;


    public Util_BossBarAPI(ItemLoreStats instance) {
        this.main = instance;
    }

    private String format(double value) {
        Locale forceLocale = new Locale("en", "UK");
        String decimalPattern = "0";
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(forceLocale);
        decimalFormat.applyPattern(decimalPattern);
        String format = decimalFormat.format(value);
        return format;
    }

    public void setBarValue(Player player, double currentHealth, double maxHealth) {
        if (!player.hasMetadata("NPC")) {
            if (ItemLoreStats.plugin.getConfig().getBoolean("usingBossBarAPI")) {
                float value = Float.parseFloat(String.valueOf(currentHealth)) / Float.parseFloat(String.valueOf(maxHealth));
                int maxHealthDiv = Double.valueOf(maxHealth).intValue() / 10;
                String splitterName = ItemLoreStats.plugin.getConfig().getString("bossBar.splitter");
                String splitterColour = ItemLoreStats.plugin.getConfig().getString("bossBar.colour");
                String healthName = ItemLoreStats.plugin.getConfig().getString("bossBar.health.name");
                String healthColour = ItemLoreStats.plugin.getConfig().getString("bossBar.health.colour");
                String healthSplitter = ItemLoreStats.plugin.getConfig().getString("bossBar.health.splitter");
                String levelName = ItemLoreStats.plugin.getConfig().getString("bossBar.level.name");
                String levelColour = ItemLoreStats.plugin.getConfig().getString("bossBar.level.colour");
                boolean level = false;
                String expName = ItemLoreStats.plugin.getConfig().getString("bossBar.exp.name");
                String expColour = ItemLoreStats.plugin.getConfig().getString("bossBar.exp.colour");
                String expSplitter = ItemLoreStats.plugin.getConfig().getString("bossBar.exp.splitter");
                boolean exp = false;
                String bossBarText = "";
                int level1;
                int exp1;
                if (currentHealth < (double) maxHealthDiv * 2.6D) {
                    bossBarText = this.util_Colours.replaceTooltipColour(healthColour) + healthName + " " + ChatColor.RED + this.format((double) Math.round(currentHealth)) + this.util_Colours.replaceTooltipColour(healthColour) + healthSplitter + this.format((double) Math.round(maxHealth));
                    if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.level.enabled")) {
                        if (ItemLoreStats.plugin.getHeroes() != null) {
                            level1 = ItemLoreStats.plugin.util_Heroes.getHeroLevel(player);
                        } else if (ItemLoreStats.plugin.getHeroes() != null) {
                            level1 = ItemLoreStats.plugin.util_SkillAPI.getSkillAPILevel(player);
                        } else {
                            level1 = player.getLevel();
                        }

                        bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(levelColour) + levelName + " " + level1;
                    }

                    if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.exp.enabled")) {
                        if (ItemLoreStats.plugin.getHeroes() != null) {
                            exp1 = ItemLoreStats.plugin.util_Heroes.getHeroExperience(player);
                        } else if (ItemLoreStats.plugin.getHeroes() != null) {
                            exp1 = ItemLoreStats.plugin.util_SkillAPI.getSkillAPIExperience(player);
                        } else {
                            exp1 = Math.round(player.getExp() * (float) player.getExpToLevel());
                        }

                        bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(expColour) + expName + " " + exp1 + expSplitter + player.getExpToLevel();
                    }

                    BossBarAPI.setMessage(player, bossBarText, value * 100.0F);
                    BossBarAPI.setHealth(player, value * 100.0F);
                } else if (currentHealth < (double) maxHealthDiv * 5.1D) {
                    bossBarText = this.util_Colours.replaceTooltipColour(healthColour) + healthName + " " + ChatColor.YELLOW + this.format((double) Math.round(currentHealth)) + this.util_Colours.replaceTooltipColour(healthColour) + healthSplitter + this.format((double) Math.round(maxHealth));
                    if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.level.enabled")) {
                        if (ItemLoreStats.plugin.getHeroes() != null) {
                            level1 = ItemLoreStats.plugin.util_Heroes.getHeroLevel(player);
                        } else if (ItemLoreStats.plugin.getHeroes() != null) {
                            level1 = ItemLoreStats.plugin.util_SkillAPI.getSkillAPILevel(player);
                        } else {
                            level1 = player.getLevel();
                        }

                        bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(levelColour) + levelName + " " + level1;
                    }

                    if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.exp.enabled")) {
                        if (ItemLoreStats.plugin.getHeroes() != null) {
                            exp1 = ItemLoreStats.plugin.util_Heroes.getHeroExperience(player);
                        } else if (ItemLoreStats.plugin.getHeroes() != null) {
                            exp1 = ItemLoreStats.plugin.util_SkillAPI.getSkillAPIExperience(player);
                        } else {
                            exp1 = Math.round(player.getExp() * (float) player.getExpToLevel());
                        }

                        bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(expColour) + expName + " " + exp1 + expSplitter + player.getExpToLevel();
                    }

                    BossBarAPI.setMessage(player, bossBarText, value * 100.0F);
                    BossBarAPI.setHealth(player, value * 100.0F);
                } else if (currentHealth < (double) maxHealthDiv * 7.6D) {
                    bossBarText = this.util_Colours.replaceTooltipColour(healthColour) + healthName + " " + ChatColor.GREEN + this.format((double) Math.round(currentHealth)) + this.util_Colours.replaceTooltipColour(healthColour) + healthSplitter + this.format((double) Math.round(maxHealth));
                    if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.level.enabled")) {
                        if (ItemLoreStats.plugin.getHeroes() != null) {
                            level1 = ItemLoreStats.plugin.util_Heroes.getHeroLevel(player);
                        } else if (ItemLoreStats.plugin.getHeroes() != null) {
                            level1 = ItemLoreStats.plugin.util_SkillAPI.getSkillAPILevel(player);
                        } else {
                            level1 = player.getLevel();
                        }

                        bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(levelColour) + levelName + " " + level1;
                    }

                    if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.exp.enabled")) {
                        if (ItemLoreStats.plugin.getHeroes() != null) {
                            exp1 = ItemLoreStats.plugin.util_Heroes.getHeroExperience(player);
                        } else if (ItemLoreStats.plugin.getHeroes() != null) {
                            exp1 = ItemLoreStats.plugin.util_SkillAPI.getSkillAPIExperience(player);
                        } else {
                            exp1 = Math.round(player.getExp() * (float) player.getExpToLevel());
                        }

                        bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(expColour) + expName + " " + exp1 + expSplitter + player.getExpToLevel();
                    }

                    BossBarAPI.setMessage(player, bossBarText, value * 100.0F);
                    BossBarAPI.setHealth(player, value * 100.0F);
                } else {
                    bossBarText = this.util_Colours.replaceTooltipColour(healthColour) + healthName + " " + this.format((double) Math.round(currentHealth)) + this.util_Colours.replaceTooltipColour(healthColour) + healthSplitter + this.format((double) Math.round(maxHealth));
                    if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.level.enabled")) {
                        if (ItemLoreStats.plugin.getHeroes() != null) {
                            level1 = ItemLoreStats.plugin.util_Heroes.getHeroLevel(player);
                        } else if (ItemLoreStats.plugin.getHeroes() != null) {
                            level1 = ItemLoreStats.plugin.util_SkillAPI.getSkillAPILevel(player);
                        } else {
                            level1 = player.getLevel();
                        }

                        bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(levelColour) + levelName + " " + level1;
                    }

                    if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.exp.enabled")) {
                        if (ItemLoreStats.plugin.getHeroes() != null) {
                            exp1 = ItemLoreStats.plugin.util_Heroes.getHeroExperience(player);
                        } else if (ItemLoreStats.plugin.getHeroes() != null) {
                            exp1 = ItemLoreStats.plugin.util_SkillAPI.getSkillAPIExperience(player);
                        } else {
                            exp1 = Math.round(player.getExp() * (float) player.getExpToLevel());
                        }

                        bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(expColour) + expName + " " + exp1 + expSplitter + player.getExpToLevel();
                    }

                    BossBarAPI.setMessage(player, bossBarText, value * 100.0F);
                    BossBarAPI.setHealth(player, value * 100.0F);
                }
            }

        }
    }

    public void removeBar(Player player) {
        BossBarAPI.removeBar(player);
    }
}
