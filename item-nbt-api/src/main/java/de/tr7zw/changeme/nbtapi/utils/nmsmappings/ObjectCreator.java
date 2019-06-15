package de.tr7zw.changeme.nbtapi.utils.nmsmappings;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;

import static de.tr7zw.changeme.nbtapi.utils.MinecraftVersion.logger;

public enum ObjectCreator {
    NMS_NBTTAGCOMPOUND(null, null, ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()),
    NMS_BLOCKPOSITION(null, null, ClassWrapper.NMS_BLOCKPOSITION.getClazz(), int.class, int.class, int.class),
    NMS_COMPOUNDFROMITEM(MinecraftVersion.MC1_11_R1, null, ClassWrapper.NMS_ITEMSTACK.getClazz(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()),
    ;

    private Constructor<?> construct;
    private Class<?> targetClass;

    ObjectCreator(MinecraftVersion from, MinecraftVersion to, Class<?> clazz, Class<?>... args){
       if(from != null && MinecraftVersion.getVersion().getVersionId() < from.getVersionId())
    	   return;
       if(to != null && MinecraftVersion.getVersion().getVersionId() > to.getVersionId())
    	   return;
    	try{
        	this.targetClass = clazz;
            construct = clazz.getDeclaredConstructor(args);
            construct.setAccessible(true);
        }catch(Exception ex){
            logger.log(Level.SEVERE, "Unable to find the constructor for the class '" + clazz.getName() + "'", ex);
        }
    }
    
    public Object getInstance(Object... args){
        try{
            return construct.newInstance(args);
        }catch(Exception ex){
        	throw new NbtApiException("Exception while creating a new instance of '" + targetClass + "'", ex);
        }
    }
    
}
