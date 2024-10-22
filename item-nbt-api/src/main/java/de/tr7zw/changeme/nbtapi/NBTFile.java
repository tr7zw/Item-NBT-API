package de.tr7zw.changeme.nbtapi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import de.tr7zw.changeme.nbtapi.iface.NBTFileHandle;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ObjectCreator;

/**
 * {@link NBTCompound} implementation backed by a {@link File}
 * 
 * @author tr7zw
 *
 */
public class NBTFile extends NBTCompound implements NBTFileHandle {

    private final File file;
    private Object nbt;

    /**
     * Creates a NBTFile that uses @param file to store its data. If this file
     * exists, the data will be loaded.
     * 
     * @param file
     * @throws IOException
     * @deprecated Use NBT.getFileHandle(file)
     */
    @Deprecated
    public NBTFile(File file) throws IOException {
        super(null, null);
        if (file == null) {
            throw new NullPointerException("File can't be null!");
        }
        this.file = file;
        if (file.exists()) {
            nbt = NBTReflectionUtil.readNBT(Files.newInputStream(file.toPath()));
        } else {
            nbt = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
            save();
        }
    }

    /**
     * Saves the data to the file
     * 
     * @throws IOException
     */
    @Override
    public void save() throws IOException {
        try {
            getWriteLock().lock();
            saveTo(file, this);
        } finally {
            getWriteLock().unlock();
        }
    }

    /**
     * @return The File used to store the data
     */
    @Override
    public File getFile() {
        return file;
    }

    @Override
    public Object getCompound() {
        return nbt;
    }

    @Override
    protected void setCompound(Object compound) {
        nbt = compound;
    }

    /**
     * Reads NBT data from the provided file.
     * <p>
     * Returns empty NBTContainer if file does not exist.
     *
     * @param file file to read
     * @return NBTCompound holding file's nbt data
     * @throws IOException exception
     * @deprecated Use NBT.readFile(file)
     */
    @Deprecated
    public static NBTCompound readFrom(File file) throws IOException {
        if (!file.exists())
            return new NBTContainer();
        return new NBTContainer(NBTReflectionUtil.readNBT(Files.newInputStream(file.toPath())));
    }

    /**
     * Saves NBT data to the provided file.
     * <p>
     * Will fully override the file if it already exists.
     *
     * @param file file
     * @param nbt  NBT data
     * @throws IOException exception
     * @deprecated Use NBT.writeFile(file, nbt)
     */
    @Deprecated
    public static void saveTo(File file, NBTCompound nbt) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            if (!file.createNewFile())
                throw new IOException("Unable to create file at " + file.getAbsolutePath());
        }
        nbt.writeCompound(Files.newOutputStream(file.toPath()));
    }

}
