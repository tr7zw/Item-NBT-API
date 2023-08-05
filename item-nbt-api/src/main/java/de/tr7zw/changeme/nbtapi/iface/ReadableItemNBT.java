package de.tr7zw.changeme.nbtapi.iface;

public interface ReadableItemNBT extends ReadableNBT {

    /**
     * Returns true if the item has NBT data.
     * 
     * @return Does the ItemStack have a NBTCompound.
     */
    public boolean hasNBTData();

}
