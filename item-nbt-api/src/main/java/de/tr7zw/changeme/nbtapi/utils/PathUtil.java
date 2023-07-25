package de.tr7zw.changeme.nbtapi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PathUtil {

    private static final Pattern pattern = Pattern.compile("[^\\\\](\\.)");

    public static List<String> splitPath(String path) {
        List<String> list = new ArrayList<>();
        Matcher matcher = pattern.matcher(path);
        int startIndex = 0;
        while (matcher.find(startIndex)) {
            list.add(path.substring(startIndex, matcher.end() - 1).replace("\\.", "."));
            startIndex = matcher.end();
        }
        list.add(path.substring(startIndex).replace("\\.", "."));
        return list;
    }

}
