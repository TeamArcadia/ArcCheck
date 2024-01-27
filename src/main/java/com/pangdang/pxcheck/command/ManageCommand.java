package com.pangdang.pxcheck.command;

import com.pangdang.pxcheck.command.sub.GiveCheckCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Command implements TabExecutor {

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public Command(JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand("수표관리");
        if (command != null) {
            command.setExecutor(this);
            command.setTabCompleter(this);
            registerSubCommands();
        }
    }

    private void registerSubCommands() {
        registerSubCommand(new GiveCheckCommand());
    }

    private void registerSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getKoName(), subCommand);
    }

    private boolean hasPermission(CommandSender sender, SubCommand subCommand) {
        String permission = "px.check.manage" + subCommand.getPermission(sender);
        if (permission == null || permission.isEmpty()) {
            return true;
        }
        return sender.hasPermission(permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            Set<SubCommand> uniqueSubCommands = new HashSet<>(subCommands.values());

            sender.sendMessage(" §6§m                                         §r");
            sender.sendMessage("§7[ §e§l수표§7§l관리 §f| §f명령어 도움말 §7]");

            for (SubCommand subCommand : uniqueSubCommands) {
                sender.sendMessage("§e▶ §f/유저관리 " + subCommand.getKoName() + " " + subCommand.getKoUsage() );
                sender.sendMessage("      §7└" + subCommand.getKoDescription());
            }

            sender.sendMessage("");
            return true;
        }

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());

        if (subCommand != null && hasPermission(sender, subCommand)) {
            subCommand.execute(sender, args);
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            for (SubCommand subCommand : subCommands.values()) {
                completions.add(subCommand.getKoName());
            }
        } else {
            SubCommand subCmdInstance = subCommands.get(args[0].toLowerCase());
            if (subCmdInstance != null) {
                completions = subCmdInstance.tabComplete(sender, args);
            }
        }
        return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<>());
    }
}