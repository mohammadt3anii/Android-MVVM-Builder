package com.yt98.manager.android_builder.ui.activity;

import android.os.Parcelable;

import com.yt98.manager.android_builder.base.BaseActivity;
import com.yt98.manager.android_builder.base.BaseView;
import com.yt98.manager.android_builder.base.BaseViewModel;
import com.yt98.manager.android_builder.utils.ClassInfo;

import androidx.lifecycle.LifecycleOwner;

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public abstract class BaseNetworkActivity<View extends BaseView , Model extends Parcelable, VM extends BaseViewModel<View, Model>>
        extends BaseActivity implements LifecycleOwner {

    /**
     * Generated Code from {@BaseActivity}
     */

    protected abstract VM getViewModel();

}
