package com.pangdang.pxcheck.exception;

public class UnsupportedBukkitVersionException extends RuntimeException {

    public UnsupportedBukkitVersionException(String version) {
        super("The Bukkit version " + version + " is not supported by this plugin.");
    }
}
