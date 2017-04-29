package de.tr7zw.itemnbtapi;

import org.bukkit.entity.Entity;

public class NBTEntity extends NBTCompound{

    private final Entity ent;
    
    public NBTEntity(Entity entity) {
        super(null, null);
        ent = entity;
    }
    
    protected Object getCompound() {
        return NBTReflectionUtil.getEntityNBTTagCompound(NBTReflectionUtil.getNMSEntity(ent));
    }
    
    protected void setCompound(Object tag) {
        NBTReflectionUtil.setEntityNBTTag(tag, NBTReflectionUtil.getNMSEntity(ent));
    }


}
