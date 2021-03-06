package com.github.supavitax.itemlorestats;

import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Classes {
    Util_Colours util_Colours = new Util_Colours();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    GearStats gearStats = new GearStats();

    public boolean checkClasses(Player player, ItemStack item) {
        if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            if (this.gearStats.getClass(item) != null) {
                ArrayList classes = this.gearStats.getClass(item);
                byte counter = 0;

                for (int cl = 0; cl < this.gearStats.getClass(item).size(); ++cl) {
                    if (player.hasPermission("ils.use." + classes.get(cl))) {
                        int var6 = counter + 1;
                        return true;
                    }
                }
            } else if (this.gearStats.getClass(item) == null) {
                return true;
            }
        }

        player.sendMessage(this.util_GetResponse.getResponse("ClassRequirementMessages.NotRequiredClass", null, null, "", ""));
        return false;
    }
}
