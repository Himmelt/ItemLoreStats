package com.github.supavitax.itemlorestats.Spells;

import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.logging.Level;

public class SpellsEvents implements Listener {

    GearStats gearStats = new GearStats();
    SpellCreator spellCreator = new SpellCreator();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    private FileConfiguration PlayerDataConfig;


    public boolean hasCooldown(String playerName, int getSeconds) {
        if (getSeconds == 0) {
            return false;
        } else if (ItemLoreStats.plugin.spellCooldowns.get(playerName) != null) {
            if (((Long) ItemLoreStats.plugin.spellCooldowns.get(playerName)).longValue() < System.currentTimeMillis() - (long) (getSeconds * 1000)) {
                return false;
            } else {
                long currentTime = System.currentTimeMillis();
                long oldTime = ((Long) ItemLoreStats.plugin.spellCooldowns.get(playerName)).longValue();
                String remainingCooldown = String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000))));
                String modifiedPlayerName = playerName.split("\\.")[0];
                Player player = Bukkit.getServer().getPlayer(modifiedPlayerName);
                if (remainingCooldown.length() > 7) {
                    player.sendMessage("      " + this.util_GetResponse.getResponse("SpellMessages.CastSpell.CooldownRemaining", player, player, String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(0, 5) + "." + String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(5, 7), String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(0, 5) + "." + String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(5, 7)));
                } else if (remainingCooldown.length() > 6) {
                    player.sendMessage("      " + this.util_GetResponse.getResponse("SpellMessages.CastSpell.CooldownRemaining", player, player, String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(0, 4) + "." + String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(4, 6), String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(0, 4) + "." + String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(4, 6)));
                } else if (remainingCooldown.length() > 5) {
                    player.sendMessage("      " + this.util_GetResponse.getResponse("SpellMessages.CastSpell.CooldownRemaining", player, player, String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(0, 3) + "." + String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(3, 5), String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(0, 3) + "." + String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(3, 5)));
                } else if (remainingCooldown.length() > 4) {
                    player.sendMessage("      " + this.util_GetResponse.getResponse("SpellMessages.CastSpell.CooldownRemaining", player, player, String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(0, 2) + "." + String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(2, 4), String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(0, 2) + "." + String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(2, 4)));
                } else if (remainingCooldown.length() > 3) {
                    player.sendMessage("      " + this.util_GetResponse.getResponse("SpellMessages.CastSpell.CooldownRemaining", player, player, String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(0, 1) + "." + String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(1, 3), String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(0, 1) + "." + String.valueOf(Math.abs(currentTime - (oldTime + (long) (getSeconds * 1000)))).substring(1, 3)));
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public void spellSelection(Player player) {
        String e;
        int spellCooldown;
        if (ItemLoreStats.plugin.util_WorldGuard != null) {
            if (!ItemLoreStats.plugin.util_WorldGuard.playerInPVPRegion(player)) {
                try {
                    if (this.gearStats.getSpellName(player.getItemInHand()) != null) {
                        e = this.gearStats.getSpellName(player.getItemInHand());
                        if (e != null) {
                            spellCooldown = this.spellCreator.getCooldown(e);
                            if (!this.hasCooldown(player.getName() + "." + e.replaceAll(" ", "").toLowerCase(), spellCooldown)) {
                                this.spellCreator.spellBuilder(e, player);
                                this.executeCommandList(player, e);
                                ItemLoreStats.plugin.spellCooldowns.put(player.getName() + "." + e.replaceAll(" ", "").toLowerCase(), Long.valueOf(System.currentTimeMillis()));
                            }
                        }
                    }
                } catch (Exception var5) {
                    ItemLoreStats.plugin.getLogger().log(Level.WARNING, player.getName() + " tried to cast " + this.gearStats.getSpellName(player.getItemInHand()) + " but ItemLoreStats was unable to find the config for that spell.");
                }
            }
        } else {
            try {
                if (this.gearStats.getSpellName(player.getItemInHand()) != null) {
                    e = this.gearStats.getSpellName(player.getItemInHand());
                    if (e != null) {
                        spellCooldown = this.spellCreator.getCooldown(e);
                        if (!this.hasCooldown(player.getName() + "." + e.replaceAll(" ", "").toLowerCase(), spellCooldown)) {
                            this.spellCreator.spellBuilder(e, player);
                            this.executeCommandList(player, e);
                            ItemLoreStats.plugin.spellCooldowns.put(player.getName() + "." + e.replaceAll(" ", "").toLowerCase(), Long.valueOf(System.currentTimeMillis()));
                        }
                    }
                }
            } catch (Exception var4) {
                ItemLoreStats.plugin.getLogger().log(Level.WARNING, player.getName() + " tried to cast " + this.gearStats.getSpellName(player.getItemInHand()) + " but ItemLoreStats was unable to find the config for that spell.");
            }
        }

    }

    public void executeCommandList(Player player, String spellFileName) {
        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + spellFileName + ".yml"));
            if (this.PlayerDataConfig.getKeys(false).toString().contains("commands") && this.PlayerDataConfig.getList("commands") != null) {
                for (int e = 0; e < this.PlayerDataConfig.getList("commands").size(); ++e) {
                    Bukkit.dispatchCommand(player, this.PlayerDataConfig.getList("commands").get(e).toString());
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
            ItemLoreStats.plugin.getLogger().log(Level.WARNING, player.getName() + " tried to execute the command list from the " + this.gearStats.getSpellName(player.getItemInHand()) + " spell.");
        }

    }
}
