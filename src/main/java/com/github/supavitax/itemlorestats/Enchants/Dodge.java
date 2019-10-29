package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.Durability.Durability;
import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.SetBonuses;
import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
import com.github.supavitax.itemlorestats.Util.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Dodge {
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

    public boolean dodgeChanceOnHit(LivingEntity getDefender, boolean isTool) {
        if (this.gearStats.getDodgeGear(getDefender) + this.gearStats.getDodgeItemInHand(getDefender) <= 0.0D) {
            return false;
        } else if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getDefender) + ".dod", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.dodge.internalCooldown"))) {
            if (getDefender instanceof Player) {
                ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getDefender) + ".dod", System.currentTimeMillis());
            }

            double dodgePercent = 0.0D;
            double modifier = 0.0D;
            if (getDefender instanceof Player) {
                modifier = this.setBonuses.checkHashMapDodge((Player) getDefender);
            } else {
                modifier = 0.0D;
            }

            if (isTool) {
                dodgePercent = this.util_Format.format(this.gearStats.getDodgeGear(getDefender) + this.gearStats.getDodgeItemInHand(getDefender) + modifier);
            } else {
                dodgePercent = this.util_Format.format(this.gearStats.getDodgeGear(getDefender) + modifier);
            }

            if (dodgePercent > 100.0D) {
                dodgePercent = 100.0D;
            }

            return dodgePercent >= (double) this.util_Random.random(100);
        } else {
            return false;
        }
    }
}
