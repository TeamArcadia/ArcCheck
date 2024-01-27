package com.pangdang.pxcheck.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    String getKoName();

    String getKoDescription();

    String getKoUsage();

    String getPermission(CommandSender sender);

    void execute(CommandSender sender, String[] args);

    List<String> tabComplete(CommandSender sender, String[] args);

}