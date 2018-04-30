package de.tr7zw.itemnbtapi;

import java.lang.reflect.Method;

import de.tr7zw.itemnbtapi.utils.MinecraftVersion;

public enum ReflectionMethod {

    COMPOUND_SET_FLOAT(NBTReflectionUtil.getNBTTagCompound(), new Class[]{String.class, float.class}, MinecraftVersion.MC1_7_R4, new Since(MinecraftVersion.MC1_7_R4, "setFloat"))
    ;
    
    private final Class<?> clazz;
    private final Class[] args;
    private final Since targetVersion;
    private Method method;
    
    ReflectionMethod(Class<?> targetClass, Class<?>[] args, MinecraftVersion addedSince, Since... methodnames){
        clazz = targetClass;
        this.args = args;
        MinecraftVersion server = MinecraftVersion.getVersion();
        Since target = methodnames[0];
        for(Since s : methodnames){
            if(s.version.getVersionId() <= server.getVersionId() && target.version.getVersionId() < s.version.getVersionId())
                target = s;
        }
        targetVersion = target;
        try{
            method = targetClass.getDeclaredMethod(targetVersion.name, args);
            method.setAccessible(true);
        }catch(NullPointerException | NoSuchMethodException | SecurityException ex){
            ex.printStackTrace();
        }
    }
    
    public Object run(Object target, Object... args){
        try{
            return method.invoke(target, args);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
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
