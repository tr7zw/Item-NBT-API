package de.tr7zw.changeme.nbtapi.data;

import java.io.File;
import java.io.IOException;

import dev.tr7zw.nbtapi.NBTCompound;
import dev.tr7zw.nbtapi.NBTFile;
import dev.tr7zw.nbtapi.NbtApiException;

public class PlayerData {

	private final NBTFile file;

	protected PlayerData(File playerFile) throws IOException {
		file = new NBTFile(playerFile);
	}

	public NBTFile getFile() {
		return file;
	}

	public NBTCompound getCompound() {
		return file;
	}

	public void saveChanges() {
		try {
			file.save();
		} catch (IOException e) {
			throw new NbtApiException("Error when saving level data!", e);
		}
	}
	
	public float getHealth(){
		return file.getFloat("Health");
	}
	
	public void setHealth(float health){
		file.setFloat("Health", health);
	}
	
}
