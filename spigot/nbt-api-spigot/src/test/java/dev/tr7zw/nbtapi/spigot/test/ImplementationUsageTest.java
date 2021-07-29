package dev.tr7zw.nbtapi.spigot.test;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import dev.tr7zw.nbtapi.spigot.impl.NBTItemStack;

import static dev.tr7zw.nbtapi.spigot.NBTApi.nbtApi;

public class ImplementationUsageTest implements Listener {

    @EventHandler
    public void onDrop(ItemSpawnEvent event) {
        nbtApi.modifyItemStack(event.getEntity().getItemStack(), nbt -> {
            if(!nbt.hasKey("firstDropTime")) {
                nbt.setLong("firstDropTime", System.currentTimeMillis());
            }
        });
        int dmg = nbtApi.parseItemStack(event.getEntity().getItemStack(), nbt -> nbt.getInteger("Damage"));
        
        NBTItemStack nbti = nbtApi.getItemStack(event.getEntity().getItemStack());
        nbti.setString("foo", "bar");
        ItemStack modified = nbti.getItemStack();
    }
    
}
