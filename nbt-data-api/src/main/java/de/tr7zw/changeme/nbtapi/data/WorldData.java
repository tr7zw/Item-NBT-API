package de.tr7zw.changeme.nbtapi.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.util.Vector;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTFile;
import de.tr7zw.changeme.nbtapi.NbtApiException;

public class WorldData {

    private final NBTFile file;

    protected WorldData(File worldFolder) throws IOException {
        if (!new File(worldFolder, "level.dat").exists())
            throw new FileNotFoundException("Level.dat at: " + new File(worldFolder, "level.dat").getAbsolutePath());
        file = new NBTFile(new File(worldFolder, "level.dat"));
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

    public String getWorldName() {
        return file.resolveOrNull("Data.LevelName", String.class);
    }

    public void setWorldName(String name) {
        file.getOrCreateCompound("Data").setString("LevelName", name);
    }

    public Vector getSpawnPosition() {
        NBTCompound data = file.getCompound("Data");
        if (data == null) {
            return null;
        }
        return new Vector(data.getInteger("SpawnX"), data.getInteger("SpawnY"), data.getInteger("SpawnZ"));
    }

    public void setSpawnPosition(Vector vec) {
        NBTCompound data = file.getOrCreateCompound("Data");
        data.setInteger("SpawnX", vec.getBlockX());
        data.setInteger("SpawnY", vec.getBlockY());
        data.setInteger("SpawnZ", vec.getBlockZ());
    }

}
