package de.tr7zw.nbtapi.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.changeme.nbtapi.NbtApiException;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.changeme.nbtapi.utils.VersionChecker;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;
import de.tr7zw.nbtapi.plugin.tests.GameprofileTest;
import de.tr7zw.nbtapi.plugin.tests.NBTFileTest;
import de.tr7zw.nbtapi.plugin.tests.blocks.BlockNBTTest;
import de.tr7zw.nbtapi.plugin.tests.chunks.ChunkNBTPersistentTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.CompoundDifferenceTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.EnumTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.EqualsTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.ForEachTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.GetterSetterTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.InterfaceTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.IteratorTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.ListTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.LongArrayTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.MergeTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.ModernSubCompoundsTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.RemovingKeys;
import de.tr7zw.nbtapi.plugin.tests.compounds.ResolveTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.StreamTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.SubCompoundsTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.TypeTest;
import de.tr7zw.nbtapi.plugin.tests.entities.EntityCustomNbtPersistentTest;
import de.tr7zw.nbtapi.plugin.tests.entities.EntityTest;
import de.tr7zw.nbtapi.plugin.tests.items.ComponentsTest;
import de.tr7zw.nbtapi.plugin.tests.items.DirectApplyMetaTest;
import de.tr7zw.nbtapi.plugin.tests.items.DirectApplyTest;
import de.tr7zw.nbtapi.plugin.tests.items.EmptyItemTest;
import de.tr7zw.nbtapi.plugin.tests.items.ItemConversionTest;
import de.tr7zw.nbtapi.plugin.tests.items.ItemJsonTest;
import de.tr7zw.nbtapi.plugin.tests.items.ItemMergingTest;
import de.tr7zw.nbtapi.plugin.tests.items.ItemStackConversionTest;
import de.tr7zw.nbtapi.plugin.tests.items.LegacyItemTest;
import de.tr7zw.nbtapi.plugin.tests.items.MetaTest;
import de.tr7zw.nbtapi.plugin.tests.items.NBTModifyItemTest;
import de.tr7zw.nbtapi.plugin.tests.items.SmuggleTest;
import de.tr7zw.nbtapi.plugin.tests.proxy.SimpleProxyTest;
import de.tr7zw.nbtapi.plugin.tests.tiles.TileTest;
import de.tr7zw.nbtapi.plugin.tests.tiles.TilesCustomNBTPersistentTest;

public class NBTAPI extends JavaPlugin {

    private boolean compatible = true;
    private List<de.tr7zw.nbtapi.plugin.tests.Test> apiTests = new ArrayList<>();

    private static NBTAPI instance;

    public static NBTAPI getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {

        getConfig().options().copyDefaults(true);
        getConfig().addDefault("bStats.enabled", true);
        getConfig().addDefault("updateCheck.enabled", true);
        getConfig().addDefault("silentquickstart", false);
        saveConfig();

        if (!getConfig().getBoolean("bStats.enabled")) {
            getLogger().info("bStats disabled");
            MinecraftVersion.disableBStats();
        }

        if (!getConfig().getBoolean("updateCheck.enabled")) {
            getLogger().info("Update check disabled");
            MinecraftVersion.disableUpdateCheck();
        } else {
            MinecraftVersion.enableUpdateCheck();
        }

        // NBTCompounds
        apiTests.add(new GetterSetterTest());
        apiTests.add(new TypeTest());
        apiTests.add(new RemovingKeys());
        apiTests.add(new EnumTest());
        apiTests.add(new InterfaceTest());
        apiTests.add(new ResolveTest());
        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3)) // 1.7.10 list support is not complete at all
            apiTests.add(new ListTest());
        apiTests.add(new SubCompoundsTest());
        apiTests.add(new ModernSubCompoundsTest());
        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3)) // 1.7.10 not a thing
            apiTests.add(new MergeTest());
        apiTests.add(new ForEachTest());
        apiTests.add(new StreamTest());
        apiTests.add(new EqualsTest());
        apiTests.add(new CompoundDifferenceTest());
        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3)) // 1.7.10 list support is not complete at all
            apiTests.add(new IteratorTest());
        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_16_R1))
            apiTests.add(new LongArrayTest());

        // Items
        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3)) { // 1.7.10 not a thing
            apiTests.add(new ItemConversionTest());
            apiTests.add(new ItemStackConversionTest());
        }
        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R4)) {
            apiTests.add(new LegacyItemTest());
        }
        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_20_R4) && !MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_21_R4)) {
            apiTests.add(new ItemJsonTest());
        }
        apiTests.add(new ComponentsTest());
        apiTests.add(new EmptyItemTest());
        apiTests.add(new SmuggleTest());
        apiTests.add(new MetaTest());
        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3)) { // 1.7.10 not a thing
            apiTests.add(new ItemMergingTest());
            apiTests.add(new DirectApplyTest());
            apiTests.add(new DirectApplyMetaTest());
            apiTests.add(new NBTModifyItemTest());
        }
        // Proxies
        apiTests.add(new SimpleProxyTest());

        // Entity
        apiTests.add(new EntityTest());
        apiTests.add(new EntityCustomNbtPersistentTest());

        // Tiles
        apiTests.add(new TileTest());
        apiTests.add(new TilesCustomNBTPersistentTest());

        // Chunks
        apiTests.add(new ChunkNBTPersistentTest());

        // Blocks
        apiTests.add(new BlockNBTTest());

        // Files
        apiTests.add(new NBTFileTest());

        if (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3))
            apiTests.add(new GameprofileTest());

    }

    @Override
    public void onEnable() {
        instance = this; // NOSONAR
        // new MetricsLite(this); The metrics moved into the API
        if (getConfig().getBoolean("silentquickstart")) {
            // we are silent, won't check anything or pre-init
            VersionChecker.hideOk = true;
            return;
        }
        MinecraftVersion.hasGsonSupport(); // init gson(if it hasn't already)
        getLogger().info("Checking bindings...");
        MinecraftVersion.getVersion();

        boolean classUnlinked = false;
        for (ClassWrapper c : ClassWrapper.values()) {
            if (c.isEnabled() && c.getClazz() == null) {
                if (!classUnlinked)
                    getLogger().info("Classes:");
                getLogger().warning(c.name() + " did not find it's class!");
                compatible = false;
                classUnlinked = true;
            }
        }
        if (!classUnlinked)
            getLogger().info("All Classes were able to link!");

        boolean methodUnlinked = false;
        for (ReflectionMethod method : ReflectionMethod.values()) {
            if (method.isCompatible() && !method.isLoaded()) {
                if (!methodUnlinked)
                    getLogger().info("Methods:");
                getLogger().warning(method.name() + " did not find the method!");
                compatible = false;
                methodUnlinked = true;
            }
        }
        if (!methodUnlinked)
            getLogger().info("All Methods were able to link!");
        getLogger().info("Running NBT reflection test...");

        Map<de.tr7zw.nbtapi.plugin.tests.Test, Exception> results = new HashMap<>();
        for (de.tr7zw.nbtapi.plugin.tests.Test test : apiTests) {
            try {
                test.test();
                results.put(test, null);
            } catch (Exception ex) {
                results.put(test, ex);
                getLogger().log(Level.WARNING, "Error during '" + test.getClass().getSimpleName() + "' test!", ex);
            } catch (NoSuchFieldError th) { // NOSONAR
                getLogger().log(Level.SEVERE, "Servere error during '" + test.getClass().getSimpleName() + "' test!");
                getLogger().warning(
                        "WARNING! This version of Item-NBT-API seems to be broken with your Spigot version! Canceled the other tests!");
                throw th;
            }
        }

        for (Entry<de.tr7zw.nbtapi.plugin.tests.Test, Exception> entry : results.entrySet()) {
            if (entry.getValue() != null) {
                compatible = false;
                getLogger().info(entry.getKey().getClass().getSimpleName() + ": " + entry.getValue().getMessage());
            }
        }

        String checkMessage = "Plugins that don't check properly may throw Exeptions, crash or have unexpected behaviors!";
        if (compatible) {
            NbtApiException.confirmedBroken = false;
            getLogger().info("Success! This version of NBT-API is compatible with your server.");
        } else {
            NbtApiException.confirmedBroken = true;
            getLogger().warning(
                    "WARNING! This version of NBT-API seems to be broken with your Spigot version! " + checkMessage);
            if (MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4) {
                getLogger().warning(
                        "1.7.10 is only partally supported! Some thing will not work/are not yet avaliable in 1.7.10!");
            }
        }

    }

    /**
     * @return True if all selfchecks succeeded
     */
    public boolean isCompatible() {
        return compatible;
    }

}
