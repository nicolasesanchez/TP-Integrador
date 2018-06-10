package com.customExceptionClasses;

public class OrdenTrabajoNotFoundException extends Exception {
    public OrdenTrabajoNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
