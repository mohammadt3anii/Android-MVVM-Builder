package com.yt98.manager.android_builder.base;

import com.yt98.manager.android_builder.utils.ClassInfo;

import androidx.fragment.app.Fragment;

/**
 * This Class is the Root For the Fragments that support MVVM Library
 * <p>
 * just Support AndroidX
 */

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public abstract class BaseFragment extends Fragment {


    protected abstract int getLayoutRes();
}
