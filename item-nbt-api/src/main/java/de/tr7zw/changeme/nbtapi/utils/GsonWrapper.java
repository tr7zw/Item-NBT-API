package de.tr7zw.changeme.nbtapi.utils;

import com.google.gson.Gson;

import de.tr7zw.changeme.nbtapi.NbtApiException;

/**
 * Helper class for 1.7 servers without Gson
 * 
 * Please consider your own Gson/Jackson instance instead of the built in
 * methods.
 * 
 * @author tr7zw
 *
 */
@Deprecated
public class GsonWrapper {

    /**
     * Private constructor
     */
    private GsonWrapper() {

    }

    private static Gson gson = new Gson();

    /**
     * Turns Objects into Json Strings
     * 
     * @param obj
     * @return Json, representing the Object
     */
    public static String getString(Object obj) {
        return gson.toJson(obj);
    }

    public static void overwriteGsonInstance(Gson replacement) {
        gson = replacement;
    }

    /**
     * Creates an Object of the given type using the Json String
     * 
     * @param json
     * @param type
     * @return Object that got created, or null if the json is null
     */
    public static <T> T deserializeJson(String json, Class<T> type) {
        try {
            if (json == null) {
                return null;
            }

            T obj = gson.fromJson(json, type);
            return type.cast(obj);
        } catch (Exception ex) {
            throw new NbtApiException("Error while converting json to " + type.getName(), ex);
        }
    }

}
