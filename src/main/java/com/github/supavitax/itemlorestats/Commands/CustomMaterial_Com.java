package com.github.supavitax.itemlorestats.Commands;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class CustomMaterial_Com {

    Util_GetResponse util_GetResponse = new Util_GetResponse();


    public void onCustomMaterialCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                if (!player.isOp() && !player.hasPermission("ils.admin")) {
                    player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", (Entity) null, (Entity) null, "", ""));
                } else if (player.getItemInHand() != null) {
                    if (player.getItemInHand().getType() != Material.AIR) {
                        if (args.length >= 3) {
                            String itemName = player.getItemInHand().getType().toString();
                            String rebuiltName = "";
                            if (args[0].equalsIgnoreCase("custom")) {
                                if (args[1].equalsIgnoreCase("tool")) {
                                    if (args[2].toString() != null) {
                                        if (itemName != null) {
                                            List armourType = ItemLoreStats.plugin.getConfig().getList("materials.tools");

                                            for (int toolsList = 0; toolsList < args.length; ++toolsList) {
                                                if (toolsList >= 3) {
                                                    rebuiltName = rebuiltName + " " + args[toolsList];
                                                } else {
                                                    rebuiltName = args[toolsList];
                                                }
                                            }

                                            armourType.add(itemName + ":" + rebuiltName.toUpperCase().replaceAll(" ", "_"));
                                            ItemLoreStats.plugin.getConfig().set("materials.tools", armourType);
                                            ItemLoreStats.plugin.saveConfig();
                                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.AddedToConfig", player, player, rebuiltName, rebuiltName));
                                        } else {
                                            player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.HoldCustomItem", player, player, rebuiltName, rebuiltName));
                                        }
                                    } else {
                                        player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomItemType", player, player, "", ""));
                                    }
                                } else if (args[1].equalsIgnoreCase("armour")) {
                                    if (args[2].toString() != null) {
                                        if (args.length > 3) {
                                            if (args[3].toString() != null) {
                                                if (itemName != null) {
                                                    String var9 = args[2].toString().toLowerCase();
                                                    List var10 = ItemLoreStats.plugin.getConfig().getList("materials.armour." + var9);

                                                    for (int i = 0; i < args.length; ++i) {
                                                        if (i >= 4) {
                                                            rebuiltName = rebuiltName + " " + args[i];
                                                        } else {
                                                            rebuiltName = args[i];
                                                        }
                                                    }

                                                    var10.add(itemName + ":" + rebuiltName.toUpperCase().replaceAll(" ", "_"));
                                                    ItemLoreStats.plugin.getConfig().set("materials.armour." + var9, var10);
                                                    ItemLoreStats.plugin.saveConfig();
                                                    player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.AddedToConfig", player, player, rebuiltName, rebuiltName));
                                                } else {
                                                    player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.HoldCustomItem", player, player, rebuiltName, rebuiltName));
                                                }
                                            } else {
                                                player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomItemType", player, player, "", ""));
                                            }
                                        } else {
                                            player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomItemType", player, player, "", ""));
                                        }
                                    } else {
                                        player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomArmourType", player, player, "", ""));
                                    }
                                } else {
                                    player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomEquipmentType", player, player, "", ""));
                                }
                            }
                        } else if (args.length == 2) {
                            if (args[1].equalsIgnoreCase("tool")) {
                                player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomItemType", player, player, "", ""));
                            } else if (args[1].equalsIgnoreCase("armour")) {
                                player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomArmourType", player, player, "", ""));
                            }
                        } else if (args.length == 1) {
                            player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomEquipmentType", player, player, "", ""));
                        }
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", (Entity) null, (Entity) null, "", ""));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", (Entity) null, (Entity) null, "", ""));
                }
            }
        }

    }
}
