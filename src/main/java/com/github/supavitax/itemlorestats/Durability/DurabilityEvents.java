package com.github.supavitax.itemlorestats.Durability;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.SetBonuses;
import com.github.supavitax.itemlorestats.Enchants.Vanilla_Unbreaking;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DurabilityEvents implements Listener {

   SetBonuses setBonuses = new SetBonuses();
   Util_Colours util_Colours = new Util_Colours();
   Vanilla_Unbreaking vanilla_Unbreaking = new Vanilla_Unbreaking();


   @EventHandler(
      ignoreCancelled = true
   )
   public void disableItemBreakItemInHand(PlayerItemBreakEvent event) {
      if(!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
         if(ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
            return;
         }

         if(event.getPlayer().isDead()) {
            return;
         }

         String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
         String durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
         final ItemStack item = event.getBrokenItem().clone();

         try {
            if(item != null && item.getItemMeta().hasLore()) {
               if(ItemLoreStats.plugin.isTool(item.getType())) {
                  List ex = item.getItemMeta().getLore();
                  Iterator var7 = ex.iterator();

                  while(var7.hasNext()) {
                     String getDurability = (String)var7.next();
                     if(ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                        String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                        int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
                        if(currentValueMinusOne > 1) {
                           double value = Double.valueOf((double)currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();
                           item.setDurability((short)((int)Math.abs(value * (double)item.getType().getMaxDurability() - (double)item.getType().getMaxDurability())));
                        } else {
                           item.setDurability((short)1);
                        }
                     }
                  }
               } else if(ItemLoreStats.plugin.isArmour(item.getType())) {
                  if(ItemLoreStats.plugin.isHelmet(item.getType())) {
                     ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                           item.setDurability((short)-1);
                        }
                     }, 1L);
                  } else if(ItemLoreStats.plugin.isChestplate(item.getType())) {
                     ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                           item.setDurability((short)-1);
                        }
                     }, 1L);
                  } else if(ItemLoreStats.plugin.isLeggings(item.getType())) {
                     ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                           item.setDurability((short)-1);
                        }
                     }, 1L);
                  } else if(ItemLoreStats.plugin.isBoots(item.getType())) {
                     ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                           item.setDurability((short)-1);
                        }
                     }, 1L);
                  }
               }
            }
         } catch (Exception var12) {
            System.out.println(var12);
         }
      }

   }

   @EventHandler
   public void blockBreakDurability(BlockBreakEvent event) {
      if(!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
         if(ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
            return;
         }

         Player player = event.getPlayer();
         if(ItemLoreStats.plugin.isHoe(player.getItemInHand()) || player.getItemInHand().getType().equals(Material.FLINT_AND_STEEL) || player.getItemInHand().getType().equals(Material.FISHING_ROD)) {
            return;
         }

         if(player.getItemInHand() != null && ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            if(player.getItemInHand().getItemMeta().hasLore()) {
               String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
               String durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
               List getItemLore = player.getItemInHand().getItemMeta().getLore();
               Iterator setItemInHandMeta = getItemLore.iterator();

               while(setItemInHandMeta.hasNext()) {
                  String setItemInHand = (String)setItemInHandMeta.next();
                  if(ChatColor.stripColor(setItemInHand).startsWith(durabilityName)) {
                     String durabilityRebuilder = "";
                     String durabilityAmountColour = "";
                     String prefixColourOnly = "";
                     int index = getItemLore.indexOf(setItemInHand);
                     String maximumValue = ChatColor.stripColor(setItemInHand).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                     int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(setItemInHand).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
                     if(currentValueMinusOne + 1 < 2) {
                        player.playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                        player.getInventory().remove(player.getItemInHand());
                        return;
                     }

                     double value = Double.valueOf((double)currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();
                     player.getItemInHand().setDurability((short)((int)Math.abs(value * (double)player.getItemInHand().getType().getMaxDurability() - (double)player.getItemInHand().getType().getMaxDurability())));
                     if(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)).contains("&")) {
                        if(setItemInHand.length() > 4) {
                           if(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(2, 4)).contains("&")) {
                              prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(2, 4)));
                           } else {
                              prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)));
                           }
                        } else {
                           prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)));
                        }
                     } else {
                        prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
                     }

                     if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&")) {
                        if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 4) {
                           if(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                              durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(2, 4);
                           } else {
                              durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2);
                           }
                        } else {
                           durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2);
                        }
                     } else {
                        durabilityAmountColour = prefixColourOnly;
                     }

                     if(this.vanilla_Unbreaking.hasUnbreaking(player.getItemInHand())) {
                        currentValueMinusOne += this.vanilla_Unbreaking.calculateNewDurabilityLoss(player.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY), "damage");
                     }

                     int remainingDurabilityPercentage = currentValueMinusOne * 100 / Integer.parseInt(maximumValue);
                     if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 2.6D) {
                        if(remainingDurabilityPercentage == 25 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning")) {
                           player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                        }

                        durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.RED + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                     } else if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 5.1D) {
                        if(remainingDurabilityPercentage == 50 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning")) {
                           player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                        }

                        durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.YELLOW + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                     } else if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 7.6D) {
                        if(remainingDurabilityPercentage == 75 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning")) {
                           player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                        }

                        durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.GREEN + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                     } else {
                        durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                     }

                     getItemLore.set(index, durabilityRebuilder);
                  }
               }

               ItemStack setItemInHand1 = new ItemStack(player.getItemInHand());
               ItemMeta setItemInHandMeta1 = setItemInHand1.getItemMeta();
               setItemInHandMeta1.setLore(getItemLore);
               setItemInHand1.setItemMeta(setItemInHandMeta1);
               player.setItemInHand(new ItemStack(setItemInHand1));
            }

            ItemLoreStats.plugin.checkWeaponSpeed(player);
         }
      }

   }

   @EventHandler
   public void fishingDurability(PlayerFishEvent event) {
      if(!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
         if(ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
            return;
         }

         Player player = event.getPlayer();
         if(player.getItemInHand() != null && ItemLoreStats.plugin.isTool(player.getItemInHand().getType()) && player.getItemInHand().getItemMeta().hasLore()) {
            String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
            String durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
            List getItemLore = player.getItemInHand().getItemMeta().getLore();
            Iterator setItemInHandMeta = getItemLore.iterator();

            while(setItemInHandMeta.hasNext()) {
               String setItemInHand = (String)setItemInHandMeta.next();
               if(ChatColor.stripColor(setItemInHand).startsWith(durabilityName)) {
                  String durabilityRebuilder = "";
                  String durabilityAmountColour = "";
                  String prefixColourOnly = "";
                  int index = getItemLore.indexOf(setItemInHand);
                  String maximumValue = ChatColor.stripColor(setItemInHand).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                  int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(setItemInHand).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
                  if(currentValueMinusOne + 1 < 2) {
                     player.playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                     player.getInventory().remove(player.getItemInHand());
                     return;
                  }

                  double value = Double.valueOf((double)currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();
                  player.getItemInHand().setDurability((short)((int)Math.abs(value * (double)player.getItemInHand().getType().getMaxDurability() - (double)player.getItemInHand().getType().getMaxDurability())));
                  if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)).contains("&")) {
                     if(setItemInHand.length() > 4) {
                        if(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(2, 4)).contains("&")) {
                           prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(2, 4)));
                        } else {
                           prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)));
                        }
                     } else {
                        prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)));
                     }
                  } else {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
                  }

                  if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&")) {
                     if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 4) {
                        if(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                           durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(2, 4);
                        } else {
                           durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2);
                        }
                     } else {
                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2);
                     }
                  } else {
                     durabilityAmountColour = prefixColourOnly;
                  }

                  if(this.vanilla_Unbreaking.hasUnbreaking(player.getItemInHand())) {
                     currentValueMinusOne += this.vanilla_Unbreaking.calculateNewDurabilityLoss(player.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY), "damage");
                  }

                  int remainingDurabilityPercentage = currentValueMinusOne * 100 / Integer.parseInt(maximumValue);
                  if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 2.6D) {
                     if(remainingDurabilityPercentage == 25 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.RED + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 5.1D) {
                     if(remainingDurabilityPercentage == 50 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.YELLOW + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 7.6D) {
                     if(remainingDurabilityPercentage == 75 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.GREEN + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else {
                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  }

                  getItemLore.set(index, durabilityRebuilder);
               }
            }

            ItemStack setItemInHand1 = new ItemStack(player.getItemInHand());
            ItemMeta setItemInHandMeta1 = setItemInHand1.getItemMeta();
            setItemInHandMeta1.setLore(getItemLore);
            setItemInHand1.setItemMeta(setItemInHandMeta1);
            player.setItemInHand(new ItemStack(setItemInHand1));
         }
      }

   }

   @EventHandler
   public void carrotStickDurability(PlayerInteractEvent event) {
      if(!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
         if(ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
            return;
         }

         Player player = event.getPlayer();
         if(player.getItemInHand().getType().equals(Material.CARROT_STICK) && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && player.getItemInHand() != null && ItemLoreStats.plugin.isTool(player.getItemInHand().getType()) && player.getItemInHand().getItemMeta().hasLore()) {
            String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
            String durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
            List getItemLore = player.getItemInHand().getItemMeta().getLore();
            Iterator setItemInHandMeta = getItemLore.iterator();

            while(setItemInHandMeta.hasNext()) {
               String setItemInHand = (String)setItemInHandMeta.next();
               if(ChatColor.stripColor(setItemInHand).startsWith(durabilityName)) {
                  String durabilityRebuilder = "";
                  String durabilityAmountColour = "";
                  String prefixColourOnly = "";
                  int index = getItemLore.indexOf(setItemInHand);
                  String maximumValue = ChatColor.stripColor(setItemInHand).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                  int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(setItemInHand).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
                  if(currentValueMinusOne + 1 < 2) {
                     player.playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                     player.getInventory().remove(player.getItemInHand());
                     return;
                  }

                  double value = Double.valueOf((double)currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();
                  player.getItemInHand().setDurability((short)((int)Math.abs(value * (double)player.getItemInHand().getType().getMaxDurability() - (double)player.getItemInHand().getType().getMaxDurability())));
                  if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)).contains("&")) {
                     if(setItemInHand.length() > 4) {
                        if(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(2, 4)).contains("&")) {
                           prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(2, 4)));
                        } else {
                           prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)));
                        }
                     } else {
                        prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)));
                     }
                  } else {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
                  }

                  if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&")) {
                     if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 4) {
                        if(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                           durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(2, 4);
                        } else {
                           durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2);
                        }
                     } else {
                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2);
                     }
                  } else {
                     durabilityAmountColour = prefixColourOnly;
                  }

                  if(this.vanilla_Unbreaking.hasUnbreaking(player.getItemInHand())) {
                     currentValueMinusOne += this.vanilla_Unbreaking.calculateNewDurabilityLoss(player.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY), "damage");
                  }

                  int remainingDurabilityPercentage = currentValueMinusOne * 100 / Integer.parseInt(maximumValue);
                  if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 2.6D) {
                     if(remainingDurabilityPercentage == 25 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.RED + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 5.1D) {
                     if(remainingDurabilityPercentage == 50 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.YELLOW + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 7.6D) {
                     if(remainingDurabilityPercentage == 75 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.GREEN + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else {
                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  }

                  getItemLore.set(index, durabilityRebuilder);
               }
            }

            ItemStack setItemInHand1 = new ItemStack(player.getItemInHand());
            ItemMeta setItemInHandMeta1 = setItemInHand1.getItemMeta();
            setItemInHandMeta1.setLore(getItemLore);
            setItemInHand1.setItemMeta(setItemInHandMeta1);
            player.setItemInHand(new ItemStack(setItemInHand1));
         }
      }

   }

   @EventHandler
   public void flintAndSteelDurability(PlayerInteractEvent event) {
      if(!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
         if(ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
            return;
         }

         Player player = event.getPlayer();
         if(player.getItemInHand().getType().equals(Material.FLINT_AND_STEEL) && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && player.getItemInHand() != null && ItemLoreStats.plugin.isTool(player.getItemInHand().getType()) && player.getItemInHand().getItemMeta().hasLore()) {
            String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
            String durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
            List getItemLore = player.getItemInHand().getItemMeta().getLore();
            Iterator setItemInHandMeta = getItemLore.iterator();

            while(setItemInHandMeta.hasNext()) {
               String setItemInHand = (String)setItemInHandMeta.next();
               if(ChatColor.stripColor(setItemInHand).startsWith(durabilityName)) {
                  String durabilityRebuilder = "";
                  String durabilityAmountColour = "";
                  String prefixColourOnly = "";
                  int index = getItemLore.indexOf(setItemInHand);
                  String maximumValue = ChatColor.stripColor(setItemInHand).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                  int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(setItemInHand).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
                  if(currentValueMinusOne + 1 < 2) {
                     player.playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                     player.getInventory().remove(player.getItemInHand());
                     return;
                  }

                  double value = Double.valueOf((double)currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();
                  player.getItemInHand().setDurability((short)((int)Math.abs(value * (double)player.getItemInHand().getType().getMaxDurability() - (double)player.getItemInHand().getType().getMaxDurability())));
                  if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)).contains("&")) {
                     if(setItemInHand.length() > 4) {
                        if(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(2, 4)).contains("&")) {
                           prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(2, 4)));
                        } else {
                           prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)));
                        }
                     } else {
                        prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)));
                     }
                  } else {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
                  }

                  if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&")) {
                     if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 4) {
                        if(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                           durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(2, 4);
                        } else {
                           durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2);
                        }
                     } else {
                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2);
                     }
                  } else {
                     durabilityAmountColour = prefixColourOnly;
                  }

                  if(this.vanilla_Unbreaking.hasUnbreaking(player.getItemInHand())) {
                     currentValueMinusOne += this.vanilla_Unbreaking.calculateNewDurabilityLoss(player.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY), "damage");
                  }

                  int remainingDurabilityPercentage = currentValueMinusOne * 100 / Integer.parseInt(maximumValue);
                  if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 2.6D) {
                     if(remainingDurabilityPercentage == 25 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.RED + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 5.1D) {
                     if(remainingDurabilityPercentage == 50 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.YELLOW + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 7.6D) {
                     if(remainingDurabilityPercentage == 75 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.GREEN + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else {
                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  }

                  getItemLore.set(index, durabilityRebuilder);
               }
            }

            ItemStack setItemInHand1 = new ItemStack(player.getItemInHand());
            ItemMeta setItemInHandMeta1 = setItemInHand1.getItemMeta();
            setItemInHandMeta1.setLore(getItemLore);
            setItemInHand1.setItemMeta(setItemInHandMeta1);
            player.setItemInHand(new ItemStack(setItemInHand1));
         }
      }

   }

   @EventHandler
   public void hoeDurability(PlayerInteractEvent event) {
      if(!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
         if(ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
            return;
         }

         Player player = event.getPlayer();
         if((player.getItemInHand().getType().equals(Material.WOOD_HOE) || player.getItemInHand().getType().equals(Material.STONE_HOE) || player.getItemInHand().getType().equals(Material.IRON_HOE) || player.getItemInHand().getType().equals(Material.DIAMOND_HOE)) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType().equals(Material.GRASS) || event.getClickedBlock().getType().equals(Material.DIRT)) && player.getItemInHand() != null && ItemLoreStats.plugin.isTool(player.getItemInHand().getType()) && player.getItemInHand().getItemMeta().hasLore()) {
            String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
            String durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
            List getItemLore = player.getItemInHand().getItemMeta().getLore();
            Iterator setItemInHandMeta = getItemLore.iterator();

            while(setItemInHandMeta.hasNext()) {
               String setItemInHand = (String)setItemInHandMeta.next();
               if(ChatColor.stripColor(setItemInHand).startsWith(durabilityName)) {
                  String durabilityRebuilder = "";
                  String durabilityAmountColour = "";
                  String prefixColourOnly = "";
                  int index = getItemLore.indexOf(setItemInHand);
                  String maximumValue = ChatColor.stripColor(setItemInHand).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                  int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(setItemInHand).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
                  if(currentValueMinusOne + 1 < 2) {
                     player.playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                     player.getInventory().remove(player.getItemInHand());
                     return;
                  }

                  double value = Double.valueOf((double)currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();
                  player.getItemInHand().setDurability((short)((int)Math.abs(value * (double)player.getItemInHand().getType().getMaxDurability() - (double)player.getItemInHand().getType().getMaxDurability())));
                  if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)).contains("&")) {
                     if(setItemInHand.length() > 4) {
                        if(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(2, 4)).contains("&")) {
                           prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(2, 4)));
                        } else {
                           prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)));
                        }
                     } else {
                        prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)));
                     }
                  } else {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
                  }

                  if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&")) {
                     if(setItemInHand.split(durabilitySplitter)[1].trim().length() > 4) {
                        if(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                           durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(2, 4);
                        } else {
                           durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2);
                        }
                     } else {
                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim()).substring(0, 2);
                     }
                  } else {
                     durabilityAmountColour = prefixColourOnly;
                  }

                  if(this.vanilla_Unbreaking.hasUnbreaking(player.getItemInHand())) {
                     currentValueMinusOne += this.vanilla_Unbreaking.calculateNewDurabilityLoss(player.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY), "damage");
                  }

                  int remainingDurabilityPercentage = currentValueMinusOne * 100 / Integer.parseInt(maximumValue);
                  if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 2.6D) {
                     if(remainingDurabilityPercentage == 25 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.RED + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 5.1D) {
                     if(remainingDurabilityPercentage == 50 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.YELLOW + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else if((double)currentValueMinusOne < (double)(Integer.parseInt(maximumValue) / 10) * 7.6D) {
                     if(remainingDurabilityPercentage == 75 && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning")) {
                        player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                     }

                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.GREEN + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  } else {
                     durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                  }

                  getItemLore.set(index, durabilityRebuilder);
               }
            }

            ItemStack setItemInHand1 = new ItemStack(player.getItemInHand());
            ItemMeta setItemInHandMeta1 = setItemInHand1.getItemMeta();
            setItemInHandMeta1.setLore(getItemLore);
            setItemInHand1.setItemMeta(setItemInHandMeta1);
            player.setItemInHand(new ItemStack(setItemInHand1));
         }
      }

   }
}
