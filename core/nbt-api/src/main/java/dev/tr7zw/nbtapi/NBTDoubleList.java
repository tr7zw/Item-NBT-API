package dev.tr7zw.nbtapi;

/**
 * Double implementation for NBTLists
 * 
 * @author tr7zw
 *
 */
public abstract class NBTDoubleList extends NBTList<Double> {

    @Override
    public NBTType getType() {
        return NBTType.NBTTagDouble;
    }

}
