package de.tr7zw.changeme.nbtapi;

import org.bukkit.Bukkit;

import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;

public enum ClassWrapper {
CRAFT_ITEMSTACK("org.bukkit.craftbukkit.", ".inventory.CraftItemStack"),
CRAFT_ENTITY("org.bukkit.craftbukkit.", ".entity.CraftEntity"),
CRAFT_WORLD("org.bukkit.craftbukkit.", ".CraftWorld"),
NMS_NBTBASE("net.minecraft.server.", ".NBTBase"),
NMS_NBTTAGSTRING("net.minecraft.server.", ".NBTTagString"),
NMS_NBTTAGINT("net.minecraft.server.", ".NBTTagInt"),
NMS_ITEMSTACK("net.minecraft.server.", ".ItemStack"),
NMS_NBTTAGCOMPOUND("net.minecraft.server.", ".NBTTagCompound"),
NMS_NBTTAGLIST("net.minecraft.server.", ".NBTTagList"),
NMS_NBTCOMPRESSEDSTREAMTOOLS("net.minecraft.server.", ".NBTCompressedStreamTools"),
NMS_MOJANGSONPARSER("net.minecraft.server.", ".MojangsonParser"),
NMS_TILEENTITY("net.minecraft.server.", ".TileEntity"),
NMS_BLOCKPOSITION("net.minecraft.server.", ".BlockPosition"),
NMS_WORLDSERVER("net.minecraft.server.", ".WorldServer"),
NMS_MINECRAFTSERVER("net.minecraft.server.", ".MinecraftServer"),
NMS_WORLD("net.minecraft.server.", ".World"),
NMS_ENTITY("net.minecraft.server.", ".Entity"),
NMS_ENTITYTYPES("net.minecraft.server.", ".EntityTypes"),
NMS_REGISTRYSIMPLE("net.minecraft.server.", ".RegistrySimple", MinecraftVersion.MC1_11_R1, MinecraftVersion.MC1_12_R1),
NMS_REGISTRYMATERIALS("net.minecraft.server.", ".RegistryMaterials"),
NMS_IREGISTRY("net.minecraft.server.", ".IRegistry"),
NMS_MINECRAFTKEY("net.minecraft.server.", ".MinecraftKey"),

;

    private Class<?> clazz;
    private boolean enabled = false;
    
    ClassWrapper(String pre, String suffix){
    	this(pre, suffix, null, null);
    }
    
    ClassWrapper(String pre, String suffix, MinecraftVersion from, MinecraftVersion to){
    	if(from != null && MinecraftVersion.getVersion().getVersionId() < from.getVersionId()) {
    		return;
    	}
    	if(to != null && MinecraftVersion.getVersion().getVersionId() > to.getVersionId()) {
    		return;
    	}
    	enabled = true;
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
    
    public boolean isEnabled() {
    	return enabled;
    }
    
}
