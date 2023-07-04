package de.tr7zw.changeme.nbtapi.utils;

import de.tr7zw.changeme.nbtapi.NbtApiException;

public class CheckUtil {

    private CheckUtil() {
        // util
    }

    public static void assertAvailable(MinecraftVersion version) {
        if (!MinecraftVersion.isAtLeastVersion(version))
            throw new NbtApiException(
                    "This Method is only avaliable for the version " + version.name() + " and above!");
    }

}
