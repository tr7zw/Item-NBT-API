package de.tr7zw.changeme.nbtapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import de.tr7zw.changeme.nbtapi.utils.CheckUtil;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;

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
    private final boolean readonly;
    private final Object compound;
    private boolean closed = false;

    /**
     * @param entity   Any valid Bukkit Entity
     * @param readonly Readonly makes a copy at init, only reading from that copy
     */
    protected NBTEntity(Entity entity, boolean readonly) {
        super(null, null);
        if (entity == null) {
            throw new NullPointerException("Entity can't be null!");
        }
        this.readonly = readonly;
        ent = entity;
        if (readonly) {
            this.compound = getCompound();
        } else {
            this.compound = null;
        }
    }

    /**
     * Deprecated: Please use the NBT class
     * 
     * @param entity Any valid Bukkit Entity
     */
    @Deprecated
    public NBTEntity(Entity entity) {
        super(null, null);
        if (entity == null) {
            throw new NullPointerException("Entity can't be null!");
        }
        this.readonly = false;
        this.compound = null;
        ent = entity;
    }

    @Override
    protected void setClosed() {
        this.closed = true;
    }

    @Override
    protected boolean isClosed() {
        return closed;
    }

    @Override
    protected boolean isReadOnly() {
        return readonly;
    }

    @Override
    public Object getCompound() {
        // this runs before async check, since it's just a copy
        if (readonly && compound != null) {
            return compound;
        }
        if (!Bukkit.isPrimaryThread())
            throw new NbtApiException("Entity NBT needs to be accessed sync!");
        return NBTReflectionUtil.getEntityNBTTagCompound(NBTReflectionUtil.getNMSEntity(ent));
    }

    @Override
    protected void setCompound(Object compound) {
        if (readonly) {
            throw new NbtApiException("Tried setting data in read only mode!");
        }
        if (!Bukkit.isPrimaryThread())
            throw new NbtApiException("Entity NBT needs to be accessed sync!");
        NBTReflectionUtil.setEntityNBTTag(compound, NBTReflectionUtil.getNMSEntity(ent));
    }

    /**
     * Gets the NBTCompound used by spigots PersistentDataAPI. This method is only
     * available for 1.14+!
     * 
     * @return NBTCompound containing the data of the PersistentDataAPI
     */
    public NBTCompound getPersistentDataContainer() {
        CheckUtil.assertAvailable(MinecraftVersion.MC1_14_R1);
        return new NBTPersistentDataContainer(ent.getPersistentDataContainer());
    }

}
