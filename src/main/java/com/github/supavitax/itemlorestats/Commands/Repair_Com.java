package com.github.supavitax.itemlorestats.Commands;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Repair.Repair;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;
import java.util.List;

public class Repair_Com {
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Colours util_Colours = new Util_Colours();
    Repair repair = new Repair();

    public void onRepairCommand(CommandSender sender, String[] args) {
        if ("repair".equalsIgnoreCase(args[0])) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                    if (!player.isOp() && !player.hasPermission("ils.admin")) {
                        if (player.hasPermission("ils.repair")) {
                            if (player.getItemInHand() != null) {
                                if (player.getItemInHand().getType() != Material.AIR) {
                                    if (player.getItemInHand().getItemMeta().hasLore()) {
                                        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType()) || ItemLoreStats.plugin.isArmour(player.getItemInHand().getType())) {
                                            this.repair.payAndRepair(player, player.getItemInHand().getType());
                                        }
                                    } else {
                                        player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NoLoreError", null, null, "", ""));
                                    }
                                } else {
                                    player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                                }
                            } else {
                                player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                            }
                        } else {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        }
                    } else if (player.getItemInHand() != null) {
                        if (player.getItemInHand().getType() != Material.AIR) {
                            if (player.getItemInHand().getItemMeta().hasLore()) {
                                List splitItemLore = player.getItemInHand().getItemMeta().getLore();
                                Iterator var6 = splitItemLore.iterator();

                                while (var6.hasNext()) {
                                    String getItemStat = (String) var6.next();
                                    String durabilityAmountColour = "";
                                    String prefixColourOnly = "";
                                    String durabilityRebuilder = "";
                                    if (ChatColor.stripColor(getItemStat).startsWith(ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name"))) {
                                        int maxAmount = Integer.parseInt(this.util_Colours.extractAndReplaceTooltipColour(ChatColor.stripColor(getItemStat).split(": ")[1].split("/")[1]).replaceAll("&([0-9a-f])", ""));
                                        int index = splitItemLore.indexOf(getItemStat);
                                        if (this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(0, 2)).contains("&")) {
                                            if (getItemStat.length() > 4) {
                                                if (this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(2, 4)).contains("&")) {
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

                                        if (this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim().substring(0, 2)).contains("&")) {
                                            if (getItemStat.split("/")[1].trim().length() > 4) {
                                                if (this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim().substring(2, 4)).contains("&")) {
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

                                        durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name") + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maxAmount + this.util_Colours.replaceTooltipColour(prefixColourOnly) + "/" + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maxAmount;
                                        splitItemLore.set(index, durabilityRebuilder);
                                        ItemStack repairedItem = player.getItemInHand();
                                        ItemMeta repairedItemMeta = repairedItem.getItemMeta();
                                        repairedItemMeta.setLore(splitItemLore);
                                        repairedItem.setItemMeta(repairedItemMeta);
                                        player.setItemInHand(repairedItem);
                                        player.getItemInHand().setDurability((short) 0);
                                        if ("Currency".equalsIgnoreCase(ItemLoreStats.plugin.getConfig().getString("durabilityAddedOnEachRepair.repairCostType"))) {
                                            player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulCurrency", player, player, player.getItemInHand().getItemMeta().getDisplayName(), player.getItemInHand().getItemMeta().getDisplayName()));
                                        } else {
                                            player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, player.getItemInHand().getItemMeta().getDisplayName(), player.getItemInHand().getItemMeta().getDisplayName()));
                                        }
                                    }
                                }
                            } else {
                                player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NoLoreError", null, null, "", ""));
                            }
                        } else {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                        }
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                    }
                }
            } else {
                System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
            }
        }

    }
}
