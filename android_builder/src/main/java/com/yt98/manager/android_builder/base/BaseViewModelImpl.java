package com.yt98.manager.android_builder.base;

import com.yt98.manager.android_builder.base.BaseView;

public interface BaseViewModelImpl<View extends BaseView> {

    void onDestroy();

    void onResume(View view);

    void onStop();

    void changeViewStatus(boolean status);

}
