package dev.tr7zw.nbtapi.plugin.tests.legacy.entities;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.utils.MinecraftVersion;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class EntityCustomNbtPersistentTest implements Test {

	@Override
	public void test() throws Exception {
		if(MinecraftVersion.getVersion().getVersionId() < MinecraftVersion.MC1_14_R1.getVersionId())return;
		if (!Bukkit.getWorlds().isEmpty()) {
			World world = Bukkit.getWorlds().get(0);
			try {
				if (!world.getEntitiesByClasses(Animals.class, Monster.class).isEmpty()) {
					Entity ent = world.getEntitiesByClasses(Animals.class, Monster.class).iterator().next();
					NBTEntity nbtEnt = new NBTEntity(ent);
					NBTCompound comp = nbtEnt.getPersistentDataContainer();
					comp.setString("Hello", "World");
					NBTEntity nbtent = new NBTEntity(ent);
					if (!nbtent.toString().contains("Hello:\"World\"")) {
						throw new NbtApiException("Custom Data did not save to the Entity!");
					}
					comp.removeKey("Hello");

				}
			} catch (Exception ex) {
				throw new NbtApiException("Wasn't able to use NBTEntities!", ex);
			}
		}
	}

}
