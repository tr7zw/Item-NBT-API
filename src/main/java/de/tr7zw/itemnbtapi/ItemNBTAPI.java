package de.tr7zw.itemnbtapi;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class ItemNBTAPI extends JavaPlugin{

    private static boolean compatible = true;
    private static boolean jsonCompatible = true;
    
    @Override
    public void onEnable() {
        initMetrics();
        getLogger().info("Running NBT reflection test...");
        try {
            ItemStack item = new ItemStack(Material.STONE, 1);
            NBTItem nbtItem = new NBTItem(item);

            nbtItem.setString(STRING_TEST_KEY, STRING_TEST_VALUE);
			nbtItem.setInteger(INT_TEST_KEY, INT_TEST_VALUE);
			nbtItem.setDouble(DOUBLE_TEST_KEY, DOUBLE_TEST_VALUE);
			nbtItem.setBoolean(BOOLEAN_TEST_KEY, BOOLEAN_TEST_VALUE);

            item = nbtItem.getItem();

            if (!nbtItem.hasKey(STRING_TEST_KEY)) {
            	getLogger().warning("Wasn't able to check a key! The Item-NBT-API may not work!");
				compatible = false;
			}
			if (!(STRING_TEST_VALUE).equals(nbtItem.getString(STRING_TEST_KEY)) 
					|| nbtItem.getInteger(INT_TEST_KEY) != INT_TEST_VALUE
					|| nbtItem.getDouble(DOUBLE_TEST_KEY) != DOUBLE_TEST_VALUE 
					|| !nbtItem.getBoolean(BOOLEAN_TEST_KEY) == BOOLEAN_TEST_VALUE) {
				getLogger().warning("One key does not equal the original value! The Item-NBT-API may not work!");
				compatible = false;
			}
			nbtItem.setString(STRING_TEST_KEY, null);
			if (nbtItem.getKeys().size() != 3) {
				getLogger().warning("Wasn't able to remove a key (Got " + nbtItem.getKeys().size()
						+ " when expecting 3)! The Item-NBT-API may not work!");
				compatible = false;
			}
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, null, ex);
            compatible = false;
        }
        
        testJson();
        
        String checkMessage = "Plugins that don't check properly, may throw Exeptions, crash or have unexpected behaviors!";
        if(compatible){
        	if(jsonCompatible){
            	getLogger().info("Success! This version of Item-NBT-API is compatible with your server.");
            }else{
            	getLogger().info("General Success! This version of Item-NBT-API is mostoly compatible with your server. JSON serialization is not working properly. " + checkMessage);
            }
        }else{
            getLogger().warning("WARNING! This version of Item-NBT-API seems to be broken with your Spigot version! " + checkMessage);
        }
        
        
    }
    
    public void testJson(){
		try {
			ItemStack item = new ItemStack(Material.STONE, 1);
			NBTItem nbtItem = new NBTItem(item);
			
			nbtItem.setObject(JSON_TEST_KEY, new SimpleJsonTestObject());
			
			item = nbtItem.getItem();
			
			if(!nbtItem.hasKey(JSON_TEST_KEY)){
				getLogger().warning("Wasn't able to find JSON key! The Item-NBT-API may not work with Json serialization/deserialization!");
				jsonCompatible = false;
			}else{
				SimpleJsonTestObject simpleObject = nbtItem.getObject(JSON_TEST_KEY, SimpleJsonTestObject.class);
				if(simpleObject == null){
					getLogger().warning("Wasn't able to check JSON key! The Item-NBT-API may not work with Json serialization/deserialization!");
					jsonCompatible = false;
				}else if (!(STRING_TEST_VALUE).equals(simpleObject.getTestString()) 
						|| simpleObject.getTestInteger() != INT_TEST_VALUE
						|| simpleObject.getTestDouble() != DOUBLE_TEST_VALUE 
						|| !simpleObject.isTestBoolean() == BOOLEAN_TEST_VALUE) {
					getLogger().warning("One key does not equal the original value in JSON! The Item-NBT-API may not work with Json serialization/deserialization!");
					jsonCompatible = false;
				}
			}
		} catch (Exception ex) {
			getLogger().log(Level.SEVERE, null, ex);
			getLogger().warning(ex.getMessage());
			jsonCompatible = false;
		}
	}

    @Override
    public void onDisable() {
    }

    public boolean isCompatible() {
        return compatible;
    }
    
    public static NBTItem getNBTItem(ItemStack item){
        return new NBTItem(item);
    }

    private void initMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, null, ex);
        }
    }
    
    
  //region STATIC FINAL VARIABLES
  	private static final String STRING_TEST_KEY = "stringTest";
  	private static final String INT_TEST_KEY = "intTest";
  	private static final String DOUBLE_TEST_KEY = "doubleTest";
  	private static final String BOOLEAN_TEST_KEY = "booleanTest";
  	private static final String JSON_TEST_KEY = "jsonTest";
  	
  	private static final String STRING_TEST_VALUE = "TestString";
  	private static final  int INT_TEST_VALUE = 42;
  	private static final double DOUBLE_TEST_VALUE = 1.5d;
  	private static final boolean BOOLEAN_TEST_VALUE = true;
  	//end region STATIC FINAL VARIABLES
  	
  	public static class SimpleJsonTestObject{
  		private String testString = STRING_TEST_VALUE;
  		private int testInteger = INT_TEST_VALUE;
  		private double testDouble = DOUBLE_TEST_VALUE;
  		private boolean testBoolean = BOOLEAN_TEST_VALUE;
  		
  		public SimpleJsonTestObject(){
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
