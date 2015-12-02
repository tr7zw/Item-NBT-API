package de.tr7zw.itemnbtapi;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class ItemNBTAPI extends JavaPlugin {
    
    private static boolean compatible = false;
    
    @Override
    public void onEnable() {
        initMetrics();
        getLogger().info("Running NBT reflection test...");
        try {
            ItemStack item = new ItemStack(Material.STONE, 1);
            NBTItem nbtItem = new NBTItem(item);
            
            nbtItem.setString("stringTest", "TestString");
            nbtItem.setInteger("intTest", 42);
            nbtItem.setDouble("doubleTest", 1.5d);
            nbtItem.setBoolean("booleanTest", true);
            
            nbtItem.getItem();
            
            if (!nbtItem.hasKey("stringTest")) {
                getLogger().info("Does not have key...");
                return;
            }
            if (!nbtItem.getString("stringTest").equals("TestString") 
                    || nbtItem.getInteger("intTest") != 42
                    || nbtItem.getDouble("doubleTest") != 1.5d
                    || !nbtItem.getBoolean("booleanTest")) {
                getLogger().info("Key does not equal original value...");
                
                return;
            }
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, null, ex);
            return;
        }
        
        compatible = true;
        getLogger().info("Success! This version of Item-NBT-API is compatible with your server.");
    }

    @Override
    public void onDisable() {
    }

    public boolean isCompatible() {
        return compatible;
    }
    
    private void initMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, null, ex);
        }
    }

}
