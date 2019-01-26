package com.github.supavitax.itemlorestats.ItemUpgrading;

import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_Format;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUpgrade {

   GearStats gearStats = new GearStats();
   Util_Colours util_Colours = new Util_Colours();
   Util_Format util_Format = new Util_Format();
   Util_GetResponse util_GetResponse = new Util_GetResponse();
   String languageRegex = "[^a-zA-Z一-龥-ÿĀ-ſƀ-ɏ]";


   public void increaseItemStatOnItemInHand(Player player) {
      if(!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
         String damageName = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
         String healthName = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name");
         String heroesManaName = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
         String levelName = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
         ItemStack item = player.getItemInHand();
         if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List itemLore = item.getItemMeta().getLore();
            ItemMeta itemMeta = item.getItemMeta();
            int globalIndex = 0;
            if(!ChatColor.stripColor(itemLore.toString()).contains(levelName)) {
               return;
            }

            String levelStatIndex = ChatColor.stripColor(itemLore.toString()).substring(ChatColor.stripColor(itemLore.toString()).indexOf(levelName)).split(",")[0];
            boolean upgrades = false;
            int var27;
            if(levelStatIndex.contains("[+")) {
               var27 = Integer.parseInt(levelStatIndex.split("\\+")[1].split("\\]")[0].replaceAll("\\[", "").replaceAll("\\]", "")) + 1;
            } else {
               var27 = 1;
            }

            if(ItemLoreStats.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap") == 0) {
               return;
            }

            if(var27 > ItemLoreStats.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap")) {
               if(ItemLoreStats.plugin.getConfig().getBoolean("messages.upgradeCapReached")) {
                  player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeCapReached", player, player, player.getItemInHand().getItemMeta().getDisplayName(), player.getItemInHand().getItemMeta().getDisplayName()));
               }

               return;
            }

            Iterator var13 = itemLore.iterator();

            while(var13.hasNext()) {
               String line = (String)var13.next();
               if(globalIndex < itemLore.size()) {
                  ++globalIndex;

                  for(int i = 0; i < ItemLoreStats.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).size(); ++i) {
                     String key = ItemLoreStats.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).toString().split(",")[i].trim().replaceAll("&([0-9a-f])", "").replaceAll("\\[", "").replaceAll("\\]", "");
                     String statName = "";
                     if(ItemLoreStats.plugin.getConfig().getString("primaryStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("primaryStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("secondaryStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("secondaryStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("bonusStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("bonusStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats." + key + ".name");
                     }

                     statName = statName.replaceAll(" ", "");
                     String lore = ChatColor.stripColor(line.toString());
                     String getLine;
                     double upgradeNumbers;
                     double var28;
                     if(lore.replaceAll(this.languageRegex, "").matches(statName) && lore.contains("%")) {
                        var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                        upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                        getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                        itemLore.set(globalIndex - 1, getLine);
                     } else if(lore.startsWith(levelName)) {
                        if(lore.contains("[+") && lore.contains("]")) {
                           int getNumbers = Integer.parseInt(ChatColor.stripColor(String.valueOf(line.split("\\+")[1].split("\\]")[0])).replaceAll("[^0-9.]", "").trim());
                           getLine = line.replaceAll("\\+" + getNumbers, "\\+" + (getNumbers + 1));
                           itemLore.set(globalIndex - 1, getLine);
                        } else {
                           itemLore.set(globalIndex - 1, line + ChatColor.DARK_GREEN + " [" + ChatColor.RED + "+1" + ChatColor.DARK_GREEN + "]");
                        }
                     } else if(!lore.replaceAll(this.languageRegex, "").matches(healthName) && !lore.replaceAll(this.languageRegex, "").matches(heroesManaName)) {
                        if(lore.replaceAll(this.languageRegex, "").matches(damageName) && !lore.contains("%")) {
                           if(lore.contains("-")) {
                              var28 = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[0].replaceAll("[^0-9.]", "").trim());
                              upgradeNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[1].replaceAll("[^0-9.]", "").trim());
                              double upgradeMinNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats.damage");
                              double upgradeMaxNumbers = upgradeNumbers + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats.damage");
                              getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeMinNumbers)));
                              getLine = getLine.replaceAll(String.valueOf(upgradeNumbers), String.valueOf(this.util_Format.format(upgradeMaxNumbers)));
                              itemLore.set(globalIndex - 1, getLine);
                           } else {
                              var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                              upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats.damage");
                              getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                              itemLore.set(globalIndex - 1, getLine);
                           }
                        }
                     } else {
                        var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                        upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                        getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                        itemLore.set(globalIndex - 1, getLine);
                     }
                  }
               }
            }

            itemMeta.setLore(itemLore);
            item.setItemMeta(itemMeta);
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(item));
            if(ItemLoreStats.plugin.getConfig().getBoolean("messages.upgradeSuccessful")) {
               player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeSuccessful", player, player, player.getItemInHand().getItemMeta().getDisplayName(), player.getItemInHand().getItemMeta().getDisplayName()));
            }
         }
      }

   }

   public void increaseItemStatOnHelmet(Player player) {
      if(!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
         String damageName = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
         String healthName = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name");
         String heroesManaName = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
         String levelName = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
         ItemStack item = player.getInventory().getHelmet();
         if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List itemLore = item.getItemMeta().getLore();
            ItemMeta itemMeta = item.getItemMeta();
            int globalIndex = 0;
            if(!ChatColor.stripColor(itemLore.toString()).contains(levelName)) {
               return;
            }

            String levelStatIndex = ChatColor.stripColor(itemLore.toString()).substring(ChatColor.stripColor(itemLore.toString()).indexOf(levelName)).split(",")[0].trim();
            boolean upgrades = false;
            int var27;
            if(levelStatIndex.contains("[+")) {
               var27 = Integer.parseInt(levelStatIndex.split("\\+")[1].split("\\]")[0].replaceAll("\\[", "").replaceAll("\\]", "").trim()) + 1;
            } else {
               var27 = 1;
            }

            if(ItemLoreStats.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap") == 0) {
               return;
            }

            if(var27 > ItemLoreStats.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap")) {
               if(ItemLoreStats.plugin.getConfig().getBoolean("messages.upgradeCapReached")) {
                  player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeCapReached", player, player, player.getItemInHand().getItemMeta().getDisplayName(), player.getItemInHand().getItemMeta().getDisplayName()));
               }

               return;
            }

            Iterator var13 = itemLore.iterator();

            while(var13.hasNext()) {
               String line = (String)var13.next();
               if(globalIndex < itemLore.size()) {
                  ++globalIndex;

                  for(int i = 0; i < ItemLoreStats.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).size(); ++i) {
                     String key = ItemLoreStats.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).toString().split(",")[i].trim().replaceAll("&([0-9a-f])", "").replaceAll("\\[", "").replaceAll("\\]", "");
                     String statName = "";
                     if(ItemLoreStats.plugin.getConfig().getString("primaryStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("primaryStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("secondaryStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("secondaryStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("bonusStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("bonusStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats." + key + ".name");
                     }

                     statName = statName.replaceAll(" ", "");
                     String lore = ChatColor.stripColor(line.toString());
                     String getLine;
                     double upgradeNumbers;
                     double var28;
                     if(lore.replaceAll(this.languageRegex, "").matches(statName) && lore.contains("%")) {
                        var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                        upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                        getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                        itemLore.set(globalIndex - 1, getLine);
                     } else if(lore.startsWith(levelName)) {
                        if(lore.contains("[+") && lore.contains("]")) {
                           int getNumbers = Integer.parseInt(ChatColor.stripColor(String.valueOf(line.split("\\+")[1].split("\\]")[0])).replaceAll("[^0-9.]", "").trim());
                           getLine = line.replaceAll("\\+" + getNumbers, "\\+" + (getNumbers + 1));
                           itemLore.set(globalIndex - 1, getLine);
                        } else {
                           itemLore.set(globalIndex - 1, line + ChatColor.DARK_GREEN + " [" + ChatColor.RED + "+1" + ChatColor.DARK_GREEN + "]");
                        }
                     } else if(!lore.replaceAll(this.languageRegex, "").matches(healthName) && !lore.replaceAll(this.languageRegex, "").matches(heroesManaName)) {
                        if(lore.replaceAll(this.languageRegex, "").matches(damageName)) {
                           if(lore.contains("-")) {
                              var28 = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[0].replaceAll("[^0-9.]", "").trim());
                              upgradeNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[1].replaceAll("[^0-9.]", "").trim());
                              double upgradeMinNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              double upgradeMaxNumbers = upgradeNumbers + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeMinNumbers)));
                              getLine = getLine.replaceAll(String.valueOf(upgradeNumbers), String.valueOf(this.util_Format.format(upgradeMaxNumbers)));
                              itemLore.set(globalIndex - 1, getLine);
                           } else {
                              var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                              upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                              itemLore.set(globalIndex - 1, getLine);
                           }
                        }
                     } else {
                        var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                        upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                        getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                        itemLore.set(globalIndex - 1, getLine);
                     }
                  }
               }
            }

            itemMeta.setLore(itemLore);
            item.setItemMeta(itemMeta);
            player.getInventory().setHelmet(new ItemStack(item));
            if(ItemLoreStats.plugin.getConfig().getBoolean("messages.upgradeSuccessful")) {
               player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeSuccessful", player, player, player.getInventory().getHelmet().getItemMeta().getDisplayName(), player.getInventory().getHelmet().getItemMeta().getDisplayName()));
            }
         }
      }

   }

   public void increaseItemStatOnChestplate(Player player) {
      if(!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
         String damageName = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
         String healthName = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name");
         String heroesManaName = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
         String levelName = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
         ItemStack item = player.getInventory().getChestplate();
         if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List itemLore = item.getItemMeta().getLore();
            ItemMeta itemMeta = item.getItemMeta();
            int globalIndex = 0;
            if(!ChatColor.stripColor(itemLore.toString()).contains(levelName)) {
               return;
            }

            String levelStatIndex = ChatColor.stripColor(itemLore.toString()).substring(ChatColor.stripColor(itemLore.toString()).indexOf(levelName)).split(",")[0].trim();
            boolean upgrades = false;
            int var27;
            if(levelStatIndex.contains("[+")) {
               var27 = Integer.parseInt(levelStatIndex.split("\\+")[1].split("\\]")[0].replaceAll("\\[", "").replaceAll("\\]", "").trim()) + 1;
            } else {
               var27 = 1;
            }

            if(ItemLoreStats.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap") == 0) {
               return;
            }

            if(var27 > ItemLoreStats.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap")) {
               if(ItemLoreStats.plugin.getConfig().getBoolean("messages.upgradeCapReached")) {
                  player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeCapReached", player, player, player.getItemInHand().getItemMeta().getDisplayName(), player.getItemInHand().getItemMeta().getDisplayName()));
               }

               return;
            }

            Iterator var13 = itemLore.iterator();

            while(var13.hasNext()) {
               String line = (String)var13.next();
               if(globalIndex < itemLore.size()) {
                  ++globalIndex;

                  for(int i = 0; i < ItemLoreStats.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).size(); ++i) {
                     String key = ItemLoreStats.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).toString().split(",")[i].trim().replaceAll("&([0-9a-f])", "").replaceAll("\\[", "").replaceAll("\\]", "").trim();
                     String statName = "";
                     if(ItemLoreStats.plugin.getConfig().getString("primaryStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("primaryStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("secondaryStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("secondaryStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("bonusStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("bonusStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats." + key + ".name");
                     }

                     statName = statName.replaceAll(" ", "");
                     String lore = ChatColor.stripColor(line.toString());
                     String getLine;
                     double upgradeNumbers;
                     double var28;
                     if(lore.replaceAll(this.languageRegex, "").matches(statName) && lore.contains("%")) {
                        var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                        upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                        getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                        itemLore.set(globalIndex - 1, getLine);
                     } else if(lore.startsWith(levelName)) {
                        if(lore.contains("[+") && lore.contains("]")) {
                           int getNumbers = Integer.parseInt(ChatColor.stripColor(String.valueOf(line.split("\\+")[1].split("\\]")[0])).replaceAll("[^0-9.]", "").trim());
                           getLine = line.replaceAll("\\+" + getNumbers, "\\+" + (getNumbers + 1));
                           itemLore.set(globalIndex - 1, getLine);
                        } else {
                           itemLore.set(globalIndex - 1, line + ChatColor.DARK_GREEN + " [" + ChatColor.RED + "+1" + ChatColor.DARK_GREEN + "]");
                        }
                     } else if(!lore.replaceAll(this.languageRegex, "").matches(healthName) && !lore.replaceAll(this.languageRegex, "").matches(heroesManaName)) {
                        if(lore.replaceAll(this.languageRegex, "").matches(damageName)) {
                           if(lore.contains("-")) {
                              var28 = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[0].replaceAll("[^0-9.]", "").trim());
                              upgradeNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[1].replaceAll("[^0-9.]", "").trim());
                              double upgradeMinNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              double upgradeMaxNumbers = upgradeNumbers + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeMinNumbers)));
                              getLine = getLine.replaceAll(String.valueOf(upgradeNumbers), String.valueOf(this.util_Format.format(upgradeMaxNumbers)));
                              itemLore.set(globalIndex - 1, getLine);
                           } else {
                              var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                              upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                              itemLore.set(globalIndex - 1, getLine);
                           }
                        }
                     } else {
                        var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                        upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                        getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                        itemLore.set(globalIndex - 1, getLine);
                     }
                  }
               }
            }

            itemMeta.setLore(itemLore);
            item.setItemMeta(itemMeta);
            player.getInventory().setChestplate(new ItemStack(item));
            if(ItemLoreStats.plugin.getConfig().getBoolean("messages.upgradeSuccessful")) {
               player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeSuccessful", player, player, player.getInventory().getChestplate().getItemMeta().getDisplayName(), player.getInventory().getChestplate().getItemMeta().getDisplayName()));
            }
         }
      }

   }

   public void increaseItemStatOnLeggings(Player player) {
      if(!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
         String damageName = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
         String healthName = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name");
         String heroesManaName = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
         String levelName = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
         ItemStack item = player.getInventory().getLeggings();
         if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List itemLore = item.getItemMeta().getLore();
            ItemMeta itemMeta = item.getItemMeta();
            int globalIndex = 0;
            if(!ChatColor.stripColor(itemLore.toString()).contains(levelName)) {
               return;
            }

            String levelStatIndex = ChatColor.stripColor(itemLore.toString()).substring(ChatColor.stripColor(itemLore.toString()).indexOf(levelName)).split(",")[0].trim();
            boolean upgrades = false;
            int var27;
            if(levelStatIndex.contains("[+")) {
               var27 = Integer.parseInt(levelStatIndex.split("\\+")[1].split("\\]")[0].replaceAll("\\[", "").replaceAll("\\]", "").trim()) + 1;
            } else {
               var27 = 1;
            }

            if(ItemLoreStats.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap") == 0) {
               return;
            }

            if(var27 > ItemLoreStats.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap")) {
               if(ItemLoreStats.plugin.getConfig().getBoolean("messages.upgradeCapReached")) {
                  player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeCapReached", player, player, player.getItemInHand().getItemMeta().getDisplayName(), player.getItemInHand().getItemMeta().getDisplayName()));
               }

               return;
            }

            Iterator var13 = itemLore.iterator();

            while(var13.hasNext()) {
               String line = (String)var13.next();
               if(globalIndex < itemLore.size()) {
                  ++globalIndex;

                  for(int i = 0; i < ItemLoreStats.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).size(); ++i) {
                     String key = ItemLoreStats.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).toString().split(",")[i].trim().replaceAll("&([0-9a-f])", "").replaceAll("\\[", "").replaceAll("\\]", "").trim();
                     String statName = "";
                     if(ItemLoreStats.plugin.getConfig().getString("primaryStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("primaryStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("secondaryStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("secondaryStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("bonusStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("bonusStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats." + key + ".name");
                     }

                     statName = statName.replaceAll(" ", "");
                     String lore = ChatColor.stripColor(line.toString());
                     String getLine;
                     double upgradeNumbers;
                     double var28;
                     if(lore.replaceAll(this.languageRegex, "").matches(statName) && lore.contains("%")) {
                        var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                        upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                        getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                        itemLore.set(globalIndex - 1, getLine);
                     } else if(lore.startsWith(levelName)) {
                        if(lore.contains("[+") && lore.contains("]")) {
                           int getNumbers = Integer.parseInt(ChatColor.stripColor(String.valueOf(line.split("\\+")[1].split("\\]")[0])).replaceAll("[^0-9.]", "").trim());
                           getLine = line.replaceAll("\\+" + getNumbers, "\\+" + (getNumbers + 1));
                           itemLore.set(globalIndex - 1, getLine);
                        } else {
                           itemLore.set(globalIndex - 1, line + ChatColor.DARK_GREEN + " [" + ChatColor.RED + "+1" + ChatColor.DARK_GREEN + "]");
                        }
                     } else if(!lore.replaceAll(this.languageRegex, "").matches(healthName) && !lore.replaceAll(this.languageRegex, "").matches(heroesManaName)) {
                        if(lore.replaceAll(this.languageRegex, "").matches(damageName)) {
                           if(lore.contains("-")) {
                              var28 = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[0].replaceAll("[^0-9.]", "").trim());
                              upgradeNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[1].replaceAll("[^0-9.]", "").trim());
                              double upgradeMinNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              double upgradeMaxNumbers = upgradeNumbers + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeMinNumbers)));
                              getLine = getLine.replaceAll(String.valueOf(upgradeNumbers), String.valueOf(this.util_Format.format(upgradeMaxNumbers)));
                              itemLore.set(globalIndex - 1, getLine);
                           } else {
                              var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                              upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                              itemLore.set(globalIndex - 1, getLine);
                           }
                        }
                     } else {
                        var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                        upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                        getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                        itemLore.set(globalIndex - 1, getLine);
                     }
                  }
               }
            }

            itemMeta.setLore(itemLore);
            item.setItemMeta(itemMeta);
            player.getInventory().setLeggings(new ItemStack(item));
            if(ItemLoreStats.plugin.getConfig().getBoolean("messages.upgradeSuccessful")) {
               player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeSuccessful", player, player, player.getInventory().getLeggings().getItemMeta().getDisplayName(), player.getInventory().getLeggings().getItemMeta().getDisplayName()));
            }
         }
      }

   }

   public void increaseItemStatOnBoots(Player player) {
      if(!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
         String damageName = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
         String healthName = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name");
         String heroesManaName = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
         String levelName = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
         ItemStack item = player.getInventory().getBoots();
         if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List itemLore = item.getItemMeta().getLore();
            ItemMeta itemMeta = item.getItemMeta();
            int globalIndex = 0;
            if(!ChatColor.stripColor(itemLore.toString()).contains(levelName)) {
               return;
            }

            String levelStatIndex = ChatColor.stripColor(itemLore.toString()).substring(ChatColor.stripColor(itemLore.toString()).indexOf(levelName)).split(",")[0].trim();
            boolean upgrades = false;
            int var27;
            if(levelStatIndex.contains("[+")) {
               var27 = Integer.parseInt(levelStatIndex.split("\\+")[1].split("\\]")[0].replaceAll("\\[", "").replaceAll("\\]", "").trim()) + 1;
            } else {
               var27 = 1;
            }

            if(ItemLoreStats.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap") == 0) {
               return;
            }

            if(var27 > ItemLoreStats.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap")) {
               if(ItemLoreStats.plugin.getConfig().getBoolean("messages.upgradeCapReached")) {
                  player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeCapReached", player, player, player.getItemInHand().getItemMeta().getDisplayName(), player.getItemInHand().getItemMeta().getDisplayName()));
               }

               return;
            }

            Iterator var13 = itemLore.iterator();

            while(var13.hasNext()) {
               String line = (String)var13.next();
               if(globalIndex < itemLore.size()) {
                  ++globalIndex;

                  for(int i = 0; i < ItemLoreStats.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).size(); ++i) {
                     String key = ItemLoreStats.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).toString().split(",")[i].trim().replaceAll("&([0-9a-f])", "").replaceAll("\\[", "").replaceAll("\\]", "").trim();
                     String statName = "";
                     if(ItemLoreStats.plugin.getConfig().getString("primaryStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("primaryStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("secondaryStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("secondaryStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("bonusStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("bonusStats." + key + ".name");
                     } else if(ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats." + key + ".name") != null) {
                        statName = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats." + key + ".name");
                     }

                     statName = statName.replaceAll(" ", "");
                     String lore = ChatColor.stripColor(line.toString());
                     String getLine;
                     double upgradeNumbers;
                     double var28;
                     if(lore.replaceAll(this.languageRegex, "").matches(statName) && lore.contains("%")) {
                        var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                        upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                        getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                        itemLore.set(globalIndex - 1, getLine);
                     } else if(lore.startsWith(levelName)) {
                        if(lore.contains("[+") && lore.contains("]")) {
                           int getNumbers = Integer.parseInt(ChatColor.stripColor(String.valueOf(line.split("\\+")[1].split("\\]")[0])).replaceAll("[^0-9.]", "").trim());
                           getLine = line.replaceAll("\\+" + getNumbers, "\\+" + (getNumbers + 1));
                           itemLore.set(globalIndex - 1, getLine);
                        } else {
                           itemLore.set(globalIndex - 1, line + ChatColor.DARK_GREEN + " [" + ChatColor.RED + "+1" + ChatColor.DARK_GREEN + "]");
                        }
                     } else if(!lore.replaceAll(this.languageRegex, "").matches(healthName) && !lore.replaceAll(this.languageRegex, "").matches(heroesManaName)) {
                        if(lore.replaceAll(this.languageRegex, "").matches(damageName)) {
                           if(lore.contains("-")) {
                              var28 = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[0].replaceAll("[^0-9.]", "").trim());
                              upgradeNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[1].replaceAll("[^0-9.]", "").trim());
                              double upgradeMinNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              double upgradeMaxNumbers = upgradeNumbers + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeMinNumbers)));
                              getLine = getLine.replaceAll(String.valueOf(upgradeNumbers), String.valueOf(this.util_Format.format(upgradeMaxNumbers)));
                              itemLore.set(globalIndex - 1, getLine);
                           } else {
                              var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                              upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                              getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                              itemLore.set(globalIndex - 1, getLine);
                           }
                        }
                     } else {
                        var28 = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                        upgradeNumbers = var28 + ItemLoreStats.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                        getLine = line.replaceAll(String.valueOf(var28), String.valueOf(this.util_Format.format(upgradeNumbers)));
                        itemLore.set(globalIndex - 1, getLine);
                     }
                  }
               }
            }

            itemMeta.setLore(itemLore);
            item.setItemMeta(itemMeta);
            player.getInventory().setBoots(new ItemStack(item));
            if(ItemLoreStats.plugin.getConfig().getBoolean("messages.upgradeSuccessful")) {
               player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeSuccessful", player, player, player.getInventory().getBoots().getItemMeta().getDisplayName(), player.getInventory().getBoots().getItemMeta().getDisplayName()));
            }
         }
      }

   }
}
