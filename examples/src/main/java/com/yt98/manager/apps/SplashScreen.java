package com.yt98.manager.apps;

import android.os.Bundle;
import android.view.View;

import com.yt98.manager.android_builder.ui.activity.BaseLocalActivity;
import com.yt98.manager.apps.networkActivity.UserActivity;
import com.yt98.manager.apps.permissionActivity.PermissionActivity;

import androidx.annotation.Nullable;

public class SplashScreen extends BaseLocalActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    public void permissionScreen(View view) {
        startScreen(this, PermissionActivity.class, true);
    }

    public void mvvmScreen(View view) {
        startScreen(this, UserActivity.class, true);
    }
}
