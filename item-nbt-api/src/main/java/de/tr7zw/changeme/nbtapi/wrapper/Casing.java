package de.tr7zw.changeme.nbtapi.wrapper;

import java.util.function.UnaryOperator;

public enum Casing {
    camelCase(s -> {
        if (s.length() < 2) {
            return s.toLowerCase();
        }
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }), snake_case(s -> {
        StringBuilder result = new StringBuilder();
        // Convert the first letter to lowercase
        result.append(Character.toLowerCase(s.charAt(0)));
        // Iterate through the rest of the string
        for (int i = 1; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            // Convert uppercase letters to lowercase and add underscore
            if (Character.isUpperCase(currentChar)) {
                result.append('_').append(Character.toLowerCase(currentChar));
            } else {
                result.append(currentChar);
            }
        }
        return result.toString();
    }), PascalCase(s -> {
        if (s.length() < 2) {
            return s.toUpperCase();
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }), lowercase(String::toLowerCase), UPPERCASE(String::toUpperCase);

    private UnaryOperator<String> convert;

    Casing(UnaryOperator<String> function) {
        this.convert = function;
    }

    public String convertString(String str) {
        return convert.apply(str);
    }
}