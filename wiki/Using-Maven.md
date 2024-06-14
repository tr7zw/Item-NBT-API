# Option 1) NBT-API as a dependency

Add the following Entries to your pom at the correct location:

```xml
<dependency>
  <groupId>de.tr7zw</groupId>
  <artifactId>item-nbt-api-plugin</artifactId>
  <version>VERSION</version>
  <scope>provided</scope>
</dependency>
```

(Get the current Version from [here](https://modrinth.com/plugin/nbtapi/versions))

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

Add the following Entries to your pom at the correct location:

```xml
<dependency>
  <groupId>de.tr7zw</groupId>
  <artifactId>item-nbt-api</artifactId>
  <version>VERSION</version>
</dependency>
```

(Get the current Version from [here](https://modrinth.com/plugin/nbtapi/versions))

> [!IMPORTANT]
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

```xml
 <plugins>
   <plugin>
     <groupId>org.apache.maven.plugins</groupId>
     <artifactId>maven-shade-plugin</artifactId>
     <version>3.4.1</version>
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

Example:

```xml
<relocation>
  <pattern>de.tr7zw.changeme.nbtapi</pattern>
  <shadedPattern>com.yourname.pluginname.nbtapi</shadedPattern>
</relocation>
```

