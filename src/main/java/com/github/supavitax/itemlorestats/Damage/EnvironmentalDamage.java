package com.github.supavitax.itemlorestats.Damage;

import com.github.supavitax.itemlorestats.Durability.Durability;
import com.github.supavitax.itemlorestats.Enchants.Vanilla_FeatherFalling;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_EntityManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EnvironmentalDamage implements Listener {

    Durability durability = new Durability();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Vanilla_FeatherFalling vanilla_FeatherFalling = new Vanilla_FeatherFalling();
    Vanilla_Base_Armour vanilla_Base_Armour = new Vanilla_Base_Armour();


    @EventHandler
    public void onBlockExplosion(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.BLOCK_EXPLOSION) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.block_explosion.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.block_explosion.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getMaxHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.block_explosion.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.block_explosion.durabilityLost"), "block_explosion");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onCactus(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.CONTACT) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.cactus.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.cactus.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getMaxHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.cactus.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.cactus.durabilityLost"), "cactus");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onDrowning(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.DROWNING) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.drowning.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.drowning.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getMaxHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.drowning.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.drowning.durabilityLost"), "drowning");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onEntityExplosion(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.ENTITY_EXPLOSION) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.entity_explosion.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.entity_explosion.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getMaxHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.entity_explosion.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.entity_explosion.durabilityLost"), "entity_explosion");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.FALL) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fall.damage") == 0) {
                    return;
                }

                double fallHeight = (double) entity.getFallDistance();
                if (fallHeight <= 2.0D) {
                    event.setCancelled(true);
                    return;
                }

                fallHeight -= 2.0D;
                double percentageOfHealth = (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fall.damage") * entity.getMaxHealth() / 100.0D;
                double multiplyDamage = percentageOfHealth * fallHeight;
                if (this.vanilla_FeatherFalling.hasFeatherFalling(this.util_EntityManager.returnItemStackBoots(entity))) {
                    multiplyDamage = this.vanilla_FeatherFalling.calculateNewFallDamage(this.util_EntityManager.returnItemStackBoots(entity).getEnchantmentLevel(Enchantment.PROTECTION_FALL), multiplyDamage);
                }

                multiplyDamage /= Math.abs(this.vanilla_Base_Armour.getDamageReductionFromArmour(entity) - 1.0D);
                event.setDamage(multiplyDamage);
                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fall.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fall.durabilityLost"), "fall");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onFire(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.FIRE) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fire.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fire.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getMaxHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fire.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fire.durabilityLost"), "fire");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onFireTick(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.FIRE_TICK) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fire_tick.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fire_tick.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getMaxHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fire_tick.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.fire_tick.durabilityLost"), "fire_tick");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onLavaBurn(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.LAVA) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.lava.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.lava.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getMaxHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.lava.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.lava.durabilityLost"), "lava");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onLightningStrike(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.LIGHTNING) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.lightning.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.lightning.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getMaxHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.lightning.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.lightning.durabilityLost"), "lightning");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onMagic(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.MAGIC) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.magic.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.magic.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getMaxHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.magic.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.magic.durabilityLost"), "magic");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onPoison(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.POISON) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.poison.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.poison.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getMaxHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.poison.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.poison.durabilityLost"), "poison");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onStarvation(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.STARVATION) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.starvation.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.starvation.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.starvation.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.starvation.durabilityLost"), "starvation");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onSuffocation(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.SUFFOCATION) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.suffocation.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.suffocation.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.suffocation.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.suffocation.durabilityLost"), "suffocation");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onSuicide(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.SUICIDE) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                double newDamage = entity.getMaxHealth();
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onThorns(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.THORNS) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.thorns.damage") == 0) {
                    return;
                }

                double newDamage = entity.getMaxHealth() / 100.0D * (double) ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.thorns.damage");
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.thorns.durabilityLost") > 0) {
                        this.durability.durabilityCalcForArmour(entity, ItemLoreStats.plugin.getConfig().getInt("environmentalDamage.thorns.durabilityLost"), "thorns");
                    }

                    this.durability.syncArmourDurability((Player) entity);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }

    @EventHandler
    public void onVoid(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.VOID) && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(entity.getWorld().getName())) {
                double newDamage = entity.getMaxHealth();
                if (newDamage > entity.getHealth()) {
                    event.setDamage(entity.getHealth());
                } else {
                    event.setDamage(newDamage);
                }

                if (entity instanceof Player) {
                    final Player playerFinal = (Player) entity;
                    ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable() {
                        public void run() {
                            ItemLoreStats.plugin.updateBarAPI(playerFinal);
                        }
                    }, 2L);
                }
            }
        }

    }
}
