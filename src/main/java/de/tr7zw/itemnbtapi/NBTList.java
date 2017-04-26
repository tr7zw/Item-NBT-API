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
		if(!(type == NBTType.NBTTagString || type == NBTType.NBTTagCompound)){
			System.err.println("List types != String/Compound are currently not implemented!");
		}
	}

	protected void save(){
		parent.set(listname, listobject);
	}
	
	public NBTListCompound addCompound(){
	       if(type != NBTType.NBTTagCompound){
	            new Throwable("Using Compound method on a non Compound list!").printStackTrace();
	            return null;
	        }
	        try{
	            Method m = listobject.getClass().getMethod("add", NBTReflectionUtil.getNBTBase());
	            Object comp = NBTReflectionUtil.getNBTTagCompound().newInstance();
	            m.invoke(listobject, comp);
	            return new NBTListCompound(this, comp);
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	        return null;
	}
	
	public NBTListCompound getCompound(int id){
	    if(type != NBTType.NBTTagCompound){
            new Throwable("Using Compound method on a non Compound list!").printStackTrace();
            return null;
        }
        try{
            Method m = listobject.getClass().getMethod("get", int.class);
            Object comp = m.invoke(listobject, id);
            return new NBTListCompound(this, comp);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
	}

	public String getString(int i){
		if(type != NBTType.NBTTagString){
		    new Throwable("Using String method on a non String list!").printStackTrace();
		    return null;
		}
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
	       if(type != NBTType.NBTTagString){
	            new Throwable("Using String method on a non String list!").printStackTrace();
	            return;
	        }
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
	       if(type != NBTType.NBTTagString){
	            new Throwable("Using String method on a non String list!").printStackTrace();
	            return;
	        }
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
	
	public NBTType getType(){
	    return type;
	}
	
}
