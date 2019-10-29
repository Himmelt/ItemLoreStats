package com.github.supavitax.itemlorestats.Util;

import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.ItemUpgrading.ItemUpgrade;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.event.PlayerLevelUpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Util_SkillAPI implements Listener {
    ItemLoreStats main;
    GearStats gearStats = new GearStats();
    ItemUpgrade itemUpgrade = new ItemUpgrade();

    public Util_SkillAPI(ItemLoreStats instance) {
        this.main = instance;
    }

    public int getSkillAPIBaseHealth(Player player) {
        int baseMax = 20;
        ItemLoreStats.plugin.getSkillAPI();
        if (SkillAPI.getPlayerData(player).getMainClass() != null) {
            ItemLoreStats.plugin.getSkillAPI();
            baseMax = (int) SkillAPI.getPlayerData(player).getMainClass().getData().getBaseHealth();
        }

        return baseMax;
    }

    public int getSkillAPIHealthPerLevel(Player player) {
        int healthPerLevel = 0;
        ItemLoreStats.plugin.getSkillAPI();
        if (SkillAPI.getPlayerData(player).getMainClass() != null) {
            ItemLoreStats.plugin.getSkillAPI();
            healthPerLevel = (int) SkillAPI.getPlayerData(player).getMainClass().getData().getHealthScale();
        }

        return healthPerLevel;
    }

    public int getSkillAPILevel(Player player) {
        int level = 0;
        ItemLoreStats.plugin.getSkillAPI();
        if (SkillAPI.getPlayerData(player).getMainClass() != null) {
            ItemLoreStats.plugin.getSkillAPI();
            level = SkillAPI.getPlayerData(player).getMainClass().getLevel();
        }

        return level;
    }

    public int getSkillAPIExperience(Player player) {
        int level = 0;
        ItemLoreStats.plugin.getSkillAPI();
        if (SkillAPI.getPlayerData(player).getMainClass() != null) {
            ItemLoreStats.plugin.getSkillAPI();
            level = (int) SkillAPI.getPlayerData(player).getMainClass().getExp();
        }

        return level;
    }

    public String getSkillAPIClass(Player player) {
        ItemLoreStats.plugin.getSkillAPI();
        if (SkillAPI.getPlayerData(player).getMainClass() != null) {
            ItemLoreStats.plugin.getSkillAPI();
            return SkillAPI.getPlayerData(player).getMainClass().getData().getName();
        } else {
            return null;
        }
    }

    @EventHandler
    public void onSkillAPIPlayerLevel(PlayerLevelUpEvent event) {
        Player player = event.getPlayerData().getPlayer();
        if (event.getLevel() <= ItemLoreStats.plugin.getConfig().getInt("levelCap")) {
            if (event.getLevel() > ItemLoreStats.plugin.util_SkillAPI.getSkillAPILevel(player)) {
                this.itemUpgrade.increaseItemStatOnItemInHand(player);
                this.itemUpgrade.increaseItemStatOnHelmet(player);
                this.itemUpgrade.increaseItemStatOnChestplate(player);
                this.itemUpgrade.increaseItemStatOnLeggings(player);
                this.itemUpgrade.increaseItemStatOnBoots(player);
                ItemLoreStats.plugin.updateHealth(player);
                ItemLoreStats.plugin.updatePlayerSpeed(player);
            }
        } else {
            player.setLevel(ItemLoreStats.plugin.getConfig().getInt("levelCap"));
            player.setExp(0.0F);
        }

    }
}
