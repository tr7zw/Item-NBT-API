package de.tr7zw.changeme.nbtapi;

import org.bukkit.entity.Entity;

public class NBTEntity extends NBTCompound {

    private final Entity ent;

    public NBTEntity(Entity entity) {
        super(null, null);
        ent = entity;
    }

    public Object getCompound() {
        return NBTReflectionUtil.getEntityNBTTagCompound(NBTReflectionUtil.getNMSEntity(ent));
    }

    protected void setCompound(Object compound) {
        NBTReflectionUtil.setEntityNBTTag(compound, NBTReflectionUtil.getNMSEntity(ent));
    }

}
