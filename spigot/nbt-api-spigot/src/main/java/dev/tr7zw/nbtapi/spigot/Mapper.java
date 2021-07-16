package dev.tr7zw.nbtapi.spigot;

final class Mapper {

    public enum EcoSystem {
        /**
         * Base package name of the NMS classes.
         */
        NMS("net.minecraft.server"),

        /**
         * Base package of the CraftBukkit classes.
         */
        CRAFTBUKKIT("org.bukkit.craftbukkit"),

        /**
         * No pre-defined package name, putting this essentially
         * at the root of all loaded classes.
         */
        DEFAULT("");

        private final String packageUri;

        EcoSystem(String packageUri) {
            this.packageUri = packageUri;
        }

        /**
         * Get the package name of the base of this eco system.
         * @return The string of the package name.
         */
        public String getPackageUri() {
            return this.packageUri;
        }
    }
}
