package de.tr7zw.changeme.nbtapi.utils.nmsmappings;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;

/**
 * This class caches method reflections, keeps track of method name changes between versions and allows early checking for problems
 * 
 * @author tr7zw
 *
 */
@SuppressWarnings("javadoc")
public enum ReflectionMethod {

    COMPOUND_SET_FLOAT(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, float.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setFloat"), new Since(MinecraftVersion.MC1_18_R1, "putFloat(java.lang.String,float)")),
    COMPOUND_SET_STRING(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setString"), new Since(MinecraftVersion.MC1_18_R1, "putString(java.lang.String,java.lang.String)")),
    COMPOUND_SET_INT(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, int.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setInt"), new Since(MinecraftVersion.MC1_18_R1, "putInt(java.lang.String,int)")),
    COMPOUND_SET_BYTEARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, byte[].class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setByteArray"), new Since(MinecraftVersion.MC1_18_R1, "putByteArray(java.lang.String,byte[])")),
    COMPOUND_SET_INTARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, int[].class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setIntArray"), new Since(MinecraftVersion.MC1_18_R1, "putIntArray(java.lang.String,int[])")),
    COMPOUND_SET_LONG(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, long.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setLong"), new Since(MinecraftVersion.MC1_18_R1, "putLong(java.lang.String,long)")),
    COMPOUND_SET_SHORT(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, short.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setShort"), new Since(MinecraftVersion.MC1_18_R1, "putShort(java.lang.String,short)")),
    COMPOUND_SET_BYTE(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, byte.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setByte"), new Since(MinecraftVersion.MC1_18_R1, "putByte(java.lang.String,byte)")),
    COMPOUND_SET_DOUBLE(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, double.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setDouble"), new Since(MinecraftVersion.MC1_18_R1, "putDouble(java.lang.String,double)")),
    COMPOUND_SET_BOOLEAN(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, boolean.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setBoolean"), new Since(MinecraftVersion.MC1_18_R1, "putBoolean(java.lang.String,boolean)")),
    COMPOUND_SET_UUID(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, UUID.class}, MinecraftVersion.MC1_16_R1, new Since(MinecraftVersion.MC1_16_R1, "a"), new Since(MinecraftVersion.MC1_18_R1, "putUUID(java.lang.String,java.util.UUID)")),
    COMPOUND_MERGE(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_8_R3, new Since(MinecraftVersion.MC1_8_R3, "a"), new Since(MinecraftVersion.MC1_18_R1, "put(java.lang.String,net.minecraft.nbt.Tag)")),
    COMPOUND_SET(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, ClassWrapper.NMS_NBTBASE.getClazz()}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "set"), new Since(MinecraftVersion.MC1_18_R1, "put(java.lang.String,net.minecraft.nbt.Tag)")),
    COMPOUND_GET(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "get"), new Since(MinecraftVersion.MC1_18_R1, "get(java.lang.String)")),
    COMPOUND_GET_LIST(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class, int.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getList"), new Since(MinecraftVersion.MC1_18_R1, "getList(java.lang.String,int)")),
    COMPOUND_OWN_TYPE(ClassWrapper.NMS_NBTBASE, new Class[]{}, MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getTypeId")), // Only needed for 1.7.10 getType
    
    COMPOUND_GET_FLOAT(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getFloat"), new Since(MinecraftVersion.MC1_18_R1, "getFloat(java.lang.String)")),
    COMPOUND_GET_STRING(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getString"), new Since(MinecraftVersion.MC1_18_R1, "getString(java.lang.String)")),
    COMPOUND_GET_INT(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getInt"), new Since(MinecraftVersion.MC1_18_R1, "getInt(java.lang.String)")),
    COMPOUND_GET_BYTEARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getByteArray"), new Since(MinecraftVersion.MC1_18_R1, "getByteArray(java.lang.String)")),
    COMPOUND_GET_INTARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getIntArray"), new Since(MinecraftVersion.MC1_18_R1, "getIntArray(java.lang.String)")),
    COMPOUND_GET_LONG(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getLong"), new Since(MinecraftVersion.MC1_18_R1, "getLong(java.lang.String)")),
    COMPOUND_GET_SHORT(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getShort"), new Since(MinecraftVersion.MC1_18_R1, "getShort(java.lang.String)")),
    COMPOUND_GET_BYTE(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getByte"), new Since(MinecraftVersion.MC1_18_R1, "getByte(java.lang.String)")),
    COMPOUND_GET_DOUBLE(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getDouble"), new Since(MinecraftVersion.MC1_18_R1, "getDouble(java.lang.String)")),
    COMPOUND_GET_BOOLEAN(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getBoolean"), new Since(MinecraftVersion.MC1_18_R1, "getBoolean(java.lang.String)")),
    COMPOUND_GET_UUID(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_16_R1, new Since(MinecraftVersion.MC1_16_R1, "a"), new Since(MinecraftVersion.MC1_18_R1, "getUUID(java.lang.String)")),
    COMPOUND_GET_COMPOUND(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getCompound"), new Since(MinecraftVersion.MC1_18_R1, "getCompound(java.lang.String)")),
    
    NMSITEM_GETTAG(ClassWrapper.NMS_ITEMSTACK, new Class[] {}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getTag"), new Since(MinecraftVersion.MC1_18_R1, "getTag()")),
    NMSITEM_SAVE(ClassWrapper.NMS_ITEMSTACK, new Class[] {ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "save"), new Since(MinecraftVersion.MC1_18_R1, "save(net.minecraft.nbt.CompoundTag)")),
    NMSITEM_CREATESTACK(ClassWrapper.NMS_ITEMSTACK, new Class[] {ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_10_R1, new Since(MinecraftVersion.MC1_7_R4, "createStack")),
    
    COMPOUND_REMOVE_KEY(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "remove"), new Since(MinecraftVersion.MC1_18_R1, "remove(java.lang.String)")),
    COMPOUND_HAS_KEY(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "hasKey"), new Since(MinecraftVersion.MC1_18_R1, "contains(java.lang.String)")),
    COMPOUND_GET_TYPE(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{String.class}, MinecraftVersion.MC1_8_R3, new Since(MinecraftVersion.MC1_8_R3, "b"), new Since(MinecraftVersion.MC1_9_R1, "d"), new Since(MinecraftVersion.MC1_15_R1, "e"), new Since(MinecraftVersion.MC1_16_R1, "d"), new Since(MinecraftVersion.MC1_18_R1, "getTagType(java.lang.String)")),
    COMPOUND_GET_KEYS(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "c"), new Since(MinecraftVersion.MC1_13_R1, "getKeys"), new Since(MinecraftVersion.MC1_18_R1, "getAllKeys()")),

    LISTCOMPOUND_GET_KEYS(ClassWrapper.NMS_NBTTAGCOMPOUND, new Class[]{}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "c"), new Since(MinecraftVersion.MC1_13_R1, "getKeys"), new Since(MinecraftVersion.MC1_18_R1, "getAllKeys()")), // FIXME ?!?
    LIST_REMOVE_KEY(ClassWrapper.NMS_NBTTAGLIST, new Class[]{int.class}, MinecraftVersion.MC1_8_R3, new Since(MinecraftVersion.MC1_8_R3, "a"), new Since(MinecraftVersion.MC1_9_R1, "remove"), new Since(MinecraftVersion.MC1_18_R1, "remove(int)")),
    LIST_SIZE(ClassWrapper.NMS_NBTTAGLIST, new Class[]{}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "size"), new Since(MinecraftVersion.MC1_18_R1, "size()")),
    LIST_SET(ClassWrapper.NMS_NBTTAGLIST, new Class[]{int.class, ClassWrapper.NMS_NBTBASE.getClazz()}, MinecraftVersion.MC1_8_R3, new Since(MinecraftVersion.MC1_8_R3, "a"), new Since(MinecraftVersion.MC1_13_R1, "set"), new Since(MinecraftVersion.MC1_18_R1, "setTag(int,net.minecraft.nbt.Tag)")),
    LEGACY_LIST_ADD(ClassWrapper.NMS_NBTTAGLIST, new Class[]{ClassWrapper.NMS_NBTBASE.getClazz()}, MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_13_R2, new Since(MinecraftVersion.MC1_7_R4, "add")),
    LIST_ADD(ClassWrapper.NMS_NBTTAGLIST, new Class[]{int.class, ClassWrapper.NMS_NBTBASE.getClazz()}, MinecraftVersion.MC1_14_R1, new Since(MinecraftVersion.MC1_14_R1, "add"), new Since(MinecraftVersion.MC1_18_R1, "addTag(int,net.minecraft.nbt.Tag)")),
    LIST_GET_STRING(ClassWrapper.NMS_NBTTAGLIST, new Class[]{int.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getString"), new Since(MinecraftVersion.MC1_18_R1, "getString(int)")),
    LIST_GET_COMPOUND(ClassWrapper.NMS_NBTTAGLIST, new Class[]{int.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "get"), new Since(MinecraftVersion.MC1_18_R1, "getCompound(int)")),
    LIST_GET(ClassWrapper.NMS_NBTTAGLIST, new Class[]{int.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "get"), new Since(MinecraftVersion.MC1_8_R3, "g"), new Since(MinecraftVersion.MC1_9_R1, "h"), new Since(MinecraftVersion.MC1_12_R1, "i"), new Since(MinecraftVersion.MC1_13_R1, "get"), new Since(MinecraftVersion.MC1_18_R1, "get(int)")),
    
    ITEMSTACK_SET_TAG(ClassWrapper.NMS_ITEMSTACK, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setTag"), new Since(MinecraftVersion.MC1_18_R1, "setTag(net.minecraft.nbt.CompoundTag)")),
    ITEMSTACK_NMSCOPY(ClassWrapper.CRAFT_ITEMSTACK, new Class[]{ItemStack.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "asNMSCopy")),
    ITEMSTACK_BUKKITMIRROR(ClassWrapper.CRAFT_ITEMSTACK, new Class[]{ClassWrapper.NMS_ITEMSTACK.getClazz()}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "asCraftMirror")),

    CRAFT_WORLD_GET_HANDLE(ClassWrapper.CRAFT_WORLD, new Class[]{}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getHandle")),
    NMS_WORLD_GET_TILEENTITY(ClassWrapper.NMS_WORLDSERVER, new Class[]{ClassWrapper.NMS_BLOCKPOSITION.getClazz()}, MinecraftVersion.MC1_8_R3, new Since(MinecraftVersion.MC1_8_R3, "getTileEntity"), new Since(MinecraftVersion.MC1_18_R1, "getBlockEntity(net.minecraft.core.BlockPos)")),
    NMS_WORLD_SET_TILEENTITY(ClassWrapper.NMS_WORLDSERVER, new Class[]{ClassWrapper.NMS_BLOCKPOSITION.getClazz(), ClassWrapper.NMS_TILEENTITY.getClazz()}, MinecraftVersion.MC1_8_R3, MinecraftVersion.MC1_16_R3, new Since(MinecraftVersion.MC1_8_R3, "setTileEntity")),
    NMS_WORLD_REMOVE_TILEENTITY(ClassWrapper.NMS_WORLDSERVER, new Class[]{ClassWrapper.NMS_BLOCKPOSITION.getClazz()}, MinecraftVersion.MC1_8_R3, MinecraftVersion.MC1_17_R1, new Since(MinecraftVersion.MC1_8_R3, "t"), new Since(MinecraftVersion.MC1_9_R1, "s"), new Since(MinecraftVersion.MC1_13_R1, "n"),  new Since(MinecraftVersion.MC1_14_R1, "removeTileEntity")),

    NMS_WORLD_GET_TILEENTITY_1_7_10(ClassWrapper.NMS_WORLDSERVER, new Class[]{int.class, int.class, int.class}, MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getTileEntity")),

    TILEENTITY_LOAD_LEGACY191(ClassWrapper.NMS_TILEENTITY, new Class[]{ClassWrapper.NMS_MINECRAFTSERVER.getClazz(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_9_R1, MinecraftVersion.MC1_9_R1, new Since(MinecraftVersion.MC1_9_R1, "a")), //FIXME: No Spigot mapping!
    TILEENTITY_LOAD_LEGACY183(ClassWrapper.NMS_TILEENTITY, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_8_R3, MinecraftVersion.MC1_9_R2, new Since(MinecraftVersion.MC1_8_R3, "c"), new Since(MinecraftVersion.MC1_9_R1, "a"), new Since(MinecraftVersion.MC1_9_R2, "c")), //FIXME: No Spigot mapping!
    TILEENTITY_LOAD_LEGACY1121(ClassWrapper.NMS_TILEENTITY, new Class[]{ClassWrapper.NMS_WORLD.getClazz(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_10_R1, MinecraftVersion.MC1_12_R1, new Since(MinecraftVersion.MC1_10_R1, "a"), new Since(MinecraftVersion.MC1_12_R1, "create")),
    TILEENTITY_LOAD_LEGACY1151(ClassWrapper.NMS_TILEENTITY, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_13_R1, MinecraftVersion.MC1_15_R1, new Since(MinecraftVersion.MC1_12_R1, "create")),
    TILEENTITY_LOAD(ClassWrapper.NMS_TILEENTITY, new Class[]{ClassWrapper.NMS_IBLOCKDATA.getClazz(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_16_R1, MinecraftVersion.MC1_16_R3, new Since(MinecraftVersion.MC1_16_R1, "create")),
	
    TILEENTITY_GET_NBT(ClassWrapper.NMS_TILEENTITY, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_17_R1, new Since(MinecraftVersion.MC1_7_R4, "b"), new Since(MinecraftVersion.MC1_9_R1, "save")),
    TILEENTITY_GET_NBT_1181(ClassWrapper.NMS_TILEENTITY, new Class[]{}, MinecraftVersion.MC1_18_R1, new Since(MinecraftVersion.MC1_18_R1, "saveWithId()")),
    TILEENTITY_SET_NBT_LEGACY1151(ClassWrapper.NMS_TILEENTITY, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_15_R1, new Since(MinecraftVersion.MC1_7_R4, "a"), new Since(MinecraftVersion.MC1_12_R1, "load")),
    TILEENTITY_SET_NBT_LEGACY1161(ClassWrapper.NMS_TILEENTITY, new Class[]{ClassWrapper.NMS_IBLOCKDATA.getClazz(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_16_R1, MinecraftVersion.MC1_16_R3, new Since(MinecraftVersion.MC1_16_R1, "load")),
    TILEENTITY_SET_NBT(ClassWrapper.NMS_TILEENTITY, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_17_R1, new Since(MinecraftVersion.MC1_16_R1, "load"), new Since(MinecraftVersion.MC1_18_R1, "load(net.minecraft.nbt.CompoundTag)")),
    TILEENTITY_GET_BLOCKDATA(ClassWrapper.NMS_TILEENTITY, new Class[]{}, MinecraftVersion.MC1_16_R1, new Since(MinecraftVersion.MC1_16_R1, "getBlock"), new Since(MinecraftVersion.MC1_18_R1, "getBlockState()")),

    CRAFT_ENTITY_GET_HANDLE(ClassWrapper.CRAFT_ENTITY, new Class[]{}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getHandle")),
    NMS_ENTITY_SET_NBT(ClassWrapper.NMS_ENTITY, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "f"), new Since(MinecraftVersion.MC1_16_R1, "load"), new Since(MinecraftVersion.MC1_18_R1, "load(net.minecraft.nbt.CompoundTag)")),
    NMS_ENTITY_GET_NBT(ClassWrapper.NMS_ENTITY, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "e"), new Since(MinecraftVersion.MC1_12_R1, "save"), new Since(MinecraftVersion.MC1_18_R1, "saveWithoutId(net.minecraft.nbt.CompoundTag)")),
    NMS_ENTITY_GETSAVEID(ClassWrapper.NMS_ENTITY, new Class[]{}, MinecraftVersion.MC1_14_R1,new Since(MinecraftVersion.MC1_14_R1, "getSaveID"), new Since(MinecraftVersion.MC1_18_R1, "getEncodeId()")),

    NBTFILE_READ(ClassWrapper.NMS_NBTCOMPRESSEDSTREAMTOOLS, new Class[]{InputStream.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "a"), new Since(MinecraftVersion.MC1_18_R1, "readCompressed(java.io.InputStream)")),
    NBTFILE_WRITE(ClassWrapper.NMS_NBTCOMPRESSEDSTREAMTOOLS, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), OutputStream.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "a"), new Since(MinecraftVersion.MC1_18_R1, "writeCompressed(net.minecraft.nbt.CompoundTag,java.io.OutputStream)")),
    
    PARSE_NBT(ClassWrapper.NMS_MOJANGSONPARSER, new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "parse"), new Since(MinecraftVersion.MC1_18_R1, "parseTag(java.lang.String)")),
    REGISTRY_KEYSET (ClassWrapper.NMS_REGISTRYSIMPLE, new Class[]{}, MinecraftVersion.MC1_11_R1, MinecraftVersion.MC1_13_R1, new Since(MinecraftVersion.MC1_11_R1, "keySet")),
    REGISTRY_GET (ClassWrapper.NMS_REGISTRYSIMPLE, new Class[]{Object.class}, MinecraftVersion.MC1_11_R1, MinecraftVersion.MC1_13_R1, new Since(MinecraftVersion.MC1_11_R1, "get")),
    REGISTRY_SET (ClassWrapper.NMS_REGISTRYSIMPLE, new Class[]{Object.class, Object.class}, MinecraftVersion.MC1_11_R1, MinecraftVersion.MC1_13_R1, new Since(MinecraftVersion.MC1_11_R1, "a")), //FIXME: No Spigot mapping!
    REGISTRY_GET_INVERSE (ClassWrapper.NMS_REGISTRYMATERIALS, new Class[]{Object.class}, MinecraftVersion.MC1_11_R1, MinecraftVersion.MC1_13_R1, new Since(MinecraftVersion.MC1_11_R1, "b")), //FIXME: No Spigot mapping!
    REGISTRYMATERIALS_KEYSET (ClassWrapper.NMS_REGISTRYMATERIALS, new Class[]{}, MinecraftVersion.MC1_13_R1, MinecraftVersion.MC1_17_R1, new Since(MinecraftVersion.MC1_13_R1, "keySet")),
    REGISTRYMATERIALS_GET (ClassWrapper.NMS_REGISTRYMATERIALS, new Class[]{ClassWrapper.NMS_MINECRAFTKEY.getClazz()}, MinecraftVersion.MC1_13_R1, MinecraftVersion.MC1_17_R1,  new Since(MinecraftVersion.MC1_13_R1, "get")),
    REGISTRYMATERIALS_GETKEY (ClassWrapper.NMS_REGISTRYMATERIALS, new Class[]{Object.class}, MinecraftVersion.MC1_13_R2, MinecraftVersion.MC1_17_R1, new Since(MinecraftVersion.MC1_13_R2, "getKey")),

    GAMEPROFILE_DESERIALIZE (ClassWrapper.NMS_GAMEPROFILESERIALIZER, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "deserialize"), new Since(MinecraftVersion.MC1_18_R1, "readGameProfile(net.minecraft.nbt.CompoundTag)")),
    GAMEPROFILE_SERIALIZE (ClassWrapper.NMS_GAMEPROFILESERIALIZER, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), ClassWrapper.GAMEPROFILE.getClazz()}, MinecraftVersion.MC1_8_R3, new Since(MinecraftVersion.MC1_8_R3, "serialize"), new Since(MinecraftVersion.MC1_18_R1, "writeGameProfile(net.minecraft.nbt.CompoundTag,com.mojang.authlib.GameProfile)")),
    
    CRAFT_PERSISTENT_DATA_CONTAINER_TO_TAG (ClassWrapper.CRAFT_PERSISTENTDATACONTAINER, new Class[]{}, MinecraftVersion.MC1_14_R1, new Since(MinecraftVersion.MC1_14_R1, "toTagCompound")),
    CRAFT_PERSISTENT_DATA_CONTAINER_GET_MAP (ClassWrapper.CRAFT_PERSISTENTDATACONTAINER, new Class[]{}, MinecraftVersion.MC1_14_R1, new Since(MinecraftVersion.MC1_14_R1, "getRaw")),
    CRAFT_PERSISTENT_DATA_CONTAINER_PUT_ALL (ClassWrapper.CRAFT_PERSISTENTDATACONTAINER, new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_14_R1, new Since(MinecraftVersion.MC1_14_R1, "putAll")),
    ;
    
    private MinecraftVersion removedAfter;
    private Since targetVersion;
    private Method method;
    private boolean loaded = false;
    private boolean compatible = false;
    private String methodName = null;
    private ClassWrapper parentClassWrapper;
    
    ReflectionMethod(ClassWrapper targetClass, Class<?>[] args, MinecraftVersion addedSince, MinecraftVersion removedAfter, Since... methodnames){
        this.removedAfter = removedAfter;
        this.parentClassWrapper = targetClass;
        //Special Case for Modded 1.7.10
        boolean specialCase = (MinecraftVersion.isForgePresent() && this.name().equals("COMPOUND_MERGE") && MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4); //COMPOUND_MERGE is only present on Crucible, not on vanilla 1.7.10
        if(!specialCase && (!MinecraftVersion.isAtLeastVersion(addedSince) || (this.removedAfter != null && MinecraftVersion.isNewerThan(removedAfter))))return;
        compatible = true;
    	MinecraftVersion server = MinecraftVersion.getVersion();
        Since target = methodnames[0];
        for(Since s : methodnames){
            if(s.version.getVersionId() <= server.getVersionId() && target.version.getVersionId() < s.version.getVersionId())
                target = s;
        }
        targetVersion = target;
        String targetMethodName = targetVersion.name;
        try{
            if (MinecraftVersion.isForgePresent() && MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4){
                targetMethodName = Forge1710Mappings.getMethodMapping().getOrDefault(this.name(), targetMethodName);
            } else if(targetVersion.version.isMojangMapping()){
                targetMethodName = MojangToMapping.getMapping().getOrDefault(targetClass.getMojangName() + "#" + targetVersion.name, "Unmapped" + targetVersion.name);
            }
            method = targetClass.getClazz().getDeclaredMethod(targetMethodName, args);
            method.setAccessible(true);
            loaded = true;
            methodName = targetVersion.name;
        }catch(NullPointerException | NoSuchMethodException | SecurityException ex){
            try{
                if(targetVersion.version.isMojangMapping())
                    targetMethodName = MojangToMapping.getMapping().getOrDefault(targetClass.getMojangName() + "#" + targetVersion.name, "Unmapped" + targetVersion.name);
                method = targetClass.getClazz().getMethod(targetMethodName, args);
                method.setAccessible(true);
                loaded = true;
                methodName = targetVersion.name;
            }catch(NullPointerException | NoSuchMethodException | SecurityException ex2){
                MinecraftVersion.getLogger().warning("[NBTAPI] Unable to find the method '" + targetMethodName + "' in '" + (targetClass.getClazz() == null ? targetClass.getMojangName() : targetClass.getClazz().getSimpleName()) + "' Args: " + Arrays.toString(args) + " Enum: " + this); //NOSONAR This gets loaded before the logger is loaded
            }
        }
    }
    
    ReflectionMethod(ClassWrapper targetClass, Class<?>[] args, MinecraftVersion addedSince, Since... methodnames){
    	this(targetClass, args, addedSince, null, methodnames);
    }
    
    /**
     * Runs the method on a given target object using the given args.
     * 
     * @param target
     * @param args
     * @return Value returned by the method
     */
    public Object run(Object target, Object... args){
        if(method == null)
        	throw new NbtApiException("Method not loaded! '" + this + "'");
    	try{
            return method.invoke(target, args);
        }catch(Exception ex){
            throw new NbtApiException("Error while calling the method '" + methodName + "', loaded: " + loaded + ", Enum: " + this + " Passed Class: " + target.getClass(), ex);
        }
    }
    
    /**
     * @return The MethodName, used in this Minecraft Version
     */
    public String getMethodName() {
    	return methodName;
    }
    
    /**
     * @return Has this method been linked
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * @return Is this method available in this Minecraft Version
     */
    public boolean isCompatible() {
        return compatible;
    }
    
    public Since getSelectedVersionInfo() {
        return targetVersion;
    }
    
    /**
     * @return Get Wrapper of the parent class
     */
    public ClassWrapper getParentClassWrapper() {
        return parentClassWrapper;
    }

    public static class Since{
        public final MinecraftVersion version;
        public final String name;
        public Since(MinecraftVersion version, String name) {
            this.version = version;
            this.name = name;
        }
    }
    
}
