package com.yt98.manager.android_builder.base.impl;

public interface BaseViewModelImpl {

    void onDestroy();

    void onResume();

    void onStop();

    void changeViewStatus(boolean status);

}
