package com.github.supavitax.itemlorestats;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class GearStatsBackup implements Listener {

   Util_Colours util_Colours = new Util_Colours();
   Util_GetResponse util_GetResponse = new Util_GetResponse();
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


   public double getArmourGear(Entity entity) {
      this.armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.armour + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.armour + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.armour + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.armour + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.armour + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.armour + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.armour + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.armour + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getDodgeGear(Entity entity) {
      this.dodge = ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.dodge + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.dodge + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.dodge + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.dodge + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.dodge + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.dodge + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.dodge + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.dodge + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getBlockGear(Entity entity) {
      this.block = ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.block + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.block + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.block + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.block + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.block + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.block + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.block + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.block + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public String getDamageGear(Entity entity) {
      this.damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
      double headMinValue = 0.0D;
      double headMaxValue = 0.0D;
      double chestMinValue = 0.0D;
      double chestMaxValue = 0.0D;
      double legsMinValue = 0.0D;
      double legsMaxValue = 0.0D;
      double bootsMinValue = 0.0D;
      double bootsMaxValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.damage + ": +")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.damage + ": +").length()).trim();
               if(bootsLore.contains("-")) {
                  headMinValue = Double.parseDouble(bootsLore.split("-")[0]);
                  headMaxValue = Double.parseDouble(bootsLore.split("-")[1]);
               } else {
                  headMinValue = Double.parseDouble(bootsLore);
                  headMaxValue = Double.parseDouble(bootsLore);
               }
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.damage + ": +")) {
               line = ChatColor.stripColor(boots1).substring((this.damage + ": +").length()).trim();
               if(line.contains("-")) {
                  chestMinValue = Double.parseDouble(line.split("-")[0]);
                  chestMaxValue = Double.parseDouble(line.split("-")[1]);
               } else {
                  chestMinValue = Double.parseDouble(line);
                  chestMaxValue = Double.parseDouble(line);
               }
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.damage + ": +")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.damage + ": +").length()).trim();
               if(value.contains("-")) {
                  legsMinValue = Double.parseDouble(value.split("-")[0]);
                  legsMaxValue = Double.parseDouble(value.split("-")[1]);
               } else {
                  legsMinValue = Double.parseDouble(value);
                  legsMaxValue = Double.parseDouble(value);
               }
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.damage + ": +")) {
               String value1 = ChatColor.stripColor(line).substring((this.damage + ": +").length()).trim();
               if(value1.contains("-")) {
                  bootsMinValue = Double.parseDouble(value1.split("-")[0]);
                  bootsMaxValue = Double.parseDouble(value1.split("-")[1]);
               } else {
                  bootsMinValue = Double.parseDouble(value1);
                  bootsMaxValue = Double.parseDouble(value1);
               }
            }
         }
      }

      return headMinValue + chestMinValue + legsMinValue + bootsMinValue + "-" + (headMaxValue + chestMaxValue + legsMaxValue + bootsMaxValue);
   }

   public double getCritChanceGear(Entity entity) {
      this.critChance = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.critChance + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.critChance + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.critChance + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.critChance + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.critChance + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.critChance + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.critChance + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.critChance + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getCritDamageGear(Entity entity) {
      this.critDamage = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.critDamage + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.critDamage + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.critDamage + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.critDamage + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.critDamage + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.critDamage + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.critDamage + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.critDamage + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getHealthGear(Entity entity) {
      this.health = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.health + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.health + ": ").length()).trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.health + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.health + ": ").length()).trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.health + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.health + ": ").length()).trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.health + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.health + ": ").length()).trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getHealthRegenGear(Entity entity) {
      this.healthRegen = ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.healthRegen + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.healthRegen + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.healthRegen + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.healthRegen + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.healthRegen + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.healthRegen + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.healthRegen + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.healthRegen + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getLifeStealGear(Entity entity) {
      this.lifeSteal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.lifeSteal + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.lifeSteal + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.lifeSteal + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.lifeSteal + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.lifeSteal + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.lifeSteal + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.lifeSteal + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.lifeSteal + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getLifeStealHealGear(Entity entity) {
      this.lifeStealHeal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeStealHeal.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.lifeStealHeal + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.lifeStealHeal + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.lifeStealHeal + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.lifeStealHeal + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.lifeStealHeal + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.lifeStealHeal + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.lifeStealHeal + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.lifeStealHeal + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getReflectGear(Entity entity) {
      this.reflect = ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.reflect + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.reflect + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.reflect + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.reflect + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.reflect + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.reflect + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.reflect + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.reflect + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getFireGear(Entity entity) {
      this.fire = ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.fire + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.fire + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.fire + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.fire + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.fire + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.fire + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.fire + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.fire + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getIceGear(Entity entity) {
      this.ice = ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.ice + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.ice + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.ice + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.ice + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.ice + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.ice + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.ice + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.ice + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getPoisonGear(Entity entity) {
      this.poison = ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.poison + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.poison + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.poison + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.poison + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.poison + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.poison + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.poison + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.poison + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getWitherGear(Entity entity) {
      this.wither = ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.wither + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.wither + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.wither + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.wither + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.wither + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.wither + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.wither + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.wither + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getHarmingGear(Entity entity) {
      this.harming = ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.harming + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.harming + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.harming + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.harming + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.harming + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.harming + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.harming + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.harming + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getBlindGear(Entity entity) {
      this.blind = ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = this.getSlots.returnHelmet(entity);
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.blind + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.blind + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = this.getSlots.returnChestplate(entity);
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.blind + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.blind + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = this.getSlots.returnLeggings(entity);
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.blind + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.blind + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = this.getSlots.returnBoots(entity);
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.blind + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.blind + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getMovementSpeedGear(Player player) {
      this.movementspeed = ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = player.getInventory().getHelmet();
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.movementspeed + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.movementspeed + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = player.getInventory().getChestplate();
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.movementspeed + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.movementspeed + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = player.getInventory().getLeggings();
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.movementspeed + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.movementspeed + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = player.getInventory().getBoots();
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.movementspeed + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.movementspeed + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getXPMultiplierGear(Player player) {
      this.xpmultiplier = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = player.getInventory().getHelmet();
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.xpmultiplier + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.xpmultiplier + ": ").length()).replace("%", "").trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = player.getInventory().getChestplate();
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.xpmultiplier + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.xpmultiplier + ": ").length()).replace("%", "").trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = player.getInventory().getLeggings();
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.xpmultiplier + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.xpmultiplier + ": ").length()).replace("%", "").trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = player.getInventory().getBoots();
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.xpmultiplier + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.xpmultiplier + ": ").length()).replace("%", "").trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double get_Heroes_MaxManaGear(Player player) {
      this.heroes_MaxMana = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
      double headValue = 0.0D;
      double chestValue = 0.0D;
      double legsValue = 0.0D;
      double bootsValue = 0.0D;
      ItemStack head = player.getInventory().getHelmet();
      String bootsLore;
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List chest = head.getItemMeta().getLore();
         Iterator boots = chest.iterator();

         while(boots.hasNext()) {
            String legs = (String)boots.next();
            if(ChatColor.stripColor(legs).startsWith(this.heroes_MaxMana + ": ")) {
               bootsLore = ChatColor.stripColor(legs).substring((this.heroes_MaxMana + ": ").length()).trim();
               headValue = Double.parseDouble(bootsLore);
            }
         }
      }

      ItemStack chest1 = player.getInventory().getChestplate();
      String line;
      if(chest1 != null && chest1.hasItemMeta() && chest1.getItemMeta().hasLore()) {
         List legs1 = chest1.getItemMeta().getLore();
         Iterator bootsLore1 = legs1.iterator();

         while(bootsLore1.hasNext()) {
            String boots1 = (String)bootsLore1.next();
            if(ChatColor.stripColor(boots1).startsWith(this.heroes_MaxMana + ": ")) {
               line = ChatColor.stripColor(boots1).substring((this.heroes_MaxMana + ": ").length()).trim();
               chestValue = Double.parseDouble(line);
            }
         }
      }

      ItemStack legs2 = player.getInventory().getLeggings();
      if(legs2 != null && legs2.hasItemMeta() && legs2.getItemMeta().hasLore()) {
         List boots2 = legs2.getItemMeta().getLore();
         Iterator line1 = boots2.iterator();

         while(line1.hasNext()) {
            bootsLore = (String)line1.next();
            if(ChatColor.stripColor(bootsLore).startsWith(this.heroes_MaxMana + ": ")) {
               String value = ChatColor.stripColor(bootsLore).substring((this.heroes_MaxMana + ": ").length()).trim();
               legsValue = Double.parseDouble(value);
            }
         }
      }

      ItemStack boots3 = player.getInventory().getBoots();
      if(boots3 != null && boots3.hasItemMeta() && boots3.getItemMeta().hasLore()) {
         List bootsLore2 = boots3.getItemMeta().getLore();
         Iterator value2 = bootsLore2.iterator();

         while(value2.hasNext()) {
            line = (String)value2.next();
            if(ChatColor.stripColor(line).startsWith(this.heroes_MaxMana + ": ")) {
               String value1 = ChatColor.stripColor(line).substring((this.heroes_MaxMana + ": ").length()).trim();
               bootsValue = Double.parseDouble(value1);
            }
         }
      }

      return headValue + chestValue + legsValue + bootsValue;
   }

   public double getArmourItemInHand(Entity entity) {
      this.armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.armour + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.armour + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getDodgeItemInHand(Entity entity) {
      this.dodge = ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.dodge + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.dodge + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getBlockItemInHand(Entity entity) {
      this.block = ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.block + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.block + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public String getDamageItemInHand(Entity entity) {
      this.damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
      double itemInHandMinValue = 0.0D;
      double itemInHandMaxValue = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var9 = itemInHandLore.iterator();

         while(var9.hasNext()) {
            String line = (String)var9.next();
            if(ChatColor.stripColor(line).startsWith(this.damage + ": +")) {
               String value = ChatColor.stripColor(line).substring((this.damage + ": +").length()).trim();
               if(value.contains("-")) {
                  itemInHandMinValue = Double.parseDouble(value.split("-")[0]);
                  itemInHandMaxValue = Double.parseDouble(value.split("-")[1]);
               } else {
                  itemInHandMinValue = Double.parseDouble(value);
                  itemInHandMaxValue = Double.parseDouble(value);
               }
            }
         }
      }

      return itemInHandMinValue + "-" + itemInHandMaxValue;
   }

   public double getCritChanceItemInHand(Entity entity) {
      this.critChance = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.critChance + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.critChance + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getCritDamageItemInHand(Entity entity) {
      this.critDamage = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.critDamage + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.critDamage + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getHealthItemInHand(Entity entity) {
      this.health = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.health + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.health + ": ").length()).trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getHealthRegenItemInHand(Entity entity) {
      this.healthRegen = ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.healthRegen + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.healthRegen + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getLifeStealItemInHand(Entity entity) {
      this.lifeSteal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.lifeSteal + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.lifeSteal + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getLifeStealHealItemInHand(Entity entity) {
      this.lifeStealHeal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeStealHeal.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.lifeStealHeal + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.lifeStealHeal + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getReflectItemInHand(Entity entity) {
      this.reflect = ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.reflect + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.reflect + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getIceItemInHand(Entity entity) {
      this.ice = ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.ice + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.ice + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getFireItemInHand(Entity entity) {
      this.fire = ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.fire + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.fire + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getPoisonItemInHand(Entity entity) {
      this.poison = ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.poison + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.poison + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getWitherItemInHand(Entity entity) {
      this.wither = ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.wither + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.wither + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getHarmingItemInHand(Entity entity) {
      this.harming = ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.harming + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.harming + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getBlindItemInHand(Entity entity) {
      this.blind = ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.blind + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.blind + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getMovementSpeedItemInHand(Player player) {
      this.movementspeed = ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = player.getItemInHand();
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.movementspeed + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.movementspeed + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public double getXPMultiplierItemInHand(Player player) {
      this.xpmultiplier = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = player.getItemInHand();
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.xpmultiplier + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.xpmultiplier + ": ").length()).replace("%", "").trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public String getPvPDamageModifierItemInHand(Entity entity) {
      this.pvpdamage = ItemLoreStats.plugin.getConfig().getString("bonusStats.pvpDamage.name");
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var5 = itemInHandLore.iterator();

         while(var5.hasNext()) {
            String line = (String)var5.next();
            if(ChatColor.stripColor(line).startsWith(this.pvpdamage + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.pvpdamage + ": ").length()).trim();
               return value;
            }
         }
      }

      return "0.0";
   }

   public String getPvEDamageModifierItemInHand(Entity entity) {
      this.pvedamage = ItemLoreStats.plugin.getConfig().getString("bonusStats.pveDamage.name");
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var5 = itemInHandLore.iterator();

         while(var5.hasNext()) {
            String line = (String)var5.next();
            if(ChatColor.stripColor(line).startsWith(this.pvedamage + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.pvedamage + ": ").length()).trim();
               return value;
            }
         }
      }

      return "0.0";
   }

   public int getSellValueItemInHand(Player player) {
      this.sellValueName = ItemLoreStats.plugin.getConfig().getString("bonusStats.sellValue.name");
      byte storeLoreValues = 0;
      ItemStack itemInHand = player.getItemInHand();
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var6 = itemInHandLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).contains(this.sellValueName)) {
               String value = ChatColor.stripColor(line).replaceAll("[^0-9]", "").trim();
               int storeLoreValues1 = Integer.parseInt(value);
               return storeLoreValues1;
            }
         }
      }

      return storeLoreValues;
   }

   public double get_Heroes_MaxManaItemInHand(Entity entity) {
      this.heroes_MaxMana = ItemLoreStats.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
      double storeLoreValues = 0.0D;
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var7 = itemInHandLore.iterator();

         while(var7.hasNext()) {
            String line = (String)var7.next();
            if(ChatColor.stripColor(line).startsWith(this.heroes_MaxMana + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.heroes_MaxMana + ": ").length()).trim();
               storeLoreValues = Double.parseDouble(value);
               return storeLoreValues;
            }
         }
      }

      return storeLoreValues;
   }

   public String getSwingSpeedItemInHand(Entity entity) {
      this.weaponspeed = ItemLoreStats.plugin.getConfig().getString("bonusStats.weaponSpeed.name");
      String storeLoreValues = "Normal";
      ItemStack itemInHand = this.getSlots.returnItemInHand(entity);
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var6 = itemInHandLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.weaponspeed + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.weaponspeed + ": ").length()).trim();
               return value;
            }
         }
      }

      return storeLoreValues;
   }

   public int getXPLevelRequirementHead(Player player) {
      this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
      byte storeLoreValues = 0;
      ItemStack item = player.getInventory().getHelmet();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.xplevel + ": ")) {
               String xpLevelValue = ChatColor.stripColor(line).substring((this.xplevel + ": ").length()).trim();
               int storeLoreValues1;
               if(xpLevelValue.contains("[+")) {
                  storeLoreValues1 = Integer.parseInt(xpLevelValue.split("\\[+")[0].trim());
               } else {
                  storeLoreValues1 = Integer.parseInt(xpLevelValue);
               }

               return storeLoreValues1;
            }
         }
      }

      return storeLoreValues;
   }

   public int getXPLevelRequirementChest(Player player) {
      this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
      byte storeLoreValues = 0;
      ItemStack item = player.getInventory().getChestplate();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.xplevel + ": ")) {
               String xpLevelValue = ChatColor.stripColor(line).substring((this.xplevel + ": ").length()).trim();
               int storeLoreValues1;
               if(xpLevelValue.contains("[+")) {
                  storeLoreValues1 = Integer.parseInt(xpLevelValue.split("\\[+")[0].trim());
               } else {
                  storeLoreValues1 = Integer.parseInt(xpLevelValue);
               }

               return storeLoreValues1;
            }
         }
      }

      return storeLoreValues;
   }

   public int getXPLevelRequirementLegs(Player player) {
      this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
      byte storeLoreValues = 0;
      ItemStack item = player.getInventory().getLeggings();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.xplevel + ": ")) {
               String xpLevelValue = ChatColor.stripColor(line).substring((this.xplevel + ": ").length()).trim();
               int storeLoreValues1;
               if(xpLevelValue.contains("[+")) {
                  storeLoreValues1 = Integer.parseInt(xpLevelValue.split("\\[+")[0].trim());
               } else {
                  storeLoreValues1 = Integer.parseInt(xpLevelValue);
               }

               return storeLoreValues1;
            }
         }
      }

      return storeLoreValues;
   }

   public int getXPLevelRequirementBoots(Player player) {
      this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
      byte storeLoreValues = 0;
      ItemStack item = player.getInventory().getBoots();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.xplevel + ": ")) {
               String xpLevelValue = ChatColor.stripColor(line).substring((this.xplevel + ": ").length()).trim();
               int storeLoreValues1;
               if(xpLevelValue.contains("[+")) {
                  storeLoreValues1 = Integer.parseInt(xpLevelValue.split("\\[+")[0].trim());
               } else {
                  storeLoreValues1 = Integer.parseInt(xpLevelValue);
               }

               return storeLoreValues1;
            }
         }
      }

      return storeLoreValues;
   }

   public int getXPLevelRequirementItemInHand(Player player) {
      this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
      byte storeLoreValues = 0;
      ItemStack itemInHand = player.getItemInHand();
      if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var6 = itemInHandLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.xplevel + ": ")) {
               String xpLevelValue = ChatColor.stripColor(line).substring((this.xplevel + ": ").length()).trim();

               try {
                  int storeLoreValues1;
                  if(xpLevelValue.contains("[+")) {
                     storeLoreValues1 = Integer.parseInt(xpLevelValue.split("\\[+")[0].trim());
                  } else {
                     storeLoreValues1 = Integer.parseInt(xpLevelValue);
                  }

                  return storeLoreValues1;
               } catch (NumberFormatException var9) {
                  ;
               }
            }
         }
      }

      return storeLoreValues;
   }

   public int getXPLevelRequirementItemOnPickup(ItemStack itemOnPickup) {
      this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
      byte storeLoreValues = 0;
      if(itemOnPickup != null && itemOnPickup.hasItemMeta() && itemOnPickup.getItemMeta().hasLore()) {
         List itemInHandLore = itemOnPickup.getItemMeta().getLore();
         Iterator var6 = itemInHandLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.xplevel + ": ")) {
               String xpLevelValue = ChatColor.stripColor(line).substring((this.xplevel + ": ").length()).trim();

               try {
                  int storeLoreValues1;
                  if(xpLevelValue.contains("[+")) {
                     storeLoreValues1 = Integer.parseInt(xpLevelValue.split("\\[+")[0].trim());
                  } else {
                     storeLoreValues1 = Integer.parseInt(xpLevelValue);
                  }

                  return storeLoreValues1;
               } catch (NumberFormatException var9) {
                  ;
               }
            }
         }
      }

      return storeLoreValues;
   }

   public String getSetBonusHead(Player player) {
      this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name");
      String headKey = "";
      ItemStack head = player.getInventory().getHelmet();
      if(head != null && head.hasItemMeta() && head.getItemMeta().hasLore()) {
         List headLore = head.getItemMeta().getLore();
         Iterator var6 = headLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(line.contains(this.setbonus)) {
               String value = this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
               return value;
            }
         }
      }

      return headKey;
   }

   public String getSetBonusChest(Player player) {
      this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name");
      String chestKey = "";
      ItemStack chest = player.getInventory().getChestplate();
      if(chest != null && chest.hasItemMeta() && chest.getItemMeta().hasLore()) {
         List chestLore = chest.getItemMeta().getLore();
         Iterator var6 = chestLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(line.contains(this.setbonus)) {
               String value = this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
               return value;
            }
         }
      }

      return chestKey;
   }

   public String getSetBonusLegs(Player player) {
      this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name");
      String legsKey = "";
      ItemStack legs = player.getInventory().getLeggings();
      if(legs != null && legs.hasItemMeta() && legs.getItemMeta().hasLore()) {
         List legsLore = legs.getItemMeta().getLore();
         Iterator var6 = legsLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(line.contains(this.setbonus)) {
               String value = this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
               return value;
            }
         }
      }

      return legsKey;
   }

   public String getSetBonusBoots(Player player) {
      this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name");
      String bootsKey = "";
      ItemStack boots = player.getInventory().getBoots();
      if(boots != null && boots.hasItemMeta() && boots.getItemMeta().hasLore()) {
         List bootsLore = boots.getItemMeta().getLore();
         Iterator var6 = bootsLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(line.contains(this.setbonus)) {
               String value = this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
               return value;
            }
         }
      }

      return bootsKey;
   }

   public String getSetBonusItemInHand(Player player) {
      this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name");
      String weaponKey = "";
      ItemStack itemInHand = player.getItemInHand();
      if(itemInHand != null && itemInHand.hasItemMeta() && ItemLoreStats.plugin.isTool(itemInHand.getType()) && itemInHand.getItemMeta().hasLore()) {
         List itemInHandLore = itemInHand.getItemMeta().getLore();
         Iterator var6 = itemInHandLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(line.contains(this.setbonus)) {
               String value = this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
               return value;
            }
         }
      }

      return weaponKey;
   }

   public String getSoulboundNameHead(Player player) {
      this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
      String storeLoreValues = "";
      ItemStack item = player.getInventory().getHelmet();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.soulbound)) {
               String value = ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
               return value;
            }
         }
      }

      return null;
   }

   public String getSoulboundNameChest(Player player) {
      this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
      String storeLoreValues = "";
      ItemStack item = player.getInventory().getChestplate();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.soulbound)) {
               String value = ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
               return value;
            }
         }
      }

      return null;
   }

   public String getSoulboundNameLegs(Player player) {
      this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
      String storeLoreValues = "";
      ItemStack item = player.getInventory().getLeggings();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.soulbound)) {
               String value = ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
               return value;
            }
         }
      }

      return null;
   }

   public String getSoulboundNameBoots(Player player) {
      this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
      String storeLoreValues = "";
      ItemStack item = player.getInventory().getBoots();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.soulbound)) {
               String value = ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
               return value;
            }
         }
      }

      return null;
   }

   public String getSoulboundNameItemInHand(Player player) {
      this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
      String storeLoreValues = "";
      ItemStack item = player.getItemInHand();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.soulbound)) {
               String value = ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
               return value;
            }
         }
      }

      return null;
   }

   public String getSoulboundNameItemOnPickup(ItemStack itemOnPickup) {
      this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
      String storeLoreValues = "";
      if(itemOnPickup != null && itemOnPickup.hasItemMeta() && itemOnPickup.getItemMeta().hasLore()) {
         List itemLore = itemOnPickup.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.soulbound)) {
               String value = ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
               return value;
            }
         }
      }

      return null;
   }

   public ArrayList getClassItemOnPickup(ItemStack itemOnPickup) {
      this.className = ItemLoreStats.plugin.getConfig().getString("bonusStats.class.name");
      if(itemOnPickup != null && itemOnPickup.hasItemMeta() && itemOnPickup.getItemMeta().hasLore()) {
         List itemLore = itemOnPickup.getItemMeta().getLore();
         Iterator var5 = itemLore.iterator();

         while(var5.hasNext()) {
            String line = (String)var5.next();
            if(ChatColor.stripColor(line).startsWith(this.className + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.className + ": ").length()).trim();
               ArrayList storeLoreValues = new ArrayList(Arrays.asList(value.split(ItemLoreStats.plugin.getConfig().getString("bonusStats.class.separator"))));
               return storeLoreValues;
            }
         }
      }

      return null;
   }

   public String playerHeldItemChangeSetBonusItemInHand(ItemStack itemstack) {
      this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name");
      String weaponKey = "";
      if(itemstack != null && itemstack.hasItemMeta() && ItemLoreStats.plugin.isTool(itemstack.getType()) && itemstack.getItemMeta().hasLore()) {
         List itemInHandLore = itemstack.getItemMeta().getLore();
         Iterator var6 = itemInHandLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(line.contains(this.setbonus)) {
               String value = this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
               return value;
            }
         }
      }

      return weaponKey;
   }

   public String playerHeldItemChangeSoulboundNameItemInHand(ItemStack itemstack) {
      this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
      String storeLoreValues = "";
      if(itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasLore()) {
         List itemLore = itemstack.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.soulbound)) {
               String value = ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
               return value;
            }
         }
      }

      return null;
   }

   public String playerHeldItemChangeClassItemInHand(ItemStack itemstack) {
      this.className = ItemLoreStats.plugin.getConfig().getString("bonusStats.class.name");
      String storeLoreValues = "";
      if(itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasLore()) {
         List itemLore = itemstack.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.className + ": ")) {
               String value = ChatColor.stripColor(line).substring((this.className + ": ").length()).trim();
               return value;
            }
         }
      }

      return null;
   }

   public int playerHeldItemChangeXPLevelRequirementItemInHand(ItemStack itemstack) {
      this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
      boolean storeLoreValues = false;
      if(itemstack != null && itemstack.hasItemMeta() && itemstack.getItemMeta().hasLore()) {
         List itemInHandLore = itemstack.getItemMeta().getLore();
         Iterator var6 = itemInHandLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(this.xplevel + ": ")) {
               String xpLevelValue = ChatColor.stripColor(line).substring((this.xplevel + ": ").length()).trim();

               try {
                  int storeLoreValues1;
                  if(xpLevelValue.contains("[+")) {
                     storeLoreValues1 = Integer.parseInt(xpLevelValue.split(" ")[0]);
                  } else {
                     storeLoreValues1 = Integer.parseInt(xpLevelValue);
                  }

                  return storeLoreValues1;
               } catch (NumberFormatException var9) {
                  ;
               }
            }
         }
      }

      return 0;
   }

   public boolean getTnT(Player player) {
      String castString = this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", (Entity)null, (Entity)null, "", "").replaceAll("&([0-9a-f])", "");
      this.tnt = ItemLoreStats.plugin.getConfig().getString("spells.tnt.name");
      ItemStack item = player.getItemInHand();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(ChatColor.stripColor(castString) + " " + this.tnt)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean getFireball(Player player) {
      String castString = this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", (Entity)null, (Entity)null, "", "").replaceAll("&([0-9a-f])", "");
      this.fireball = ItemLoreStats.plugin.getConfig().getString("spells.fireball.name");
      ItemStack item = player.getItemInHand();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(ChatColor.stripColor(castString) + " " + this.fireball)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean getLightning(Player player) {
      String castString = this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", (Entity)null, (Entity)null, "", "").replaceAll("&([0-9a-f])", "");
      this.lightning = ItemLoreStats.plugin.getConfig().getString("spells.lightning.name");
      ItemStack item = player.getItemInHand();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).startsWith(ChatColor.stripColor(castString) + " " + this.lightning)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean getFrostbolt(Player player) {
      String castString = this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", (Entity)null, (Entity)null, "", "").replaceAll("&([0-9a-f])", "");
      this.frostbolt = ItemLoreStats.plugin.getConfig().getString("spells.frostbolt.name");
      ItemStack item = player.getItemInHand();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).equals(ChatColor.stripColor(castString) + " " + this.frostbolt)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean getMinorHeal(Player player) {
      String castString = this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", (Entity)null, (Entity)null, "", "").replaceAll("&([0-9a-f])", "");
      this.minorHeal = ItemLoreStats.plugin.getConfig().getString("spells.minorHeal.name");
      ItemStack item = player.getItemInHand();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).equals(ChatColor.stripColor(castString) + " " + this.minorHeal)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean getMajorHeal(Player player) {
      String castString = this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", (Entity)null, (Entity)null, "", "").replaceAll("&([0-9a-f])", "");
      this.majorHeal = ItemLoreStats.plugin.getConfig().getString("spells.majorHeal.name");
      ItemStack item = player.getItemInHand();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).equals(ChatColor.stripColor(castString) + " " + this.majorHeal)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean getMinorAOEHeal(Player player) {
      String castString = this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", (Entity)null, (Entity)null, "", "").replaceAll("&([0-9a-f])", "");
      this.minorAOEHeal = ItemLoreStats.plugin.getConfig().getString("spells.minorAOEHeal.name");
      ItemStack item = player.getItemInHand();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).equals(ChatColor.stripColor(castString) + " " + this.minorAOEHeal)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean getMajorAOEHeal(Player player) {
      String castString = this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", (Entity)null, (Entity)null, "", "").replaceAll("&([0-9a-f])", "");
      this.majorAOEHeal = ItemLoreStats.plugin.getConfig().getString("spells.majorAOEHeal.name");
      ItemStack item = player.getItemInHand();
      if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
         List itemLore = item.getItemMeta().getLore();
         Iterator var6 = itemLore.iterator();

         while(var6.hasNext()) {
            String line = (String)var6.next();
            if(ChatColor.stripColor(line).equals(ChatColor.stripColor(castString) + " " + this.majorAOEHeal)) {
               return true;
            }
         }
      }

      return false;
   }
}
