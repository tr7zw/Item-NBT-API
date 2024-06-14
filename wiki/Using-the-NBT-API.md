## General usage

### Basics overview

Most of the things are exposed inside the [NBT](https://tr7zw.github.io/Item-NBT-API/v2-api/de/tr7zw/changeme/nbtapi/NBT.html) class.

#### Basic getting and setting of data

[ReadableNBT](https://tr7zw.github.io/Item-NBT-API/v2-api/de/tr7zw/changeme/nbtapi/iface/ReadableNBT.html) and [ReadWriteNBT](https://tr7zw.github.io/Item-NBT-API/v2-api/de/tr7zw/changeme/nbtapi/iface/ReadWriteNBT.html) are used to read/write nbt:

```java
// Creating a new empty NBT tag
ReadWriteNBT nbt = NBT.createNBTObject();

// Setting nbt
nbt.setString("Stringtest", "Teststring");
nbt.setInteger("Inttest", 42);
nbt.setDouble("Doubletest", 1.5);
nbt.setBoolean("Booleantest", true);
// More are available! Ask your IDE, or see Javadoc for suggestions!

// Getting nbt
String s1 = nbt.getString("Stringtest");
int i1 = nbt.getInteger("Inttest");
double d = nbt.getDouble("Doubletest");
boolean b = nbt.getBoolean("Booleantest");
// Or, alternatively
String s2 = nbt.getOrDefault("Stringtest", "fallback_value");
Integer i2 = nbt.getOrNull("Inttest", Integer.class);

// Keys manipulation
nbt.getKeys(); // Get all Tags
nbt.hasTag("key"); // Check whether the Tag exists
nbt.hasTag("key", NbtType.NBTTagString); // Check whether the Tag exists and matches the type
nbt.removeKey("key"); // Remove the Tag
NBTType nbtType = getType("key"); // Get the type of the Tag

// Subtag compounds
nbt.getCompound("subtag"); // Get a subtag, or null
nbt.getOrCreateCompound("subtag"); // Get or create a subtag
```

#### Converting NBT to String and the String back to NBT

```java
// Parse Mojang-Json string to nbt
ReadWriteNBT nbt = NBT.parseNBT("{Silent:1b,Invulnerable:1b,Glowing:1b,IsBaby:1b}");
// Get the NBT back as a Mojang-Json string (works with any NBT object)
String json = nbt.toString();
// Turn back into nbt again
ReadWriteNBT nbt2 = NBT.parseNBT(json);
```

#### Merging Compounds

You can "merge" the data from any given Compound onto some other Compound. This will overwrite the tags with the same name.

Example:

`Tag1: {Num1:1, Num2:2} Tag2: {Num1:7, Num3:3}`

After merging Tag2 on Tag1:

```java
tag1.mergeCompound(tag2);
```

`Tag1: {Num1:7, Num2:2, Num3:3} Tag2: {Num1:7, Num3:3}`

#### Resolving data

```java
// For accessing deeply nested subtags, you may use the resolve methods
// For example, the following code:
nbt.resolveOrCreateCompound("foo.bar.baz");
// Will get/create the same subtag compound as:
nbt.getOrCreateCompound("foo").getOrCreateCompound("bar").getOrCreateCompound("baz");

// Dots in keys may be escaped with a backslash
nbt.resolveCompound("foo.some\\.key.baz"); // Gets the nested compound, or null

// Similarly, you may also fetch values from nested subtags
String s = nbt.resolveOrDefault("foo.bar.Stringtest", "fallback_value");
Integer i = nbt.resolveOrNull("foo\\.bar.baz.Inttest", Integer.class);
```

#### Accessing/creating lists and their data

> [!NOTE]
> The lists will be created if they do not exist, there are no setters for lists!

```java
// Get or create a string list
ReadWriteNBTList<String> stringList = nbt.getStringList("list_key");
stringList.add("value");

// You can obtain the value back just like with normal Lists
nbt.getStringList("list_key").get(0);
// Or by fetching it
nbt.resolveOrNull("list_key[0]", String.class); // Get the first value in list
// You can also use negative number to get the value from the end of the list/array
nbt.resolveOrDefault("list_key[-1]", "fallback_value"); // Get the last value in list, or use the default


// NBT compound lists
ReadWriteNBTCompoundList nbtList = nbt.getOrCreateCompound("foo").getCompoundList("other_key");
ReadWriteNBT nbtListEntry = addCompound();
nbtListEntry.setBoolean("bar", true);

// You can also fetch compounds in lists
nbt.resolveCompound("foo.other_key[0]"); // Get the first compound in list, or null
nbt.resolveOrCreateCompound("foo.other_key[1]"); // Get the second compound in list, or create it
// Or fetch data from them
boolean bar = nbt.resolveOrDefault("foo.other_key[0].bar", false);
```

### Working with items

```java
// Setting data
NBT.modify(itemStack, nbt -> {
    nbt.setString("Stringtest", "Teststring");
    nbt.setInteger("Inttest", 42);
    nbt.setDouble("Doubletest", 1.5d);
    nbt.setBoolean("Booleantest", true);
    // More are available! Ask your IDE, or see Javadoc for suggestions!
});

// Reading data
NBT.get(itemStack, nbt -> {
    String stringTest = nbt.getString("Stringtest");
    int intTest = nbt.getOrDefault("Inttest", 0);
    // Do more stuff
});

// Getting data
String string = NBT.get(itemStack, (String) nbt -> nbt.getString("Stringtest"));

// Modifying and getting data
int someValue = NBT.modify(itemStack, nbt -> {
    int i = nbt.getOrDefault("key", 0) + 1;
    nbt.setInteger(i);
    return i;
});

// Updating ItemMeta using NBT
NBT.modify(itemStack, nbt -> {
    nbt.setInteger("kills", nbt.getOrDefault("kills", 0) + 1);
    nbt.modifyMeta((meta, readOnlyNbt) -> { // Do not modify the nbt while modifying the meta!
        meta.setDisplayName("Kills: " + readOnlyNbt.getOrDefault("kills", 0));
    });
    // Do more stuff
});
```

> [!IMPORTANT]
> Since Minecraft 1.20.5 ItemStacks no longer have vanilla nbt during runtime.
> 
> As a workaround, you may use the following code:

```java
// NOTE: This code is only for 1.20.5+!
NBT.modifyComponents(item, nbt -> {
    nbt.setString("minecraft:custom_name", "{\"extra\":[\"foobar\"],\"text\":\"\"}");
});
```

### Working with tiles/entities

Working with entities and tile entities is similar to working items.

#### Accessing vanilla nbt

The example code for entities is applicable for tile entities too.

```java
// Obtain data
boolean silent = NBT.get(entity, (boolean) nbt -> nbt.getBoolean("Silent"));
// Modify data
NBT.modify(entity, nbt -> {
    nbt.setBoolean("Silent", true);
    nbt.setByte("CanPickUpLoot", (byte) 1);
});
```

#### Accessing custom data

For reading/storing custom data on tiles/entities, you should use methods that end with PersistentData.

```java
// Obtain data
boolean test = NBT.getPersistentData(entity, (boolean) nbt -> nbt.getBoolean("custom_key"));
// Modify data
NBT.modifyPersistentData(entity, nbt -> {
    nbt.setBoolean("custom_key", true);
    nbt.setByte("custom_byte", (byte) 1);
});
```

> [!IMPORTANT]
> When working with tile entities, make sure that the block entity exists in the world.
> 
> For example, you may not be able to add data to a chest in `BlockPlaceEvent` because the chest hasn't been placed yet. In such case, you can delay your actions by one tick or set the block to chest manually.

#### Simulate the "/data merge" command

```java
NBT.modify(zombie, nbt -> {
    nbt.mergeCompound(NBT.parseNBT("{Silent:1b,Invulnerable:1b,Glowing:1b,IsBaby:1b}"));
});
```

### Working with blocks

You can store data in tile blocks (tile entities like chest, furnace, etc.) using the examples above, but normal blocks do not have the nbt data.

// TODO nbt chunk & nbt block

## Extras

> [!TIP]
> Besides this page, you may also take a look at some code examples at [example usages](https://github.com/tr7zw/Item-NBT-API/wiki/Example-Usages).

### NBT files

```java
// Creating nbt file
// NBTFile will automatically create the file if it does not exist
NBTFile file = new NBTFile(new File("directory"), "test.nbt"); // Will be created at ./directory/test.nbt
// Setting data 
file.setString("foo", "bar");
// Saving file
file.save();

// Alternatively, you may use helper methods in NBTFile class
// To read the file without creating it if does not exist:
ReadWriteNBT nbt = NBTFile.readFrom(file);
// To save nbt to the file:
NBTFile.saveTo(file, nbt);
```

### Converting Minecraft Objects to NBT and Strings

The basic idea is that you can convert data into different representations:
Minecraft Objects <-> NBT <-> String (Mojang-Json)

#### Items

// TODO explanation about ItemStack object nbt vs get/setItemStack nbt

```java
// Saving
ReadWriteNBT nbt = NBT.itemStackToNBT(itemStack);
ReadWriteNBT nbt = NBT.itemStackArrayToNBT(itemStacks);
// Restoring
ItemStack itemStack = NBT.itemStackFromNBT(nbt);
ItemStack[] itemStacks = NBT.itemStackArrayFromNBT(nbt);
```

#### Tiles/Entities

// TODO is there a more elegant solution?

```java
// Saving
ReadWriteNBT entityNbt = NBT.parseNBT(NBT.get(entity, (String) nbt -> nbt.toString()));
// Restoring
NBT.modify(entity, nbt -> {
    // You might want also to filter out entityNbt first,
    // e.g. remove some data like location, uuid, entityId, etc.
    nbt.mergeCompound(entityNbt);
});
```

#### Game profiles
```java
// Saving
ReadWriteNBT nbt = NBT.gameProfileToNBT(profile);
// Restoring
GameProfile profile = NBT.gameProfileFromNBT(nbt);
```

### NBT proxies

// TODO

### Data converter utils

// TODO
