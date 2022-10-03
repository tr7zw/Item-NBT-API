# Option 1) NBT-API as a dependency
Add the following Entries to your Gradle build at the correct location:
```
compile group: 'de.tr7zw', name: 'item-nbt-api-plugin', version: 'VERSION'
```
(Get the current Version from [here](https://www.spigotmc.org/resources/nbt-api.7939/))
```
	repositories {
        ...
        maven {
            name = 'CodeMC'
            url = 'https://repo.codemc.org/repository/maven-public/'
        }
        ...
    }
```
Add the API as dependency to your plugin.yml
```
depend: [NBTAPI]
```

# Option 2) Shading the NBT-API into your plugin

// TODO, the gradle shadow plugin is used for this