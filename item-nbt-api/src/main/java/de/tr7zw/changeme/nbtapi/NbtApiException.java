package de.tr7zw.changeme.nbtapi;

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
	    if(confirmedBroken == false) {
	        return "[Selfchecked]"+message;
	    }
	    if(confirmedBroken == null) {
	        return "[?]"+message;
	    }
	    return "During the server start selfcheck there where detected errors! Please make sure that the NBTAPI is on it's newest version. Error message: " + message;
	}

}
