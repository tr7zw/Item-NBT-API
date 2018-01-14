package de.tr7zw.itemnbtapi.utils;

public class MethodNames {

    private final static MinecraftVersion MINECRAFT_VERSION = MinecraftVersion.getVersion();

    public static String getTileDataGetterMethodName() {
        if (MINECRAFT_VERSION == MinecraftVersion.MC1_8_R3) {
            return "b";
        }
        return "save";
    }
    
    public static String getTileDataSetterMethodName() {
        if (MINECRAFT_VERSION != MinecraftVersion.MC1_12_R1) {
            return "a";
        }
        return "load";
    }

    public static String getTypeMethodName() {
        if (MINECRAFT_VERSION == MinecraftVersion.MC1_8_R3) {
            return "b";
        }
        return "d";
    }

    public static String getEntityNbtGetterMethodName() {
        return "b";
    }

    public static String getEntityNbtSetterMethodName() {
        return "a";
    }

    public static String getRemoveMethodName() {
        if (MINECRAFT_VERSION == MinecraftVersion.MC1_8_R3) {
            return "a";
        }
        return "remove";
    }

}
