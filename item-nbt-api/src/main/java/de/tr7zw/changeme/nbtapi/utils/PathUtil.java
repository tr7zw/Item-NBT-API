package de.tr7zw.changeme.nbtapi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PathUtil {

    private static final Pattern pattern = Pattern.compile("[^\\\\](\\.)");
    private static final Pattern indexPattern = Pattern.compile(".*\\[(-?[0-9]+)\\]");

    public static List<PathSegment> splitPath(String path) {
        List<PathSegment> list = new ArrayList<>();
        Matcher matcher = pattern.matcher(path);
        int startIndex = 0;
        while (matcher.find(startIndex)) {
            list.add(new PathSegment(path.substring(startIndex, matcher.end() - 1).replace("\\.", ".")));
            startIndex = matcher.end();
        }
        list.add(new PathSegment(path.substring(startIndex).replace("\\.", ".")));
        return list;
    }

    public static class PathSegment {

        private final String path;
        private final Integer index;

        public PathSegment(String path, int index) {
            this.path = path;
            this.index = index;
        }

        public PathSegment(String path) {
            Matcher matcher = indexPattern.matcher(path);
            if (matcher.find()) {
                this.path = path.substring(0, path.indexOf("["));
                this.index = Integer.parseInt(matcher.group(1));
            } else {
                this.path = path;
                this.index = null;
            }
        }

        public String getPath() {
            return path;
        }

        public int getIndex() {
            return index;
        }

        public boolean hasIndex() {
            return index != null;
        }

        @Override
        public String toString() {
            return "PathSegment [path=" + path + ", index=" + index + "]";
        }

    }

}
