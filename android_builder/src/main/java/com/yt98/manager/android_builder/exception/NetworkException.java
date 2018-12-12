package com.yt98.manager.android_builder.exception;


import com.yt98.manager.android_builder.base.ClassInfo;

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
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
