package de.tr7zw.changeme.nbtapi.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.DrilldownPie;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

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
    MC1_13_R1(1131), MC1_13_R2(1132), MC1_14_R1(1141), MC1_15_R1(1151), MC1_16_R1(1161), MC1_16_R2(1162),
    MC1_16_R3(1163), MC1_17_R1(1171), MC1_18_R1(1181, true), MC1_18_R2(1182, true), MC1_19_R1(1191, true),
    MC1_19_R2(1192, true), MC1_19_R3(1193, true), MC1_20_R1(1201, true), MC1_20_R2(1202, true), MC1_20_R3(1203, true),
    MC1_20_R4(1204, true), MC1_21_R1(1211, true), MC1_21_R2(1212, true), MC1_21_R3(1213, true), MC1_21_R4(1214, true), 
    MC1_21_R5(1215, true), MC1_21_R6(1216, true);

    private static MinecraftVersion version;
    private static Boolean hasGsonSupport;
    private static Boolean isForgePresent;
    private static Boolean isNeoForgePresent;
    private static Boolean isFabricPresent;
    private static Boolean isFoliaPresent;
    private static boolean bStatsDisabled = false;
    private static boolean disablePackageWarning = false;
    private static boolean updateCheckDisabled = true;
    /**
     * Logger used by the api
     */
    private static Logger logger = Logger.getLogger("NBTAPI");

    // NBT-API Version
    protected static final String VERSION = "2.15.3-SNAPSHOT";

    private final int versionId;
    private final boolean mojangMapping;

    // TODO: not nice
    @SuppressWarnings("serial")
    private static final Map<String, MinecraftVersion> VERSION_TO_REVISION = new HashMap<String, MinecraftVersion>() {
        { 
            this.put("1.20", MC1_20_R1);
            this.put("1.20.1",  MC1_20_R1);
            this.put("1.20.2", MC1_20_R2);
            this.put("1.20.3", MC1_20_R3);
            this.put("1.20.4", MC1_20_R3);
            this.put("1.20.5", MC1_20_R4);
            this.put("1.20.6", MC1_20_R4);
            this.put("1.21", MC1_21_R1);
            this.put("1.21.1", MC1_21_R1);
            this.put("1.21.2", MC1_21_R2);
            this.put("1.21.3", MC1_21_R2);
            this.put("1.21.4", MC1_21_R3);
            this.put("1.21.5", MC1_21_R4);
            this.put("1.21.6", MC1_21_R5);
            this.put("1.21.7", MC1_21_R5);
            this.put("1.21.8", MC1_21_R5);
            this.put("1.21.9", MC1_21_R6);
        }
    };

    MinecraftVersion(int versionId) {
        this(versionId, false);
    }

    MinecraftVersion(int versionId, boolean mojangMapping) {
        this.versionId = versionId;
        this.mojangMapping = mojangMapping;
    }

    /**
     * @return A simple comparable Integer, representing the version.
     */
    public int getVersionId() {
        return versionId;
    }

    /**
     * @return True if method names are in Mojang format and need to be remapped
     *         internally
     */
    public boolean isMojangMapping() {
        return mojangMapping;
    }

    /**
     * This method is required to hot-wire the plugin during mappings generation for
     * newer mc versions thanks to md_5 not used mojmap.
     * 
     * @return
     */
    public String getPackageName() {
        if (this == UNKNOWN) {
            try {
                return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            } catch (Exception ex) {
                // ignore, paper without remap, will fail
            }
        }
        return this.name().replace("MC", "v");
    }

    /**
     * Returns true if the current versions is at least the given Version
     * 
     * @param version The minimum version
     * @return
     */
    public static boolean isAtLeastVersion(MinecraftVersion version) {
        return getVersion().getVersionId() >= version.getVersionId();
    }

    /**
     * Returns true if the current versions newer (not equal) than the given version
     * 
     * @param version The minimum version
     * @return
     */
    public static boolean isNewerThan(MinecraftVersion version) {
        return getVersion().getVersionId() > version.getVersionId();
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
        try {
            final String ver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            logger.info("[NBTAPI] Found Minecraft: " + ver + "! Trying to find NMS support");
            version = MinecraftVersion.valueOf(ver.replace("v", "MC"));
        } catch (Exception ex) {
            logger.info("[NBTAPI] Found Minecraft: " + Bukkit.getServer().getBukkitVersion().split("-")[0]
                    + "! Trying to find NMS support");
            version = VERSION_TO_REVISION.getOrDefault(Bukkit.getServer().getBukkitVersion().split("-")[0],
                    MinecraftVersion.UNKNOWN);
        }
        if (version != UNKNOWN) {
            logger.info("[NBTAPI] NMS support '" + version.name() + "' loaded!");
        } else {
            logger.warning("[NBTAPI] This Server-Version(" + Bukkit.getServer().getBukkitVersion()
                    + ") is not supported by this NBT-API Version(" + VERSION + ") located in "
                    + VersionChecker.getPlugin()
                    + ". The NBT-API will try to work as good as it can! Some functions may not work!");
        }
        init();
        return version;
    }

    public static String getNBTAPIVersion() {
        return VERSION;
    }

    private static void init() {
        // Maven's Relocate is clever and changes strings, too. So we have to use this
        // little "trick" ... :D (from bStats)
        final String defaultPackage = new String(new byte[] { 'd', 'e', '.', 't', 'r', '7', 'z', 'w', '.', 'c', 'h',
                'a', 'n', 'g', 'e', 'm', 'e', '.', 'n', 'b', 't', 'a', 'p', 'i', '.', 'u', 't', 'i', 'l', 's' });
        final String reservedPackage = new String(new byte[] { 'd', 'e', '.', 't', 'r', '7', 'z', 'w', '.', 'n', 'b',
                't', 'a', 'p', 'i', '.', 'u', 't', 'i', 'l', 's' });
        try {
            if (hasGsonSupport() && !bStatsDisabled) {
                Plugin plugin = Bukkit.getPluginManager().getPlugin(VersionChecker.getPlugin());
                if (plugin != null && plugin instanceof JavaPlugin) {
                    getLogger()
                            .info("[NBTAPI] Using the plugin '" + plugin.getName() + "' to create a bStats instance!");
                    Metrics metrics = new Metrics((JavaPlugin) plugin, 1058);
                    metrics.addCustomChart(new SimplePie("nbtapi_version", () -> {
                        return VERSION;
                    }));
                    metrics.addCustomChart(new DrilldownPie("nms_version", () -> {
                        Map<String, Map<String, Integer>> map = new HashMap<>();
                        Map<String, Integer> entry = new HashMap<>();
                        entry.put(Bukkit.getName(), 1);
                        map.put(getVersion().name(), entry);
                        return map;
                    }));
                    metrics.addCustomChart(new SimplePie("shaded", () -> {
                        return Boolean.toString(!"NBTAPI".equals(VersionChecker.getPlugin()));
                    }));
                    metrics.addCustomChart(new SimplePie("server_software", () -> {
                        return Bukkit.getName();
                    }));
                    metrics.addCustomChart(new SimplePie("parent_plugin", () -> {
                        return VersionChecker.getPluginforBStats();
                    }));
                    metrics.addCustomChart(new SimplePie("parent_plugin_type", () -> {
                        return VersionChecker.getPluginType();
                    }));
                    metrics.addCustomChart(new SimplePie("special_environment", () -> {
                        if (isFoliaPresent()) {
                            return "Folia";
                        } else if (isForgePresent()) {
                            return "Forge";
                        } else if (isFabricPresent()) {
                            return "Fabric";
                        } else if (isNeoForgePresent()) {
                            return "NeoForge";
                        } else {
                            return "None";
                        }
                    }));
                    metrics.addCustomChart(new SimplePie("bindings_check", () -> {
                        
                        boolean failedBinding = false;
                        for (ClassWrapper c : ClassWrapper.values()) {
                            if (c.isEnabled() && c.getClazz() == null) {
                                failedBinding = true;
                            }
                        }
                        for (ReflectionMethod method : ReflectionMethod.values()) {
                            if (method.isCompatible() && !method.isLoaded()) {
                                failedBinding = true;
                            }
                        }
                        
                        return failedBinding ? "Failed" : "Pass";
                    }));
                } else if (plugin == null) {
                    getLogger().info("[NBTAPI] Unable to create a bStats instance!!");
                }
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "[NBTAPI] Error enabling Metrics!", ex);
        }

        if (hasGsonSupport() && !updateCheckDisabled)
            new Thread(() -> {
                try {
                    VersionChecker.checkForUpdates();
                } catch (Exception ex) {
                    logger.log(Level.WARNING, "[NBTAPI] Error while checking for updates! Error: " + ex.getMessage());
                }
            }).start();
        if (!disablePackageWarning && MinecraftVersion.class.getPackage().getName().equals(defaultPackage)) {
            logger.warning(
                    "#########################################- NBTAPI -#########################################");
            logger.warning(
                    "The NBT-API package has not been moved! This *will* cause problems with other plugins containing");
            logger.warning(
                    "a different version of the api! Please read the guide on the plugin page on how to get the");
            logger.warning(
                    "Maven Shade plugin to relocate the api to your personal location! If you are not the developer,");
            logger.warning("please check your plugins and contact their developer, so they can fix this issue.");
            logger.warning(
                    "#########################################- NBTAPI -#########################################");
        }
        if (!disablePackageWarning && !"NBTAPI".equals(VersionChecker.getPlugin())) { // we are not the nbtapi, check
                                                                                      // for common shading errors
            if (!"de.tr7zw.nbtapi.utils".equals(reservedPackage)) {
                logger.warning(
                        "#########################################- NBTAPI -#########################################");
                logger.warning(
                        "The NBT-API inside " + VersionChecker.getPlugin() + " is the plugin version, not the API!");
                logger.warning(
                        "The plugin itself should never be shaded! Remove the `-plugin` from the dependency and fix your shading setup.");
                logger.warning(
                        "For more info check: https://github.com/tr7zw/Item-NBT-API/wiki/Using-Maven#option-2-shading-the-nbt-api-into-your-plugin");
                logger.warning(
                        "#########################################- NBTAPI -#########################################");
                return; // don't also print the second error
            }
            if (MinecraftVersion.class.getPackage().getName().equals("de.tr7zw.nbtapi.utils")) {
                logger.warning(
                        "#########################################- NBTAPI -#########################################");
                logger.warning(
                        "The NBT-API inside " + VersionChecker.getPlugin() + " is located at 'de.tr7zw.nbtapi.utils'!");
                logger.warning(
                        "This package name is reserved for the official NBTAPI plugin, and not intended to be used for shading!");
                logger.warning("Please change the relocate to something else. For example: com.example.util.nbtapi");
                logger.warning(
                        "#########################################- NBTAPI -#########################################");
            }
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
            Class.forName("com.google.gson.Gson");
            hasGsonSupport = true;
        } catch (Exception ex) {
            logger.info("[NBTAPI] Gson not found! This will not allow the usage of some methods!");
            hasGsonSupport = false;
        }
        return hasGsonSupport;
    }

    /**
     * @return True, if Fabric is present
     */
    public static boolean isFabricPresent() {
        if (isFabricPresent != null) {
            return isFabricPresent;
        }
        try {
            logger.info("[NBTAPI] Found Fabric: " + Class.forName("net.fabricmc.api.ModInitializer"));
            isFabricPresent = true;
        } catch (Exception ex) {
            isFabricPresent = false;
        }
        return isFabricPresent;
    }
    
    /**
     * @return True, if Forge is present
     */
    public static boolean isForgePresent() {
        if (isForgePresent != null) {
            return isForgePresent;
        }
        try {
            logger.info("[NBTAPI] Found Forge: "
                    + (getVersion() == MinecraftVersion.MC1_7_R4 ? Class.forName("cpw.mods.fml.common.Loader")
                            : Class.forName("net.minecraftforge.fml.common.Loader")));
            isForgePresent = true;
        } catch (Exception ex) {
            isForgePresent = false;
        }
        return isForgePresent;
    }
    
    /**
     * @return True, if NeoForge is present
     */
    public static boolean isNeoForgePresent() {
        if (isNeoForgePresent != null) {
            return isNeoForgePresent;
        }
        try {
            logger.info("[NBTAPI] Found NeoForge: " + Class.forName("net.neoforged.neoforge.common.NeoForge"));
            isNeoForgePresent = true;
        } catch (Exception ex) {
            isNeoForgePresent = false;
        }
        return isNeoForgePresent;
    }

    /**
     * @return True, if Folia is present
     */
    public static boolean isFoliaPresent() {
        if (isFoliaPresent != null) {
            return isFoliaPresent;
        }
        try {
            logger.info("[NBTAPI] Found Folia: " + Class.forName("io.papermc.paper.threadedregions.RegionizedServer"));
            isFoliaPresent = true;
        } catch (Exception ex) {
            isFoliaPresent = false;
        }
        return isFoliaPresent;
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
     * Enables the update check. Uses Spiget to get the current version and prints a
     * warning when outdated.
     */
    public static void enableUpdateCheck() {
        updateCheckDisabled = false;
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

    /**
     * @return Logger used by the NBT-API
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Replaces the NBT-API logger with a custom implementation.
     * 
     * @param logger The new logger(can not be null!)
     */
    public static void replaceLogger(Logger logger) {
        if (logger == null)
            throw new NullPointerException("Logger can not be null!");
        MinecraftVersion.logger = logger;
    }

}
