package com.github.supavitax.itemlorestats.API;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_Format;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SetStats {
    Util_Format util_Format = new Util_Format();

    private int getLineForStat(String searchFor, String itemLore) {
        int line = 0;
        String[] splitLore = itemLore.split(", ");
        String[] var8 = splitLore;
        int var7 = splitLore.length;

        for (int var6 = 0; var6 < var7; ++var6) {
            String getStat = var8[var6];
            if (ChatColor.stripColor(getStat).startsWith(searchFor + ": ")) {
                return line;
            }

            ++line;
        }

        return 1337;
    }

    public void setArmour(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setDodge(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setBlock(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setCritChance(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setCritDamage(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setDamageMin(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name") + ": ").length()).trim().split("-")[0].trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setDamageMax(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name") + ": ").length()).trim().split("-")[1].trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setHealth(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setHealthRegen(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setLifeSteal(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setReflect(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setIce(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setFire(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setPoison(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setWither(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setHarming(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setBlind(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setMovementSpeed(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setXPMultiplier(Player player, ItemStack itemStack, double value) {
        double storeVal = this.util_Format.format(value);
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            if (storeItemLore.contains(ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name"))) {
                int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name"), itemStack.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name") + ": ").length()).trim().replaceAll("[^0-9.]", "");
                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));
                modifyItemMeta.setLore(storeItemLore);
                itemStack.setItemMeta(modifyItemMeta);
                player.updateInventory();
            }
        }

    }

    public void setDurability(Player player, ItemStack itemStack, int percentage) {
        double storeVal = percentage;
        if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            List storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();
            int loreLineNumb = this.getLineForStat(ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name"), itemStack.getItemMeta().getLore().toString());
            String loreLineStat = (String) storeItemLore.get(loreLineNumb);
            String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name") + ": ").length()).trim().split("/")[0]).replaceAll("[^0-9.]", "");
            double maxDur = Integer.parseInt(String.valueOf(ChatColor.stripColor(loreLineStat).substring((ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name") + ": ").length()).trim().split("/")[1]).replaceAll("[^0-9.]", ""));
            storeVal = maxDur / 100.0D * (double) percentage;
            storeItemLore.set(loreLineNumb, loreLineStat.replaceFirst(currentStatVal, String.valueOf(Math.round(storeVal))));
            modifyItemMeta.setLore(storeItemLore);
            itemStack.setItemMeta(modifyItemMeta);
            player.updateInventory();
        }

    }
}
