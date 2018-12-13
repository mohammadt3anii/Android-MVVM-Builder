package com.yt98.manager.android_builder.base;


import com.yt98.manager.android_builder.utils.ClassInfo;

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public interface BaseViewModelImpl {

    void onDestroy();

    void onResume(BaseView view);

    void onStop();

    void changeViewStatus(boolean status);

}
