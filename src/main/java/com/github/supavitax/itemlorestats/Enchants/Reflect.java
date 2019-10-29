package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.Durability.Durability;
import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.SetBonuses;
import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
import com.github.supavitax.itemlorestats.Util.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Reflect {
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

    public double reflectChanceOnHit(LivingEntity getAttacker, boolean isTool) {
        if (this.gearStats.getReflectGear(getAttacker) + this.gearStats.getReflectItemInHand(getAttacker) <= 0.0D) {
            return 0.0D;
        } else if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".ref", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.reflect.internalCooldown"))) {
            if (getAttacker instanceof Player) {
                ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".ref", System.currentTimeMillis());
            }

            double reflect = 0.0D;
            double modifier = 0.0D;
            if (getAttacker instanceof Player) {
                modifier = this.setBonuses.checkHashMapReflect((Player) getAttacker);
            } else {
                modifier = 0.0D;
            }

            if (isTool) {
                reflect = this.util_Format.format(this.gearStats.getReflectGear(getAttacker) + this.gearStats.getReflectItemInHand(getAttacker) + modifier);
            } else {
                reflect = this.util_Format.format(this.gearStats.getReflectGear(getAttacker) + modifier);
            }

            return this.util_Format.format(reflect);
        } else {
            return 0.0D;
        }
    }
}
