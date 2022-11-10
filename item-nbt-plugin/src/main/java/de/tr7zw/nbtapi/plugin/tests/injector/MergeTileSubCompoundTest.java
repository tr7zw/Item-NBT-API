package de.tr7zw.nbtapi.plugin.tests.injector;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;
import de.tr7zw.nbtinjector.NBTInjector;

public class MergeTileSubCompoundTest implements Test {

    @Override
    public void test() throws Exception {
        if (!NBTInjector.isInjected())
            return;
        if (!Bukkit.getWorlds().isEmpty()) {
            World world = Bukkit.getWorlds().get(0);
            try {
                boolean failed = false;
                Block block = world.getBlockAt(world.getSpawnLocation().getBlockX(), 255,
                        world.getSpawnLocation().getBlockZ());
                if (block.getType() == Material.AIR) {
                    block.setType(Material.CHEST);
                    NBTCompound comp = NBTInjector.getNbtData(block.getState());
                    comp.addCompound("subcomp").setString("hello", "world");
                    NBTContainer cont = new NBTContainer();
                    cont.mergeCompound(comp.getCompound("subcomp"));
                    if (!(cont.hasTag("hello") && "world".equals(cont.getString("hello")))) {
                        failed = true;
                    }
                    block.setType(Material.AIR);
                    if (failed) {
                        throw new NbtApiException("Data was not correct! " + cont);
                    }
                }
            } catch (Exception ex) {
                throw new NbtApiException("Wasn't able to use NBTTiles!", ex);
            }
        }
    }

}
