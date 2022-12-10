package de.tr7zw.changeme.nbtapi.iface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.tr7zw.changeme.nbtapi.NBTType;

public interface ReadableNBTList<T> extends Iterable<T> {

    /**
     * Get the object with the given id.
     * 
     * @param id The id of the object to get.
     * @return The object with the given id.
     */
    T get(int id);

    /**
     * Returns the number of elements in this list.
     * 
     * @return The size of the list.
     */
    int size();

    /**
     * Returns the type of this tag
     * 
     * @return The type of the tag.
     */
    NBTType getType();

    /**
     * Returns true if the list is empty, false otherwise.
     * 
     * @return A boolean value.
     */
    boolean isEmpty();

    /**
     * Returns true if this list contains the specified element.
     * 
     * @param o The object to be searched for in the list.
     * @return A boolean value.
     */
    boolean contains(Object o);

    /**
     * Returns the index of the first occurrence of the specified element in this
     * list, or -1 if this list does not contain the element.
     * 
     * @param o The object to search for.
     * @return The index of the first occurrence of the specified element in this
     *         list, or -1 if this list does not contain the element.
     */
    int indexOf(Object o);

    /**
     * Returns true if this collection contains all of the elements in the specified
     * collection
     * 
     * @param c The collection to be checked for containment in this list
     * @return A boolean value.
     */
    boolean containsAll(Collection<?> c);

    /**
     * Returns the index of the last occurrence of the specified element in this
     * list, or -1 if this list does not contain the element
     * 
     * @param o The object to search for.
     * @return The index of the last occurrence of the specified element in this
     *         list, or -1 if this list does not contain the element.
     */
    int lastIndexOf(Object o);

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element).
     * 
     * @return An array of objects.
     */
    Object[] toArray();

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element).
     * 
     * @param a The array into which the elements of the list are to be stored, if
     *          it is big enough; otherwise, a new array of the same runtime type is
     *          allocated for this purpose.
     * @return An array of the elements in the list.
     */
    <E> E[] toArray(E[] a);

    /**
     * Returns a view of the portion of this list between the specified fromIndex,
     * inclusive, and toIndex, exclusive
     * 
     * @param fromIndex The starting index of the sublist (inclusive).
     * @param toIndex   The index of the last element in the sublist.
     * @return A view of the specified range within this list.
     */
    List<T> subList(int fromIndex, int toIndex);

    /**
     * Create a new list containing all entries of this list.
     * 
     * @return A list of the elements in the stream.
     */
    default List<T> toListCopy() {
        List<T> list = new ArrayList<>();
        iterator().forEachRemaining(list::add);
        return list;
    }

}