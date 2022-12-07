package de.tr7zw.changeme.nbtapi.iface;

public interface ReadWriteNBTCompoundList extends ReadableNBTList<ReadWriteNBT> {

    ReadWriteNBT addCompound();

    ReadWriteNBT remove(int i);

    void clear();

}