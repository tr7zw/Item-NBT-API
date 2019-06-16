package de.tr7zw.changeme.nbtapi.utils.nmsmappings;

import java.util.logging.Level;
import org.bukkit.Bukkit;

import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import static de.tr7zw.changeme.nbtapi.utils.MinecraftVersion.logger;

/**
 * Wraps NMS and CRAFT classes
 * 
 * @author tr7zw
 *
 */
@SuppressWarnings("javadoc")
public enum ClassWrapper {
CRAFT_ITEMSTACK(PackageWrapper.CRAFTBUKKIT, "inventory.CraftItemStack"),
CRAFT_ENTITY(PackageWrapper.CRAFTBUKKIT, "entity.CraftEntity"),
CRAFT_WORLD(PackageWrapper.CRAFTBUKKIT, "CraftWorld"),
NMS_NBTBASE(PackageWrapper.NMS, "NBTBase"),
NMS_NBTTAGSTRING(PackageWrapper.NMS, "NBTTagString"),
NMS_NBTTAGINT(PackageWrapper.NMS, "NBTTagInt"),
NMS_ITEMSTACK(PackageWrapper.NMS, "ItemStack"),
NMS_NBTTAGCOMPOUND(PackageWrapper.NMS, "NBTTagCompound"),
NMS_NBTTAGLIST(PackageWrapper.NMS, "NBTTagList"),
NMS_NBTCOMPRESSEDSTREAMTOOLS(PackageWrapper.NMS, "NBTCompressedStreamTools"),
NMS_MOJANGSONPARSER(PackageWrapper.NMS, "MojangsonParser"),
NMS_TILEENTITY(PackageWrapper.NMS, "TileEntity"),
NMS_BLOCKPOSITION(PackageWrapper.NMS, "BlockPosition"),
NMS_WORLDSERVER(PackageWrapper.NMS, "WorldServer"),
NMS_MINECRAFTSERVER(PackageWrapper.NMS, "MinecraftServer"),
NMS_WORLD(PackageWrapper.NMS, "World"),
NMS_ENTITY(PackageWrapper.NMS, "Entity"),
NMS_ENTITYTYPES(PackageWrapper.NMS, "EntityTypes"),
NMS_REGISTRYSIMPLE(PackageWrapper.NMS, "RegistrySimple", MinecraftVersion.MC1_11_R1, MinecraftVersion.MC1_12_R1),
NMS_REGISTRYMATERIALS(PackageWrapper.NMS, "RegistryMaterials"),
NMS_IREGISTRY(PackageWrapper.NMS, "IRegistry"),
NMS_MINECRAFTKEY(PackageWrapper.NMS, "MinecraftKey"),

;
	
    private Class<?> clazz;
    private boolean enabled = false;
    
    ClassWrapper(PackageWrapper packageId, String suffix){
    	this(packageId, suffix, null, null);
    }
    
    ClassWrapper(PackageWrapper packageId, String suffix, MinecraftVersion from, MinecraftVersion to){
    	if(from != null && MinecraftVersion.getVersion().getVersionId() < from.getVersionId()) {
    		return;
    	}
    	if(to != null && MinecraftVersion.getVersion().getVersionId() > to.getVersionId()) {
    		return;
    	}
    	enabled = true;
        try{
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            clazz = Class.forName(packageId.getUri() + "." + version + "." + suffix);
        }catch(Exception ex){
            logger.log(Level.WARNING, "[NBTAPI] Error while trying to resolve the class '" + suffix + "'!", ex);
        }
    }
    
    /**
     * @return The wrapped class
     */
    public Class<?> getClazz(){
        return clazz;
    }
    
    /**
     * @return Is this class available in this Version
     */
    public boolean isEnabled() {
    	return enabled;
    }
    
}
