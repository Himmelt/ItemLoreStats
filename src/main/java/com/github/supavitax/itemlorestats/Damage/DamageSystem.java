package com.github.supavitax.itemlorestats.Damage;

import com.github.supavitax.itemlorestats.*;
import com.github.supavitax.itemlorestats.Durability.Durability;
import com.github.supavitax.itemlorestats.Enchants.Wither;
import com.github.supavitax.itemlorestats.Enchants.*;
import com.github.supavitax.itemlorestats.Spells.SpellCreator;
import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
import com.github.supavitax.itemlorestats.Util.*;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Iterator;

public class DamageSystem implements Listener {
    public ItemLoreStats instance;
    Classes classes = new Classes();
    Durability durability = new Durability();
    GearStats gearStats = new GearStats();
    GetSlots getSlots = new GetSlots();
    InternalCooldown internalCooldown = new InternalCooldown();
    SetBonuses setBonuses = new SetBonuses();
    XpLevel xpLevel = new XpLevel();
    Soulbound soulbound = new Soulbound();
    SpellCreator spellCreator = new SpellCreator();
    Util_Colours util_Colours = new Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Material util_Material = new Util_Material();
    Util_Random util_Random = new Util_Random();
    Util_Citizens util_Citizens;
    Util_WorldGuard util_WorldGuard;
    Armour armour;
    Blind blind;
    Block block;
    CriticalStrike criticalStrike;
    Dodge dodge;
    Fire fire;
    Harming harming;
    Ice ice;
    LifeSteal lifeSteal;
    Poison poison;
    Reflect reflect;
    Wither wither;
    Vanilla_Sharpness vanilla_Sharpness;
    Vanilla_Power vanilla_Power;
    Vanilla_Base_Armour vanilla_Base_Armour;

    public DamageSystem(ItemLoreStats i) {
        this.util_Citizens = new Util_Citizens(ItemLoreStats.plugin);
        this.util_WorldGuard = new Util_WorldGuard(ItemLoreStats.plugin);
        this.armour = new Armour();
        this.blind = new Blind();
        this.block = new Block();
        this.criticalStrike = new CriticalStrike();
        this.dodge = new Dodge();
        this.fire = new Fire();
        this.harming = new Harming();
        this.ice = new Ice();
        this.lifeSteal = new LifeSteal();
        this.poison = new Poison();
        this.reflect = new Reflect();
        this.wither = new Wither();
        this.vanilla_Sharpness = new Vanilla_Sharpness();
        this.vanilla_Power = new Vanilla_Power();
        this.vanilla_Base_Armour = new Vanilla_Base_Armour();
        this.instance = i;
    }

    public void setMetadata(Entity entity, String key, Object value, Plugin plugin) {
        entity.setMetadata(key, new FixedMetadataValue(plugin, value));
    }

    @EventHandler(
            ignoreCancelled = true
    )
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getDamager().getWorld().getName())) {
            if (!(event.getEntity() instanceof LivingEntity)) {
                return;
            }

            if (ItemLoreStats.plugin.util_WorldGuard != null && event.getEntity() instanceof Player && this.util_WorldGuard.playerInInvincibleRegion((Player) event.getEntity())) {
                event.setCancelled(true);
                return;
            }

            Object getAttacker = null;
            boolean isTool = false;
            if (!(event.getDamager() instanceof Player) && !(event.getDamager() instanceof Projectile)) {
                if (!(event.getDamager() instanceof LivingEntity)) {
                    if (event.getCause().equals(DamageCause.LIGHTNING)) {
                        return;
                    }

                    event.setCancelled(true);
                    return;
                }

                getAttacker = event.getDamager();
            } else if (event.getDamager() instanceof Projectile) {
                Entity getDefender = null;
                Projectile eventDamage = (Projectile) event.getDamager();
                if (!(eventDamage.getShooter() instanceof Entity)) {
                    return;
                }

                getDefender = eventDamage.getShooter();
                if (eventDamage.hasMetadata("ILS_Snowball") || eventDamage.hasMetadata("ILS_SmallFireball") || eventDamage.hasMetadata("ILS_Fireball") || eventDamage.hasMetadata("ILS_Arrow") || eventDamage.hasMetadata("ILS_Egg") || eventDamage.hasMetadata("ILS_TnT")) {
                    double DirectHealAmount;
                    double AOEHealAmount;
                    double AOEHealRange;
                    Effect projectileHitEffect;
                    Iterator reflectVal;
                    Entity entity;
                    if (eventDamage.hasMetadata("Damage=")) {
                        DirectHealAmount = eventDamage.getMetadata("DDA=").get(0).asDouble();
                        AOEHealAmount = eventDamage.getMetadata("ADA=").get(0).asDouble();
                        AOEHealRange = eventDamage.getMetadata("ADR=").get(0).asDouble();
                        projectileHitEffect = this.spellCreator.getProjectileHitEffect(this.gearStats.getSpellName(this.util_EntityManager.returnItemStackInHand(getDefender)));
                        event.getEntity().getLocation().getWorld().playEffect(event.getEntity().getLocation(), projectileHitEffect, 3);
                        if (event.getEntity() instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
                            ((Player) event.getEntity()).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Damage", getDefender, event.getEntity(), String.valueOf((int) DirectHealAmount), String.valueOf((int) DirectHealAmount)));
                        }

                        if (event.getEntity() instanceof LivingEntity) {
                            ((LivingEntity) event.getEntity()).damage(DirectHealAmount);
                        }

                        if (AOEHealRange > 0.0D) {
                            reflectVal = event.getEntity().getNearbyEntities(AOEHealRange, 256.0D, AOEHealRange).iterator();

                            while (reflectVal.hasNext()) {
                                entity = (Entity) reflectVal.next();
                                if (entity.equals(event.getDamager())) {
                                    event.getEntity().getLocation().getWorld().playEffect(entity.getLocation(), projectileHitEffect, 3);
                                    if (entity instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
                                        ((Player) entity).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Damage", getDefender, entity, String.valueOf((int) AOEHealAmount), String.valueOf((int) AOEHealAmount)));
                                    }

                                    if (entity instanceof LivingEntity) {
                                        ((LivingEntity) entity).damage(AOEHealAmount);
                                    }
                                }
                            }
                        }
                    } else {
                        event.setCancelled(true);
                    }

                    if (eventDamage.hasMetadata("Heal=")) {
                        DirectHealAmount = eventDamage.getMetadata("DHA=").get(0).asDouble();
                        AOEHealAmount = eventDamage.getMetadata("AHA=").get(0).asDouble();
                        AOEHealRange = eventDamage.getMetadata("AHR=").get(0).asDouble();
                        projectileHitEffect = this.spellCreator.getProjectileHitEffect(this.gearStats.getSpellName(this.util_EntityManager.returnItemStackInHand(getDefender)));
                        event.getEntity().getLocation().getWorld().playEffect(event.getEntity().getLocation(), projectileHitEffect, 3);
                        if (event.getEntity() instanceof Player) {
                            ((Player) event.getEntity()).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Heal", getDefender, event.getEntity(), String.valueOf((int) DirectHealAmount), String.valueOf((int) DirectHealAmount)));
                        }

                        if (this.util_EntityManager.returnEntityCurrentHealth(event.getEntity()) + DirectHealAmount > this.util_EntityManager.returnEntityMaxHealth(event.getEntity())) {
                            this.util_EntityManager.setEntityCurrentHealth(event.getEntity(), this.util_EntityManager.returnEntityMaxHealth(event.getEntity()));
                        } else {
                            this.util_EntityManager.setEntityCurrentHealth(event.getEntity(), this.util_EntityManager.returnEntityCurrentHealth(event.getEntity()) + DirectHealAmount);
                        }

                        if (AOEHealRange > 0.0D) {
                            reflectVal = event.getEntity().getNearbyEntities(AOEHealRange, 256.0D, AOEHealRange).iterator();

                            while (reflectVal.hasNext()) {
                                entity = (Entity) reflectVal.next();
                                event.getEntity().getLocation().getWorld().playEffect(entity.getLocation(), projectileHitEffect, 3);
                                if (entity instanceof Player) {
                                    ((Player) entity).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Heal", getDefender, entity, String.valueOf((int) AOEHealAmount), String.valueOf((int) AOEHealAmount)));
                                }

                                if (this.util_EntityManager.returnEntityCurrentHealth(entity) + AOEHealAmount > this.util_EntityManager.returnEntityMaxHealth(entity)) {
                                    this.util_EntityManager.setEntityCurrentHealth(entity, this.util_EntityManager.returnEntityMaxHealth(entity));
                                } else {
                                    this.util_EntityManager.setEntityCurrentHealth(entity, this.util_EntityManager.returnEntityCurrentHealth(entity) + AOEHealAmount);
                                }
                            }
                        }
                    }

                    return;
                }

                if (!(getDefender instanceof Player)) {
                    if (event.getEntity() instanceof Player && event.getEntity() instanceof LivingEntity) {
                        getAttacker = getDefender;
                    }
                } else {
                    getAttacker = getDefender;
                }
            } else if (event.getDamager() instanceof Player) {
                if (event.getEntity() instanceof Player && ItemLoreStats.plugin.getWorldGuard() != null && (ItemLoreStats.plugin.util_WorldGuard.playerInPVPRegion((Player) event.getEntity()) || ItemLoreStats.plugin.util_WorldGuard.playerInInvincibleRegion((Player) event.getEntity()))) {
                    return;
                }

                if (((Player) event.getDamager()).getItemInHand().getType().equals(Material.BOW)) {
                    if (event.getEntity() instanceof Player) {
                        event.setDamage(1.0D);
                        this.damageDealtMessage(event.getDamager(), event.getEntity(), event.getDamage());
                        return;
                    }

                    event.setDamage(1.0D);
                    this.damageDealtMessage(event.getDamager(), event.getEntity(), event.getDamage());
                    return;
                }

                getAttacker = event.getDamager();
            }

            if (!(event.getEntity() instanceof LivingEntity)) {
                event.setCancelled(true);
                return;
            }

            LivingEntity getDefender1;
            if (event.getDamager() instanceof LivingEntity) {
                getDefender1 = (LivingEntity) event.getEntity();
                if (getDefender1.getNoDamageTicks() > 10) {
                    event.setCancelled(true);
                }
            }

            getDefender1 = (LivingEntity) event.getEntity();
            if (getAttacker instanceof Player && getDefender1 instanceof Player) {
                if (ItemLoreStats.plugin.getWorldGuard() != null && (ItemLoreStats.plugin.util_WorldGuard.playerInPVPRegion((Player) getDefender1) || ItemLoreStats.plugin.util_WorldGuard.playerInInvincibleRegion((Player) getDefender1))) {
                    return;
                }

                if (((Player) getAttacker).getName().equals(((Player) getDefender1).getName())) {
                    event.setCancelled(true);
                    return;
                }
            }

            if (getAttacker instanceof Player) {
                if (!this.xpLevel.checkXPLevel((Player) getAttacker, ((Player) getAttacker).getItemInHand())) {
                    event.setDamage(1.0D);
                    this.damageDealtMessage((Entity) getAttacker, getDefender1, 1.0D);
                    return;
                }

                if (!this.soulbound.checkSoulbound((Player) getAttacker, ((Player) getAttacker).getItemInHand())) {
                    event.setDamage(1.0D);
                    this.damageDealtMessage((Entity) getAttacker, getDefender1, 1.0D);
                    return;
                }

                if (!this.classes.checkClasses((Player) getAttacker, ((Player) getAttacker).getItemInHand())) {
                    event.setDamage(1.0D);
                    this.damageDealtMessage((Entity) getAttacker, getDefender1, 1.0D);
                    return;
                }
            }

            if (ItemLoreStats.plugin.isTool(this.util_EntityManager.returnItemStackInHand((Entity) getAttacker).getType())) {
                isTool = true;
            }

            if (getDefender1.hasMetadata("NPC") && !this.util_Citizens.checkVulnerability(getDefender1)) {
                event.setCancelled(true);
                return;
            }

            if (getAttacker instanceof Player && !((Entity) getAttacker).hasMetadata("NPC")) {
                this.checkWeaponSpeed((Player) getAttacker, isTool);
            }

            if (this.dodge.dodgeChanceOnHit(getDefender1, isTool)) {
                if (getAttacker instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.enemyDodgedAttack")) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyDodgeSuccess", (Entity) getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                }

                if (getDefender1 instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.dodgeAttack")) {
                    ((Player) getDefender1).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DodgeSuccess", (Entity) getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                }

                event.setCancelled(true);
                return;
            }

            if (this.block.blockChanceOnHit(getDefender1, isTool)) {
                if (getAttacker instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.enemyBlockedAttack")) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyBlockSuccess", (Entity) getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                }

                if (getDefender1 instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.blockAttack")) {
                    ((Player) getDefender1).sendMessage(this.util_GetResponse.getResponse("DamageMessages.BlockSuccess", (Entity) getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                    getDefender1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 1));
                }

                event.setCancelled(true);
                return;
            }

            double eventDamage1 = event.getDamage();
            double getDefenderArmour;
            double getAttackerDamage;
            if (ItemLoreStats.plugin.getConfig().getString("npcModifier." + event.getDamager().getWorld().getName()) != null && !(getAttacker instanceof Player)) {
                getDefenderArmour = this.util_EntityManager.returnEntityMaxHealth((Entity) getAttacker) / ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + event.getDamager().getWorld().getName() + ".healthMultiplier");
                getAttackerDamage = (double) Math.round(this.attackerDamage((LivingEntity) getAttacker, getDefender1, getDefender1.getType(), event.getDamage(), this.util_Material.materialToDamage(this.util_EntityManager.returnItemStackInHand((Entity) getAttacker).getType()), 0.0D, false, isTool) + getDefenderArmour * ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + event.getDamager().getWorld().getName() + ".damageMultiplier"));
                eventDamage1 = getAttackerDamage;
            }

            getDefenderArmour = this.armour.armourChanceOnHit(getDefender1);
            getAttackerDamage = 0.0D;
            if (this.util_EntityManager.returnItemStackInHand((Entity) getAttacker).getType() == Material.BOW) {
                getAttackerDamage = this.attackerDamage((LivingEntity) getAttacker, getDefender1, getDefender1.getType(), eventDamage1, eventDamage1, getDefenderArmour, true, isTool);
            } else {
                getAttackerDamage = this.attackerDamage((LivingEntity) getAttacker, getDefender1, getDefender1.getType(), eventDamage1, this.util_Material.materialToDamage(this.util_EntityManager.returnItemStackInHand((Entity) getAttacker).getType()), getDefenderArmour, true, isTool);
            }

            double reducedDamage = getAttackerDamage / 100.0D * getDefenderArmour;
            if (getDefender1 instanceof Player && !ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
                this.durability.durabilityCalcForArmour(getDefender1, 1, "damage");
                if (((Player) getDefender1).isBlocking()) {
                    this.durability.durabilityCalcForItemInHand((Player) getDefender1, 1, "damage");
                }
            }

            if (getAttacker instanceof Player && !ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
                this.durability.durabilityCalcForItemInHand((Player) getAttacker, 1, "damage");
            }

            double reflectVal1 = this.reflect.reflectChanceOnHit(getDefender1, isTool);
            if (reflectVal1 > 0.0D && (double) this.util_Random.random(100) <= reflectVal1) {
                double damage;
                if (getAttacker instanceof Player) {
                    damage = getAttackerDamage - reducedDamage;
                    ((Player) getAttacker).damage(damage);
                    if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.enemyReflectedAttack")) {
                        if (getDefender1 instanceof Player) {
                            ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyReflectSuccess", (Entity) getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                        } else {
                            ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyReflectSuccess", (Entity) getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                        }
                    }

                    if (getDefender1 instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.reflectAttack")) {
                        ((Player) getDefender1).sendMessage(this.util_GetResponse.getResponse("DamageMessages.ReflectSuccess", (Entity) getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                    }

                    event.setCancelled(true);
                    return;
                }

                if (getAttacker instanceof LivingEntity) {
                    damage = getAttackerDamage - reducedDamage;
                    ((LivingEntity) getAttacker).damage(damage);
                    if (getDefender1 instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.reflectAttack")) {
                        ((Player) getDefender1).sendMessage(this.util_GetResponse.getResponse("DamageMessages.ReflectSuccess", (Entity) getAttacker, getDefender1, String.valueOf(0), String.valueOf(0)));
                    }

                    event.setCancelled(true);
                    return;
                }
            }

            this.lifeSteal.lifeStealChanceOnHit(getDefender1, (LivingEntity) getAttacker, getAttackerDamage - reducedDamage, isTool);
            this.fire.fireChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            this.ice.iceChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            this.poison.poisonChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            this.wither.witherChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            this.harming.harmingChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            this.blind.blindChanceOnHit(getDefender1, (LivingEntity) getAttacker, isTool);
            getAttackerDamage -= reducedDamage;
            event.setDamage(getAttackerDamage);
            if (getDefender1 instanceof Player) {
                ItemLoreStats.plugin.updateBarAPI((Player) getDefender1);
            }
        }

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getEntity().getWorld().getName()) && event.getEntity().getKiller() instanceof Player && !(event.getEntity() instanceof Player) && ItemLoreStats.plugin.getConfig().getString("npcModifier." + event.getEntity().getWorld().getName()) != null) {
            double distance = this.util_EntityManager.returnEntityMaxHealth(event.getEntity()) / ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + event.getEntity().getWorld().getName() + ".healthMultiplier");
            double newExp = (double) Math.round((double) event.getDroppedExp() + distance * ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + event.getEntity().getWorld().getName() + ".expMultiplier"));
            event.setDroppedExp((int) newExp);
        }

    }

    public void damageDealtMessage(Entity getAttacker, Entity getDefender, double damageDealt) {
        if (getAttacker instanceof Player) {
            if (ItemLoreStats.plugin.combatLogVisible.get(((Player) getAttacker).getName()) == null || (Boolean) ItemLoreStats.plugin.combatLogVisible.get(((Player) getAttacker).getName())) {
                if (getDefender instanceof Player) {
                    if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
                        ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageTaken", getAttacker, getDefender, ((Player) getAttacker).getName(), String.valueOf(Math.round(damageDealt))));
                    }

                    if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
                        ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDone", getAttacker, getDefender, ((Player) getDefender).getName(), String.valueOf(Math.round(damageDealt))));
                    }
                } else if (getDefender instanceof LivingEntity) {
                    if (((LivingEntity) getDefender).getCustomName() != null) {
                        if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
                            ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDone", getAttacker, getDefender, ((LivingEntity) getDefender).getCustomName(), String.valueOf(Math.round(damageDealt))));
                        }
                    } else if (!"a".equalsIgnoreCase(getDefender.getType().toString().substring(0, 1)) && !"e".equalsIgnoreCase(getDefender.getType().toString().substring(0, 1)) && !"i".equalsIgnoreCase(getDefender.getType().toString().substring(0, 1)) && !"o".equalsIgnoreCase(getDefender.getType().toString().substring(0, 1)) && !"u".equalsIgnoreCase(getDefender.getType().toString().substring(0, 1))) {
                        if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
                            ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDoneWithoutVowel", getAttacker, getDefender, getDefender.getType().toString().substring(0, 1) + getDefender.getType().toString().substring(1).toLowerCase().replace("_", " "), String.valueOf(Math.round(damageDealt))));
                        }
                    } else if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
                        ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDoneWithoutVowel", getAttacker, getDefender, getAttacker.getType().toString().substring(0, 1) + getDefender.getType().toString().substring(1).toLowerCase().replace("_", " "), String.valueOf(Math.round(damageDealt))));
                    }
                }
            }
        } else if (getDefender instanceof Player && (ItemLoreStats.plugin.combatLogVisible.get(((Player) getDefender).getName()) == null || (Boolean) ItemLoreStats.plugin.combatLogVisible.get(((Player) getDefender).getName())) && getAttacker instanceof LivingEntity) {
            if (((LivingEntity) getAttacker).getCustomName() != null) {
                if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
                    ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageTaken", getAttacker, getDefender, ((LivingEntity) getAttacker).getCustomName(), String.valueOf(Math.round(damageDealt))));
                }
            } else if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
                ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageTaken", getAttacker, getDefender, getAttacker.getType().toString().substring(0, 1) + getAttacker.getType().toString().substring(1).toLowerCase().replace("_", " "), String.valueOf(Math.round(damageDealt))));
            }
        }

    }

    public double attackerDamage(LivingEntity getAttacker, LivingEntity getDefender, EntityType entityType, double eventDamage, double vanillaDamage, double defenderArmour, boolean sendMessage, boolean isTool) {
        double valueMin = 0.0D;
        double valueMax = 0.0D;
        double valueRand = 0.0D;
        double modifier = getAttacker instanceof Player ? this.setBonuses.checkHashMapDamage((Player) getAttacker) : 0.0D;
        double damage = 0.0D;
        if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
            if (isTool) {
                if (this.getSlots.returnItemInHand(getAttacker).getItemMeta().hasLore()) {
                    valueMin = eventDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(getAttacker).split("-")[0]) + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[0]);
                    valueMax = eventDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(getAttacker).split("-")[1]) + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[1]);
                } else {
                    valueMin = eventDamage + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[0]);
                    valueMax = eventDamage + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[1]);
                }
            } else {
                valueMin = eventDamage + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[0]);
                valueMax = eventDamage + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[1]);
            }
        } else if (isTool) {
            if (this.getSlots.returnItemInHand(getAttacker).getItemMeta().hasLore()) {
                valueMin = eventDamage - vanillaDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(getAttacker).split("-")[0]) + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[0]);
                valueMax = eventDamage - vanillaDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(getAttacker).split("-")[1]) + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[1]);
            } else {
                valueMin = eventDamage + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[0]);
                valueMax = eventDamage + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[1]);
            }
        } else {
            valueMin = eventDamage + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[0]);
            valueMax = eventDamage + Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[1]);
        }

        valueMin += modifier;
        valueMax += modifier;
        if (valueMin > 0.0D && valueMax > 0.0D) {
            damage = Double.parseDouble(this.util_Random.formattedRandomRange(valueMin, valueMax));
            if (this.vanilla_Sharpness.hasSharpness(this.util_EntityManager.returnItemStackInHand(getAttacker))) {
                damage = this.vanilla_Sharpness.calculateNewDamage(damage, this.util_EntityManager.returnItemStackInHand(getAttacker).getEnchantmentLevel(Enchantment.DAMAGE_ALL));
            } else if (this.vanilla_Power.hasPower(this.util_EntityManager.returnItemStackInHand(getAttacker))) {
                damage = this.vanilla_Power.calculateNewDamage(damage, this.util_EntityManager.returnItemStackInHand(getAttacker).getEnchantmentLevel(Enchantment.ARROW_DAMAGE));
            }

            if (this.criticalStrike.criticalStrikeChanceOnHit(getAttacker, getDefender) > 1) {
                if (isTool) {
                    valueRand = damage + damage * (ItemLoreStats.plugin.getConfig().getDouble("baseCritDamage") + this.gearStats.getCritDamageGear(getAttacker) + this.gearStats.getCritDamageItemInHand(getAttacker)) / 100.0D;
                } else {
                    valueRand = damage + damage * (ItemLoreStats.plugin.getConfig().getDouble("baseCritDamage") + this.gearStats.getCritDamageGear(getAttacker)) / 100.0D;
                }
            } else {
                valueRand = damage;
            }
        } else if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
            valueRand = eventDamage + modifier;
        } else {
            valueRand = eventDamage - vanillaDamage + modifier;
        }

        double modifiedDamage;
        int valueRandInt;
        int dam;
        int modifiedDamage1;
        if (getDefender instanceof Player) {
            if (this.gearStats.getPvPDamageModifierItemInHand(getAttacker) != 0.0D) {
                if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeArmour")) {
                    valueRand += this.gearStats.getPvPDamageModifierItemInHand(getAttacker);
                    modifiedDamage1 = (int) Math.round(valueRand);
                    if (sendMessage) {
                        if (modifiedDamage1 < 0) {
                            this.damageDealtMessage(getAttacker, getDefender, 0.0D);
                        } else {
                            this.damageDealtMessage(getAttacker, getDefender, modifiedDamage1);
                        }
                    }

                    return this.util_Format.format(modifiedDamage1);
                }

                valueRand += this.gearStats.getPvPDamageModifierItemInHand(getAttacker);
                modifiedDamage = 0.0D;
                valueRandInt = (int) Math.round(valueRand);
                modifiedDamage = (double) valueRandInt / Math.abs(this.vanilla_Base_Armour.getDamageReductionFromArmour(getDefender) - 1.0D);
                if (sendMessage) {
                    dam = (int) Math.round(valueRand - valueRand / 100.0D * defenderArmour);
                    if (dam < 0) {
                        this.damageDealtMessage(getAttacker, getDefender, 0.0D);
                    } else {
                        this.damageDealtMessage(getAttacker, getDefender, dam);
                    }
                }

                return this.util_Format.format(modifiedDamage);
            }
        } else if (getDefender instanceof LivingEntity && this.gearStats.getPvEDamageModifierItemInHand(getAttacker) != 0.0D) {
            if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeArmour")) {
                valueRand += this.gearStats.getPvEDamageModifierItemInHand(getAttacker);
                modifiedDamage1 = (int) Math.round(valueRand);
                if (sendMessage) {
                    if (modifiedDamage1 < 0) {
                        this.damageDealtMessage(getAttacker, getDefender, 0.0D);
                    } else {
                        this.damageDealtMessage(getAttacker, getDefender, modifiedDamage1);
                    }
                }

                return this.util_Format.format(modifiedDamage1);
            }

            valueRand += this.gearStats.getPvEDamageModifierItemInHand(getAttacker);
            modifiedDamage = 0.0D;
            valueRandInt = (int) Math.round(valueRand);
            modifiedDamage = (double) valueRandInt / Math.abs(this.vanilla_Base_Armour.getDamageReductionFromArmour(getDefender) - 1.0D);
            if (sendMessage) {
                dam = (int) Math.round(valueRand - valueRand / 100.0D * defenderArmour);
                if (dam < 0) {
                    this.damageDealtMessage(getAttacker, getDefender, 0.0D);
                } else {
                    this.damageDealtMessage(getAttacker, getDefender, dam);
                }
            }

            return this.util_Format.format(modifiedDamage);
        }

        if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeArmour")) {
            modifiedDamage = 0.0D;
            valueRandInt = (int) Math.round(valueRand);
            modifiedDamage = (double) valueRandInt * Math.abs(this.vanilla_Base_Armour.getDamageReductionFromArmour(getDefender) - 1.0D);
            if (sendMessage) {
                dam = (int) Math.round(modifiedDamage * Math.abs(this.vanilla_Base_Armour.getDamageReductionFromArmour(getDefender) - 1.0D));
                if (dam < 0) {
                    this.damageDealtMessage(getAttacker, getDefender, 0.0D);
                } else {
                    this.damageDealtMessage(getAttacker, getDefender, dam);
                }
            }

            return this.util_Format.format(modifiedDamage);
        } else {
            modifiedDamage = 0.0D;
            valueRandInt = (int) Math.round(valueRand);
            modifiedDamage = (double) valueRandInt / Math.abs(this.vanilla_Base_Armour.getDamageReductionFromArmour(getDefender) - 1.0D);
            if (sendMessage) {
                dam = (int) Math.round(valueRand - valueRand / 100.0D * defenderArmour);
                if (dam < 0) {
                    this.damageDealtMessage(getAttacker, getDefender, 0.0D);
                } else {
                    this.damageDealtMessage(getAttacker, getDefender, dam);
                }
            }

            return this.util_Format.format(modifiedDamage);
        }
    }

    public void checkWeaponSpeed(Player player, boolean isTool) {
        String weaponSpeed = "normal";
        ItemStack checkItemHeld = player.getItemInHand();
        if (checkItemHeld != null && checkItemHeld.getType() != Material.AIR && checkItemHeld.getItemMeta() != null && checkItemHeld.getItemMeta().getLore() != null && isTool) {
            weaponSpeed = this.gearStats.getSwingSpeedItemInHand(player).toLowerCase();
        }

        if ("very fast".equalsIgnoreCase(weaponSpeed)) {
            if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
                player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            }

            ItemLoreStats.plugin.removeWeaponSpeedEffects(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 1));
        } else if ("fast".equalsIgnoreCase(weaponSpeed)) {
            if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
                player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            }

            ItemLoreStats.plugin.removeWeaponSpeedEffects(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 0));
        } else if ("slow".equalsIgnoreCase(weaponSpeed)) {
            if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            }

            ItemLoreStats.plugin.removeWeaponSpeedEffects(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, 0));
        } else if ("very slow".equalsIgnoreCase(weaponSpeed)) {
            if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            }

            ItemLoreStats.plugin.removeWeaponSpeedEffects(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, 1));
        } else if ("normal".equalsIgnoreCase(weaponSpeed)) {
            ItemLoreStats.plugin.removeWeaponSpeedEffects(player);
            ItemLoreStats.plugin.updateMana(player);
        }
    }
}
