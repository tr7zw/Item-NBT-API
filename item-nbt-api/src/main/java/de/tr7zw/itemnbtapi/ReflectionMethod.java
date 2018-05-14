package de.tr7zw.itemnbtapi;

import java.lang.reflect.Method;

import de.tr7zw.itemnbtapi.utils.MinecraftVersion;

public enum ReflectionMethod {

    COMPOUND_SET_FLOAT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, float.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setFloat")),
    COMPOUND_SET_STRING(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setString")),
    COMPOUND_SET_INT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, int.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setInt")),
    COMPOUND_SET_BYTEARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, byte[].class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setByteArray")),
    COMPOUND_SET_INTARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, int[].class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setIntArray")),
    COMPOUND_SET_LONG(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, long.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setLong")),
    COMPOUND_SET_SHORT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, short.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setShort")),
    COMPOUND_SET_BYTE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, byte.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setByte")),
    COMPOUND_SET_DOUBLE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, double.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setDouble")),
    COMPOUND_SET_BOOLEAN(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, boolean.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setBoolean")),

    COMPOUND_GET_FLOAT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getFloat")),
    COMPOUND_GET_STRING(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getString")),
    COMPOUND_GET_INT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getInt")),
    COMPOUND_GET_BYTEARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getByteArray")),
    COMPOUND_GET_INTARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getIntArray")),
    COMPOUND_GET_LONG(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getLong")),
    COMPOUND_GET_SHORT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getShort")),
    COMPOUND_GET_BYTE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getByte")),
    COMPOUND_GET_DOUBLE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getDouble")),
    COMPOUND_GET_BOOLEAN(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "getBoolean")),

   ;
    
    private Since targetVersion;
    private Method method;
    private boolean loaded = false;
    private boolean compatible = false;
    
    ReflectionMethod(Class<?> targetClass, Class<?>[] args, MinecraftVersion addedSince, Since... methodnames){
        MinecraftVersion server = MinecraftVersion.getVersion();
        if(server.compareTo(addedSince) < 0)return;
        compatible = true;
        Since target = methodnames[0];
        for(Since s : methodnames){
            if(s.version.getVersionId() <= server.getVersionId() && target.version.getVersionId() < s.version.getVersionId())
                target = s;
        }
        targetVersion = target;
        try{
            method = targetClass.getMethod(targetVersion.name, args);
            method.setAccessible(true);
            loaded = true;
        }catch(NullPointerException | NoSuchMethodException | SecurityException ex){
            ex.printStackTrace();
        }
    }
    
    public Object run(Object target, Object... args){
        try{
            //System.out.println(method.getName() + " executed with " + args[0] + " " + args[1] + " on " + target.getClass().getName());           
            return method.invoke(target, args);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public boolean isLoaded() {
        return loaded;
    }

    public boolean isCompatible() {
        return compatible;
    }

    public static class Since{
        public final MinecraftVersion version;
        public final String name;
        public Since(MinecraftVersion version, String name) {
            this.version = version;
            this.name = name;
        }
    }
    
}
