package de.tr7zw.changeme.nbtapi;

import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;

/**
 * A generic {@link RuntimeException} that can be thrown by most methods in the
 * NBTAPI.
 * 
 * @author tr7zw
 *
 */
public class NbtApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -993309714559452334L;
	/**
	 * Keep track of the plugin selfcheck. 
	 * Null = not checked(silentquickstart/shaded)
	 * true = selfcheck failed
	 * false = everything should be fine, but apparently wasn't?
	 */
	public static Boolean confirmedBroken = null;

	/**
	 * 
	 */
	public NbtApiException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NbtApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(generateMessage(message), cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NbtApiException(String message, Throwable cause) {
		super(generateMessage(message), cause);
	}

	/**
	 * @param message
	 */
	public NbtApiException(String message) {
		super(generateMessage(message));
	}

	/**
	 * @param cause
	 */
	public NbtApiException(Throwable cause) {
		super(generateMessage(cause==null ? null : cause.toString()), cause);
	}
	
	private static String generateMessage(String message) {
	    if(message == null)return null;
	    if(confirmedBroken == null) {
            return "[?]"+message;
        }else if(confirmedBroken == false) {
	        return "[Selfchecked]"+message;
	    }
	    
	    return "[" + MinecraftVersion.getVersion() + "]There were errors detected during the server self-check! Please, make sure that NBT-API is up to date. Error message: " + message;
	}

}
