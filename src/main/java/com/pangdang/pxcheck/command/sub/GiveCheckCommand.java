package com.pangdang.pxcheck.command.sub;

import com.pangdang.pxcheck.PXCheck;
import com.pangdang.pxcheck.command.SubCommand;
import com.pangdang.pxcheck.manage.CheckManager;
import com.pangdang.pxcheck.message.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CheckGiveCommand implements SubCommand {
    @Override
    public String getKoName() {
        return "수표관리";
    }

    @Override
    public String getKoDescription() {
        return "수표 설정을 관리합니다.";
    }

    @Override
    public String getKoUsage() {
        return null;
    }

    @Override
    public String getPermission(CommandSender sender) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        String targetPlayerName = args[1];
        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
        String amount = args[2];

        CheckManager.giveCheck(targetPlayer, Integer.parseInt(amount));

    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
