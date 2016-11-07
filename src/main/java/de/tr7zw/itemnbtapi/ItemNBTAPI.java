package de.tr7zw.itemnbtapi;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class ItemNBTAPI extends JavaPlugin{

    private static boolean compatible = true;

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

            item = nbtItem.getItem();

            if (!nbtItem.hasKey("stringTest")) {
                getLogger().warning("Wasn't able to check a key! The Item-NBT-API may not work!");
                compatible = false;
            }
            if (!("TestString").equals(nbtItem.getString("stringTest"))
                    || nbtItem.getInteger("intTest") != 42
                    || nbtItem.getDouble("doubleTest") != 1.5d
                    || !nbtItem.getBoolean("booleanTest")) {
                getLogger().warning("One key does not equal the original value! The Item-NBT-API may not work!");
                compatible = false;
            }
            nbtItem.setString("stringTest", null);
            if(nbtItem.getKeys().size() != 3){
                getLogger().warning("Wasn't able to remove a key (Got " + nbtItem.getKeys().size() + " when expecting 3)! The Item-NBT-API may not work!");
                compatible = false;
            }
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, null, ex);
            compatible = false;
        }
        if(compatible){
        getLogger().info("Success! This version of Item-NBT-API is compatible with your server.");
        }else{
            getLogger().warning("WARNING! This version of Item-NBT-API seems to be broken with your Spigot version! Plugins that don't check properly, may throw Exeptions, crash or have unexpected behaviors!");
        }
    }

    @Override
    public void onDisable() {
    }

    public boolean isCompatible() {
        return compatible;
    }
    
    public static NBTItem getNBTItem(ItemStack item){
        return new NBTItem(item);
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
