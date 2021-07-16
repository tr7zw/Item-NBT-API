package dev.tr7zw.nbtapi.plugin.tests.legacy.injector;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtinjector.NBTInjector;
import dev.tr7zw.nbtapi.NBTApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class SpawnEntityCustomNbtInjectorTest implements Test {

	@Override
	public void test() throws Exception {
		if (!NBTInjector.isInjected())
			return;
		if (!Bukkit.getWorlds().isEmpty()) {
			World world = Bukkit.getWorlds().get(0);
			try {
				Entity entity = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ARMOR_STAND);
				entity = NBTInjector.patchEntity(entity);
				NBTCompound comp = NBTInjector.getNbtData(entity);
				comp.setString("Hello", "World");
				NBTEntity nbtent = new NBTEntity(entity);
				if (!nbtent.toString().contains("__extraData:{Hello:\"World\"}")) {
					throw new NBTApiException("Custom Data did not save to the Entity!");
				}
				comp.removeKey("Hello");
				entity.remove();

			} catch (Exception ex) {
				throw new NBTApiException("Wasn't able to use NBTEntities!", ex);
			}
		}
	}

}
