package dev.tr7zw.nbtapi;

/**
 * Float implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
public abstract class NBTFloatList extends NBTList<Float> {

    @Override
    public NBTType getType() {
        return NBTType.NBTTagFloat;
    }

}
