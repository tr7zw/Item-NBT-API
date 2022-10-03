# Welcome to the NBT API wiki!

NBT API has been tested on Spigot
1.7.10-1.16+

The NBTInjector will not work with: 1.12.0, 1.12.1, 1.13.0 (Please just update to the final release of your chosen version)
On startup you will get a notification when there's a version problem!

#### 1.7.x Notes:
* Use a 1.7.x version with NMS R4 (1.7.10)
* For Gson support in 1.7.x add [this](https://www.spigotmc.org/resources/gson-for-1-8-3-or-older.30852/ "Gson for 1.8.3 or older") to your server.
* NBTLists may not work
* NBTTypes don't work as 1.7.x is missing this feature.
* TLDR: 1.7.10 is a bit broken and not everything will work!

## What do I have to do as a server owner?
Just download the jar and drop it in the plugins folder. Done.
Note that outdated plugins may ask for "ItemNBTAPI". In this case, download version 1.8.3 from the versions tab. The outdated "ItemNBTAPI" and "NBTAPI" can be used at the same time.

### Don't reload the NBT-Injector
Reloading in general is a horrible thing and it will break the NBTInjector in horrible ways! When updating plugins/changing configs always restart the server normally!

## How can I use the API as a developer?
See [Using the NBT API](https://github.com/tr7zw/Item-NBT-API/wiki/Using-the-NBT-API "Using the NBT API")