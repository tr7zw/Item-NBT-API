package de.tr7zw.changeme.nbtapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ObjectCreator;

public class NBTFile extends NBTCompound {

    private final File file;
    private Object nbt;

    public NBTFile(File file) throws IOException {
        super(null, null);
        this.file = file;
        if (file.exists()) {
            FileInputStream inputsteam = new FileInputStream(file);
            nbt = NBTReflectionUtil.readNBTFile(inputsteam);
        } else {
            nbt = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance();
            save();
        }
    }

    public void save() throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            if(!file.createNewFile())
            	throw new IOException("Unable to create file at " + file.getAbsolutePath());
        }
        FileOutputStream outStream = new FileOutputStream(file);
        NBTReflectionUtil.saveNBTFile(nbt, outStream);
    }

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

}
