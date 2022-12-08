package de.tr7zw.changeme.nbtapi.iface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.tr7zw.changeme.nbtapi.NBTType;

public interface ReadableNBTList<T> extends Iterable<T> {

    T get(int id);

    int size();

    /**
     * @return The type that this list contains
     */
    NBTType getType();

    boolean isEmpty();

    boolean contains(Object o);

    int indexOf(Object o);

    boolean containsAll(Collection<?> c);

    int lastIndexOf(Object o);

    Object[] toArray();

    <E> E[] toArray(E[] a);

    List<T> subList(int fromIndex, int toIndex);

    default List<T> toListCopy() {
        List<T> list = new ArrayList<>();
        iterator().forEachRemaining(list::add);
        return list;
    }

}