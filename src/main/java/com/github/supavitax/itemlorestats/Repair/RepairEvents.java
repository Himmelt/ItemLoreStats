package com.github.supavitax.itemlorestats.Repair;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Repair.Repair;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RepairEvents implements Listener {

   Repair repair = new Repair();


   public Material getRepairBlock() {
      if(ItemLoreStats.plugin.getConfig().getString("durabilityAddedOnEachRepair.repairBlock") != null) {
         Material repairBlock = Material.getMaterial(ItemLoreStats.plugin.getConfig().getString("durabilityAddedOnEachRepair.repairBlock"));
         return repairBlock;
      } else {
         return Material.WORKBENCH;
      }
   }

   @EventHandler
   public void repairItemOnLeftClick(PlayerInteractEvent event) {
      if(ItemLoreStats.plugin.getConfig().getBoolean("enableItemRepairing") && (event.getAction() == Action.LEFT_CLICK_BLOCK && ItemLoreStats.plugin.isTool(event.getPlayer().getItemInHand().getType()) || event.getAction() == Action.LEFT_CLICK_BLOCK && ItemLoreStats.plugin.isArmour(event.getPlayer().getItemInHand().getType())) && event.getClickedBlock().getType().equals(this.getRepairBlock()) && !ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
         if(ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO") || event.getClickedBlock() == null) {
            return;
         }

         Player player = event.getPlayer();
         if(ItemLoreStats.plugin.isTool(player.getItemInHand().getType()) || ItemLoreStats.plugin.isArmour(player.getItemInHand().getType())) {
            this.repair.payAndRepair(player, player.getItemInHand().getType());
         }
      }

   }
}
