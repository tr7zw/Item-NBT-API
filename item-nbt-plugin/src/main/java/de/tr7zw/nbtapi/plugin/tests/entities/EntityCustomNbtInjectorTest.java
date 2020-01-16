package de.tr7zw.nbtapi.plugin.tests.entities;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.tests.Test;
import de.tr7zw.nbtinjector.NBTInjector;

public class EntityCustomNbtInjectorTest implements Test {

	@Override
	public void test() throws Exception {
		if(!NBTInjector.isInjected())return;
		if (!Bukkit.getWorlds().isEmpty()) {
			World world = Bukkit.getWorlds().get(0);
			try {
				if (!world.getEntitiesByClasses(Animals.class, Monster.class).isEmpty()) {
					Entity ent = world.getEntitiesByClasses(Animals.class, Monster.class).iterator().next();
					ent = NBTInjector.patchEntity(ent);
					NBTCompound comp = NBTInjector.getNbtData(ent);
					comp.setString("Hello", "World");
					NBTEntity nbtent = new NBTEntity(ent);
					if (!nbtent.toString().contains("__extraData:{Hello:\"World\"}")) {
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
