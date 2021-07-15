package de.tr7zw.nbtinjector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import dev.tr7zw.nbtapi.NBTCompound;
import dev.tr7zw.nbtapi.NBTContainer;
import dev.tr7zw.nbtapi.NBTEntity;
import dev.tr7zw.nbtapi.NBTReflectionUtil;
import dev.tr7zw.nbtapi.NBTTileEntity;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.Writeable;
import dev.tr7zw.nbtapi.utils.MinecraftVersion;
import dev.tr7zw.nbtapi.utils.nmsmappings.ClassWrapper;
import dev.tr7zw.nbtapi.utils.nmsmappings.ObjectCreator;
import dev.tr7zw.nbtapi.utils.nmsmappings.ReflectionMethod;
import javassist.ClassPool;

/**
 * Main class to interact with custom NBT on Tiles and Entities
 * 
 * @author tr7zw
 *
 */
public class NBTInjector {

	/**
	 * Hidden Constructor
	 */
	private NBTInjector() {

	}

	protected static Logger logger = Logger.getLogger("NBTInjector");
	private static boolean isInjected = false;
	private static final String NOT_INJECTED_MESSAGE = "The NBTInjector has not been enabled!\n"
			+ "You need to call 'NBTInjector.inject()' during the Method 'onLoad' of your Plugin!"
			+ " Check the Wiki/Pluginpage for more information!";

	/**
	 * Replaces the vanilla classes with Wrapped classes that support custom NBT.
	 * This method needs to be called during onLoad so classes are replaced before
	 * worlds load. If your plugin adds a new Entity(probably during onLoad) recall
	 * this method so it's class gets Wrapped.
	 */
	public static void inject() {
		if(isInjected)return;
		if(MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4 || MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_16_R1)) {
			logger.warning("[NBTINJECTOR] The NBT-Injector is not compatibel with this Minecraft Version! For 1.16+ please use the persistent storage API.");
			return;
		}
		isInjected = true;
		try {
			ClassPool classPool = ClassPool.getDefault();
			logger.info("[NBTINJECTOR] Injecting Entity classes...");
			if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_10_R1.getVersionId()) {
				InternalInjectors.entity1v10Below(classPool);
			} else if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_12_R1.getVersionId()) {
				InternalInjectors.entity1v12Below(classPool);
			} else if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_13_R2.getVersionId()) {
				InternalInjectors.entity1v13Below(classPool);
			} else { // 1.14+
				InternalInjectors.entity1v14(classPool);
			}

			logger.info("[NBTINJECTOR] Injecting Tile Entity classes...");
			if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_10_R1.getVersionId()) {
				InternalInjectors.tile1v10Below(classPool);
			} else if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_12_R1.getVersionId()) {
				InternalInjectors.tile1v12Below(classPool);
			} else { // 1.13+
				InternalInjectors.tile1v13(classPool);
			}
		} catch (Exception e) {
			throw new NbtApiException(e);
		}
	}

	public static boolean isInjected() {
		return isInjected;
	}

	private static NBTCompound getNbtData(Object object) {
		if (object instanceof INBTWrapper) {
			return ((INBTWrapper) object).getNbtData();
		}
		return null;
	}

	/**
	 * Entities that have just been spawned(from plugins or natually) may use the
	 * wrong(Vanilla) class. Calling this method removes the wrong entity and
	 * respawns it using the correct class. It also tries to keep all data of the
	 * original entity, but some stuff like passengers will probably cause problems.
	 * Recalling this method on a patched Entity doesn nothing and returns the
	 * Entity instance.
	 * 
	 * WARNING: This causes the entity to get a new Bukkit Entity instance. For
	 * other plugins the entity will be a dead/removed entity, even if it's still
	 * kinda there. Bestcase you spawn an Entity and directly replace your instance
	 * with a patched one. Also, for players ingame the entity will quickly flash,
	 * since it's respawned.
	 * 
	 * @param entity Entity to respawn with the correct class.
	 * @return Entity The new instance of the entity.
	 */
	public static org.bukkit.entity.Entity patchEntity(org.bukkit.entity.Entity entity) {
		if (entity == null) {
			return null;
		}
		if(!isInjected)throw new NbtApiException(NOT_INJECTED_MESSAGE);
		try {
			Object ent = NBTReflectionUtil.getNMSEntity(entity);
			if (!(ent instanceof INBTWrapper)) {// Replace Entity with custom one
				Object cworld = ClassWrapper.CRAFT_WORLD.getClazz().cast(entity.getWorld());
				Object nmsworld = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(cworld);
				NBTContainer oldNBT = new NBTContainer(new NBTEntity(entity).getCompound());
				Method create = ClassWrapper.NMS_ENTITYTYPES.getClazz().getMethod("a",
						ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), ClassWrapper.NMS_WORLD.getClazz());
				String id = "";
				if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_10_R1.getVersionId()) {
					id = Entity.getBackupMap().get(ent.getClass());
				} else if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_12_R1.getVersionId()) {
					id = ReflectionMethod.REGISTRY_GET_INVERSE.run(Entity.getRegistry(), ent.getClass()).toString();
				} else if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_13_R2.getVersionId()) {
					id = InternalInjectors.classToMCKey.get(ent.getClass()).toString();
				} else {
					id = (String) ReflectionMethod.NMS_ENTITY_GETSAVEID.run(ent);
				}
				oldNBT.setString("id", id);
				oldNBT.removeKey("UUIDMost");
				oldNBT.removeKey("UUIDLeast");
				entity.remove();
				Object newEntity = create.invoke(null, oldNBT.getCompound(), nmsworld);
				if (newEntity instanceof Optional<?> && ((Optional<?>) newEntity).isPresent())
					newEntity = ((Optional<?>) newEntity).get();
				Method spawn = ClassWrapper.NMS_WORLD.getClazz().getMethod("addEntity",
						ClassWrapper.NMS_ENTITY.getClazz());
				spawn.invoke(nmsworld, newEntity);
				//logger.info("Created patched instance: " + newEntity.getClass().getName());
				Method asBukkit = newEntity.getClass().getMethod("getBukkitEntity");
				return (org.bukkit.entity.Entity) asBukkit.invoke(newEntity);
			}
		} catch (Exception e) {
			throw new NbtApiException("Error while patching an Entity '" + entity + "'", e);
		}
		return entity;
	}

	/**
	 * Gets the persistant NBTCompound from a given entity. If the Entity isn't yet
	 * patched, this method will return null.
	 * 
	 * @param entity Entity to get the NBTCompound from
	 * @return NBTCompound instance
	 */
	public static Writeable getNbtData(org.bukkit.entity.Entity entity) {
		if (entity == null) {
			return null;
		}
		if(!isInjected)throw new NbtApiException(NOT_INJECTED_MESSAGE);
		try {
			Object ent = NBTReflectionUtil.getNMSEntity(entity);
			if (!(ent instanceof INBTWrapper)) {
				logger.info("Entity wasn't the correct class! '" + ent.getClass().getName() + "'");
			}
			return getNbtData(ent);
		} catch (Exception e) {
			throw new NbtApiException("Error while getting the NBT from an Entity '" + entity + "'.", e);
		}
	}

	/**
	 * Gets the persistant NBTCompound from a given TileEntity. If the Tile isn't
	 * yet patched, this method will autopatch it. This will unlink the given
	 * BlockState, so calling block.getState() again may be necessary. This behavior
	 * may change in the future.
	 * 
	 * @param tile TileEntity to get the NBTCompound from
	 * @return NBTCompound instance
	 */
	public static NBTCompound getNbtData(org.bukkit.block.BlockState tile) {
		if (tile == null) {
			return null;
		}
		if(!isInjected)throw new NbtApiException(NOT_INJECTED_MESSAGE);
		try {
			Object pos = ObjectCreator.NMS_BLOCKPOSITION.getInstance(tile.getX(), tile.getY(), tile.getZ());
			Object cworld = ClassWrapper.CRAFT_WORLD.getClazz().cast(tile.getWorld());
			Object nmsworld = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(cworld);
			Object tileEntity = ReflectionMethod.NMS_WORLD_GET_TILEENTITY.run(nmsworld, pos);
			if (tileEntity == null) { // Not a tile block
				return null;
			}
			if (!(tileEntity instanceof INBTWrapper)) {
				// Loading Updated Tile

				Object tileEntityUpdated;
				if (MinecraftVersion.getVersion() == MinecraftVersion.MC1_9_R1) {
					tileEntityUpdated = ReflectionMethod.TILEENTITY_LOAD_LEGACY191.run(null, null,
							new NBTTileEntity(tile).getCompound());
				} else if (MinecraftVersion.getVersion() == MinecraftVersion.MC1_8_R3
						|| MinecraftVersion.getVersion() == MinecraftVersion.MC1_9_R2) {
					tileEntityUpdated = ReflectionMethod.TILEENTITY_LOAD_LEGACY183.run(null,
							new NBTTileEntity(tile).getCompound());
				} else if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_12_R1.getVersionId()) {
					tileEntityUpdated = ReflectionMethod.TILEENTITY_LOAD_LEGACY1121.run(null, nmsworld,
							new NBTTileEntity(tile).getCompound());
				} else {
					tileEntityUpdated = ReflectionMethod.TILEENTITY_LOAD.run(null,
							new NBTTileEntity(tile).getCompound());
				}
				ReflectionMethod.NMS_WORLD_REMOVE_TILEENTITY.run(nmsworld, pos);
				ReflectionMethod.NMS_WORLD_SET_TILEENTITY.run(nmsworld, pos, tileEntityUpdated);
				return getNbtData(tileEntityUpdated);
			}
			return getNbtData(tileEntity);
		} catch (Exception e) {
			throw new NbtApiException(e);
		}
	}

	private static Field getAccessable(Field field) {
		field.setAccessible(true);
		return field;
	}

	@SuppressWarnings("unchecked")
	static class Entity {

		/**
		 * Hidden Constructor
		 */
		private Entity() {
		}

		private static Map<Class<?>, String> backupMap = new HashMap<>();

		static {
			try {
				if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_10_R1.getVersionId()) {
					backupMap.putAll(getDMap());
				}
			} catch (ReflectiveOperationException e) {
				throw new NbtApiException(e);
			}
		}
		
		static Object getRegistry() throws ReflectiveOperationException {
			return getAccessable(ClassWrapper.NMS_ENTITYTYPES.getClazz().getDeclaredField("b")).get(null);
		}

		static Object getRegistryId(Object reg) throws ReflectiveOperationException {
			return getAccessable(reg.getClass().getDeclaredField("a")).get(reg);
		}

		static Map<Class<?>, String> getBackupMap() throws ReflectiveOperationException {
			return backupMap;
		}

		static Map<String, Class<?>> getCMap() throws ReflectiveOperationException {
			return (Map<String, Class<?>>) getAccessable(ClassWrapper.NMS_ENTITYTYPES.getClazz().getDeclaredField("c"))
					.get(null);
		}

		static Map<Class<?>, String> getDMap() throws ReflectiveOperationException {
			return (Map<Class<?>, String>) getAccessable(ClassWrapper.NMS_ENTITYTYPES.getClazz().getDeclaredField("d"))
					.get(null);
		}

		static Map<Integer, Class<?>> getEMap() throws ReflectiveOperationException {
			return (Map<Integer, Class<?>>) getAccessable(ClassWrapper.NMS_ENTITYTYPES.getClazz().getDeclaredField("e"))
					.get(null);
		}

		static Map<Class<?>, Integer> getFMap() throws ReflectiveOperationException {
			return (Map<Class<?>, Integer>) getAccessable(ClassWrapper.NMS_ENTITYTYPES.getClazz().getDeclaredField("f"))
					.get(null);
		}
	}

	@SuppressWarnings("unchecked")
	static class TileEntity {
		/**
		 * Hidden Constructor
		 */
		private TileEntity() {
		}

		static Object getRegistry() throws ReflectiveOperationException {
			return getAccessable(ClassWrapper.NMS_TILEENTITY.getClazz().getDeclaredField("f")).get(null);
		}

		static Map<String, Class<?>> getFMap() throws ReflectiveOperationException {
			return (Map<String, Class<?>>) getAccessable(ClassWrapper.NMS_TILEENTITY.getClazz().getDeclaredField("f"))
					.get(null);
		}

		static Map<Class<?>, String> getGMap() throws ReflectiveOperationException {
			return (Map<Class<?>, String>) getAccessable(ClassWrapper.NMS_TILEENTITY.getClazz().getDeclaredField("g"))
					.get(null);
		}
	}
}
