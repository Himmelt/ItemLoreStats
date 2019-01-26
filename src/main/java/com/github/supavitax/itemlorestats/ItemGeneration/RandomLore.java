package com.github.supavitax.itemlorestats.ItemGeneration;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_Random;
import org.bukkit.Material;

import java.util.List;

public class RandomLore {

    Util_Random random = new Util_Random();
    Util_Colours util_Colours = new Util_Colours();


    public String get(Material getMaterial) {
        String material = getMaterial.toString();
        List getRandomLore;
        String selectRandomLore;
        String materialType;
        if (!material.contains("_SWORD") && !material.contains("_AXE") && !material.contains("_HOE") && !material.contains("_SPADE") && !material.contains("_PICKAXE")) {
            if (!material.contains("_HELMET") && !material.contains("_CHESTPLATE") && !material.contains("_LEGGINGS") && !material.contains("_BOOTS")) {
                if (!material.contains("BOW") && !material.contains("STICK") && !material.contains("STRING") && !material.contains("BLAZE_ROD") && !material.contains("SHEARS") && !material.contains("BUCKET")) {
                    return "";
                } else {
                    getRandomLore = ItemLoreStats.getPlugin().getConfig().getStringList("randomLore.tools." + material.trim().toLowerCase());
                    selectRandomLore = (String) getRandomLore.get(this.random.random(getRandomLore.size()) - 1);
                    materialType = material.substring(material.lastIndexOf("_") + 1).trim().toLowerCase();
                    return this.util_Colours.replaceTooltipColour(selectRandomLore.replace("{item}", materialType.substring(0, 1).toUpperCase() + materialType.substring(1).toLowerCase()));
                }
            } else {
                getRandomLore = ItemLoreStats.getPlugin().getConfig().getStringList("randomLore.armour." + material.split("_")[0].trim().toLowerCase());
                selectRandomLore = (String) getRandomLore.get(this.random.random(getRandomLore.size()) - 1);
                materialType = material.substring(material.lastIndexOf("_") + 1).trim().toLowerCase();
                return this.util_Colours.replaceTooltipColour(selectRandomLore.replace("{item}", materialType.substring(0, 1).toUpperCase() + materialType.substring(1).toLowerCase()));
            }
        } else {
            getRandomLore = ItemLoreStats.getPlugin().getConfig().getStringList("randomLore.tools." + material.split("_")[0].trim().toLowerCase());
            selectRandomLore = (String) getRandomLore.get(this.random.random(getRandomLore.size()) - 1);
            materialType = material.substring(material.lastIndexOf("_") + 1).trim().toLowerCase();
            return this.util_Colours.replaceTooltipColour(selectRandomLore.replace("{item}", materialType.substring(0, 1).toUpperCase() + materialType.substring(1).toLowerCase()));
        }
    }
}
