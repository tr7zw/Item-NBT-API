package de.tr7zw.nbtapi;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.NotImplementedException;

/**
 * Abstract List implementation for ListCompounds
 * 
 * @author tr7zw
 *
 * @param <T>
 */
@Deprecated
public abstract class NBTList<T> implements List<T> {



	protected NBTList(NBTCompound owner, String name, NBTType type, Object list) {

	}

	/**
	 * @return Name of this list-compound
	 */
	public String getName() {
	    throw new NotImplementedException();
	}

	/**
	 * @return The Compound's parent Object
	 */
	public NBTCompound getParent() {
	    throw new NotImplementedException();
	}

	@Override
	public boolean add(T element) {
	    throw new NotImplementedException();
	}

	@Override
	public void add(int index, T element) {
	    throw new NotImplementedException();
	}

	@Override
	public T set(int index, T element) {
	    throw new NotImplementedException();
	}

	public T remove(int i) {
	    throw new NotImplementedException();
	}

	public int size() {
	    throw new NotImplementedException();
	}

	/**
	 * @return The type that this list contains
	 */
	public NBTType getType() {
	    throw new NotImplementedException();
	}

	@Override
	public boolean isEmpty() {
	    throw new NotImplementedException();
	}

	@Override
	public void clear() {
	    throw new NotImplementedException();
	}

	@Override
	public boolean contains(Object o) {
	    throw new NotImplementedException();
	}

	@Override
	public int indexOf(Object o) {
	    throw new NotImplementedException();
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
	    throw new NotImplementedException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
	    throw new NotImplementedException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
	    throw new NotImplementedException();
	}

	@Override
	public int lastIndexOf(Object o) {
	    throw new NotImplementedException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
	    throw new NotImplementedException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
	    throw new NotImplementedException();
	}

	@Override
	public boolean remove(Object o) {
	    throw new NotImplementedException();
	}

	@Override
	public Iterator<T> iterator() {
	    throw new NotImplementedException();
	}

	@Override
	public ListIterator<T> listIterator() {
	    throw new NotImplementedException();
	}

	@Override
	public ListIterator<T> listIterator(int startIndex) {
	    throw new NotImplementedException();
	}

	@Override
	public Object[] toArray() {
	    throw new NotImplementedException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E[] toArray(E[] a) {
	    throw new NotImplementedException();
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
	    throw new NotImplementedException();
	}

	@Override
	public String toString() {
	    throw new NotImplementedException();
	}

}
