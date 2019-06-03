package de.tr7zw.itemnbtapi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

import javassist.ClassPool;

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

	public static org.bukkit.entity.Entity patchEntity(org.bukkit.entity.Entity entity){
		if (entity == null) { return null; }
		try {
			Object ent = NBTReflectionUtil.getNMSEntity(entity);
			if (!(ent instanceof INBTWrapper)) {//Replace Entity with custom one
				System.out.println("Respawning Entity!");
				Object cworld = ClassWrapper.CRAFT_WORLD.getClazz().cast(entity.getWorld());
	            Object nmsworld = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(cworld);
	            NBTContainer oldNBT = new NBTContainer(new NBTEntity(entity).getCompound());
	            Method create = ClassWrapper.NMS_ENTITYTYPES.getClazz().getMethod("a", ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), ClassWrapper.NMS_WORLD.getClazz());
	            oldNBT.setString("id", Entity.getBackupMap().get(ent.getClass()));
	            oldNBT.removeKey("UUIDMost");
	            oldNBT.removeKey("UUIDLeast");
	            entity.remove();
	            Object newEntity = create.invoke(null, oldNBT.getCompound(), nmsworld);
	            Method spawn = ClassWrapper.NMS_WORLD.getClazz().getMethod("addEntity", ClassWrapper.NMS_ENTITY.getClazz());
	            spawn.invoke(nmsworld, newEntity);
	            Method asBukkit = newEntity.getClass().getMethod("getBukkitEntity");
	            return (org.bukkit.entity.Entity) asBukkit.invoke(newEntity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return entity;
	}
	
	public static NBTCompound getNbtData(org.bukkit.entity.Entity entity) {
		if (entity == null) { return null; }
		try {
			Object ent = NBTReflectionUtil.getNMSEntity(entity);
			if (!(ent instanceof INBTWrapper)) {//Replace Entity with custom one
				entity = patchEntity(entity);
				System.out.println("Autopatched Entity: " + entity);
	            return getNbtData(NBTReflectionUtil.getNMSEntity(entity));
			}
			return getNbtData(ent);
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
            if(tileEntity == null) { // Not a tile block
            	return null;
            }
    		if (!(tileEntity instanceof INBTWrapper)) {
    			System.out.print("Tile is not custom yet, converting!");
    			//Loading Updated Tile
    			Method load = ClassWrapper.NMS_TILEENTITY.getClazz().getDeclaredMethod("c", ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
    			Object tileEntityUpdated = load.invoke(null, new NBTTileEntity(tile).getCompound());
    			Method setter = nmsworld.getClass().getMethod("setTileEntity", ClassWrapper.NMS_BLOCKPOSITION.getClazz(), ClassWrapper.NMS_TILEENTITY.getClazz());
    			Method remove = nmsworld.getClass().getMethod("s", ClassWrapper.NMS_BLOCKPOSITION.getClazz());
    			remove.invoke(nmsworld, pos);
    			setter.invoke(nmsworld, pos, tileEntityUpdated);
    			return getNbtData(tileEntityUpdated);
    		}
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
		private static Map<Class<?>, String> backupMap = new HashMap<>();
		
		static {
			try {
				backupMap.putAll(getDMap());
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		}
		
		static Map<Class<?>, String> getBackupMap() throws ReflectiveOperationException {
			return backupMap;
		}
		
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
