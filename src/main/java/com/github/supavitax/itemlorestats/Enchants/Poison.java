package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.Durability.Durability;
import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.SetBonuses;
import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
import com.github.supavitax.itemlorestats.Util.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poison {
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

    public void poisonChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, boolean isTool) {
        if (this.gearStats.getPoisonGear(getAttacker) + this.gearStats.getPoisonItemInHand(getAttacker) > 0.0D && !this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".poi", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.poison.internalCooldown"))) {
            if (getAttacker instanceof Player) {
                ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".poi", System.currentTimeMillis());
            }

            double poisonPercent = 0.0D;
            double modifier = 0.0D;
            if (getAttacker instanceof Player) {
                modifier = this.setBonuses.checkHashMapPoison((Player) getAttacker);
            } else {
                modifier = 0.0D;
            }

            if (isTool) {
                poisonPercent = this.util_Format.format(this.gearStats.getPoisonGear(getAttacker) + this.gearStats.getPoisonItemInHand(getAttacker) + modifier);
            } else {
                poisonPercent = this.util_Format.format(this.gearStats.getPoisonGear(getAttacker) + modifier);
            }

            if (poisonPercent > 100.0D) {
                poisonPercent = 100.0D;
            }

            if ((double) this.util_Random.random(100) <= poisonPercent) {
                if (getAttacker instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.poison")) {
                    getAttacker.sendMessage(this.util_GetResponse.getResponse("DamageMessages.PoisonSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                }

                if (getDefender instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.enemyPoison")) {
                    if (getAttacker instanceof Player) {
                        getDefender.sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyPoisonSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                    } else if (getAttacker instanceof LivingEntity) {
                        if (getAttacker.getCustomName() != null) {
                            getDefender.sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyPoisonSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        } else {
                            getDefender.sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyPoisonSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        }
                    }
                }

                getDefender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.poison.effectDuration") * 20, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.poison.effectAmplifier")));
            }
        }

    }
}
