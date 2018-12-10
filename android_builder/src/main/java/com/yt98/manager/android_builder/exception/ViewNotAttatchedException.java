package com.yt98.manager.android_builder.exception;

public class ViewNotAttatchedException extends Exception {

    private String mesage;

    public ViewNotAttatchedException(String mesage) {
        super(mesage);
        this.mesage = mesage;
    }

    public String getMesage() {
        return mesage;
    }
}
