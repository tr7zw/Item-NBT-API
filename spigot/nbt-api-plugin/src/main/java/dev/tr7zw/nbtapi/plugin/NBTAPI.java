package dev.tr7zw.nbtapi.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.nbtapi.utils.MinecraftVersion;
import de.tr7zw.nbtinjector.NBTInjector;
import dev.tr7zw.nbtapi.plugin.tests.GameprofileTest;
import dev.tr7zw.nbtapi.plugin.tests.NBTFileTest;
import dev.tr7zw.nbtapi.plugin.tests.blocks.BlockNBTTest;
import dev.tr7zw.nbtapi.plugin.tests.chunks.ChunkNBTPersistentTest;
import dev.tr7zw.nbtapi.plugin.tests.compounds.EqualsTest;
import dev.tr7zw.nbtapi.plugin.tests.compounds.ForEachTest;
import dev.tr7zw.nbtapi.plugin.tests.compounds.GetterSetterTest;
import dev.tr7zw.nbtapi.plugin.tests.compounds.IteratorTest;
import dev.tr7zw.nbtapi.plugin.tests.compounds.ListTest;
import dev.tr7zw.nbtapi.plugin.tests.compounds.MergeTest;
import dev.tr7zw.nbtapi.plugin.tests.compounds.RemovingKeys;
import dev.tr7zw.nbtapi.plugin.tests.compounds.StreamTest;
import dev.tr7zw.nbtapi.plugin.tests.compounds.SubCompoundsTest;
import dev.tr7zw.nbtapi.plugin.tests.compounds.TypeTest;
import dev.tr7zw.nbtapi.plugin.tests.data.WorldDataTest;
import dev.tr7zw.nbtapi.plugin.tests.entities.EntityCustomNbtPersistentTest;
import dev.tr7zw.nbtapi.plugin.tests.entities.EntityTest;
import dev.tr7zw.nbtapi.plugin.tests.injector.EntityCustomNbtInjectorTest;
import dev.tr7zw.nbtapi.plugin.tests.injector.MergeTileSubCompoundTest;
import dev.tr7zw.nbtapi.plugin.tests.injector.SpawnEntityCustomNbtInjectorTest;
import dev.tr7zw.nbtapi.plugin.tests.injector.TilesCustomNBTInjectorTest;
import dev.tr7zw.nbtapi.plugin.tests.items.DirectApplyTest;
import dev.tr7zw.nbtapi.plugin.tests.items.EmptyItemTest;
import dev.tr7zw.nbtapi.plugin.tests.items.ItemConvertionTest;
import dev.tr7zw.nbtapi.plugin.tests.items.ItemMergingTest;
import dev.tr7zw.nbtapi.plugin.tests.tiles.TileTest;
import dev.tr7zw.nbtapi.plugin.tests.tiles.TilesCustomNBTPersistentTest;
import dev.tr7zw.nbtapi.utils.nmsmappings.ClassWrapper;
import dev.tr7zw.nbtapi.utils.nmsmappings.ReflectionMethod;

public class NBTAPI extends JavaPlugin {

	private boolean compatible = true;
	private ArrayList<dev.tr7zw.nbtapi.plugin.tests.Test> apiTests = new ArrayList<>();

	private static NBTAPI instance;

	public static NBTAPI getInstance() {
		return instance;
	}

	@Override
	public void onLoad() {
		
		getConfig().options().copyDefaults(true);
		getConfig().addDefault("nbtInjector.enabled", false);
		getConfig().addDefault("bStats.enabled", true);
		getConfig().addDefault("updateCheck.enabled", true);
		saveConfig();
		
		if(!getConfig().getBoolean("bStats.enabled")) {
			getLogger().info("bStats disabled");
			MinecraftVersion.disableBStats();
		}
		
		if(!getConfig().getBoolean("updateCheck.enabled")) {
			getLogger().info("Update check disabled");
			MinecraftVersion.disableUpdateCheck();
		}
		
		//Disabled by default since 2.1. Enable it yourself by calling NBTInjector.inject(); during onLoad/config
		if(getConfig().getBoolean("nbtInjector.enabled")) {
			getLogger().info("Injecting custom NBT");
			try {
				NBTInjector.inject();
				getLogger().info("Injected!");
			} catch (Throwable ex) { // NOSONAR
				getLogger().log(Level.SEVERE, "Error while Injecting custom Tile/Entity classes!", ex);
				compatible = false;
			}
		}

		// NBTCompounds
		apiTests.add(new GetterSetterTest());
		apiTests.add(new TypeTest());
		apiTests.add(new RemovingKeys());
		if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3)) // 1.7.10 list support is not complete at all
			apiTests.add(new ListTest());
		apiTests.add(new SubCompoundsTest());
		if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3)) // 1.7.10 not a thing
			apiTests.add(new MergeTest());
		apiTests.add(new ForEachTest());
		apiTests.add(new StreamTest());
		apiTests.add(new EqualsTest());
		if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3)) // 1.7.10 list support is not complete at all
			apiTests.add(new IteratorTest());

		// Items
		if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3)) // 1.7.10 not a thing
			apiTests.add(new ItemConvertionTest());
		apiTests.add(new EmptyItemTest());
		if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3)) { // 1.7.10 not a thing
			apiTests.add(new ItemMergingTest());
			apiTests.add(new DirectApplyTest());
		}

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
		
		// Data
		apiTests.add(new WorldDataTest());
		
		// Injector
		apiTests.add(new TilesCustomNBTInjectorTest());
		apiTests.add(new MergeTileSubCompoundTest());
		apiTests.add(new EntityCustomNbtInjectorTest());
		apiTests.add(new SpawnEntityCustomNbtInjectorTest());
		
		if(MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3))
			apiTests.add(new GameprofileTest());

	}

	@Override
	public void onEnable() {
		instance = this; // NOSONAR
		// new MetricsLite(this); The metrics moved into the API

		getLogger().info("Adding listeners...");
		Bukkit.getPluginManager().registerEvents(new ReloadListener(), this);
		getLogger().info("Gson:");
		MinecraftVersion.hasGsonSupport();
		getLogger().info("Checking bindings...");
		MinecraftVersion.getVersion();
		
		boolean classUnlinked = false;
		for (ClassWrapper c : ClassWrapper.values()) {
			if (c.isEnabled() && c.getClazz() == null) {
				if(!classUnlinked)
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
				if(!methodUnlinked)
					getLogger().info("Methods:");
				getLogger().warning(method.name() + " did not find the method!");
				compatible = false;
				methodUnlinked = true;
			}
		}
		if (!methodUnlinked)
			getLogger().info("All Methods were able to link!");
		getLogger().info("Running NBT reflection test...");

		Map<dev.tr7zw.nbtapi.plugin.tests.Test, Exception> results = new HashMap<>();
		for (dev.tr7zw.nbtapi.plugin.tests.Test test : apiTests) {
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

		for (Entry<dev.tr7zw.nbtapi.plugin.tests.Test, Exception> entry : results.entrySet()) {
			if (entry.getValue() != null)
				compatible = false;
			getLogger().info(entry.getKey().getClass().getSimpleName() + ": "
					+ (entry.getValue() == null ? "Ok" : entry.getValue().getMessage()));
		}

		String checkMessage = "Plugins that don't check properly may throw Exeptions, crash or have unexpected behaviors!";
		if (compatible) {
			getLogger().info("Success! This version of NBT-API is compatible with your server.");
		} else {
			getLogger().warning(
					"WARNING! This version of NBT-API seems to be broken with your Spigot version! " + checkMessage);
			if(MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4) {
				getLogger().warning("1.7.10 is only partally supported! Some thing will not work/are not yet avaliable in 1.7.10!");
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
