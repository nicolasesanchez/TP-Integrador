package com.customExceptionClasses;

public class ClientNotFoundException extends CustomException {
    public ClientNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
