package com.pangdang.pxcheck.message;

import com.pangdang.pxcheck.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MessageContext {

    private static MessageContext instance;

    public static MessageContext getInstance() {
        if (instance == null) instance = new MessageContext();
        return instance;
    }

    private MessageContext() {}

    private final Map<Pair<MessageType, String>, String> map = new HashMap<>();

    public void initialize(FileConfiguration config) {
        map.clear();
        Arrays.stream(MessageType.values()).forEach(messageType -> {
            ConfigurationSection configurationSection = config.getConfigurationSection(messageType.getType());
            if (configurationSection != null) {
                configurationSection.getKeys(false).forEach(key -> set(messageType, key, configurationSection.getString(key)));
            }
        });
    }

    public PXMessage get(MessageType type, String key, String orElse) {
        return new PXMessage(getPrefix(), map.getOrDefault(new Pair<>(type, key), orElse));
    }

    public PXMessage get(MessageType type, String key) {
        return get(type, key, "");
    }

    public PXMessage get(MessageType type, String key, String orElse, Function<String, String> replacer) {
        return new PXMessage(getPrefix(), replacer.apply(get(type, key, orElse).getMessage()));
    }

    public PXMessage get(MessageType type, String key, Function<String, String> replacer) {
        return get(type, key, "", replacer);
    }

    public void set(MessageType type, String key, String value) {
        map.put(new Pair<>(type, key), ChatColor.translateAlternateColorCodes('&', value));
    }

    public String getOnlyString(MessageType type, String key) {
        return map.getOrDefault(new Pair<>(type, key), "");
    }

    public String getPrefix() {
        return map.getOrDefault(new Pair<>(MessageType.NORMAL, "prefix"), "");
    }
}