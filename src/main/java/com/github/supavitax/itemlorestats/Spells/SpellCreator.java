package com.github.supavitax.itemlorestats.Spells;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;

public class SpellCreator {
    private FileConfiguration PlayerDataConfig;

    public void spellBuilder(String spellFileName, Player player) {
        Projectile projectile = this.getProjectile(spellFileName, player);
        projectile.setShooter(player);
        projectile.setVelocity(player.getLocation().getDirection().multiply(this.getProjectileVelocity(spellFileName)));
        this.setProjectileProperties(projectile, spellFileName);
    }

    public String getValue(String spellFileName, String val) {
        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + spellFileName + ".yml"));
            return this.PlayerDataConfig.getString(val);
        } catch (Exception var4) {
            var4.printStackTrace();
            System.out.println("*********** Failed to load " + val + " from " + spellFileName + "! ***********");
            return null;
        }
    }

    public Projectile getProjectile(String spellFileName, Player player) {
        String projectileType = this.getValue(spellFileName, "projectile-type").toLowerCase();
        Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
        if ("snowball".equals(projectileType)) {
            Snowball projectile4 = player.getWorld().spawn(loc, Snowball.class);
            projectile4.setMetadata("ILS_Snowball", new FixedMetadataValue(ItemLoreStats.getPlugin(), player.getName()));
            return projectile4;
        } else if ("smallfireball".equals(projectileType)) {
            SmallFireball projectile3 = player.getWorld().spawn(loc, SmallFireball.class);
            projectile3.setYield(0.0F);
            projectile3.setIsIncendiary(false);
            projectile3.setMetadata("ILS_SmallFireball", new FixedMetadataValue(ItemLoreStats.getPlugin(), player.getName()));
            return projectile3;
        } else if ("fireball".equals(projectileType)) {
            Fireball projectile2 = player.getWorld().spawn(loc, Fireball.class);
            projectile2.setYield(0.0F);
            projectile2.setIsIncendiary(false);
            projectile2.setMetadata("ILS_Fireball", new FixedMetadataValue(ItemLoreStats.getPlugin(), player.getName()));
            return projectile2;
        } else if ("arrow".equals(projectileType)) {
            Arrow projectile1 = player.getWorld().spawn(loc, Arrow.class);
            projectile1.setMetadata("ILS_Arrow", new FixedMetadataValue(ItemLoreStats.getPlugin(), player.getName()));
            return projectile1;
        } else if ("egg".equals(projectileType)) {
            Egg projectile = player.getWorld().spawn(loc, Egg.class);
            projectile.setMetadata("ILS_Egg", new FixedMetadataValue(ItemLoreStats.getPlugin(), player.getName()));
            return projectile;
        } else {
            return null;
        }
    }

    public double getProjectileVelocity(String spellFileName) {
        double projectileVelocity = Double.parseDouble(this.getValue(spellFileName, "projectile-velocity").toLowerCase());
        return projectileVelocity;
    }

    public Effect getProjectileHitEffect(String spellFileName) {
        String projectileHitEffect = this.getValue(spellFileName, "projectile-hit-effect").toLowerCase();
        Effect hitEffect;
        if ("ender_signal".equals(projectileHitEffect)) {
            hitEffect = Effect.ENDER_SIGNAL;
            return hitEffect;
        } else if ("mobspawner_flames".equals(projectileHitEffect)) {
            hitEffect = Effect.MOBSPAWNER_FLAMES;
            return hitEffect;
        } else if ("potion_break".equals(projectileHitEffect)) {
            hitEffect = Effect.POTION_BREAK;
            return hitEffect;
        } else if ("smoke".equals(projectileHitEffect)) {
            hitEffect = Effect.SMOKE;
            return hitEffect;
        } else {
            return null;
        }
    }

    public int getCooldown(String spellFileName) {
        int cooldown = Integer.parseInt(this.getValue(spellFileName, "cooldown"));
        return cooldown;
    }

    public double getDirectHealAmount(String spellFileName) {
        double directHealAmount = Double.parseDouble(this.getValue(spellFileName, "heal.direct-heal-amount").toLowerCase());
        return directHealAmount;
    }

    public double getAOEHealAmount(String spellFileName) {
        double aoeHealAmount = Double.parseDouble(this.getValue(spellFileName, "heal.aoe-heal-amount").toLowerCase());
        return aoeHealAmount;
    }

    public double getAOEHealRange(String spellFileName) {
        double aoeHealRange = Double.parseDouble(this.getValue(spellFileName, "heal.aoe-heal-range").toLowerCase());
        return aoeHealRange;
    }

    public double getDirectDamageAmount(String spellFileName) {
        double directDamageAmount = Double.parseDouble(this.getValue(spellFileName, "damage.direct-damage-amount").toLowerCase());
        return directDamageAmount;
    }

    public double getAOEDamageAmount(String spellFileName) {
        double aoeDamageAmount = Double.parseDouble(this.getValue(spellFileName, "damage.aoe-damage-amount").toLowerCase());
        return aoeDamageAmount;
    }

    public double getAOEDamageRange(String spellFileName) {
        double aoeDamageRange = Double.parseDouble(this.getValue(spellFileName, "damage.aoe-damage-range").toLowerCase());
        return aoeDamageRange;
    }

    public void setProjectileProperties(Projectile projectile, String spellFileName) {
        double DHA = this.getDirectHealAmount(spellFileName);
        double AHA = this.getAOEHealAmount(spellFileName);
        double DDA = this.getDirectDamageAmount(spellFileName);
        double ADA = this.getAOEDamageAmount(spellFileName);
        projectile.setMetadata("DHA=", new FixedMetadataValue(ItemLoreStats.getPlugin(), DHA));
        projectile.setMetadata("AHA=", new FixedMetadataValue(ItemLoreStats.getPlugin(), AHA));
        projectile.setMetadata("AHR=", new FixedMetadataValue(ItemLoreStats.getPlugin(), this.getAOEHealRange(spellFileName)));
        projectile.setMetadata("DDA=", new FixedMetadataValue(ItemLoreStats.getPlugin(), DDA));
        projectile.setMetadata("ADA=", new FixedMetadataValue(ItemLoreStats.getPlugin(), ADA));
        projectile.setMetadata("ADR=", new FixedMetadataValue(ItemLoreStats.getPlugin(), this.getAOEDamageRange(spellFileName)));
        if (DHA > 0.0D || AHA > 0.0D) {
            projectile.setMetadata("Heal=", new FixedMetadataValue(ItemLoreStats.getPlugin(), true));
        }

        if (DDA > 0.0D || ADA > 0.0D) {
            projectile.setMetadata("Damage=", new FixedMetadataValue(ItemLoreStats.getPlugin(), true));
        }

    }
}
