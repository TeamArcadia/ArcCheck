package com.pangdang.pxcheck.hook;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class VaultEconomyHook {

    private static Economy economy = null;

    public static void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);

        if (rsp != null)
            economy = rsp.getProvider();
    }

    public boolean hasEconomy() {
        return economy != null;
    }

    public double getBalance(OfflinePlayer target) {
        if (!hasEconomy())
            throw new UnsupportedOperationException("Vault Economy not found, call hasEconomy() to check it first.");

        return economy.getBalance(target);
    }

    public String withdraw(OfflinePlayer target, double amount) {
        if (!hasEconomy())
            throw new UnsupportedOperationException("Vault Economy not found, call hasEconomy() to check it first.");

        return economy.withdrawPlayer(target, amount).errorMessage;
    }

    public String deposit(OfflinePlayer target, double amount) {
        if (!hasEconomy())
            throw new UnsupportedOperationException("Vault Economy not found, call hasEconomy() to check it first.");

        return economy.depositPlayer(target, amount).errorMessage;
    }
}