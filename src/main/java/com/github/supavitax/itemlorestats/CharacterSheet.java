package com.github.supavitax.itemlorestats;

import com.github.supavitax.itemlorestats.Enchants.Vanilla_Power;
import com.github.supavitax.itemlorestats.Enchants.Vanilla_Sharpness;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_EntityManager;
import com.github.supavitax.itemlorestats.Util.Util_Format;
import com.github.supavitax.itemlorestats.Util.Util_Material;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class CharacterSheet {
    GearStats gearStats = new GearStats();
    SetBonuses setBonuses = new SetBonuses();
    Util_Colours util_Colours = new Util_Colours();
    Util_Material util_Material = new Util_Material();
    Util_Format util_Format = new Util_Format();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Vanilla_Sharpness vanilla_Sharpness = new Vanilla_Sharpness();
    Vanilla_Power vanilla_Power = new Vanilla_Power();

    public void returnStats(Player player, double passHealth) {
        String armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name");
        String dodge = ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name");
        String block = ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name");
        String critChance = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name");
        String critDamage = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name");
        String damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
        String health = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name");
        String healthRegen = ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name");
        String lifeSteal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name");
        String reflect = ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name");
        String fire = ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name");
        String ice = ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name");
        String poison = ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name");
        String wither = ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name");
        String harming = ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name");
        String blind = ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name");
        String xpmultiplier = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.colour") + ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name");
        String movementspeed = ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name");
        double armourModifier = this.setBonuses.checkHashMapArmour(player);
        double dodgeModifier = this.setBonuses.checkHashMapDodge(player);
        double blockModifier = this.setBonuses.checkHashMapBlock(player);
        double damageModifier = this.setBonuses.checkHashMapDamage(player);
        double critChanceModifier = this.setBonuses.checkHashMapCritChance(player);
        double critDamageModifier = this.setBonuses.checkHashMapCritDamage(player);
        double healthModifier = this.setBonuses.checkHashMapHealth(player);
        double healthRegenModifier = this.setBonuses.checkHashMapHealthRegen(player);
        double lifeStealModifier = this.setBonuses.checkHashMapLifeSteal(player);
        double reflectModifier = this.setBonuses.checkHashMapReflect(player);
        double fireModifier = this.setBonuses.checkHashMapFire(player);
        double iceModifier = this.setBonuses.checkHashMapIce(player);
        double poisonModifier = this.setBonuses.checkHashMapPoison(player);
        double witherModifier = this.setBonuses.checkHashMapWither(player);
        double harmingModifier = this.setBonuses.checkHashMapHarming(player);
        double blindModifier = this.setBonuses.checkHashMapBlind(player);
        double xpmultiplierModifier = this.setBonuses.checkHashMapXPMultiplier(player);
        double speedModifier = this.setBonuses.checkHashMapSpeed(player);
        player.sendMessage("" + ChatColor.BLUE + ChatColor.BOLD + "Stats:");
        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(armour) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getArmourGear(player) + this.gearStats.getArmourItemInHand(player) + armourModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(armour)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(armour) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getArmourGear(player) + armourModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(armour)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(dodge) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getDodgeGear(player) + this.gearStats.getDodgeItemInHand(player) + dodgeModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(dodge)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(dodge) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getDodgeGear(player) + dodgeModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(dodge)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(block) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getBlockGear(player) + this.gearStats.getBlockItemInHand(player) + blockModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(block)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(block) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getBlockGear(player) + blockModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(block)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            double minDamage = 0.0D;
            double maxDamage = 0.0D;
            if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
                minDamage = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[0]) + Double.parseDouble(this.gearStats.getDamageItemInHand(player).split("-")[0]) + this.util_Material.materialToDamage(player.getItemInHand().getType()) + damageModifier;
                maxDamage = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[1]) + Double.parseDouble(this.gearStats.getDamageItemInHand(player).split("-")[1]) + this.util_Material.materialToDamage(player.getItemInHand().getType()) + damageModifier;
                if (this.vanilla_Sharpness.hasSharpness(this.util_EntityManager.returnItemStackInHand(player))) {
                    minDamage = this.vanilla_Sharpness.calculateNewDamage(minDamage, this.util_EntityManager.returnItemStackInHand(player).getEnchantmentLevel(Enchantment.DAMAGE_ALL));
                    maxDamage = this.vanilla_Sharpness.calculateNewDamage(maxDamage, this.util_EntityManager.returnItemStackInHand(player).getEnchantmentLevel(Enchantment.DAMAGE_ALL));
                } else if (this.vanilla_Power.hasPower(this.util_EntityManager.returnItemStackInHand(player))) {
                    minDamage = this.vanilla_Sharpness.calculateNewDamage(minDamage, this.util_EntityManager.returnItemStackInHand(player).getEnchantmentLevel(Enchantment.ARROW_DAMAGE));
                    maxDamage = this.vanilla_Sharpness.calculateNewDamage(maxDamage, this.util_EntityManager.returnItemStackInHand(player).getEnchantmentLevel(Enchantment.ARROW_DAMAGE));
                }

                player.sendMessage("    " + this.util_Colours.replaceTooltipColour(damage) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(minDamage) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(damage)) + " - " + ChatColor.LIGHT_PURPLE + this.util_Format.format(maxDamage));
            } else {
                minDamage = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[0]) + Double.parseDouble(this.gearStats.getDamageItemInHand(player).split("-")[0]) + 1.0D + damageModifier;
                maxDamage = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[1]) + Double.parseDouble(this.gearStats.getDamageItemInHand(player).split("-")[1]) + 1.0D + damageModifier;
                if (this.vanilla_Sharpness.hasSharpness(this.util_EntityManager.returnItemStackInHand(player))) {
                    minDamage = this.vanilla_Sharpness.calculateNewDamage(minDamage, this.util_EntityManager.returnItemStackInHand(player).getEnchantmentLevel(Enchantment.DAMAGE_ALL));
                    maxDamage = this.vanilla_Sharpness.calculateNewDamage(maxDamage, this.util_EntityManager.returnItemStackInHand(player).getEnchantmentLevel(Enchantment.DAMAGE_ALL));
                } else if (this.vanilla_Power.hasPower(this.util_EntityManager.returnItemStackInHand(player))) {
                    minDamage = this.vanilla_Sharpness.calculateNewDamage(minDamage, this.util_EntityManager.returnItemStackInHand(player).getEnchantmentLevel(Enchantment.ARROW_DAMAGE));
                    maxDamage = this.vanilla_Sharpness.calculateNewDamage(maxDamage, this.util_EntityManager.returnItemStackInHand(player).getEnchantmentLevel(Enchantment.ARROW_DAMAGE));
                }

                player.sendMessage("    " + this.util_Colours.replaceTooltipColour(damage) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(minDamage) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(damage)) + " - " + ChatColor.LIGHT_PURPLE + this.util_Format.format(maxDamage));
            }
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(damage) + ": " + ChatColor.LIGHT_PURPLE + (Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[0]) + 1.0D + damageModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(damage)) + " - " + ChatColor.LIGHT_PURPLE + (Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[1]) + 1.0D + damageModifier));
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(critChance) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getCritChanceGear(player) + this.gearStats.getCritChanceItemInHand(player) + critChanceModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critChance)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(critChance) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getCritChanceGear(player) + critChanceModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critChance)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(critDamage) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getCritDamageGear(player) + this.gearStats.getCritDamageItemInHand(player) + critDamageModifier + ItemLoreStats.plugin.getConfig().getDouble("baseCritDamage")) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critDamage)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(critDamage) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getCritDamageItemInHand(player) + critDamageModifier + ItemLoreStats.plugin.getConfig().getDouble("baseCritDamage")) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critDamage)) + "%");
        }

        player.sendMessage("    " + this.util_Colours.replaceTooltipColour(health) + ": " + ChatColor.LIGHT_PURPLE + (passHealth + healthModifier));
        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(healthRegen) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(ItemLoreStats.plugin.getConfig().getDouble("baseHealthRegen") + this.gearStats.getHealthRegenGear(player) + this.gearStats.getHealthRegenItemInHand(player) + ItemLoreStats.plugin.getConfig().getDouble("additionalStatsPerLevel.healthRegen") + healthRegenModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(healthRegen)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(healthRegen) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(ItemLoreStats.plugin.getConfig().getDouble("baseHealthRegen") + this.gearStats.getHealthRegenGear(player) + ItemLoreStats.plugin.getConfig().getDouble("additionalStatsPerLevel.healthRegen") + healthRegenModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(healthRegen)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(lifeSteal) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getLifeStealGear(player) + this.gearStats.getLifeStealItemInHand(player) + lifeStealModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(lifeSteal)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(lifeSteal) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getLifeStealGear(player) + lifeStealModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(lifeSteal)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(reflect) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getReflectGear(player) + this.gearStats.getReflectItemInHand(player) + reflectModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(reflect)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(reflect) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getReflectGear(player) + reflectModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(reflect)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(fire) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getFireGear(player) + this.gearStats.getFireItemInHand(player) + fireModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(fire)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(fire) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getFireGear(player) + fireModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(fire)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(ice) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getIceGear(player) + this.gearStats.getIceItemInHand(player) + iceModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ice)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(ice) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getIceGear(player) + iceModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ice)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(poison) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getPoisonGear(player) + this.gearStats.getPoisonItemInHand(player) + poisonModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(poison)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(poison) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getPoisonGear(player) + poisonModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(poison)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(wither) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getWitherGear(player) + this.gearStats.getWitherItemInHand(player) + witherModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(wither)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(wither) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getWitherGear(player) + witherModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(wither)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(harming) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getHarmingGear(player) + this.gearStats.getHarmingItemInHand(player) + harmingModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(harming)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(harming) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getHarmingGear(player) + harmingModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(harming)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(blind) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getBlindGear(player) + this.gearStats.getBlindItemInHand(player) + blindModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(blind)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(blind) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getBlindGear(player) + blindModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(blind)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(xpmultiplier) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getXPMultiplierGear(player) + this.gearStats.getXPMultiplierItemInHand(player) + xpmultiplierModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpmultiplier)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(xpmultiplier) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getXPMultiplierGear(player) + xpmultiplierModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpmultiplier)) + "%");
        }

        if (ItemLoreStats.plugin.isTool(player.getItemInHand().getType())) {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(movementspeed) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getMovementSpeedGear(player) + this.gearStats.getMovementSpeedItemInHand(player) + Double.valueOf(player.getLevel()) * ItemLoreStats.plugin.getConfig().getDouble("additionalStatsPerLevel.speed") + speedModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(movementspeed)) + "%");
        } else {
            player.sendMessage("    " + this.util_Colours.replaceTooltipColour(movementspeed) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(this.gearStats.getMovementSpeedGear(player) + Double.valueOf(player.getLevel()) * ItemLoreStats.plugin.getConfig().getDouble("additionalStatsPerLevel.speed") + speedModifier) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(movementspeed)) + "%");
        }

    }
}
