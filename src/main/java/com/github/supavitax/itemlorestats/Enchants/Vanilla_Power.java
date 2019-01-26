package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_EntityManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Vanilla_Power {

    Util_EntityManager util_EntityManager = new Util_EntityManager();


    public boolean hasPower(ItemStack itemInHand) {
        return ItemLoreStats.plugin.isTool(itemInHand.getType()) && itemInHand.getEnchantments().containsKey(Enchantment.ARROW_DAMAGE);
    }

    public double calculateNewDamage(double damage, int enchantLevel) {
        double value = damage + damage / 100.0D * ItemLoreStats.plugin.getConfig().getDouble("enchants.power.levelMultiplier") * (double) enchantLevel;
        return value;
    }
}
