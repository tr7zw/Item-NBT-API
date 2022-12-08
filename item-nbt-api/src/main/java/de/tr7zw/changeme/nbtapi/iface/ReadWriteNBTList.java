package de.tr7zw.changeme.nbtapi.iface;

import java.util.Collection;
import java.util.ListIterator;
import java.util.function.Predicate;

public interface ReadWriteNBTList<T> extends ReadableNBTList<T> {

    boolean add(T element);

    void add(int index, T element);

    T set(int index, T element);

    T remove(int i);

    void clear();

    boolean addAll(Collection<? extends T> c);

    boolean addAll(int index, Collection<? extends T> c);

    boolean removeAll(Collection<?> c);

    boolean retainAll(Collection<?> c);

    default void removeIf(Predicate<T> pred) {
        ListIterator<T> it = listIterator(0);
        while (it.hasNext()) {
            if (pred.test(it.next())) {
                it.remove();
            }
        }
    }

    boolean remove(Object o);

    ListIterator<T> listIterator(int startIndex);

}