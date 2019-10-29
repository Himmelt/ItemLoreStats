package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.Durability.Durability;
import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.SetBonuses;
import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
import com.github.supavitax.itemlorestats.Util.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class LifeSteal {
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

    public void lifeStealChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, double weaponDamage, boolean isTool) {
        if (this.gearStats.getLifeStealGear(getAttacker) + this.gearStats.getLifeStealItemInHand(getAttacker) > 0.0D && !this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".lif", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.lifeSteal.internalCooldown"))) {
            if (getAttacker instanceof Player) {
                ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".lif", System.currentTimeMillis());
            }

            double lifeStealHeal = 0.0D;
            double lifeStealPercent = 0.0D;
            double modifier = 0.0D;
            if (getAttacker instanceof Player) {
                modifier = this.setBonuses.checkHashMapLifeSteal((Player) getAttacker);
            } else {
                modifier = 0.0D;
            }

            if (isTool) {
                lifeStealPercent = this.util_Format.format(this.gearStats.getLifeStealGear(getAttacker) + this.gearStats.getLifeStealItemInHand(getAttacker) + modifier);
            } else {
                lifeStealPercent = this.util_Format.format(this.gearStats.getLifeStealGear(getAttacker) + modifier);
            }

            if (lifeStealPercent > 100.0D) {
                lifeStealPercent = 100.0D;
            }

            if ((double) this.util_Random.random(100) <= lifeStealPercent) {
                lifeStealHeal = ItemLoreStats.plugin.getConfig().getDouble("secondaryStats.lifeSteal.healPercentage") * weaponDamage;
                if (getAttacker instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.lifeSteal")) {
                    getAttacker.sendMessage(this.util_GetResponse.getResponse("DamageMessages.LifeStealSuccess", getAttacker, getDefender, String.valueOf((int) lifeStealHeal), String.valueOf((int) lifeStealHeal)));
                }

                if (getDefender instanceof Player && ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.enemyLifeSteal")) {
                    if (getAttacker instanceof Player) {
                        getDefender.sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyLifeStealSuccess", getAttacker, getDefender, String.valueOf((int) lifeStealHeal), String.valueOf((int) lifeStealHeal)));
                    } else if (getAttacker instanceof LivingEntity) {
                        if (getAttacker.getCustomName() != null) {
                            getDefender.sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyLifeStealSuccess", getAttacker, getDefender, String.valueOf((int) lifeStealHeal), String.valueOf((int) lifeStealHeal)));
                        } else {
                            getDefender.sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyLifeStealSuccess", getAttacker, getDefender, String.valueOf((int) lifeStealHeal), String.valueOf((int) lifeStealHeal)));
                        }
                    }
                }

                if (this.util_EntityManager.returnEntityCurrentHealth(getAttacker) < this.util_EntityManager.returnEntityMaxHealth(getAttacker)) {
                    if (lifeStealHeal > Math.abs(this.util_EntityManager.returnEntityCurrentHealth(getAttacker) - this.util_EntityManager.returnEntityMaxHealth(getAttacker))) {
                        double getRemainingHealth = Math.abs(this.util_EntityManager.returnEntityCurrentHealth(getAttacker) - this.util_EntityManager.returnEntityMaxHealth(getAttacker));
                        this.util_EntityManager.setEntityCurrentHealth(getAttacker, this.util_EntityManager.returnEntityCurrentHealth(getAttacker) + getRemainingHealth);
                    } else {
                        this.util_EntityManager.setEntityCurrentHealth(getAttacker, this.util_EntityManager.returnEntityCurrentHealth(getAttacker) + lifeStealHeal);
                    }
                }

                if (getAttacker instanceof Player) {
                    ItemLoreStats.plugin.updateBarAPI((Player) getAttacker);
                }

                if (getDefender instanceof Player) {
                    ItemLoreStats.plugin.updateBarAPI((Player) getDefender);
                }
            }
        }

    }
}
