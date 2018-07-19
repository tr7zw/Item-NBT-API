package de.tr7zw.itemnbtapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
            file.createNewFile();
        }
        FileOutputStream outStream = new FileOutputStream(file);
        NBTReflectionUtil.saveNBTFile(nbt, outStream);
    }

    public File getFile() {
        return file;
    }

    protected Object getCompound() {
        return nbt;
    }

    protected void setCompound(Object compound) {
        nbt = compound;
    }

}
