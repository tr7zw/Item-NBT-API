package de.tr7zw.changeme.nbtapi;

/**
 * A generic {@link RuntimeException} that can be thrown by most methods in the NBTAPI.
 * 
 * @author tr7zw
 *
 */
public class NbtApiException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -993309714559452334L;

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
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NbtApiException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NbtApiException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NbtApiException(Throwable cause) {
		super(cause);
	}
	
}
