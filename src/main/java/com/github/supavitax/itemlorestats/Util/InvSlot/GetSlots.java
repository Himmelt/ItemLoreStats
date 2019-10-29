package com.github.supavitax.itemlorestats.Util.InvSlot;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetSlots {
    public ItemStack returnItemInHand(Entity entity) {
        ItemStack item;
        if (entity instanceof Player) {
            item = ((Player) entity).getItemInHand();
            return item;
        } else if (entity instanceof LivingEntity) {
            item = ((LivingEntity) entity).getEquipment().getItemInHand();
            return item;
        } else {
            return new ItemStack(Material.POTATO);
        }
    }

    public ItemStack returnHelmet(Entity entity) {
        ItemStack item;
        if (entity instanceof Player) {
            item = ((Player) entity).getInventory().getHelmet();
            return item;
        } else if (entity instanceof LivingEntity) {
            item = ((LivingEntity) entity).getEquipment().getHelmet();
            return item;
        } else {
            return new ItemStack(Material.POTATO);
        }
    }

    public ItemStack returnChestplate(Entity entity) {
        ItemStack item;
        if (entity instanceof Player) {
            item = ((Player) entity).getInventory().getChestplate();
            return item;
        } else if (entity instanceof LivingEntity) {
            item = ((LivingEntity) entity).getEquipment().getChestplate();
            return item;
        } else {
            return new ItemStack(Material.POTATO);
        }
    }

    public ItemStack returnLeggings(Entity entity) {
        ItemStack item;
        if (entity instanceof Player) {
            item = ((Player) entity).getInventory().getLeggings();
            return item;
        } else if (entity instanceof LivingEntity) {
            item = ((LivingEntity) entity).getEquipment().getLeggings();
            return item;
        } else {
            return new ItemStack(Material.POTATO);
        }
    }

    public ItemStack returnBoots(Entity entity) {
        ItemStack item;
        if (entity instanceof Player) {
            item = ((Player) entity).getInventory().getBoots();
            return item;
        } else if (entity instanceof LivingEntity) {
            item = ((LivingEntity) entity).getEquipment().getBoots();
            return item;
        } else {
            return new ItemStack(Material.POTATO);
        }
    }
}
