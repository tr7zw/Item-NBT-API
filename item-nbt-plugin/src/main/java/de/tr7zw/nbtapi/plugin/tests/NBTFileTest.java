package de.tr7zw.nbtapi.plugin.tests;

import java.io.File;
import java.nio.file.Files;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.iface.NBTFileHandle;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.plugin.NBTAPI;

public class NBTFileTest implements Test {

    @Override
    public void test() throws Exception {
        NBTAPI.getInstance().getDataFolder().mkdirs();
        File testFile = new File(NBTAPI.getInstance().getDataFolder(), "test.nbt");
        Files.deleteIfExists(testFile.toPath());
        NBTFileHandle file = NBT.getFileHandle(testFile);
        file.getOrCreateCompound("testcomp").setString("test1", "ok");
        ReadWriteNBT comp = file.getOrCreateCompound("testcomp");
        if (comp == null) {
            throw new NbtApiException("Error getting compound!");
        }
        comp.setString("test2", "ok");
        file.setLong("time", System.currentTimeMillis());
        file.setString("test", "test");
        ReadWriteNBT chunks = file.getOrCreateCompound("chunks");
        ReadWriteNBT chunk = chunks.getOrCreateCompound("somechunk");
        ReadWriteNBT block = chunk.getOrCreateCompound("someblock");
        block.setString("type", "wool");
        file.save();

        if (!"wool".equals(block.getString("type"))) {
            throw new NbtApiException("SubCompounds did not work!");
        }

        NBTFileHandle fileLoaded = NBT.getFileHandle(testFile);
        if (!fileLoaded.getString("test").equals("test")) {
            throw new NbtApiException("Wasn't able to load NBT File with the correct content!");
        }
        Files.deleteIfExists(fileLoaded.getFile().toPath());
        // String
        String str = fileLoaded.toString();
        ReadWriteNBT rebuild = NBT.parseNBT(str);
        if (!str.equals(rebuild.toString())) {
            throw new NbtApiException("Wasn't able to parse NBT from a String!");
        }

        ReadWriteNBT dummy = NBT.createNBTObject();
        dummy.setString("test1", "key1");
        NBT.writeFile(testFile, dummy);
        dummy = NBT.createNBTObject();
        dummy.setString("test2", "key2");
        NBT.writeFile(testFile, dummy);
        dummy = NBT.readFile(testFile);
        if (dummy.hasTag("test1") || !dummy.getString("test2").equals("key2")) {
            throw new NbtApiException("Wasn't able to save NBT File with the correct content!");
        }
        Files.deleteIfExists(fileLoaded.getFile().toPath());
    }

}
