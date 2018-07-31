package com.customExceptionClasses;

public class RepuestoNotFoundException extends CustomException {
    public RepuestoNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
