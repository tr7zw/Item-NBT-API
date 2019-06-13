package de.tr7zw.nbtinjector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.tr7zw.changeme.nbtapi.ClassWrapper;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NBTReflectionUtil;
import de.tr7zw.changeme.nbtapi.NBTTileEntity;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.ObjectCreator;
import de.tr7zw.changeme.nbtapi.ReflectionMethod;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import javassist.ClassPool;

public class NBTInjector {

	static Logger logger = Logger.getLogger("NBTInjector");

	/**
	 * Replaces the vanilla classes with Wrapped classes that support custom NBT.
	 * This method needs to be called during onLoad so classes are replaced before worlds load.
	 * If your plugin adds a new Entity(probably during onLoad) recall this method so it's class gets Wrapped.
	 */
	public static void inject() {
		try {
			ClassPool classPool = ClassPool.getDefault();
			logger.info("[NBTINJECTOR] Injecting Entity classes...");
			if(MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_10_R1.getVersionId()) {
				for (Map.Entry<String, Class<?>> entry : new HashSet<>(Entity.getCMap().entrySet())) {
					try {
						if (INBTWrapper.class.isAssignableFrom(entry.getValue())) { continue; }//Already injected
						int entityId = Entity.getFMap().get(entry.getValue());

						Class<?> wrapped = ClassGenerator.wrapEntity(classPool, entry.getValue(), "__extraData");
						Entity.getCMap().put(entry.getKey(), wrapped);
						Entity.getDMap().put(wrapped, entry.getKey());

						Entity.getEMap().put(entityId, wrapped);
						Entity.getFMap().put(wrapped, entityId);
					} catch (Exception e) {
						throw new RuntimeException("Exception while injecting " + entry.getKey(), e);
					}
				}
			} else if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_12_R1.getVersionId()){
				Object registry = Entity.getRegistry();
				Map<Object, Object> inverse = new HashMap<>();
				Set<?> it = new HashSet<>((Set<?>) ReflectionMethod.REGISTRY_KEYSET.run(registry));
				for(Object mckey : it) {
					Class<?> tileclass = (Class<?>) ReflectionMethod.REGISTRY_GET.run(registry, mckey);
					inverse.put(tileclass, mckey);
					try {
						if (INBTWrapper.class.isAssignableFrom(tileclass)) { continue; }//Already injected
						Class<?> wrapped = ClassGenerator.wrapEntity(classPool, tileclass, "__extraData");
						ReflectionMethod.REGISTRY_SET.run(registry, mckey, wrapped);
						inverse.put(wrapped, mckey);
					} catch (Exception e) {
						throw new RuntimeException("Exception while injecting " + mckey, e);
					}
				}
				Field inverseField = registry.getClass().getDeclaredField("b");
				setFinal(registry, inverseField, inverse);
			} else if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_13_R2.getVersionId()) {
				Object entityRegistry = ClassWrapper.NMS_IREGISTRY.getClazz().getField("ENTITY_TYPE").get(null);
				Set<?> registryentries = new HashSet<>((Set<?>) ReflectionMethod.REGISTRYMATERIALS_KEYSET.run(entityRegistry));
				for(Object mckey : registryentries) {
					Object entityTypesObj = ReflectionMethod.REGISTRYMATERIALS_GET.run(entityRegistry, mckey);
					Field supplierField = entityTypesObj.getClass().getDeclaredField("aT");
					Field classField = entityTypesObj.getClass().getDeclaredField("aS");
					classField.setAccessible(true);
					supplierField.setAccessible(true);
					Function<Object,Object> function = (Function<Object,Object>) supplierField.get(entityTypesObj);
					Class<?> nmsclass = (Class<?>) classField.get(entityTypesObj);
					try {
						if (INBTWrapper.class.isAssignableFrom(nmsclass)) { continue; }//Already injected
						Class<?> wrapped = ClassGenerator.wrapEntity(classPool, nmsclass, "__extraData");
						setFinal(entityTypesObj, classField, wrapped);
						setFinal(entityTypesObj, supplierField, new Function<Object, Object>() {

							@Override
							public Object apply(Object t) {
								try {
									return wrapped.getConstructor(ClassWrapper.NMS_WORLD.getClazz()).newInstance(t);
								}catch(Exception ex) {
									logger.log(Level.SEVERE, "Error while creating custom entity instance! ",ex);
									return function.apply(t);//Fallback to the original one
								}
							}
						});
					} catch (Exception e) {
						throw new RuntimeException("Exception while injecting " + mckey, e);
					}
				}
			} else { //1.14+
				Object entityRegistry = ClassWrapper.NMS_IREGISTRY.getClazz().getField("ENTITY_TYPE").get(null);
				Set<?> registryentries = new HashSet<>((Set<?>) ReflectionMethod.REGISTRYMATERIALS_KEYSET.run(entityRegistry));
				for(Object mckey : registryentries) {
					Object entityTypesObj = ReflectionMethod.REGISTRYMATERIALS_GET.run(entityRegistry, mckey);
					Field creatorField = entityTypesObj.getClass().getDeclaredField("aZ");
					creatorField.setAccessible(true);
					Object creator = creatorField.get(entityTypesObj);
					Method createEntityMethod = creator.getClass().getMethod("create", ClassWrapper.NMS_ENTITYTYPES.getClazz(), ClassWrapper.NMS_WORLD.getClazz());
					createEntityMethod.setAccessible(true);
					Object entityInstance = null;
					try {
						entityInstance = createEntityMethod.invoke(creator, entityTypesObj, null);
						if(entityInstance == null)
							throw new NullPointerException();
					}catch(Throwable npe) {
						logger.info("Wasn't able to create an Entity instace, won't be able add NBT to '" + entityTypesObj.getClass().getMethod("g").invoke(entityTypesObj) + "' entity Type!");
						continue;
					}
					Class<?> nmsclass = entityInstance.getClass();
					try {
						if (INBTWrapper.class.isAssignableFrom(nmsclass)) { continue; }//Already injected
						Class<?> wrapped = ClassGenerator.wrapEntity(classPool, nmsclass, "__extraData");
						setFinal(entityTypesObj, creatorField, ClassGenerator.createEntityTypeWrapper(classPool, wrapped).newInstance());
					} catch (Exception e) {
						throw new RuntimeException("Exception while injecting " + mckey, e);
					}
				}
			}

			logger.info("[NBTINJECTOR] Injecting Tile Entity classes...");
			if(MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_10_R1.getVersionId()) {
				for (Map.Entry<String, Class<?>> entry : new HashSet<>(TileEntity.getFMap().entrySet())) {
					try {
						if (INBTWrapper.class.isAssignableFrom(entry.getValue())) { continue; }//Already injected
						Class<?> wrapped = ClassGenerator.wrapTileEntity(classPool, entry.getValue(), "__extraData");
						TileEntity.getFMap().put(entry.getKey(), wrapped);
						TileEntity.getGMap().put(wrapped, entry.getKey());
					} catch (Exception e) {
						throw new RuntimeException("Exception while injecting " + entry.getKey(), e);
					}
				}
			} else if (MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_12_R1.getVersionId()){
				Object registry = TileEntity.getRegistry();
				Map<Object, Object> inverse = new HashMap<>();
				Set<?> it = new HashSet<>((Set<?>) ReflectionMethod.REGISTRY_KEYSET.run(registry));
				for(Object mckey : it) {
					Class<?> tileclass = (Class<?>) ReflectionMethod.REGISTRY_GET.run(registry, mckey);
					inverse.put(tileclass, mckey);
					try {
						if (INBTWrapper.class.isAssignableFrom(tileclass)) { continue; }//Already injected
						Class<?> wrapped = ClassGenerator.wrapTileEntity(classPool, tileclass, "__extraData");
						ReflectionMethod.REGISTRY_SET.run(registry, mckey, wrapped);
						inverse.put(wrapped, mckey);
					} catch (Exception e) {
						throw new RuntimeException("Exception while injecting " + mckey, e);
					}
				}
				Field inverseField = registry.getClass().getDeclaredField("b");
				setFinal(registry, inverseField, inverse);
			} else { // 1.13+
				Object tileRegistry = ClassWrapper.NMS_IREGISTRY.getClazz().getField("BLOCK_ENTITY_TYPE").get(null);
				Set<?> registryentries = new HashSet<>((Set<?>) ReflectionMethod.REGISTRYMATERIALS_KEYSET.run(tileRegistry));
				for(Object mckey : registryentries) {
					Object tileEntityTypesObj = ReflectionMethod.REGISTRYMATERIALS_GET.run(tileRegistry, mckey);
					String supplierFieldName = "A";
					if(MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId())
						supplierFieldName = "H";
					Field supplierField = tileEntityTypesObj.getClass().getDeclaredField(supplierFieldName);
					supplierField.setAccessible(true);
					Supplier<Object> supplier = (Supplier<Object>) supplierField.get(tileEntityTypesObj);
					Class<?> nmsclass = supplier.get().getClass();
					try {
						if (INBTWrapper.class.isAssignableFrom(nmsclass)) { continue; }//Already injected
						Class<?> wrapped = ClassGenerator.wrapTileEntity(classPool, nmsclass, "__extraData");
						setFinal(tileEntityTypesObj, supplierField, new Supplier<Object>() {
							@Override
							public Object get() {
								try {
									return wrapped.newInstance();
								} catch (InstantiationException | IllegalAccessException e) {
									logger.log(Level.SEVERE, "Error while creating custom tile instance! ",e);
									return supplier.get(); //Use the original one as fallback
								}
							}
						});
					} catch (Exception e) {
						throw new RuntimeException("Exception while injecting " + mckey, e);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static NBTCompound getNbtData(Object object) {
		if (object instanceof INBTWrapper) {
			return ((INBTWrapper) object).getNbtData();
		}
		return null;
	}

	/**
	 * Entities that have just been spawned(from plugins or natually) may use the wrong(Vanilla) class.
	 * Calling this method removes the wrong entity and respawns it using the correct class. It also tries
	 * to keep all data of the original entity, but some stuff like passengers will probably cause problems.
	 * Recalling this method on a patched Entity doesn nothing and returns the Entity instance.
	 * 
	 * WARNING: This causes the entity to get a new Bukkit Entity instance. For other plugins the entity will
	 * be a dead/removed entity, even if it's still kinda there. Bestcase you spawn an Entity and directly replace
	 * your instance with a patched one.
	 * Also, for players ingame the entity will quickly flash, since it's respawned.
	 * 
	 * @param entity Entity to respawn with the correct class.
	 * @return Entity The new instance of the entity.
	 */
	public static org.bukkit.entity.Entity patchEntity(org.bukkit.entity.Entity entity){
		if (entity == null) { return null; }
		try {
			Object ent = NBTReflectionUtil.getNMSEntity(entity);
			if (!(ent instanceof INBTWrapper)) {//Replace Entity with custom one
				Object cworld = ClassWrapper.CRAFT_WORLD.getClazz().cast(entity.getWorld());
				Object nmsworld = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(cworld);
				NBTContainer oldNBT = new NBTContainer(new NBTEntity(entity).getCompound());
				Method create = ClassWrapper.NMS_ENTITYTYPES.getClazz().getMethod("a", ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), ClassWrapper.NMS_WORLD.getClazz());
				String id = "";
				if(MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_10_R1.getVersionId()) {
					id = Entity.getBackupMap().get(ent.getClass());
				} else if(MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_13_R1.getVersionId()){
					id = ReflectionMethod.REGISTRY_GET_INVERSE.run(Entity.getRegistry(), ent.getClass()).toString();
				} else {
					id = (String) ReflectionMethod.NMS_ENTITY_GETSAVEID.run(ent);
				}
				oldNBT.setString("id", id);
				oldNBT.removeKey("UUIDMost");
				oldNBT.removeKey("UUIDLeast");
				entity.remove();
				Object newEntity = create.invoke(null, oldNBT.getCompound(), nmsworld);
				if(newEntity instanceof Optional<?>)
					newEntity = ((Optional<?>)newEntity).get();
				Method spawn = ClassWrapper.NMS_WORLD.getClazz().getMethod("addEntity", ClassWrapper.NMS_ENTITY.getClazz());
				spawn.invoke(nmsworld, newEntity);
				logger.info("Created patched instance: " + newEntity.getClass().getName());
				Method asBukkit = newEntity.getClass().getMethod("getBukkitEntity");
				return (org.bukkit.entity.Entity) asBukkit.invoke(newEntity);
			}
		} catch (Exception e) {
			throw new NbtApiException("Error while patching an Entity '" + entity + "'", e);
		}
		return entity;
	}

	/**
	 * Gets the persistant NBTCompound from a given entity. If the Entity isn't yet patched,
	 * this method will return null.
	 * 
	 * @param entity Entity to get the NBTCompound from
	 * @return NBTCompound instance
	 */
	public static NBTCompound getNbtData(org.bukkit.entity.Entity entity) {
		if (entity == null) { return null; }
		try {
			Object ent = NBTReflectionUtil.getNMSEntity(entity);
			if (!(ent instanceof INBTWrapper)) {
				logger.info("Entity wasn't the correct class! '" + ent.getClass().getName() + "'");
			}
			/*if (!(ent instanceof INBTWrapper)) {//Replace Entity with custom one
				entity = patchEntity(entity);
				System.out.println("Autopatched Entity: " + entity);
	            return getNbtData(NBTReflectionUtil.getNMSEntity(entity));
				return null; // For now don't do anything, just return null.
			}*/
			return getNbtData(ent);
		} catch (Exception e) {
			throw new NbtApiException("Error while getting the NBT from an Entity '" + entity + "'.", e);
		}
	}

	/**
	 * Gets the persistant NBTCompound from a given TileEntity. If the Tile isn't yet patched,
	 * this method will autopatch it. This will unlink the given BlockState, so calling block.getState()
	 * again may be necessary. This behavior may change in the future.
	 * 
	 * @param tile TileEntity to get the NBTCompound from
	 * @return NBTCompound instance
	 */
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
				//Loading Updated Tile

				Object tileEntityUpdated;
				if(MinecraftVersion.getVersion() == MinecraftVersion.MC1_9_R1) {
					tileEntityUpdated = ReflectionMethod.TILEENTITY_LOAD_LEGACY191.run(null, null, new NBTTileEntity(tile).getCompound());
				} else if(MinecraftVersion.getVersion() == MinecraftVersion.MC1_8_R3 || MinecraftVersion.getVersion() == MinecraftVersion.MC1_9_R2) {
					tileEntityUpdated = ReflectionMethod.TILEENTITY_LOAD_LEGACY183.run(null, new NBTTileEntity(tile).getCompound());
				} else if(MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_12_R1.getVersionId()){
					tileEntityUpdated = ReflectionMethod.TILEENTITY_LOAD_LEGACY1121.run(null, nmsworld, new NBTTileEntity(tile).getCompound());
				} else {
					tileEntityUpdated = ReflectionMethod.TILEENTITY_LOAD.run(null, new NBTTileEntity(tile).getCompound());
				}
				ReflectionMethod.NMS_WORLD_REMOVE_TILEENTITY.run(nmsworld, pos);
				ReflectionMethod.NMS_WORLD_SET_TILEENTITY.run(nmsworld, pos, tileEntityUpdated);
				return getNbtData(tileEntityUpdated);
			}
			return getNbtData(tileEntity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void setFinal(Object obj, Field field, Object newValue) throws Exception {
		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(obj, newValue);
	}

	private static Field getAccessable(Field field) {
		field.setAccessible(true);
		return field;
	}

	static class Entity {
		private static Map<Class<?>, String> backupMap = new HashMap<>();

		static {
			try {
				if(MinecraftVersion.getVersion().getVersionId() <= MinecraftVersion.MC1_10_R1.getVersionId()) {
					backupMap.putAll(getDMap());
				}
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		}

		static Object getRegistry() throws ReflectiveOperationException {
			return getAccessable(ClassWrapper.NMS_ENTITYTYPES.getClazz().getDeclaredField("b")).get(null);
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

		static Object getRegistry() throws ReflectiveOperationException {
			return getAccessable(ClassWrapper.NMS_TILEENTITY.getClazz().getDeclaredField("f")).get(null);
		}

		static Map<String, Class<?>> getFMap() throws ReflectiveOperationException {
			return (Map<String, Class<?>>) getAccessable(ClassWrapper.NMS_TILEENTITY.getClazz().getDeclaredField("f")).get(null);
		}

		static Map<Class<?>, String> getGMap() throws ReflectiveOperationException {
			return (Map<Class<?>, String>) getAccessable(ClassWrapper.NMS_TILEENTITY.getClazz().getDeclaredField("g")).get(null);
		}
	}
}
