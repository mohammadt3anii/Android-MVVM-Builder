package com.yt98.manager.android_builder.ui.activity;

import android.os.Parcelable;

import com.yt98.manager.android_builder.base.BaseActivity;
import com.yt98.manager.android_builder.base.BaseView;
import com.yt98.manager.android_builder.base.BaseViewModel;

import androidx.lifecycle.LifecycleOwner;

public abstract class BaseNetworkActivity<View extends BaseView , Model extends Parcelable, VM extends BaseViewModel<View, Model>>
        extends BaseActivity implements LifecycleOwner {



    protected abstract VM getViewModel();

}
