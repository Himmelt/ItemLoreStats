package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_EntityManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Vanilla_Sharpness {

   Util_EntityManager util_EntityManager = new Util_EntityManager();


   public boolean hasSharpness(ItemStack itemInHand) {
      return ItemLoreStats.plugin.isTool(itemInHand.getType()) && itemInHand.getEnchantments().containsKey(Enchantment.DAMAGE_ALL);
   }

   public double calculateNewDamage(double damage, int enchantLevel) {
      double value = damage + damage / 100.0D * ItemLoreStats.plugin.getConfig().getDouble("enchants.sharpness.levelMultiplier") * (double)enchantLevel;
      return value;
   }
}
