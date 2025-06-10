package com.blbilink.blbilogin.modules.events;

import com.blbilink.blbilogin.BlbiLogin;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatLogger implements Listener {
    private final BlbiLogin plugin;
    private final File logFile;

    public ChatLogger(BlbiLogin plugin) {
        this.plugin = plugin;
        this.logFile = new File(plugin.getDataFolder(), "LoraMemory.txt");
        if (!logFile.exists()) {
            try {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        plugin.foliaUtil.runTask(plugin, task -> {
            log(player, message);
        });
    }

    private void log(Player player, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        World world = player.getWorld();
        Location loc = player.getLocation();
        GameMode gamemode = player.getGameMode();

        ItemStack held = player.getInventory().getItemInMainHand();
        String heldName = itemName(held);

        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chest = player.getInventory().getChestplate();
        ItemStack legs = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        String line = String.format("[Time: %s] [World: %s] [X: %d, Y: %d, Z: %d] [Gamemode: %s] [Player: %s] [Held: %s] [Armor: %s, %s, %s, %s] %s", 
                timestamp, world.getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
                gamemode.name(), player.getName(), heldName,
                itemName(helmet), itemName(chest), itemName(legs), itemName(boots), message);

        try {
            Files.writeString(logFile.toPath(), line + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {
        }
    }

    private String itemName(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return "None";
        }
        return item.getType().name();
    }
}

