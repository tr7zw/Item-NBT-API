package de.tr7zw.nbtapi.plugin.tests.entities;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class EntityTest implements Test {

    @Override
    public void test() throws Exception {
        if (!Bukkit.getWorlds().isEmpty()) {
            World world = Bukkit.getWorlds().get(0);
            try {
                if (!world.getEntitiesByClasses(Animals.class, Monster.class).isEmpty()) {
                    NBTEntity nbte = new NBTEntity(
                            world.getEntitiesByClasses(Animals.class, Monster.class).iterator().next());
                    nbte.setString("INVALIDEKEY", "test");
                }
            } catch (Exception ex) {
                throw new NbtApiException("Wasn't able to use NBTEntities!", ex);
            }
        }
    }

}
