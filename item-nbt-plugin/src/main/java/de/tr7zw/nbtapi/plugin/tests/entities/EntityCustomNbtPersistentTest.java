package de.tr7zw.nbtapi.plugin.tests.entities;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.nbtapi.plugin.tests.Test;

public class EntityCustomNbtPersistentTest implements Test {

    @Override
    public void test() throws Exception {
        if (MinecraftVersion.getVersion().getVersionId() < MinecraftVersion.MC1_14_R1.getVersionId())
            return;
        if (!Bukkit.getWorlds().isEmpty()) {
            World world = Bukkit.getWorlds().get(0);
            try {
                if (!world.getEntitiesByClasses(Animals.class, Monster.class).isEmpty()) {
                    Entity ent = world.getEntitiesByClasses(Animals.class, Monster.class).iterator().next();
                    NBT.modifyPersistentData(ent, comp -> {
                        comp.setString("Hello", "World");
                    });
                    
                    NBT.modifyPersistentData(ent, comp -> {
                        if (!comp.toString().contains("Hello:\"World\"")) {
                            throw new NbtApiException("Custom Data did not save to the Entity!");
                        }
                        comp.removeKey("Hello");
                    });

                }
            } catch (Exception ex) {
                throw new NbtApiException("Wasn't able to use NBTEntities!", ex);
            }
        }
    }

}
