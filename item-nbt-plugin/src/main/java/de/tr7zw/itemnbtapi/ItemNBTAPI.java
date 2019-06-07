package de.tr7zw.itemnbtapi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.itemnbtapi.tests.compounds.GetterSetterTest;
import de.tr7zw.itemnbtapi.tests.compounds.ListTest;
import de.tr7zw.itemnbtapi.tests.compounds.TypeTest;
import de.tr7zw.itemnbtapi.tests.items.ItemConvertionTest;
import de.tr7zw.itemnbtapi.utils.MinecraftVersion;

public class ItemNBTAPI extends JavaPlugin {

	private static boolean compatible = true;
	private static boolean jsonCompatible = true;
	private static ArrayList<de.tr7zw.itemnbtapi.tests.Test> apiTests = new ArrayList<>();

	@Deprecated
	public static ItemNBTAPI instance;

	public static ItemNBTAPI getInstance() {
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
		apiTests.add(new ListTest());
		
		//Items
		apiTests.add(new ItemConvertionTest());
		
	}

	@Override
	public void onEnable() {
		instance = this;
		//new MetricsLite(this); The metrics moved into the API
		
		Bukkit.getPluginManager().registerEvents(new Test(), this);
		getLogger().info("Checking bindings...");
		getLogger().info("Minecraft Version:");
		MinecraftVersion.getVersion();
		getLogger().info("Gson:");
		MinecraftVersion.hasGsonSupport();
		getLogger().info("Classes:");
		for (ClassWrapper c : ClassWrapper.values()) {
			if (c.getClazz() == null) {
				getLogger().warning(c.name() + " did not find it's class!");
				compatible = false;
			}
		}
		getLogger().info("Methods:");
		for (ReflectionMethod method : ReflectionMethod.values()) {
			if (method.isCompatible() && !method.isLoaded()) {
				getLogger().warning(method.name() + " did not find the method!");
				compatible = false;
			}
		}
		getLogger().info("Running NBT reflection test...");
		
		Map<de.tr7zw.itemnbtapi.tests.Test, Exception> results = new HashMap<>();
		for(de.tr7zw.itemnbtapi.tests.Test test : apiTests) {
			try {
				test.test();
				results.put(test, null);
			}catch(Exception ex) {
				results.put(test, ex);
				getLogger().log(Level.WARNING, "Error during '" + test.getClass().getSimpleName() + "' test!", ex);
			}
		}
		
		for(Entry<de.tr7zw.itemnbtapi.tests.Test, Exception> entry : results.entrySet()) {
			if(entry.getValue() != null)
				compatible = false;
			getLogger().info(entry.getKey().getClass().getSimpleName() + ": " + (entry.getValue() == null ? "Ok" : entry.getValue().getMessage()));
		}
		
		try {
			//Item
			ItemStack item = new ItemStack(Material.STONE, 1);
			NBTItem nbtItem = new NBTItem(item);


			NBTCompound comp = nbtItem.getCompound(COMP_TEST_KEY);
			comp.setString(STRING_TEST_KEY, STRING_TEST_VALUE + "2");
			comp.setInteger(INT_TEST_KEY, INT_TEST_VALUE * 2);
			comp.setDouble(DOUBLE_TEST_KEY, DOUBLE_TEST_VALUE * 2);

			item = nbtItem.getItem();
			nbtItem = new NBTItem(item);


			nbtItem.setString(STRING_TEST_KEY, null);
			if (nbtItem.getKeys().size() != 10) {
				getLogger().warning("Wasn't able to remove a key (Got " + nbtItem.getKeys().size()
						+ " when expecting 10)! The Item-NBT-API may not work!");
				compatible = false;
			}
			comp = nbtItem.getCompound(COMP_TEST_KEY);
			if (comp == null) {
				getLogger().warning("Wasn't able to get the NBTCompound! The Item-NBT-API may not work!");
				compatible = false;
			}
			if (!comp.hasKey(STRING_TEST_KEY)) {
				getLogger().warning("Wasn't able to check a compound key! The Item-NBT-API may not work!");
				compatible = false;
			}
			if (!(STRING_TEST_VALUE + "2").equals(comp.getString(STRING_TEST_KEY))
					|| comp.getInteger(INT_TEST_KEY) != INT_TEST_VALUE * 2
					|| comp.getDouble(DOUBLE_TEST_KEY) != DOUBLE_TEST_VALUE * 2
					|| comp.getBoolean(BOOLEAN_TEST_KEY) == BOOLEAN_TEST_VALUE) {
				getLogger()
				.warning("One key does not equal the original compound value! The Item-NBT-API may not work!");
				compatible = false;
			}


			NBTList<Integer> intlist = comp.getIntegerList("inttest");
			intlist.add(42);
			intlist.add(69);
			if(intlist.size() == 2 && intlist.get(0) == 42 && intlist.get(1) == 69) {
				//ok
			}else {
				getLogger().warning("IntList is not correct! The Item-NBT-API may not work!");
				compatible = false;
			}

		} catch (Exception ex) {
			getLogger().log(Level.SEVERE, null, ex);
			compatible = false;
		}



		testJson();

		try {
			//File
			NBTFile file = new NBTFile(new File(getDataFolder(), "test.nbt"));
			file.addCompound("testcomp").setString("test1", "ok");
			NBTCompound comp = file.getCompound("testcomp");
			comp.setString("test2", "ok");
			file.setLong("time", System.currentTimeMillis());
			file.setString("test", "test");
			file.save();
			file = null;
			file = new NBTFile(new File(getDataFolder(), "test.nbt"));
			System.out.println(file.asNBTString());
			file.getFile().delete();
			//String
			String str = file.asNBTString();
			NBTContainer rebuild = new NBTContainer(str);
			if (!str.equals(rebuild.asNBTString())) {
				getLogger().warning("Wasn't able to parse NBT from a String! The Item-NBT-API may not work!");
				compatible = false;
			}
			//Item->NBT->String->NBT->Item
			ItemStack preitem = new ItemStack(Material.STICK, 5);
			ItemMeta premeta = preitem.getItemMeta();
			premeta.setDisplayName("test");
			preitem.setItemMeta(premeta);
			// String itemasString = NBTItem.convertItemtoNBT(preitem).asNBTString();
			//ItemStack afteritem = NBTItem.convertNBTtoItem(new NBTContainer(itemasString));
			//if(!preitem.isSimilar(afteritem)){
			//    getLogger().warning("Wasn't able to convert an Item to String and back to Item! The Item-NBT-API may not work!");
			//   compatible = false;
			//}
			//mergingtags
			NBTContainer test1 = new NBTContainer();
			test1.setString("test1", "test");
			NBTContainer test2 = new NBTContainer();
			test2.setString("test2", "test");
			test2.addCompound("test").setLong("time", System.currentTimeMillis());
			test1.mergeCompound(test2);
			if (!test1.getString("test1").equals(test1.getString("test2"))) {
				getLogger().warning("Wasn't able to merge Compounds! The Item-NBT-API may not work!");
				compatible = false;
			}

			if (!Bukkit.getWorlds().isEmpty()) {
				World world = Bukkit.getWorlds().get(0);
				try {
					if (!world.getEntitiesByClasses(Animals.class, Monster.class).isEmpty()) {
						getLogger().info("Testing Entity NBT!");
						NBTEntity nbte = new NBTEntity(
								world.getEntitiesByClasses(Animals.class, Monster.class).iterator().next());
						getLogger().info(nbte.asNBTString());
						nbte.setString("INVALIDEKEY", "test");
						getLogger().info("Entity NBT seems to work!");
					}
				} catch (Exception ex) {
					getLogger().warning("Wasn't able to use NBTEntities! The Item-NBT-API may not work!");
					compatible = false;
					ex.printStackTrace();
				}
				/*  try {
                    Block block = world.getBlockAt(world.getSpawnLocation().getBlockX(), 255, world.getSpawnLocation().getBlockZ());
                    if (block.getType() == Material.AIR) {
                        getLogger().info("Testing Tile NBT!");
                        block.setType(Material.CHEST);
                        NBTTileEntity tile = new NBTTileEntity(block.getState());
                        getLogger().info(tile.asNBTString());
                        tile.setString("Lock", "test");
                        getLogger().info(tile.asNBTString());
                        block.setType(Material.AIR);
                        getLogger().info("Tile NBT seems to work!");
                    }
                } catch (Exception ex) {
                    getLogger().warning("Wasn't able to use NBTTiles! The Item-NBT-API may not work!");
                    compatible = false;
                    ex.printStackTrace();
                }*/
			}

		} catch (Exception ex) {
			getLogger().log(Level.SEVERE, null, ex);
			compatible = false;
		}

		String checkMessage = "Plugins that don't check properly may throw Exeptions, crash or have unexpected behaviors!";
		if (compatible) {
			if (jsonCompatible) {
				getLogger().info("Success! This version of Item-NBT-API is compatible with your server.");
			} else {
				getLogger()
				.info("General Success! This version of Item-NBT-API is mostly compatible with your server. JSON serialization is not working properly. "
						+ checkMessage);
			}
		} else {
			getLogger().warning("WARNING! This version of Item-NBT-API seems to be broken with your Spigot version! "
					+ checkMessage);
		}

	}

	public void testJson() {
		if (!MinecraftVersion.hasGsonSupport()) {
			getLogger().warning(
					"Wasn't able to find Gson! The Item-NBT-API may not work with Json serialization/deserialization!");
			return;
		}
		try {
			ItemStack item = new ItemStack(Material.STONE, 1);
			NBTItem nbtItem = new NBTItem(item);

			nbtItem.setObject(JSON_TEST_KEY, new SimpleJsonTestObject());

			if (!nbtItem.hasKey(JSON_TEST_KEY)) {
				getLogger().warning(
						"Wasn't able to find JSON key! The Item-NBT-API may not work with Json serialization/deserialization!");
				jsonCompatible = false;
			} else {
				SimpleJsonTestObject simpleObject = nbtItem.getObject(JSON_TEST_KEY, SimpleJsonTestObject.class);
				if (simpleObject == null) {
					getLogger().warning(
							"Wasn't able to check JSON key! The Item-NBT-API may not work with Json serialization/deserialization!");
					jsonCompatible = false;
				} else if (!(STRING_TEST_VALUE).equals(simpleObject.getTestString())
						|| simpleObject.getTestInteger() != INT_TEST_VALUE
						|| simpleObject.getTestDouble() != DOUBLE_TEST_VALUE
						|| !simpleObject.isTestBoolean() == BOOLEAN_TEST_VALUE) {
					getLogger().warning(
							"One key does not equal the original value in JSON! The Item-NBT-API may not work with Json serialization/deserialization!");
					jsonCompatible = false;
				}
			}
		} catch (Exception ex) {
			getLogger().log(Level.SEVERE, null, ex);
			getLogger().warning(ex.getMessage());
			jsonCompatible = false;
		}
	}

	public boolean isCompatible() {
		return compatible;
	}

	public static NBTItem getNBTItem(ItemStack item) {
		return new NBTItem(item);
	}

	//region STATIC FINAL VARIABLES
	private static final String STRING_TEST_KEY = "stringTest";
	private static final String INT_TEST_KEY = "intTest";
	private static final String DOUBLE_TEST_KEY = "doubleTest";
	private static final String BOOLEAN_TEST_KEY = "booleanTest";
	private static final String JSON_TEST_KEY = "jsonTest";
	private static final String COMP_TEST_KEY = "componentTest";


	private static final String STRING_TEST_VALUE = "TestString";
	private static final int INT_TEST_VALUE = 42;
	private static final double DOUBLE_TEST_VALUE = 1.5d;
	private static final boolean BOOLEAN_TEST_VALUE = true;

	//end region STATIC FINAL VARIABLES

	public static class SimpleJsonTestObject {
		private String testString = STRING_TEST_VALUE;
		private int testInteger = INT_TEST_VALUE;
		private double testDouble = DOUBLE_TEST_VALUE;
		private boolean testBoolean = BOOLEAN_TEST_VALUE;

		public SimpleJsonTestObject() {
		}

		public String getTestString() {
			return testString;
		}

		public void setTestString(String testString) {
			this.testString = testString;
		}

		public int getTestInteger() {
			return testInteger;
		}

		public void setTestInteger(int testInteger) {
			this.testInteger = testInteger;
		}

		public double getTestDouble() {
			return testDouble;
		}

		public void setTestDouble(double testDouble) {
			this.testDouble = testDouble;
		}

		public boolean isTestBoolean() {
			return testBoolean;
		}

		public void setTestBoolean(boolean testBoolean) {
			this.testBoolean = testBoolean;
		}
	}

}
