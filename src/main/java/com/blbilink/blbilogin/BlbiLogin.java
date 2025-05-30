// Main class
package com.blbilink.blbilogin;

import com.blbilink.blbilogin.load.LoadConfig;
import com.blbilink.blbilogin.load.LoadFunction;
import com.blbilink.blbilogin.modules.events.CheckOnline;
import com.blbilink.blbilogin.modules.events.LoginAction;
import com.blbilink.blbilogin.vars.Configvar;
import org.blbilink.blbiLibrary.I18n;
import org.blbilink.blbiLibrary.Metrics;
import org.blbilink.blbiLibrary.utils.ConfigUtil;
import org.blbilink.blbiLibrary.utils.FoliaUtil;
import org.blbilink.blbiLibrary.utils.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public final class BlbiLogin extends JavaPlugin implements Listener {
    public static BlbiLogin plugin;
    public I18n i18n;
    public ConfigUtil config;
    public FoliaUtil foliaUtil;

    @Override
    public void onEnable() {
        plugin = this;

        // Initialize plugin
        LoadConfig.loadConfig(this);
        LoadFunction loadFunction = new LoadFunction(this);
        loadFunction.loadFunction();
        LoginAction.INSTANCE.sync(this);
        CheckOnline.INSTANCE.sync(this);
        foliaUtil = new FoliaUtil(this);

        // Verify running on a Folia server
        foliaUtil.checkFolia(true);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        // Print logo
        getLogger().info(TextUtil.getLogo(
                "Loading...",
                "BLBILOGIN",
                "SpigotMC: https://www.spigotmc.org/resources/117672/",
                plugin,
                Arrays.asList("EggFine","ImFoxerARG"),
                Arrays.asList("Mgazul")));

        getLogger().info("6b6t login system injected.");


        // Load bStats metrics
        Metrics metrics = new Metrics(this, 22490);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
    }


    @Override
    public void onDisable() {
        // Print logo
        getLogger().info(TextUtil.getLogo(
                "Disabling...",
                "BLBILOGIN",
                "SpigotMC: https://www.spigotmc.org/resources/117672/",
                plugin,
                List.of("EggFine"),
                List.of("Mgazul")));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {

        if (Configvar.noLoginPlayerList.contains(e.getPlayer().getName()) && Configvar.config.getBoolean("noLoginPlayerCantMove")) {
            String msgNoLoginTryMove = i18n.as("logNoLoginTryMove", false, e.getPlayer().getName());
            this.getLogger().info(msgNoLoginTryMove);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e) {
        if (Configvar.noLoginPlayerList.contains(e.getPlayer().getName()) && Configvar.config.getBoolean("noLoginPlayerCantBreak")) {
            getLogger().info("Unlogged player " + e.getPlayer().getName() + " tried to break block " + e.getBlock().getType().name() + " and was blocked.");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHurt(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player) {

            if (Configvar.noLoginPlayerList.contains(player.getName()) && Configvar.config.getBoolean("noLoginPlayerCantHurt")) {
                EntityDamageEvent.DamageCause currentDamageCause = e.getCause();
                String damageCauseName = currentDamageCause.name();

                getLogger().info("Unlogged player " + player.getName() + " received damage " + damageCauseName + " and was blocked.");
                e.setCancelled(true);
            }
        }
    }
}