package de.tr7zw.itemnbtapi.utils;

import de.tr7zw.itemnbtapi.Logger;
import org.bukkit.Bukkit;

public enum MinecraftVersion {
    Unknown(Integer.MAX_VALUE),//Use the newest known mappings
    MC1_7_R4(174),
    MC1_8_R3(183),
    MC1_9_R1(191),
    MC1_9_R2(192),
    MC1_10_R1(1101),
    MC1_11_R1(1111),
    MC1_12_R1(1121),
    MC1_13_R1(1131),
    MC1_13_R2(1132),
    MC1_14_R1(1141);

    private static MinecraftVersion version;
    private static Boolean hasGsonSupport;

    private final int versionId;

    MinecraftVersion(int versionId) {
        this.versionId = versionId;
    }

    public int getVersionId() {
        return versionId;
    }

    public static MinecraftVersion getVersion() {
        if (version != null) {
            return version;
        }
        final String ver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        Logger.log("Found Spigot: " + ver + "! Trying to find NMS support");
        try {
            version = MinecraftVersion.valueOf(ver.replace("v", "MC"));
        } catch (IllegalArgumentException ex) {
            version = MinecraftVersion.Unknown;
        }
        if (version != Unknown) {
            Logger.log("NMS support '" + version.name() + "' loaded!");
        } else {
            Logger.log("Wasn't able to find NMS Support! Some functions may not work!");
        }
        return version;
    }

    public static boolean hasGsonSupport() {
        if (hasGsonSupport != null) {
            return hasGsonSupport;
        }
        try {
            Logger.log("Found Gson: " + Class.forName("com.google.gson.Gson"));
            hasGsonSupport = true;
        } catch (Exception ex) {
            hasGsonSupport = false;
        }
        return hasGsonSupport;
    }

}
