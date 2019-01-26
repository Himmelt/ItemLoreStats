package com.github.supavitax.itemlorestats.Enchants;

import com.github.supavitax.itemlorestats.ItemLoreStats;

public class InternalCooldown {

    public boolean hasCooldown(String playerName, int getSeconds) {
        return getSeconds == 0 ? false : (ItemLoreStats.plugin.internalCooldowns.get(playerName) != null ? System.currentTimeMillis() <= ((Long) ItemLoreStats.plugin.internalCooldowns.get(playerName)).longValue() + (long) (getSeconds * 1000) : false);
    }
}
