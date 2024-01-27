package com.pangdang.pxcheck.manage;

import com.pangdang.pxcheck.hook.VaultEconomyHook;
import org.bukkit.OfflinePlayer;

public class EconomyManager {
    private static VaultEconomyHook vaultHook = new VaultEconomyHook();

    public static double getBalance(OfflinePlayer target) {
        double balance = 0;
        balance = vaultHook.getBalance(target);

        return balance;
    }

    public static void deposit(OfflinePlayer target, double amount) {
        vaultHook.deposit(target, amount);
    }

    public static void withdraw(OfflinePlayer target, double amount) {
        vaultHook.withdraw(target, amount);
    }
}