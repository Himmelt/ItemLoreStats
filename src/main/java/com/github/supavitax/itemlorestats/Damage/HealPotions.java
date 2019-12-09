package com.github.supavitax.itemlorestats.Damage;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Iterator;

public class HealPotions implements Listener {
    @EventHandler
    public void drinkHealPotion(PlayerItemConsumeEvent event) {
        if (event.getItem().getDurability() == 8261 || event.getItem().getDurability() == 8229) {
            Player player = event.getPlayer();
            double playerFinal;
            if (event.getItem().getDurability() == 8261) {
                playerFinal = player.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("potions.instantHealthI");
                if (player.getHealth() - 5.0D + playerFinal > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                } else {
                    player.setHealth(player.getHealth() - 5.0D + playerFinal);
                }
            } else if (event.getItem().getDurability() == 8229) {
                playerFinal = player.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("potions.instantHealthII");
                if (player.getHealth() - 5.0D + playerFinal > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                } else {
                    player.setHealth(player.getHealth() - 5.0D + playerFinal);
                }
            }

            final Player playerFinal1 = event.getPlayer();
            ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                @Override
                public void run() {
                    ItemLoreStats.plugin.updateBarAPI(playerFinal1);
                }
            }, 2L);
        }

    }

    @EventHandler
    public void useSplashHealPotion(PotionSplashEvent event) {
        if (ItemLoreStats.plugin.getBarAPI() != null) {

            for (PotionEffect e : event.getPotion().getEffects()) {
                if (e.getType().equals(PotionEffectType.HEAL)) {

                    for (LivingEntity entity : event.getAffectedEntities()) {
                        if (entity instanceof Player) {
                            double playerFinal;
                            if (event.getPotion().getItem().getDurability() == 16453) {
                                playerFinal = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("potions.splashHealthI");
                                if (entity.getHealth() - 5.0D + playerFinal > entity.getMaxHealth()) {
                                    entity.setHealth(entity.getMaxHealth());
                                } else {
                                    entity.setHealth(entity.getHealth() - 5.0D + playerFinal);
                                }
                            } else if (event.getPotion().getItem().getDurability() == 16421) {
                                playerFinal = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("potions.splashHealthII");
                                if (entity.getHealth() - 5.0D + playerFinal > entity.getMaxHealth()) {
                                    entity.setHealth(entity.getMaxHealth());
                                } else {
                                    entity.setHealth(entity.getHealth() - 5.0D + playerFinal);
                                }
                            }

                            final Player playerFinal1 = (Player) entity;
                            ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                                public void run() {
                                    ItemLoreStats.plugin.updateBarAPI(playerFinal1);
                                }
                            }, 2L);
                        }
                    }
                }
            }
        }
    }
}