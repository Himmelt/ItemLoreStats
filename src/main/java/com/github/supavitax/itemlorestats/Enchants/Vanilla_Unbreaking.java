package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_EntityManager;
import com.github.supavitax.itemlorestats.Util.Util_Random;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Vanilla_Unbreaking {

   Util_EntityManager util_EntityManager = new Util_EntityManager();
   Util_Random util_Random = new Util_Random();


   public boolean hasUnbreaking(ItemStack itemInHand) {
      return itemInHand.getEnchantments().containsKey(Enchantment.DURABILITY);
   }

   public int calculateNewDurabilityLoss(int enchantLevel, String durabilityLost) {
      int r = this.util_Random.random(1000);
      return r <= ItemLoreStats.plugin.getConfig().getInt("enchants.unbreaking.levelMultiplier") * enchantLevel * 10?ItemLoreStats.plugin.getConfig().getInt("environmentalDamage." + durabilityLost + ".durabilityLost"):0;
   }
}
