package com.blbilink.blbilogin.load;

import com.blbilink.blbilogin.BlbiLogin;
import com.blbilink.blbilogin.modules.Sqlite;
import com.blbilink.blbilogin.modules.commands.*;
import com.blbilink.blbilogin.modules.events.PlayerJoin;
import com.blbilink.blbilogin.modules.events.PlayerSendMessage;
import com.blbilink.blbilogin.modules.events.PlayerUseCommands;
import com.blbilink.blbilogin.modules.events.PlayerInteraction;
import com.blbilink.blbilogin.modules.events.BlockPluginsCommand;
import com.blbilink.blbilogin.modules.events.AntiBotChatListener;
import com.blbilink.blbilogin.modules.messages.PlayerSender;
import org.bukkit.Bukkit;

import java.util.Objects;

public class LoadFunction {
    private final BlbiLogin plugin;
    public static Sqlite sqlite;
    public LoadFunction(BlbiLogin plugin) {
        this.plugin = plugin;
    }

    public void loadFunction(){
        loadCommands();
        loadListeners();
        loadSqlite();
    }

    private void loadCommands(){
        BlbiLoginCommand blbiLoginCommand = new BlbiLoginCommand();
        Objects.requireNonNull(plugin.getCommand("blbilogin")).setExecutor(blbiLoginCommand);
        Objects.requireNonNull(plugin.getCommand("blbilogin")).setTabCompleter(blbiLoginCommand);


        Objects.requireNonNull(plugin.getCommand("register")).setExecutor(new Register());
        Objects.requireNonNull(plugin.getCommand("login")).setExecutor(new Login());
        Objects.requireNonNull(plugin.getCommand("captcha")).setExecutor(new CaptchaCommand());
        Objects.requireNonNull(plugin.getCommand("resetpassword")).setExecutor(new ResetPassword());
        Objects.requireNonNull(plugin.getCommand("kill")).setExecutor(new KillCommand());
        Objects.requireNonNull(plugin.getCommand("worldstats")).setExecutor(new WorldStatsCommand());
        Objects.requireNonNull(plugin.getCommand("info")).setExecutor(new InfoCommand());
        Objects.requireNonNull(plugin.getCommand("dupe")).setExecutor(new DupeCommand());
        Objects.requireNonNull(plugin.getCommand("tpa")).setExecutor(new TpaCommand());
        Objects.requireNonNull(plugin.getCommand("tphere")).setExecutor(new TphereCommand());
        Objects.requireNonNull(plugin.getCommand("tpaccept")).setExecutor(new TpacceptCommand());
    }
    private void loadListeners(){
        // Register Bukkit event listeners
        Bukkit.getPluginManager().registerEvents(BlbiLogin.plugin, plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerSender(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerUseCommands(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(BlbiLogin.plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerSendMessage(), plugin);
        Bukkit.getPluginManager().registerEvents(new AntiBotChatListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerInteraction(), plugin);
        Bukkit.getPluginManager().registerEvents(new BlockPluginsCommand(), plugin);
        Bukkit.getPluginManager().registerEvents(new com.blbilink.blbilogin.modules.dupe.ChestBoatDupeListener(60), plugin);
        Bukkit.getPluginManager().registerEvents(new com.blbilink.blbilogin.modules.events.WitherSkullExplodeFix(), plugin);
    }

    private void loadSqlite(){
        // Initialize SQLite database
        sqlite = new Sqlite();
    }

}