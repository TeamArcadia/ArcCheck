package com.pangdang.pxcheck.config;

import com.pangdang.pxcheck.PXCheck;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private String checkItemType;
    private Map<String, String> checkItemDataMap = new HashMap<>();
    private Map<String, String> soundDataMap = new HashMap<>();

    public void initialize() {
        checkItemDataMap.clear();
        soundDataMap.clear();

        FileConfiguration config = PXCheck.getInstance().getConfig();
        ConfigurationSection soundSec = config.getConfigurationSection("sound");

        for (String key : soundSec.getKeys(false)) {
            soundDataMap.put(key, soundSec.getString(key));
        }

        checkItemType = config.getString("check-item.type");
        checkItemDataMap.put("name", config.getString("check-item.name"));
        checkItemDataMap.put("lore", config.getString("check-item.lore"));
        checkItemDataMap.put("custom_model_data", config.getString("check-item.custom_model_data"));
    }

    public String getCheckItemType() {
        return checkItemType;
    }

    public String getCheckItemInfo(String key) {
        return ChatColor.translateAlternateColorCodes('&', checkItemDataMap.get(key));
    }

    public Sound getSound(String soundKey) {
        String soundString = soundDataMap.get(soundKey);
        if (soundString == null) {
            return null;
        }
        return Sound.valueOf(soundString);
    }

}