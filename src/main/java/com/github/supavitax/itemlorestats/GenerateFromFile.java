package com.github.supavitax.itemlorestats;

import com.github.supavitax.itemlorestats.Util.Util_Colours;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class GenerateFromFile implements Listener {

    Util_Colours util_Colours = new Util_Colours();
    private File PlayerDataFile;
    private FileConfiguration PlayerDataConfig;


    public void exportWeapon(Player player, String WeaponName) {
        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + WeaponName + ".yml");
            ItemStack e = player.getItemInHand();
            if (e != null) {
                if (e.getItemMeta().getLore() != null) {
                    this.PlayerDataConfig.set("Item", e);
                    this.PlayerDataConfig.save(this.PlayerDataFile);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully exported " + ChatColor.GOLD + WeaponName + ChatColor.LIGHT_PURPLE + "!");
                } else {
                    player.sendMessage(ChatColor.RED + "Failed to export: Item in hand doesn\'t contain any lore!");
                }
            }
        } catch (Exception var4) {
            player.sendMessage(ChatColor.RED + "Failed to export: Check console!");
            var4.printStackTrace();
            System.out.println("*********** Item config failed to save! ***********");
        }

    }

    public ItemStack importWeapon(String weaponName, String newWeaponName, String playerName) {
        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + weaponName + ".yml"));
            ItemStack e = this.PlayerDataConfig.getItemStack("Item");
            if (newWeaponName != "noChange") {
                ItemMeta newItemInHandMeta = e.getItemMeta();
                newItemInHandMeta.setDisplayName(this.util_Colours.replaceTooltipColour(newWeaponName));
                e.setItemMeta(newItemInHandMeta);
            }

            if (ItemLoreStats.plugin.getConfig().getBoolean("messages.itemReceived")) {
                Bukkit.getServer().getOfflinePlayer(playerName).getPlayer().sendMessage(Bukkit.getServer().getOfflinePlayer(playerName).getPlayer().getName() + ChatColor.LIGHT_PURPLE + " successfully received " + ChatColor.RESET + e.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + ".");
            }

            return e;
        } catch (Exception var6) {
            Bukkit.getServer().getOfflinePlayer(playerName).getPlayer().sendMessage(ChatColor.RED + "Failed to load: Check console!");
            var6.printStackTrace();
            System.out.println("*********** Item config failed to load! ***********");
            return null;
        }
    }
}
