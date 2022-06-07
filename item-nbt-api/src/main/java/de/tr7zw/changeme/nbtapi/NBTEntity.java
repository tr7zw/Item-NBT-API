package de.tr7zw.changeme.nbtapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import de.tr7zw.annotations.FAUtil;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.changeme.nbtapi.utils.annotations.AvailableSince;
import de.tr7zw.changeme.nbtapi.utils.annotations.CheckUtil;

/**
 * NBT class to access vanilla tags from Entities. Entities don't support custom
 * tags. Use the NBTInjector for custom tags. Changes will be instantly applied
 * to the Entity, use the merge method to do many things at once.
 * 
 * @author tr7zw
 *
 */
public class NBTEntity extends NBTCompound {

	private final Entity ent;

	/**
	 * @param entity Any valid Bukkit Entity
	 */
	public NBTEntity(Entity entity) {
		super(null, null);
		if (entity == null) {
			throw new NullPointerException("Entity can't be null!");
		}
		ent = entity;
	}

	@Override
	public Object getCompound() {
	    if(!Bukkit.isPrimaryThread())throw new NbtApiException("Entity NBT needs to be accessed sync!");
		return NBTReflectionUtil.getEntityNBTTagCompound(NBTReflectionUtil.getNMSEntity(ent));
	}

	@Override
	protected void setCompound(Object compound) {
	    if(!Bukkit.isPrimaryThread())throw new NbtApiException("Entity NBT needs to be accessed sync!");
		NBTReflectionUtil.setEntityNBTTag(compound, NBTReflectionUtil.getNMSEntity(ent));
	}

	/**
	 * Gets the NBTCompound used by spigots PersistentDataAPI. This method is only
	 * available for 1.14+!
	 * 
	 * @return NBTCompound containing the data of the PersistentDataAPI
	 */
	@AvailableSince(version = MinecraftVersion.MC1_14_R1)
	public NBTCompound getPersistentDataContainer() {
		FAUtil.check(this::getPersistentDataContainer, CheckUtil::isAvaliable);
		return new NBTPersistentDataContainer(ent.getPersistentDataContainer());
	}

}
