package de.tr7zw.itemnbtapi;

import java.lang.reflect.Method;

public class NBTList{

	private String listname;
	private NBTCompound parent;
	private NBTType type;
	private Object listobject;

	protected NBTList(NBTCompound owner, String name, NBTType type, Object list){
		parent = owner;
		listname = name;
		this.type = type;
		this.listobject = list;
		if(type != NBTType.NBTTagString){
			System.err.println("List types != String are currently not implemented!");
		}
	}

	private void save(){
		parent.set(listname, listobject);
	}

	public String get(int i){
		try{
			Method m = listobject.getClass().getMethod("getString", int.class);
			return (String) m.invoke(listobject, i);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void addString(String s){
		try{
			Method m = listobject.getClass().getMethod("add", NBTReflectionUtil.getNBTBase());
			m.invoke(listobject, NBTReflectionUtil.getNBTTagString().getConstructor(String.class).newInstance(s));
			save();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setString(int i, String s){
		try{
			Method m = listobject.getClass().getMethod("a", int.class, NBTReflectionUtil.getNBTBase());
			m.invoke(listobject, i, NBTReflectionUtil.getNBTTagString().getConstructor(String.class).newInstance(s));
			save();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void remove(int i){
		try{
			Method m = listobject.getClass().getMethod("remove", int.class);
			m.invoke(listobject, i);
			save();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public int size(){
		try{
			Method m = listobject.getClass().getMethod("size");
			return (int) m.invoke(listobject);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return -1;
	}
	
}
