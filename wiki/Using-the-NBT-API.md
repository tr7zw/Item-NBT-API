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
NBTType nbtType = nbt.getType("key"); // Get the type of the Tag

// Subtag compounds
nbt.getCompound("subtag"); // Get a subtag, or null
nbt.getOrCreateCompound("subtag"); // Get or create a subtag
```

#### Converting NBT to String and the String back to NBT

```java
// Parse Mojang-Json string to nbt
ReadWriteNBT nbt = NBT.parseNBT("{Health:20.0f,Motion:[0.0d,10.0d,0.0d],Silent:1b}");
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

To simplify working with deeply nested NBT, resolve methods allow directly getting or working with these nested tags.

Compounds are separated by dots ``.`` In case you need a ``.`` inside a key, it can be escaped with a backslash ``\``.

```java
// For example, the following code:
nbt.resolveOrCreateCompound("foo.bar.baz");
// Will get/create the same subtag compound as:
nbt.getOrCreateCompound("foo").getOrCreateCompound("bar").getOrCreateCompound("baz");

// Get compound if exists, or null otherwise
nbt.resolveCompound("foo.some.key.baz");

// Sets foo/bar/baz/test to 42
nbt.resolveOrCreateCompound("foo.bar.baz").setInteger("test", 42);

// Example of a key with a . in it. Sets the key foo/some.key/baz/other to 123
nbt.resolveOrCreateCompound("foo.some\\.key.baz").setInteger("other", 123);

// Similarly, you may also fetch values from nested compounds/subtags
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

// Get the list type, NBTTagString in this case
NBTType type = nbt.getListType("list_key");

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

// You can fetch compounds in lists
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
String string = NBT.get(itemStack, nbt -> (String) nbt.getString("Stringtest"));

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

###### Changing vanilla nbt on 1.20.5+

> [!IMPORTANT]
> Since Minecraft 1.20.5 ItemStacks no longer have vanilla nbt during runtime.
> Any calls like ``NBT.get`` or ``NBT.modify`` will access only the item's ``custom_data`` component.
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
boolean silent = NBT.get(entity, nbt -> (boolean) nbt.getBoolean("Silent"));
// Modify data
NBT.modify(entity, nbt -> {
    nbt.setBoolean("Silent", true);
    nbt.setByte("CanPickUpLoot", (byte) 1);
});
```

#### Accessing custom data

For reading/storing custom data on tiles/entities, you should use methods that end with PersistentData.

> [!IMPORTANT]
> When working with tile entities, make sure that the block entity exists in the world.
>
> For example, you may not be able to add data to a chest in `BlockPlaceEvent` because the chest hasn't been placed yet. In such case, you can delay your actions by one tick or set the block to chest manually.

```java
// Obtain data
boolean test = NBT.getPersistentData(entity, nbt -> (boolean) nbt.getBoolean("custom_key"));
// Modify data
NBT.modifyPersistentData(entity, nbt -> {
    nbt.setBoolean("custom_key", true);
    nbt.setByte("custom_byte", (byte) 1);
});
```

> [!NOTE]
> If you plan to store some data for players, you might also consider using an external storage instead to not clutter the players' data files inside the world folder. Since any data written to the persistent storage is there forever, you can leave redundant data behind when your plugin is removed, but it's perfectly fine to store data in there otherwise.
>
> Storage solutions like per-player [NBT files](https://github.com/tr7zw/Item-NBT-API/wiki/Using-the-NBT-API#nbt-files) might be more than enough (and is basically what vanilla does, but you'll have your own file instead of using the one inside the world). Cache the file's data on join and save on quit/server shutdown, optionally adding auto saving.

#### Simulate the "/data merge" command

Applicable for items/tiles/entities/etc.

```java
NBT.modify(zombie, nbt -> {
    nbt.mergeCompound(NBT.parseNBT("{Silent:1b,Invulnerable:1b,Glowing:1b,IsBaby:1b}"));
});
```

### Working with blocks

You can store data in tile entities (block entities like chest, furnace, etc.) using the examples from section above, but normal blocks do not have the nbt data.

Thus, you have to use your own block data storage to store custom block data.

You can store data inside Chunks since 1.16.4, and NBT-API allows you to do so by using ``NBTChunk``:

```java
ReadWriteNBT nbt = new NBTChunk(chunk).getPersistentDataContainer();
```

Similarly, there is ``NBTBlock``, which allows you to store block data inside chunk's data.

```java
// Block's data will be stored in Chunk's data in "blocks.x_y_z" subtag
ReadWriteNBT nbt = new NBTBlock(block).getData();
```

**However**, keep in mind that this data is linked only to the location, and if the block gets broken/changed/exploded/moved/etc., the data will still be on that location unless manually cleared/moved!

Moreover, since the data is stored inside Chunk, this will increase the chunk's size on the disk.

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

While ``NBT.get/modify`` allows you to modify item's direct nbt (or only the ``custom_data`` container since 1.20.5), the ItemStack object nbt represents the item's serialized data. It includes item type, amount and all extra item tags.

For example, when ``NBT.get`` looks like this:

`{foo:"bar",points:12,test:1b}`

The ItemStack object nbt from ``NBT.itemStackToNBT`` may look like this:

`{components:{"minecraft:custom_data":{foo:"bar",points:12,test:1b}},count:2,id:"minecraft:stone"}`

Or like this in versions prior to 1.20.5:

`{Count:2b,id:"minecraft:stone",tag:{foo:"bar",points:12,test:1b}}`

###### Serializing/deserializing items

```java
// Saving
ReadWriteNBT nbt = NBT.itemStackToNBT(itemStack);
ReadWriteNBT nbt = NBT.itemStackArrayToNBT(itemStacks);
// Restoring
ItemStack itemStack = NBT.itemStackFromNBT(nbt);
ItemStack[] itemStacks = NBT.itemStackArrayFromNBT(nbt);
// Reminder (NBT <-> String)
String json = nbt.toString();
ReadWriteNBT nbt = NBT.parseNBT(json);
```

#### Tiles/Entities

```java
// Saving
ReadWriteNBT entityNbt = NBT.parseNBT(NBT.get(entity, nbt -> (String) nbt.toString()));
// Restoring
NBT.modify(entity, nbt -> {
    // You might also want to filter out entityNbt first,
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

### Interface proxies

You may define your own interfaces extending ``NBTProxy`` to create wrappers around nbt.

Methods starting with ``has``/``get``/``set`` will be interpreted as their respective calls.

```java
interface TestInterface extends NBTProxy {
    // Runs: return nbt.hasTag("kills");
    boolean hasKills();
    // Runs: nbt.setInteger("kills", amount);
    void setKills(int amount);
    // Runs: return nbt.getInteger("kills");
    int getKills();

    // Also supported
    default void addKill() {
        setKills(getKills() + 1);
    }
}
```

Getters are also allowed to return other interfaces extending ``NBTProxy``.

And by using ``@NBTTarget`` annotation you may have a more gradual control over data. It allows to set the type of the method (``has``/``get``/``set``) and the data key.

```java
interface TestInterface extends NBTProxy {
    // Will get PointsInterface from data in "other" key
    @NBTTarget(type = Type.GET, value = "other")
    PointsInterface getOtherInterface();
}

interface PointsInterface extends NBTProxy {
    int getPoints();
    void setPoints(int points);
}
```

To support other data types like ItemStacks, you need to override the init method and register appropriate handlers.

```java
interface TestInterface extends NBTProxy {
    @Override
    default void init() {
        registerHandler(ItemStack.class, NBTHandlers.ITEM_STACK);
        registerHandler(ReadableNBT.class, NBTHandlers.STORE_READABLE_TAG);
        registerHandler(ReadWriteNBT.class, NBTHandlers.STORE_READWRITE_TAG);
    }

    ItemStack getItem();
    void setItem(ItemStack item);

    ReadWriteNBT getBlockStateTag();
    void setBlockStateTag(ReadableNBT blockState);
}
```

``NBTHandlers`` class contains some pre-defined handlers.

###### Custom handlers

If you need to support custom data types that aren't available in ``NBTHandlers``, you can write your own by creating a new ``NBTHandler``.

You can refer to [NBTHandlers](https://github.com/tr7zw/Item-NBT-API/blob/master/item-nbt-api/src/main/java/de/tr7zw/changeme/nbtapi/handler/NBTHandlers.java) class to see how the default implementations are done.

### Data fixer utils

``DataFixerUtil`` allows updating nbt from versions since 1.12.2 to more recent ones.

For example, given the input from 1.12.2:

`{Count:42,id:"cobblestone",tag:{display:{Name:"test"},ench:[{id:34,lvl:3}]}}`

You can update it to 1.20.6:

`{components:{"minecraft:custom_name":'{"text":"test"}',"minecraft:enchantments":{levels:{"minecraft:unbreaking":3}}},count:42,id:"minecraft:cobblestone"}`

By using the following code:

```java
DataFixerUtil.fixUpItemData(nbt, DataFixerUtil.VERSION1_12_2, DataFixerUtil.VERSION1_20_6);
```

You can also use `DataFixerUtil.getCurrentVersion()` to update the data to whatever version the server s running.
