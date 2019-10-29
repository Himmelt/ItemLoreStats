package com.github.supavitax.itemlorestats.Util;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Util_EntityManager {
    public ItemStack returnItemStackHelmet(Entity entity) {
        ItemStack stack;
        if (entity instanceof Player) {
            stack = ((Player) entity).getInventory().getHelmet();
            if (stack != null) {
                return stack;
            }
        } else if (entity instanceof LivingEntity) {
            stack = ((LivingEntity) entity).getEquipment().getHelmet();
            if (stack != null) {
                return stack;
            }
        }

        return new ItemStack(Material.POTATO);
    }

    public ItemStack returnItemStackChestplate(Entity entity) {
        ItemStack stack;
        if (entity instanceof Player) {
            stack = ((Player) entity).getInventory().getChestplate();
            if (stack != null) {
                return stack;
            }
        } else if (entity instanceof LivingEntity) {
            stack = ((LivingEntity) entity).getEquipment().getChestplate();
            if (stack != null) {
                return stack;
            }
        }

        return new ItemStack(Material.POTATO);
    }

    public ItemStack returnItemStackLeggings(Entity entity) {
        ItemStack stack;
        if (entity instanceof Player) {
            stack = ((Player) entity).getInventory().getLeggings();
            if (stack != null) {
                return stack;
            }
        } else if (entity instanceof LivingEntity) {
            stack = ((LivingEntity) entity).getEquipment().getLeggings();
            if (stack != null) {
                return stack;
            }
        }

        return new ItemStack(Material.POTATO);
    }

    public ItemStack returnItemStackBoots(Entity entity) {
        ItemStack stack;
        if (entity instanceof Player) {
            stack = ((Player) entity).getInventory().getBoots();
            if (stack != null) {
                return stack;
            }
        } else if (entity instanceof LivingEntity) {
            stack = ((LivingEntity) entity).getEquipment().getBoots();
            if (stack != null) {
                return stack;
            }
        }

        return new ItemStack(Material.POTATO);
    }

    public ItemStack returnItemStackInHand(Entity entity) {
        ItemStack stack;
        if (entity instanceof Player) {
            stack = ((Player) entity).getItemInHand();
            if (stack != null) {
                return stack;
            }
        } else if (entity instanceof LivingEntity) {
            stack = ((LivingEntity) entity).getEquipment().getItemInHand();
            if (stack != null) {
                return stack;
            }
        }

        return new ItemStack(Material.POTATO);
    }

    public String returnEntityName(Entity entity) {
        String name;
        if (entity instanceof Player) {
            name = ((Player) entity).getName();
            return name;
        } else if (entity instanceof LivingEntity) {
            if (((LivingEntity) entity).getCustomName() != null) {
                name = ((LivingEntity) entity).getCustomName();
                return name;
            } else {
                name = entity.getType().toString().substring(0, 1) + entity.getType().toString().substring(1).toLowerCase().replace("_", " ");
                return name;
            }
        } else {
            return null;
        }
    }

    public double returnEntityCurrentHealth(Entity entity) {
        double health;
        if (entity instanceof Player) {
            health = ((Player) entity).getHealth();
            return health;
        } else if (entity instanceof LivingEntity) {
            health = ((LivingEntity) entity).getHealth();
            return health;
        } else {
            return 0.0D;
        }
    }

    public double returnEntityMaxHealth(Entity entity) {
        double health;
        if (entity instanceof Player) {
            health = ((Player) entity).getMaxHealth();
            return health;
        } else if (entity instanceof LivingEntity) {
            health = ((LivingEntity) entity).getMaxHealth();
            return health;
        } else {
            return 0.0D;
        }
    }

    public void setEntityCurrentHealth(Entity entity, double health) {
        if (entity instanceof Player) {
            ((Player) entity).setHealth(health);
        } else if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).setHealth(health);
        }

    }

    public void setEntityMaxHealth(Entity entity, double health) {
        if (entity instanceof Player) {
            ((Player) entity).setMaxHealth(health);
        } else if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).setMaxHealth(health);
        }
    }
}
