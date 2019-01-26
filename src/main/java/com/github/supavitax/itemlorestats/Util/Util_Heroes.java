package com.github.supavitax.itemlorestats.Util;

import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import org.bukkit.entity.Player;

public class Util_Heroes {

    GearStats gearStats = new GearStats();
    ItemLoreStats main;


    public Util_Heroes(ItemLoreStats instance) {
        this.main = instance;
    }

    public int getHeroBaseHealth(Player player) {
        boolean baseMax = true;
        Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
        int baseMax1 = hero.getHeroClass().getBaseMaxHealth();
        return baseMax1;
    }

    public double getHeroHealthPerLevel(Player player) {
        double healthPerLevel = 0.0D;
        Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
        healthPerLevel = hero.getHeroClass().getMaxHealthPerLevel();
        return healthPerLevel;
    }

    public int getHeroLevel(Player player) {
        boolean level = false;
        Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
        int level1 = hero.getLevel(hero.getHeroClass());
        return level1;
    }

    public int getHeroExperience(Player player) {
        boolean experience = false;
        Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
        int experience1 = (int) hero.getExperience(hero.getHeroClass());
        return experience1;
    }

    public int getHeroMaxMana(Player player) {
        boolean maxMana = false;
        Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
        int maxMana1 = hero.getMaxMana();
        return maxMana1;
    }

    public void addHeroMaxManaStat(Player player, int manaStat) {
        Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
        hero.addMaxMana("ILS", manaStat);
    }

    public void removeHeroMaxManaStat(Player player) {
        Hero hero = Heroes.getInstance().getCharacterManager().getHero(player);
        hero.removeMaxMana("ILS");
    }
}
