package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.modules.Sqlite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListPlayersCommand implements CommandExecutor {
    private static final int PAGE_SIZE = 5;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        int page = 1;
        if (args.length == 1) {
            try { page = Integer.parseInt(args[0]); } catch (NumberFormatException ignore) {}
        }
        List<String> users = Sqlite.getSqlite().listUsernames();
        int maxPage = (int) Math.ceil(users.size() / (double) PAGE_SIZE);
        if (page < 1 || page > maxPage) {
            sender.sendMessage("Page out of range. Max " + maxPage);
            return true;
        }
        sender.sendMessage("Users page " + page + "/" + maxPage + ":");
        int start = (page - 1) * PAGE_SIZE;
        for (int i = start; i < start + PAGE_SIZE && i < users.size(); i++) {
            sender.sendMessage("- " + users.get(i));
        }
        return true;
    }
}
