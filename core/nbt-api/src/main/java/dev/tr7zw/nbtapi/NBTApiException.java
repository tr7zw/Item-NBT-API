package dev.tr7zw.nbtapi;

/**
 * A generic {@link RuntimeException} that can be thrown by most methods in the
 * NBT-API in case of compatibility or logic issues.
 * 
 * @author tr7zw
 */
public class NBTApiException extends RuntimeException {

	private static final long serialVersionUID = -993309714559452334L;

	/**
	 * 
	 */
	public NBTApiException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NBTApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NBTApiException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NBTApiException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NBTApiException(Throwable cause) {
		super(cause);
	}

}
