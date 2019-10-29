package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.Durability.Durability;
import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.SetBonuses;
import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
import com.github.supavitax.itemlorestats.Util.*;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Harming {
    Durability durability = new Durability();
    GearStats gearStats = new GearStats();
    GetSlots getSlots = new GetSlots();
    InternalCooldown internalCooldown = new InternalCooldown();
    SetBonuses setBonuses = new SetBonuses();
    Util_Colours util_Colours = new Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Random util_Random = new Util_Random();

    public void harmingChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, boolean isTool) {
        if (this.gearStats.getHarmingGear(getAttacker) + this.gearStats.getHarmingItemInHand(getAttacker) > 0.0D && !this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".har", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.harming.internalCooldown"))) {
            if (getAttacker instanceof Player) {
                ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".har", System.currentTimeMillis());
            }

            double harmingPercent = 0.0D;
            double modifier = 0.0D;
            if (getAttacker instanceof Player) {
                modifier = this.setBonuses.checkHashMapHarming((Player) getAttacker);
            } else {
                modifier = 0.0D;
            }

            if (isTool) {
                harmingPercent = this.util_Format.format(this.gearStats.getHarmingGear(getAttacker) + this.gearStats.getHarmingItemInHand(getAttacker) + modifier);
            } else {
                harmingPercent = this.util_Format.format(this.gearStats.getHarmingGear(getAttacker) + modifier);
            }

            if (harmingPercent > 100.0D) {
                harmingPercent = 100.0D;
            }

            if ((double) this.util_Random.random(100) <= harmingPercent) {
                if (getAttacker instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.harm")) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.HarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                }

                if (getDefender instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.enemyHarm")) {
                    if (getAttacker instanceof Player) {
                        ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyHarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                    } else if (getAttacker instanceof LivingEntity) {
                        if (getAttacker.getCustomName() != null) {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyHarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        } else {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyHarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        }
                    }
                }

                if (!(getDefender instanceof org.bukkit.entity.Wither) && !(getDefender instanceof Zombie) && !(getDefender instanceof Skeleton) && !(getDefender instanceof PigZombie)) {
                    getDefender.addPotionEffect(new PotionEffect(PotionEffectType.HARM, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.harming.effectDuration") * 20, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.harming.effectAmplifier")));
                } else {
                    getDefender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.harming.effectDuration") * 20, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.harming.effectAmplifier")));
                }
            }
        }
    }
}
