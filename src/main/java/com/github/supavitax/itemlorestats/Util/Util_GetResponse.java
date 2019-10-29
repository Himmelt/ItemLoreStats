package com.github.supavitax.itemlorestats.Util;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Iterator;

public class Util_GetResponse {
    private FileConfiguration PlayerDataConfig;
    Util_Colours util_Colours = new Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();

    public String getResponse(String getKeyFromLanguageFile, Entity getAttacker, Entity getDefender, String value1, String value2) {
        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + ItemLoreStats.plugin.getConfig().getString("languageFile") + ".yml"));
            if (this.PlayerDataConfig.getInt("FileVersion") >= ItemLoreStats.plugin.getConfig().getInt("fileVersion")) {
                if (getAttacker != null && getDefender != null && !"".equals(value1) && !"".equals(value2)) {
                    String e1 = this.util_EntityManager.returnEntityName(getAttacker);
                    String defender1 = this.util_EntityManager.returnEntityName(getDefender);
                    String message = this.PlayerDataConfig.getString(getKeyFromLanguageFile);
                    if (message.contains("{attacker}")) {
                        message = message.replaceAll("\\{attacker}", e1);
                    }

                    if (message.contains("{defender}")) {
                        message = message.replaceAll("\\{defender}", defender1);
                    }

                    if (message.contains("{value1}")) {
                        message = message.replaceAll("\\{value1}", value1);
                    }

                    if (message.contains("{value2}")) {
                        message = message.replaceAll("\\{value2}", value2);
                    }

                    return this.util_Colours.replaceTooltipColour(message);
                }

                return this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(getKeyFromLanguageFile));
            }

            Iterator defender = Bukkit.getServer().getOnlinePlayers().iterator();

            while (defender.hasNext()) {
                Player e = (Player) defender.next();
                if (e.isOp()) {
                    e.sendMessage(ChatColor.GREEN + "[ItemLoreStats]" + ChatColor.RED + " An error has occured when trying to generate a response message from Item Lore Stats. This is usually caused by the language file being out of date. Try deleting the language-en.yml file and restart the server. The language file version is " + this.PlayerDataConfig.getInt("FileVersion") + " and should be at least " + Integer.parseInt(ItemLoreStats.plugin.getDescription().getVersion().replace(".", "")) + ".");
                }
            }
        } catch (Exception var12) {
            var12.printStackTrace();
            System.out.println("*********** Failed to load message from language file. Please notify an admin or OP! ***********");
        }

        return "*********** Failed to load message from language file. Please notify an admin or OP! ***********";
    }
}
