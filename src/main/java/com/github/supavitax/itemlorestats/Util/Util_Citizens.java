package com.github.supavitax.itemlorestats.Util;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.entity.Entity;

public class Util_Citizens {

   ItemLoreStats main;


   public Util_Citizens(ItemLoreStats instance) {
      this.main = instance;
   }

   public boolean checkVulnerability(Entity entity) {
      NPCRegistry registry = CitizensAPI.getNPCRegistry();
      NPC npc = registry.getNPC(entity);
      return entity != null?npc.data().has("protected"):false;
   }
}
