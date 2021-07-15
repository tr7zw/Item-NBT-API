package dev.tr7zw.nbtapi;

import com.mojang.authlib.GameProfile;

import dev.tr7zw.nbtapi.utils.nmsmappings.ObjectCreator;
import dev.tr7zw.nbtapi.utils.nmsmappings.ReflectionMethod;

public class NBTGameProfile {

	/**
	 * Convert a GameProfile to NBT. The NBT then can be modified or be stored
	 * 
	 * @param profile
	 * @return A NBTContainer with all the GameProfile data
	 */
	public static NBTCompound toNBT(GameProfile profile) {
		return new NBTContainer(ReflectionMethod.GAMEPROFILE_SERIALIZE.run(null, ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(), profile));
	}
	
	/**
	 * Reconstructs a GameProfile from a NBTCompound
	 * 
	 * @param compound Has to contain GameProfile data
	 * @return The reconstructed GameProfile
	 */
	public static GameProfile fromNBT(NBTCompound compound) {
		return (GameProfile) ReflectionMethod.GAMEPROFILE_DESERIALIZE.run(null, NBTReflectionUtil.gettoCompount(compound.getCompound(), compound));
	}
	
}
