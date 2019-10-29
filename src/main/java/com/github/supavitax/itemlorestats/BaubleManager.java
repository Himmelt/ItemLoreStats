package com.github.supavitax.itemlorestats;

import net.minecraft.server.v1_7_R4.NBTCompressedStreamTools;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * @author Himmelt
 */
public class BaubleManager implements Listener {

    private int rowSize = 1;
    private String baubleName = "Bauble";
    private final HashMap<Integer, String> BAUBLES_MARK = new HashMap<>();
    private final HashMap<UUID, Inventory> PLAYER_BAUBLES = new HashMap<>();

    private final Path baublesPath;

    public BaubleManager(Path root) {
        baublesPath = root.resolve("baubles");
        try {
            Files.createDirectories(baublesPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        ConfigurationSection section = ItemLoreStats.plugin.getConfig().getConfigurationSection("baubles");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                if ("name".equals(key)) {
                    baubleName = section.getString(key);
                    if (baubleName == null || baubleName.isEmpty()) {
                        baubleName = "Bauble";
                    }
                    section.set("name", baubleName);
                } else if ("size".equals(key)) {
                    rowSize = section.getInt(key);
                    if (rowSize <= 0) {
                        rowSize = 1;
                    } else if (rowSize > 6) {
                        rowSize = 6;
                    }
                    section.set("size", rowSize);
                } else if (key.matches("\\d+")) {
                    int slot = Integer.parseInt(key);
                    String mark = section.getString(key);
                    BAUBLES_MARK.put(slot, mark);
                }
            }
        }
    }

    public HashMap<Integer, ItemStack> getBaubles(LivingEntity entity) {
        HashMap<Integer, ItemStack> stacks = new HashMap<>();
        if (!BAUBLES_MARK.isEmpty() && entity instanceof Player) {
            Set<Integer> slots = BAUBLES_MARK.keySet();
            Inventory inv = PLAYER_BAUBLES.get(entity.getUniqueId());
            if (inv != null) {
                for (int slot : slots) {
                    if (slot >= 0 && slot < 9 * rowSize) {
                        stacks.put(slot, inv.getItem(slot));
                    }
                }
            }
        }
        return stacks;
    }

    public Inventory getBaubleInv(UUID uuid) {
        return PLAYER_BAUBLES.get(uuid);
    }

    public Inventory getBaubleInv(Player player) {
        return PLAYER_BAUBLES.get(player.getUniqueId());
    }

    public void openBaubleInv(Player player) {
        Inventory inv = PLAYER_BAUBLES.computeIfAbsent(player.getUniqueId(), uid -> Bukkit.createInventory(player, 9 * rowSize, "饰品栏"));
        player.openInventory(inv);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Inventory inv = Bukkit.createInventory(player, 9 * rowSize, "饰品栏");
        PLAYER_BAUBLES.put(uuid, inv);
        Path dataFile = baublesPath.resolve(uuid.toString() + ".dat");
        if (Files.exists(dataFile)) {
            try {
                NBTTagCompound comp = NBTCompressedStreamTools.a(new FileInputStream(dataFile.toFile()));
                NBTTagList list = comp.getList("items", 10);
                for (int i = 0; i < list.size(); i++) {
                    NBTTagCompound item = list.get(i);
                    int slot = item.getByte("slot");
                    if (slot >= 0 && slot < 9 * rowSize) {
                        inv.setItem(slot, CraftItemStack.asCraftMirror(net.minecraft.server.v1_7_R4.ItemStack.createStack(item)));
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Inventory inv = PLAYER_BAUBLES.remove(uuid);
        if (inv != null) {
            NBTTagList list = new NBTTagList();
            for (byte i = 0; i < inv.getSize(); i++) {
                ItemStack stack = inv.getItem(i);
                if (stack != null && stack.getType() != Material.AIR) {
                    NBTTagCompound item = new NBTTagCompound();
                    CraftItemStack.asNMSCopy(stack).save(item);
                    item.setByte("slot", i);
                    list.add(item);
                }
            }
            if (list.size() > 0) {
                NBTTagCompound comp = new NBTTagCompound();
                comp.set("items", list);
                try {
                    Path dataFile = baublesPath.resolve(uuid.toString() + ".dat");
                    if (Files.notExists(dataFile)) {
                        Files.createFile(dataFile);
                    }
                    NBTCompressedStreamTools.a(comp, new FileOutputStream(dataFile.toFile()));
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getMark(int slot) {
        return BAUBLES_MARK.getOrDefault(slot, "");
    }

    public String getBaubleName() {
        return baubleName;
    }
}
