package com.github.supavitax.itemlorestats.Crafting;

import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_Format;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AddedStats implements Listener {

    Util_Colours util_Colours = new Util_Colours();
    Util_Format util_Format = new Util_Format();


    private int randomRange(int min, int max) {
        return (int) ((double) min + Math.random() * (double) (max - min));
    }

    private double randomRangeDouble(double min, double max) {
        return min + Math.random() * (max - min);
    }

    @EventHandler
    public void addStatsToCraftedItem(PrepareItemCraftEvent event) {
        if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getView().getPlayer().getWorld().getName())) {
            if (event.isRepair()) {
                event.getInventory().setResult((ItemStack) null);
            } else if (!event.getRecipe().getResult().getItemMeta().hasLore() && (ItemLoreStats.plugin.isTool(event.getRecipe().getResult().getType()) || ItemLoreStats.plugin.isArmour(event.getRecipe().getResult().getType()))) {
                ItemStack craftedItem = event.getRecipe().getResult();
                boolean rename = false;
                ArrayList setItemLore = new ArrayList();
                String craftedItemMeta;
                String durabilityName;
                String durabilitySplitter;
                String type;
                String selectedDurability;
                if (ItemLoreStats.plugin.isArmour(event.getRecipe().getResult().getType())) {
                    if (ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedArmour.enableArmourOnCrafted")) {
                        craftedItemMeta = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name");
                        durabilityName = "";
                        durabilitySplitter = "";
                        if (craftedItem.getType().toString().contains("LEATHER")) {
                            durabilityName = "leather";
                            rename = true;
                        } else if (craftedItem.getType().toString().contains("CHAINMAIL")) {
                            durabilityName = "chainmail";
                            rename = true;
                        } else if (craftedItem.getType().toString().contains("IRON")) {
                            durabilityName = "iron";
                            rename = true;
                        } else if (craftedItem.getType().toString().contains("GOLD")) {
                            durabilityName = "gold";
                            rename = true;
                        } else if (craftedItem.getType().toString().contains("DIAMOND")) {
                            durabilityName = "diamond";
                            rename = true;
                        } else if (craftedItem.getType().toString().contains("SKULL_ITEM")) {
                            durabilityName = "mobHead";
                            rename = true;
                        } else {
                            durabilityName = null;
                            rename = false;
                        }

                        if (durabilityName != null) {
                            setItemLore.add("");
                            if (ItemLoreStats.plugin.isArmour(craftedItem.getType()) && ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + durabilityName) != null) {
                                if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + durabilityName).contains("-")) {
                                    durabilitySplitter = String.valueOf(this.randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + durabilityName).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + durabilityName).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                                    type = ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armourStatFormat").replace("[statName]", craftedItemMeta).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(durabilitySplitter))));
                                    setItemLore.add(this.util_Colours.replaceTooltipColour(type));
                                } else {
                                    durabilitySplitter = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + durabilityName).replaceAll("&([0-9a-f])", "").trim());
                                    type = ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armourStatFormat").replace("[statName]", craftedItemMeta).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(durabilitySplitter))));
                                    setItemLore.add(this.util_Colours.replaceTooltipColour(type));
                                }
                            }
                        }
                    }
                } else if (ItemLoreStats.plugin.isTool(event.getRecipe().getResult().getType()) && ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedDamage.enableDamageOnCrafted")) {
                    craftedItemMeta = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
                    durabilityName = "";
                    durabilitySplitter = "";
                    type = "";
                    if (craftedItem.getType().toString().contains("BOW")) {
                        durabilityName = "bow";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("WOOD")) {
                        durabilityName = "wood";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("STONE")) {
                        durabilityName = "stone";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("IRON")) {
                        durabilityName = "iron";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("GOLD")) {
                        durabilityName = "gold";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("DIAMOND")) {
                        durabilityName = "diamond";
                        rename = true;
                    } else {
                        durabilityName = null;
                        rename = false;
                    }

                    if (durabilityName != null) {
                        setItemLore.add("");
                        if (ItemLoreStats.plugin.isTool(craftedItem.getType()) && ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName) != null) {
                            if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).contains("-")) {
                                durabilitySplitter = String.valueOf(this.randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                                type = String.valueOf(this.randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(durabilitySplitter)));
                                selectedDurability = ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.damageStatFormat").replace("[statName]", craftedItemMeta).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(durabilitySplitter)))).replace("[statValue2]", String.valueOf(this.util_Format.format(Double.parseDouble(type))));
                                setItemLore.add(this.util_Colours.replaceTooltipColour(selectedDurability));
                            } else {
                                durabilitySplitter = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).replaceAll("&([0-9a-f])", "").trim());
                                type = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).replaceAll("&([0-9a-f])", "").trim());
                                selectedDurability = ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.damageStatFormat").replace("[statName]", craftedItemMeta).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(durabilitySplitter)))).replace("-[statValue2]", "");
                                setItemLore.add(this.util_Colours.replaceTooltipColour(selectedDurability));
                            }
                        }
                    }
                }

                if (!ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO") && ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedDurability.enableDurabilityOnCrafted")) {
                    craftedItemMeta = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.colour");
                    durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
                    durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
                    type = "";
                    selectedDurability = "";
                    if (craftedItem.getType().toString().contains("WOOD")) {
                        type = "wood";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("LEATHER")) {
                        type = "leather";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("STONE")) {
                        type = "stone";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("CHAINMAIL")) {
                        type = "chainmail";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("IRON")) {
                        type = "iron";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("GOLD")) {
                        type = "gold";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("DIAMOND")) {
                        type = "diamond";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("BOW")) {
                        type = "bow";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("SHEARS")) {
                        type = "shears";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("STICK")) {
                        type = null;
                        rename = false;
                    } else if (craftedItem.getType().toString().contains("BLAZE_ROD")) {
                        type = null;
                        rename = false;
                    } else if (craftedItem.getType().toString().contains("STRING")) {
                        type = null;
                        rename = false;
                    } else if (craftedItem.getType().toString().contains("FLINT_AND_STEEL")) {
                        type = "flintAndSteel";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("FISHING_ROD")) {
                        type = "fishingRod";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("CARROT_STICK")) {
                        type = "carrotStick";
                        rename = true;
                    } else if (craftedItem.getType().toString().contains("SKULL_ITEM")) {
                        type = "mobHead";
                        rename = true;
                    } else {
                        type = null;
                        rename = false;
                    }

                    if (type != null) {
                        setItemLore.add("");
                        if (ItemLoreStats.plugin.isTool(craftedItem.getType())) {
                            if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type) != null) {
                                if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).contains("-")) {
                                    selectedDurability = String.valueOf(this.randomRange(Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                                    setItemLore.add(this.util_Colours.replaceTooltipColour(craftedItemMeta) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type))) + selectedDurability + this.util_Colours.replaceTooltipColour(craftedItemMeta) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type))) + selectedDurability);
                                } else {
                                    selectedDurability = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).replaceAll("&([0-9a-f])", "").trim());
                                    setItemLore.add(this.util_Colours.replaceTooltipColour(craftedItemMeta) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type))) + selectedDurability + this.util_Colours.replaceTooltipColour(craftedItemMeta) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type))) + selectedDurability);
                                }
                            }
                        } else if (ItemLoreStats.plugin.isArmour(craftedItem.getType()) && ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type) != null) {
                            if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).contains("-")) {
                                selectedDurability = String.valueOf(this.randomRange(Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                                setItemLore.add(this.util_Colours.replaceTooltipColour(craftedItemMeta) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type))) + selectedDurability + this.util_Colours.replaceTooltipColour(craftedItemMeta) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type))) + selectedDurability);
                            } else {
                                selectedDurability = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).replaceAll("&([0-9a-f])", "").trim());
                                setItemLore.add(this.util_Colours.replaceTooltipColour(craftedItemMeta) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type))) + selectedDurability + this.util_Colours.replaceTooltipColour(craftedItemMeta) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type))) + selectedDurability);
                            }
                        }
                    }
                }

                if (rename) {
                    ItemMeta craftedItemMeta1 = craftedItem.getItemMeta();
                    craftedItemMeta1.setDisplayName(ChatColor.RESET + event.getRecipe().getResult().getType().toString().replaceAll("SKULL_ITEM", "Decapitated Head").replaceAll("WOOD_", "Wooden ").replaceAll("LEATHER_", "Leather ").replaceAll("STONE_", "Stone ").replaceAll("CHAINMAIL_", "Chainmail ").replaceAll("IRON_", "Iron ").replaceAll("GOLD_", "Golden ").replaceAll("DIAMOND_", "Diamond ").replaceAll("FISHING_ROD", "Fishing Rod").replaceAll("BOW", "Bow").replaceAll("SHEARS", "Shears").replaceAll("FLINT_AND_STEEL", "Flint and Steel").replaceAll("CARROT_STICK", "Carrot on a Stick").replaceAll("SWORD", "Sword").replaceAll("HOE", "Hoe").replaceAll("SPADE", "Spade").replaceAll("PICKAXE", "Pickaxe").replaceAll("AXE", "Axe").replaceAll("HELMET", "Helmet").replaceAll("CHESTPLATE", "Chestplate").replaceAll("LEGGINGS", "Leggings").replaceAll("BOOTS", "Boots"));
                    craftedItemMeta1.setLore(setItemLore);
                    craftedItem.setItemMeta(craftedItemMeta1);
                    event.getInventory().setResult(craftedItem);
                }
            }
        }

    }

    @EventHandler
    public void addStatsToDroppedItem(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getEntity().getLocation().getWorld().getName())) {
                for (int i = 0; i < event.getDrops().size(); ++i) {
                    if (!((ItemStack) event.getDrops().get(i)).getItemMeta().hasLore() && (ItemLoreStats.plugin.isTool(((ItemStack) event.getDrops().get(i)).getType()) || ItemLoreStats.plugin.isArmour(((ItemStack) event.getDrops().get(i)).getType())) && ((ItemStack) event.getDrops().get(i)).getItemMeta().getDisplayName() == null && (!((ItemStack) event.getDrops().get(i)).getItemMeta().hasLore() || !((ItemStack) event.getDrops().get(i)).getItemMeta().getLore().toString().contains(ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name") + ":"))) {
                        ItemStack droppedItem = (ItemStack) event.getDrops().get(i);
                        ItemMeta droppedItemMeta = droppedItem.getItemMeta();
                        boolean rename = false;
                        ArrayList setItemLore = new ArrayList();
                        String durabilityColour;
                        String durabilityName;
                        String durabilitySplitter;
                        String type;
                        String selectedDurability;
                        if (ItemLoreStats.plugin.isArmour(droppedItem.getType())) {
                            if (ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedArmour.enableArmourOnDrops")) {
                                durabilityColour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name");
                                durabilityName = "";
                                durabilitySplitter = "";
                                if (droppedItem.getType().toString().contains("LEATHER")) {
                                    durabilityName = "leather";
                                    rename = true;
                                } else if (droppedItem.getType().toString().contains("CHAINMAIL")) {
                                    durabilityName = "chainmail";
                                    rename = true;
                                } else if (droppedItem.getType().toString().contains("IRON")) {
                                    durabilityName = "iron";
                                    rename = true;
                                } else if (droppedItem.getType().toString().contains("GOLD")) {
                                    durabilityName = "gold";
                                    rename = true;
                                } else if (droppedItem.getType().toString().contains("DIAMOND")) {
                                    durabilityName = "diamond";
                                    rename = true;
                                } else if (droppedItem.getType().toString().contains("SKULL_ITEM")) {
                                    durabilityName = "mobHead";
                                    rename = true;
                                } else {
                                    durabilityName = null;
                                    rename = false;
                                }

                                if (durabilityName != null) {
                                    setItemLore.add("");
                                    if (ItemLoreStats.plugin.isArmour(droppedItem.getType()) && ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + durabilityName) != null) {
                                        if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + durabilityName).contains("-")) {
                                            durabilitySplitter = String.valueOf(this.randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + durabilityName).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + durabilityName).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                                            type = ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armourStatFormat").replace("[statName]", durabilityColour).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(durabilitySplitter))));
                                            setItemLore.add(this.util_Colours.replaceTooltipColour(type));
                                        } else {
                                            durabilitySplitter = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + durabilityName).replaceAll("&([0-9a-f])", "").trim());
                                            type = ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armourStatFormat").replace("[statName]", durabilityColour).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(durabilitySplitter))));
                                            setItemLore.add(this.util_Colours.replaceTooltipColour(type));
                                        }
                                    }
                                }
                            }
                        } else if (ItemLoreStats.plugin.isTool(droppedItem.getType()) && ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedDamage.enableDamageOnDrops")) {
                            durabilityColour = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
                            durabilityName = "";
                            durabilitySplitter = "";
                            type = "";
                            if (droppedItem.getType().toString().contains("BOW")) {
                                durabilityName = "bow";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("WOOD")) {
                                durabilityName = "wood";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("STONE")) {
                                durabilityName = "stone";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("IRON")) {
                                durabilityName = "iron";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("GOLD")) {
                                durabilityName = "gold";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("DIAMOND")) {
                                durabilityName = "diamond";
                                rename = true;
                            } else {
                                durabilityName = null;
                                rename = false;
                            }

                            if (durabilityName != null) {
                                setItemLore.add("");
                                if (ItemLoreStats.plugin.isTool(droppedItem.getType()) && ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName) != null) {
                                    if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).contains("-")) {
                                        durabilitySplitter = String.valueOf(this.randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                                        type = String.valueOf(this.randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(durabilitySplitter)));
                                        selectedDurability = ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.damageStatFormat").replace("[statName]", durabilityColour).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(durabilitySplitter)))).replace("[statValue2]", String.valueOf(this.util_Format.format(Double.parseDouble(type))));
                                        setItemLore.add(this.util_Colours.replaceTooltipColour(selectedDurability));
                                    } else {
                                        durabilitySplitter = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).replaceAll("&([0-9a-f])", "").trim());
                                        type = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + durabilityName).replaceAll("&([0-9a-f])", "").trim());
                                        selectedDurability = ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.damageStatFormat").replace("[statName]", durabilityColour).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(durabilitySplitter)))).replace("-[statValue2]", "");
                                        setItemLore.add(this.util_Colours.replaceTooltipColour(selectedDurability));
                                    }
                                }
                            }
                        }

                        if (!ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO") && ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedDurability.enableDurabilityOnDrops")) {
                            durabilityColour = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.colour");
                            durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
                            durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
                            type = "";
                            selectedDurability = "";
                            if (droppedItem.getType().toString().contains("WOOD")) {
                                type = "wood";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("LEATHER")) {
                                type = "leather";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("STONE")) {
                                type = "stone";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("CHAINMAIL")) {
                                type = "chainmail";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("IRON")) {
                                type = "iron";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("GOLD")) {
                                type = "gold";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("DIAMOND")) {
                                type = "diamond";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("BOW")) {
                                type = "bow";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("SHEARS")) {
                                type = "shears";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("STICK")) {
                                type = null;
                                rename = false;
                            } else if (droppedItem.getType().toString().contains("BLAZE_ROD")) {
                                type = null;
                                rename = false;
                            } else if (droppedItem.getType().toString().contains("STRING")) {
                                type = null;
                                rename = false;
                            } else if (droppedItem.getType().toString().contains("FLINT_AND_STEEL")) {
                                type = "flintAndSteel";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("FISHING_ROD")) {
                                type = "fishingRod";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("CARROT_STICK")) {
                                type = "carrotStick";
                                rename = true;
                            } else if (droppedItem.getType().toString().contains("SKULL_ITEM")) {
                                type = "mobHead";
                                rename = true;
                            } else {
                                type = null;
                            }

                            if (type != null) {
                                setItemLore.add("");
                                if (ItemLoreStats.plugin.isTool(droppedItem.getType())) {
                                    if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type) != null) {
                                        if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).contains("-")) {
                                            selectedDurability = String.valueOf(this.randomRange(Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                                            setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type))) + selectedDurability);
                                        } else {
                                            selectedDurability = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).replaceAll("&([0-9a-f])", "").trim());
                                            setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type))) + selectedDurability);
                                        }
                                    }
                                } else if (ItemLoreStats.plugin.isArmour(droppedItem.getType()) && ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type) != null) {
                                    if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).contains("-")) {
                                        selectedDurability = String.valueOf(this.randomRange(Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                                        setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type))) + selectedDurability);
                                    } else {
                                        selectedDurability = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).replaceAll("&([0-9a-f])", "").trim());
                                        setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type))) + selectedDurability);
                                    }
                                }
                            }
                        }

                        if (rename) {
                            droppedItemMeta.setDisplayName(ChatColor.RESET + ((ItemStack) event.getDrops().get(i)).getType().toString().replaceAll("SKULL_ITEM", "Decapitated Head").replaceAll("WOOD_", "Wooden ").replaceAll("LEATHER_", "Leather ").replaceAll("STONE_", "Stone ").replaceAll("IRON_", "Iron ").replaceAll("GOLD_", "Golden ").replaceAll("DIAMOND_", "Diamond ").replaceAll("FISHING_ROD", "Fishing Rod").replaceAll("BOW", "Bow").replaceAll("SHEARS", "Shears").replaceAll("FLINT_AND_STEEL", "Flint and Steel").replaceAll("CARROT_STICK", "Carrot on a Stick").replaceAll("SWORD", "Sword").replaceAll("HOE", "Hoe").replaceAll("SPADE", "Spade").replaceAll("PICKAXE", "Pickaxe").replaceAll("AXE", "Axe").replaceAll("HELMET", "Helmet").replaceAll("CHESTPLATE", "Chestplate").replaceAll("LEGGINGS", "Leggings").replaceAll("BOOTS", "Boots"));
                        }

                        droppedItemMeta.setLore(setItemLore);
                        droppedItem.setItemMeta(droppedItemMeta);
                        if (event.getDrops().get(i) != null) {
                            event.getDrops().remove(i);
                        }

                        event.getDrops().add(droppedItem);
                    }
                }
            }

        }
    }
}
