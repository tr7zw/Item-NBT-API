package de.tr7zw.changeme.nbtapi.utils.nmsmappings;

/**
 * Package enum
 * 
 * @author tr7zw
 *
 */
@SuppressWarnings("javadoc")
public enum PackageWrapper {
	NMS(new String(new byte[] {'n', 'e', 't', '.', 'm', 'i', 'n', 'e', 'c', 'r', 'a', 'f', 't', '.', 's', 'e', 'r', 'v', 'e', 'r'})),
	CRAFTBUKKIT(new String(new byte[] {'o', 'r', 'g', '.', 'b', 'u', 'k', 'k', 'i', 't', '.', 'c', 'r', 'a', 'f', 't', 'b', 'u', 'k', 'k', 'i', 't'})),
	NONE("")
	;
	
	private final String uri;

	private PackageWrapper(String uri) {
		this.uri = uri;
	}

	/**
	 * @return The Uri for that package
	 */
	public String getUri() {
		return uri;
	}

}
