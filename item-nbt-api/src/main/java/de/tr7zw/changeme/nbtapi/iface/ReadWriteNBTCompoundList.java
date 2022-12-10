package de.tr7zw.changeme.nbtapi.iface;

import java.util.function.Predicate;

public interface ReadWriteNBTCompoundList extends ReadableNBTList<ReadWriteNBT> {

    /**
     * Adds a new compound tag to the current compound tag
     * 
     * @return A new instance of the class.
     */
    ReadWriteNBT addCompound();

    /**
     * Removes the element at the specified position in this list
     * 
     * @param i The index of the element to remove.
     * @return A new instance of the class.
     */
    ReadWriteNBT remove(int i);

    /**
     * Clears the contents of the list
     */
    void clear();

    /**
     * Removes all elements of this list that satisfy the given predicate
     * 
     * @param pred The predicate to use to test elements.
     * @return A boolean value.
     */
    boolean removeIf(Predicate<? super ReadWriteNBT> pred);

}