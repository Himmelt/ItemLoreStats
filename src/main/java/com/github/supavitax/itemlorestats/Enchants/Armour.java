package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.Durability.Durability;
import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.SetBonuses;
import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
import com.github.supavitax.itemlorestats.Util.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Armour {
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

    public double armourChanceOnHit(LivingEntity getDefender) {
        double modifier = 0.0D;
        if (getDefender instanceof Player) {
            modifier = this.setBonuses.checkHashMapArmour((Player) getDefender);
        } else {
            modifier = 0.0D;
        }

        return ItemLoreStats.plugin.isTool(this.getSlots.returnItemInHand(getDefender).getType()) ? this.util_Format.format(this.gearStats.getArmourGear(getDefender) + this.gearStats.getArmourItemInHand(getDefender) + modifier) : this.util_Format.format(this.gearStats.getArmourGear(getDefender) + modifier);
    }
}
