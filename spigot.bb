[B][SIZE=6](Item/Entity/Tile) NBT API[/SIZE][/B]
[URL='https://ci.codemc.io/job/Tr7zw/job/Item-NBT-API/'][IMG]https://ci.codemc.org/buildStatus/icon?job=Tr7zw%2FItem-NBT-API[/IMG][/URL]  [URL='https://sonarcloud.io/dashboard?id=tr7zw_Item-NBT-API'][IMG]https://sonarcloud.io/api/project_badges/measure?project=tr7zw_Item-NBT-API&metric=ncloc[/IMG][/URL] [URL='https://bstats.org/plugin/bukkit/ItemNBTAPI'][IMG]https://img.shields.io/bstats/servers/1058.svg?color=green&label=OnlineServers&style=plastic[/IMG][/URL] [URL='https://bstats.org/plugin/bukkit/ItemNBTAPI'][IMG]https://img.shields.io/bstats/players/1058.svg?color=green&label=OnlinePlayers&style=plastic[/IMG] [/URL][URL='https://discordapp.com/invite/yk4caxM'][IMG]https://img.shields.io/discord/342814924310970398?color=%237289DA&label=Discord&logo=discord&logoColor=white[/IMG][/URL]
The NBT API allows you to add custom NBT tags to Itemstacks, TileEntities and Entities, or modify excisting ones!
It completely uses reflections to interact with NMS code and works with all the latest versions. On server start the plugin checks all reflections.

Tested on Spigot
[LIST]
[*](1.7*)1.8.8-1.19
[*]1.7.10 Crucible
[*]The NBTInjector will not work with: 1.12.0, 1.12.1, 1.13.0 (Please just update to the final release of your chosen version)

[*]On startup you will get a notification if there is a version problem!
[/LIST]
* 1.7 Notes: Use a 1.7 with R4 (1.7.10), NBTLists may not work, everything using Gson is disabled(Add Gson before the NBTAPI loads, to turn it back on), and you can't get the NBTTypes, because 1.7 is missing this feature. TLDR: 1.7.10 is somewhat broken and not everything might work due to 1.7.10 limitations.

[B]What do I have to do as a server owner?[/B]
Just download the jar and drop it in the plugins folder. Done.
Note that outdated plugins may ask for "ItemNBTAPI". In this case download version 1.8.3 from the versions tab. The outdated "ItemNBTAPI" and "NBTApi" can be used at the same time.
[B]Important for version 2.0.0:[/B] Don't reload while using the NBTInjector. Reloading, in general, is a horrible thing and it will break the NBTInjector in horrible ways! When updating plugins/changing configs always restart the server normally!

[B]How can I use the API as developer?[/B]
Create the NBT Wrapper:
[CODE]NBTItem nbti = new NBTItem(item);
NBTEntity nbtent = new NBTEntity(zombie); //Only for vanilla tags!
NBTTileEntity tent = new NBTTileEntity(block.getState()); //Only for vanilla tags!
NBTFile file = new NBTFile(new File(getDataFolder(), "test.nbt"));
NBTContainer container =  new NBTContainer(json); //Parse in json
[/CODE]

Add/Get/Override NBT tags:
[CODE]//Set
nbti.setString("Stringtest", "Teststring");
nbti.setInteger("Inttest", 42);
nbti.setDouble("Doubletest", 1.5d);
nbti.setBoolean("Booleantest", true);
//More are available!
//Get
nbti.getString("Stringtest");
nbti.getInteger("Inttest");
nbti.getDouble("Doubletest");
nbti.getBoolean("Booleantest");
//Get all Tags
nbti.getKeys();
//Check for a tag
nbti.hasTag("Key");
//Remove a tag
nbti.removeKey("Key");
//or
nbti.setString("Stringtest", null);
//Create NBTTagCombounds(Imagine folders)
nbti.addCompound("subtag");
//Get Compound
NBTCompound comp = nbti.getCompound("subtag");
//Get Compound parent(null at root)
comp.getParent();
//Save Objects via Gson
nbti.setObject("myobject", new SimpleJsonTestObject());
//Get Objects
SimpleJsonTestObject simpleObject = nbti.getObject("myobject", SimpleJsonTestObject.class);
//Merge the data from compound A into B (Like the /data merge command)
B.mergeCompound(A);
//Get the nbt as json
comp.asNBTString();

//Access/create lists
nbtent.getCompoundList("Attributes");
nbtent.getStringList("Attributes");
[/CODE]

And finally get back the Bukkit Itemstack(Changes on Tiles/Entities will happen directly):
[CODE]nbti.getItem();[/CODE]
Saving the NBTFile:
[CODE]file.save();[/CODE]

[B]Important note regarding Tiles/Entities
[B]Starting with 1.14+ you can use the getPersistentDataContainer() (for NBT(Tile)-Entity's) to save custom tags. This replaces the now mentioned NBT-Injector.[/B][/B]
Non-vanilla tags can't usually be added to to Tiles/Entities. Thanks to
[URL='https://www.spigotmc.org/members/inventivetalent.6643/'][IMG]https://tr7zw.dev/nbtapi/spigotPeople.php?id=6643[/IMG][/URL] the NBTAPI now includes a tool called "NBTInjector", that compiles custom (Tile-)Entity classes during runtime to allow custom nbt. The custom data will remain during chunk unloads/server restarts. [B]Data will be lost when chunks get loaded without the NBTAPI loaded! Since 2.1.0 the NBTInjector won't be enabled by default! Please enable it during onLoad with NBTInjector.inject(); if you are using it![/B]
Adding custom nbt to tiles:
[CODE]
NBTCompound comp = NBTInjector.getNbtData(block.getState());
comp.setString("Foo", "Bar");
[/CODE]
Adding custom nbt to entities:
[CODE]
Entity ent = world.getEntitiesByClasses(Animals.class, Monster.class).iterator().next();
ent = NBTInjector.patchEntity(ent); //This needs to be called at least once on the entity to enable custom nbt!
NBTCompound comp = NBTInjector.getNbtData(ent);
comp.setString("Hello", "World");
[/CODE]
[B]Note[/B]: Before NBTInjector.getNbtData can be used on an Entity, NBTInjector.patchEntity has to be used on it at least once. This method will check if the entity is patched, and if not remove the entity and respawn it with the correct class. This destroys the original entity reference(the new one is returned) and the entity will blink once ingame. Bestcase you call this method right when the entity is spawned to prevent unwanted behavior(the blink, entities interacting with the Entity beeing patched like riding/leashes/combat may have problems).

[B]Maven[/B]
(Historicly it was Jitpack. Don't use it anymore!)
NBTApi is hosted and compiled by CodeMC.
[URL='https://ci.codemc.org/job/Tr7zw/job/Item-NBT-API/']JenkinsBuild[/URL] [URL='https://github.com/CodeMC/CI-Documentation']CodeMC-Info[/URL]
[code]<repositories>
...
<!-- CodeMC -->
<repository>
<id>codemc-repo</id>
<url>https://repo.codemc.org/repository/maven-public/</url>
<layout>default</layout>
</repository>
...
</repositories>
[/code]
The API can be used in different ways:
[SPOILER="Normal Plugin dependency"]
Just use the Plugin as Maven dependency and remember to add "NBTAPI" as dependency into the plugin.yml. This way server owners will have to install the api and keep it updated.
[CODE]
<dependency>
  <groupId>de.tr7zw</groupId>
  <artifactId>item-nbt-api-plugin</artifactId>
  <version>2.15.5-SNAPSHOT</version>
</dependency>
[/CODE]
[/SPOILER]
[SPOILER="Shading the API into your plugin"]
Using this method the API gets copied into your plugin. Server owners don't have to install the API on their own, but you need to keep it updated. Also [B]relocating is required[/B]!
[CODE]
<dependency>
  <groupId>de.tr7zw</groupId>
  <artifactId>item-nbt-api</artifactId>
  <version>2.15.5-SNAPSHOT</version>
</dependency>
...
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.2.1</version>
        <executions>
          <execution>
            <id>shade</id>
            <phase>package</phase>
            <goals>
              <goal>shade</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <relocations>
            <relocation>
              <pattern>de.tr7zw.changeme.nbtapi</pattern>
              <shadedPattern>YOUR PACKGE WHERE THE API SHOULD END UP</shadedPattern>
            </relocation>
          </relocations>
        </configuration>
      </plugin>
   </plugins>
[/CODE]
[/SPOILER]

[B]Example Code[/B]
[SPOILER="Zombie that will pickup loot and does 0.5 Hearts of damage"]
[CODE]
 Zombie z = (Zombie) b.getWorld().spawnEntity(b.getLocation().add(0, 1, 0), EntityType.ZOMBIE);
                NBTEntity nbtent = new NBTEntity(z);
                nbtent.setByte("CanPickUpLoot", (byte) 1);
                NBTList list = nbtent.getCompoundList("Attributes");
                for(int i = 0; i < list.size(); i++){
                    NBTListCompound lc = list.getCompound(i);
                    if(lc.getString("Name").equals("generic.attackDamage")){
                        lc.setDouble("Base", 0.5d);
                    }
                }
[/CODE]
[/SPOILER]

[SPOILER="Simulate the command /data merge entity @e[type=zombie] {Silent:1b,Invulnerable:1b,Glowing:1b,IsBaby:1b}"]
[CODE]
                NBTEntity nbtent = new NBTEntity(zombie);
                nbtent.mergeCompound(new NBTContainer("{Silent:1b,Invulnerable:1b,Glowing:1b,IsBaby:1b}"));
[/CODE]
[/SPOILER]

[SPOILER="Creating a Head with texture, healthboost and some more tags"]
[CODE]
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        NBTItem nbti = new NBTItem(head);
        NBTCompound disp = nbti.addCompound("display");
        disp.setString("Name", "Testitem");
        List<String> l = disp.getStringList("Lore");
        l.add("Some lore");
        NBTCompound skull = nbti.addCompound("SkullOwner");
        skull.setString("Name", "tr7zw");
        skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");
        NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        texture.setString("Signature", "XpRfRz6/vXE6ip7/vq+40H6W70GFB0yjG6k8hG4pmFdnJFR+VQhslE0gXX/i0OAGThcAVSIT+/W1685wUxNofAiy+EhcxGNxNSJkYfOgXEVHTCuugpr+EQCUBI6muHDKms3PqY8ECxdbFTUEuWxdeiJsGt9VjHZMmUukkGhk0IobjQS3hjQ44FiT1tXuUU86oAxqjlKFpXG/iXtpcoXa33IObSI1S3gCKzVPOkMGlHZqRqKKElB54I2Qo4g5CJ+noudIDTzxPFwEEM6XrbM0YBi+SOdRvTbmrlkWF+ndzVWEINoEf++2hkO0gfeCqFqSMHuklMSgeNr/YtFZC5ShJRRv7zbyNF33jZ5DYNVR+KAK9iLO6prZhCVUkZxb1/BjOze6aN7kyN01u3nurKX6n3yQsoQQ0anDW6gNLKzO/mCvoCEvgecjaOQarktl/xYtD4YvdTTlnAlv2bfcXUtc++3UPIUbzf/jpf2g2wf6BGomzFteyPDu4USjBdpeWMBz9PxVzlVpDAtBYClFH/PFEQHMDtL5Q+VxUPu52XlzlUreMHpLT9EL92xwCAwVBBhrarQQWuLjAQXkp3oBdw6hlX6Fj0AafMJuGkFrYzcD7nNr61l9ErZmTWnqTxkJWZfZxmYBsFgV35SKc8rkRSHBNjcdKJZVN4GA+ZQH5B55mi4=");
        texture.setString("Value", "eyJ0aW1lc3RhbXAiOjE0OTMwNDkwMTcxNTIsInByb2ZpbGVJZCI6ImZjZTAzMjNkN2Y1MDQzMTc5NzIwNWY2YjE0Y2Y3OGVhIiwicHJvZmlsZU5hbWUiOiJ0cjd6dyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTI3NDZlNWU5OGMwZWRmZTU1YTI3ZGRjNjUxMmJmNjllYzJiYmNlNmM3ZmNhNTQ5YmEzNjZkYThiNTRjZTRkYiJ9fX0=");

        NBTCompoundList attribute = nbti.getCompoundList("AttributeModifiers");
        NBTListCompound mod1 = attribute.addCompound();
        mod1.setInteger("Amount", 10);
        mod1.setString("AttributeName", "generic.maxHealth");
        mod1.setString("Name", "generic.maxHealth");
        mod1.setInteger("Operation", 0);
        mod1.setInteger("UUIDLeast", 59664);
        mod1.setInteger("UUIDMost", 31453);

        nbti.setInteger("HideFlags", 4);
        nbti.setBoolean("Unbreakable", true);
        head = nbti.getItem();
        e.getPlayer().getInventory().addItem(head);
[/CODE]
[/SPOILER]

[SPOILER="Printing the NBT tags"]
[CODE]
                Ocelot m = (Ocelot) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.OCELOT);
                NBTEntity ent = new NBTEntity(m);
                System.out.println(ent);
[/CODE]
Output:
[CODE]
-AgeLocked: 0b
-HurtByTimestamp: 0
-CatType: 0
-Leashed: 0b
-Health: 10.0f
-Sitting: 0b
-Attributes: [0:{Base:10.0d,Name:"generic.maxHealth"},1:{Base:0.0d,Name:"generic.knockbackResistance"},2:{Base:0.30000001192092896d,Name:"generic.movementSpeed"},3:{Base:0.0d,Name:"generic.armor"},4:{Base:0.0d,Name:"generic.armorToughness"},5:{Base:16.0d,Modifiers:[0:{UUIDMost:-8783172028836920024L,UUIDLeast:-6820414278857408689L,Amount:-0.030166473586416633d,Operation:1,Name:"Random spawn bonus"}],Name:"generic.followRange"}]
-FallFlying: 0b
-LeftHanded: 0b
-ForcedAge: 0
-AbsorptionAmount: 0.0f
-HandItems: [0:{},1:{}]
-ArmorDropChances: [0:0.085f,1:0.085f,2:0.085f,3:0.085f]
-OwnerUUID: ""
-InLove: 0
-DeathTime: 0s
-ArmorItems: [0:{},1:{},2:{},3:{}]
-CanPickUpLoot: 0b
-HandDropChances: [0:0.085f,1:0.085f]
-PersistenceRequired: 0b
-HurtTime: 0s
-Age: 0
[/CODE]
[/SPOILER]

[SPOILER="Locking Chests"]
[CODE]                NBTTileEntity tent = new NBTTileEntity(b.getState());
                tent.setString("Lock", "test");[/CODE]
[/SPOILER]

[B]More Infos:[/B]
[LIST]
[*][URL]https://www.spigotmc.org/threads/override-base-item-attribute-value.236434/#post-2387235[/URL]
[/LIST]

[B]This API can't be used for:[/B]
[LIST]
[*][I][SIZE=4][S]Adding custom tags to TileEntities/Entities[/S] [/SIZE][/I][SIZE=4]Not true anymore, check the NBTInjector examples above.*[/SIZE]
*This only is required for <= 1.13, 1.14+ can just use the getPersistentDataContainer method on NBTEntity/NBTTileEntity to store custom data.
[/LIST]
Git: [URL='https://github.com/tr7zw/Item-NBT-API']Github[/URL]
Maven Repo: [URL='https://repo.codemc.org/#browse/browse:maven-public:de%2Ftr7zw%2Fitem-nbt-api-plugin']CodeMC[/URL]
Jenkins: [URL='https://ci.codemc.org/job/Tr7zw/job/Item-NBT-API/']Jenkins[/URL]
Discord server for help: [URL='https://discord.gg/yk4caxM']Discord[/URL]
Feel free to create forks for your need! (Send me a message on [S]Spigot[/S] the Discord server if you want stuff to be added/need help)
Yes you are allowed to integrate the API into your plugin(remember to shade)! (Send me a link to your project so I can check it out ;))

[B][SIZE=5]Projects using the API(That I know of)[/SIZE]
[U][SIZE=4]Plugins[/SIZE][/U][/B]
[URL='https://www.spigotmc.org/resources/crazy-enchantments.16470/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=16470&[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/levelledmobs-for-1-16-x-1-17-x.74304/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=74304&[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/backpacks.45622/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=45622&[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/gravy.45288/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=45288&[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/interactivebooks.45604/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=45604&[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/vouchers.13654/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=13654&[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/itemcommands.49543/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=49543&[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/comparable-hoppers-2.45058/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=45058&[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/nottooexpensive.62680/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=62680&[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/papermoney.70361/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=70361&[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/blockstackerx-recoded.73749/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=73749[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/63020/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=63020&reload[/IMG][/URL]
[URL='https://www.spigotmc.org/resources/72810/'][IMG]https://tr7zw.dev/nbtapi/linkedProjects.php?id=72810&reload[/IMG][/URL]
[U][B]Server/Networks[/B][/U]
[URL='http://Timolia.de'][IMG]http://status.mclive.eu/Timolia.de/timolia.de/25565/banner.png[/IMG][/URL]
[U][B]Other[/B][/U]
- [URL='https://github.com/LogisticsCraft/Logistics-API']Logistics-API[/URL]

[SIZE=4][SIZE=4][U][B]Donators[/B][/U]
[U][B][URL='https://www.spigotmc.org/members/mrdienns.35704/'][IMG]https://tr7zw.dev/nbtapi/spigotPeople.php?id=35704[/IMG][/URL] [/B][/U]
[U][B][URL='https://www.spigotmc.org/members/mraxetv.27720/'][IMG]https://tr7zw.dev/nbtapi/spigotPeople.php?id=27720[/IMG][/URL] [/B][/U]
[URL='https://www.spigotmc.org/members/lonedev.88296/'][IMG]https://tr7zw.dev/nbtapi/spigotPeople.php?id=88296[/IMG][/URL] 
[URL='https://www.spigotmc.org/members/gc.25090/'][IMG]https://tr7zw.dev/nbtapi/spigotPeople.php?id=25090[/IMG][/URL]
[URL='https://www.spigotmc.org/members/peaches_mlg100.194338/'][IMG]https://tr7zw.dev/nbtapi/spigotPeople.php?id=194338[/IMG][/URL][/SIZE][/SIZE]
[IMG]https://bstats.org/signatures/bukkit/ItemNBTAPI.svg[/IMG]