package com.github.supavitax.itemlorestats.Durability;

import com.github.supavitax.itemlorestats.Enchants.Vanilla_Unbreaking;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;
import java.util.List;

public class Durability {
    DurabilityEvents durabilityEvents = new DurabilityEvents();
    Util_Colours util_Colours = new Util_Colours();
    Vanilla_Unbreaking vanilla_Unbreaking = new Vanilla_Unbreaking();

    public void durabilityCalcForItemInHand(Player player, int amount, String durabilityLost) {
        if (!ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO") && player.getItemInHand() != null && ItemLoreStats.plugin.isTool(player.getItemInHand().getType()) && player.getItemInHand().getItemMeta().hasLore()) {
            String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
            String durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
            List getItemLore = player.getItemInHand().getItemMeta().getLore();
            Iterator setItemInHandMeta = getItemLore.iterator();

            while (setItemInHandMeta.hasNext()) {
                String setItemInHand = (String) setItemInHandMeta.next();
                if (ChatColor.stripColor(setItemInHand).startsWith(durabilityName)) {
                    String durabilityRebuilder = "";
                    String durabilityAmountColour = "";
                    String prefixColourOnly = "";
                    prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(setItemInHand));
                    int index = getItemLore.indexOf(setItemInHand);
                    String maximumValue = ChatColor.stripColor(setItemInHand).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                    int calculateNewValue = Integer.parseInt(ChatColor.stripColor(setItemInHand).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - amount;
                    if (calculateNewValue + 1 < 2) {
                        player.playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                        player.getInventory().remove(player.getItemInHand());
                        return;
                    }

                    double value = Double.valueOf(calculateNewValue) / Double.valueOf(maximumValue);
                    player.getItemInHand().setDurability((short) ((int) Math.abs(value * (double) player.getItemInHand().getType().getMaxDurability() - (double) player.getItemInHand().getType().getMaxDurability())));
                    if (setItemInHand.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(0, 2)).contains("&")) {
                        if (setItemInHand.length() > 4) {
                            if (this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.substring(2, 4)).contains("&")) {
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

                    if (setItemInHand.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&")) {
                        if (setItemInHand.split(durabilitySplitter)[1].trim().length() > 4) {
                            if (this.util_Colours.extractAndReplaceTooltipColour(setItemInHand.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
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

                    if (this.vanilla_Unbreaking.hasUnbreaking(player.getItemInHand())) {
                        calculateNewValue += this.vanilla_Unbreaking.calculateNewDurabilityLoss(player.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY), durabilityLost);
                    }

                    if (calculateNewValue < Integer.valueOf(Integer.parseInt(maximumValue) / 4)) {
                        if (calculateNewValue == Integer.valueOf(Integer.parseInt(maximumValue) / 4) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning")) {
                            player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                        }

                        durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.RED + calculateNewValue + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                    } else if (calculateNewValue < Integer.valueOf(Integer.parseInt(maximumValue) / 2)) {
                        if (calculateNewValue == Integer.valueOf(Integer.parseInt(maximumValue) / 2) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning")) {
                            player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                        }

                        durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.YELLOW + calculateNewValue + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                    } else if (calculateNewValue <= Integer.valueOf(Integer.parseInt(maximumValue) / 4 * 3)) {
                        if (calculateNewValue == Integer.valueOf(Integer.parseInt(maximumValue) / 4 * 3) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning")) {
                            player.sendMessage(player.getItemInHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                        }

                        durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.GREEN + calculateNewValue + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                    } else {
                        durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + calculateNewValue + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                    }

                    getItemLore.set(index, durabilityRebuilder);
                }
            }
            ItemStack setItemInHand1 = player.getItemInHand();
            ItemMeta setItemInHandMeta1 = setItemInHand1.getItemMeta();
            setItemInHandMeta1.setLore(getItemLore);
            setItemInHand1.setItemMeta(setItemInHandMeta1);
            player.setItemInHand(new ItemStack(Material.AIR));
            player.setItemInHand(setItemInHand1);
        }
    }

    public void durabilityCalcForArmour(Entity getDefender, int amount, String durabilityLost) {
        String durabilityName;
        String durabilitySplitter;
        List getItemLore;
        String getDurability;
        Iterator var8;
        String maximumValue;
        String currentValue;
        if (getDefender instanceof Player) {
            String index;
            int durabilityRebuilder;
            String getBootsItem;
            int getBootsItemMeta;
            double value;
            ItemStack getItem;
            ItemMeta getItemMeta;
            if (((Player) getDefender).getInventory().getHelmet() != null && ItemLoreStats.plugin.isHelmet(((Player) getDefender).getInventory().getHelmet().getType()) && ((Player) getDefender).getInventory().getHelmet().getItemMeta().hasLore()) {
                durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
                durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
                getItemLore = ((Player) getDefender).getInventory().getHelmet().getItemMeta().getLore();
                var8 = getItemLore.iterator();

                while (var8.hasNext()) {
                    getDurability = (String) var8.next();
                    if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                        index = "";
                        maximumValue = "";
                        currentValue = "";
                        durabilityRebuilder = getItemLore.indexOf(getDurability);
                        getBootsItem = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                        getBootsItemMeta = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - amount;
                        value = Double.valueOf(getBootsItemMeta) / Double.valueOf(getBootsItem);
                        ((Player) getDefender).getInventory().getHelmet().setDurability((short) ((int) Math.abs(value * (double) ((Player) getDefender).getInventory().getHelmet().getType().getMaxDurability() - (double) ((Player) getDefender).getInventory().getHelmet().getType().getMaxDurability())));
                        if (getBootsItemMeta + 1 <= 1) {
                            ((Player) getDefender).playEffect(getDefender.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                            ((Player) getDefender).getInventory().setHelmet(new ItemStack(Material.AIR));
                        } else {
                            if (getDurability.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&")) {
                                if (getDurability.length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)).contains("&")) {
                                        currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)));
                                    } else {
                                        currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                                    }
                                } else {
                                    currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                                }
                            } else {
                                currentValue = this.util_Colours.replaceTooltipColour("&5&o");
                            }

                            if (getDurability.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&")) {
                                if (getDurability.split(durabilitySplitter)[1].trim().length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                                        maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(2, 4);
                                    } else {
                                        maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                    }
                                } else {
                                    maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                }
                            } else {
                                maximumValue = currentValue;
                            }

                            if (this.vanilla_Unbreaking.hasUnbreaking(((Player) getDefender).getInventory().getHelmet())) {
                                getBootsItemMeta += this.vanilla_Unbreaking.calculateNewDurabilityLoss(((Player) getDefender).getInventory().getHelmet().getEnchantmentLevel(Enchantment.DURABILITY), durabilityLost);
                            }

                            if (getBootsItemMeta < Integer.valueOf(Integer.parseInt(getBootsItem) / 4)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 4) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getHelmet().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.RED + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else if (getBootsItemMeta < Integer.valueOf(Integer.parseInt(getBootsItem) / 2)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 2) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getHelmet().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.YELLOW + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else if (getBootsItemMeta <= Integer.valueOf(Integer.parseInt(getBootsItem) / 4 * 3)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 4 * 3) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getHelmet().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.GREEN + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else {
                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            }

                            getItemLore.set(durabilityRebuilder, index);
                            getItem = ((Player) getDefender).getInventory().getHelmet();
                            getItemMeta = getItem.getItemMeta();
                            getItemMeta.setLore(getItemLore);
                            getItem.setItemMeta(getItemMeta);
                            ((Player) getDefender).getInventory().remove(((Player) getDefender).getInventory().getHelmet());
                            ((Player) getDefender).getInventory().setHelmet(getItem);
                        }
                    }
                }
            }

            if (((Player) getDefender).getInventory().getChestplate() != null && ItemLoreStats.plugin.isChestplate(((Player) getDefender).getInventory().getChestplate().getType()) && ((Player) getDefender).getInventory().getChestplate().getItemMeta().hasLore()) {
                durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
                durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
                getItemLore = ((Player) getDefender).getInventory().getChestplate().getItemMeta().getLore();
                var8 = getItemLore.iterator();

                while (var8.hasNext()) {
                    getDurability = (String) var8.next();
                    if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                        index = "";
                        maximumValue = "";
                        currentValue = "";
                        durabilityRebuilder = getItemLore.indexOf(getDurability);
                        getBootsItem = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                        getBootsItemMeta = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - amount;
                        value = Double.valueOf(getBootsItemMeta) / Double.valueOf(getBootsItem);
                        ((Player) getDefender).getInventory().getChestplate().setDurability((short) ((int) Math.abs(value * (double) ((Player) getDefender).getInventory().getChestplate().getType().getMaxDurability() - (double) ((Player) getDefender).getInventory().getChestplate().getType().getMaxDurability())));
                        if (getBootsItemMeta + 1 <= 1) {
                            ((Player) getDefender).playEffect(getDefender.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                            ((Player) getDefender).getInventory().setChestplate(new ItemStack(Material.AIR));
                        } else {
                            if (getDurability.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&")) {
                                if (getDurability.length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)).contains("&")) {
                                        currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)));
                                    } else {
                                        currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                                    }
                                } else {
                                    currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                                }
                            } else {
                                currentValue = this.util_Colours.replaceTooltipColour("&5&o");
                            }

                            if (getDurability.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&")) {
                                if (getDurability.split(durabilitySplitter)[1].trim().length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                                        maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(2, 4);
                                    } else {
                                        maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                    }
                                } else {
                                    maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                }
                            } else {
                                maximumValue = currentValue;
                            }

                            if (this.vanilla_Unbreaking.hasUnbreaking(((Player) getDefender).getInventory().getChestplate())) {
                                getBootsItemMeta += this.vanilla_Unbreaking.calculateNewDurabilityLoss(((Player) getDefender).getInventory().getChestplate().getEnchantmentLevel(Enchantment.DURABILITY), durabilityLost);
                            }

                            if (getBootsItemMeta < Integer.valueOf(Integer.parseInt(getBootsItem) / 4)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 4) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getChestplate().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.RED + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else if (getBootsItemMeta < Integer.valueOf(Integer.parseInt(getBootsItem) / 2)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 2) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getChestplate().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.YELLOW + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else if (getBootsItemMeta <= Integer.valueOf(Integer.parseInt(getBootsItem) / 4 * 3)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 4 * 3) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getChestplate().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.GREEN + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else {
                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            }

                            getItemLore.set(durabilityRebuilder, index);
                            getItem = ((Player) getDefender).getInventory().getChestplate();
                            getItemMeta = getItem.getItemMeta();
                            getItemMeta.setLore(getItemLore);
                            getItem.setItemMeta(getItemMeta);
                            ((Player) getDefender).getInventory().remove(((Player) getDefender).getInventory().getChestplate());
                            ((Player) getDefender).getInventory().setChestplate(getItem);
                        }
                    }
                }
            }

            if (((Player) getDefender).getInventory().getLeggings() != null && ItemLoreStats.plugin.isLeggings(((Player) getDefender).getInventory().getLeggings().getType()) && ((Player) getDefender).getInventory().getLeggings().getItemMeta().hasLore()) {
                durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
                durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
                getItemLore = ((Player) getDefender).getInventory().getLeggings().getItemMeta().getLore();
                var8 = getItemLore.iterator();

                while (var8.hasNext()) {
                    getDurability = (String) var8.next();
                    if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                        index = "";
                        maximumValue = "";
                        currentValue = "";
                        durabilityRebuilder = getItemLore.indexOf(getDurability);
                        getBootsItem = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                        getBootsItemMeta = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - amount;
                        value = Double.valueOf(getBootsItemMeta) / Double.valueOf(getBootsItem);
                        ((Player) getDefender).getInventory().getLeggings().setDurability((short) ((int) Math.abs(value * (double) ((Player) getDefender).getInventory().getLeggings().getType().getMaxDurability() - (double) ((Player) getDefender).getInventory().getLeggings().getType().getMaxDurability())));
                        if (getBootsItemMeta + 1 <= 1) {
                            ((Player) getDefender).playEffect(getDefender.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                            ((Player) getDefender).getInventory().setLeggings(new ItemStack(Material.AIR));
                        } else {
                            if (getDurability.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&")) {
                                if (getDurability.length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)).contains("&")) {
                                        currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)));
                                    } else {
                                        currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                                    }
                                } else {
                                    currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                                }
                            } else {
                                currentValue = this.util_Colours.replaceTooltipColour("&5&o");
                            }

                            if (getDurability.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&")) {
                                if (getDurability.split(durabilitySplitter)[1].trim().length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                                        maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(2, 4);
                                    } else {
                                        maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                    }
                                } else {
                                    maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                }
                            } else {
                                maximumValue = currentValue;
                            }

                            if (this.vanilla_Unbreaking.hasUnbreaking(((Player) getDefender).getInventory().getLeggings())) {
                                getBootsItemMeta += this.vanilla_Unbreaking.calculateNewDurabilityLoss(((Player) getDefender).getInventory().getLeggings().getEnchantmentLevel(Enchantment.DURABILITY), durabilityLost);
                            }

                            if (getBootsItemMeta < Integer.valueOf(Integer.parseInt(getBootsItem) / 4)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 4) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getLeggings().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.RED + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else if (getBootsItemMeta < Integer.valueOf(Integer.parseInt(getBootsItem) / 2)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 2) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getLeggings().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.YELLOW + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else if (getBootsItemMeta <= Integer.valueOf(Integer.parseInt(getBootsItem) / 4 * 3)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 4 * 3) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getLeggings().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.GREEN + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else {
                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            }

                            getItemLore.set(durabilityRebuilder, index);
                            getItem = ((Player) getDefender).getInventory().getLeggings();
                            getItemMeta = getItem.getItemMeta();
                            getItemMeta.setLore(getItemLore);
                            getItem.setItemMeta(getItemMeta);
                            ((Player) getDefender).getInventory().remove(((Player) getDefender).getInventory().getLeggings());
                            ((Player) getDefender).getInventory().setLeggings(getItem);
                        }
                    }
                }
            }

            if (((Player) getDefender).getInventory().getBoots() != null && ItemLoreStats.plugin.isBoots(((Player) getDefender).getInventory().getBoots().getType()) && ((Player) getDefender).getInventory().getBoots().getItemMeta().hasLore()) {
                durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
                durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
                getItemLore = ((Player) getDefender).getInventory().getBoots().getItemMeta().getLore();
                var8 = getItemLore.iterator();

                while (var8.hasNext()) {
                    getDurability = (String) var8.next();
                    if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                        index = "";
                        maximumValue = "";
                        currentValue = "";
                        durabilityRebuilder = getItemLore.indexOf(getDurability);
                        getBootsItem = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                        getBootsItemMeta = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - amount;
                        value = Double.valueOf(getBootsItemMeta) / Double.valueOf(getBootsItem);
                        ((Player) getDefender).getInventory().getBoots().setDurability((short) ((int) Math.abs(value * (double) ((Player) getDefender).getInventory().getBoots().getType().getMaxDurability() - (double) ((Player) getDefender).getInventory().getBoots().getType().getMaxDurability())));
                        if (getBootsItemMeta + 1 <= 1) {
                            ((Player) getDefender).playEffect(getDefender.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                            ((Player) getDefender).getInventory().setBoots(new ItemStack(Material.AIR));
                        } else {
                            if (getDurability.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&")) {
                                if (getDurability.length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)).contains("&")) {
                                        currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)));
                                    } else {
                                        currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                                    }
                                } else {
                                    currentValue = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                                }
                            } else {
                                currentValue = this.util_Colours.replaceTooltipColour("&5&o");
                            }

                            if (getDurability.split(durabilitySplitter)[1].trim().length() > 1 && this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&")) {
                                if (getDurability.split(durabilitySplitter)[1].trim().length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                                        maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(2, 4);
                                    } else {
                                        maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                    }
                                } else {
                                    maximumValue = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                }
                            } else {
                                maximumValue = currentValue;
                            }

                            if (this.vanilla_Unbreaking.hasUnbreaking(((Player) getDefender).getInventory().getBoots())) {
                                getBootsItemMeta += this.vanilla_Unbreaking.calculateNewDurabilityLoss(((Player) getDefender).getInventory().getBoots().getEnchantmentLevel(Enchantment.DURABILITY), durabilityLost);
                            }

                            if (getBootsItemMeta < Integer.valueOf(Integer.parseInt(getBootsItem) / 4)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 4) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getBoots().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.RED + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else if (getBootsItemMeta < Integer.valueOf(Integer.parseInt(getBootsItem) / 2)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 2) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getBoots().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.YELLOW + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else if (getBootsItemMeta <= Integer.valueOf(Integer.parseInt(getBootsItem) / 4 * 3)) {
                                if (getBootsItemMeta == Integer.valueOf(Integer.parseInt(getBootsItem) / 4 * 3) && ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning")) {
                                    getDefender.sendMessage(((Player) getDefender).getInventory().getBoots().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }

                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + ChatColor.GREEN + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            } else {
                                index = this.util_Colours.replaceTooltipColour(currentValue) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItemMeta + this.util_Colours.replaceTooltipColour(currentValue) + durabilitySplitter + this.util_Colours.replaceTooltipColour(maximumValue) + getBootsItem;
                            }

                            getItemLore.set(durabilityRebuilder, index);
                            getItem = ((Player) getDefender).getInventory().getBoots();
                            getItemMeta = getItem.getItemMeta();
                            getItemMeta.setLore(getItemLore);
                            getItem.setItemMeta(getItemMeta);
                            ((Player) getDefender).getInventory().remove(((Player) getDefender).getInventory().getBoots());
                            ((Player) getDefender).getInventory().setBoots(getItem);
                        }
                    }
                }
            }
        } else if (getDefender instanceof LivingEntity) {
            int index1;
            String durabilityRebuilder1;
            ItemStack getBootsItem1;
            ItemMeta getBootsItemMeta1;
            if (((LivingEntity) getDefender).getEquipment().getHelmet() != null && ItemLoreStats.plugin.isHelmet(((LivingEntity) getDefender).getEquipment().getHelmet().getType())) {
                ((LivingEntity) getDefender).getEquipment().getHelmet().setDurability((short) 0);
                if (((LivingEntity) getDefender).getEquipment().getHelmet().getItemMeta().hasLore()) {
                    durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
                    durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
                    getItemLore = ((LivingEntity) getDefender).getEquipment().getHelmet().getItemMeta().getLore();
                    var8 = getItemLore.iterator();

                    while (var8.hasNext()) {
                        getDurability = (String) var8.next();
                        if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                            index1 = getItemLore.indexOf(getDurability);
                            maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                            currentValue = ChatColor.stripColor(getDurability).trim().replace("[", "").replace(":", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("]", "").trim();
                            if (Integer.parseInt(currentValue) > 1) {
                                durabilityRebuilder1 = durabilityName + ": " + (Integer.parseInt(currentValue) - 1) + durabilitySplitter + maximumValue;
                                getItemLore.set(index1, durabilityRebuilder1);
                                getBootsItem1 = ((LivingEntity) getDefender).getEquipment().getHelmet();
                                getBootsItemMeta1 = getBootsItem1.getItemMeta();
                                getBootsItemMeta1.setLore(getItemLore);
                                getBootsItem1.setItemMeta(getBootsItemMeta1);
                                ((LivingEntity) getDefender).getEquipment().setHelmet(new ItemStack(Material.AIR));
                                ((LivingEntity) getDefender).getEquipment().setHelmet(getBootsItem1);
                            } else {
                                ((LivingEntity) getDefender).getEquipment().setHelmet(new ItemStack(Material.AIR));
                            }
                        }
                    }
                }
            }

            if (((LivingEntity) getDefender).getEquipment().getChestplate() != null && ItemLoreStats.plugin.isChestplate(((LivingEntity) getDefender).getEquipment().getChestplate().getType())) {
                ((LivingEntity) getDefender).getEquipment().getChestplate().setDurability((short) 0);
                if (((LivingEntity) getDefender).getEquipment().getChestplate().getItemMeta().hasLore()) {
                    durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
                    durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
                    getItemLore = ((LivingEntity) getDefender).getEquipment().getChestplate().getItemMeta().getLore();
                    var8 = getItemLore.iterator();

                    while (var8.hasNext()) {
                        getDurability = (String) var8.next();
                        if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                            index1 = getItemLore.indexOf(getDurability);
                            maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                            currentValue = ChatColor.stripColor(getDurability).trim().replace("[", "").replace(":", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("]", "").trim();
                            if (Integer.parseInt(currentValue) > 1) {
                                durabilityRebuilder1 = durabilityName + ": " + (Integer.parseInt(currentValue) - 1) + durabilitySplitter + maximumValue;
                                getItemLore.set(index1, durabilityRebuilder1);
                                getBootsItem1 = ((LivingEntity) getDefender).getEquipment().getChestplate();
                                getBootsItemMeta1 = getBootsItem1.getItemMeta();
                                getBootsItemMeta1.setLore(getItemLore);
                                getBootsItem1.setItemMeta(getBootsItemMeta1);
                                ((LivingEntity) getDefender).getEquipment().setChestplate(new ItemStack(Material.AIR));
                                ((LivingEntity) getDefender).getEquipment().setChestplate(getBootsItem1);
                            } else {
                                ((LivingEntity) getDefender).getEquipment().setChestplate(new ItemStack(Material.AIR));
                            }
                        }
                    }
                }
            }

            if (((LivingEntity) getDefender).getEquipment().getLeggings() != null && ItemLoreStats.plugin.isLeggings(((LivingEntity) getDefender).getEquipment().getLeggings().getType())) {
                ((LivingEntity) getDefender).getEquipment().getLeggings().setDurability((short) 0);
                if (((LivingEntity) getDefender).getEquipment().getLeggings().getItemMeta().hasLore()) {
                    durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
                    durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
                    getItemLore = ((LivingEntity) getDefender).getEquipment().getLeggings().getItemMeta().getLore();
                    var8 = getItemLore.iterator();

                    while (var8.hasNext()) {
                        getDurability = (String) var8.next();
                        if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                            index1 = getItemLore.indexOf(getDurability);
                            maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                            currentValue = ChatColor.stripColor(getDurability).trim().replace("[", "").replace(":", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("]", "").trim();
                            if (Integer.parseInt(currentValue) > 1) {
                                durabilityRebuilder1 = durabilityName + ": " + (Integer.parseInt(currentValue) - 1) + durabilitySplitter + maximumValue;
                                getItemLore.set(index1, durabilityRebuilder1);
                                getBootsItem1 = ((LivingEntity) getDefender).getEquipment().getLeggings();
                                getBootsItemMeta1 = getBootsItem1.getItemMeta();
                                getBootsItemMeta1.setLore(getItemLore);
                                getBootsItem1.setItemMeta(getBootsItemMeta1);
                                ((LivingEntity) getDefender).getEquipment().setLeggings(new ItemStack(Material.AIR));
                                ((LivingEntity) getDefender).getEquipment().setLeggings(getBootsItem1);
                            } else {
                                ((LivingEntity) getDefender).getEquipment().setLeggings(new ItemStack(Material.AIR));
                            }
                        }
                    }
                }
            }

            if (((LivingEntity) getDefender).getEquipment().getBoots() != null && ItemLoreStats.plugin.isBoots(((LivingEntity) getDefender).getEquipment().getBoots().getType())) {
                ((LivingEntity) getDefender).getEquipment().getBoots().setDurability((short) 0);
                if (((LivingEntity) getDefender).getEquipment().getBoots().getItemMeta().hasLore()) {
                    durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
                    durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
                    getItemLore = ((LivingEntity) getDefender).getEquipment().getBoots().getItemMeta().getLore();
                    var8 = getItemLore.iterator();

                    while (var8.hasNext()) {
                        getDurability = (String) var8.next();
                        if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                            index1 = getItemLore.indexOf(getDurability);
                            maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                            currentValue = ChatColor.stripColor(getDurability).trim().replace("[", "").replace(":", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("]", "").trim();
                            if (Integer.parseInt(currentValue) > 1) {
                                durabilityRebuilder1 = durabilityName + ": " + (Integer.parseInt(currentValue) - 1) + durabilitySplitter + maximumValue;
                                getItemLore.set(index1, durabilityRebuilder1);
                                getBootsItem1 = ((LivingEntity) getDefender).getEquipment().getBoots();
                                getBootsItemMeta1 = getBootsItem1.getItemMeta();
                                getBootsItemMeta1.setLore(getItemLore);
                                getBootsItem1.setItemMeta(getBootsItemMeta1);
                                ((LivingEntity) getDefender).getEquipment().setBoots(new ItemStack(Material.AIR));
                                ((LivingEntity) getDefender).getEquipment().setBoots(getBootsItem1);
                            } else {
                                ((LivingEntity) getDefender).getEquipment().setBoots(new ItemStack(Material.AIR));
                            }
                        }
                    }
                }
            }
        }

    }

    public void syncArmourDurability(final Player player) {
        String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
        String durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
        List getItemLore;
        String getDurability;
        Iterator var6;
        String maximumValue;
        int calculateNewValue;
        double value;
        if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getItemMeta().hasLore()) {
            getItemLore = player.getInventory().getHelmet().getItemMeta().getLore();
            var6 = getItemLore.iterator();

            while (var6.hasNext()) {
                getDurability = (String) var6.next();
                if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                    maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                    calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
                    if (calculateNewValue > 1) {
                        value = Double.valueOf(calculateNewValue) / Double.valueOf(maximumValue);
                        player.getInventory().getHelmet().setDurability((short) ((int) Math.abs(value * (double) player.getInventory().getHelmet().getType().getMaxDurability() - (double) player.getInventory().getHelmet().getType().getMaxDurability())));
                        ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                            public void run() {
                                player.updateInventory();
                            }
                        }, 1L);
                    }
                }
            }
        }

        if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getItemMeta().hasLore()) {
            getItemLore = player.getInventory().getChestplate().getItemMeta().getLore();
            var6 = getItemLore.iterator();

            while (var6.hasNext()) {
                getDurability = (String) var6.next();
                if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                    maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                    calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
                    if (calculateNewValue > 1) {
                        value = Double.valueOf(calculateNewValue) / Double.valueOf(maximumValue);
                        player.getInventory().getChestplate().setDurability((short) ((int) Math.abs(value * (double) player.getInventory().getChestplate().getType().getMaxDurability() - (double) player.getInventory().getChestplate().getType().getMaxDurability())));
                        ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                            public void run() {
                                player.updateInventory();
                            }
                        }, 1L);
                    }
                }
            }
        }

        if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getItemMeta().hasLore()) {
            getItemLore = player.getInventory().getLeggings().getItemMeta().getLore();
            var6 = getItemLore.iterator();

            while (var6.hasNext()) {
                getDurability = (String) var6.next();
                if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                    maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                    calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
                    if (calculateNewValue > 1) {
                        value = Double.valueOf(calculateNewValue) / Double.valueOf(maximumValue);
                        player.getInventory().getLeggings().setDurability((short) ((int) Math.abs(value * (double) player.getInventory().getLeggings().getType().getMaxDurability() - (double) player.getInventory().getLeggings().getType().getMaxDurability())));
                        ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                            public void run() {
                                player.updateInventory();
                            }
                        }, 1L);
                    }
                }
            }
        }

        if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasLore()) {
            getItemLore = player.getInventory().getBoots().getItemMeta().getLore();
            var6 = getItemLore.iterator();

            while (var6.hasNext()) {
                getDurability = (String) var6.next();
                if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                    maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                    calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
                    if (calculateNewValue > 1) {
                        value = Double.valueOf(calculateNewValue) / Double.valueOf(maximumValue);
                        player.getInventory().getBoots().setDurability((short) ((int) Math.abs(value * (double) player.getInventory().getBoots().getType().getMaxDurability() - (double) player.getInventory().getBoots().getType().getMaxDurability())));
                        ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                            public void run() {
                                player.updateInventory();
                            }
                        }, 1L);
                    }
                }
            }
        }

    }
}
