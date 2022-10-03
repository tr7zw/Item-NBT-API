
Creating an NBT Wrapper:
```java
NBTItem nbti = new NBTItem(ItemStack);

NBTEntity nbtent = new NBTEntity(Entity); // Vanilla tags only!

NBTTileEntity tent = new NBTTileEntity(block.getState()); // Vanilla tags only!

NBTFile file = new NBTFile(new File("directory"), "test.nbt")); // Will be created at ./directory/test.nbt

NBTContainer container =  new NBTContainer(json); // Parse in json

NBTContainer container =  new NBTContainer(); // Empty NBTCompound
```

Using an NBT Wrapper:
```java
//Set
nbti.setString("Stringtest", "Teststring");
nbti.setInteger("Inttest", 42);
nbti.setDouble("Doubletest", 1.5d);
nbti.setBoolean("Booleantest", true);
//More are available!

//Access/create lists
nbtent.getCompoundList("Attributes");
nbtent.getStringList("Attributes");

//Get
nbti.getString("Stringtest");
nbti.getInteger("Inttest");
nbti.getDouble("Doubletest");
nbti.getBoolean("Booleantest");
nbti.getKeys(); //Get all Tags
nbti.hasKey("Key");
nbti.removeKey("Key");

nbti.addCompound("subtag"); // Create NBTTagCombounds(Imagine folders)

NBTCompound comp = nbti.getCompound("subtag"); // Get an NBT Compound
comp.getParent(); // Get Compound parent(null at root)
nbti.setObject("myobject", new SimpleJsonTestObject()); // Save Objects via Gson
SimpleJsonTestObject simpleObject = nbti.getObject("myobject", SimpleJsonTestObject.class); // Get an Gson object
B.mergeCompound(A); // Merge the data from compound A into B (Like the /data merge command)
comp.asNBTString(); // Get the nbt as a Json string

```