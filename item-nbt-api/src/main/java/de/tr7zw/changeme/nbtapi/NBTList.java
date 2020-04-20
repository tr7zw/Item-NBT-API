package de.tr7zw.changeme.nbtapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * Abstract List implementation for ListCompounds
 * 
 * @author tr7zw
 *
 * @param <T>
 */
public abstract class NBTList<T> implements List<T> {

	private String listName;
	private NBTCompound parent;
	private NBTType type;
	protected Object listObject;

	protected NBTList(NBTCompound owner, String name, NBTType type, Object list) {
		parent = owner;
		listName = name;
		this.type = type;
		this.listObject = list;
	}

	/**
	 * @return Name of this list-compound
	 */
	public String getName() {
		return listName;
	}

	/**
	 * @return The Compound's parent Object
	 */
	public NBTCompound getParent() {
		return parent;
	}

	protected void save() {
		parent.set(listName, listObject);
	}

	protected abstract Object asTag(T object);

	@Override
	public boolean add(T element) {
		try {
			parent.getWriteLock().lock();
			if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
				ReflectionMethod.LIST_ADD.run(listObject, size(), asTag(element));
			} else {
				ReflectionMethod.LEGACY_LIST_ADD.run(listObject, asTag(element));
			}
			save();
			return true;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		} finally {
			parent.getWriteLock().unlock();
		}
	}

	@Override
	public void add(int index, T element) {
		try {
			parent.getWriteLock().lock();
			if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
				ReflectionMethod.LIST_ADD.run(listObject, index, asTag(element));
			} else {
				ReflectionMethod.LEGACY_LIST_ADD.run(listObject, asTag(element));
			}
			save();
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		} finally {
			parent.getWriteLock().unlock();
		}
	}

	@Override
	public T set(int index, T element) {
		try {
			parent.getWriteLock().lock();
			T prev = get(index);
			ReflectionMethod.LIST_SET.run(listObject, index, asTag(element));
			save();
			return prev;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		} finally {
			parent.getWriteLock().unlock();
		}
	}

	public T remove(int i) {
		try {
			parent.getWriteLock().lock();
			T old = get(i);
			ReflectionMethod.LIST_REMOVE_KEY.run(listObject, i);
			save();
			return old;
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		} finally {
			parent.getWriteLock().unlock();
		}
	}

	public int size() {
		try {
			parent.getReadLock().lock();
			return (int) ReflectionMethod.LIST_SIZE.run(listObject);
		} catch (Exception ex) {
			throw new NbtApiException(ex);
		} finally {
			parent.getReadLock().unlock();
		}
	}

	/**
	 * @return The type that this list contains
	 */
	public NBTType getType() {
		return type;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public void clear() {
		while (!isEmpty()) {
			remove(0);
		}
	}

	@Override
	public boolean contains(Object o) {
		try {
			parent.getReadLock().lock();
			for (int i = 0; i < size(); i++) {
				if (o.equals(get(i)))
					return true;
			}
			return false;
		} finally {
			parent.getReadLock().unlock();
		}
	}

	@Override
	public int indexOf(Object o) {
		try {
			parent.getReadLock().lock();
			for (int i = 0; i < size(); i++) {
				if (o.equals(get(i)))
					return i;
			}
			return -1;
		} finally {
			parent.getReadLock().unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		try {
			parent.getWriteLock().lock();
			int size = size();
			for (T ele : c) {
				add(ele);
			}
			return size != size();
		} finally {
			parent.getWriteLock().unlock();
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		try {
			parent.getWriteLock().lock();
			int size = size();
			for (T ele : c) {
				add(index++, ele);
			}
			return size != size();
		} finally {
			parent.getWriteLock().unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		try {
			parent.getReadLock().lock();
			for (Object ele : c) {
				if (!contains(ele))
					return false;
			}
			return true;
		} finally {
			parent.getReadLock().unlock();
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		try {
			parent.getReadLock().lock();
			int index = -1;
			for (int i = 0; i < size(); i++) {
				if (o.equals(get(i)))
					index = i;
			}
			return index;
		} finally {
			parent.getReadLock().unlock();
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		try {
			parent.getWriteLock().lock();
			int size = size();
			for (Object obj : c) {
				remove(obj);
			}
			return size != size();
		} finally {
			parent.getWriteLock().unlock();
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		try {
			parent.getWriteLock().lock();
			int size = size();
			for (Object obj : c) {
				for (int i = 0; i < size(); i++) {
					if (!obj.equals(get(i))) {
						remove(i--);
					}
				}
			}
			return size != size();
		} finally {
			parent.getWriteLock().unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		try {
			parent.getWriteLock().lock();
			int size = size();
			int id = -1;
			while ((id = indexOf(o)) != -1) {
				remove(id);
			}
			return size != size();
		} finally {
			parent.getWriteLock().unlock();
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			private int index = -1;

			@Override
			public boolean hasNext() {
				return size() > index + 1;
			}

			@Override
			public T next() {
				if (!hasNext())
					throw new NoSuchElementException();
				return get(++index);
			}

			@Override
			public void remove() {
				NBTList.this.remove(index);
				index--;
			}
		};
	}

	@Override
	public ListIterator<T> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<T> listIterator(int startIndex) {
		final NBTList<T> list = this;
		return new ListIterator<T>() {

			int index = startIndex - 1;

			@Override
			public void add(T e) {
				list.add(index, e);
			}

			@Override
			public boolean hasNext() {
				return size() > index + 1;
			}

			@Override
			public boolean hasPrevious() {
				return index >= 0 && index <= size();
			}

			@Override
			public T next() {
				if (!hasNext())
					throw new NoSuchElementException();
				return get(++index);
			}

			@Override
			public int nextIndex() {
				return index + 1;
			}

			@Override
			public T previous() {
				if (!hasPrevious())
					throw new NoSuchElementException("Id: " + (index - 1));
				return get(index--);
			}

			@Override
			public int previousIndex() {
				return index - 1;
			}

			@Override
			public void remove() {
				list.remove(index);
				index--;
			}

			@Override
			public void set(T e) {
				list.set(index, e);
			}
		};
	}

	@Override
	public Object[] toArray() {
		try {
			parent.getReadLock().lock();
			Object[] ar = new Object[size()];
			for (int i = 0; i < size(); i++)
				ar[i] = get(i);
			return ar;
		} finally {
			parent.getReadLock().unlock();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E[] toArray(E[] a) {
		try {
			parent.getReadLock().lock();
			E[] ar = Arrays.copyOf(a, size());
			Arrays.fill(ar, null);
			Class<?> arrayclass = a.getClass().getComponentType();
			for (int i = 0; i < size(); i++) {
				T obj = get(i);
				if (arrayclass.isInstance(obj)) {
					ar[i] = (E) get(i);
				} else {
					throw new ArrayStoreException("The array does not match the objects stored in the List.");
				}
			}
			return ar;
		} finally {
			parent.getReadLock().unlock();
		}
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		try {
			parent.getReadLock().lock();
			ArrayList<T> list = new ArrayList<>();
			for (int i = fromIndex; i < toIndex; i++)
				list.add(get(i));
			return list;
		} finally {
			parent.getReadLock().unlock();
		}
	}

	@Override
	public String toString() {
		try {
			parent.getReadLock().lock();
			return listObject.toString();
		} finally {
			parent.getReadLock().unlock();
		}
	}

}
