package com.yt98.manager.android_builder.utils;

public @interface ClassInfo {

    int version();

    String created();

    String createdBy();

    boolean modified() default false;

    String modifiedBy() default "";

}
