package com.customExceptionClasses;

public class ExceptionUtil {

    public static void throwClientNotFoundException(String message) throws ClientNotFoundException {
        throw new ClientNotFoundException(message);
    }

}
