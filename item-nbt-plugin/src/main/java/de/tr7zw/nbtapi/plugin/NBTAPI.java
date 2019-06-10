package de.tr7zw.nbtapi.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.changeme.nbtapi.ClassWrapper;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.ReflectionMethod;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import de.tr7zw.nbtapi.plugin.tests.NBTFileTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.GetterSetterTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.ListTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.MergeTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.RemovingKeys;
import de.tr7zw.nbtapi.plugin.tests.compounds.SubCompoundsTest;
import de.tr7zw.nbtapi.plugin.tests.compounds.TypeTest;
import de.tr7zw.nbtapi.plugin.tests.entities.EntityTest;
import de.tr7zw.nbtapi.plugin.tests.items.EmptyItemTest;
import de.tr7zw.nbtapi.plugin.tests.items.ItemConvertionTest;
import de.tr7zw.nbtinjector.NBTInjector;

public class NBTAPI extends JavaPlugin {

	private static boolean compatible = true;
	private static ArrayList<de.tr7zw.nbtapi.plugin.tests.Test> apiTests = new ArrayList<>();

	@Deprecated
	public static NBTAPI instance;

	public static NBTAPI getInstance() {
		return instance;
	}

	@Override
	public void onLoad() {
		getLogger().info("Injecting custom NBT");
		try {
			NBTInjector.inject();
			getLogger().info("Injected!");
		}catch(Exception ex) {
			ex.printStackTrace();
		}

		//NBTCompounds
		apiTests.add(new GetterSetterTest());
		apiTests.add(new TypeTest());
		apiTests.add(new RemovingKeys());
		apiTests.add(new ListTest());
		apiTests.add(new SubCompoundsTest());
		apiTests.add(new MergeTest());
		
		//Items
		apiTests.add(new ItemConvertionTest());
		apiTests.add(new EmptyItemTest());
		
		//Entity
		apiTests.add(new EntityTest());
		
		//Files
		apiTests.add(new NBTFileTest());
		
	}

	@Override
	public void onEnable() {
		instance = this;
		//new MetricsLite(this); The metrics moved into the API
		
		Bukkit.getPluginManager().registerEvents(new Test(), this);
		getLogger().info("Checking bindings...");
		MinecraftVersion.getVersion();
		getLogger().info("Gson:");
		MinecraftVersion.hasGsonSupport();
		getLogger().info("Classes:");
		boolean classUnlinked = false;
		for (ClassWrapper c : ClassWrapper.values()) {
			if (c.getClazz() == null) {
				getLogger().warning(c.name() + " did not find it's class!");
				compatible = false;
				classUnlinked = true;
			}
		}
		if(!classUnlinked)
			getLogger().info("All Classes where able to link!");
		
		getLogger().info("Methods:");
		boolean methodUnlinked = false;
		for (ReflectionMethod method : ReflectionMethod.values()) {
			if (method.isCompatible() && !method.isLoaded()) {
				getLogger().warning(method.name() + " did not find the method!");
				compatible = false;
				methodUnlinked = true;
			}
		}
		if(!methodUnlinked)
			getLogger().info("All Methods where able to link!");
		getLogger().info("Running NBT reflection test...");
		
		Map<de.tr7zw.nbtapi.plugin.tests.Test, Exception> results = new HashMap<>();
		for(de.tr7zw.nbtapi.plugin.tests.Test test : apiTests) {
			try {
				test.test();
				results.put(test, null);
			}catch(Exception ex) {
				results.put(test, ex);
				getLogger().log(Level.WARNING, "Error during '" + test.getClass().getSimpleName() + "' test!", ex);
			}
		}
		
		for(Entry<de.tr7zw.nbtapi.plugin.tests.Test, Exception> entry : results.entrySet()) {
			if(entry.getValue() != null)
				compatible = false;
			getLogger().info(entry.getKey().getClass().getSimpleName() + ": " + (entry.getValue() == null ? "Ok" : entry.getValue().getMessage()));
		}

		String checkMessage = "Plugins that don't check properly may throw Exeptions, crash or have unexpected behaviors!";
		if (compatible) {
				getLogger().info("Success! This version of Item-NBT-API is compatible with your server.");
		} else {
			getLogger().warning("WARNING! This version of Item-NBT-API seems to be broken with your Spigot version! "
					+ checkMessage);
		}

	}

	public boolean isCompatible() {
		return compatible;
	}

	public static NBTItem getNBTItem(ItemStack item) {
		return new NBTItem(item);
	}

}
