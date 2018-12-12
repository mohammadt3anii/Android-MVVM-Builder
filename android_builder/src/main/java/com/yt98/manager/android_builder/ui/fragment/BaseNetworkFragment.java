package com.yt98.manager.android_builder.ui.fragment;

import android.os.Parcelable;

import com.yt98.manager.android_builder.base.BaseFragment;
import com.yt98.manager.android_builder.base.BaseView;
import com.yt98.manager.android_builder.base.BaseViewModel;
import com.yt98.manager.android_builder.base.ClassInfo;

import androidx.lifecycle.LifecycleOwner;

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public abstract class BaseNetworkFragment<View extends BaseView, Model extends Parcelable, VM extends BaseViewModel<View, Model>>
        extends BaseFragment implements LifecycleOwner {


    /**
     * Generated Code from {@BaseFragment}
     */


    protected abstract VM getViewModel();

}
