package de.tr7zw.changeme.nbtapi.wrapper;

public interface ProxyList<T extends NBTProxy> extends Iterable<T> {

    /**
     * Adds a new compound tag to the current compound tag
     * 
     * @return A new instance of the class.
     */
    T addCompound();

    /**
     * Returns the number of elements in this collection. If this collection
     * contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     *
     * @return the number of elements in this collection
     */
    int size();

    /**
     * Returns true if this collection contains no elements.
     *
     * @return true if this collection contains no elements
     */
    boolean isEmpty();

    /**
     * Get the object with the given id.
     * 
     * @param id The id of the object to get.
     * @return The object with the given id.
     */
    T get(int id);

    /**
     * Removes the element at the specified position in this list
     * 
     * @param i The index of the element to remove.
     */
    void remove(int i);

}
