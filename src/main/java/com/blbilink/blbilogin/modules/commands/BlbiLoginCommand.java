package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.load.LoadConfig;
import com.blbilink.blbilogin.vars.Configvar;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.blbilink.blbilogin.BlbiLogin.plugin;

public class BlbiLoginCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (command.getName().equalsIgnoreCase("blbilogin")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                reload(sender);
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("savelocation")) {
                saveLocation(sender);
                return true;
            }
        }

        return false;
    }

    private void reload(CommandSender sender) {
        boolean allowed = !(sender instanceof Player p) || p.hasPermission("blbilogin.reload");
        if (!allowed) {
            ((Player) sender).sendMessage(plugin.i18n.as("msgNoPermission", true, ((Player) sender).getName()));
            return;
        }

        // Schedule reload to avoid disabling the plugin while this command is executing
        Bukkit.getScheduler().runTask(plugin, () -> {
            try {
                // Resolve current plugin JAR location
                java.io.File file = new java.io.File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
                org.bukkit.plugin.PluginManager pm = Bukkit.getPluginManager();

                // Disable current instance
                pm.disablePlugin(plugin);

                // Load new instance from the (potentially replaced) JAR
                org.bukkit.plugin.Plugin loaded = ((org.bukkit.plugin.SimplePluginManager) pm).loadPlugin(file);
                loaded.onLoad();
                pm.enablePlugin(loaded);

                if (sender instanceof Player player) {
                    player.sendMessage(plugin.i18n.as("msgReloaded", true, player.getName()));
                } else {
                    plugin.getLogger().info(plugin.i18n.as("msgReloaded", false));
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to reload plugin: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void saveLocation(CommandSender sender) {
        if ((sender instanceof Player player)) {
            if (player.hasPermission("blbilogin.savelocation")) {
                Location loc = player.getLocation();
                Configvar.config.set("locationPos.world", loc.getWorld().getName());
                Configvar.config.set("locationPos.x", loc.getX());
                Configvar.config.set("locationPos.y", loc.getY());
                Configvar.config.set("locationPos.z", loc.getZ());
                Configvar.config.set("locationPos.yaw", loc.getYaw());
                Configvar.config.set("locationPos.pitch", loc.getPitch());
                player.sendMessage(plugin.i18n.as("msgSavedLocation", true, player.getName()));
                try {
                    Configvar.config.save(Configvar.configFile);
                    plugin.getLogger().info(plugin.i18n.as("msgSavedConfigFile", true));
                } catch (IOException e) {
                    plugin.getLogger().warning(plugin.i18n.as("msgFailSaveConfigFile", true));
                    throw new RuntimeException(e);
                }

            } else {
                player.sendMessage(plugin.i18n.as("msgNoPermission", true, player.getName()));
            }
        } else {
            plugin.getLogger().info(plugin.i18n.as("msgCommandOnlyPlayer"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // First level command completion
            String[] subCommands = {"reload", "savelocation"};
            StringUtil.copyPartialMatches(args[0], Arrays.asList(subCommands), completions);
        }

        return completions;
    }
}
