package de.tr7zw.nbtapi;

import org.apache.commons.lang.NotImplementedException;

import com.mojang.authlib.GameProfile;

@Deprecated
public class NBTGameProfile {

	/**
	 * Convert a GameProfile to NBT. The NBT then can be modified or be stored
	 * 
	 * @param profile
	 * @return A NBTContainer with all the GameProfile data
	 */
	public static NBTCompound toNBT(GameProfile profile) {
	    throw new NotImplementedException();
	}
	
	/**
	 * Reconstructs a GameProfile from a NBTCompound
	 * 
	 * @param compound Has to contain GameProfile data
	 * @return The reconstructed GameProfile
	 */
	public static GameProfile fromNBT(NBTCompound compound) {
	    throw new NotImplementedException();
	}
	
}
