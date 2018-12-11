package com.yt98.manager.android_builder.exception;

public class NetworkException extends Exception {

    private String message;

    public NetworkException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
