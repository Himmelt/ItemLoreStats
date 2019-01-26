package com.github.supavitax.itemlorestats.API;

import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.SetBonuses;
import com.github.supavitax.itemlorestats.Util.Util_Format;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GetStats {

   SetBonuses setBonuses = new SetBonuses();
   GearStats gearStats = new GearStats();
   Util_Format util_Format = new Util_Format();


   private double itemInHandBaseDamage(Player player) {
      double noTool = 1.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         if(player.getItemInHand().getType() == Material.WOOD_SWORD) {
            return 5.0D;
         }

         if(player.getItemInHand().getType() == Material.WOOD_AXE) {
            return 4.0D;
         }

         if(player.getItemInHand().getType() == Material.WOOD_PICKAXE) {
            return 3.0D;
         }

         if(player.getItemInHand().getType() == Material.WOOD_SPADE) {
            return 2.0D;
         }

         if(player.getItemInHand().getType() == Material.WOOD_HOE) {
            return 1.0D;
         }

         if(player.getItemInHand().getType() == Material.STONE_SWORD) {
            return 6.0D;
         }

         if(player.getItemInHand().getType() == Material.STONE_AXE) {
            return 5.0D;
         }

         if(player.getItemInHand().getType() == Material.STONE_PICKAXE) {
            return 4.0D;
         }

         if(player.getItemInHand().getType() == Material.STONE_SPADE) {
            return 3.0D;
         }

         if(player.getItemInHand().getType() == Material.STONE_HOE) {
            return 1.0D;
         }

         if(player.getItemInHand().getType() == Material.GOLD_SWORD) {
            return 5.0D;
         }

         if(player.getItemInHand().getType() == Material.GOLD_AXE) {
            return 4.0D;
         }

         if(player.getItemInHand().getType() == Material.GOLD_PICKAXE) {
            return 3.0D;
         }

         if(player.getItemInHand().getType() == Material.GOLD_SPADE) {
            return 2.0D;
         }

         if(player.getItemInHand().getType() == Material.GOLD_HOE) {
            return 1.0D;
         }

         if(player.getItemInHand().getType() == Material.IRON_SWORD) {
            return 7.0D;
         }

         if(player.getItemInHand().getType() == Material.IRON_AXE) {
            return 6.0D;
         }

         if(player.getItemInHand().getType() == Material.IRON_PICKAXE) {
            return 5.0D;
         }

         if(player.getItemInHand().getType() == Material.IRON_SPADE) {
            return 4.0D;
         }

         if(player.getItemInHand().getType() == Material.IRON_HOE) {
            return 1.0D;
         }

         if(player.getItemInHand().getType() == Material.DIAMOND_SWORD) {
            return 8.0D;
         }

         if(player.getItemInHand().getType() == Material.DIAMOND_AXE) {
            return 7.0D;
         }

         if(player.getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
            return 6.0D;
         }

         if(player.getItemInHand().getType() == Material.DIAMOND_SPADE) {
            return 5.0D;
         }

         if(player.getItemInHand().getType() == Material.DIAMOND_HOE) {
            return 1.0D;
         }
      }

      return noTool;
   }

   public double getBaseHealth() {
      return ItemLoreStats.plugin.getConfig().getDouble("baseHealth");
   }

   public double getBaseHealthRegen() {
      return ItemLoreStats.plugin.getConfig().getDouble("baseHealthRegen");
   }

   public double getHealthPerLevel() {
      return ItemLoreStats.plugin.getConfig().getDouble("healthPerLevel");
   }

   public double getBaseMovementSpeed() {
      return ItemLoreStats.plugin.getConfig().getDouble("baseMovementSpeed");
   }

   public double getBaseCritDamage() {
      return ItemLoreStats.plugin.getConfig().getDouble("baseCritDamage");
   }

   public String getDamageStatValue(Player player) {
      double damageModifier = this.setBonuses.checkHashMapDamage(player);
      String value = "0 - 0";
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[0]) + Double.parseDouble(this.gearStats.getDamageItemInHand(player).split("-")[0]) + damageModifier + " - " + (Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[1]) + Double.parseDouble(this.gearStats.getDamageItemInHand(player).split("-")[1]) + damageModifier);
      } else {
         value = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[0]) + damageModifier + " - " + (Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[1]) + damageModifier);
      }

      return value;
   }

   public double getArmourStatValue(Player player) {
      double armourModifier = this.setBonuses.checkHashMapArmour(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getArmourGear(player) + this.gearStats.getArmourItemInHand(player) + armourModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getArmourGear(player) + armourModifier);
      }

      return value;
   }

   public double getDodgeStatValue(Player player) {
      double dodgeModifier = this.setBonuses.checkHashMapDodge(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getDodgeGear(player) + this.gearStats.getDodgeItemInHand(player) + dodgeModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getDodgeGear(player) + dodgeModifier);
      }

      return value;
   }

   public double getBlockStatValue(Player player) {
      double blockModifier = this.setBonuses.checkHashMapBlock(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getBlockGear(player) + this.gearStats.getBlockItemInHand(player) + blockModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getBlockGear(player) + blockModifier);
      }

      return value;
   }

   public double getCritChanceStatValue(Player player) {
      double critChanceModifier = this.setBonuses.checkHashMapCritChance(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getCritChanceGear(player) + this.gearStats.getCritChanceItemInHand(player) + critChanceModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getCritChanceGear(player) + critChanceModifier);
      }

      return value;
   }

   public double getCritDamageStatValue(Player player) {
      double critDamageModifier = this.setBonuses.checkHashMapCritDamage(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getCritDamageGear(player) + this.gearStats.getCritDamageItemInHand(player) + critDamageModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getCritDamageGear(player) + critDamageModifier);
      }

      return value;
   }

   public double getHealthStatValue(Player player) {
      double healthModifier = this.setBonuses.checkHashMapHealth(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getHealthGear(player) + this.gearStats.getHealthItemInHand(player) + healthModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getHealthGear(player) + healthModifier);
      }

      return value;
   }

   public double getHealthRegenStatValue(Player player) {
      double healthRegenModifier = this.setBonuses.checkHashMapHealthRegen(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getHealthRegenGear(player) + this.gearStats.getHealthRegenItemInHand(player) + healthRegenModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getHealthRegenGear(player) + healthRegenModifier);
      }

      return value;
   }

   public double getLifeStealStatValue(Player player) {
      double lifeStealModifier = this.setBonuses.checkHashMapLifeSteal(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getLifeStealGear(player) + this.gearStats.getLifeStealItemInHand(player) + lifeStealModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getLifeStealGear(player) + lifeStealModifier);
      }

      return value;
   }

   public double getFireStatValue(Player player) {
      double fireModifier = this.setBonuses.checkHashMapFire(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getFireGear(player) + this.gearStats.getFireItemInHand(player) + fireModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getFireGear(player) + fireModifier);
      }

      return value;
   }

   public double getIceStatValue(Player player) {
      double iceModifier = this.setBonuses.checkHashMapIce(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getIceGear(player) + this.gearStats.getIceItemInHand(player) + iceModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getIceGear(player) + iceModifier);
      }

      return value;
   }

   public double getReflectStatValue(Player player) {
      double reflectModifier = this.setBonuses.checkHashMapReflect(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getReflectGear(player) + this.gearStats.getReflectItemInHand(player) + reflectModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getReflectGear(player) + reflectModifier);
      }

      return value;
   }

   public double getPoisonStatValue(Player player) {
      double poisonModifier = this.setBonuses.checkHashMapPoison(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getPoisonGear(player) + this.gearStats.getPoisonItemInHand(player) + poisonModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getPoisonGear(player) + poisonModifier);
      }

      return value;
   }

   public double getWitherStatValue(Player player) {
      double witherModifier = this.setBonuses.checkHashMapWither(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getWitherGear(player) + this.gearStats.getWitherItemInHand(player) + witherModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getWitherGear(player) + witherModifier);
      }

      return value;
   }

   public double getHarmingStatValue(Player player) {
      double harmingModifier = this.setBonuses.checkHashMapHarming(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getHarmingGear(player) + this.gearStats.getHarmingItemInHand(player) + harmingModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getHarmingGear(player) + harmingModifier);
      }

      return value;
   }

   public double getBlindStatValue(Player player) {
      double blindModifier = this.setBonuses.checkHashMapBlind(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getBlindGear(player) + this.gearStats.getBlindItemInHand(player) + blindModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getBlindGear(player) + blindModifier);
      }

      return value;
   }

   public double getMovementSpeedStatValue(Player player) {
      double speedModifier = this.setBonuses.checkHashMapSpeed(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getMovementSpeedGear(player) + this.gearStats.getMovementSpeedItemInHand(player) + speedModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getMovementSpeedGear(player) + speedModifier);
      }

      return value;
   }

   public double getXPMultiplierStatValue(Player player) {
      double xpmultiplierModifier = this.setBonuses.checkHashMapXPMultiplier(player);
      double value = 0.0D;
      if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
         value = this.util_Format.format(this.gearStats.getXPMultiplierGear(player) + this.gearStats.getXPMultiplierItemInHand(player) + xpmultiplierModifier);
      } else {
         value = this.util_Format.format(this.gearStats.getXPMultiplierGear(player) + xpmultiplierModifier);
      }

      return value;
   }

   public double getItemInHandValue(Player player) {
      double value = 0.0D;
      value = this.util_Format.format((double)this.gearStats.getSellValueItemInHand(player));
      return value;
   }
}
