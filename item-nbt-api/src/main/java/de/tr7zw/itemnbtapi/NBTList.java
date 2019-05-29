package de.tr7zw.itemnbtapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
		if (!(type == NBTType.NBTTagString || type == NBTType.NBTTagCompound)) {
			System.err.println("List types != String/Compound are currently not implemented!");
		}
	}

	protected void save() {
		parent.set(listName, listObject);
	}

	public T remove(int i) {
		try {
			ReflectionMethod.LIST_REMOVE_KEY.run(listObject, i);
			save();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public int size() {
		try {
			return (int) ReflectionMethod.LIST_SIZE.run(listObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public NBTType getType() {
		return type;
	}
	
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public void clear() {
		while(!isEmpty()) {
			remove(0);
		}
	}
	
	@Override
	public boolean contains(Object o) {
		for(int i = 0; i < size(); i++) {
			if(o.equals(get(i)))
				return true;
		}
		return false;
	}
	
	@Override
	public int indexOf(Object o) {
		for(int i = 0; i < size(); i++) {
			if(o.equals(get(i)))
				return i;
		}
		return -1;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		int size = size();
		for(T ele : c) {
			add(ele);
		}
		return size != size();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		int size = size();
		for(T ele : c) {
			add(index++, ele);
		}
		return size != size();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for(Object ele : c) {
			if(!contains(ele))
				return false;
		}
		return true;
	}
	
	@Override
	public int lastIndexOf(Object o) {
		int index = -1;
		for(int i = 0; i < size(); i++) {
			if(o.equals(get(i)))
				index = i;
		}
		return index;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		int size = size();
		for(Object obj : c) {
			remove(obj);
		}
		return size != size();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		int size = size();
		for(Object obj : c) {
			for(int i = 0; i < size(); i++) {
				if(!obj.equals(get(i))) {
					remove(i--);
				}
			}
		}
		return size != size();
	}
	
	@Override
	public boolean remove(Object o) {
		int size = size();
		int id = -1;
		while((id = indexOf(o)) != -1) {
			remove(id);
		}
		return size != size();
	}
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			private int index = 0;
			
			@Override
			public boolean hasNext() {
				return size() > index + 1;
			}

			@Override
			public T next() {
				return get(++index);
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

			int index = startIndex;
			
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
				return index > 0;
			}

			@Override
			public T next() {
				return get(++index);
			}

			@Override
			public int nextIndex() {
				return index + 1;
			}

			@Override
			public T previous() {
				return get(--index);
			}

			@Override
			public int previousIndex() {
				return index - 1;
			}

			@Override
			public void remove() {
				list.remove(index);
			}

			@Override
			public void set(T e) {
				list.set(index, e);
			}
		};
	}


	@Override
	public Object[] toArray() {
		Object[] ar = new Object[size()];
		for(int i = 0; i < size(); i++)
			ar[i] = get(i);
		return ar;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E[] toArray(E[] a) {
		E[] ar = Arrays.copyOf(a, size());
		Arrays.fill(ar, null);
		Class<?> arrayclass = a.getClass().getComponentType();
		for(int i = 0; i < size(); i++) {
			T obj = get(i);
			if(arrayclass.isInstance(obj)) {
				ar[i] = (E) get(i);
			}else {
				throw new ArrayStoreException("The array does not match the objects stored in the List.");
			}
		}
		return ar;
	}
	
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		ArrayList<T> list = new ArrayList<>();
		for(int i = fromIndex; i < toIndex; i++)
			list.add(get(i));
		return list;
	}
	
}
