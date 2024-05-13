package com.pangdang.pxcheck.manage;


import com.pangdang.pxcheck.PXCheck;
import com.pangdang.pxcheck.nms.tank.NmsItemStackUtil;
import com.pangdang.pxcheck.nms.wrapper.NBTTagCompoundWrapper;
import com.pangdang.pxcheck.nms.wrapper.NmsItemWrapper;
import com.pangdang.pxcheck.util.DecimalFormat;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CheckManager {

    public ItemStack createCheck(int amount) {
        String amount_string = String.valueOf(amount);

        Material material = Material.getMaterial(PXCheck.getInstance().getConfigManager().getCheckItemType());
        ItemStack itemStack = new ItemStack(material);

        NmsItemWrapper nmsItemWrapper = NmsItemStackUtil.getInstance().asNMSCopy(itemStack);
        NBTTagCompoundWrapper nbtTagCompoundWrapper = nmsItemWrapper.getTag();

        if (nbtTagCompoundWrapper == null) nbtTagCompoundWrapper = NmsItemStackUtil.getInstance().getNbtCompoundUtil().newInstance();

        nbtTagCompoundWrapper.setString("checkAmount", amount_string);
        nmsItemWrapper.setTag(nbtTagCompoundWrapper);
        ItemMeta itemMeta = NmsItemStackUtil.getInstance().asBukkitCopy(nmsItemWrapper).getItemMeta();

        String df_money = DecimalFormat.decimalFormat(amount);

        String name = PXCheck.getInstance().getConfigManager().getCheckItemInfo("name").replace("{check_amount}", df_money);
        itemMeta.setDisplayName(name);


        List<String> lore = PXCheck.getInstance().getConfig().getStringList("check-item.lore");

        List<String> coloredLore = lore.stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList());

        itemMeta.setLore(coloredLore);

        itemMeta.setCustomModelData(Integer.valueOf(PXCheck.getInstance().getConfigManager().getCheckItemInfo("custom_model_data")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static void giveCheck(Player player, int amount) {
        CheckManager checkManager = new CheckManager();
        ItemStack check = checkManager.createCheck(amount);
        player.getInventory().addItem(check);
    }


}