package com.pangdang.pxcheck.command;

import com.pangdang.pxcheck.PXCheck;
import com.pangdang.pxcheck.manage.CheckManager;
import com.pangdang.pxcheck.manage.EconomyManager;
import com.pangdang.pxcheck.message.MessageContext;
import com.pangdang.pxcheck.message.MessageType;
import com.pangdang.pxcheck.util.DecimalFormat;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        MessageContext messageContext = MessageContext.getInstance();

        if (!(sender instanceof Player player)) {
            messageContext.get(MessageType.NORMAL, "player_only").send(sender);
            return false;
        }

        if (args.length < 2) {
            messageContext.get(MessageType.ERROR, "player_wrong_command").send(sender);
            return false;
        }

        if (args[0].equalsIgnoreCase("발행")) {
            int amount = Integer.valueOf(args[1]);
            String df_money = DecimalFormat.decimalFormat(amount);

            if (EconomyManager.getBalance(player) >= amount) {
                EconomyManager.withdraw(player, amount);

                CheckManager.giveCheck(player, amount);

                messageContext.get(MessageType.MAIN, "get_check",
                        (it) -> it.replace("{check_amount}", df_money)).send(sender);
                player.playSound(player.getLocation(), PXCheck.getInstance().getConfigManager().getSound("get_check"), 1.0f, 1.0f);

            } else {
                messageContext.get(MessageType.MAIN, "not_enough_balance").send(sender);
                player.playSound(player.getLocation(), PXCheck.getInstance().getConfigManager().getSound("failed_get_check"), 1.0f, 1.0f);
            }
            return true;
        } else {
            messageContext.get(MessageType.ERROR, " player_wrong_command").send(sender);
            return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (label.equalsIgnoreCase("수표")) {
            if (args.length == 1) {
                completions.add("발행");
            } else if (args.length == 2) {
                completions.addAll(Arrays.asList("10", "100", "1000"));
            }
        }
        return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<>());
    }
}