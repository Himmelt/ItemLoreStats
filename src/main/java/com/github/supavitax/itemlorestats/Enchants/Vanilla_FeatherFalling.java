package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_EntityManager;
import com.github.supavitax.itemlorestats.Util.Util_Random;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Vanilla_FeatherFalling {
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Random util_Random = new Util_Random();

    public boolean hasFeatherFalling(ItemStack boots) {
        return boots.getEnchantments().containsKey(Enchantment.PROTECTION_FALL);
    }

    public double calculateNewFallDamage(int enchantLevel, double fallDamage) {
        int percentage = enchantLevel * ItemLoreStats.plugin.getConfig().getInt("enchants.featherFalling.levelMultiplier");
        double value = fallDamage - fallDamage * (double) percentage / 100.0D;
        return value;
    }
}
