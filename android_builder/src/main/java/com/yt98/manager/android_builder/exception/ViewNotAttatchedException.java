package com.yt98.manager.android_builder.exception;


import com.yt98.manager.android_builder.utils.ClassInfo;

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
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
