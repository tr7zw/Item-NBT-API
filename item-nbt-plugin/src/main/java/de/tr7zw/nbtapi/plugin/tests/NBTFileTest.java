package de.tr7zw.nbtapi.plugin.tests;

import java.io.File;
import java.nio.file.Files;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTFile;
import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.nbtapi.plugin.NBTAPI;

public class NBTFileTest implements Test{

	@Override
	public void test() throws Exception {
		NBTAPI.getInstance().getDataFolder().mkdirs();
		NBTFile file = new NBTFile(new File(NBTAPI.getInstance().getDataFolder(), "test.nbt"));
		file.addCompound("testcomp").setString("test1", "ok");
		NBTCompound comp = file.getCompound("testcomp");
		comp.setString("test2", "ok");
		file.setLong("time", System.currentTimeMillis());
		file.setString("test", "test");
		file.save();
		
		NBTFile fileLoaded = new NBTFile(new File(NBTAPI.getInstance().getDataFolder(), "test.nbt"));
		if(!fileLoaded.getString("test").equals("test")) {
			throw new NbtApiException("Wasn't able to load NBT File with the correct content!");
		}
		Files.deleteIfExists(fileLoaded.getFile().toPath());
		//String
		String str = fileLoaded.asNBTString();
		NBTContainer rebuild = new NBTContainer(str);
		if (!str.equals(rebuild.asNBTString())) {
			throw new NbtApiException("Wasn't able to parse NBT from a String!");
		}
	}

}
