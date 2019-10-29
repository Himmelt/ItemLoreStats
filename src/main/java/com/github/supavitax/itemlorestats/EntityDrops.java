package com.github.supavitax.itemlorestats;

import com.github.supavitax.itemlorestats.ItemGeneration.*;
import com.github.supavitax.itemlorestats.Util.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;

public class EntityDrops implements Listener {
    Util_Colours util_Colours = new Util_Colours();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Material util_Material = new Util_Material();
    Util_Random util_Random = new Util_Random();
    Util_Format util_Format = new Util_Format();
    Prefix prefix = new Prefix();
    Suffix suffix = new Suffix();
    SellValue sellValueCalc = new SellValue();
    RandomLore randomLore = new RandomLore();
    Rarity rarity = new Rarity();
    MaterialType materialType = new MaterialType();
    StatRanges statRanges = new StatRanges();
    private FileConfiguration PlayerDataConfig;

    private int random(int length) {
        return (new Random()).nextInt(length) + 1;
    }

    private int randomKeySelection(int length) {
        return (new Random()).nextInt(length);
    }

    private int randomRangeInt(int min, int max) {
        return (int) ((double) min + Math.random() * (double) (max - min));
    }

    public boolean dropChance(int setDropChance) {
        return this.random(100) <= setDropChance;
    }

    public String randomClass() {
        List getListClasses = ItemLoreStats.plugin.getConfig().getStringList("bonusStats.class.list");
        String selectClass = (String) getListClasses.get(this.random(getListClasses.size()) - 1);
        return selectClass;
    }

    public double statMultiplier(String statType, String stat) {
        if (ItemLoreStats.plugin.getConfig().getDouble(statType + "." + stat + ".statMultiplierOnDrop") > 0.0D) {
            double multiplier = ItemLoreStats.plugin.getConfig().getDouble(statType + "." + stat + ".statMultiplierOnDrop");
            return multiplier;
        } else {
            return 0.0D;
        }
    }

    public List setLore(Player player, int level, int mobLevel, String entity, String dropChance, Material material) {
        String armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name");
        String armourNoColour = armour.replaceAll("&([0-9a-f])", "");
        String dodge = ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name");
        String dodgeNoColour = dodge.replaceAll("&([0-9a-f])", "");
        String block = ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name");
        String blockNoColour = block.replaceAll("&([0-9a-f])", "");
        String critChance = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name");
        String critChanceNoColour = critChance.replaceAll("&([0-9a-f])", "");
        String critDamage = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name");
        String critDamageNoColour = critDamage.replaceAll("&([0-9a-f])", "");
        String damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
        String damageNoColour = damage.replaceAll("&([0-9a-f])", "");
        String health = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name");
        String healthNoColour = health.replaceAll("&([0-9a-f])", "");
        String healthRegen = ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name");
        String healthRegenNoColour = healthRegen.replaceAll("&([0-9a-f])", "");
        String lifeSteal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name");
        String lifeStealNoColour = lifeSteal.replaceAll("&([0-9a-f])", "");
        String reflect = ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name");
        String reflectNoColour = reflect.replaceAll("&([0-9a-f])", "");
        String fire = ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name");
        String fireNoColour = fire.replaceAll("&([0-9a-f])", "");
        String ice = ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name");
        String iceNoColour = ice.replaceAll("&([0-9a-f])", "");
        String poison = ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name");
        String poisonNoColour = poison.replaceAll("&([0-9a-f])", "");
        String wither = ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name");
        String witherNoColour = wither.replaceAll("&([0-9a-f])", "");
        String harming = ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name");
        String harmingNoColour = harming.replaceAll("&([0-9a-f])", "");
        String blind = ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name");
        String blindNoColour = blind.replaceAll("&([0-9a-f])", "");
        String movementSpeed = ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name");
        String movementSpeedNoColour = movementSpeed.replaceAll("&([0-9a-f])", "");
        String xpMultiplier = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.colour") + ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name");
        String xpMultiplierNoColour = xpMultiplier.replaceAll("&([0-9a-f])", "");
        String weaponSpeed = ItemLoreStats.plugin.getConfig().getString("bonusStats.weaponSpeed.colour") + ItemLoreStats.plugin.getConfig().getString("bonusStats.weaponSpeed.name");
        String weaponSpeedNoColour = weaponSpeed.replaceAll("&([0-9a-f])", "");
        String xpLevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.colour") + ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
        String xpLevelNoColour = xpLevel.replaceAll("&([0-9a-f])", "");
        String className = ItemLoreStats.plugin.getConfig().getString("bonusStats.class.colour") + ItemLoreStats.plugin.getConfig().getString("bonusStats.class.name");
        String classNameNoColour = className.replaceAll("&([0-9a-f])", "");
        String soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.colour") + ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
        String durability = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.colour") + ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
        String durabilityNoColour = durability.replaceAll("&([0-9a-f])", "");
        String tnt = ItemLoreStats.plugin.getConfig().getString("spells.tnt.colour") + ItemLoreStats.plugin.getConfig().getString("spells.tnt.name");
        String tntNoColour = tnt.replaceAll("&([0-9a-f])", "");
        String fireball = ItemLoreStats.plugin.getConfig().getString("spells.fireball.colour") + ItemLoreStats.plugin.getConfig().getString("spells.fireball.name");
        String fireballNoColour = fireball.replaceAll("&([0-9a-f])", "");
        String lightning = ItemLoreStats.plugin.getConfig().getString("spells.lightning.colour") + ItemLoreStats.plugin.getConfig().getString("spells.lightning.name");
        String lightningNoColour = lightning.replaceAll("&([0-9a-f])", "");
        String frostbolt = ItemLoreStats.plugin.getConfig().getString("spells.frostbolt.colour") + ItemLoreStats.plugin.getConfig().getString("spells.frostbolt.name");
        String frostboltNoColour = frostbolt.replaceAll("&([0-9a-f])", "");
        String minorHeal = ItemLoreStats.plugin.getConfig().getString("spells.minorHeal.colour") + ItemLoreStats.plugin.getConfig().getString("spells.minorHeal.name");
        String minorHealNoColour = minorHeal.replaceAll("&([0-9a-f])", "");
        String majorHeal = ItemLoreStats.plugin.getConfig().getString("spells.majorHeal.colour") + ItemLoreStats.plugin.getConfig().getString("spells.majorHeal.name");
        String majorHealNoColour = majorHeal.replaceAll("&([0-9a-f])", "");
        int randomApplyChance = ItemLoreStats.plugin.getConfig().getInt("randomApplyChance");
        double armourValue = 0.0D;
        double dodgeValue = 0.0D;
        double blockValue = 0.0D;
        double critChanceValue = 0.0D;
        double critDamageValue = 0.0D;
        double healthRegenValue = 0.0D;
        double lifeStealValue = 0.0D;
        double reflectValue = 0.0D;
        double fireValue = 0.0D;
        double iceValue = 0.0D;
        double poisonValue = 0.0D;
        double witherValue = 0.0D;
        double harmingValue = 0.0D;
        double blindValue = 0.0D;
        double xpMultiplierValue = 0.0D;
        double movementSpeedValue = 0.0D;
        ArrayList setLoreList = new ArrayList(Arrays.asList(""));
        ArrayList highestValue = new ArrayList();
        double sellValue = 0.0D;
        int playerLevel = level;
        if (level < 1) {
            playerLevel = 1;
        }

        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + entity.toLowerCase() + ".yml"));
            if (this.PlayerDataConfig.contains(dropChance + ".properties.weaponspeed") && !"0".equals(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed"))) {
                if ("random".equalsIgnoreCase(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed"))) {
                    int e;
                    if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
                        if (this.random(100) <= randomApplyChance) {
                            e = this.random(100);
                            if (e <= 20) {
                                setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.DARK_RED + "Very Slow");
                                sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.verySlow");
                            } else if (e > 20 && e < 40) {
                                setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.RED + "Slow");
                                sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.slow");
                            } else if (e > 40 && e < 60) {
                                setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.YELLOW + "Normal");
                                sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.normal");
                            } else if (e > 60 && e < 80) {
                                setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.GREEN + "Fast");
                                sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.fast");
                            } else if (e >= 80) {
                                setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.DARK_GREEN + "Very Fast");
                                sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.veryFast");
                            }
                        }
                    } else {
                        e = this.random(100);
                        if (e <= 20) {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.DARK_RED + "Very Slow");
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.verySlow");
                        } else if (e > 20 && e < 40) {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.RED + "Slow");
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.slow");
                        } else if (e > 40 && e < 60) {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.YELLOW + "Normal");
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.normal");
                        } else if (e > 60 && e < 80) {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.GREEN + "Fast");
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.fast");
                        } else if (e >= 80) {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.DARK_GREEN + "Very Fast");
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.veryFast");
                        }
                    }
                } else if ("verySlow".equalsIgnoreCase(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed"))) {
                    if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
                        if (this.random(100) <= randomApplyChance) {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed")));
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.verySlow");
                        }
                    } else {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed")));
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.verySlow");
                    }
                } else if ("slow".equalsIgnoreCase(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed"))) {
                    if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
                        if (this.random(100) <= randomApplyChance) {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed")));
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.slow");
                        }
                    } else {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed")));
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.slow");
                    }
                } else if (this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed").contains("fast")) {
                    if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
                        if (this.random(100) <= randomApplyChance) {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed")));
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.fast");
                        }
                    } else {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed")));
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.fast");
                    }
                } else if (this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed").contains("veryFast")) {
                    if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
                        if (this.random(100) <= randomApplyChance) {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed")));
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.veryFast");
                        }
                    } else {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed")));
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.veryFast");
                    }
                } else if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed")));
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.normal");
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed")));
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.normal");
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.armour")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.armourRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(armour)) + armourNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.armour"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "armour", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(armour)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.armour.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(armour)) + armourNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.armour"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "armour", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(armour)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.armour.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.dodge")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.dodgeRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(dodge)) + dodgeNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.dodge"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "dodge", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(dodge)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.dodge.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(dodge)) + dodgeNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.dodge"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "dodge", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(dodge)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.dodge.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.block")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.blockRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(block)) + blockNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.block"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "block", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(block)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.block.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(block)) + blockNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.block"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "block", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(block)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.block.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.damage")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.damageRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(damage)) + damageNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.damage"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "damage", dropChance).replace(",", "") + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(damage)));
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.damage.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(damage)) + damageNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.damage"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "damage", dropChance).replace(",", "") + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(damage)));
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.damage.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.critChance")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.critChanceRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critChance)) + critChanceNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.critChance"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "critChance", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critChance)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.critChance.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critChance)) + critChanceNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.critChance"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "critChance", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critChance)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.critChance.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.critDamage")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.critDamageRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critDamage)) + critDamageNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.critDamage"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "critDamage", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critDamage)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.critDamage.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critDamage)) + critDamageNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.critDamage"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "critDamage", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critDamage)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.critDamage.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.health")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.healthRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(health)) + healthNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.health"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "health", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(health)));
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.health.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(health)) + healthNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.health"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "health", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(health)));
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.health.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.healthRegen")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.healthRegenRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(healthRegen)) + healthRegenNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.healthRegen"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "healthRegen", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(healthRegen)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.healthRegen.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(healthRegen)) + healthRegenNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.healthRegen"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "healthRegen", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(healthRegen)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.healthRegen.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.lifeSteal")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.lifeStealRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(lifeSteal)) + lifeStealNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.lifeSteal"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "lifeSteal", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(lifeSteal)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.lifeSteal.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(lifeSteal)) + lifeStealNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.lifeSteal"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "lifeSteal", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(lifeSteal)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.lifeSteal.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.reflect")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.reflectRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(reflect)) + reflectNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.reflect"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "reflect", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(reflect)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.reflect.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(reflect)) + reflectNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.reflect"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "reflect", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(reflect)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.reflect.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.fire")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.fireRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(fire)) + fireNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.fire"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "fire", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(fire)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.fire.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(fire)) + fireNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.fire"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "fire", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(fire)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.fire.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.ice")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.iceRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ice)) + iceNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.ice"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "ice", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ice)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.ice.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ice)) + iceNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.ice"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "ice", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ice)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.ice.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.poison")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.poisonRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(poison)) + poisonNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.poison"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "poison", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(poison)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.poison.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(poison)) + poisonNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.poison"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "poison", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(poison)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.poison.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.wither")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.witherRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(wither)) + witherNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.wither"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "wither", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(wither)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.wither.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(wither)) + witherNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.wither"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "wither", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(wither)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.wither.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.harming")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.harmingRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(harming)) + harmingNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.harming"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "harming", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(harming)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.harming.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(harming)) + harmingNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.harming"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "harming", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(harming)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.harming.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.blind")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.blindRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(blind)) + blindNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.blind"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "blind", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(blind)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.blind.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(blind)) + blindNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.blind"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "blind", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(blind)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.blind.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.xpMultiplier")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.xpMultiplierRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpMultiplier)) + xpMultiplierNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.xpMultiplier"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "xpMultiplier", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpMultiplier)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.xpMultiplier.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpMultiplier)) + xpMultiplierNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.xpMultiplier"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "xpMultiplier", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpMultiplier)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.xpMultiplier.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.movementSpeed")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.movementSpeedRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(movementSpeed)) + movementSpeedNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.movementSpeed"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "movementSpeed", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(movementSpeed)) + "%");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.movementSpeed.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(movementSpeed)) + movementSpeedNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.movementSpeed"))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "movementSpeed", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(movementSpeed)) + "%");
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.movementSpeed.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (!ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) {
                if (setLoreList.get(setLoreList.size() - 1) != "") {
                    setLoreList.add("");
                }

                String e1 = "";
                String durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
                if (this.PlayerDataConfig.contains(dropChance + ".properties.durability") && !"0".equals(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) {
                    if ("player".equalsIgnoreCase(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) {
                        if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.durabilityRandomApply")) {
                            if (this.random(100) <= randomApplyChance) {
                                setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + Double.valueOf(playerLevel) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + playerLevel);
                                setLoreList.add("");
                                sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
                            }
                        } else {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + Double.valueOf(playerLevel) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + playerLevel);
                            setLoreList.add("");
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
                        }
                    } else if (this.PlayerDataConfig.getString(dropChance + ".properties.durability").contains("-player+")) {
                        if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.durabilityRandomApply")) {
                            if (this.random(100) <= randomApplyChance) {
                                e1 = String.valueOf(this.randomRangeInt(Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                                setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + e1 + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + e1);
                                setLoreList.add("");
                                sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
                            }
                        } else {
                            e1 = String.valueOf(this.randomRangeInt(Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + e1 + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + e1);
                            setLoreList.add("");
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
                        }
                    } else if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.durabilityRandomApply")) {
                        if (this.random(100) <= randomApplyChance) {
                            e1 = String.valueOf(this.randomRangeInt(Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + e1 + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + e1);
                            setLoreList.add("");
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
                        }
                    } else {
                        e1 = String.valueOf(this.randomRangeInt(Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + e1 + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.durability"))) + e1);
                        setLoreList.add("");
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
                    }
                }
            }

            if (ItemLoreStats.plugin.getConfig().getBoolean("addRandomLore")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.addRandomLoreRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add("");
                        setLoreList.add(this.randomLore.get(material));
                        setLoreList.add("");
                    }
                } else {
                    setLoreList.add("");
                    setLoreList.add(this.randomLore.get(material));
                    setLoreList.add("");
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.xpLevel")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.xpLevelRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)) + xpLevelNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.xpLevel"))) + Math.round(Double.parseDouble(this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "xpLevel", dropChance))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)));
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.xpLevel.sellValuePerStat") * Double.valueOf(playerLevel);
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)) + xpLevelNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.xpLevel"))) + Math.round(Double.parseDouble(this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "xpLevel", dropChance))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)));
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("secondaryStats.xpLevel.sellValuePerStat") * Double.valueOf(playerLevel);
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.class") && !"0".equals(this.PlayerDataConfig.getString(dropChance + ".properties.class"))) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.classRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        if ("random".equalsIgnoreCase(this.PlayerDataConfig.getString(dropChance + ".properties.class"))) {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.class"))) + this.util_Colours.replaceTooltipColour(this.util_Colours.replaceTooltipColour(this.randomClass())));
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
                        } else {
                            setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.class"))) + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.class")));
                            sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
                        }
                    } else if ("random".equalsIgnoreCase(this.PlayerDataConfig.getString(dropChance + ".properties.class"))) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.class"))) + this.util_Colours.replaceTooltipColour(this.util_Colours.replaceTooltipColour(this.randomClass())));
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
                    } else {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.class"))) + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.class")));
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
                    }
                } else if ("random".equalsIgnoreCase(this.PlayerDataConfig.getString(dropChance + ".properties.class"))) {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.class"))) + this.util_Colours.replaceTooltipColour(this.util_Colours.replaceTooltipColour(this.randomClass())));
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.class"))) + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(dropChance + ".properties.class")));
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".properties.soulbound") && this.PlayerDataConfig.getBoolean(dropChance + ".properties.soulbound")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.soulboundRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_Colours.replaceTooltipColour(soulbound) + " " + player.getName());
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.soulbound.sellValuePerStat") * 1.0D;
                    }
                } else {
                    setLoreList.add(this.util_Colours.replaceTooltipColour(soulbound) + " " + player.getName());
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("bonusStats.soulbound.sellValuePerStat") * 1.0D;
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".spells.tnt") && this.PlayerDataConfig.getBoolean(dropChance + ".spells.tnt")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.tntRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.tnt.colour")) + tntNoColour);
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.tnt.sellValuePerStat") * 1.0D;
                    }
                } else {
                    setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.tnt.colour")) + tntNoColour);
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.tnt.sellValuePerStat") * 1.0D;
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".spells.fireball") && this.PlayerDataConfig.getBoolean(dropChance + ".spells.fireball")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.fireballRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.fireball.colour")) + fireballNoColour);
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.fireball.sellValuePerStat") * 1.0D;
                    }
                } else {
                    setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.fireball.colour")) + fireballNoColour);
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.fireball.sellValuePerStat") * 1.0D;
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".spells.lightning") && this.PlayerDataConfig.getBoolean(dropChance + ".spells.lightning")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.lightningRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.lightning.colour")) + lightningNoColour);
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.lightning.sellValuePerStat") * 1.0D;
                    }
                } else {
                    setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.lightning.colour")) + lightningNoColour);
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.lightning.sellValuePerStat") * 1.0D;
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".spells.frostbolt") && this.PlayerDataConfig.getBoolean(dropChance + ".spells.frostbolt")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.frostboltRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.frostbolt.colour")) + frostboltNoColour);
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.frostbolt.sellValuePerStat") * 1.0D;
                    }
                } else {
                    setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.frostbolt.colour")) + frostboltNoColour);
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.frostbolt.sellValuePerStat") * 1.0D;
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".spells.minorHeal") && this.PlayerDataConfig.getBoolean(dropChance + ".spells.minorHeal")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.minorHealRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.minorHeal.colour")) + minorHealNoColour);
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.minorHeal.sellValuePerStat") * 1.0D;
                    }
                } else {
                    setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.minorHeal.colour")) + minorHealNoColour);
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.minorHeal.sellValuePerStat") * 1.0D;
                }
            }

            if (this.PlayerDataConfig.contains(dropChance + ".spells.majorHeal") && this.PlayerDataConfig.getBoolean(dropChance + ".spells.majorHeal")) {
                if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.majorHealRandomApply")) {
                    if (this.random(100) <= randomApplyChance) {
                        setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.majorHeal.colour")) + majorHealNoColour);
                        sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.majorHeal.sellValuePerStat") * 1.0D;
                    }
                } else {
                    setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("spells.majorHeal.colour")) + majorHealNoColour);
                    sellValue += ItemLoreStats.getPlugin().getConfig().getDouble("spells.majorHeal.sellValuePerStat") * 1.0D;
                }
            }

            if (ItemLoreStats.getPlugin().getConfig().getBoolean("addSellValueToDrops")) {
                if (setLoreList.get(setLoreList.size() - 1) != "") {
                    setLoreList.add("");
                }

                sellValue += this.sellValueCalc.get(material);
                setLoreList.add(this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("bonusStats.sellValue.colour")) + ItemLoreStats.getPlugin().getConfig().getString("bonusStats.sellValue.name") + ": " + this.util_Colours.replaceTooltipColour(ItemLoreStats.getPlugin().getConfig().getString("bonusStats.sellValue.currency.colour")) + Double.valueOf(sellValue).intValue() + " " + ItemLoreStats.getPlugin().getConfig().getString("bonusStats.sellValue.currency.name"));
            }

            highestValue.add(armourValue);
            highestValue.add(dodgeValue);
            highestValue.add(blockValue);
            highestValue.add(critChanceValue);
            highestValue.add(critDamageValue);
            highestValue.add(healthRegenValue);
            highestValue.add(lifeStealValue);
            highestValue.add(reflectValue);
            highestValue.add(fireValue);
            highestValue.add(iceValue);
            highestValue.add(poisonValue);
            highestValue.add(witherValue);
            highestValue.add(harmingValue);
            highestValue.add(blindValue);
            highestValue.add(xpMultiplierValue);
            highestValue.add(movementSpeedValue);
            highestValue.add(1.0D);
            double e2 = (Double) Collections.max(highestValue);

            while (highestValue.contains(e2)) {
                int i = highestValue.indexOf(e2);
                if (highestValue.indexOf(e2) == 0) {
                    setLoreList.add("primaryStats.armour");
                    break;
                }

                if (highestValue.indexOf(e2) == 1) {
                    setLoreList.add("secondaryStats.dodge");
                    break;
                }

                if (highestValue.indexOf(e2) == 2) {
                    setLoreList.add("secondaryStats.block");
                    break;
                }

                if (highestValue.indexOf(e2) == 3) {
                    setLoreList.add("secondaryStats.critChance");
                    break;
                }

                if (highestValue.indexOf(e2) == 4) {
                    setLoreList.add("secondaryStats.critDamage");
                    break;
                }

                if (highestValue.indexOf(e2) == 5) {
                    setLoreList.add("primaryStats.healthRegen");
                    break;
                }

                if (highestValue.indexOf(e2) == 6) {
                    setLoreList.add("secondaryStats.lifeSteal");
                    break;
                }

                if (highestValue.indexOf(e2) == 7) {
                    setLoreList.add("secondaryStats.reflect");
                    break;
                }

                if (highestValue.indexOf(e2) == 8) {
                    setLoreList.add("secondaryStats.fire");
                    break;
                }

                if (highestValue.indexOf(e2) == 9) {
                    setLoreList.add("secondaryStats.ice");
                    break;
                }

                if (highestValue.indexOf(e2) == 10) {
                    setLoreList.add("secondaryStats.poison");
                    break;
                }

                if (highestValue.indexOf(e2) == 11) {
                    setLoreList.add("secondaryStats.wither");
                    break;
                }

                if (highestValue.indexOf(e2) == 12) {
                    setLoreList.add("secondaryStats.harming");
                    break;
                }

                if (highestValue.indexOf(e2) == 13) {
                    setLoreList.add("secondaryStats.blind");
                    break;
                }

                if (highestValue.indexOf(e2) == 14) {
                    setLoreList.add("bonusStats.xpMultiplier");
                    break;
                }

                if (highestValue.indexOf(e2) == 15) {
                    setLoreList.add("secondaryStats.movementSpeed");
                    break;
                }

                if (highestValue.indexOf(e2) == 16) {
                    setLoreList.add(".");
                    break;
                }

                highestValue.set(i, -1.0D);
            }

            return setLoreList;
        } catch (Exception var105) {
            var105.printStackTrace();
            System.out.println("*********** Failed to load lore from " + entity + " file! ***********");
            return setLoreList;
        }
    }

    public ItemStack gearGenerator(Player player, int playerLevel, int mobLevel, String entityType, String dropChance) {
        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + entityType + ".yml"));
            String e = ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + entityType + ".yml";
            ItemStack createItem;
            if (this.PlayerDataConfig.getString(dropChance + ".savedItem") == null) {
                Material fileToLoad1 = this.util_Material.idToMaterial(this.PlayerDataConfig.getString(dropChance + ".itemId").toUpperCase().replace(" ", "_"));
                createItem = new ItemStack(fileToLoad1, 1);
                ItemMeta createdItemMeta = createItem.getItemMeta();
                List generateLore = this.setLore(player, playerLevel, mobLevel, entityType, dropChance, fileToLoad1);
                String setPrefix = this.prefix.get(this.PlayerDataConfig, entityType, dropChance);
                String setSuffix = this.suffix.get(this.PlayerDataConfig, entityType, dropChance);
                int selectStatSuffixFromStat;
                if ("Stat".equalsIgnoreCase(this.PlayerDataConfig.getString(dropChance + ".prefix"))) {
                    if (!".".equals(generateLore.get(generateLore.size() - 1))) {
                        selectStatSuffixFromStat = this.randomKeySelection(ItemLoreStats.plugin.getConfig().getStringList(generateLore.get(generateLore.size() - 1) + ".prefix.list").size());
                        setPrefix = this.util_Colours.replaceTooltipColour(ItemLoreStats.plugin.getConfig().getString(generateLore.get(generateLore.size() - 1) + ".prefix.colour")) + ItemLoreStats.plugin.getConfig().getStringList(generateLore.get(generateLore.size() - 1) + ".prefix.list").toString().split(",")[selectStatSuffixFromStat].replaceAll("\\[", "").replaceAll("\\]", "").trim();
                    } else {
                        setPrefix = ".";
                    }
                }

                if ("Stat".equalsIgnoreCase(this.PlayerDataConfig.getString(dropChance + ".suffix"))) {
                    if (!".".equals(generateLore.get(generateLore.size() - 1))) {
                        selectStatSuffixFromStat = this.randomKeySelection(ItemLoreStats.plugin.getConfig().getStringList(generateLore.get(generateLore.size() - 1) + ".suffix.list").size());
                        setSuffix = this.util_Colours.replaceTooltipColour(ItemLoreStats.plugin.getConfig().getString(generateLore.get(generateLore.size() - 1) + ".suffix.colour")) + ItemLoreStats.plugin.getConfig().getStringList(generateLore.get(generateLore.size() - 1) + ".suffix.list").toString().split(",")[selectStatSuffixFromStat].replaceAll("\\[", "").replaceAll("\\]", "").trim();
                    } else {
                        setSuffix = ".";
                    }
                }

                generateLore.remove(generateLore.size() - 1);
                if (setPrefix == ".") {
                    if (setSuffix == ".") {
                        createdItemMeta.setDisplayName("ILS_" + this.util_Colours.replaceTooltipColour(this.rarity.get(Double.parseDouble(dropChance)) + this.materialType.setType(e, createItem)));
                    } else {
                        createdItemMeta.setDisplayName("ILS_" + this.util_Colours.replaceTooltipColour(this.rarity.get(Double.parseDouble(dropChance)) + this.materialType.setType(e, createItem) + " " + setSuffix));
                    }
                } else if (setSuffix == ".") {
                    createdItemMeta.setDisplayName("ILS_" + this.util_Colours.replaceTooltipColour(this.rarity.get(Double.parseDouble(dropChance)) + setPrefix + " " + this.materialType.setType(e, createItem)));
                } else {
                    createdItemMeta.setDisplayName("ILS_" + this.util_Colours.replaceTooltipColour(this.rarity.get(Double.parseDouble(dropChance)) + setPrefix + " " + this.materialType.setType(e, createItem) + " " + setSuffix));
                }

                createdItemMeta.setLore(generateLore);
                createItem.setItemMeta(createdItemMeta);
                return createItem;
            } else {
                String fileToLoad = null;
                fileToLoad = this.PlayerDataConfig.getString(dropChance + ".savedItem");
                this.PlayerDataConfig = new YamlConfiguration();
                this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + fileToLoad + ".yml"));
                createItem = this.PlayerDataConfig.getItemStack("Item");
                return createItem;
            }
        } catch (Exception var14) {
            var14.printStackTrace();
            System.out.println("*********** Failed to create ItemStack for " + entityType + "! ***********");
            return new ItemStack(Material.POTATO);
        }
    }

    public void dropEquippedArmour(LivingEntity mob) {
        if (mob instanceof LivingEntity && !(mob instanceof Player)) {
            mob.getEquipment().setHelmetDropChance((float) (ItemLoreStats.plugin.getConfig().getDouble("npcDropEquippedGear.dropChance.helmet") / 100.0D));
            mob.getEquipment().setChestplateDropChance((float) (ItemLoreStats.plugin.getConfig().getDouble("npcDropEquippedGear.dropChance.chestplate") / 100.0D));
            mob.getEquipment().setLeggingsDropChance((float) (ItemLoreStats.plugin.getConfig().getDouble("npcDropEquippedGear.dropChance.leggings") / 100.0D));
            mob.getEquipment().setBootsDropChance((float) (ItemLoreStats.plugin.getConfig().getDouble("npcDropEquippedGear.dropChance.boots") / 100.0D));
            mob.getEquipment().setItemInHandDropChance((float) (ItemLoreStats.plugin.getConfig().getDouble("npcDropEquippedGear.dropChance.itemInHand") / 100.0D));
        }

    }

    @EventHandler
    public void dropGear(EntityDeathEvent mob) {
        if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(mob.getEntity().getWorld().getName())) {
            this.dropEquippedArmour(mob.getEntity());
            if (!ItemLoreStats.plugin.getConfig().getBoolean("dropCustomILSLoot")) {
                return;
            }

            if (mob.getEntity().getKiller() instanceof Player) {
                int e;
                byte mobLevel;
                int i;
                int selectRandomDrop;
                Zombie skeleton;
                int var12;
                Skeleton var17;
                if (mob.getEntity().hasMetadata("naturalSpawn")) {
                    if ((new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml")).exists()) {
                        try {
                            this.PlayerDataConfig = new YamlConfiguration();
                            if (mob.getEntityType().equals(EntityType.ZOMBIE)) {
                                Zombie playerLevel = (Zombie) mob.getEntity();
                                if (playerLevel.isBaby()) {
                                    this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "baby_zombie.yml"));
                                } else if (playerLevel.isVillager()) {
                                    this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "villager_zombie.yml"));
                                } else {
                                    this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
                                }
                            } else if (mob.getEntityType().equals(EntityType.SKELETON)) {
                                Skeleton var11 = (Skeleton) mob.getEntity();
                                if (var11.getSkeletonType().equals(SkeletonType.WITHER)) {
                                    this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "wither_skeleton.yml"));
                                } else {
                                    this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
                                }
                            } else {
                                this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
                            }

                            var12 = this.random(100);
                            e = mob.getEntity().getKiller().getLevel();
                            mobLevel = 0;

                            for (i = this.PlayerDataConfig.getKeys(false).size() - 1; i >= 0 && i <= this.PlayerDataConfig.getKeys(false).size() - 1; --i) {
                                if (var12 <= Integer.parseInt(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())) {
                                    selectRandomDrop = this.randomKeySelection(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).size());
                                    if (this.PlayerDataConfig.getString(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim() + ".properties.droppedXp") != null) {
                                        mob.setDroppedExp(Integer.parseInt(this.PlayerDataConfig.getString(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim() + ".properties.droppedXp")));
                                    }

                                    if (mob.getEntityType().equals(EntityType.ZOMBIE)) {
                                        skeleton = (Zombie) mob.getEntity();
                                        if (skeleton.isBaby()) {
                                            mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), e, mobLevel, "baby_zombie", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                        } else if (skeleton.isVillager()) {
                                            mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), e, mobLevel, "villager_zombie", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                        } else {
                                            mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), e, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                        }
                                    } else if (mob.getEntityType().equals(EntityType.SKELETON)) {
                                        var17 = (Skeleton) mob.getEntity();
                                        if (var17.getSkeletonType().equals(SkeletonType.WITHER)) {
                                            mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), e, mobLevel, "wither_skeleton", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                        } else {
                                            mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), e, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                        }
                                    } else {
                                        mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), e, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                    }
                                    break;
                                }
                            }
                        } catch (Exception var13) {
                            var13.printStackTrace();
                            System.out.println("*********** Failed to drop gear from " + mob.getEntityType().toString().toLowerCase() + "! ***********");
                        }
                    }
                } else if (mob.getEntity().getCustomName() != null) {
                    if ((new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + this.util_Colours.extractAndReplaceTooltipColour(mob.getEntity().getCustomName()) + ".yml")).exists()) {
                        var12 = mob.getEntity().getKiller().getLevel();
                        String var13 = this.util_Colours.extractAndReplaceTooltipColour(mob.getEntity().getCustomName());

                        try {
                            this.PlayerDataConfig = new YamlConfiguration();
                            this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + var13 + ".yml"));
                            int var16 = this.random(100);

                            for (i = this.PlayerDataConfig.getKeys(false).size() - 1; i >= 0 && i <= this.PlayerDataConfig.getKeys(false).size() - 1; --i) {
                                if (var16 <= Integer.parseInt(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())) {
                                    selectRandomDrop = this.randomKeySelection(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).size());
                                    mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), var12, 0, var13, this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                    if (this.PlayerDataConfig.getString(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim() + ".properties.droppedXp") != null) {
                                        mob.setDroppedExp(Integer.parseInt(this.PlayerDataConfig.getString(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim() + ".properties.droppedXp")));
                                    }
                                    break;
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            System.out.println("*********** Failed to drop gear from " + var13 + "! ***********");
                        }
                    }
                } else if ((new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml")).exists()) {
                    var12 = mob.getEntity().getKiller().getLevel();

                    try {
                        this.PlayerDataConfig = new YamlConfiguration();
                        if (mob.getEntityType().equals(EntityType.ZOMBIE)) {
                            Zombie var14 = (Zombie) mob.getEntity();
                            if (var14.isBaby()) {
                                this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "baby_zombie.yml"));
                            } else if (var14.isVillager()) {
                                this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "villager_zombie.yml"));
                            } else {
                                this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
                            }
                        } else if (mob.getEntityType().equals(EntityType.SKELETON)) {
                            Skeleton var15 = (Skeleton) mob.getEntity();
                            if (var15.getSkeletonType().equals(SkeletonType.WITHER)) {
                                this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "wither_skeleton.yml"));
                            } else {
                                this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
                            }
                        } else {
                            this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
                        }

                        e = this.random(100);
                        mobLevel = 0;

                        for (i = this.PlayerDataConfig.getKeys(false).size() - 1; i >= 0 && i <= this.PlayerDataConfig.getKeys(false).size() - 1; --i) {
                            if (e <= Integer.parseInt(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())) {
                                selectRandomDrop = this.randomKeySelection(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).size());
                                if (this.PlayerDataConfig.getString(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim() + ".properties.droppedXp") != null) {
                                    mob.setDroppedExp(Integer.parseInt(this.PlayerDataConfig.getString(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim() + ".properties.droppedXp")));
                                }

                                if (mob.getEntityType().equals(EntityType.ZOMBIE)) {
                                    skeleton = (Zombie) mob.getEntity();
                                    if (skeleton.isBaby()) {
                                        mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), var12, mobLevel, "baby_zombie", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                    } else if (skeleton.isVillager()) {
                                        mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), var12, mobLevel, "villager_zombie", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                    } else {
                                        mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), var12, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                    }
                                } else if (mob.getEntityType().equals(EntityType.SKELETON)) {
                                    var17 = (Skeleton) mob.getEntity();
                                    if (var17.getSkeletonType().equals(SkeletonType.WITHER)) {
                                        mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), var12, mobLevel, "wither_skeleton", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                    } else {
                                        mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), var12, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                    }
                                } else {
                                    mob.getDrops().add(this.gearGenerator(mob.getEntity().getKiller(), var12, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                                }
                                break;
                            }
                        }
                    } catch (Exception var11) {
                        var11.printStackTrace();
                        System.out.println("*********** Failed to drop gear from " + mob.getEntityType().toString().toLowerCase() + "! ***********");
                    }
                }
            }
        }

    }
}
