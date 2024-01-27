package com.pangdang.pxcheck.message;

import com.pangdang.pxcheck.PXCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

@AllArgsConstructor
@Data
public class PXMessage {

    private String prefix;
    private String message;

    public void send(CommandSender sender) {
        if (message.isEmpty()) return;
        sender.sendMessage(prefix + message);
    }

    public void send(ConsoleCommandSender console) {
        if (message.isEmpty()) return;
        console.sendMessage(prefix + message);
    }

    public void broadcast() {
        if (message.isEmpty()) return;
        PXCheck.getInstance().getServer().broadcastMessage(prefix + message);
    }

    public String getText() {
        return prefix + message;
    }
}