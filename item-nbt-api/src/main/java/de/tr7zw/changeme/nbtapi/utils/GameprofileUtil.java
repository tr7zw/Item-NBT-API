package de.tr7zw.changeme.nbtapi.utils;

import java.util.UUID;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import de.tr7zw.changeme.nbtapi.NBTType;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBTCompoundList;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBT;
import de.tr7zw.changeme.nbtapi.iface.ReadableNBTList;

public class GameprofileUtil {

    @Nullable
    public static GameProfile readGameProfile(ReadableNBT arg) {
        String string = null;
        UUID uUID = null;
        if (arg.hasTag("Name") && arg.getType("Name") == NBTType.NBTTagString) {
            string = arg.getString("Name");
        }
        if (arg.hasTag("Id") && arg.getType("Id") == NBTType.NBTTagIntArray && arg.getIntArray("Id").length == 4) {
            uUID = arg.getUUID("Id");
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
            }
            return gameProfile;
        } catch (Throwable var11) {
            return null;
        }
    }

    public static ReadWriteNBT writeGameProfile(ReadWriteNBT arg, GameProfile gameProfile) {
        if (!(gameProfile.getName() == null || gameProfile.getName().isEmpty())) {
            arg.setString("Name", gameProfile.getName());
        }
        if (gameProfile.getId() != null) {
            arg.setUUID("Id", gameProfile.getId());
        }
        if (!gameProfile.getProperties().isEmpty()) {
            ReadWriteNBT compoundTag = arg.getOrCreateCompound("Properties");
            for (String string : gameProfile.getProperties().keySet()) {
                ReadWriteNBTCompoundList list = compoundTag.getCompoundList(string);
                for (com.mojang.authlib.properties.Property property : gameProfile.getProperties().get(string)) {
                    ReadWriteNBT tag = list.addCompound();
                    tag.setString("Value", property.getValue());
                    if (property.hasSignature()) {
                        tag.setString("Signature", property.getSignature());
                    }
                }
            }
        }
        return arg;
    }

}
