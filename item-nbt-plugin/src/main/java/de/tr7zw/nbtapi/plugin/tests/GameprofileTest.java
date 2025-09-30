package de.tr7zw.nbtapi.plugin.tests;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;

public class GameprofileTest implements Test {

    @Override
    public void test() throws Exception {
        UUID uuid = UUID.randomUUID();
        GameProfile profile = new GameProfile(uuid, "random");
        ReadWriteNBT nbt = NBT.gameProfileToNBT(profile);
        GameProfile profile2 = NBT.gameProfileFromNBT(nbt);
        if (profile == null || !profile.equals(profile2)) {
            throw new NbtApiException("Error when converting a GameProfile from/to NBT!");
        }
    }

}
