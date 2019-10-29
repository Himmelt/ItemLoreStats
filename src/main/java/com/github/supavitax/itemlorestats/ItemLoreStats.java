package com.github.supavitax.itemlorestats;

import com.github.supavitax.itemlorestats.Commands.CustomMaterial_Com;
import com.github.supavitax.itemlorestats.Commands.Export_Com;
import com.github.supavitax.itemlorestats.Commands.Name_Com;
import com.github.supavitax.itemlorestats.Commands.Repair_Com;
import com.github.supavitax.itemlorestats.Crafting.AddedStats;
import com.github.supavitax.itemlorestats.Damage.DamageSystem;
import com.github.supavitax.itemlorestats.Damage.EnvironmentalDamage;
import com.github.supavitax.itemlorestats.Damage.HealPotions;
import com.github.supavitax.itemlorestats.Durability.Durability;
import com.github.supavitax.itemlorestats.Durability.DurabilityEvents;
import com.github.supavitax.itemlorestats.Interact.InteractEvents;
import com.github.supavitax.itemlorestats.ItemUpgrading.ItemUpgrade;
import com.github.supavitax.itemlorestats.ItemUpgrading.PlayerLevelEvents;
import com.github.supavitax.itemlorestats.Misc.MetricsLite;
import com.github.supavitax.itemlorestats.Misc.SpigotStatCapWarning;
import com.github.supavitax.itemlorestats.Misc.Updater;
import com.github.supavitax.itemlorestats.Misc.WriteDefaultFiles;
import com.github.supavitax.itemlorestats.Repair.RepairEvents;
import com.github.supavitax.itemlorestats.Util.*;
import com.herocraftonline.heroes.Heroes;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sucy.skill.SkillAPI;
import me.confuser.barapi.BarAPI;
import net.citizensnpcs.Citizens;
import net.milkbowl.vault.Vault;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.inventivetalent.bossbar.BossBarPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public final class ItemLoreStats extends JavaPlugin {
    public static ItemLoreStats plugin;
    private static final Logger log = Logger.getLogger("Minecraft");
    private File PlayerDataFile;
    private FileConfiguration PlayerDataConfig;
    public Util_BarAPI util_BarAPI;
    public Util_BossBarAPI util_BossBarAPI;
    public Util_Citizens util_Citizens;
    public Util_Heroes util_Heroes;
    public Util_SkillAPI util_SkillAPI;
    public Util_Vault util_Vault;
    public Util_WorldGuard util_WorldGuard;
    public HashMap spellCooldowns = new HashMap();
    public HashMap internalCooldowns = new HashMap();
    public HashMap combatLogVisible = new HashMap();
    public HashMap setBonusesModifiers = new HashMap();
    CharacterSheet characterSheet = new CharacterSheet();
    Classes classes = new Classes();
    DamageSystem damageSystem = new DamageSystem(this);
    Durability durability = new Durability();
    EnvironmentalDamage environmentalDamage = new EnvironmentalDamage();
    GearStats gearStats = new GearStats();
    GenerateFromFile generateFromFile = new GenerateFromFile();
    ItemUpgrade itemUpgrade = new ItemUpgrade();
    public SetBonuses setBonuses = new SetBonuses();
    Soulbound soulbound = new Soulbound();
    WriteDefaultFiles writeDefaultFiles = new WriteDefaultFiles();
    XpLevel xpLevel = new XpLevel();
    Util_Colours util_Colours = new Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Random util_Random = new Util_Random();
    SpigotStatCapWarning spigotStatCapWarning = new SpigotStatCapWarning();
    CustomMaterial_Com customMaterial_Com = new CustomMaterial_Com();
    Export_Com export_Com = new Export_Com();
    Name_Com name_Com = new Name_Com();
    Repair_Com repair_Com = new Repair_Com();

    private int setMinecraftBuildNumber(String buildNum) {
        String version = buildNum.split("-")[0].replace(".", "");
        this.getConfig().set("serverVersion", Integer.parseInt(version));
        return Integer.parseInt(version);
    }

    public int getMinecraftBuildNumber(String buildNum) {
        String version = buildNum.split("-")[0].replace(".", "");
        return Integer.parseInt(version);
    }

    public int BukkitVersion() {
        return this.getConfig().getInt("serverVersion");
    }

    @Override
    public void onEnable() {
        this.checkDependencies();
        PluginManager plma = this.getServer().getPluginManager();
        plma.registerEvents(new ItemLoreStats.ItemLoreStatsListener(), this);
        plma.registerEvents(new AddedStats(), this);
        plma.registerEvents(new DamageSystem(this), this);
        plma.registerEvents(new DurabilityEvents(), this);
        plma.registerEvents(new EnvironmentalDamage(), this);
        plma.registerEvents(new EntityDrops(), this);
        plma.registerEvents(new HealPotions(), this);
        plma.registerEvents(new InteractEvents(), this);
        if (this.getSkillAPI() != null) {
            plma.registerEvents(new Util_SkillAPI(this), this);
        }

        plma.registerEvents(new PlayerLevelEvents(), this);
        plma.registerEvents(new RepairEvents(), this);
        plugin = this;
        this.writeDefaultFiles.checkExistence();

        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException var3) {
        }

        this.getConfig().options().copyDefaults(true);
        this.setMinecraftBuildNumber(Bukkit.getBukkitVersion());
        this.getConfig().set("fileVersion", Integer.parseInt(this.getDescription().getVersion().replace(".", "")));
        this.saveConfig();
        this.spigotStatCapWarning.updateSpigotValues();
    }

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", this.getDescription().getName(), this.getDescription().getVersion()));
    }

    public static ItemLoreStats getPlugin() {
        return (ItemLoreStats) Bukkit.getPluginManager().getPlugin("ItemLoreStats");
    }

    public void checkDependencies() {
        if (this.getWorldGuard() != null) {
            log.info("[ItemLoreStats] Successfully found and hooked into WorldGuard.");
        } else {
            log.info("[ItemLoreStats] Unable to find WorldGuard!");
        }

        if (this.getHeroes() != null) {
            log.info("[ItemLoreStats] Successfully found and hooked into Heroes.");
        } else {
            log.info("[ItemLoreStats] Unable to find Heroes!");
        }

        if (this.getBarAPI() != null) {
            log.info("[ItemLoreStats] Successfully found and hooked into BarAPI.");
        } else {
            log.info("[ItemLoreStats] Unable to find BarAPI!");
        }

        if (this.getBossBarAPI() != null) {
            log.info("[ItemLoreStats] Successfully found and hooked into BossBarAPI.");
        } else {
            log.info("[ItemLoreStats] Unable to find BossBarAPI!");
        }

        if (this.getSkillAPI() != null) {
            log.info("[ItemLoreStats] Successfully found and hooked into SkillAPI.");
        } else {
            log.info("[ItemLoreStats] Unable to find SkillAPI!");
        }

        if (this.getVault() != null) {
            log.info("[ItemLoreStats] Successfully found and hooked into Vault.");
        } else {
            log.info("[ItemLoreStats] Unable to find Vault!");
        }

        if (this.getCitizens() != null) {
            log.info("[ItemLoreStats] Successfully found and hooked into Citizens.");
        } else {
            log.info("[ItemLoreStats] Unable to find Citizens!");
        }

    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin WorldGuard = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (WorldGuard != null && WorldGuard instanceof WorldGuardPlugin) {
            this.util_WorldGuard = new Util_WorldGuard(plugin);
            return (WorldGuardPlugin) WorldGuard;
        } else {
            return null;
        }
    }

    public Vault getVault() {
        Plugin Vault = Bukkit.getServer().getPluginManager().getPlugin("Vault");
        if (Vault != null && Vault instanceof Vault) {
            this.util_Vault = new Util_Vault(plugin);
            return (Vault) Vault;
        } else {
            return null;
        }
    }

    public Heroes getHeroes() {
        Plugin Heroes = Bukkit.getServer().getPluginManager().getPlugin("Heroes");
        if (Heroes != null && Heroes instanceof Heroes) {
            this.util_Heroes = new Util_Heroes(plugin);
            return (Heroes) Heroes;
        } else {
            return null;
        }
    }

    public BarAPI getBarAPI() {
        Plugin BarAPI = Bukkit.getServer().getPluginManager().getPlugin("BarAPI");
        if (BarAPI != null && BarAPI instanceof BarAPI) {
            this.util_BarAPI = new Util_BarAPI(plugin);
            return (BarAPI) BarAPI;
        } else {
            return null;
        }
    }

    public BossBarPlugin getBossBarAPI() {
        Plugin BossBarAPI = Bukkit.getServer().getPluginManager().getPlugin("BossBarAPI");
        if (BossBarAPI != null && BossBarAPI instanceof BossBarPlugin) {
            this.util_BossBarAPI = new Util_BossBarAPI(plugin);
            return (BossBarPlugin) BossBarAPI;
        } else {
            return null;
        }
    }

    public SkillAPI getSkillAPI() {
        Plugin SkillAPI = Bukkit.getServer().getPluginManager().getPlugin("SkillAPI");
        if (SkillAPI != null && SkillAPI instanceof SkillAPI) {
            this.util_SkillAPI = new Util_SkillAPI(plugin);
            return (SkillAPI) SkillAPI;
        } else {
            return null;
        }
    }

    public Citizens getCitizens() {
        Plugin Citizens = Bukkit.getServer().getPluginManager().getPlugin("Citizens");
        if (Citizens != null && Citizens instanceof Citizens) {
            this.util_Citizens = new Util_Citizens(plugin);
            return (Citizens) Citizens;
        } else {
            return null;
        }
    }

    public boolean isTool(Material material) {
        for (int i = 0; i < plugin.getConfig().getList("materials.tools").size(); ++i) {
            if (plugin.getConfig().getList("materials.tools").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }

        return false;
    }

    public boolean isPotion(int potionID) {
        for (int i = 0; i < plugin.getConfig().getList("materials.potions").size(); ++i) {
            if (Integer.parseInt(plugin.getConfig().getList("materials.potions").get(i).toString().split(":")[0]) == potionID) {
                return true;
            }
        }

        return false;
    }

    public boolean isArmourSlot(String slot) {
        String[] inventorySlots = slot.split(",");
        String[] var6 = inventorySlots;
        int var5 = inventorySlots.length;

        for (int var4 = 0; var4 < var5; ++var4) {
            String theSlot = var6[var4];
            if (Integer.parseInt(theSlot.trim()) >= 5 && Integer.parseInt(theSlot.trim()) <= 8) {
                return true;
            }
        }

        return false;
    }

    public boolean isHotbarSlot(String slot) {
        String[] inventorySlots = slot.split(",");
        String[] var6 = inventorySlots;
        int var5 = inventorySlots.length;

        for (int var4 = 0; var4 < var5; ++var4) {
            String theSlot = var6[var4];
            if (Integer.parseInt(theSlot.trim()) >= 36 && Integer.parseInt(theSlot.trim()) <= 44) {
                return true;
            }
        }

        return false;
    }

    public int getRawHeldItemSlot(String slot) {
        String[] inventorySlots = slot.split(",");
        String[] var6 = inventorySlots;
        int var5 = inventorySlots.length;

        for (int var4 = 0; var4 < var5; ++var4) {
            String theSlot = var6[var4];
            if (Integer.parseInt(theSlot.trim()) >= 36 && Integer.parseInt(theSlot.trim()) <= 44) {
                if (Integer.parseInt(theSlot.trim()) == 36) {
                    return 0;
                }

                if (Integer.parseInt(theSlot.trim()) == 37) {
                    return 1;
                }

                if (Integer.parseInt(theSlot.trim()) == 38) {
                    return 2;
                }

                if (Integer.parseInt(theSlot.trim()) == 39) {
                    return 3;
                }

                if (Integer.parseInt(theSlot.trim()) == 40) {
                    return 4;
                }

                if (Integer.parseInt(theSlot.trim()) == 41) {
                    return 5;
                }

                if (Integer.parseInt(theSlot.trim()) == 42) {
                    return 6;
                }

                if (Integer.parseInt(theSlot.trim()) == 43) {
                    return 7;
                }

                if (Integer.parseInt(theSlot.trim()) == 44) {
                    return 8;
                }
            }
        }

        return -1;
    }

    public boolean isArmour(Material material) {
        List helmetList = plugin.getConfig().getList("materials.armour.helmet");
        List chestList = plugin.getConfig().getList("materials.armour.chest");
        List legsList = plugin.getConfig().getList("materials.armour.legs");
        List bootsList = plugin.getConfig().getList("materials.armour.boots");
        ArrayList armourList = new ArrayList();
        armourList.addAll(helmetList);
        armourList.addAll(chestList);
        armourList.addAll(legsList);
        armourList.addAll(bootsList);

        for (int i = 0; i < armourList.size(); ++i) {
            if (((String) armourList.get(i)).split(":")[0].equals(material.toString())) {
                return true;
            }
        }

        return false;
    }

    public boolean isHelmet(Material material) {
        for (int i = 0; i < plugin.getConfig().getList("materials.armour.helmet").size(); ++i) {
            if (plugin.getConfig().getList("materials.armour.helmet").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }

        return false;
    }

    public boolean isChestplate(Material material) {
        for (int i = 0; i < plugin.getConfig().getList("materials.armour.chest").size(); ++i) {
            if (plugin.getConfig().getList("materials.armour.chest").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }

        return false;
    }

    public boolean isLeggings(Material material) {
        for (int i = 0; i < plugin.getConfig().getList("materials.armour.legs").size(); ++i) {
            if (plugin.getConfig().getList("materials.armour.legs").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }

        return false;
    }

    public boolean isBoots(Material material) {
        for (int i = 0; i < plugin.getConfig().getList("materials.armour.boots").size(); ++i) {
            if (plugin.getConfig().getList("materials.armour.boots").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }

        return false;
    }

    public boolean isSword(ItemStack item) {
        return item.equals(Material.WOOD_SWORD) || item.equals(Material.STONE_SWORD) || item.equals(Material.IRON_SWORD) || item.equals(Material.GOLD_SWORD) || item.equals(Material.DIAMOND_SWORD);
    }

    public boolean isHoe(ItemStack item) {
        return item.equals(Material.WOOD_HOE) || item.equals(Material.STONE_HOE) || item.equals(Material.IRON_HOE) || item.equals(Material.GOLD_HOE) || item.equals(Material.DIAMOND_HOE);
    }

    public boolean isAxe(ItemStack item) {
        return item.equals(Material.WOOD_AXE) || item.equals(Material.STONE_AXE) || item.equals(Material.IRON_AXE) || item.equals(Material.GOLD_AXE) || item.equals(Material.DIAMOND_AXE);
    }

    public boolean isPickAxe(ItemStack item) {
        return item.equals(Material.WOOD_PICKAXE) || item.equals(Material.STONE_PICKAXE) || item.equals(Material.IRON_PICKAXE) || item.equals(Material.GOLD_PICKAXE) || item.equals(Material.DIAMOND_PICKAXE);
    }

    public boolean isSpade(ItemStack item) {
        return item.equals(Material.WOOD_SPADE) || item.equals(Material.STONE_SPADE) || item.equals(Material.IRON_SPADE) || item.equals(Material.GOLD_SPADE) || item.equals(Material.DIAMOND_SPADE);
    }

    public void updateBarAPI(final Player player) {
        if (plugin.getBarAPI() != null) {
            this.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                    ItemLoreStats.this.util_BarAPI.setBarValue(player, player.getHealth(), player.getMaxHealth());
                } else {
                    ItemLoreStats.this.util_BarAPI.removeBar(player);
                }

            }, 2L);
        } else if (plugin.getBossBarAPI() != null) {
            this.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                    ItemLoreStats.this.util_BossBarAPI.setBarValue(player, player.getHealth(), player.getMaxHealth());
                } else {
                    ItemLoreStats.this.util_BossBarAPI.removeBar(player);
                }

            }, 2L);
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        final Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            player = null;
        }

        if ("ils".equalsIgnoreCase(cmd.getName())) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    sender.sendMessage(ChatColor.GOLD + "Item Lore Stats " + ChatColor.LIGHT_PURPLE + "commands:");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils reload");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils stats");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils version");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils name " + ChatColor.GOLD + "<text>");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils lore " + ChatColor.GOLD + "<player_name> <line#> <text>");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils give " + ChatColor.GOLD + "<player_name> <item_name>");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils give " + ChatColor.GOLD + "<player_name> <item_name>, <new_item_name>");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils custom tool " + ChatColor.GOLD + "<Item Type Name>");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils custom armour helmet/chest/legs/boots " + ChatColor.GOLD + "<Item Type Name>");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils repair");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils upgrade hand");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils upgrade armour");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils upgrade all");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils combatLog");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils sell");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils export " + ChatColor.GOLD + "<item_name>");
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils setMultiplier");
                } else {
                    System.out.println("Item Lore Stats commands:");
                    System.out.println("   /ils");
                    System.out.println("   /ils reload");
                    System.out.println("   /ils stats");
                    System.out.println("   /ils version");
                    System.out.println("   /ils name <text>");
                    System.out.println("   /ils lore <player_name> <line#> <text>");
                    System.out.println("   /ils give <player_name> <item_name>");
                    System.out.println("   /ils give <player_name> <item_name>, <new_item_name>");
                    System.out.println("   /ils custom tool <Item Type Name>");
                    System.out.println("   /ils custom armour helmet/chest/legs/boots <Item Type Name>");
                    System.out.println("   /ils repair");
                    System.out.println("   /ils upgrade hand");
                    System.out.println("   /ils upgrade armour");
                    System.out.println("   /ils upgrade all");
                    System.out.println("   /ils combatLog");
                    System.out.println("   /ils sell");
                    System.out.println("   /ils export <item_name>");
                    System.out.println("   /ils setMultiplier");
                }
            }

            if (args.length > 0) {
                if ("version".equalsIgnoreCase(args[0])) {
                    if (sender instanceof Player) {
                        player.sendMessage(ChatColor.GOLD + "[ItemLoreStats] " + ChatColor.GREEN + " Currently running version " + this.getDescription().getVersion() + ", Created by Supavitax.");
                        return true;
                    }

                    System.out.println("[ItemLoreStats] Currently running version " + this.getDescription().getVersion());
                }

                if ("name".equalsIgnoreCase(args[0])) {
                    this.name_Com.onNameCommand(sender, args);
                }

                String updater;
                String playerFinal;
                String var43;
                Player player5;
                if ("give".equalsIgnoreCase(args[0])) {
                    int damage;
                    if (sender instanceof Player) {
                        if (!player.hasPermission("ils.admin")) {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        } else if (args.length <= 1) {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.EnterPlayerNameError", null, null, "", ""));
                        } else if (player.getServer().getPlayer(args[1]) == null) {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PlayerNotOnlineError", player, player, args[1], args[1]));
                        } else if (args.length <= 2) {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.IncludeItemNameError", null, null, "", ""));
                        } else {
                            updater = "";
                            playerFinal = "";

                            for (damage = 0; damage < args.length; ++damage) {
                                if (damage >= 3) {
                                    updater = updater + " " + args[damage];
                                } else {
                                    updater = args[damage];
                                }
                            }

                            if (updater.contains(",")) {
                                playerFinal = updater.split(",")[1].trim();
                                updater = updater.split(",")[0].trim();
                            }

                            if ((new File(this.getDataFolder() + File.separator + "SavedItems" + File.separator + updater + ".yml")).exists()) {
                                player5 = player.getServer().getPlayer(args[1]);
                                if (player5.getInventory().firstEmpty() == -1) {
                                    player5.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.InventoryFull", player, player, player5.getName(), player5.getName()));
                                    if (!"".equals(playerFinal)) {
                                        Bukkit.getServer().getWorld(player5.getWorld().getName()).dropItemNaturally(player5.getLocation(), this.generateFromFile.importWeapon(updater, playerFinal, player.getName()));
                                    } else {
                                        Bukkit.getServer().getWorld(player5.getWorld().getName()).dropItemNaturally(player5.getLocation(), this.generateFromFile.importWeapon(updater, "noChange", player.getName()));
                                    }
                                } else if (!"".equals(playerFinal)) {
                                    player5.getInventory().addItem(this.generateFromFile.importWeapon(updater, playerFinal, player.getName()));
                                } else {
                                    player5.getInventory().addItem(this.generateFromFile.importWeapon(updater, "noChange", player.getName()));
                                }
                            } else {
                                player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.DoesntExistError", player, player, updater, updater));
                            }
                        }
                    } else if (args.length <= 1) {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.EnterPlayerNameError", null, null, "", ""));
                    } else if (Bukkit.getServer().getPlayer(args[1]) != null) {
                        if (args.length <= 2) {
                            System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IncludeItemNameError", null, null, "", ""));
                        } else {
                            var43 = "";
                            updater = "";

                            for (damage = 0; damage < args.length; ++damage) {
                                if (damage >= 3) {
                                    var43 = var43 + " " + args[damage];
                                } else {
                                    var43 = args[damage];
                                }
                            }

                            if (var43.contains(",")) {
                                updater = var43.split(",")[1].trim();
                            }

                            if ((new File(this.getDataFolder() + File.separator + "SavedItems" + File.separator + var43 + ".yml")).exists()) {
                                player5 = Bukkit.getServer().getPlayer(args[1]);
                                if (player5.getInventory().firstEmpty() == -1) {
                                    System.out.println(ChatColor.stripColor(this.util_GetResponse.getResponse("ErrorMessages.InventoryFull", player5, player5, player5.getName(), player5.getName())));
                                    if (!"".equals(updater)) {
                                        Bukkit.getServer().getWorld(player5.getWorld().getName()).dropItemNaturally(player5.getLocation(), this.generateFromFile.importWeapon(var43, updater, player5.getName()));
                                    } else {
                                        Bukkit.getServer().getWorld(player5.getWorld().getName()).dropItemNaturally(player5.getLocation(), this.generateFromFile.importWeapon(var43, "noChange", player5.getName()));
                                    }
                                } else if (var43.contains(",")) {
                                    System.out.println(player5.getName() + " successfully received " + updater + ".");
                                    player5.getInventory().addItem(this.generateFromFile.importWeapon(var43, updater, player5.getName()));
                                } else {
                                    System.out.println(player5.getName() + " successfully received " + var43 + ".");
                                    player5.getInventory().addItem(this.generateFromFile.importWeapon(var43, "noChange", player5.getName()));
                                }
                            }
                        }
                    }
                }

                if ("export".equalsIgnoreCase(args[0])) {
                    this.export_Com.onExportCommand(sender, args);
                }

                if ("lore".equalsIgnoreCase(args[0])) {
                    if (!(sender instanceof Player)) {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    } else if (args.length <= 1) {
                        sender.sendMessage(ChatColor.RED + "You need a line number or player name. For example, /ils lore " + ChatColor.DARK_RED + "1" + ChatColor.RED + " or /ils lore " + ChatColor.DARK_RED + sender.getName());
                    } else {
                        ItemMeta healthRegen;
                        int lifeSteal;
                        int reflect;
                        Object var44;
                        int var50;
                        ItemStack var52;
                        ItemMeta var54;
                        ItemStack health;
                        if (Bukkit.getServer().getPlayer(args[1]) != null) {
                            final Player tplayer = Bukkit.getServer().getPlayer(args[1]);
                            if (tplayer.getItemInHand() == null) {
                                tplayer.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                            } else if (tplayer.getItemInHand().getType() == Material.AIR) {
                                tplayer.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                            } else if (!sender.hasPermission("ils.admin")) {
                                tplayer.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                            } else if (tplayer.getItemInHand().getItemMeta().getDisplayName() == null) {
                                tplayer.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.IncludeItemNameError", null, null, "", ""));
                            } else if (args.length <= 2) {
                                tplayer.sendMessage(ChatColor.RED + "You need a line number and a stat. For example, /ils lore " + ChatColor.DARK_RED + "1 Damage: +15");
                            } else if (!isInteger(args[2])) {
                                tplayer.sendMessage(ChatColor.RED + args[2] + " is not a line number. For example, /ils lore " + ChatColor.DARK_RED + "1");
                            } else if (args.length <= 3) {
                                tplayer.sendMessage(ChatColor.RED + "You need a stat to add. For example, /ils lore " + args[1] + " " + args[2] + " " + ChatColor.DARK_RED + "Damage: +15");
                            } else if (args.length <= 4) {
                                tplayer.sendMessage(ChatColor.RED + "You need to give the stat a value. For example, /ils lore " + args[2] + " Damage: " + ChatColor.DARK_RED + "+15");
                            } else {
                                playerFinal = "";
                                if (tplayer.getItemInHand().getItemMeta().hasLore()) {
                                    var44 = tplayer.getItemInHand().getItemMeta().getLore();
                                } else {
                                    var44 = new ArrayList();
                                }

                                if (Integer.parseInt(args[2]) - 1 >= ((List) var44).size()) {
                                    health = tplayer.getItemInHand();
                                    healthRegen = health.getItemMeta();

                                    for (lifeSteal = 0; lifeSteal < args.length; ++lifeSteal) {
                                        if (lifeSteal >= 4) {
                                            playerFinal = playerFinal + " " + args[lifeSteal];
                                        } else {
                                            playerFinal = args[lifeSteal];
                                        }
                                    }

                                    ((List) var44).add(this.util_Colours.replaceTooltipColour(playerFinal));
                                    healthRegen.setLore((List) var44);
                                    health.setItemMeta(healthRegen);
                                    if (tplayer.getItemInHand().getItemMeta().getDisplayName() != null) {
                                        if (this.getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                            tplayer.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + tplayer.getItemInHand().getItemMeta().getDisplayName());
                                        }
                                    } else if (this.getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                        tplayer.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + tplayer.getItemInHand().getType().name());
                                    }

                                    tplayer.getInventory().setItemInHand(health);
                                } else {
                                    var50 = Integer.parseInt(args[2]);
                                    var52 = tplayer.getItemInHand();
                                    var54 = var52.getItemMeta();

                                    for (reflect = 0; reflect < args.length; ++reflect) {
                                        if (reflect >= 4) {
                                            playerFinal = playerFinal + " " + args[reflect];
                                        } else {
                                            playerFinal = args[reflect];
                                        }
                                    }

                                    ((List) var44).set(var50 - 1, this.util_Colours.replaceTooltipColour(playerFinal));
                                    var54.setLore((List) var44);
                                    var52.setItemMeta(var54);
                                    if (tplayer.getItemInHand().getItemMeta().getDisplayName() != null) {
                                        if (this.getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                            tplayer.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + tplayer.getItemInHand().getItemMeta().getDisplayName());
                                        }

                                        this.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                            ItemLoreStats.this.updateHealth(tplayer);
                                            ItemLoreStats.this.updatePlayerSpeed(tplayer);
                                            ItemLoreStats.this.removeWeaponSpeedEffects(tplayer);
                                        }, 2L);
                                    } else {
                                        if (this.getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                            tplayer.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + tplayer.getItemInHand().getType().name());
                                        }

                                        this.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                            ItemLoreStats.this.updateHealth(tplayer);
                                            ItemLoreStats.this.updatePlayerSpeed(tplayer);
                                            ItemLoreStats.this.removeWeaponSpeedEffects(tplayer);
                                        }, 2L);
                                    }

                                    tplayer.getInventory().setItemInHand(var52);
                                }
                            }
                        } else if (player.getItemInHand() == null) {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                        } else if (player.getItemInHand().getType() == Material.AIR) {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                        } else if (!sender.hasPermission("ils.admin")) {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        } else if (player.getItemInHand().getItemMeta().getDisplayName() == null) {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.IncludeItemNameError", null, null, "", ""));
                        } else if (args.length <= 1) {
                            player.sendMessage(ChatColor.RED + "You need a line number and a stat. For example, /ils lore " + ChatColor.DARK_RED + "1 Damage: +15");
                        } else if (!isInteger(args[1])) {
                            player.sendMessage(ChatColor.RED + args[1] + " is not a line number. For example, /ils lore " + ChatColor.DARK_RED + "1 " + ChatColor.RED + "Damage: +15");
                        } else if (args.length <= 2) {
                            player.sendMessage(ChatColor.RED + "You need a stat to add. For example, /ils lore " + args[1] + " " + ChatColor.DARK_RED + "Damage: +15");
                        } else if (args.length <= 3) {
                            player.sendMessage(ChatColor.RED + "You need to give the stat a value. For example, /ils lore " + args[1] + " Damage: " + ChatColor.DARK_RED + "+15");
                        } else {
                            playerFinal = "";
                            if (player.getItemInHand().getItemMeta().hasLore()) {
                                var44 = player.getItemInHand().getItemMeta().getLore();
                            } else {
                                var44 = new ArrayList();
                            }

                            if (Integer.parseInt(args[1]) - 1 >= ((List) var44).size()) {
                                health = player.getItemInHand();
                                healthRegen = health.getItemMeta();

                                for (lifeSteal = 0; lifeSteal < args.length; ++lifeSteal) {
                                    if (lifeSteal >= 3) {
                                        playerFinal = playerFinal + " " + args[lifeSteal];
                                    } else {
                                        playerFinal = args[lifeSteal];
                                    }
                                }

                                ((List) var44).add(this.util_Colours.replaceTooltipColour(playerFinal));
                                healthRegen.setLore((List) var44);
                                health.setItemMeta(healthRegen);
                                if (player.getItemInHand().getItemMeta().getDisplayName() != null) {
                                    if (this.getConfig().getBoolean("messages.messages.loreSuccessfullyAdded")) {
                                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + player.getItemInHand().getItemMeta().getDisplayName());
                                    }

                                    this.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                        ItemLoreStats.this.updateHealth(player);
                                        ItemLoreStats.this.updatePlayerSpeed(player);
                                        ItemLoreStats.this.removeWeaponSpeedEffects(player);
                                    }, 2L);
                                } else {
                                    if (this.getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + player.getItemInHand().getType().name());
                                    }

                                    this.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                        ItemLoreStats.this.updateHealth(player);
                                        ItemLoreStats.this.updatePlayerSpeed(player);
                                        ItemLoreStats.this.removeWeaponSpeedEffects(player);
                                    }, 2L);
                                }

                                player.getInventory().setItemInHand(health);
                            } else {
                                var50 = Integer.parseInt(args[1]);
                                var52 = player.getItemInHand();
                                var54 = var52.getItemMeta();

                                for (reflect = 0; reflect < args.length; ++reflect) {
                                    if (reflect >= 3) {
                                        playerFinal = playerFinal + " " + args[reflect];
                                    } else {
                                        playerFinal = args[reflect];
                                    }
                                }

                                ((List) var44).set(var50 - 1, this.util_Colours.replaceTooltipColour(playerFinal));
                                var54.setLore((List) var44);
                                var52.setItemMeta(var54);
                                if (player.getItemInHand().getItemMeta().getDisplayName() != null) {
                                    if (this.getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + player.getItemInHand().getItemMeta().getDisplayName());
                                    }

                                    this.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                        ItemLoreStats.this.updateHealth(player);
                                        ItemLoreStats.this.updatePlayerSpeed(player);
                                        ItemLoreStats.this.removeWeaponSpeedEffects(player);
                                    }, 2L);
                                } else {
                                    if (this.getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + player.getItemInHand().getType().name());
                                    }

                                    this.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                        ItemLoreStats.this.updateHealth(player);
                                        ItemLoreStats.this.updatePlayerSpeed(player);
                                        ItemLoreStats.this.removeWeaponSpeedEffects(player);
                                    }, 2L);
                                }

                                player.getInventory().setItemInHand(var52);
                            }
                        }
                    }
                }

                if ("repair".equalsIgnoreCase(args[0])) {
                    this.repair_Com.onRepairCommand(sender, args);
                }

                if ("custom".equalsIgnoreCase(args[0])) {
                    this.customMaterial_Com.onCustomMaterialCommand(sender, args);
                }

                if ("test".equalsIgnoreCase(args[0]) && sender instanceof Player) {
                    player.hasPermission("ils.admin");
                }

                if ("reload".equalsIgnoreCase(args[0])) {
                    if (sender instanceof Player) {
                        player5 = (Player) sender;
                        if (!player5.hasPermission("ils.admin")) {
                            player5.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        } else {
                            this.reloadConfig();
                            player5.sendMessage(ChatColor.GOLD + "[ItemLoreStats] " + ChatColor.GREEN + " Configuration Reloaded!");
                        }

                        return true;
                    }

                    this.reloadConfig();
                    System.out.println("[ItemLoreStats] Configuration Reloaded!");
                }

                if ("createlore".equalsIgnoreCase(args[0])) {
                    if (sender instanceof Player) {
                        var43 = plugin.getConfig().getString("primaryStats.armour.name");
                        updater = plugin.getConfig().getString("secondaryStats.critChance.name");
                        playerFinal = plugin.getConfig().getString("secondaryStats.critDamage.name");
                        String var49 = plugin.getConfig().getString("primaryStats.damage.name");
                        String var51 = plugin.getConfig().getString("primaryStats.health.name");
                        String var53 = plugin.getConfig().getString("primaryStats.healthRegen.name");
                        String var55 = plugin.getConfig().getString("secondaryStats.lifeSteal.name");
                        String var56 = plugin.getConfig().getString("secondaryStats.reflect.name");
                        String fire = plugin.getConfig().getString("secondaryStats.fire.name");
                        String ice = plugin.getConfig().getString("secondaryStats.ice.name");
                        String poison = plugin.getConfig().getString("secondaryStats.poison.name");
                        String wither = plugin.getConfig().getString("secondaryStats.wither.name");
                        String harming = plugin.getConfig().getString("secondaryStats.harming.name");
                        String movementspeed = plugin.getConfig().getString("secondaryStats.movementSpeed.name");
                        String weaponspeed = plugin.getConfig().getString("bonusStats.weaponSpeed.name");
                        String xplevel = plugin.getConfig().getString("bonusStats.xpLevel.name");
                        String soulbound = plugin.getConfig().getString("bonusStats.soulbound.name");
                        String durability = plugin.getConfig().getString("bonusStats.durability.name");
                        Player player1 = (Player) sender;
                        if (!player1.isOp() && !player1.hasPermission("ils.admin")) {
                            player1.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        } else {
                            ItemStack debugItem = new ItemStack(Material.LEATHER_HELMET, 1);
                            ItemMeta debugItemMeta = debugItem.getItemMeta();
                            ArrayList debugItemList = new ArrayList();
                            debugItemMeta.setDisplayName("" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Fire-Chanter Wrap");
                            debugItemList.add("");
                            debugItemList.add(ChatColor.AQUA + var43 + ": " + ChatColor.DARK_GREEN + "2.0" + ChatColor.GREEN + "%");
                            debugItemList.add(ChatColor.AQUA + var51 + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "45.1");
                            debugItemList.add(ChatColor.AQUA + var53 + ": " + ChatColor.DARK_GREEN + "3.1" + ChatColor.GREEN + "%");
                            debugItemList.add(ChatColor.RED + fire + ": " + ChatColor.DARK_GREEN + "9.0" + ChatColor.GREEN + "%");
                            debugItemList.add(ChatColor.DARK_RED + wither + ": " + ChatColor.DARK_GREEN + "4.0" + ChatColor.GREEN + "%");
                            debugItemList.add("");
                            debugItemList.add(ChatColor.GOLD + durability + ": 1250/1250");
                            debugItemList.add("");
                            debugItemList.add(ChatColor.DARK_AQUA + soulbound + " " + player1.getName());
                            debugItemMeta.setLore(debugItemList);
                            debugItem.setItemMeta(debugItemMeta);
                            player1.getInventory().addItem(new ItemStack(debugItem));
                            ItemStack debugItem1 = new ItemStack(Material.IRON_CHESTPLATE, 1);
                            ItemMeta debugItemMeta1 = debugItem1.getItemMeta();
                            ArrayList debugItemList1 = new ArrayList();
                            debugItemMeta1.setDisplayName("" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Revenant Chestplate of Health");
                            debugItemList1.add("");
                            debugItemList1.add(ChatColor.AQUA + var43 + ": " + ChatColor.DARK_GREEN + "5.0" + ChatColor.GREEN + "%");
                            debugItemList1.add(ChatColor.AQUA + var51 + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "109.4");
                            debugItemList1.add(ChatColor.AQUA + var53 + ": " + ChatColor.DARK_GREEN + "4.0" + ChatColor.GREEN + "%");
                            debugItemList1.add(ChatColor.LIGHT_PURPLE + poison + ": " + ChatColor.DARK_GREEN + "2.0" + ChatColor.GREEN + "%");
                            debugItemList1.add(ChatColor.GREEN + var55 + ": " + ChatColor.DARK_GREEN + "4.0" + ChatColor.GREEN + "%");
                            debugItemList1.add("");
                            debugItemList1.add(ChatColor.GOLD + durability + ": 1750/1750");
                            debugItemList1.add("");
                            debugItemList1.add(ChatColor.DARK_AQUA + xplevel + ": 2");
                            debugItemMeta1.setLore(debugItemList1);
                            debugItem1.setItemMeta(debugItemMeta1);
                            player1.getInventory().addItem(new ItemStack(debugItem1));
                            ItemStack debugItem2 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
                            ItemMeta debugItemMeta2 = debugItem2.getItemMeta();
                            ArrayList debugItemList2 = new ArrayList();
                            debugItemMeta2.setDisplayName("" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "DragonScale Leg Wraps");
                            debugItemList2.add("");
                            debugItemList2.add(ChatColor.AQUA + var43 + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
                            debugItemList2.add(ChatColor.AQUA + var51 + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "59.7");
                            debugItemList2.add(ChatColor.AQUA + var53 + ": " + ChatColor.DARK_GREEN + "2.0" + ChatColor.GREEN + "%");
                            debugItemList2.add(ChatColor.RED + fire + ": " + ChatColor.DARK_GREEN + "5.0" + ChatColor.GREEN + "%");
                            debugItemList2.add(ChatColor.GREEN + var55 + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
                            debugItemList2.add("");
                            debugItemList2.add(ChatColor.GOLD + durability + ": 1500/1500");
                            debugItemList2.add("");
                            debugItemList2.add(ChatColor.DARK_AQUA + xplevel + ": 3");
                            debugItemMeta2.setLore(debugItemList2);
                            debugItem2.setItemMeta(debugItemMeta2);
                            player1.getInventory().addItem(new ItemStack(debugItem2));
                            ItemStack debugItem3 = new ItemStack(Material.DIAMOND_BOOTS, 1);
                            ItemMeta debugItemMeta3 = debugItem3.getItemMeta();
                            ArrayList debugItemList3 = new ArrayList();
                            debugItemMeta3.setDisplayName("" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Boots of the Black Glacier");
                            debugItemList3.add("");
                            debugItemList3.add(ChatColor.AQUA + var43 + ": " + ChatColor.DARK_GREEN + "9.0" + ChatColor.GREEN + "%");
                            debugItemList3.add(ChatColor.AQUA + var51 + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "97.8");
                            debugItemList3.add(ChatColor.AQUA + var53 + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
                            debugItemList3.add(ChatColor.BLUE + ice + ": " + ChatColor.DARK_GREEN + "5.0" + ChatColor.GREEN + "%");
                            debugItemList3.add(ChatColor.DARK_RED + wither + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
                            debugItemList3.add(ChatColor.YELLOW + movementspeed + ": " + ChatColor.DARK_GREEN + "8.0" + ChatColor.GREEN + "%");
                            debugItemList3.add("");
                            debugItemList3.add(ChatColor.GOLD + durability + ": 1500/1500");
                            debugItemList3.add("");
                            debugItemList3.add(ChatColor.DARK_AQUA + xplevel + ": 4");
                            debugItemMeta3.setLore(debugItemList3);
                            debugItem3.setItemMeta(debugItemMeta3);
                            player1.getInventory().addItem(new ItemStack(debugItem3));
                            ItemStack debugItem4 = new ItemStack(Material.DIAMOND_SWORD, 1);
                            ItemMeta debugItemMeta4 = debugItem4.getItemMeta();
                            ArrayList debugItemList4 = new ArrayList();
                            debugItemMeta4.setDisplayName("" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Slaying Thrustblade");
                            debugItemList4.add("");
                            debugItemList4.add(ChatColor.AQUA + weaponspeed + ": " + ChatColor.RED + "Slow");
                            debugItemList4.add(ChatColor.AQUA + var49 + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "68.1" + ChatColor.DARK_GREEN + "-" + ChatColor.DARK_GREEN + "93.7");
                            debugItemList4.add(ChatColor.AQUA + updater + ": " + ChatColor.DARK_GREEN + "4.0" + ChatColor.GREEN + "%");
                            debugItemList4.add(ChatColor.AQUA + playerFinal + ": " + ChatColor.DARK_GREEN + "12.0" + ChatColor.GREEN + "%");
                            debugItemList4.add(ChatColor.YELLOW + var56 + ": " + ChatColor.DARK_GREEN + "6.0" + ChatColor.GREEN + "%");
                            debugItemList4.add(ChatColor.RED + fire + ": " + ChatColor.DARK_GREEN + "7.0" + ChatColor.GREEN + "%");
                            debugItemList4.add(ChatColor.DARK_PURPLE + harming + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
                            debugItemList4.add("");
                            debugItemList4.add(ChatColor.GOLD + durability + ": 350/350");
                            debugItemList4.add("");
                            debugItemList4.add(ChatColor.DARK_AQUA + "Level: 1");
                            debugItemList4.add(ChatColor.DARK_AQUA + soulbound + " " + player1.getName());
                            debugItemMeta4.setLore(debugItemList4);
                            debugItem4.setItemMeta(debugItemMeta4);
                            player1.getInventory().addItem(new ItemStack(debugItem4));
                            ItemStack debugItem5 = new ItemStack(Material.BOW, 1);
                            ItemMeta debugItemMeta5 = debugItem5.getItemMeta();
                            ArrayList debugItemList5 = new ArrayList();
                            debugItemMeta5.setDisplayName("" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Slaying Thrustbow");
                            debugItemList5.add("");
                            debugItemList5.add(ChatColor.AQUA + weaponspeed + ": " + ChatColor.RED + "Slow");
                            debugItemList5.add(ChatColor.AQUA + var49 + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "68" + ChatColor.DARK_GREEN + "-" + ChatColor.DARK_GREEN + "93");
                            debugItemList5.add(ChatColor.AQUA + updater + ": " + ChatColor.DARK_GREEN + "4.0" + ChatColor.GREEN + "%");
                            debugItemList5.add(ChatColor.AQUA + playerFinal + ": " + ChatColor.DARK_GREEN + "12.0" + ChatColor.GREEN + "%");
                            debugItemList5.add(ChatColor.YELLOW + var56 + ": " + ChatColor.DARK_GREEN + "6.0" + ChatColor.GREEN + "%");
                            debugItemList5.add(ChatColor.RED + fire + ": " + ChatColor.DARK_GREEN + "7.0" + ChatColor.GREEN + "%");
                            debugItemList5.add(ChatColor.DARK_PURPLE + harming + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
                            debugItemList5.add("");
                            debugItemList5.add(ChatColor.GOLD + durability + ": 350/350");
                            debugItemList5.add("");
                            debugItemList5.add(ChatColor.DARK_AQUA + "Level: 1");
                            debugItemList5.add(ChatColor.DARK_AQUA + soulbound + " " + player1.getName());
                            debugItemMeta5.setLore(debugItemList5);
                            debugItem5.setItemMeta(debugItemMeta5);
                            player1.getInventory().addItem(new ItemStack(debugItem5));
                            player1.sendMessage(ChatColor.RED + "[DEBUGGER] " + ChatColor.WHITE + "items created!");
                        }
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if ("setMultiplier".equalsIgnoreCase(args[0])) {
                    if (sender instanceof Player) {
                        player5 = (Player) sender;
                        if (!player5.isOp() && !player5.hasPermission("ils.admin")) {
                            player5.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        } else {
                            this.getConfig().set("npcModifier." + player5.getWorld().getName() + ".healthMultiplier", 0.045D);
                            this.getConfig().set("npcModifier." + player5.getWorld().getName() + ".damageMultiplier", 0.004D);
                            this.getConfig().set("npcModifier." + player5.getWorld().getName() + ".expMultiplier", 0.004D);
                            this.getConfig().set("npcModifier." + player5.getWorld().getName() + ".location.x", player5.getLocation().getBlockX());
                            this.getConfig().set("npcModifier." + player5.getWorld().getName() + ".location.y", player5.getLocation().getBlockY());
                            this.getConfig().set("npcModifier." + player5.getWorld().getName() + ".location.z", player5.getLocation().getBlockZ());
                            this.saveConfig();
                            player5.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set the NPC multiplier to multiply health and damage from " + ChatColor.GOLD + player5.getLocation().getBlockX() + ChatColor.LIGHT_PURPLE + ", " + ChatColor.GOLD + player5.getLocation().getBlockY() + ChatColor.LIGHT_PURPLE + ", " + ChatColor.GOLD + player5.getLocation().getBlockZ() + ChatColor.LIGHT_PURPLE + ".");
                        }
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if ("stats".equalsIgnoreCase(args[0])) {
                    if (sender instanceof Player) {
                        player5 = (Player) sender;
                        this.characterSheet.returnStats(player5, this.getHealthValue(player5));
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if ("combatlog".equalsIgnoreCase(args[0])) {
                    if (sender instanceof Player) {
                        player5 = (Player) sender;
                        if (this.getConfig().getBoolean("combatMessages.outgoing.damageDone") || this.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
                            try {
                                this.PlayerDataConfig = new YamlConfiguration();
                                this.PlayerDataConfig.load(new File(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));
                                if (this.PlayerDataConfig.getBoolean("extra.combatLogVisible")) {
                                    this.PlayerDataConfig.set("extra.combatLogVisible", false);
                                    this.combatLogVisible.put(player.getName(), false);
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.RED + "disabled" + ChatColor.LIGHT_PURPLE + ".");
                                } else if (!this.PlayerDataConfig.getBoolean("extra.combatLogVisible")) {
                                    this.PlayerDataConfig.set("extra.combatLogVisible", true);
                                    this.combatLogVisible.put(player.getName(), true);
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.GREEN + "enabled" + ChatColor.LIGHT_PURPLE + ".");
                                } else if (this.PlayerDataConfig.get("extra.combatLogVisible") == null) {
                                    this.PlayerDataConfig.set("extra.combatLogVisible", false);
                                    this.combatLogVisible.put(player.getName(), false);
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.RED + "disabled" + ChatColor.LIGHT_PURPLE + ".");
                                }

                                this.PlayerDataConfig.save(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("*********** Failed to load player data for " + player.getName() + " when toggling combat log! ***********");
                            }
                        }
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if ("sell".equalsIgnoreCase(args[0])) {
                    if (sender instanceof Player) {
                        player5 = (Player) sender;
                        this.util_Vault.removeMoneyForSale(player, player.getItemInHand().getAmount());
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if ("health".equalsIgnoreCase(args[0])) {
                    if (sender instanceof Player) {
                        player5 = (Player) sender;
                        player.sendMessage(ChatColor.RED + "[DEBUGGER] " + ChatColor.WHITE + player.getHealth() + " out of " + player.getMaxHealth());
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if ("speed".equalsIgnoreCase(args[0])) {
                    if (sender instanceof Player) {
                        player.sendMessage(ChatColor.GOLD + "Your movement speed is " + ChatColor.WHITE + player.getWalkSpeed());
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if ("upgrade".equalsIgnoreCase(args[0])) {
                    if (sender instanceof Player) {
                        if (!player.isOp() && !player.hasPermission("ils.command.upgrade")) {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        } else if (args.length >= 2) {
                            if (!"armour".equalsIgnoreCase(args[1]) && !"armor".equalsIgnoreCase(args[1])) {
                                if ("hand".equalsIgnoreCase(args[1])) {
                                    this.itemUpgrade.increaseItemStatOnItemInHand(player);
                                } else if ("all".equalsIgnoreCase(args[1])) {
                                    this.itemUpgrade.increaseItemStatOnHelmet(player);
                                    this.itemUpgrade.increaseItemStatOnChestplate(player);
                                    this.itemUpgrade.increaseItemStatOnLeggings(player);
                                    this.itemUpgrade.increaseItemStatOnBoots(player);
                                    this.itemUpgrade.increaseItemStatOnItemInHand(player);
                                } else {
                                    player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.EnterTypeError", null, null, "", ""));
                                }
                            } else {
                                this.itemUpgrade.increaseItemStatOnHelmet(player);
                                this.itemUpgrade.increaseItemStatOnChestplate(player);
                                this.itemUpgrade.increaseItemStatOnLeggings(player);
                                this.itemUpgrade.increaseItemStatOnBoots(player);
                            }
                        } else {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.EnterTypeError", null, null, "", ""));
                        }
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else {
                    LivingEntity var45;
                    if ("zombie".equalsIgnoreCase(args[0])) {
                        if (sender instanceof Player) {
                            if (player.isOp() || player.hasPermission("ils.admin")) {
                                var45 = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                                var45.setCustomName("ItemLoreStats Test Dummy");
                                var45.setMaxHealth(2048.0D);
                                var45.setHealth(2048.0D);
                                if (player.getItemInHand() != null) {
                                    var45.getEquipment().setItemInHand(player.getItemInHand().clone());
                                }

                                if (player.getInventory().getHelmet() != null) {
                                    var45.getEquipment().setHelmet(player.getInventory().getHelmet().clone());
                                }

                                if (player.getInventory().getChestplate() != null) {
                                    var45.getEquipment().setChestplate(player.getInventory().getChestplate().clone());
                                }

                                if (player.getInventory().getLeggings() != null) {
                                    var45.getEquipment().setLeggings(player.getInventory().getLeggings().clone());
                                }

                                if (player.getInventory().getBoots() != null) {
                                    var45.getEquipment().setBoots(player.getInventory().getBoots().clone());
                                }

                                var45.setCustomNameVisible(true);
                            }
                        } else {
                            System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                        }
                    } else if ("skeleton".equalsIgnoreCase(args[0])) {
                        if (sender instanceof Player) {
                            if (player.isOp() || player.hasPermission("ils.admin")) {
                                var45 = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
                                var45.setCustomName("ItemLoreStats Test Dummy");
                                var45.setMaxHealth(2048.0D);
                                var45.setHealth(2048.0D);
                                if (player.getItemInHand() != null) {
                                    var45.getEquipment().setItemInHand(player.getItemInHand().clone());
                                }

                                if (player.getInventory().getHelmet() != null) {
                                    var45.getEquipment().setHelmet(player.getInventory().getHelmet().clone());
                                }

                                if (player.getInventory().getChestplate() != null) {
                                    var45.getEquipment().setChestplate(player.getInventory().getChestplate().clone());
                                }

                                if (player.getInventory().getLeggings() != null) {
                                    var45.getEquipment().setLeggings(player.getInventory().getLeggings().clone());
                                }

                                if (player.getInventory().getBoots() != null) {
                                    var45.getEquipment().setBoots(player.getInventory().getBoots().clone());
                                }

                                var45.setCustomNameVisible(true);
                            }
                        } else {
                            System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                        }
                    } else if ("update".equalsIgnoreCase(args[0])) {
                        if (sender instanceof Player) {
                            if ((player.isOp() || player.hasPermission("ils.admin")) && this.getConfig().getBoolean("checkForUpdates")) {
                                new Updater(this, 67983, this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
                                this.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                    player.sendMessage(ChatColor.GOLD + "Item Lore Stats" + ChatColor.GREEN + " update has successfully completed. To complete the update please stop the server and delete/backup the /SavedItems/ directory, the /SavedMobs/ directory and the language file. Once that is completed you can start the server back up.");
                                }, 260L);
                            }
                        } else if (this.getConfig().getBoolean("checkForUpdates")) {
                            new Updater(this, 67983, this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
                        }
                    }
                }
            }
        }

        return false;
    }

    public void swapItems(int slotA, int slotB, Inventory inv) {
        ItemStack itemStackA = inv.getItem(slotA);
        ItemStack itemStackB = inv.getItem(slotB);
        inv.setItem(slotA, itemStackB);
        inv.setItem(slotB, itemStackA);
    }

    public void checkForUpdate(final Player player) {
        if (this.getConfig().getBoolean("checkForUpdate")) {
            Updater updater = new Updater(plugin, 67983, plugin.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
            boolean update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
            final String name = updater.getLatestName();
            if (update) {
                this.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (Integer.parseInt(String.valueOf(ItemLoreStats.this.util_Format.formatString(Double.parseDouble(ItemLoreStats.this.getDescription().getVersion()))).replace(".", "")) < Integer.parseInt(name.split(" v")[1].replace(".", ""))) {
                        player.sendMessage("");
                        player.sendMessage(ChatColor.GREEN + "               <------- " + ChatColor.GOLD + "Update Available" + ChatColor.GREEN + " ------->");
                        player.sendMessage(ChatColor.GREEN + "An update is available for " + ChatColor.GOLD + ItemLoreStats.this.getDescription().getName() + ChatColor.GREEN + ". You have " + ChatColor.GOLD + "Item Lore Stats v" + ItemLoreStats.this.util_Format.formatString(Double.parseDouble(ItemLoreStats.this.getDescription().getVersion())) + ChatColor.GREEN + " and " + ChatColor.GOLD + name + ChatColor.GREEN + " is available from" + ChatColor.GOLD + " http://dev.bukkit.org/bukkit-plugins/item-lore-stats/ " + ChatColor.GREEN + ".");
                        player.sendMessage(ChatColor.GREEN + "             Type " + ChatColor.LIGHT_PURPLE + "/ils update " + ChatColor.GREEN + "to download the update.");
                        player.sendMessage("");
                    }

                }, 80L);
            }
        }

    }

    public void updateMana(Player player) {
        if (plugin.getHeroes() != null && this.getConfig().getBoolean("heroesOnlyStats.heroesMaxMana.enabled")) {
            this.util_Heroes.removeHeroMaxManaStat(player);
            if (plugin.isTool(player.getItemInHand().getType())) {
                this.util_Heroes.addHeroMaxManaStat(player, (int) (this.gearStats.get_MaxManaGear(player) + this.gearStats.get_MaxManaItemInHand(player)));
            } else {
                this.util_Heroes.addHeroMaxManaStat(player, (int) this.gearStats.get_MaxManaGear(player));
            }
        }

    }

    public double getHealthValue(Player player) {
        if (plugin.getConfig().getInt("baseHealth") == 0) {
            return 0.0D;
        } else {
            double health = 0.0D;
            double healthBoost = 0.0D;
            if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
                Iterator var7 = player.getActivePotionEffects().iterator();

                while (var7.hasNext()) {
                    PotionEffect maxHealth = (PotionEffect) var7.next();
                    if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
                        healthBoost = (double) maxHealth.getAmplifier() * 4.0D;
                    }
                }
            }

            if (!this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                double maxHealth1;
                double newHP;
                if (plugin.getHeroes() != null) {
                    maxHealth1 = (double) this.util_Heroes.getHeroBaseHealth(player) + this.util_Heroes.getHeroHealthPerLevel(player) * (double) this.util_Heroes.getHeroLevel(player) + healthBoost;
                    if (this.isTool(player.getItemInHand().getType())) {
                        newHP = Double.valueOf(maxHealth1 + this.gearStats.getHealthGear(player) + this.gearStats.getHealthItemInHand(player)).intValue();
                        return newHP;
                    } else {
                        newHP = Double.valueOf(maxHealth1 + this.gearStats.getHealthGear(player)).intValue();
                        return newHP;
                    }
                } else if (plugin.getSkillAPI() != null) {
                    maxHealth1 = (double) (this.util_SkillAPI.getSkillAPIBaseHealth(player) + this.util_SkillAPI.getSkillAPIHealthPerLevel(player) * this.util_SkillAPI.getSkillAPILevel(player)) + healthBoost;
                    if (this.isTool(player.getItemInHand().getType())) {
                        newHP = Double.valueOf(maxHealth1 + this.gearStats.getHealthGear(player) + this.gearStats.getHealthItemInHand(player)).intValue();
                        return newHP;
                    } else {
                        newHP = Double.valueOf(maxHealth1 + this.gearStats.getHealthGear(player)).intValue();
                        return newHP;
                    }
                } else {
                    maxHealth1 = this.getConfig().getDouble("baseHealth") + this.getConfig().getDouble("additionalStatsPerLevel.health") * (double) player.getLevel() + healthBoost;
                    if (this.isTool(player.getItemInHand().getType())) {
                        newHP = maxHealth1 + this.gearStats.getHealthGear(player) + this.gearStats.getHealthItemInHand(player);
                        return newHP;
                    } else {
                        newHP = maxHealth1 + this.gearStats.getHealthGear(player);
                        return newHP;
                    }
                }
            } else {
                return health;
            }
        }
    }

    public void updateHealth(Player player) {
        if (!this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName()) && !player.hasMetadata("NPC")) {
            if (plugin.getConfig().getInt("baseHealth") == 0) {
                return;
            }

            double modifier = 0.0D;
            double healthBoost = 0.0D;
            if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
                Iterator var7 = player.getActivePotionEffects().iterator();

                while (var7.hasNext()) {
                    PotionEffect maxHealth = (PotionEffect) var7.next();
                    if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
                        healthBoost = (double) maxHealth.getAmplifier() * 4.0D;
                    }
                }
            }

            double maxHealth1;
            double newHP;
            if (plugin.getHeroes() != null) {
                maxHealth1 = (double) this.util_Heroes.getHeroBaseHealth(player) + modifier + this.util_Heroes.getHeroHealthPerLevel(player) * (double) this.util_Heroes.getHeroLevel(player) + healthBoost;
                if (this.isTool(player.getItemInHand().getType())) {
                    newHP = Double.valueOf(maxHealth1 + this.gearStats.getHealthGear(player) + this.gearStats.getHealthItemInHand(player)).intValue();
                    player.setMaxHealth(newHP);
                } else {
                    newHP = Double.valueOf(maxHealth1 + this.gearStats.getHealthGear(player)).intValue();
                    player.setMaxHealth(newHP);
                }
            } else if (plugin.getSkillAPI() != null) {
                maxHealth1 = (double) (this.util_SkillAPI.getSkillAPIBaseHealth(player) + this.util_SkillAPI.getSkillAPIHealthPerLevel(player) * this.util_SkillAPI.getSkillAPILevel(player)) + healthBoost;
                if (this.isTool(player.getItemInHand().getType())) {
                    newHP = Double.valueOf(maxHealth1 + this.gearStats.getHealthGear(player) + this.gearStats.getHealthItemInHand(player)).intValue();
                    player.setMaxHealth(newHP);
                } else {
                    newHP = Double.valueOf(maxHealth1 + this.gearStats.getHealthGear(player)).intValue();
                    player.setMaxHealth(newHP);
                }
            } else {
                maxHealth1 = this.getConfig().getDouble("baseHealth") + modifier + this.getConfig().getDouble("additionalStatsPerLevel.health") * (double) player.getLevel() + healthBoost;
                if (this.isTool(player.getItemInHand().getType())) {
                    newHP = Double.valueOf(maxHealth1 + this.gearStats.getHealthGear(player) + this.gearStats.getHealthItemInHand(player)).intValue();
                    player.setMaxHealth(newHP);
                } else {
                    newHP = Double.valueOf(maxHealth1 + this.gearStats.getHealthGear(player)).intValue();
                    player.setMaxHealth(newHP);
                }
            }

            if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                this.updateBarAPI(player);
            }

            if (this.getConfig().getInt("healthScale") > 0) {
                player.setHealthScale(this.getConfig().getDouble("healthScale"));
            }
        }

    }

    public void updatePlayerSpeed(final Player player) {
        if (!this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            this.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                float compressedModifier = 0.0F;
                float maxSpeed;
                float speed;
                if (ItemLoreStats.this.isTool(player.getItemInHand().getType())) {
                    maxSpeed = 0.99F;
                    speed = (float) (0.0020000000949949026D * (ItemLoreStats.this.gearStats.getMovementSpeedGear(player) + ItemLoreStats.this.gearStats.getMovementSpeedItemInHand(player)) + (double) compressedModifier + ItemLoreStats.plugin.getConfig().getDouble("baseMovementSpeed") + Double.valueOf(player.getLevel()) * ItemLoreStats.this.getConfig().getDouble("additionalStatsPerLevel.speed"));
                    if (speed > maxSpeed) {
                        player.setWalkSpeed(maxSpeed);
                    } else {
                        player.setWalkSpeed(speed);
                    }
                } else {
                    maxSpeed = 0.99F;
                    speed = (float) (0.0020000000949949026D * ItemLoreStats.this.gearStats.getMovementSpeedGear(player) + (double) compressedModifier + ItemLoreStats.plugin.getConfig().getDouble("baseMovementSpeed") + Double.valueOf(player.getLevel()) * ItemLoreStats.this.getConfig().getDouble("additionalStatsPerLevel.speed"));
                    if (speed > maxSpeed) {
                        player.setWalkSpeed(maxSpeed);
                    } else {
                        player.setWalkSpeed(speed);
                    }
                }

            }, 2L);
        } else {
            player.setWalkSpeed((float) plugin.getConfig().getDouble("baseMovementSpeed"));
        }

    }

    public void removeWeaponSpeedEffects(Player player) {
        if (!this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
                player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            } else if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            }
        }

    }

    public void checkWeaponSpeed(Player player) {
        String weaponSpeed = "normal";
        ItemStack checkItemHeld = player.getItemInHand();
        if (checkItemHeld != null && checkItemHeld.getType() != Material.AIR && checkItemHeld.getItemMeta() != null && checkItemHeld.getItemMeta().getLore() != null && plugin.isTool(checkItemHeld.getType())) {
            weaponSpeed = this.gearStats.getSwingSpeedItemInHand(player).toLowerCase();
        }

        if ("very fast".equalsIgnoreCase(weaponSpeed)) {
            if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
                player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            }

            plugin.updateHealth(player);
            plugin.updatePlayerSpeed(player);
            this.setBonuses.updateSetBonus(player);
            plugin.removeWeaponSpeedEffects(player);
            plugin.updateMana(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 1));
        } else if ("fast".equalsIgnoreCase(weaponSpeed)) {
            if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
                player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            }

            plugin.updateHealth(player);
            plugin.updatePlayerSpeed(player);
            this.setBonuses.updateSetBonus(player);
            plugin.removeWeaponSpeedEffects(player);
            plugin.updateMana(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 0));
        } else if ("slow".equalsIgnoreCase(weaponSpeed)) {
            if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            }

            plugin.updateHealth(player);
            plugin.updatePlayerSpeed(player);
            this.setBonuses.updateSetBonus(player);
            plugin.removeWeaponSpeedEffects(player);
            plugin.updateMana(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, 0));
        } else if ("very slow".equalsIgnoreCase(weaponSpeed)) {
            if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            }

            plugin.updateHealth(player);
            plugin.updatePlayerSpeed(player);
            this.setBonuses.updateSetBonus(player);
            plugin.removeWeaponSpeedEffects(player);
            plugin.updateMana(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, 1));
        } else if ("normal".equalsIgnoreCase(weaponSpeed)) {
            plugin.updateHealth(player);
            plugin.updatePlayerSpeed(player);
            this.setBonuses.updateSetBonus(player);
            plugin.removeWeaponSpeedEffects(player);
            plugin.updateMana(player);
        }

    }

    public class ItemLoreStatsListener implements Listener {
        @EventHandler
        public void onRegenHealth(EntityRegainHealthEvent event) {
            if (event.getEntity() instanceof Player) {
                if (event.getRegainReason().equals(RegainReason.SATIATED)) {
                    Player player = (Player) event.getEntity();
                    if (!ItemLoreStats.this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                        if (ItemLoreStats.plugin.getConfig().getDouble("baseHealthRegen") == 0.0D) {
                            return;
                        }

                        double gearRegen = 0.0D;
                        double modifier = 0.0D;
                        if (ItemLoreStats.this.isTool(player.getItemInHand().getType())) {
                            gearRegen = ItemLoreStats.this.gearStats.getHealthRegenGear(player) + ItemLoreStats.this.gearStats.getHealthRegenItemInHand(player);
                        } else {
                            gearRegen = ItemLoreStats.this.gearStats.getHealthRegenGear(player);
                        }

                        double baseRegen = ItemLoreStats.this.getConfig().getDouble("baseHealthRegen");
                        double additionalLevelRegen = ItemLoreStats.this.getConfig().getDouble("additionalStatsPerLevel.healthRegen");
                        double modifiedHealthRegen = player.getMaxHealth() / 100.0D * (gearRegen + baseRegen + Double.valueOf(player.getLevel()) * additionalLevelRegen + modifier);
                        if (ItemLoreStats.plugin.getHeroes() != null) {
                            if (player.getHealth() + modifiedHealthRegen > player.getMaxHealth()) {
                                event.setCancelled(true);
                                player.setHealth(player.getMaxHealth());
                            } else {
                                event.setCancelled(true);
                                player.setHealth(player.getHealth() + modifiedHealthRegen);
                            }
                        } else {
                            event.setAmount(modifiedHealthRegen);
                        }
                    }
                }

                ItemLoreStats.this.updateBarAPI((Player) event.getEntity());
            }

        }

        @EventHandler
        public void expChangeEvent(PlayerExpChangeEvent event) {
            if (!event.getPlayer().hasMetadata("NPC")) {
                Player player = event.getPlayer();
                double bonusExp;
                double xpMultiplier;
                if (ItemLoreStats.this.isTool(player.getItemInHand().getType())) {
                    if (ItemLoreStats.this.gearStats.getXPMultiplierGear(player) + ItemLoreStats.this.gearStats.getXPMultiplierItemInHand(player) > 0.0D) {
                        bonusExp = 0.0D;
                        xpMultiplier = ItemLoreStats.this.gearStats.getXPMultiplierGear(player) + ItemLoreStats.this.gearStats.getXPMultiplierItemInHand(player);
                        bonusExp = (double) event.getAmount() * xpMultiplier / 100.0D;
                        player.giveExp((int) bonusExp);
                    }
                } else if (ItemLoreStats.this.gearStats.getXPMultiplierGear(player) > 0.0D) {
                    bonusExp = 0.0D;
                    xpMultiplier = ItemLoreStats.this.gearStats.getXPMultiplierGear(player);
                    bonusExp = (double) event.getAmount() * xpMultiplier / 100.0D;
                    player.giveExp((int) bonusExp);
                }

                ItemLoreStats.this.updateBarAPI(player);
            }

        }

        @EventHandler
        public void enchantTableUse(EnchantItemEvent event) {
            if (ItemLoreStats.plugin.getConfig().getBoolean("keepXPOnDeath")) {
                final Player playerFinal = event.getEnchanter();
                ItemLoreStats.this.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, () -> {
                    try {
                        ItemLoreStats.this.PlayerDataConfig = new YamlConfiguration();
                        ItemLoreStats.this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));
                        ItemLoreStats.this.PlayerDataConfig.set("extra.xp", playerFinal.getExp());
                        ItemLoreStats.this.PlayerDataConfig.set("extra.level", playerFinal.getLevel());
                        ItemLoreStats.this.PlayerDataConfig.save(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml");
                        ItemLoreStats.this.updateBarAPI(playerFinal);
                    } catch (Exception var2) {
                        var2.printStackTrace();
                        System.out.println("*********** Failed to save player data for " + playerFinal.getName() + " when using the enchanting table! ***********");
                    }

                }, 4L);
            }

        }

        @EventHandler
        public void saveExpOnDeath(PlayerDeathEvent event) {
            if (!event.getEntity().hasMetadata("NPC") && ItemLoreStats.plugin.getConfig().getBoolean("keepXPOnDeath")) {
                Player player = event.getEntity();

                try {
                    ItemLoreStats.this.PlayerDataConfig = new YamlConfiguration();
                    ItemLoreStats.this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));
                    event.setDroppedExp(0);
                    ItemLoreStats.this.PlayerDataConfig.set("extra.xp", player.getExp());
                    ItemLoreStats.this.PlayerDataConfig.set("extra.level", player.getLevel());
                    ItemLoreStats.this.PlayerDataConfig.save(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");
                } catch (Exception var4) {
                    var4.printStackTrace();
                    System.out.println("*********** Failed to save player data for " + player.getName() + " when dying! ***********");
                }
            }

        }

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            final Player playerFinal = event.getPlayer();
            ItemLoreStats.this.getServer().getScheduler().scheduleAsyncDelayedTask(ItemLoreStats.plugin, () -> {
                if (!(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml")).exists()) {
                    try {
                        ItemLoreStats.this.PlayerDataConfig = new YamlConfiguration();
                        ItemLoreStats.this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml");
                        ItemLoreStats.this.updateHealth(playerFinal);
                        ItemLoreStats.this.PlayerDataConfig.set("extra.logoutHealth", 20.0D);
                        ItemLoreStats.this.PlayerDataConfig.set("extra.maxHealth", 20.0D);
                        ItemLoreStats.this.PlayerDataConfig.set("extra.hunger", 20);
                        ItemLoreStats.this.PlayerDataConfig.set("extra.xp", 0.0F);
                        ItemLoreStats.this.PlayerDataConfig.set("extra.level", 0);
                        ItemLoreStats.this.PlayerDataConfig.save(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml");
                    } catch (Exception var3) {
                        var3.printStackTrace();
                        System.out.println("*********** Failed to save player data for " + playerFinal.getName() + " when logging in! ***********");
                    }
                } else if ((new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml")).exists()) {
                    try {
                        ItemLoreStats.this.PlayerDataConfig = new YamlConfiguration();
                        ItemLoreStats.this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));
                        playerFinal.setMaxHealth(ItemLoreStats.this.PlayerDataConfig.getDouble("extra.maxHealth"));
                        playerFinal.setHealth(ItemLoreStats.this.PlayerDataConfig.getDouble("extra.logoutHealth"));
                        playerFinal.setFoodLevel(ItemLoreStats.this.PlayerDataConfig.getInt("extra.hunger"));
                        if (ItemLoreStats.this.PlayerDataConfig.get("extra.combatLogVisible") == null) {
                            ItemLoreStats.this.combatLogVisible.put(playerFinal.getName(), true);
                        } else {
                            ItemLoreStats.this.combatLogVisible.put(playerFinal.getName(), ItemLoreStats.this.PlayerDataConfig.getBoolean("extra.combatLogVisible"));
                        }

                        if (ItemLoreStats.plugin.getConfig().getBoolean("keepXPOnDeath") && ItemLoreStats.plugin.getHeroes() == null) {
                            playerFinal.setExp((float) ItemLoreStats.this.PlayerDataConfig.getDouble("extra.xp"));
                            playerFinal.setLevel(ItemLoreStats.this.PlayerDataConfig.getInt("extra.level"));
                        }

                        ItemLoreStats.this.updateHealth(playerFinal);
                    } catch (Exception var2) {
                        var2.printStackTrace();
                        System.out.println("*********** Failed to load player data for " + playerFinal.getName() + " when logging in! ***********");
                    }
                }

                ItemLoreStats.this.updateHealth(playerFinal);
                ItemLoreStats.this.updatePlayerSpeed(playerFinal);
                ItemLoreStats.this.removeWeaponSpeedEffects(playerFinal);
                ItemLoreStats.this.updateBarAPI(playerFinal);
            }, 5L);
            if (event.getPlayer().isOp()) {
                ItemLoreStats.this.getServer().getScheduler().runTaskLaterAsynchronously(ItemLoreStats.plugin, () -> {
                    if (ItemLoreStats.this.getMinecraftBuildNumber(Bukkit.getBukkitVersion()) >= 1100) {
                        ItemLoreStats.this.checkForUpdate(playerFinal);
                    }

                }, 60L);
            }

        }

        @EventHandler
        public void onPlayerRespawn(PlayerRespawnEvent event) {
            final Player player = event.getPlayer();
            if (!ItemLoreStats.this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                ItemLoreStats.this.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, () -> {
                    if (ItemLoreStats.plugin.getConfig().getBoolean("keepXPOnDeath")) {
                        try {
                            ItemLoreStats.this.PlayerDataConfig = new YamlConfiguration();
                            ItemLoreStats.this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));
                            if (ItemLoreStats.plugin.getHeroes() == null) {
                                player.setExp((float) ItemLoreStats.this.PlayerDataConfig.getDouble("extra.xp"));
                                player.setLevel(ItemLoreStats.this.PlayerDataConfig.getInt("extra.level"));
                                ItemLoreStats.this.combatLogVisible.put(player.getName(), ItemLoreStats.this.PlayerDataConfig.getBoolean("extra.combatLogVisible"));
                                ItemLoreStats.this.updateHealth(player);
                                ItemLoreStats.this.updatePlayerSpeed(player);
                                ItemLoreStats.this.setBonuses.updateSetBonus(player);
                                ItemLoreStats.this.removeWeaponSpeedEffects(player);
                                player.setHealth(player.getMaxHealth());
                            }
                        } catch (Exception var2) {
                            var2.printStackTrace();
                            System.out.println("*********** Failed to load player data for " + player.getName() + " when respawning! ***********");
                        }
                    } else {
                        ItemLoreStats.this.updateHealth(player);
                        ItemLoreStats.this.updatePlayerSpeed(player);
                        ItemLoreStats.this.setBonuses.updateSetBonus(player);
                        ItemLoreStats.this.removeWeaponSpeedEffects(player);
                    }

                }, 3L);
            }

        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            if (event.getPlayer() instanceof Player) {
                Player player = event.getPlayer();
                if (!(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml")).exists()) {
                    if (!player.isDead()) {
                        player.setMaxHealth(20.0D);
                        if (player.getHealth() > 20.0D) {
                            player.setHealth(20.0D);
                        }
                    } else {
                        player.setMaxHealth(20.0D);
                    }
                } else if ((new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml")).exists()) {
                    try {
                        ItemLoreStats.this.PlayerDataConfig = new YamlConfiguration();
                        ItemLoreStats.this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));
                        ItemLoreStats.this.PlayerDataConfig.set("extra.logoutHealth", Math.round(player.getHealth()));
                        ItemLoreStats.this.PlayerDataConfig.set("extra.maxHealth", Math.round(player.getMaxHealth()));
                        ItemLoreStats.this.PlayerDataConfig.set("extra.hunger", player.getFoodLevel());
                        ItemLoreStats.this.PlayerDataConfig.set("extra.xp", player.getExp());
                        ItemLoreStats.this.PlayerDataConfig.set("extra.level", player.getLevel());
                        ItemLoreStats.this.PlayerDataConfig.set("extra.combatLogVisible", ItemLoreStats.this.combatLogVisible.get(player.getName()));
                        ItemLoreStats.this.PlayerDataConfig.save(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");
                        if (!player.isDead()) {
                            player.setMaxHealth(20.0D);
                            if (player.getHealth() > 20.0D) {
                                player.setHealth(20.0D);
                            }
                        } else {
                            player.setMaxHealth(20.0D);
                        }
                    } catch (Exception var4) {
                        var4.printStackTrace();
                        System.out.println("*********** Failed to save player data for " + player.getName() + " when logging out! ***********");
                    }
                }
            }

        }

        @EventHandler
        public void onDropItemEvent(PlayerDropItemEvent event) {
            final Player player = event.getPlayer();
            if (!ItemLoreStats.this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                ItemLoreStats.this.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, () -> {
                    ItemLoreStats.this.updateHealth(player);
                    ItemLoreStats.this.updatePlayerSpeed(player);
                    ItemLoreStats.this.setBonuses.updateSetBonus(player);
                    ItemLoreStats.this.removeWeaponSpeedEffects(player);
                    ItemLoreStats.this.updateMana(player);
                }, 2L);
            }

        }

        @EventHandler
        public void onPickupCustomItem(PlayerPickupItemEvent event) {
            Player player = event.getPlayer();
            ItemStack item = event.getItem().getItemStack();
            if (!ItemLoreStats.this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName()) && ItemLoreStats.this.getConfig().getBoolean("messages.itemLooted") && item != null && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().startsWith("ILS_")) {
                ItemStack itemStack = item.clone();
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(item.getItemMeta().getDisplayName().substring(4));
                item.setItemMeta(itemMeta);
                event.getItem().setItemStack(itemStack);
                player.sendMessage(ItemLoreStats.this.util_GetResponse.getResponse("Item.Looted", player, player, item.getItemMeta().getDisplayName(), item.getItemMeta().getDisplayName()));
            }

        }

        @EventHandler
        public void onGameModeChange(PlayerGameModeChangeEvent event) {
            final Player player = event.getPlayer();
            ItemLoreStats.this.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, () -> ItemLoreStats.this.updateHealth(player), 2L);
        }

        @EventHandler
        public void checkOnPickup(PlayerPickupItemEvent event) {
            if (!event.isCancelled() && !event.getPlayer().getGameMode().equals(GameMode.CREATIVE) && !ItemLoreStats.this.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
                Player player = event.getPlayer();
                ItemStack item = event.getItem().getItemStack().clone();
                if (item != null && item.getAmount() == 1 && ItemLoreStats.this.isTool(item.getType()) && item.hasItemMeta() && item.getItemMeta().getLore() != null && player.getInventory().firstEmpty() == player.getInventory().getHeldItemSlot()) {
                    for (int slot = player.getInventory().getHeldItemSlot() + 1; slot < 35; ++slot) {
                        if (player.getInventory().getItem(slot) == null) {
                            if (ItemLoreStats.this.gearStats.getSoulboundName(player, item) != "" && !ItemLoreStats.this.gearStats.getSoulboundName(player, item).equals(player.getName())) {
                                event.setCancelled(true);
                                event.getItem().remove();
                                event.getPlayer().getInventory().setItem(slot, item);
                                break;
                            }

                            if (ItemLoreStats.this.gearStats.getXPLevelRequirement(player, item) != 0 && ItemLoreStats.this.gearStats.getXPLevelRequirement(player, item) > player.getLevel()) {
                                event.setCancelled(true);
                                event.getItem().remove();
                                event.getPlayer().getInventory().setItem(slot, item);
                                break;
                            }

                            if (ItemLoreStats.this.gearStats.getClass(item) != null && !player.hasPermission("ils.use." + ItemLoreStats.this.gearStats.getClass(item))) {
                                event.setCancelled(true);
                                event.getItem().remove();
                                event.getPlayer().getInventory().setItem(slot, item);
                                break;
                            }
                        }
                    }
                }

                ItemLoreStats.this.updateHealth(player);
                ItemLoreStats.this.updatePlayerSpeed(player);
                ItemLoreStats.this.updateMana(player);
                ItemLoreStats.this.setBonuses.updateSetBonus(player);
            }

        }

        @EventHandler
        public void onPlayerHeldItemChange(final PlayerItemHeldEvent event) {
            if (!ItemLoreStats.this.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
                final Player playerFinal = event.getPlayer();
                ItemLoreStats.this.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, () -> {
                    ItemStack checkItemHeld = playerFinal.getInventory().getItem(event.getNewSlot());
                    if (checkItemHeld != null && checkItemHeld.getType() != Material.AIR && checkItemHeld.getItemMeta() != null && checkItemHeld.getItemMeta().getLore() != null && ItemLoreStats.this.isTool(checkItemHeld.getType())) {
                        if (ItemLoreStats.this.gearStats.playerHeldItemChangeSoulboundNameItemInHand(checkItemHeld) != null && !ItemLoreStats.this.gearStats.playerHeldItemChangeSoulboundNameItemInHand(checkItemHeld).equals(playerFinal.getName())) {
                            ItemLoreStats.this.swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                            playerFinal.sendMessage(ItemLoreStats.this.util_GetResponse.getResponse("SoulboundMessages.BoundToSomeoneElseForItemInHand", playerFinal, playerFinal, String.valueOf(ItemLoreStats.this.gearStats.playerHeldItemChangeXPLevelRequirementItemInHand(checkItemHeld)), String.valueOf(ItemLoreStats.this.gearStats.playerHeldItemChangeXPLevelRequirementItemInHand(checkItemHeld))));
                            playerFinal.sendMessage(ItemLoreStats.this.util_GetResponse.getResponse("SoulboundMessages.BoundToSomeoneElseForItemInHand", playerFinal, playerFinal, ItemLoreStats.this.gearStats.playerHeldItemChangeSoulboundNameItemInHand(checkItemHeld), ItemLoreStats.this.gearStats.playerHeldItemChangeSoulboundNameItemInHand(checkItemHeld)));
                            return;
                        }

                        if (ItemLoreStats.this.gearStats.playerHeldItemChangeClassItemInHand(checkItemHeld) != null && !playerFinal.hasPermission("ils.use." + ItemLoreStats.this.gearStats.playerHeldItemChangeClassItemInHand(checkItemHeld))) {
                            ItemLoreStats.this.swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                            playerFinal.sendMessage(ItemLoreStats.this.util_GetResponse.getResponse("ClassRequirementMessages.NotRequiredClassForItemInHand", playerFinal, playerFinal, String.valueOf(ItemLoreStats.this.gearStats.playerHeldItemChangeClassItemInHand(checkItemHeld)), String.valueOf(ItemLoreStats.this.gearStats.playerHeldItemChangeClassItemInHand(checkItemHeld))));
                            return;
                        }

                        if (ItemLoreStats.this.gearStats.playerHeldItemChangeXPLevelRequirementItemInHand(checkItemHeld) != 0 && ItemLoreStats.this.gearStats.playerHeldItemChangeXPLevelRequirementItemInHand(checkItemHeld) > playerFinal.getLevel()) {
                            ItemLoreStats.this.swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                            playerFinal.sendMessage(ItemLoreStats.this.util_GetResponse.getResponse("LevelRequirementMessages.LevelTooLowForItemInHand", playerFinal, playerFinal, String.valueOf(ItemLoreStats.this.gearStats.playerHeldItemChangeXPLevelRequirementItemInHand(checkItemHeld)), String.valueOf(ItemLoreStats.this.gearStats.playerHeldItemChangeXPLevelRequirementItemInHand(checkItemHeld))));
                            return;
                        }
                    }

                    ItemLoreStats.this.updateHealth(playerFinal);
                    ItemLoreStats.this.updatePlayerSpeed(playerFinal);
                    ItemLoreStats.this.updateMana(playerFinal);
                    ItemLoreStats.this.setBonuses.updateSetBonus(playerFinal);
                }, 2L);
            }

        }

        @EventHandler
        public void onInventoryDrag(InventoryDragEvent event) {
            if (!event.isCancelled() && !event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE) && (event.getInventory().getType().equals(InventoryType.CRAFTING) || event.getInventory().getType().equals(InventoryType.PLAYER) || event.getInventory().getType().equals(InventoryType.FURNACE) || event.getInventory().getType().equals(InventoryType.DROPPER) || event.getInventory().getType().equals(InventoryType.HOPPER) || event.getInventory().getType().equals(InventoryType.DISPENSER) || event.getInventory().getType().equals(InventoryType.CHEST) || event.getInventory().getType().equals(InventoryType.ENCHANTING) || event.getInventory().getType().equals(InventoryType.ENDER_CHEST))) {
                Player player = (Player) event.getWhoClicked();
                if (event.getOldCursor() != null) {
                    ItemStack item = event.getOldCursor().clone();
                    if (!ItemLoreStats.this.getConfig().getBoolean("usingMcMMO")) {
                        ItemLoreStats.this.durability.syncArmourDurability(player);
                    }

                    if (ItemLoreStats.this.isArmourSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", "")) && ItemLoreStats.this.isArmour(item.getType()) || ItemLoreStats.this.isHotbarSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", "")) && player.getInventory().getHeldItemSlot() == ItemLoreStats.this.getRawHeldItemSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", "")) && ItemLoreStats.this.isTool(item.getType())) {
                        if (!ItemLoreStats.this.xpLevel.checkXPLevel(player, item)) {
                            event.setCancelled(true);
                            player.updateInventory();
                            return;
                        }

                        if (!ItemLoreStats.this.soulbound.checkSoulbound(player, item)) {
                            event.setCancelled(true);
                            player.updateInventory();
                            return;
                        }

                        if (!ItemLoreStats.this.classes.checkClasses(player, item)) {
                            event.setCancelled(true);
                            player.updateInventory();
                            return;
                        }
                    }
                }
            }

        }

        @EventHandler
        public void healthIncreaseOnDragEquip(InventoryDragEvent event) {
            if (!event.isCancelled() && !event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
                final Player player = (Player) event.getWhoClicked();
                if ((event.getInventory().getType().equals(InventoryType.CRAFTING) || event.getInventory().getType().equals(InventoryType.PLAYER) || event.getInventory().getType().equals(InventoryType.FURNACE) || event.getInventory().getType().equals(InventoryType.DROPPER) || event.getInventory().getType().equals(InventoryType.HOPPER) || event.getInventory().getType().equals(InventoryType.DISPENSER) || event.getInventory().getType().equals(InventoryType.CHEST) || event.getInventory().getType().equals(InventoryType.ENCHANTING) || event.getInventory().getType().equals(InventoryType.ENDER_CHEST)) && (ItemLoreStats.this.isArmourSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", "")) || ItemLoreStats.this.isHotbarSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", "")))) {
                    ItemLoreStats.this.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, () -> {
                        player.updateInventory();
                        ItemLoreStats.this.updateHealth(player);
                        ItemLoreStats.this.updatePlayerSpeed(player);
                        ItemLoreStats.this.updateMana(player);
                    }, 1L);
                }
            }

        }

        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            if (!event.isCancelled() && !event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE) && !ItemLoreStats.this.getConfig().getStringList("disabledInWorlds").contains(event.getWhoClicked().getWorld().getName()) && (event.getInventory().getType().equals(InventoryType.CRAFTING) || event.getInventory().getType().equals(InventoryType.PLAYER) || event.getInventory().getType().equals(InventoryType.FURNACE) || event.getInventory().getType().equals(InventoryType.DROPPER) || event.getInventory().getType().equals(InventoryType.HOPPER) || event.getInventory().getType().equals(InventoryType.DISPENSER) || event.getInventory().getType().equals(InventoryType.CHEST) || event.getInventory().getType().equals(InventoryType.ENCHANTING) || event.getInventory().getType().equals(InventoryType.ENDER_CHEST))) {
                Player player = (Player) event.getWhoClicked();
                if (event.getCurrentItem() != null) {
                    ItemStack item = event.getCursor().clone();
                    if (event.isShiftClick()) {
                        item = event.getCurrentItem().clone();
                    }

                    if (!ItemLoreStats.this.getConfig().getBoolean("usingMcMMO")) {
                        ItemLoreStats.this.durability.syncArmourDurability(player);
                    }

                    if (event.getSlotType().equals(SlotType.ARMOR) && ItemLoreStats.this.isArmour(item.getType()) || event.isShiftClick() && ItemLoreStats.this.isArmour(item.getType()) || event.getSlotType().equals(SlotType.QUICKBAR) && event.getSlot() == player.getInventory().getHeldItemSlot() && ItemLoreStats.this.isTool(item.getType())) {
                        if (!ItemLoreStats.this.xpLevel.checkXPLevel(player, item)) {
                            event.setCancelled(true);
                            player.updateInventory();
                            return;
                        }

                        if (!ItemLoreStats.this.soulbound.checkSoulbound(player, item)) {
                            event.setCancelled(true);
                            player.updateInventory();
                            return;
                        }

                        if (!ItemLoreStats.this.classes.checkClasses(player, item)) {
                            event.setCancelled(true);
                            player.updateInventory();
                            return;
                        }
                    }
                }
            }

        }

        @EventHandler
        public void healthIncreaseOnEquip(InventoryClickEvent event) {
            if (!event.isCancelled() && !event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
                final Player player = (Player) event.getWhoClicked();
                if ((event.getInventory().getType().equals(InventoryType.CRAFTING) || event.getInventory().getType().equals(InventoryType.PLAYER) || event.getInventory().getType().equals(InventoryType.FURNACE) || event.getInventory().getType().equals(InventoryType.DROPPER) || event.getInventory().getType().equals(InventoryType.HOPPER) || event.getInventory().getType().equals(InventoryType.DISPENSER) || event.getInventory().getType().equals(InventoryType.CHEST) || event.getInventory().getType().equals(InventoryType.ENCHANTING) || event.getInventory().getType().equals(InventoryType.ENDER_CHEST)) && (event.getSlotType().equals(SlotType.ARMOR) || event.getSlotType().equals(SlotType.QUICKBAR) || event.isShiftClick())) {
                    ItemLoreStats.this.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, () -> {
                        player.updateInventory();
                        ItemLoreStats.this.updateHealth(player);
                        ItemLoreStats.this.updatePlayerSpeed(player);
                        ItemLoreStats.this.updateMana(player);
                    }, 1L);
                }
            }

        }

        @EventHandler
        public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
            if (event.getPlayer() instanceof Player) {
                Player player = event.getPlayer();
                final Player playerFinal = event.getPlayer();
                ItemLoreStats.this.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, () -> {
                    ItemLoreStats.this.updateHealth(playerFinal);
                    ItemLoreStats.this.updatePlayerSpeed(playerFinal);
                    ItemLoreStats.this.setBonuses.updateSetBonus(playerFinal);
                    ItemLoreStats.this.updateMana(playerFinal);
                }, 2L);
                if (!ItemLoreStats.this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                    ItemLoreStats.this.updateBarAPI(playerFinal);
                    String weaponSpeed = "normal";
                    ItemStack checkItemHeld = player.getItemInHand();
                    if (checkItemHeld != null && checkItemHeld.getType() != Material.AIR && checkItemHeld.getItemMeta() != null && checkItemHeld.getItemMeta().getLore() != null && ItemLoreStats.plugin.isTool(checkItemHeld.getType())) {
                        weaponSpeed = ItemLoreStats.this.gearStats.getSwingSpeedItemInHand(player).toLowerCase();
                        if ("very fast".equalsIgnoreCase(weaponSpeed)) {
                            if (event.getPlayer().hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
                                event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            }

                            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 1));
                        } else if ("fast".equalsIgnoreCase(weaponSpeed)) {
                            if (event.getPlayer().hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
                                event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            }

                            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 0));
                        } else if ("slow".equalsIgnoreCase(weaponSpeed)) {
                            if (event.getPlayer().hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                                event.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
                            }

                            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, 0));
                        } else if ("very slow".equalsIgnoreCase(weaponSpeed)) {
                            if (event.getPlayer().hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                                event.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
                            }

                            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, 1));
                        } else if ("normal".equalsIgnoreCase(weaponSpeed)) {
                            ItemLoreStats.this.removeWeaponSpeedEffects(event.getPlayer());
                        }
                    }
                } else {
                    BarAPI.removeBar(playerFinal);
                }
            }

        }

        @EventHandler
        public void modifyMobHealth(CreatureSpawnEvent event) {
            if (!ItemLoreStats.this.getConfig().getStringList("disabledInWorlds").contains(event.getEntity().getWorld().getName())) {
                if (ItemLoreStats.plugin.getConfig().getBoolean("ILSLootFromNaturalSpawnsOnly") && event.getSpawnReason().equals(SpawnReason.NATURAL)) {
                    event.getEntity().setMetadata("naturalSpawn", new FixedMetadataValue(ItemLoreStats.plugin, true));
                }

                if (ItemLoreStats.this.getConfig().getString("npcModifier." + event.getEntity().getWorld().getName()) != null) {
                    String worldName = event.getEntity().getWorld().getName();
                    Location loc = new Location(event.getEntity().getWorld(), ItemLoreStats.this.getConfig().getInt("npcModifier." + worldName + ".location.x"), ItemLoreStats.this.getConfig().getInt("npcModifier." + worldName + ".location.y"), ItemLoreStats.this.getConfig().getInt("npcModifier." + worldName + ".location.z"));
                    double distance = event.getEntity().getLocation().distance(loc);
                    double distanceHealth = (double) Math.round(event.getEntity().getHealth() + distance * ItemLoreStats.this.getConfig().getDouble("npcModifier." + worldName + ".healthMultiplier"));
                    double additionalHealth = ItemLoreStats.this.gearStats.getHealthItemInHand(event.getEntity()) + ItemLoreStats.this.gearStats.getHealthGear(event.getEntity());
                    double newHealth = distanceHealth + additionalHealth;
                    event.getEntity().setMaxHealth(Double.valueOf(newHealth).intValue());
                    event.getEntity().setHealth(Double.valueOf(newHealth).intValue());
                }
            }

        }
    }
}
