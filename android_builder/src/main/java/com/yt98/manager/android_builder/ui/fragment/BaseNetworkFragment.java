package com.yt98.manager.android_builder.ui.fragment;

import android.os.Parcelable;

import com.yt98.manager.android_builder.base.BaseFragment;
import com.yt98.manager.android_builder.base.BaseView;
import com.yt98.manager.android_builder.base.BaseViewModel;

import androidx.lifecycle.LifecycleOwner;

public abstract class BaseNetworkFragment<View extends BaseView, Model extends Parcelable, VM extends BaseViewModel<View, Model>>
        extends BaseFragment implements LifecycleOwner {



    protected abstract VM getViewModel();

}
