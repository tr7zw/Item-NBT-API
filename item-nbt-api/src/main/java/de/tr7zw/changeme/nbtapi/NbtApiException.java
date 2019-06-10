package de.tr7zw.changeme.nbtapi;

public class NbtApiException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -993309714559452334L;

	public NbtApiException() {
		super();
	}

	public NbtApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NbtApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public NbtApiException(String message) {
		super(message);
	}

	public NbtApiException(Throwable cause) {
		super(cause);
	}
	
}
