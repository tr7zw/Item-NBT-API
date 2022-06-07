package de.tr7zw.changeme.nbtapi.utils.nmsmappings;

import de.tr7zw.changeme.nbtapi.NbtApiException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Temporary solution to hold Forge1710 mappings.
 * 
 * @author EverNife
 *
 */
public class Forge1710Mappings {

    private static Map<String,String> classMap = new HashMap<>();
    private static Map<String,String> methodMap = new HashMap<>();
    private static Method crucible_toString;

    static {
        //Fields
        classMap.put("NMS_NBTBASE","net.minecraft.nbt.NBTBase");
        classMap.put("NMS_NBTTAGSTRING","net.minecraft.nbt.NBTTagString");
        classMap.put("NMS_NBTTAGINT","net.minecraft.nbt.NBTTagInt");
        classMap.put("NMS_NBTTAGINTARRAY","net.minecraft.nbt.NBTTagIntArray");
        classMap.put("NMS_NBTTAGFLOAT","net.minecraft.nbt.NBTTagFloat");
        classMap.put("NMS_NBTTAGDOUBLE","net.minecraft.nbt.NBTTagDouble");
        classMap.put("NMS_NBTTAGLONG","net.minecraft.nbt.NBTTagLong");
        classMap.put("NMS_ITEMSTACK","net.minecraft.item.ItemStack");
        classMap.put("NMS_NBTTAGCOMPOUND","net.minecraft.nbt.NBTTagCompound");
        classMap.put("NMS_NBTTAGLIST","net.minecraft.nbt.NBTTagList");
        classMap.put("NMS_NBTCOMPRESSEDSTREAMTOOLS","net.minecraft.nbt.CompressedStreamTools");
        classMap.put("NMS_MOJANGSONPARSER","io.github.crucible.nbt.Crucible_JsonToNBT");
        classMap.put("NMS_TILEENTITY","net.minecraft.tileentity.TileEntity");
        classMap.put("NMS_WORLDSERVER","net.minecraft.world.WorldServer");
        classMap.put("NMS_MINECRAFTSERVER","net.minecraft.server.MinecraftServer");
        classMap.put("NMS_WORLD","net.minecraft.world.World");
        classMap.put("NMS_ENTITY","net.minecraft.entity.Entity");
        classMap.put("NMS_ENTITYTYPES","net.minecraft.entity.EntityList");
        classMap.put("NMS_REGISTRYMATERIALS","net.minecraft.util.RegistryNamespaced");
        classMap.put("NMS_GAMEPROFILESERIALIZER","net.minecraft.nbt.NBTUtil");
        classMap.put("NMS_IREGISTRY","net.minecraft.util.IRegistry");

        //Methods
        methodMap.put("COMPOUND_SET_FLOAT","func_74776_a");
        methodMap.put("COMPOUND_SET_STRING","func_74778_a");
        methodMap.put("COMPOUND_SET_INT","func_74768_a");
        methodMap.put("COMPOUND_SET_BYTEARRAY","func_74773_a");
        methodMap.put("COMPOUND_SET_INTARRAY","func_74783_a");
        methodMap.put("COMPOUND_SET_LONG","func_74772_a");
        methodMap.put("COMPOUND_SET_SHORT","func_74777_a");
        methodMap.put("COMPOUND_SET_BYTE","func_74774_a");
        methodMap.put("COMPOUND_SET_DOUBLE","func_74780_a");
        methodMap.put("COMPOUND_SET_BOOLEAN","func_74757_a");
        methodMap.put("COMPOUND_MERGE","merge");//Only present on Crucible
        methodMap.put("COMPOUND_SET","func_74782_a");
        methodMap.put("COMPOUND_GET","func_74781_a");
        methodMap.put("COMPOUND_GET_LIST","func_150295_c");
        methodMap.put("COMPOUND_OWN_TYPE","func_74732_a");
        methodMap.put("COMPOUND_GET_FLOAT","func_74760_g");
        methodMap.put("COMPOUND_GET_STRING","func_74779_i");
        methodMap.put("COMPOUND_GET_INT","func_74762_e");
        methodMap.put("COMPOUND_GET_BYTEARRAY","func_74770_j");
        methodMap.put("COMPOUND_GET_INTARRAY","func_74759_k");
        methodMap.put("COMPOUND_GET_LONG","func_74763_f");
        methodMap.put("COMPOUND_GET_SHORT","func_74765_d");
        methodMap.put("COMPOUND_GET_BYTE","func_74771_c");
        methodMap.put("COMPOUND_GET_DOUBLE","func_74769_h");
        methodMap.put("COMPOUND_GET_BOOLEAN","func_74767_n");
        methodMap.put("COMPOUND_GET_COMPOUND","func_74775_l");
        methodMap.put("COMPOUND_REMOVE_KEY","func_82580_o");
        methodMap.put("COMPOUND_HAS_KEY","func_74764_b");
        methodMap.put("COMPOUND_GET_KEYS","func_150296_c");
        methodMap.put("LISTCOMPOUND_GET_KEYS","func_150296_c");
        methodMap.put("NMSITEM_GETTAG","func_77978_p");
        methodMap.put("NMSITEM_SAVE","func_77955_b");
        methodMap.put("NMSITEM_CREATESTACK","func_77949_a");
        methodMap.put("ITEMSTACK_SET_TAG","func_77982_d");
        methodMap.put("LIST_SIZE","func_74745_c");
        methodMap.put("LEGACY_LIST_ADD","func_74742_a");
        methodMap.put("LIST_GET_STRING","func_150307_f");
        methodMap.put("LIST_GET_COMPOUND","func_150305_b");
        methodMap.put("LIST_GET","func_150305_b");
        methodMap.put("NMS_WORLD_GET_TILEENTITY_1_7_10","func_147438_o");
        methodMap.put("TILEENTITY_GET_NBT","func_145841_b");
        methodMap.put("TILEENTITY_SET_NBT","func_145839_a");
        methodMap.put("TILEENTITY_SET_NBT_LEGACY1151","func_145839_a");
        methodMap.put("NMS_ENTITY_GET_NBT","func_70109_d");
        methodMap.put("NMS_ENTITY_SET_NBT","func_70020_e");
        methodMap.put("NBTFILE_READ","func_74796_a");
        methodMap.put("NBTFILE_WRITE","func_74799_a");
        methodMap.put("PARSE_NBT","getTagFromJson");
        methodMap.put("GAMEPROFILE_DESERIALIZE","func_152459_a");

        //Crucible
        try {
            crucible_toString = Class.forName("net.minecraft.nbt.NBTTagCompound").getDeclaredMethod("crucible_toString");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getClassMappings(){
        return classMap;
    }

    public static Map<String, String> getMethodMapping(){
        return methodMap;
    }

    public static String toString(Object nbtTagCompound) {
        if(crucible_toString == null)
            throw new NbtApiException("Method not loaded! 'Forge1710Mappings.crucible_toString' ");
        try{
            return (String) crucible_toString.invoke(nbtTagCompound);
        }catch(Exception ex){
            throw new NbtApiException("Error while calling the method 'crucible_toString', from Forge1710Mappings. Passed Class: " + Forge1710Mappings.class, ex);
        }
    }

}
