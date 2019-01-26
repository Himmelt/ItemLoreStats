package com.github.supavitax.itemlorestats.Util;

import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Repair.Repair;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Util_Vault {

   GearStats gearStats = new GearStats();
   Repair repair = new Repair();
   Util_GetResponse util_GetResponse = new Util_GetResponse();
   public static Economy econ = null;
   ItemLoreStats main;


   public Util_Vault(ItemLoreStats instance) {
      this.main = instance;
   }

   public String getItemInHandName(ItemStack itemStack) {
      return itemStack.getItemMeta().getDisplayName() != null?itemStack.getItemMeta().getDisplayName():itemStack.getType().toString().substring(0, 1) + itemStack.getType().toString().substring(1).toLowerCase().replace("_", " ");
   }

   public boolean setupEconomy() {
      if(ItemLoreStats.plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
         return false;
      } else {
         RegisteredServiceProvider rsp = ItemLoreStats.plugin.getServer().getServicesManager().getRegistration(Economy.class);
         if(rsp == null) {
            return false;
         } else {
            econ = (Economy)rsp.getProvider();
            return econ != null;
         }
      }
   }

   public void removeMoneyForRepair(Player player, String type, String material) {
      this.setupEconomy();
      double getBalance = econ.getBalance(player.getName());
      int currentBalanceToBeDeducted = ItemLoreStats.plugin.getConfig().getInt("durabilityAddedOnEachRepair.repairCurrencyCost." + type + "." + material);
      EconomyResponse r = econ.withdrawPlayer(player.getName(), (double)currentBalanceToBeDeducted);
      if(Double.valueOf(getBalance).intValue() >= currentBalanceToBeDeducted) {
         if(r.transactionSuccess()) {
            this.repair.repair(player, type, material);
            player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulCurrency", player, player, this.getItemInHandName(player.getItemInHand()), String.valueOf(currentBalanceToBeDeducted)));
         }
      } else {
         player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughMoney", player, player, this.getItemInHandName(player.getItemInHand()), String.valueOf(currentBalanceToBeDeducted - Double.valueOf(getBalance).intValue())));
      }

   }

   public void removeMoneyForSale(Player player, int stackSize) {
      this.setupEconomy();
      if(player.getItemInHand().getType() != Material.AIR) {
         int currentBalanceToBeAdded = this.gearStats.getSellValueItemInHand(player) * stackSize;
         if(currentBalanceToBeAdded > 0) {
            EconomyResponse r = econ.depositPlayer(player.getName(), (double)currentBalanceToBeAdded);
            if(r.transactionSuccess()) {
               player.sendMessage(this.util_GetResponse.getResponse("SellMessages.SellSuccessful", player, player, this.getItemInHandName(player.getItemInHand()), String.valueOf(currentBalanceToBeAdded)));
               player.setItemInHand(new ItemStack(Material.AIR));
            }
         } else {
            player.sendMessage(this.util_GetResponse.getResponse("SellMessages.UnableToSell", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
         }
      } else {
         player.sendMessage(this.util_GetResponse.getResponse("SellMessages.NoItemInHand", player, player, "", ""));
      }

   }
}
