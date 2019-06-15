package de.tr7zw.changeme.nbtapi.utils;

import com.google.gson.Gson;

import de.tr7zw.changeme.nbtapi.NbtApiException;

public class GsonWrapper {

	/**
	 * Private constructor
	 */
	private GsonWrapper() {
		
	}
	
    private static final Gson gson = new Gson();

    public static String getString(Object obj) {
        return gson.toJson(obj);
    }

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
