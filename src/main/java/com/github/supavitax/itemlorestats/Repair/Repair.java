package com.github.supavitax.itemlorestats.Repair;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Repair {

   Util_Colours util_Colours = new Util_Colours();
   Util_GetResponse util_GetResponse = new Util_GetResponse();


   public void repair(Player player, String type, String material) {
      int repairCost = ItemLoreStats.plugin.getConfig().getInt("durabilityAddedOnEachRepair." + type + "." + material);
      if(player.getItemInHand().getItemMeta().hasLore()) {
         List splitItemLore = player.getItemInHand().getItemMeta().getLore();
         Iterator var7 = splitItemLore.iterator();

         while(var7.hasNext()) {
            String getItemStat = (String)var7.next();
            String durabilityAmountColour = "";
            String prefixColourOnly = "";
            String durabilityRebuilder = "";
            String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
            if(ChatColor.stripColor(getItemStat).startsWith(durabilityName)) {
               int currentAmount = Integer.parseInt(this.util_Colours.extractAndReplaceTooltipColour(ChatColor.stripColor(getItemStat).split(": ")[1].split("/")[0]).replaceAll("&([0-9a-f])", ""));
               int maxAmount = Integer.parseInt(this.util_Colours.extractAndReplaceTooltipColour(ChatColor.stripColor(getItemStat).split(": ")[1].split("/")[1]).replaceAll("&([0-9a-f])", ""));
               int index = splitItemLore.indexOf(getItemStat);
               if(currentAmount + repairCost > maxAmount) {
                  currentAmount = maxAmount;
               } else {
                  currentAmount += repairCost;
               }

               double value = Double.valueOf((double)currentAmount).doubleValue() / Double.valueOf((double)maxAmount).doubleValue();
               player.getItemInHand().setDurability((short)((int)Math.abs(value * (double)player.getItemInHand().getType().getMaxDurability() - (double)player.getItemInHand().getType().getMaxDurability())));
               if(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(0, 2)).contains("&")) {
                  if(getItemStat.length() > 4) {
                     if(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(2, 4)).contains("&")) {
                        prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(2, 4)));
                     } else {
                        prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(0, 2)));
                     }
                  } else {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(0, 2)));
                  }
               } else {
                  prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
               }

               if(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim().substring(0, 2)).contains("&")) {
                  if(getItemStat.split("/")[1].trim().length() > 4) {
                     if(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim().substring(2, 4)).contains("&")) {
                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim()).substring(2, 4);
                     } else {
                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim()).substring(0, 2);
                     }
                  } else {
                     durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim()).substring(0, 2);
                  }
               } else {
                  durabilityAmountColour = prefixColourOnly;
               }

               durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentAmount + this.util_Colours.replaceTooltipColour(prefixColourOnly) + "/" + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maxAmount;
               splitItemLore.set(index, durabilityRebuilder);
               ItemStack repairedItem = new ItemStack(player.getItemInHand());
               ItemMeta repairedItemMeta = repairedItem.getItemMeta();
               repairedItemMeta.setLore(splitItemLore);
               repairedItem.setItemMeta(repairedItemMeta);
               player.setItemInHand(repairedItem);
            }
         }
      }

   }

   public boolean isFullDurability(Player player) {
      if(player.getItemInHand().getItemMeta().hasLore()) {
         List splitItemLore = player.getItemInHand().getItemMeta().getLore();
         Iterator var4 = splitItemLore.iterator();

         while(var4.hasNext()) {
            String getItemStat = (String)var4.next();
            String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
            if(ChatColor.stripColor(getItemStat).startsWith(durabilityName)) {
               int currentAmount = Integer.parseInt(this.util_Colours.extractAndReplaceTooltipColour(ChatColor.stripColor(getItemStat).split(": ")[1].split("/")[0]).replaceAll("&([0-9a-f])", ""));
               int maxAmount = Integer.parseInt(this.util_Colours.extractAndReplaceTooltipColour(ChatColor.stripColor(getItemStat).split(": ")[1].split("/")[1]).replaceAll("&([0-9a-f])", ""));
               if(currentAmount == maxAmount) {
                  return true;
               }

               return false;
            }
         }
      }

      return false;
   }

   public String getItemInHandName(ItemStack itemStack) {
      return itemStack.getItemMeta().getDisplayName() != null?itemStack.getItemMeta().getDisplayName():itemStack.getType().toString().substring(0, 1) + itemStack.getType().toString().substring(1).toLowerCase().replace("_", " ");
   }

   public void payAndRepair(Player player, Material type) {
      if(!this.isFullDurability(player)) {
         if(ItemLoreStats.plugin.getConfig().getString("durabilityAddedOnEachRepair.repairCostType").equalsIgnoreCase("Currency")) {
            if(type.equals(Material.WOOD_AXE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "wood");
            } else if(type.equals(Material.STONE_AXE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stone");
            } else if(type.equals(Material.IRON_AXE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "iron");
            } else if(type.equals(Material.GOLD_AXE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "gold");
            } else if(type.equals(Material.DIAMOND_AXE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "diamond");
            } else if(type.equals(Material.WOOD_HOE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "wood");
            } else if(type.equals(Material.STONE_HOE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stone");
            } else if(type.equals(Material.IRON_HOE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "iron");
            } else if(type.equals(Material.GOLD_HOE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "gold");
            } else if(type.equals(Material.DIAMOND_HOE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "diamond");
            } else if(type.equals(Material.WOOD_SPADE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "wood");
            } else if(type.equals(Material.STONE_SPADE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stone");
            } else if(type.equals(Material.IRON_SPADE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "iron");
            } else if(type.equals(Material.GOLD_SPADE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "gold");
            } else if(type.equals(Material.DIAMOND_SPADE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "diamond");
            } else if(type.equals(Material.WOOD_PICKAXE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "wood");
            } else if(type.equals(Material.STONE_PICKAXE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stone");
            } else if(type.equals(Material.IRON_PICKAXE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "iron");
            } else if(type.equals(Material.GOLD_PICKAXE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "gold");
            } else if(type.equals(Material.DIAMOND_PICKAXE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "diamond");
            } else if(type.equals(Material.WOOD_SWORD)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "wood");
            } else if(type.equals(Material.STONE_SWORD)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stone");
            } else if(type.equals(Material.IRON_SWORD)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "iron");
            } else if(type.equals(Material.GOLD_SWORD)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "gold");
            } else if(type.equals(Material.DIAMOND_SWORD)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "diamond");
            } else if(type.equals(Material.SHEARS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "shears");
            } else if(type.equals(Material.BOW)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "bow");
            } else if(type.equals(Material.STICK)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stick");
            } else if(type.equals(Material.FLINT_AND_STEEL)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "flintAndSteel");
            } else if(type.equals(Material.FISHING_ROD)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "fishingRod");
            } else if(type.equals(Material.CARROT_STICK)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "carrotStick");
            } else if(type.equals(Material.SHEARS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "tools", "shears");
            } else if(type.equals(Material.LEATHER_HELMET)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "leather");
            } else if(type.equals(Material.CHAINMAIL_HELMET)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "chainmail");
            } else if(type.equals(Material.IRON_HELMET)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "iron");
            } else if(type.equals(Material.GOLD_HELMET)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "gold");
            } else if(type.equals(Material.DIAMOND_HELMET)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "diamond");
            } else if(type.equals(Material.LEATHER_CHESTPLATE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "leather");
            } else if(type.equals(Material.CHAINMAIL_CHESTPLATE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "chainmail");
            } else if(type.equals(Material.IRON_CHESTPLATE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "iron");
            } else if(type.equals(Material.GOLD_CHESTPLATE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "gold");
            } else if(type.equals(Material.DIAMOND_CHESTPLATE)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "diamond");
            } else if(type.equals(Material.LEATHER_LEGGINGS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "leather");
            } else if(type.equals(Material.CHAINMAIL_LEGGINGS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "chainmail");
            } else if(type.equals(Material.IRON_LEGGINGS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "iron");
            } else if(type.equals(Material.GOLD_LEGGINGS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "gold");
            } else if(type.equals(Material.DIAMOND_LEGGINGS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "diamond");
            } else if(type.equals(Material.LEATHER_BOOTS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "leather");
            } else if(type.equals(Material.CHAINMAIL_BOOTS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "chainmail");
            } else if(type.equals(Material.IRON_BOOTS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "iron");
            } else if(type.equals(Material.GOLD_BOOTS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "gold");
            } else if(type.equals(Material.DIAMOND_BOOTS)) {
               ItemLoreStats.plugin.util_Vault.removeMoneyForRepair(player, "armour", "diamond");
            }
         } else if(type.equals(Material.WOOD_AXE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 3)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 3)});
                  this.repair(player, "tools", "wood");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.STONE_AXE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 3)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.COBBLESTONE, 3)});
                  this.repair(player, "tools", "stone");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCobblestone", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.IRON_AXE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 3)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 3)});
                  this.repair(player, "tools", "iron");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.GOLD_AXE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 3)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 3)});
                  this.repair(player, "tools", "gold");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.DIAMOND_AXE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 3)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 3)});
                  this.repair(player, "tools", "diamond");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.WOOD_HOE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 2)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 2)});
                  this.repair(player, "tools", "wood");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.STONE_HOE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 2)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.COBBLESTONE, 2)});
                  this.repair(player, "tools", "stone");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCobblestone", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.IRON_HOE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 2)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 2)});
                  this.repair(player, "tools", "iron");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.GOLD_HOE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 2)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 2)});
                  this.repair(player, "tools", "gold");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.DIAMOND_HOE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 2)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 2)});
                  this.repair(player, "tools", "diamond");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.WOOD_SPADE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 1)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 1)});
                  this.repair(player, "tools", "wood");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.STONE_SPADE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 1)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.COBBLESTONE, 1)});
                  this.repair(player, "tools", "stone");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCobblestone", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.IRON_SPADE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 1)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 1)});
                  this.repair(player, "tools", "iron");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.GOLD_SPADE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 1)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 1)});
                  this.repair(player, "tools", "gold");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.DIAMOND_SPADE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 1)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 1)});
                  this.repair(player, "tools", "diamond");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.WOOD_PICKAXE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 3)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 3)});
                  this.repair(player, "tools", "wood");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.STONE_PICKAXE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 3)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.COBBLESTONE, 3)});
                  this.repair(player, "tools", "stone");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCobblestone", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.IRON_PICKAXE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 3)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 3)});
                  this.repair(player, "tools", "iron");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.GOLD_PICKAXE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 3)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 3)});
                  this.repair(player, "tools", "gold");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.DIAMOND_PICKAXE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 3)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 3)});
                  this.repair(player, "tools", "diamond");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.WOOD_SWORD)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 1)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 2)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 1)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 2)});
                  this.repair(player, "tools", "wood");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.STONE_SWORD)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 1)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 2)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 1)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.COBBLESTONE, 2)});
                  this.repair(player, "tools", "stone");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCobblestone", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.IRON_SWORD)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 1)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 2)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 1)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 2)});
                  this.repair(player, "tools", "iron");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.GOLD_SWORD)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 1)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 2)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 1)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 2)});
                  this.repair(player, "tools", "gold");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.DIAMOND_SWORD)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 1)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 2)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 1)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 2)});
                  this.repair(player, "tools", "diamond");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.SHEARS)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 2)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 2)});
               this.repair(player, "tools", "shears");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.BOW)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 3)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.STRING), 3)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 3)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STRING, 3)});
                  this.repair(player, "tools", "bow");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughString", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.STICK)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 1)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 1)});
               this.repair(player, "tools", "stick");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.FLINT_AND_STEEL)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 1)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.FLINT), 1)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.FLINT, 1)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 1)});
                  this.repair(player, "tools", "flintAndSteel");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughFlint", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.FISHING_ROD)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 3)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.STRING), 2)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 3)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STRING, 2)});
                  this.repair(player, "tools", "fishingRod");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughString", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.CARROT_STICK)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.CARROT_ITEM), 1)) {
               if(player.getInventory().containsAtLeast(new ItemStack(Material.FISHING_ROD), 1)) {
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.CARROT_ITEM, 1)});
                  player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.FISHING_ROD, 1)});
                  this.repair(player, "tools", "carrotStick");
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughFishingRod", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCarrots", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.LEATHER_HELMET)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.LEATHER), 5)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.LEATHER, 5)});
               this.repair(player, "armour", "leather");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughLeather", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.CHAINMAIL_HELMET)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 4)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 4)});
               this.repair(player, "armour", "chainmail");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.IRON_HELMET)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 5)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 5)});
               this.repair(player, "armour", "iron");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.GOLD_HELMET)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 5)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 5)});
               this.repair(player, "armour", "gold");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.DIAMOND_HELMET)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 5)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 5)});
               this.repair(player, "armour", "diamond");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.LEATHER_CHESTPLATE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.LEATHER), 8)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.LEATHER, 8)});
               this.repair(player, "armour", "leather");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughLeather", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.CHAINMAIL_CHESTPLATE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 7)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 7)});
               this.repair(player, "armour", "chainmail");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.IRON_CHESTPLATE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 8)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 8)});
               this.repair(player, "armour", "iron");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.GOLD_CHESTPLATE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 8)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 8)});
               this.repair(player, "armour", "gold");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.DIAMOND_CHESTPLATE)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 8)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 8)});
               this.repair(player, "armour", "diamond");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.LEATHER_LEGGINGS)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.LEATHER), 7)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.LEATHER, 7)});
               this.repair(player, "armour", "leather");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughLeather", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.CHAINMAIL_LEGGINGS)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 6)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 6)});
               this.repair(player, "armour", "chainmail");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.IRON_LEGGINGS)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 7)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 7)});
               this.repair(player, "armour", "iron");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.GOLD_LEGGINGS)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 7)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 7)});
               this.repair(player, "armour", "gold");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.DIAMOND_LEGGINGS)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 7)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 7)});
               this.repair(player, "armour", "diamond");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.LEATHER_BOOTS)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.LEATHER), 4)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.LEATHER, 4)});
               this.repair(player, "armour", "leather");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughLeather", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.CHAINMAIL_BOOTS)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 3)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 3)});
               this.repair(player, "armour", "chainmail");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.IRON_BOOTS)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 4)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 4)});
               this.repair(player, "armour", "iron");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.GOLD_BOOTS)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 4)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 4)});
               this.repair(player, "armour", "gold");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         } else if(type.equals(Material.DIAMOND_BOOTS)) {
            if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 4)) {
               player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 4)});
               this.repair(player, "armour", "diamond");
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
            }
         }
      } else {
         player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.DoesntNeedRepair", player, player, this.getItemInHandName(player.getItemInHand()), this.getItemInHandName(player.getItemInHand())));
      }

   }
}
