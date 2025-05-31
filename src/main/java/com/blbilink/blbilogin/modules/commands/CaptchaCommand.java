package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.vars.Configvar;
import com.blbilink.blbilogin.modules.messages.PlayerSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.blbilink.blbilogin.BlbiLogin.plugin;

public class CaptchaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.i18n.as("msgCommandOnlyPlayer", true));
            return true;
        }

        String code = Configvar.captchaCodes.get(player.getName());
        if (code == null) {
            player.sendMessage(plugin.i18n.as("msgCaptchaNone", true));
            return true;
        }
        if (args.length != 1) {
            player.sendMessage(plugin.i18n.as("msgCaptchaUsage", true, code));
            return true;
        }

        if (args[0].equals(code)) {
            Configvar.captchaCodes.remove(player.getName());
            Configvar.captchaPassed.add(player.getName());
            player.sendMessage(plugin.i18n.as("msgCaptchaSuccess", true));

            // After captcha is passed, begin sending login prompts so the
            // player knows to log in.
            PlayerSender.startLoginPrompts(player);
        } else {
            player.sendMessage(plugin.i18n.as("msgCaptchaFail", true));
        }
        return true;
    }
}
