To start using NB-API, you either need to depend on its plugin version, or shade (include) it inside your plugin.

> [!IMPORTANT]
> Plugin and shaded versions have different ``artifactId``. Make sure to correctly choose the one you need!

# Option 1) NBT-API as a dependency

Add the following entries to your pom at the correct locations:

```xml
<dependency>
  <groupId>de.tr7zw</groupId>
  <artifactId>item-nbt-api-plugin</artifactId>
  <version>VERSION</version>
  <scope>provided</scope>
</dependency>
```

(Get the current ``VERSION`` from [here](https://modrinth.com/plugin/nbtapi/versions))

```xml
<repositories>
...
<!-- CodeMC -->
<repository>
<id>codemc-repo</id>
<url>https://repo.codemc.io/repository/maven-public/</url>
<layout>default</layout>
</repository>
...
</repositories>
```

Add the API as dependency to your ``plugin.yml``:

```yaml
depend: [NBTAPI]
```

# Option 2) Shading the NBT-API into your plugin

To include NBT-API directly in your plugin, you can use the maven shade plugin.

Add the plugin to the build configuration, as shown here:

```xml
 <plugins>
   <plugin>
     <groupId>org.apache.maven.plugins</groupId>
     <artifactId>maven-shade-plugin</artifactId>
     <version>3.6.0</version>
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
           <shadedPattern>YOUR PACKAGE WHERE THE API SHOULD END UP</shadedPattern>
         </relocation>
       </relocations>
     </configuration>
   </plugin>
</plugins>
```

The latest version of the shade plugin can be found [here](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-shade-plugin).

Replace ``YOUR PACKAGE WHERE THE API SHOULD END UP`` with your own unique package. For example:

```xml
<relocation>
  <pattern>de.tr7zw.changeme.nbtapi</pattern>
  <shadedPattern>com.yourname.pluginname.nbtapi</shadedPattern>
</relocation>
```

Then, add NBT-API to your dependencies by including the following entries to your pom at the correct locations:

```xml
<dependency>
  <groupId>de.tr7zw</groupId>
  <artifactId>item-nbt-api</artifactId>
  <version>VERSION</version>
</dependency>
```

(Get the current ``VERSION`` from [here](https://modrinth.com/plugin/nbtapi/versions))

> [!WARNING]
> Make sure you're using ``item-nbt-api`` as ``artifactId``, never shade the ``-plugin`` artifact!

```xml
<repositories>
...
<!-- CodeMC -->
<repository>
<id>codemc-repo</id>
<url>https://repo.codemc.io/repository/maven-public/</url>
<layout>default</layout>
</repository>
...
</repositories>
```

