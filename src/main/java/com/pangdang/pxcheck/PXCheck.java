package com.pangdang.pxcheck;

import com.pangdang.pxcheck.command.CheckCommand;
import com.pangdang.pxcheck.command.ManageCommand;
import com.pangdang.pxcheck.config.ConfigManager;
import com.pangdang.pxcheck.hook.VaultEconomyHook;
import com.pangdang.pxcheck.listener.ItemClickListener;
import com.pangdang.pxcheck.message.MessageContext;
import com.pangdang.pxcheck.version.VersionController;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class PXCheck extends JavaPlugin {

    @Getter private static PXCheck instance;
    @Getter private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;

        /* --------------- ITEM NBT ---------------*/
        VersionController.initialize(this);

        /* --------------- DEPENDENCY --------------- */
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            getServer().getLogger().warning(getName() + " - Vault 플러그인을 사용합니다.");
            VaultEconomyHook.setupEconomy();
        } else {
            getServer().getLogger().warning(getName() + " - Economy 플러그인이 없습니다. 플러그인을 비활성화합니다.");
            getServer().getPluginManager().disablePlugin(this);
        }

        /* --------------- CONFIG --------------- */
        saveDefaultConfig();

        MessageContext.getInstance().initialize(getConfig());

        configManager = new ConfigManager();
        configManager.initialize();

        /* --------------- COMMAND --------------- */
        getCommand("수표").setExecutor(new CheckCommand());
        new ManageCommand(this);

        /* --------------- LISTENER --------------- */
        getServer().getPluginManager().registerEvents(new ItemClickListener(), this);
    }
}