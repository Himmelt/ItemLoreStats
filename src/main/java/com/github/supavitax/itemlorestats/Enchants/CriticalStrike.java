package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.Durability.Durability;
import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.SetBonuses;
import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
import com.github.supavitax.itemlorestats.Util.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class CriticalStrike {
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

    public int criticalStrikeChanceOnHit(LivingEntity getAttacker, LivingEntity getDefender) {
        if (this.gearStats.getCritChanceGear(getAttacker) + this.gearStats.getCritChanceItemInHand(getAttacker) <= 0.0D) {
            return 0;
        } else if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".cri", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.critChance.internalCooldown"))) {
            if (getAttacker instanceof Player) {
                ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".cri", System.currentTimeMillis());
            }

            double critPercent = 0.0D;
            double modifier = 0.0D;
            if (getAttacker instanceof Player) {
                modifier = this.setBonuses.checkHashMapCritChance((Player) getAttacker);
            } else {
                modifier = 0.0D;
            }

            if (ItemLoreStats.plugin.isTool(this.getSlots.returnItemInHand(getAttacker).getType())) {
                critPercent = this.util_Format.format(this.gearStats.getCritChanceGear(getAttacker) + this.gearStats.getCritChanceItemInHand(getAttacker) + modifier);
            } else {
                critPercent = this.util_Format.format(this.gearStats.getCritChanceGear(getAttacker) + modifier);
            }

            if (critPercent >= (double) this.util_Random.random(100)) {
                if (getAttacker instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.critStrike") && getAttacker instanceof Player) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.CriticalStrikeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                }

                if (getDefender instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.enemyCritStrike")) {
                    if (getAttacker instanceof Player) {
                        ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyCriticalStrikeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                    } else if (getAttacker instanceof LivingEntity) {
                        if (getAttacker.getCustomName() != null) {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyCriticalStrikeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        } else {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyCriticalStrikeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        }
                    }
                }

                return 2;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}
