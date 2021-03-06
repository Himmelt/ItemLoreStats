package com.github.supavitax.itemlorestats;

import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Soulbound {
    Util_Colours util_Colours = new Util_Colours();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    GearStats gearStats = new GearStats();

    public boolean checkSoulbound(Player player, ItemStack item) {
        if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            String soulboundName = this.gearStats.getSoulboundName(player, item);
            if (soulboundName != null) {
                if (soulboundName.equals(player.getName())) {
                    return true;
                }

                if (soulboundName == "") {
                    return true;
                }
            }
        }

        player.sendMessage(this.util_GetResponse.getResponse("SoulboundMessages.BoundToSomeoneElse", null, null, "", ""));
        return false;
    }
}
