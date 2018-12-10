package com.yt98.manager.apps;

import com.yt98.manager.android_builder.base.BaseView;
import com.yt98.manager.apps.data.UserModel;

public interface UserView extends BaseView {

    void showLoading();

    void hideLoading();

    void onSuccess(UserModel userModel);

    void onError(String message);

}
