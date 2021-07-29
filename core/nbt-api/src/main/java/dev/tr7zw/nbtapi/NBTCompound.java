package dev.tr7zw.nbtapi;

public interface NBTCompound extends NBTCompoundAccessor, Writeable<NBTCompound> {

    /**
     * Gets an existing compound, or creates it if it
     * doesn't exist yet.
     * 
     * @param name The key of the compound.
     */
    NBTCompound getOrCreateCompound(String name);
    
}
