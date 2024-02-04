package com.pangdang.pxcheck.command.sub;

import com.pangdang.pxcheck.command.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class TestCommand implements SubCommand {
    @Override
    public String getKoName() {
        return "테스트";
    }

    @Override
    public String getKoDescription() {
        return "테스트 명령어입니다.";
    }

    @Override
    public String getKoUsage() {
        return "";
    }

    @Override
    public String getPermission(CommandSender sender) {
        return "permission";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {



    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}