package com.github.supavitax.itemlorestats.Damage;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Vanilla_Base_Armour {
    public double getDamageReductionFromArmour(Entity getDefender) {
        double reduction = 0.0D;
        ItemStack leggings;
        ItemStack boots;
        if (getDefender instanceof Player) {
            Player helmet1 = (Player) getDefender;
            PlayerInventory chestplate1 = helmet1.getInventory();
            leggings = chestplate1.getHelmet();
            boots = chestplate1.getChestplate();
            ItemStack leggings1 = chestplate1.getLeggings();
            ItemStack boots1 = chestplate1.getBoots();
            if (leggings != null) {
                if (leggings.getType() == Material.LEATHER_HELMET) {
                    reduction += 0.04D;
                } else if (leggings.getType() == Material.GOLD_HELMET) {
                    reduction += 0.08D;
                } else if (leggings.getType() == Material.CHAINMAIL_HELMET) {
                    reduction += 0.08D;
                } else if (leggings.getType() == Material.IRON_HELMET) {
                    reduction += 0.08D;
                } else if (leggings.getType() == Material.DIAMOND_HELMET) {
                    reduction += 0.12D;
                }
            }

            if (boots != null) {
                if (boots.getType() == Material.LEATHER_CHESTPLATE) {
                    reduction += 0.12D;
                } else if (boots.getType() == Material.GOLD_CHESTPLATE) {
                    reduction += 0.2D;
                } else if (boots.getType() == Material.CHAINMAIL_CHESTPLATE) {
                    reduction += 0.2D;
                } else if (boots.getType() == Material.IRON_CHESTPLATE) {
                    reduction += 0.24D;
                } else if (boots.getType() == Material.DIAMOND_CHESTPLATE) {
                    reduction += 0.32D;
                }
            }

            if (leggings1 != null) {
                if (leggings1.getType() == Material.LEATHER_LEGGINGS) {
                    reduction += 0.08D;
                } else if (leggings1.getType() == Material.GOLD_LEGGINGS) {
                    reduction += 0.12D;
                } else if (leggings1.getType() == Material.CHAINMAIL_LEGGINGS) {
                    reduction += 0.16D;
                } else if (leggings1.getType() == Material.IRON_LEGGINGS) {
                    reduction += 0.2D;
                } else if (leggings1.getType() == Material.DIAMOND_LEGGINGS) {
                    reduction += 0.24D;
                }
            }

            if (boots1 != null) {
                if (boots1.getType() == Material.LEATHER_BOOTS) {
                    reduction += 0.04D;
                } else if (boots1.getType() == Material.GOLD_BOOTS) {
                    reduction += 0.04D;
                } else if (boots1.getType() == Material.CHAINMAIL_BOOTS) {
                    reduction += 0.04D;
                } else if (boots1.getType() == Material.IRON_BOOTS) {
                    reduction += 0.08D;
                } else if (boots1.getType() == Material.DIAMOND_BOOTS) {
                    reduction += 0.12D;
                }
            }

            return reduction;
        } else if (!(getDefender instanceof Zombie) && !(getDefender instanceof Skeleton) && !(getDefender instanceof Giant)) {
            return reduction;
        } else {
            ItemStack helmet = ((LivingEntity) getDefender).getEquipment().getHelmet();
            ItemStack chestplate = ((LivingEntity) getDefender).getEquipment().getChestplate();
            leggings = ((LivingEntity) getDefender).getEquipment().getLeggings();
            boots = ((LivingEntity) getDefender).getEquipment().getBoots();
            if (helmet != null) {
                if (helmet.getType() == Material.LEATHER_HELMET) {
                    reduction += 0.04D;
                } else if (helmet.getType() == Material.GOLD_HELMET) {
                    reduction += 0.08D;
                } else if (helmet.getType() == Material.CHAINMAIL_HELMET) {
                    reduction += 0.08D;
                } else if (helmet.getType() == Material.IRON_HELMET) {
                    reduction += 0.08D;
                } else if (helmet.getType() == Material.DIAMOND_HELMET) {
                    reduction += 0.12D;
                }
            }

            if (chestplate != null) {
                if (chestplate.getType() == Material.LEATHER_CHESTPLATE) {
                    reduction += 0.12D;
                } else if (chestplate.getType() == Material.GOLD_CHESTPLATE) {
                    reduction += 0.2D;
                } else if (chestplate.getType() == Material.CHAINMAIL_CHESTPLATE) {
                    reduction += 0.2D;
                } else if (chestplate.getType() == Material.IRON_CHESTPLATE) {
                    reduction += 0.24D;
                } else if (chestplate.getType() == Material.DIAMOND_CHESTPLATE) {
                    reduction += 0.32D;
                }
            }

            if (leggings != null) {
                if (leggings.getType() == Material.LEATHER_LEGGINGS) {
                    reduction += 0.08D;
                } else if (leggings.getType() == Material.GOLD_LEGGINGS) {
                    reduction += 0.12D;
                } else if (leggings.getType() == Material.CHAINMAIL_LEGGINGS) {
                    reduction += 0.16D;
                } else if (leggings.getType() == Material.IRON_LEGGINGS) {
                    reduction += 0.2D;
                } else if (leggings.getType() == Material.DIAMOND_LEGGINGS) {
                    reduction += 0.24D;
                }
            }

            if (boots != null) {
                if (boots.getType() == Material.LEATHER_BOOTS) {
                    reduction += 0.04D;
                } else if (boots.getType() == Material.GOLD_BOOTS) {
                    reduction += 0.04D;
                } else if (boots.getType() == Material.CHAINMAIL_BOOTS) {
                    reduction += 0.04D;
                } else if (boots.getType() == Material.IRON_BOOTS) {
                    reduction += 0.08D;
                } else if (boots.getType() == Material.DIAMOND_BOOTS) {
                    reduction += 0.12D;
                }
            }

            return reduction;
        }
    }
}
