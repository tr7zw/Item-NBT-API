package dev.tr7zw.nbtapi;

/**
 * Integer implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
public abstract class NBTIntegerList extends NBTList<Integer> {

    @Override
    public NBTType getType() {
        return NBTType.NBTTagInt;
    }

}
