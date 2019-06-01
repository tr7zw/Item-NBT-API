package de.tr7zw.itemnbtapi;

import javassist.ClassPool;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

public class NBTInjector {

	static Logger           logger           = Logger.getLogger("NBTInjector");

	public static void inject() {
		try {
			ClassPool classPool = ClassPool.getDefault();
			logger.info("Injecting Entity classes...");
			for (Map.Entry<String, Class<?>> entry : new HashSet<>(Entity.getCMap().entrySet())) {
				try {
					if (INBTWrapper.class.isAssignableFrom(entry.getValue())) { continue; }//Already injected
					int entityId = Entity.getFMap().get(entry.getValue());

					Class wrapped = ClassGenerator.wrapEntity(classPool, entry.getValue(), "__extraData");
					Entity.getCMap().put(entry.getKey(), wrapped);
					Entity.getDMap().put(wrapped, entry.getKey());

					Entity.getEMap().put(entityId, wrapped);
					Entity.getFMap().put(wrapped, entityId);
				} catch (Exception e) {
					throw new RuntimeException("Exception while injecting " + entry.getKey(), e);
				}
			}

			logger.info("Injecting Tile Entity classes...");
			for (Map.Entry<String, Class<?>> entry : new HashSet<>(TileEntity.getFMap().entrySet())) {
				try {
					if (INBTWrapper.class.isAssignableFrom(entry.getValue())) { continue; }//Already injected

					Class wrapped = ClassGenerator.wrapTileEntity(classPool, entry.getValue(), "__extraData");
					TileEntity.getFMap().put(entry.getKey(), wrapped);
					TileEntity.getGMap().put(wrapped, entry.getKey());
				} catch (Exception e) {
					throw new RuntimeException("Exception while injecting " + entry.getKey(), e);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static NBTCompound getNbtData(Object object) {
		System.out.print("Getting from: " + object.getClass().getSimpleName());
		if (object instanceof INBTWrapper) {
			System.out.println("Is INBTWrapper");
			return ((INBTWrapper) object).getNbtData();
		}
		return null;
	}

	public static NBTCompound getNbtData(org.bukkit.entity.Entity entity) {
		if (entity == null) { return null; }
		try {
			return getNbtData(NBTReflectionUtil.getNMSEntity(entity));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static NBTCompound getNbtData(org.bukkit.block.BlockState tile) {
		if (tile == null) { return null; }
		try {
            Object pos = ObjectCreator.NMS_BLOCKPOSITION.getInstance(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = ClassWrapper.CRAFT_WORLD.getClazz().cast(tile.getWorld());
            Object nmsworld = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(cworld);
            Object tileEntity = ReflectionMethod.NMS_WORLD_GET_TILEENTITY.run(nmsworld, pos);
			return getNbtData(tileEntity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Field getAccessable(Field field) {
		field.setAccessible(true);
		return field;
	}

	static class Entity {
		static Map<String, Class<?>> getCMap() throws ReflectiveOperationException {
			return (Map<String, Class<?>>) getAccessable(ClassWrapper.NMS_ENTITYTYPES.getClazz().getDeclaredField("c")).get(null);
		}

		static Map<Class<?>, String> getDMap() throws ReflectiveOperationException {
			return (Map<Class<?>, String>) getAccessable(ClassWrapper.NMS_ENTITYTYPES.getClazz().getDeclaredField("d")).get(null);
		}

		static Map<Integer, Class<?>> getEMap() throws ReflectiveOperationException {
			return (Map<Integer, Class<?>>) getAccessable(ClassWrapper.NMS_ENTITYTYPES.getClazz().getDeclaredField("e")).get(null);
		}

		static Map<Class<?>, Integer> getFMap() throws ReflectiveOperationException {
			return (Map<Class<?>, Integer>) getAccessable(ClassWrapper.NMS_ENTITYTYPES.getClazz().getDeclaredField("f")).get(null);
		}
	}

	static class TileEntity {
		static Map<String, Class<?>> getFMap() throws ReflectiveOperationException {
			return (Map<String, Class<?>>) getAccessable(ClassWrapper.NMS_TILEENTITY.getClazz().getDeclaredField("f")).get(null);
		}

		static Map<Class<?>, String> getGMap() throws ReflectiveOperationException {
			return (Map<Class<?>, String>) getAccessable(ClassWrapper.NMS_TILEENTITY.getClazz().getDeclaredField("g")).get(null);
		}
	}
}
