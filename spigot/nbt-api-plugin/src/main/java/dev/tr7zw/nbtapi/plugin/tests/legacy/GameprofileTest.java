package dev.tr7zw.nbtapi.plugin.tests.legacy;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTGameProfile;
import dev.tr7zw.nbtapi.NBTApiException;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class GameprofileTest implements Test {

	@Override
	public void test() throws Exception {
		UUID uuid = UUID.randomUUID();
		GameProfile profile = new GameProfile(uuid, "random");
		NBTCompound nbt = NBTGameProfile.toNBT(profile);
		profile = null;
		profile = NBTGameProfile.fromNBT(nbt);
		if (profile == null || !profile.getId().equals(uuid)) {
			throw new NBTApiException("Error when converting a GameProfile from/to NBT!");
		}
	}

}
