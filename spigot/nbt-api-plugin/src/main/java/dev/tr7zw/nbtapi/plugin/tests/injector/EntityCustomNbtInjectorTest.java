package dev.tr7zw.nbtapi.plugin.tests.injector;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtinjector.NBTInjector;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.Writeable;
import dev.tr7zw.nbtapi.plugin.tests.Test;

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
					Writeable comp = NBTInjector.getNbtData(ent);
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
