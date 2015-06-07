package de.tr7zw.itemnbtapi;

import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class Itemnbtapi extends JavaPlugin{

	private static Boolean works = true;
	
	@Override
	public void onEnable(){
		initmcstats();
		System.out.println("Checking if reflections are working!");
		try{
		ItemStack item = new ItemStack(Material.STONE, 1);
		NBTItem nbti = new NBTItem(item);
		nbti.setString("Stringtest", "Teststring");
		nbti.setInteger("Inttest", 42);
		nbti.setDouble("Doubletest", 1.5d);
		nbti.setBoolean("Booleantest", true);
		nbti.getItem();
		if(!nbti.getString("Stringtest").equals("Teststring")){
			works = false;
			System.out.println("Exeption while testing! Assuming reflections are broken! Check for a plugin update!");
			onDisable();
			return;
		}
		if(nbti.getInteger("Inttest") != 42){
			works = false;
			System.out.println("Exeption while testing! Assuming reflections are broken! Check for a plugin update!");
			onDisable();
			return;
		}
		if(nbti.getDouble("Doubletest") != 1.5d){
			works = false;
			System.out.println("Exeption while testing! Assuming reflections are broken! Check for a plugin update!");
			onDisable();
			return;
		}
		if(!nbti.getBoolean("Booleantest")){
			works = false;
			System.out.println("Exeption while testing! Assuming reflections are broken! Check for a plugin update!");
			onDisable();
			return;
		}
		}catch(Exception ex){
			works = false;
			System.out.println("Exeption while testing! Assuming reflections are broken! Check for a plugin update!");
			onDisable();
			return;
		}
		System.out.println("Everything seems to work!");
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public static NBTItem getNBTItem(ItemStack item){
		if(!ispluginisworking())
			return null;
		return new NBTItem(item);
	}
	
	public static Boolean ispluginisworking(){
		return works;
	}
	
	private void initmcstats(){
	    try {
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	    } catch (IOException e) {
	        
	    }
	}
	
}
