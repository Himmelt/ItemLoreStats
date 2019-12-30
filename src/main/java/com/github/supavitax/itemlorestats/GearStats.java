package com.github.supavitax.itemlorestats;

import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_Format;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import com.github.supavitax.itemlorestats.Util.Util_RPGInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GearStats implements Listener {
    Util_Colours util_Colours = new Util_Colours();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_RPGInventory util_RPGInventory = new Util_RPGInventory();
    GetSlots getSlots = new GetSlots();
    String armour = null;
    String dodge = null;
    String block = null;
    String critChance = null;
    String critDamage = null;
    String damage = null;
    String health = null;
    String healthRegen = null;
    String lifeSteal = null;
    String lifeStealHeal = null;
    String reflect = null;
    String ice = null;
    String fire = null;
    String poison = null;
    String wither = null;
    String harming = null;
    String blind = null;
    String movementspeed = null;
    String weight = null;
    String weaponspeed = null;
    String xpmultiplier = null;
    String pvpdamage = null;
    String pvedamage = null;
    String setbonus = null;
    String xplevel = null;
    String className = null;
    String soulbound = null;
    String durability = null;
    String sellValueName = null;
    String currencyName = null;
    String clickToCast = null;
    String tnt = null;
    String fireball = null;
    String lightning = null;
    String frostbolt = null;
    String minorHeal = null;
    String majorHeal = null;
    String minorAOEHeal = null;
    String majorAOEHeal = null;
    String heroes_MaxMana = null;
    String skillAPI_MaxMana = null;
    String languageRegex = "[^a-zA-Z一-龥\u0080-ÿĀ-ſƀ-ɏ가-힣Ѐ-ҁ]";

    private static final Method cantUseStack;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[+]?\\d+(\\.\\d+)?");

    static {
        Method method = null;

        try {
            method = Class.forName("me.cilio.lvlitem.LevelItem").getDeclaredMethod("cantUseStack", Integer.TYPE, ItemStack.class);
            method.setAccessible(true);
        } catch (Throwable ignored) {
        }

        cantUseStack = method;
    }

    private double getDouble(LivingEntity entity, String format) {
        if (entity == null) {
            return 0.0D;
        }
        double value = 0.0D;
        for (Map.Entry<Integer, ItemStack> entry : getStacks(entity).entrySet()) {
            double temp = 0.0D;
            ItemStack stack = entry.getValue();
            if (stack != null && stack.hasItemMeta() && stack.getItemMeta().hasLore()) {
                List<String> list = stack.getItemMeta().getLore();
                boolean lvlOK = false;
                if (entity instanceof Player) {
                    if (cantUseStack != null) {
                        try {
                            lvlOK = !(Boolean) cantUseStack.invoke(((Player) entity).getLevel(), stack);
                        } catch (Throwable ignored) {
                            lvlOK = true;
                        }
                    } else {
                        lvlOK = true;
                    }
                    lvlOK = lvlOK && ((Player) entity).getLevel() >= getXPLevelRequirement((Player) entity, stack);
                }
                for (String line : list) {
                    String lore = ChatColor.stripColor(line);
                    if (lvlOK && lore.replaceAll(this.languageRegex, "").matches(format)) {
                        temp += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
            value += temp;
        }
        final String baubleName = ItemLoreStats.getBaubleManager().getBaubleName();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            for (Map.Entry<Integer, ItemStack> entry : ItemLoreStats.getBaubleManager().getBaubles(player).entrySet()) {
                double temp = 0.0D;
                int slot = entry.getKey();
                String type = ItemLoreStats.getBaubleManager().getMark(slot);
                ItemStack stack = entry.getValue();
                if (stack != null && stack.hasItemMeta() && stack.getItemMeta().hasLore()) {
                    List<String> list = stack.getItemMeta().getLore();
                    boolean lvlOK;
                    if (cantUseStack != null) {
                        try {
                            lvlOK = !(Boolean) cantUseStack.invoke(player.getLevel(), stack);
                        } catch (Throwable ignored) {
                            lvlOK = true;
                        }
                    } else {
                        lvlOK = true;
                    }
                    lvlOK = lvlOK && (player.getLevel() >= getXPLevelRequirement(player, stack));
                    if (lvlOK) {
                        boolean valid = false;
                        for (String line : list) {
                            line = ChatColor.stripColor(line);
                            if (line.contains(baubleName) && line.contains(type)) {
                                valid = true;
                            }
                            Matcher matcher = NUMBER_PATTERN.matcher(line);
                            if (valid && line.contains(format) && matcher.find()) {
                                temp += Double.parseDouble(matcher.group());
                                break;
                            }
                        }
                    }
                }
                value += temp;
            }
        }
        return value;
    }

    private String getRange(LivingEntity entity, String format) {
        if (entity == null) {
            return "0.0-0.0";
        }
        double minValue = 0.0D;
        double maxValue = 0.0D;
        for (Map.Entry<Integer, ItemStack> entry : getStacks(entity).entrySet()) {
            double min = 0.0D;
            double max = 0.0D;
            ItemStack stack = entry.getValue();
            if (stack != null && stack.hasItemMeta() && stack.getItemMeta().hasLore()) {
                List<String> list = stack.getItemMeta().getLore();
                boolean lvlOK = false;
                if (entity instanceof Player) {
                    if (cantUseStack != null) {
                        try {
                            lvlOK = !(Boolean) cantUseStack.invoke(((Player) entity).getLevel(), stack);
                        } catch (Throwable ignored) {
                            lvlOK = true;
                        }
                    } else {
                        lvlOK = true;
                    }
                    lvlOK = lvlOK && ((Player) entity).getLevel() >= getXPLevelRequirement((Player) entity, stack);
                }
                for (String line : list) {
                    String lore = ChatColor.stripColor(line);
                    if (lvlOK && lore.replaceAll(this.languageRegex, "").matches(format)) {
                        if (lore.contains("-")) {
                            min += Double.parseDouble(lore.split("-")[0].replaceAll("[^0-9.+-]", ""));
                            max += Double.parseDouble(lore.split("-")[1].replaceAll("[^0-9.+-]", ""));
                        } else {
                            min += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                            max += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                        }
                    }
                }
            }
            minValue += min;
            maxValue += max;
        }
        final String baubleName = ItemLoreStats.getBaubleManager().getBaubleName();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            for (Map.Entry<Integer, ItemStack> entry : ItemLoreStats.getBaubleManager().getBaubles(player).entrySet()) {
                double min = 0.0D;
                double max = 0.0D;
                int slot = entry.getKey();
                String type = ItemLoreStats.getBaubleManager().getMark(slot);
                ItemStack stack = entry.getValue();
                if (stack != null && stack.hasItemMeta() && stack.getItemMeta().hasLore()) {
                    List<String> list = stack.getItemMeta().getLore();
                    boolean lvlOK = false;
                    if (cantUseStack != null) {
                        try {
                            lvlOK = !(Boolean) cantUseStack.invoke(player, stack);
                        } catch (Throwable ignored) {
                            lvlOK = true;
                        }
                    } else {
                        lvlOK = true;
                    }
                    lvlOK = lvlOK && (player.getLevel() >= getXPLevelRequirement(player, stack));

                    if (lvlOK) {
                        boolean valid = false;
                        for (String line : list) {
                            line = ChatColor.stripColor(line);
                            if (line.contains(baubleName) && line.contains(type)) {
                                valid = true;
                            }
                            Matcher matcher = NUMBER_PATTERN.matcher(line);
                            if (valid && line.contains(format) && matcher.find()) {
                                String left = matcher.group();
                                min += Double.parseDouble(left);
                                if (matcher.find()) {
                                    String right = matcher.group();
                                    max += Double.parseDouble(right);
                                } else {
                                    max += Double.parseDouble(left);
                                }
                                break;
                            }
                        }
                    }
                }
                minValue += min;
                maxValue += max;
            }
        }
        return minValue + "-" + maxValue;
    }

    private HashMap<Integer, ItemStack> getStacks(LivingEntity entity) {
        HashMap<Integer, ItemStack> stacks = new HashMap<>();
        EntityEquipment equip = entity.getEquipment();
        stacks.put(-1, equip.getHelmet());
        stacks.put(-2, equip.getChestplate());
        stacks.put(-3, equip.getLeggings());
        stacks.put(-4, equip.getBoots());
        return stacks;
    }

    public double getArmourGear(LivingEntity entity) {
        this.armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.armour));
    }

    public double getDodgeGear(LivingEntity entity) {
        this.dodge = ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.dodge));
    }

    public double getBlockGear(LivingEntity entity) {
        this.block = ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.block));
    }

    public String getDamageGear(LivingEntity entity) {
        this.damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name").replaceAll(" ", "");
        return this.getRange(entity, this.damage);
    }

    public double getCritChanceGear(LivingEntity entity) {
        this.critChance = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.critChance));
    }

    public double getCritDamageGear(LivingEntity entity) {
        this.critDamage = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.critDamage));
    }

    public double getHealthGear(LivingEntity entity) {
        this.health = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.health));
    }

    public double getHealthRegenGear(LivingEntity entity) {
        this.healthRegen = ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.healthRegen));
    }

    public double getLifeStealGear(LivingEntity entity) {
        this.lifeSteal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.lifeSteal));
    }

    public double getLifeStealHealGear(LivingEntity entity) {
        this.lifeStealHeal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeStealHeal.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.lifeStealHeal));
    }

    public double getReflectGear(LivingEntity entity) {
        this.reflect = ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.reflect));
    }

    public double getFireGear(LivingEntity entity) {
        this.fire = ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.fire));
    }

    public double getIceGear(LivingEntity entity) {
        this.ice = ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.ice));
    }

    public double getPoisonGear(LivingEntity entity) {
        this.poison = ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.poison));
    }

    public double getWitherGear(LivingEntity entity) {
        this.wither = ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.wither));
    }

    public double getHarmingGear(LivingEntity entity) {
        this.harming = ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.harming));
    }

    public double getBlindGear(LivingEntity entity) {
        this.blind = ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(entity, this.blind));
    }

    public double getMovementSpeedGear(Player player) {
        this.movementspeed = ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(player, this.movementspeed));
    }

    public double getXPMultiplierGear(Player player) {
        this.xpmultiplier = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name").replaceAll(" ", "");
        return this.util_Format.format(this.getDouble(player, this.xpmultiplier));
    }

    public double get_MaxManaGear(Player player) {
        String mana = null;
        if (ItemLoreStats.plugin.getHeroes() != null) {
            this.heroes_MaxMana = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name").replaceAll(" ", "");
            mana = this.heroes_MaxMana;
        } else if (ItemLoreStats.plugin.getSkillAPI() != null) {
            this.skillAPI_MaxMana = ItemLoreStats.plugin.getConfig().getString("skillAPIOnlyStats.skillAPIMaxMana.name").replaceAll(" ", "");
            mana = this.skillAPI_MaxMana;
        }
        return this.util_Format.format(getDouble(player, mana));
    }

    /**********************************************************************/

    private double getHandDouble(ItemStack stack, String format) {
        double value = 0.0D;
        if (stack != null && stack.hasItemMeta() && stack.getItemMeta().hasLore()) {
            List<String> list = stack.getItemMeta().getLore();
            for (String line : list) {
                String lore = ChatColor.stripColor(line);
                if (lore.replaceAll(this.languageRegex, "").matches(format)) {
                    value += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }
        return value;
    }

    public double getArmourItemInHand(ItemStack item) {
        this.armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.armour));
    }

    public double getDodgeItemInHand(ItemStack item) {
        this.dodge = ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.dodge));
    }

    public double getBlockItemInHand(ItemStack item) {
        this.block = ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.block));
    }

    public String getDamageItemInHand(ItemStack item) {
        this.damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name").replaceAll(" ", "");
        double damageMinValue = 0.0D;
        double damageMaxValue = 0.0D;
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> itemLore = item.getItemMeta().getLore();
            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line);
                if (lore.replaceAll(this.languageRegex, "").matches(this.damage)) {
                    if (lore.contains("-")) {
                        damageMinValue += Double.parseDouble(lore.split("-")[0].replaceAll("[^0-9.+-]", ""));
                        damageMaxValue += Double.parseDouble(lore.split("-")[1].replaceAll("[^0-9.+-]", ""));
                    } else {
                        damageMinValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                        damageMaxValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }
        return damageMinValue + "-" + damageMaxValue;
    }

    public double getCritChanceItemInHand(ItemStack item) {
        this.critChance = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.critChance));
    }

    public double getCritDamageItemInHand(ItemStack item) {
        this.critDamage = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.critDamage));
    }

    public double getHealthItemInHand(ItemStack item) {
        this.health = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.health));
    }

    public double getHealthRegenItemInHand(ItemStack item) {
        this.healthRegen = ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.healthRegen));
    }

    public double getLifeStealItemInHand(ItemStack item) {
        this.lifeSteal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.lifeSteal));
    }

    public double getLifeStealHealItemInHand(ItemStack item) {
        this.lifeStealHeal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeStealHeal.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.lifeStealHeal));
    }

    public double getReflectItemInHand(ItemStack item) {
        this.reflect = ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.reflect));
    }

    public double getIceItemInHand(ItemStack item) {
        this.ice = ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.ice));
    }

    public double getFireItemInHand(ItemStack item) {
        this.fire = ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.fire));
    }

    public double getPoisonItemInHand(ItemStack item) {
        this.poison = ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.poison));
    }

    public double getWitherItemInHand(ItemStack item) {
        this.wither = ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.wither));
    }

    public double getHarmingItemInHand(ItemStack item) {
        this.harming = ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.harming));
    }

    public double getBlindItemInHand(ItemStack item) {
        this.blind = ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.blind));
    }

    public double getMovementSpeedItemInHand(ItemStack item) {
        this.movementspeed = ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.movementspeed));
    }

    public double getXPMultiplierItemInHand(ItemStack item) {
        this.xpmultiplier = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.xpmultiplier));
    }

    public double getPvPDamageModifierItemInHand(ItemStack item) {
        this.pvpdamage = ItemLoreStats.plugin.getConfig().getString("bonusStats.pvpDamage.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.pvpdamage));
    }

    public double getPvEDamageModifierItemInHand(ItemStack item) {
        this.pvedamage = ItemLoreStats.plugin.getConfig().getString("bonusStats.pveDamage.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(item, this.pvedamage));
    }

    public int getSellValueItemInHand(ItemStack item) {
        this.sellValueName = ItemLoreStats.plugin.getConfig().getString("bonusStats.sellValue.name").replaceAll(" ", "");
        this.currencyName = ItemLoreStats.plugin.getConfig().getString("bonusStats.sellValue.currency.name").replaceAll(" ", "");
        int value = 0;
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> list = item.getItemMeta().getLore();
            for (String line : list) {
                String lore = ChatColor.stripColor(line);
                if (lore.replaceAll(this.languageRegex, "").matches(this.sellValueName + this.currencyName)) {
                    value += Integer.parseInt(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }
        return value;
    }

    public double get_MaxManaItemInHandX(ItemStack item) {
        String mana = null;
        if (ItemLoreStats.plugin.getHeroes() != null) {
            this.heroes_MaxMana = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name").replaceAll(" ", "");
            mana = this.heroes_MaxMana;
        } else if (ItemLoreStats.plugin.getSkillAPI() != null) {
            this.skillAPI_MaxMana = ItemLoreStats.plugin.getConfig().getString("skillAPIOnlyStats.skillAPIMaxMana.name").replaceAll(" ", "");
            mana = this.skillAPI_MaxMana;
        }

        return this.util_Format.format(this.getHandDouble(item, mana));
    }

    public int getXPLevelRequirement(Player player, ItemStack item) {
        this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name").replaceAll(" ", "");
        byte itemXPLevel = 0;
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line);
                if (lore.replaceAll(this.languageRegex, "").matches(this.xplevel)) {
                    return Integer.parseInt(lore.split("\\+")[0].replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return itemXPLevel;
    }

    public int getXPLevelRequirementItemOnPickup(ItemStack itemOnPickup) {
        this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name").replaceAll(" ", "");
        byte itemInHandValue = 0;
        if (itemOnPickup != null && itemOnPickup.hasItemMeta() && itemOnPickup.getItemMeta().hasLore()) {
            List<String> itemLore = itemOnPickup.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line);
                if (lore.replaceAll(this.languageRegex, "").matches(this.xplevel)) {
                    return Integer.parseInt(lore.split("\\+")[0].replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return itemInHandValue;
    }

    public String getSoulboundName(Player player, ItemStack item) {
        this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
        String storeLoreValues = "";
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List itemLore = item.getItemMeta().getLore();

            for (Object o : itemLore) {
                String line = (String) o;
                if (ChatColor.stripColor(line).startsWith(this.soulbound)) {
                    return ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
                }
            }
        }

        return storeLoreValues;
    }

    public String getSoulboundNameItemOnPickup(ItemStack itemOnPickup) {
        this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
        String storeLoreValues = "";
        if (itemOnPickup != null && itemOnPickup.hasItemMeta() && itemOnPickup.getItemMeta().hasLore()) {
            List itemLore = itemOnPickup.getItemMeta().getLore();

            for (Object o : itemLore) {
                String line = (String) o;
                if (ChatColor.stripColor(line).startsWith(this.soulbound)) {
                    return ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
                }
            }
        }

        return storeLoreValues;
    }

    public ArrayList<String> getClass(ItemStack item) {
        this.className = ItemLoreStats.plugin.getConfig().getString("bonusStats.class.name").replaceAll(" ", "");
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> itemLore = item.getItemMeta().getLore();
            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(this.className + ": ")) {
                    String value = ChatColor.stripColor(line).substring((this.className + ": ").length()).trim();
                    return new ArrayList<>(Arrays.asList(value.split(ItemLoreStats.plugin.getConfig().getString("bonusStats.class.separator"))));
                }
            }
        }

        return null;
    }

    public String phic_SetBonusItemInHand(ItemStack itemstack) {
        this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name").replaceAll(" ", "");
        String weaponKey = "";
        if (itemstack != null && itemstack.hasItemMeta() && ItemLoreStats.plugin.isTool(itemstack.getType()) && itemstack.getItemMeta().hasLore()) {
            List<String> itemInHandLore = itemstack.getItemMeta().getLore();
            for (String line : itemInHandLore) {
                if (line.contains(this.setbonus)) {
                    return this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
                }
            }
        }

        return weaponKey;
    }

    public String phic_SoulboundNameItemInHand(ItemStack itemstack) {
        this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
        String storeLoreValues = "";
        if (itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasLore()) {
            List<String> itemLore = itemstack.getItemMeta().getLore();

            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(this.soulbound)) {
                    return ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
                }
            }
        }

        return null;
    }

    public String phic_ClassItemInHand(ItemStack itemstack) {
        this.className = ItemLoreStats.plugin.getConfig().getString("bonusStats.class.name").replaceAll(" ", "");
        String storeLoreValues = "";
        if (itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasLore()) {
            List<String> itemLore = itemstack.getItemMeta().getLore();

            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(this.className + ": ")) {
                    return ChatColor.stripColor(line).substring((this.className + ": ").length()).trim();
                }
            }
        }

        return null;
    }

    public int phic_XPLevelRequirementItemInHand(ItemStack itemstack) {
        this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name").replaceAll(" ", "");
        boolean storeLoreValues = false;
        if (itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasLore()) {
            List<String> itemInHandLore = itemstack.getItemMeta().getLore();

            for (String line : itemInHandLore) {
                if (ChatColor.stripColor(line).startsWith(this.xplevel + ": ")) {
                    String xpLevelValue = ChatColor.stripColor(line).substring((this.xplevel + ": ").length()).trim();

                    try {
                        int storeLoreValues1;
                        if (xpLevelValue.contains("[+")) {
                            storeLoreValues1 = Integer.parseInt(xpLevelValue.split(" ")[0]);
                        } else {
                            storeLoreValues1 = Integer.parseInt(xpLevelValue);
                        }

                        return storeLoreValues1;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return 0;
    }

    public String getSpellName(ItemStack item) {
        String castString = ChatColor.stripColor(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "").replaceAll("&([0-9a-f])", ""));
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> itemLore = item.getItemMeta().getLore();
            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(castString)) {
                    return ChatColor.stripColor(line).substring(castString.length()).trim();
                }
            }
        }
        return null;
    }

    public void containsSetBonus() {
    }

    /**************************************************************************/

    public double getArmourItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.armour));
    }

    public double getDodgeItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.dodge = ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.dodge));
    }

    public double getBlockItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.block = ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.block));
    }

    public String getDamageItemInHand(LivingEntity entity) {
        this.damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name").replaceAll(" ", "");
        double damageMinValue = 0.0D;
        double damageMaxValue = 0.0D;
        ItemStack gear = entity.getEquipment().getItemInHand();
        if (gear != null && gear.hasItemMeta() && gear.getItemMeta().hasLore()) {
            List<String> itemLore = gear.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line);
                if (lore.replaceAll(this.languageRegex, "").matches(this.damage)) {
                    if (lore.contains("-")) {
                        damageMinValue += Double.parseDouble(lore.split("-")[0].replaceAll("[^0-9.+-]", ""));
                        damageMaxValue += Double.parseDouble(lore.split("-")[1].replaceAll("[^0-9.+-]", ""));
                    } else {
                        damageMinValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                        damageMaxValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }

        return damageMinValue + "-" + damageMaxValue;
    }

    public double getCritChanceItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.critChance = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.critChance));
    }

    public double getCritDamageItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.critDamage = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.critDamage));
    }

    public double getHealthItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.health = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.health));
    }

    public double getHealthRegenItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.healthRegen = ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.healthRegen));
    }

    public double getLifeStealItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.lifeSteal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.lifeSteal));
    }

    public double getLifeStealHealItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.lifeStealHeal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeStealHeal.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.lifeStealHeal));
    }

    public double getReflectItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.reflect = ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.reflect));
    }

    public double getIceItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.ice = ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.ice));
    }

    public double getFireItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.fire = ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.fire));
    }

    public double getPoisonItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.poison = ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.poison));
    }

    public double getWitherItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.wither = ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.wither));
    }

    public double getHarmingItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.harming = ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.harming));
    }

    public double getBlindItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.blind = ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.blind));
    }

    public double getMovementSpeedItemInHand(Player player) {
        if (player == null) {
            return 0.0D;
        }
        this.movementspeed = ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(player.getEquipment().getItemInHand(), this.movementspeed));
    }

    public double getXPMultiplierItemInHand(Player player) {
        if (player == null) {
            return 0.0D;
        }
        this.xpmultiplier = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(player.getEquipment().getItemInHand(), this.xpmultiplier));
    }

    public double getPvPDamageModifierItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.pvpdamage = ItemLoreStats.plugin.getConfig().getString("bonusStats.pvpDamage.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.pvpdamage));
    }

    public double getPvEDamageModifierItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        this.pvedamage = ItemLoreStats.plugin.getConfig().getString("bonusStats.pveDamage.name").replaceAll(" ", "");
        return this.util_Format.format(this.getHandDouble(entity.getEquipment().getItemInHand(), this.pvedamage));
    }

    public int getSellValueItemInHand(Player player) {
        if (player == null) {
            return 0;
        }
        this.sellValueName = ItemLoreStats.plugin.getConfig().getString("bonusStats.sellValue.name").replaceAll(" ", "");
        this.currencyName = ItemLoreStats.plugin.getConfig().getString("bonusStats.sellValue.currency.name").replaceAll(" ", "");
        int value = 0;
        ItemStack stack = player.getEquipment().getItemInHand();
        if (stack != null && stack.hasItemMeta() && stack.getItemMeta().hasLore()) {
            List<String> list = stack.getItemMeta().getLore();
            for (String line : list) {
                String lore = ChatColor.stripColor(line);
                if (lore.replaceAll(this.languageRegex, "").matches(this.sellValueName + this.currencyName)) {
                    value += Integer.parseInt(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }
        return value;
    }

    public double get_MaxManaItemInHand(LivingEntity entity) {
        if (entity == null) {
            return 0.0D;
        }
        String mana = null;
        if (ItemLoreStats.plugin.getHeroes() != null) {
            this.heroes_MaxMana = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name").replaceAll(" ", "");
            mana = this.heroes_MaxMana;
        } else if (ItemLoreStats.plugin.getSkillAPI() != null) {
            this.skillAPI_MaxMana = ItemLoreStats.plugin.getConfig().getString("skillAPIOnlyStats.skillAPIMaxMana.name").replaceAll(" ", "");
            mana = this.skillAPI_MaxMana;
        }
        return this.util_Format.format(getHandDouble(entity.getEquipment().getItemInHand(), mana));
    }

    public String getSwingSpeedItemInHand(LivingEntity entity) {
        this.weaponspeed = ItemLoreStats.plugin.getConfig().getString("bonusStats.weaponSpeed.name").replaceAll(" ", "");
        StringBuilder builder = new StringBuilder("Normal");
        ItemStack gear = entity.getEquipment().getItemInHand();
        if (gear != null && gear.hasItemMeta() && gear.getItemMeta().hasLore()) {
            List<String> itemLore = gear.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line);
                if (lore.replaceAll(this.languageRegex, "").matches(this.weaponspeed)) {
                    builder.append(Integer.parseInt(lore.replaceAll("Very Slow", "").replaceAll("Slow", "").replaceAll("Normal", "").replaceAll("Fast", "").replaceAll("Very Fast", "").replaceAll("[^0-9.+-]", "")));
                }
            }
        }

        return builder.toString();
    }

    public String getSetBonusHead(Player player) {
        this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name").replaceAll(" ", "");
        String headKey = "";
        ItemStack head = player.getInventory().getHelmet();
        if (head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
            List<String> headLore = head.getItemMeta().getLore();

            for (String line : headLore) {
                if (line.contains(this.setbonus)) {
                    return this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
                }
            }
        }

        return headKey;
    }

    public String getSetBonusChest(Player player) {
        this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name").replaceAll(" ", "");
        String chestKey = "";
        ItemStack chest = player.getInventory().getChestplate();
        if (chest != null && chest.hasItemMeta() && chest.getItemMeta().hasLore()) {
            List<String> chestLore = chest.getItemMeta().getLore();

            for (String line : chestLore) {
                if (line.contains(this.setbonus)) {
                    return this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
                }
            }
        }

        return chestKey;
    }

    public String getSetBonusLegs(Player player) {
        this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name").replaceAll(" ", "");
        String legsKey = "";
        ItemStack legs = player.getInventory().getLeggings();
        if (legs != null && legs.hasItemMeta() && legs.getItemMeta().hasLore()) {
            List<String> legsLore = legs.getItemMeta().getLore();

            for (String line : legsLore) {
                if (line.contains(this.setbonus)) {
                    return this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
                }
            }
        }

        return legsKey;
    }

    public String getSetBonusBoots(Player player) {
        this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name").replaceAll(" ", "");
        String bootsKey = "";
        ItemStack boots = player.getInventory().getBoots();
        if (boots != null && boots.hasItemMeta() && boots.getItemMeta().hasLore()) {
            List<String> bootsLore = boots.getItemMeta().getLore();

            for (String line : bootsLore) {
                if (line.contains(this.setbonus)) {
                    return this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
                }
            }
        }

        return bootsKey;
    }

    public String getSetBonusItemInHand(Player player) {
        this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name").replaceAll(" ", "");
        String weaponKey = "";
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand != null && itemInHand.hasItemMeta() && ItemLoreStats.plugin.isTool(itemInHand.getType()) && itemInHand.getItemMeta().hasLore()) {
            List<String> itemInHandLore = itemInHand.getItemMeta().getLore();

            for (String line : itemInHandLore) {
                if (line.contains(this.setbonus)) {
                    return this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
                }
            }
        }

        return weaponKey;
    }

    public String playerHeldItemChangeSetBonusItemInHand(ItemStack itemstack) {
        this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name").replaceAll(" ", "");
        String weaponKey = "";
        if (itemstack != null && itemstack.hasItemMeta() && ItemLoreStats.plugin.isTool(itemstack.getType()) && itemstack.getItemMeta().hasLore()) {
            List<String> itemInHandLore = itemstack.getItemMeta().getLore();

            for (String line : itemInHandLore) {
                if (line.contains(this.setbonus)) {
                    return this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
                }
            }
        }

        return weaponKey;
    }

    public String playerHeldItemChangeSoulboundNameItemInHand(ItemStack itemstack) {
        this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
        String storeLoreValues = "";
        if (itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasLore()) {
            List<String> itemLore = itemstack.getItemMeta().getLore();

            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(this.soulbound)) {
                    return ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
                }
            }
        }

        return null;
    }

    public String playerHeldItemChangeClassItemInHand(ItemStack itemstack) {
        this.className = ItemLoreStats.plugin.getConfig().getString("bonusStats.class.name").replaceAll(" ", "");
        String storeLoreValues = "";
        if (itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasLore()) {
            List<String> itemLore = itemstack.getItemMeta().getLore();

            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(this.className + ": ")) {
                    return ChatColor.stripColor(line).substring((this.className + ": ").length()).trim();
                }
            }
        }

        return null;
    }

    public int playerHeldItemChangeXPLevelRequirementItemInHand(ItemStack itemstack) {
        this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name").replaceAll(" ", "");
        boolean storeLoreValues = false;
        if (itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasLore()) {
            List<String> itemInHandLore = itemstack.getItemMeta().getLore();

            for (String line : itemInHandLore) {
                if (ChatColor.stripColor(line).startsWith(this.xplevel + ": ")) {
                    String xpLevelValue = ChatColor.stripColor(line).substring((this.xplevel + ": ").length()).trim();

                    try {
                        int value;
                        if (xpLevelValue.contains("[+")) {
                            value = Integer.parseInt(xpLevelValue.split(" ")[0]);
                        } else {
                            value = Integer.parseInt(xpLevelValue);
                        }
                        return value;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }

        return 0;
    }
}
