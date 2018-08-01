package com.utils;

public class Validator {
    private final static String BLANK_SPACE_REGEX = "\\s*";

    public static String isValidValue(String value) throws IllegalArgumentException {
        if (value == null || value.matches(BLANK_SPACE_REGEX)) {
            throw new IllegalArgumentException("The value cannot be null or blank spaces");
        } else {
            return value;
        }
    }

    public static boolean isValidOption(int option, int max) {
        return (option > 0 && option <= max) || option == -1;
    }

    public static boolean isValidDNI(int dni) {
        return dni > 0 && String.valueOf(dni).length() == 8;
    }

}