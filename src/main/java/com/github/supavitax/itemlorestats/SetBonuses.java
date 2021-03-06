package com.github.supavitax.itemlorestats;

import com.github.supavitax.itemlorestats.Util.Util_Colours;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class SetBonuses {
    private FileConfiguration PlayerDataConfig;
    GearStats gearStats = new GearStats();
    Util_Colours util_Colours = new Util_Colours();

    public void updateSetBonus(Player player) {
        try {
            this.PlayerDataConfig = new YamlConfiguration();
            this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + "SetBonuses.yml"));
            String e = "";
            String gearDodge = "";
            String gearBlock = "";
            String gearCritChance = "";
            String gearCritDamage = "";
            String gearDamage = "";
            String gearHealth = "";
            String gearHealthRegen = "";
            String gearLifeSteal = "";
            String gearReflect = "";
            String gearFire = "";
            String gearIce = "";
            String gearPoison = "";
            String gearWither = "";
            String gearHarming = "";
            String gearBlind = "";
            String gearXPMultiplier = "";
            String gearSpeed = "";
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".armour", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".dodge", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".block", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".damage", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critChance", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critDamage", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".health", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".healthRegen", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".lifeSteal", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".reflect", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".fire", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".ice", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".poison", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".wither", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".harming", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".blind", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".xpMultiplier", 0.0D);
            ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".speed", 0.0D);

            for (int a = this.PlayerDataConfig.getKeys(false).size() - 1; a >= 0 && a <= this.PlayerDataConfig.getKeys(false).size() - 1; --a) {
                int x = 0;
                String key = this.PlayerDataConfig.getKeys(false).toString().split(",")[a].replaceAll("\\[", "").replaceAll("\\]", "").trim();
                if (key != null) {
                    if (this.gearStats.getSetBonusHead(player).equals(key)) {
                        ++x;
                    }

                    if (this.gearStats.getSetBonusChest(player).equals(key)) {
                        ++x;
                    }

                    if (this.gearStats.getSetBonusLegs(player).equals(key)) {
                        ++x;
                    }

                    if (this.gearStats.getSetBonusBoots(player).equals(key)) {
                        ++x;
                    }

                    if (this.gearStats.getSetBonusItemInHand(player).equals(key)) {
                        ++x;
                    }

                    for (int b = this.PlayerDataConfig.getConfigurationSection(key).getKeys(false).size() - 1; b >= 0 && b <= this.PlayerDataConfig.getConfigurationSection(key).getKeys(false).size() - 1; --b) {
                        if (this.PlayerDataConfig.getConfigurationSection(key).getKeys(false).toString().split(",")[b].replaceAll("\\[", "").replaceAll("\\]", "").trim() != null) {
                            String keyFromParentKey = this.PlayerDataConfig.getConfigurationSection(key).getKeys(false).toString().split(",")[b].replaceAll("\\[", "").replaceAll("\\]", "").trim();
                            e = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".armour");
                            gearDodge = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".dodge");
                            gearBlock = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".block");
                            gearCritChance = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".critChance");
                            gearCritDamage = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".critDamage");
                            gearDamage = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".damage");
                            gearHealth = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".health");
                            gearHealthRegen = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".healthRegen");
                            gearLifeSteal = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".lifeSteal");
                            gearReflect = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".reflect");
                            gearFire = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".fire");
                            gearIce = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".ice");
                            gearPoison = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".poison");
                            gearWither = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".wither");
                            gearHarming = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".harming");
                            gearBlind = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".blind");
                            gearXPMultiplier = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".xpMultiplier");
                            gearSpeed = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".movementSpeed");
                            if (x >= Integer.parseInt(keyFromParentKey)) {
                                if (e != null) {
                                    player.sendMessage("armour hashmap set to " + Integer.parseInt(e));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".armour", Double.parseDouble(e));
                                } else {
                                    player.sendMessage("armour hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".armour", 0.0D);
                                }

                                if (gearDodge != null) {
                                    player.sendMessage("dodge hashmap set to " + Integer.parseInt(gearDodge));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".dodge", Double.parseDouble(gearDodge));
                                } else {
                                    player.sendMessage("dodge hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".dodge", 0.0D);
                                }

                                if (gearBlock != null) {
                                    player.sendMessage("block hashmap set to " + Integer.parseInt(gearBlock));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".block", Double.parseDouble(gearBlock));
                                } else {
                                    player.sendMessage("block hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".block", 0.0D);
                                }

                                if (gearDamage != null) {
                                    player.sendMessage("damage hashmap set to " + Integer.parseInt(gearDamage));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".damage", Double.parseDouble(gearDamage));
                                } else {
                                    player.sendMessage("damage hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".damage", 0.0D);
                                }

                                if (gearCritChance != null) {
                                    player.sendMessage("critchance hashmap set to " + Integer.parseInt(gearCritChance));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critChance", Double.parseDouble(gearCritChance));
                                } else {
                                    player.sendMessage("critchance hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critChance", 0.0D);
                                }

                                if (gearCritDamage != null) {
                                    player.sendMessage("critdamage hashmap set to " + Integer.parseInt(gearCritDamage));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critDamage", Double.parseDouble(gearCritDamage));
                                } else {
                                    player.sendMessage("critdamage hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critDamage", 0.0D);
                                }

                                if (gearHealth != null) {
                                    player.sendMessage("health hashmap set to " + Integer.parseInt(gearHealth));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".health", Double.parseDouble(gearHealth));
                                } else {
                                    player.sendMessage("health hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".health", 0.0D);
                                }

                                if (gearHealthRegen != null) {
                                    player.sendMessage("health regen hashmap set to " + Integer.parseInt(gearHealthRegen));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".healthRegen", Double.parseDouble(gearHealthRegen));
                                } else {
                                    player.sendMessage("health regen hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".healthRegen", 0.0D);
                                }

                                if (gearLifeSteal != null) {
                                    player.sendMessage("lifesteal hashmap set to " + Integer.parseInt(gearLifeSteal));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".lifeSteal", Double.parseDouble(gearLifeSteal));
                                } else {
                                    player.sendMessage("lifesteal hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".lifeSteal", 0.0D);
                                }

                                if (gearReflect != null) {
                                    player.sendMessage("reflect hashmap set to " + Integer.parseInt(gearReflect));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".reflect", Double.parseDouble(gearReflect));
                                } else {
                                    player.sendMessage("reflect hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".reflect", 0.0D);
                                }

                                if (gearFire != null) {
                                    player.sendMessage("fire hashmap set to " + Integer.parseInt(gearFire));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".fire", Double.parseDouble(gearFire));
                                } else {
                                    player.sendMessage("fire hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".fire", 0.0D);
                                }

                                if (gearIce != null) {
                                    player.sendMessage("ice hashmap set to " + Integer.parseInt(gearIce));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".ice", Double.parseDouble(gearIce));
                                } else {
                                    player.sendMessage("ice hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".ice", 0.0D);
                                }

                                if (gearPoison != null) {
                                    player.sendMessage("poison hashmap set to " + Integer.parseInt(gearPoison));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".poison", Double.parseDouble(gearPoison));
                                } else {
                                    player.sendMessage("poison hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".poison", 0.0D);
                                }

                                if (gearWither != null) {
                                    player.sendMessage("wither hashmap set to " + Integer.parseInt(gearWither));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".wither", Double.parseDouble(gearWither));
                                } else {
                                    player.sendMessage("wither hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".wither", 0.0D);
                                }

                                if (gearHarming != null) {
                                    player.sendMessage("harming hashmap set to " + Integer.parseInt(gearHarming));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".harming", Double.parseDouble(gearHarming));
                                } else {
                                    player.sendMessage("harming hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".harming", 0.0D);
                                }

                                if (gearBlind != null) {
                                    player.sendMessage("blind hashmap set to " + Integer.parseInt(gearBlind));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".blind", Double.parseDouble(gearBlind));
                                } else {
                                    player.sendMessage("blind hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".blind", 0.0D);
                                }

                                if (gearXPMultiplier != null) {
                                    player.sendMessage("xpMultiplier hashmap set to " + Integer.parseInt(gearXPMultiplier));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".xpMultiplier", Double.parseDouble(gearXPMultiplier));
                                } else {
                                    player.sendMessage("xpMultiplier hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".xpMultiplier", 0.0D);
                                }

                                if (gearSpeed != null) {
                                    player.sendMessage("speed hashmap set to " + Integer.parseInt(gearSpeed));
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".speed", Double.parseDouble(gearSpeed));
                                } else {
                                    player.sendMessage("speed hashmap set to 0");
                                    ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".speed", 0.0D);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception var25) {
            var25.printStackTrace();
            System.out.println("*********** Failed to load set bonus for " + player.getName() + "! ***********");
        }

    }

    public double checkHashMapArmour(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".armour") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".armour");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".armour");
        }
    }

    public double checkHashMapDodge(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".dodge") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".dodge");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".dodge");
        }
    }

    public double checkHashMapBlock(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".block") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".block");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".block");
        }
    }

    public double checkHashMapDamage(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".damage") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".damage");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".damage");
        }
    }

    public double checkHashMapCritChance(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critChance") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critChance");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critChance");
        }
    }

    public double checkHashMapCritDamage(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critDamage") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critDamage");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critDamage");
        }
    }

    public double checkHashMapHealth(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".health") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".health");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".health");
        }
    }

    public double checkHashMapHealthRegen(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".healthRegen") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".healthRegen");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".healthRegen");
        }
    }

    public double checkHashMapLifeSteal(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".lifeSteal") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".lifeSteal");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".lifeSteal");
        }
    }

    public double checkHashMapReflect(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".reflect") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".reflect");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".reflect");
        }
    }

    public double checkHashMapFire(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".fire") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".fire");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".fire");
        }
    }

    public double checkHashMapIce(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".ice") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".ice");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".ice");
        }
    }

    public double checkHashMapPoison(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".poison") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".poison");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".poison");
        }
    }

    public double checkHashMapWither(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".wither") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".wither");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".wither");
        }
    }

    public double checkHashMapHarming(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".harming") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".harming");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".harming");
        }
    }

    public double checkHashMapBlind(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".blind") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".blind");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".blind");
        }
    }

    public double checkHashMapXPMultiplier(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".xpMultiplier") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".xpMultiplier");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".xpMultiplier");
        }
    }

    public double checkHashMapSpeed(Player player) {
        if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".speed") != null) {
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".speed");
        } else {
            ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
            return (Double) ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".speed");
        }
    }
}
