package com.github.supavitax.itemlorestats.ItemGeneration;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import org.bukkit.Material;

public class SellValue {
    public double get(Material getMaterial) {
        String material = getMaterial.toString();
        return !material.contains("_SWORD") && !material.contains("_AXE") && !material.contains("_HOE") && !material.contains("_SPADE") && !material.contains("_PICKAXE") ? (!material.contains("_HELMET") && !material.contains("_CHESTPLATE") && !material.contains("_LEGGINGS") && !material.contains("_BOOTS") ? (!material.contains("BOW") && !material.contains("STICK") && !material.contains("STRING") && !material.contains("BLAZE_ROD") && !material.contains("SHEARS") && !material.contains("BUCKET") ? 0.0D : ItemLoreStats.getPlugin().getConfig().getDouble("materialSellValue.tools." + material.replaceAll("_", "").toLowerCase())) : ItemLoreStats.getPlugin().getConfig().getDouble("materialSellValue.armour." + material.split("_")[0].trim().toLowerCase() + "." + material.substring(material.lastIndexOf("_") + 1).trim().toLowerCase())) : ItemLoreStats.getPlugin().getConfig().getDouble("materialSellValue.tools." + material.split("_")[0].trim().toLowerCase() + "." + material.substring(material.lastIndexOf("_") + 1).trim().toLowerCase());
    }
}
