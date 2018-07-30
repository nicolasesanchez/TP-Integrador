package com.utils;

public class Validator {
    private final static String BLANK_SPACE_REGEX = "\\s*";
    private final static String PATENTE_REGEX = "[A-Z]{3}-[0-9]{3}";

    public static String isValidValue(String value) throws IllegalArgumentException {
        if (value == null || value.matches(BLANK_SPACE_REGEX)) {
            throw new IllegalArgumentException("The value cannot be null or blank spaces");
        } else {
            return value;
        }
    }

    public static String validatePatente(String value) {
        value = value.toUpperCase();
        if (isValidValue(value) != null && value.matches(PATENTE_REGEX)) {
            return value;
        } else {
            throw new IllegalArgumentException("The value contains invalid characters");
        }
    }

    public static boolean isValidOption(int option, int max) {
        return (option > 0 && option <= max) || option == -1;
    }

    public static boolean isValidDNI(int dni) {
        return dni > 0 && String.valueOf(dni).length() == 8;
    }

}