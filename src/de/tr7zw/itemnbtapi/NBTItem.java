package de.tr7zw.itemnbtapi;

import org.bukkit.inventory.ItemStack;

public class NBTItem {

	private ItemStack bukkititem;
	
	public NBTItem(ItemStack Item){
		bukkititem = Item.clone();
	}
	
	public ItemStack getItem(){
		return bukkititem;
	}
	
	public void setString(String Key, String Text){
		bukkititem = NBTReflectionutil.setString(bukkititem, Key, Text);
	}
	
	public String getString(String Key){
		return NBTReflectionutil.getString(bukkititem, Key);
	}
	
	public void setInteger(String key, Integer Int){
		bukkititem = NBTReflectionutil.setInt(bukkititem, key, Int);
	}
	
	public Integer getInteger(String key){
		return NBTReflectionutil.getInt(bukkititem, key);
	}
	
	public void setDouble(String key, Double d){
		bukkititem = NBTReflectionutil.setDouble(bukkititem, key, d);
	}
	
	public Double getDouble(String key){
		return NBTReflectionutil.getDouble(bukkititem, key);
	}
	
	public void setBoolean(String key, Boolean b){
		bukkititem = NBTReflectionutil.setBoolean(bukkititem, key, b);
	}
	
	public Boolean getBoolean(String key){
		return NBTReflectionutil.getBoolean(bukkititem, key);
	}
	
}
