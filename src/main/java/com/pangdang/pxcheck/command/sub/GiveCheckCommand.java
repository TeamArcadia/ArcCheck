package com.pangdang.pxcheck.command.sub;

import com.pangdang.pxcheck.PXCheck;
import com.pangdang.pxcheck.command.SubCommand;
import com.pangdang.pxcheck.manage.CheckManager;
import com.pangdang.pxcheck.message.MessageContext;
import com.pangdang.pxcheck.message.MessageType;
import com.pangdang.pxcheck.util.DecimalFormat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiveCheckCommand implements SubCommand {
    private final PXCheck plugin = PXCheck.getInstance();

    @Override
    public String getKoName() {
        return "지급";
    }

    @Override
    public String getKoDescription() {
        return "플러이어에게 수표를 지급합니다";
    }

    @Override
    public String getKoUsage() {
        return "<플레이어>";
    }

    @Override
    public String getPermission(CommandSender sender) {
        return "give";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        MessageContext messageContext = MessageContext.getInstance();
        Player player = (Player) sender;

        if (args.length == 3) {
            Player target = plugin.getServer().getPlayerExact(args[1]);
            int amount = Integer.parseInt(args[2]);

            CheckManager.giveCheck(target, amount);
            String df_money = DecimalFormat.decimalFormat(amount);

            player.playSound(player.getLocation(), PXCheck.getInstance().getConfigManager().getSound("give_check"), 1.0f, 1.0f);
            messageContext.get(MessageType.MAIN, "give_check",
                    (it) -> it.replace("{check_amount}", df_money)
                            .replace("{player}" , target.getName())).send(sender);

        } else {
            messageContext.get(MessageType.ERROR, "wrong_command").send(sender);
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        if (sender.isOp()) {
            if (args.length == 2) {
                completions.addAll(PXCheck.getInstance().getServer().getOnlinePlayers().stream().map(Player::getName).toList());
            } else if (args.length == 3) {
                completions.addAll(Arrays.asList("10", "100", "1000"));
            }
        }
        return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<>());
    }
}