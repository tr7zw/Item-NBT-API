package dev.tr7zw.mappingsparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ClassWrapper;
import de.tr7zw.changeme.nbtapi.utils.nmsmappings.ReflectionMethod;

/**
 * Internal class to be used to port SpecialSource mapping txt files to nbt-api
 * internal mappings
 * 
 * @author tr7zw
 *
 */
public class MappingsParser {

    public static StringBuilder builder = new StringBuilder();

    public static void main(String[] args) throws IOException {
        File input = new File("minecraft_server.1.18.2.txt");
        List<String> lines = Files.readAllLines(input.toPath());

        Map<String, ClassWrapper> classes = new HashMap<>();
        for (ClassWrapper wrapper : ClassWrapper.values()) {
            if (wrapper.getMojangName() != null) {
                classes.put(wrapper.getMojangName(), wrapper);
            }
        }

        String currentClass = null;
        String mojangName = null;
        String unmappedName = null;
        ClassWrapper nmsWrapper = null;
        Map<ReflectionMethod, String> mappedNames = null;
        for (String line : lines) {
            if (!line.startsWith(" ")) {
                if (nmsWrapper != null && mappedNames != null) {
                    proccessMapping(nmsWrapper, mappedNames);
                }
                currentClass = line.split(" ")[0];
                nmsWrapper = classes.get(currentClass);
                mappedNames = new HashMap<>();
                for (ReflectionMethod method : ReflectionMethod.values()) {
                    if (method.getParentClassWrapper() == nmsWrapper && method.isCompatible()) {
                        mappedNames.put(method, null);
                    }
                }
                continue;
            }
            if (nmsWrapper == null)
                continue;
            mojangName = line.trim().split(" ")[1];
            unmappedName = line.trim().split(" ")[3];
            classes.remove(currentClass);
            for (ReflectionMethod method : mappedNames.keySet()) {
                if (method.getSelectedVersionInfo() == null)
                    continue;
                if (!mojangName.contains("("))
                    continue;
                if (mojangName.equals(method.getSelectedVersionInfo().name)) {
                    mappedNames.put(method, unmappedName);
                }
            }
            // System.out.println(currentClass + ": " + mojangName + " -> " + unmappedName);
        }
        System.out.println(builder);
    }

    public static void proccessMapping(ClassWrapper nmsWrapper, Map<ReflectionMethod, String> mappedNames) {
        for (Entry<ReflectionMethod, String> entry : mappedNames.entrySet()) {
            if (entry.getValue() == null) {
                System.out.println(
                        "Missing mapping in class " + nmsWrapper.getMojangName() + " method " + entry.getKey().name());
            } else {
                builder.append("put(\"" + nmsWrapper.getMojangName() + "#"
                        + entry.getKey().getSelectedVersionInfo().name + "\", \"" + entry.getValue() + "\");\n");
            }
        }
    }

}
