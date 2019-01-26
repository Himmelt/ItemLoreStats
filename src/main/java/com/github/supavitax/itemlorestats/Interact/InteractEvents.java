package com.github.supavitax.itemlorestats.Interact;

import com.github.supavitax.itemlorestats.*;
import com.github.supavitax.itemlorestats.Spells.SpellsEvents;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvents implements Listener {

    public ItemLoreStats instance;
    Classes classes = new Classes();
    GearStats gearStats = new GearStats();
    Soulbound soulbound = new Soulbound();
    XpLevel xpLevel = new XpLevel();
    SpellsEvents spellsEvents = new SpellsEvents();


    @EventHandler
    public void mainInteractEvent(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (!ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                if (!player.getGameMode().equals(GameMode.CREATIVE) || !player.getItemInHand().getType().equals(Material.SKULL_ITEM)) {
                    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
                            this.spellsEvents.spellSelection(player);
                        } else if (ItemLoreStats.plugin.isArmour(player.getItemInHand().getType())) {
                            if (!this.xpLevel.checkXPLevel(player, player.getItemInHand())) {
                                event.setCancelled(true);
                                player.updateInventory();
                                return;
                            }

                            if (!this.soulbound.checkSoulbound(player, player.getItemInHand())) {
                                event.setCancelled(true);
                                player.updateInventory();
                                return;
                            }

                            if (!this.classes.checkClasses(player, player.getItemInHand())) {
                                event.setCancelled(true);
                                player.updateInventory();
                                return;
                            }

                            ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                                public void run() {
                                    ItemLoreStats.plugin.updateHealth(player);
                                    ItemLoreStats.plugin.updatePlayerSpeed(player);
                                    ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
                                    ItemLoreStats.plugin.updateMana(player);
                                }
                            }, 3L);
                        } else if (event.getItem() != null && ItemLoreStats.plugin.isPotion(event.getItem().getDurability())) {
                            if (!this.xpLevel.checkXPLevel(player, player.getItemInHand())) {
                                event.setCancelled(true);
                                player.updateInventory();
                                return;
                            }

                            if (!this.soulbound.checkSoulbound(player, player.getItemInHand())) {
                                event.setCancelled(true);
                                player.updateInventory();
                                return;
                            }

                            if (!this.classes.checkClasses(player, player.getItemInHand())) {
                                event.setCancelled(true);
                                player.updateInventory();
                                return;
                            }
                        }
                    }

                    if (event.getAction() == Action.LEFT_CLICK_BLOCK && ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
                        if (!this.xpLevel.checkXPLevel(player, player.getItemInHand())) {
                            event.setCancelled(true);
                            player.updateInventory();
                            return;
                        }

                        if (!this.soulbound.checkSoulbound(player, player.getItemInHand())) {
                            event.setCancelled(true);
                            player.updateInventory();
                            return;
                        }

                        if (!this.classes.checkClasses(player, player.getItemInHand())) {
                            event.setCancelled(true);
                            player.updateInventory();
                            return;
                        }
                    }

                }
            }
        }
    }
}
