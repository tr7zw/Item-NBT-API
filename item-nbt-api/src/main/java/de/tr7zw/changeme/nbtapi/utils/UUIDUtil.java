package de.tr7zw.changeme.nbtapi.utils;

import java.util.UUID;

public class UUIDUtil {

    public static UUID uuidFromIntArray(int[] is) {
        return new UUID((long) is[0] << 32 | (long) is[1] & 4294967295L,
                (long) is[2] << 32 | (long) is[3] & 4294967295L);
    }

    public static int[] uuidToIntArray(UUID uUID) {
        long l = uUID.getMostSignificantBits();
        long m = uUID.getLeastSignificantBits();
        return leastMostToIntArray(l, m);
    }

    private static int[] leastMostToIntArray(long l, long m) {
        return new int[]{(int) (l >> 32), (int) l, (int) (m >> 32), (int) m};
    }
    
}
