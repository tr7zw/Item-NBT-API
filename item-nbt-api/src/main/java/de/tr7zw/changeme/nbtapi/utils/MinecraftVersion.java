package de.tr7zw.changeme.nbtapi.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

/**
 * This class acts as the "Brain" of the NBTApi. It contains the main logger for
 * other classes,registers bStats and checks rather Maven shading was done
 * correctly.
 * 
 * @author tr7zw
 *
 */
@SuppressWarnings("javadoc")
public enum MinecraftVersion {
	UNKNOWN(Integer.MAX_VALUE), // Use the newest known mappings
	MC1_7_R4(174), MC1_8_R3(183), MC1_9_R1(191), MC1_9_R2(192), MC1_10_R1(1101), MC1_11_R1(1111), MC1_12_R1(1121),
	MC1_13_R1(1131), MC1_13_R2(1132), MC1_14_R1(1141), MC1_15_R1(1151), MC1_16_R1(1161);

	private static MinecraftVersion version;
	private static Boolean hasGsonSupport;
	private static boolean bStatsDisabled = false;
	private static boolean disablePackageWarning = false;
	private static boolean updateCheckDisabled = false;
	/**
	 * Logger used by the api
	 */
	public static final Logger logger = Logger.getLogger("NBTAPI");

	// NBT-API Version
	protected static final String VERSION = "2.5.0-SNAPSHOT";

	private final int versionId;

	MinecraftVersion(int versionId) {
		this.versionId = versionId;
	}

	/**
	 * @return A simple comparable Integer, representing the version.
	 */
	public int getVersionId() {
		return versionId;
	}

	/**
	 * Getter for this servers MinecraftVersion. Also init's bStats and checks the
	 * shading.
	 * 
	 * @return The enum for the MinecraftVersion this server is running
	 */
	public static MinecraftVersion getVersion() {
		if (version != null) {
			return version;
		}
		final String ver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		logger.info("[NBTAPI] Found Spigot: " + ver + "! Trying to find NMS support");
		try {
			version = MinecraftVersion.valueOf(ver.replace("v", "MC"));
		} catch (IllegalArgumentException ex) {
			version = MinecraftVersion.UNKNOWN;
		}
		if (version != UNKNOWN) {
			logger.info("[NBTAPI] NMS support '" + version.name() + "' loaded!");
		} else {
			logger.warning("[NBTAPI] Wasn't able to find NMS Support! Some functions may not work!");
		}
		init();
		return version;
	}

	private static void init() {
		try {
			if (!bStatsDisabled)
				new ApiMetricsLite();
		} catch (Exception ex) {
			logger.log(Level.WARNING, "[NBTAPI] Error enabling Metrics!", ex);
		}

		if (!updateCheckDisabled)
			new Thread(() -> {
				try {
					VersionChecker.checkForUpdates();
				} catch (Exception ex) {
					logger.log(Level.WARNING, "[NBTAPI] Error while checking for updates!", ex);
				}
			}).start();
		// Maven's Relocate is clever and changes strings, too. So we have to use this
		// little "trick" ... :D (from bStats)
		final String defaultPackage = new String(new byte[] { 'd', 'e', '.', 't', 'r', '7', 'z', 'w', '.', 'c', 'h',
				'a', 'n', 'g', 'e', 'm', 'e', '.', 'n', 'b', 't', 'a', 'p', 'i', '.', 'u', 't', 'i', 'l', 's' });
		if (!disablePackageWarning && MinecraftVersion.class.getPackage().getName().equals(defaultPackage)) {
			logger.warning(
					"#########################################- NBTAPI -#########################################");
			logger.warning(
					"The NBT-API package has not been moved! This *will* cause problems with other plugins containing");
			logger.warning(
					"a different version of the api! Please read the guide on the plugin page on how to get the");
			logger.warning(
					"Maven Shade plugin to relocate the api to your personal location! If you are not the developer,");
			logger.warning("please check your plugins and contact their developer, so he can fix this issue.");
			logger.warning(
					"#########################################- NBTAPI -#########################################");
		}
	}

	/**
	 * @return True, if Gson is usable
	 */
	public static boolean hasGsonSupport() {
		if (hasGsonSupport != null) {
			return hasGsonSupport;
		}
		try {
			logger.info("[NBTAPI] Found Gson: " + Class.forName("com.google.gson.Gson"));
			hasGsonSupport = true;
		} catch (Exception ex) {
			logger.info("[NBTAPI] Gson not found! This will not allow the usage of some methods!");
			hasGsonSupport = false;
		}
		return hasGsonSupport;
	}

	/**
	 * Calling this function before the NBT-Api is used will disable bStats stats
	 * collection. Please consider not to do that, since it won't affect your plugin
	 * and helps the NBT-Api developer to see api's demand.
	 */
	public static void disableBStats() {
		bStatsDisabled = true;
	}

	/**
	 * Disables the update check. Uses Spiget to get the current version and prints
	 * a warning when outdated.
	 */
	public static void disableUpdateCheck() {
		updateCheckDisabled = true;
	}

	/**
	 * Forcefully disables the log message for plugins not shading the API to
	 * another location. This may be helpful for networks or development
	 * environments, but please don't use it for plugins that are uploaded to
	 * Spigotmc.
	 */
	public static void disablePackageWarning() {
		disablePackageWarning = true;
	}

}
