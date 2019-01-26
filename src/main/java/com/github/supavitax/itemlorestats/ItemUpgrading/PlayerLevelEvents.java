package com.github.supavitax.itemlorestats.ItemUpgrading;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.ItemUpgrading.ItemUpgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class PlayerLevelEvents implements Listener {

   ItemUpgrade itemUpgrade = new ItemUpgrade();


   @EventHandler
   public void onPlayerLevel(PlayerLevelChangeEvent event) {
      if(event.getPlayer() instanceof Player) {
         Player player = event.getPlayer();
         if(event.getNewLevel() <= ItemLoreStats.plugin.getConfig().getInt("levelCap")) {
            if(ItemLoreStats.plugin.getConfig().getBoolean("upgradeStatsOnLevelChange.enabled") && event.getNewLevel() > event.getOldLevel()) {
               this.itemUpgrade.increaseItemStatOnItemInHand(player);
               this.itemUpgrade.increaseItemStatOnHelmet(player);
               this.itemUpgrade.increaseItemStatOnChestplate(player);
               this.itemUpgrade.increaseItemStatOnLeggings(player);
               this.itemUpgrade.increaseItemStatOnBoots(player);
               ItemLoreStats.plugin.updateHealth(player);
               ItemLoreStats.plugin.updatePlayerSpeed(player);
               ItemLoreStats.plugin.updateMana(player);
            }
         } else {
            player.setLevel(ItemLoreStats.plugin.getConfig().getInt("levelCap"));
            player.setExp(0.0F);
         }
      }

   }
}
