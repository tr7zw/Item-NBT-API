package de.tr7zw.itemnbtapi;

public class NBTAPI {

    private static boolean log;
    public static void setLogging(boolean newLogging) {
        log = newLogging;
    }

    public static void log(String message) {
        if(log) System.out.println("[NBT-API] " + message);
    }
}
