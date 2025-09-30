package de.tr7zw.changeme.nbtapi.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;

import de.tr7zw.changeme.nbtapi.NBTType;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTCompoundList;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBTList;

public class GameprofileUtil {

    private static Method GET_NAME = null;
    private static Method GET_ID = null;
    private static Method GET_VALUE = null;
    private static Method GET_SIGNATURE = null;
    private static Method GET_PROPERTY_NAME = null;
    private static Method GET_PROPERTIES = null;
    
    static {
        if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R6)) {
            try {
                GET_NAME = GameProfile.class.getDeclaredMethod("name");
                GET_ID = GameProfile.class.getDeclaredMethod("id");
                GET_VALUE = com.mojang.authlib.properties.Property.class.getDeclaredMethod("value");
                GET_SIGNATURE = com.mojang.authlib.properties.Property.class.getDeclaredMethod("signature");
                GET_PROPERTY_NAME = com.mojang.authlib.properties.Property.class.getDeclaredMethod("name");
                GET_PROPERTIES = GameProfile.class.getDeclaredMethod("properties");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Nullable
    public static GameProfile readGameProfile(ReadableNBT arg) {
        String string = null;
        UUID uUID = null;
        if (arg.hasTag("Name") && arg.getType("Name") == NBTType.NBTTagString) {
            string = arg.getString("Name");
        } else if (arg.hasTag("name") && arg.getType("name") == NBTType.NBTTagString) {
            string = arg.getString("name");
        }
        if (arg.hasTag("Id") && arg.getType("Id") == NBTType.NBTTagIntArray && arg.getIntArray("Id").length == 4) {
            uUID = arg.getUUID("Id");
        } else if (arg.hasTag("id") && arg.getType("id") == NBTType.NBTTagIntArray
                && arg.getIntArray("id").length == 4) {
            uUID = arg.getUUID("id");
        }
        try {
            GameProfile gameProfile = new GameProfile(uUID, string);
            if (arg.hasTag("Properties") && arg.getType("Properties") == NBTType.NBTTagCompound) {
                ReadableNBT compoundTag = arg.getCompound("Properties");
                for (String string2 : compoundTag.getKeys()) {
                    ReadableNBTList<ReadWriteNBT> listTag = compoundTag.getCompoundList(string);
                    for (int i = 0; i < listTag.size(); ++i) {
                        ReadableNBT compoundTag2 = listTag.get(i);
                        String string3 = compoundTag2.getString("Value");
                        if (compoundTag2.hasTag("Signature")
                                && compoundTag2.getType("Signature") == NBTType.NBTTagString) {
                            gameProfile.getProperties().put(string2, new com.mojang.authlib.properties.Property(string2,
                                    string3, compoundTag2.getString("Signature")));
                        } else {
                            gameProfile.getProperties().put(string2,
                                    new com.mojang.authlib.properties.Property(string2, string3));
                        }
                    }
                }
            } else if (arg.getType("properties") == NBTType.NBTTagList) { // 1.20.5+
                ReadableNBTList<ReadWriteNBT> listTag = arg.getCompoundList("properties");
                for (int i = 0; i < listTag.size(); ++i) {
                    ReadableNBT compoundTag2 = listTag.get(i);
                    String string2 = compoundTag2.getString("name");
                    String string3 = compoundTag2.getString("value");
                    if (compoundTag2.hasTag("signature")
                            && compoundTag2.getType("signature") == NBTType.NBTTagString) {
                        gameProfile.getProperties().put(string2, new com.mojang.authlib.properties.Property(string2,
                                string3, compoundTag2.getString("signature")));
                    } else {
                        gameProfile.getProperties().put(string2,
                                new com.mojang.authlib.properties.Property(string2, string3));
                    }
                }
            }
            return gameProfile;
        } catch (Throwable var11) {
            return null;
        }
    }

    public static ReadWriteNBT writeGameProfile(ReadWriteNBT arg, GameProfile gameProfile) {
        String name = getName(gameProfile);
        if (!(name == null || name.isEmpty())) {
            String nameKey = MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R4) ? "name" : "Name";
            arg.setString(nameKey, getName(gameProfile));
        }
        UUID id = getId(gameProfile);
        if (id != null) {
            String idKey = MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R4) ? "id" : "Id";
            arg.setUUID(idKey, id);
        }
        PropertyMap properties = getProperties(gameProfile);
        if (!properties.isEmpty()) {
            if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R4)) {
                    ReadWriteNBTCompoundList list = arg.getCompoundList("properties");
                    for (com.mojang.authlib.properties.Property property : properties.values()) {
                        ReadWriteNBT tag = list.addCompound();
                        tag.setString("name", getPropertyName(property));
                        tag.setString("value", getValue(property));
                        if (property.hasSignature()) {
                            tag.setString("signature", getSignature(property));
                        }
                }
            } else {
                ReadWriteNBT compoundTag = arg.getOrCreateCompound("Properties");
                for (String string : gameProfile.getProperties().keySet()) {
                    ReadWriteNBTCompoundList list = compoundTag.getCompoundList(string);
                    for (com.mojang.authlib.properties.Property property : properties.get(string)) {
                        ReadWriteNBT tag = list.addCompound();
                        tag.setString("Value", getValue(property));
                        if (property.hasSignature()) {
                            tag.setString("Signature", getSignature(property));
                        }
                    }
                }
            }
        }
        return arg;
    }
    
    private static String getName(GameProfile profile) {
        if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R6)) {
            try {
                return GET_NAME.invoke(profile).toString();
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new NbtApiException("Failed to get GameProfile name via reflection", e);
            }
        } else {
            return profile.getName();
        }
    }
    
    private static UUID getId(GameProfile profile) {
        if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R6)) {
            try {
                return (UUID) GET_ID.invoke(profile);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new NbtApiException("Failed to get GameProfile id via reflection", e);
            }
        } else {
            return profile.getId();
        }
    }
    
    private static String getValue(com.mojang.authlib.properties.Property property) {
        if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R6)) {
            try {
                return GET_VALUE.invoke(property).toString();
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new NbtApiException("Failed to get Property value via reflection", e);
            }
        } else {
            return property.getValue();
        }
    }
    
    private static String getSignature(com.mojang.authlib.properties.Property property) {
        if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R6)) {
            try {
                return GET_SIGNATURE.invoke(property).toString();
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new NbtApiException("Failed to get Property signature via reflection", e);
            }
        } else {
            return property.getSignature();
        }
    }
    
    private static String getPropertyName(com.mojang.authlib.properties.Property property) {
        if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R6)) {
            try {
                return GET_PROPERTY_NAME.invoke(property).toString();
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new NbtApiException("Failed to get Property name via reflection", e);
            }
        } else {
            return property.getName();
        }
    }
    
    private static PropertyMap getProperties(GameProfile profile) {
        if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R6)) {
            try {
                return (PropertyMap) GET_PROPERTIES.invoke(profile);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new NbtApiException("Failed to get GameProfile properties via reflection", e);
            }
        } else {
            return profile.getProperties();
        }
    }

}
