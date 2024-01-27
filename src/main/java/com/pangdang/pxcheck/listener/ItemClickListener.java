package com.pangdang.pxcheck.listener;

import com.pangdang.pxcheck.PXCheck;
import com.pangdang.pxcheck.manage.EconomyManager;
import com.pangdang.pxcheck.message.MessageContext;
import com.pangdang.pxcheck.message.MessageType;
import com.pangdang.pxcheck.nms.tank.NmsItemStackUtil;
import com.pangdang.pxcheck.nms.wrapper.NBTTagCompoundWrapper;
import com.pangdang.pxcheck.nms.wrapper.NmsItemWrapper;
import com.pangdang.pxcheck.util.DecimalFormat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ItemClickListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        MessageContext messageContext = MessageContext.getInstance();
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();


        if (!(event.getHand() == EquipmentSlot.HAND)) return;
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (itemInHand == null || itemInHand.getType().equals(Material.AIR)) return;

        NmsItemWrapper itemStackWrapper = NmsItemStackUtil.getInstance().asNMSCopy(itemInHand);
        NBTTagCompoundWrapper nbtTagCompoundWrapper = itemStackWrapper.getTag();
        if (nbtTagCompoundWrapper == null) return;

        String tag = nbtTagCompoundWrapper.getString("checkAmount");
        if (tag == null || tag.isEmpty()) return;

        String df_money = DecimalFormat.decimalFormat(Integer.parseInt(tag));

        EconomyManager.deposit(player, Double.parseDouble(tag));
        itemInHand.setAmount(itemInHand.getAmount() - 1);
        messageContext.get(MessageType.MAIN, "used_check",
                (it) -> it.replace("{check_amount}", df_money)).send(player);
        player.playSound(player.getLocation(), PXCheck.getInstance().getConfigManager().getSound("click_check"), 1.0f, 1.0f);

    }
}
