package de.tr7zw.itemnbtapi;

import org.bukkit.Bukkit;

public enum ClassWrapper {
CRAFT_ITEMSTACK("org.bukkit.craftbukkit.", ".inventory.CraftItemStack"),
CRAFT_ENTITY("org.bukkit.craftbukkit.", ".entity.CraftEntity"),
CRAFT_WORLD("org.bukkit.craftbukkit.", ".CraftWorld"),
NMS_NBTBASE("net.minecraft.server.", ".NBTBase"),
NMS_NBTTAGSTRING("net.minecraft.server.", ".NBTTagString"),
NMS_ITEMSTACK("net.minecraft.server.", ".ItemStack"),
NMS_NBTTAGCOMPOUND("net.minecraft.server.", ".NBTTagCompound"),
NMS_NBTTAGLIST("net.minecraft.server.", ".NBTTagList"),
NMS_NBTCOMPRESSEDSTREAMTOOLS("net.minecraft.server.", ".NBTCompressedStreamTools"),
NMS_MOJANGSONPARSER("net.minecraft.server.", ".MojangsonParser"),
NMS_TILEENTITY("net.minecraft.server.", ".TileEntity"),
NMS_BLOCKPOSITION("net.minecraft.server.", ".BlockPosition"),
NMS_WORLD("net.minecraft.server.", ".WorldServer"),
NMS_ENTITY("net.minecraft.server.", ".Entity"),
;
    
    
    
    private Class<?> clazz;
    
    ClassWrapper(String pre, String suffix){
        try{
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            clazz = Class.forName(pre + version + suffix);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Class<?> getClazz(){
        return clazz;
    }
    
}
