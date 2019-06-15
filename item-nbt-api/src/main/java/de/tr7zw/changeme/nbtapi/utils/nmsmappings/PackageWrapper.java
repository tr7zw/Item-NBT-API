package de.tr7zw.changeme.nbtapi.utils.nmsmappings;

public enum PackageWrapper {
	NMS("net.minecraft.server"),
	CRAFTBUKKIT("org.bukkit.craftbukkit"),
	;
	
	private final String uri;

	private PackageWrapper(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

}
