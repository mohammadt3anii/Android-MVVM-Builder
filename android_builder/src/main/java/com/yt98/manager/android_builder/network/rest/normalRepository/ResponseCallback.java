package com.yt98.manager.android_builder.network.rest.normalRepository;

import android.os.Parcelable;

import com.yt98.manager.android_builder.base.ClassInfo;

/**
 * BaseCallback for Any Request
 * @param <Model>
 */

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public interface ResponseCallback<Model extends Parcelable> {

    void onSuccess(Model content , Integer code);

    void onError(Throwable throwable);

}
