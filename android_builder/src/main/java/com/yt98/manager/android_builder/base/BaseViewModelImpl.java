package com.yt98.manager.android_builder.base;



@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public interface BaseViewModelImpl<View extends BaseView> {

    void onDestroy();

    void onResume(View view);

    void onStop();

    void changeViewStatus(boolean status);

}
