package com.github.supavitax.itemlorestats.ItemGeneration;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.logging.Level;

public class MaterialType {
    private FileConfiguration PlayerDataConfig;

    public String setType(String configFile, ItemStack getMaterial) {
        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + ItemLoreStats.plugin.getConfig().getString("languageFile") + ".yml"));
            String e = getMaterial.getType().toString();
            String type = "";
            if (!e.contains("_SWORD") && !e.contains("_AXE") && !e.contains("_HOE") && !e.contains("_SPADE") && !e.contains("_PICKAXE")) {
                if (!e.contains("_HELMET") && !e.contains("_CHESTPLATE") && !e.contains("_LEGGINGS") && !e.contains("_BOOTS")) {
                    if (!e.contains("BOW") && !e.contains("STICK") && !e.contains("STRING") && !e.contains("BLAZE_ROD") && !e.contains("SHEARS") && !e.contains("BUCKET")) {
                        int i;
                        if (ItemLoreStats.plugin.isTool(getMaterial.getType())) {
                            for (i = 0; i < ItemLoreStats.plugin.getConfig().getList("materials.tools").size(); ++i) {
                                if (ItemLoreStats.plugin.getConfig().getList("materials.tools").get(i).toString().split(":")[0].equals(getMaterial.getType().toString())) {
                                    String customMaterial = ItemLoreStats.plugin.getConfig().getList("materials.tools").get(i).toString().split(":")[1];
                                    if (!customMaterial.contains("_SWORD") && !customMaterial.contains("_AXE") && !customMaterial.contains("_HOE") && !customMaterial.contains("_SPADE") && !customMaterial.contains("_PICKAXE")) {
                                        if (!e.contains("BOW") && !e.contains("STICK") && !e.contains("STRING") && !e.contains("BLAZE_ROD") && !e.contains("SHEARS") && !e.contains("BUCKET")) {
                                            type = "Weapon";
                                        } else {
                                            type = this.PlayerDataConfig.getString("ItemType.Tool." + e.substring(e.lastIndexOf("_") + 1, e.lastIndexOf("_") + 2).trim().toUpperCase() + e.substring(e.lastIndexOf("_") + 2).trim().toLowerCase());
                                        }
                                    } else {
                                        type = this.PlayerDataConfig.getString("ItemType.Tool." + customMaterial.substring(customMaterial.lastIndexOf("_") + 1, customMaterial.lastIndexOf("_") + 2).trim().toUpperCase() + customMaterial.substring(customMaterial.lastIndexOf("_") + 2).trim().toLowerCase());
                                    }
                                }
                            }
                        } else if (ItemLoreStats.plugin.isHelmet(getMaterial.getType())) {
                            for (i = 0; i < ItemLoreStats.plugin.getConfig().getList("materials.helmet").size(); ++i) {
                                if (ItemLoreStats.plugin.getConfig().getList("materials.helmet").get(i).toString().split(":")[0].equals(getMaterial.getType().toString())) {
                                    type = this.PlayerDataConfig.getString("ItemType.Armour.Helmet");
                                }
                            }
                        } else if (ItemLoreStats.plugin.isChestplate(getMaterial.getType())) {
                            for (i = 0; i < ItemLoreStats.plugin.getConfig().getList("materials.chest").size(); ++i) {
                                if (ItemLoreStats.plugin.getConfig().getList("materials.chest").get(i).toString().split(":")[0].equals(getMaterial.getType().toString())) {
                                    type = this.PlayerDataConfig.getString("ItemType.Armour.Chestplate");
                                }
                            }
                        } else if (ItemLoreStats.plugin.isLeggings(getMaterial.getType())) {
                            for (i = 0; i < ItemLoreStats.plugin.getConfig().getList("materials.legs").size(); ++i) {
                                if (ItemLoreStats.plugin.getConfig().getList("materials.legs").get(i).toString().split(":")[0].equals(getMaterial.getType().toString())) {
                                    type = this.PlayerDataConfig.getString("ItemType.Armour.Leggings");
                                }
                            }
                        } else if (ItemLoreStats.plugin.isBoots(getMaterial.getType())) {
                            for (i = 0; i < ItemLoreStats.plugin.getConfig().getList("materials.boots").size(); ++i) {
                                if (ItemLoreStats.plugin.getConfig().getList("materials.boots").get(i).toString().split(":")[0].equals(getMaterial.getType().toString())) {
                                    type = this.PlayerDataConfig.getString("ItemType.Armour.Boots");
                                }
                            }
                        }
                    } else {
                        type = this.PlayerDataConfig.getString("ItemType.Tool." + e.substring(e.lastIndexOf("_") + 1, e.lastIndexOf("_") + 2).trim().toUpperCase() + e.substring(e.lastIndexOf("_") + 2).trim().toLowerCase());
                    }
                } else {
                    type = this.PlayerDataConfig.getString("ItemType.Armour." + e.substring(e.lastIndexOf("_") + 1, e.lastIndexOf("_") + 2).trim().toUpperCase() + e.substring(e.lastIndexOf("_") + 2).trim().toLowerCase());
                }
            } else {
                type = this.PlayerDataConfig.getString("ItemType.Tool." + e.substring(e.lastIndexOf("_") + 1, e.lastIndexOf("_") + 2).trim().toUpperCase() + e.substring(e.lastIndexOf("_") + 2).trim().toLowerCase());
            }

            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataConfig.load(new File(configFile));
            return type.replaceAll("_", " ");
        } catch (Exception var7) {
            var7.printStackTrace();
            ItemLoreStats.plugin.getLogger().log(Level.SEVERE, "Unable to set item type for " + getMaterial.getType().toString());
            return "";
        }
    }
}
