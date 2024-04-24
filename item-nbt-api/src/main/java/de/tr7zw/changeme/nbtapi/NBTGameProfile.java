package de.tr7zw.changeme.nbtapi;

import com.mojang.authlib.GameProfile;

import de.tr7zw.changeme.nbtapi.utils.GameprofileUtil;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ObjectCreator;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

public class NBTGameProfile {

    /**
     * Convert a GameProfile to NBT. The NBT then can be modified or be stored
     * 
     * @param profile
     * @return A NBTContainer with all the GameProfile data
     */
    public static NBTCompound toNBT(GameProfile profile) {
        if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R4)) {
            return (NBTCompound) GameprofileUtil.writeGameProfile(NBT.createNBTObject(), profile);
        }
        return new NBTContainer(ReflectionMethod.GAMEPROFILE_SERIALIZE.run(null,
                ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(), profile));
    }

    /**
     * Reconstructs a GameProfile from a NBTCompound
     * 
     * @param compound Has to contain GameProfile data
     * @return The reconstructed GameProfile
     */
    public static GameProfile fromNBT(NBTCompound compound) {
        if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R4)) {
            return GameprofileUtil.readGameProfile(compound);
        }
        return (GameProfile) ReflectionMethod.GAMEPROFILE_DESERIALIZE.run(null,
                NBTReflectionUtil.getToCompount(compound.getCompound(), compound));
    }

}
