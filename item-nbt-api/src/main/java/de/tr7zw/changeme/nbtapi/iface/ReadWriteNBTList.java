package de.tr7zw.changeme.nbtapi.iface;

import java.util.Collection;
import java.util.ListIterator;
import java.util.function.Predicate;

public interface ReadWriteNBTList<T> extends ReadableNBTList<T> {

    /**
     * Adds the specified element to this set if it is not already present.
     * 
     * @param element The element to be added to the list.
     * @return A boolean value.
     */
    boolean add(T element);

    /**
     * Adds the specified element at the specified position in this list.
     * 
     * @param index   The index at which the specified element is to be inserted
     * @param element The element to be added to the list.
     */
    void add(int index, T element);

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * 
     * @param index   The index of the element to replace
     * @param element The element to be stored at the specified position
     * @return The element that was previously at the specified position.
     */
    T set(int index, T element);

    /**
     * Remove the element at index i and return it.
     * 
     * @param i the index of the element to be removed
     * @return The element that was removed.
     */
    T remove(int i);

    /**
     * Clears the contents of the list
     */
    void clear();

    /**
     * Adds all of the elements in the specified collection to this collection.
     * 
     * @param c The collection to be added to the list.
     * @return A boolean value.
     */
    boolean addAll(Collection<? extends T> c);

    /**
     * Inserts all of the elements in the specified collection into this list,
     * starting at the specified position.
     * 
     * @param index The index at which the elements of the specified collection are
     *              to be inserted.
     * @param c     The collection to be added to the list.
     * @return A boolean value.
     */
    boolean addAll(int index, Collection<? extends T> c);

    /**
     * Removes from this collection all of its elements that are contained in the
     * specified collection
     * 
     * @param c The collection to be removed from this list.
     * @return A boolean value.
     */
    boolean removeAll(Collection<?> c);

    /**
     * Removes all of this collection's elements that are not contained in the
     * specified collection.
     * 
     * @param c The collection to be retained in this list
     * @return A boolean value.
     */
    boolean retainAll(Collection<?> c);

    /**
     * "Remove all elements from the list that match the given predicate."
     * 
     * The Predicate interface is a functional interface that takes a single
     * argument and returns a boolean
     * 
     * @param pred The predicate to apply to each element to determine if it should
     *             be removed.
     * @return A boolean value.
     */
    boolean removeIf(Predicate<? super T> pred);

    /**
     * Removes the first occurrence of the specified element from this list, if it
     * is present.
     * 
     * @param o The object to be removed from the list.
     * @return A boolean value.
     */
    boolean remove(Object o);

    /**
     * Returns a list iterator over the elements in this list (in proper sequence),
     * starting at the specified position in the list
     * 
     * @param startIndex The index of the first element to be returned from the list
     *                   iterator (by a call to the next method).
     * @return A list iterator of the list starting at the specified index.
     */
    ListIterator<T> listIterator(int startIndex);

}