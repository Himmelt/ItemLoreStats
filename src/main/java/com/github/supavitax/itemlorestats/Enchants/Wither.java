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

public class Wither {
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

    public void witherChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, boolean isTool) {
        if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".wit", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.wither.internalCooldown"))) {
            if (getAttacker instanceof Player) {
                ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".wit", System.currentTimeMillis());
            }

            double witherPercent = 0.0D;
            double modifier = 0.0D;
            if (getAttacker instanceof Player) {
                modifier = this.setBonuses.checkHashMapWither((Player) getAttacker);
            } else {
                modifier = 0.0D;
            }

            if (isTool) {
                witherPercent = this.util_Format.format(this.gearStats.getWitherGear(getAttacker) + this.gearStats.getWitherItemInHand(getAttacker) + modifier);
            } else {
                witherPercent = this.util_Format.format(this.gearStats.getWitherGear(getAttacker) + modifier);
            }

            if (witherPercent > 100.0D) {
                witherPercent = 100.0D;
            }

            if ((double) this.util_Random.random(100) <= witherPercent) {
                if (getAttacker instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.wither")) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.WitherSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                }

                if (getDefender instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.enemyWither")) {
                    if (getAttacker instanceof Player) {
                        ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyWitherSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                    } else if (getAttacker instanceof LivingEntity) {
                        if (getAttacker.getCustomName() != null) {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyWitherSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        } else {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyWitherSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        }
                    }
                }

                getDefender.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.wither.effectDuration") * 20, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.wither.effectAmplifier")));
            }
        }
    }
}
