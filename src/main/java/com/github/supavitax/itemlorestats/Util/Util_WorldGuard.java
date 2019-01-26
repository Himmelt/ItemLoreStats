package com.github.supavitax.itemlorestats.Util;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import org.bukkit.entity.Player;

public class Util_WorldGuard {

   ItemLoreStats main;


   public Util_WorldGuard(ItemLoreStats instance) {
      this.main = instance;
   }

   public boolean playerInPVPRegion(Player player) {
      ApplicableRegionSet set = ItemLoreStats.plugin.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
      return set.allows(DefaultFlag.PVP);
   }

   public boolean playerInInvincibleRegion(Player player) {
      ApplicableRegionSet set = ItemLoreStats.plugin.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
      return set.allows(DefaultFlag.INVINCIBILITY);
   }
}
