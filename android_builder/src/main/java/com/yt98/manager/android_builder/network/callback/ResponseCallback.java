package com.yt98.manager.android_builder.network.callback;

import android.os.Parcelable;

import com.yt98.manager.android_builder.utils.ClassInfo;
import com.yt98.manager.android_builder.utils.ResponseStatus;

/**
 * BaseCallback for Any Request
 *
 * @param <Model>
 */

@ClassInfo(
        version = 1,
        created = "11/11/2018",
        createdBy = "Yazan98"
)
public interface ResponseCallback<Model> {

    void onSuccess(Model content, @ResponseStatus Integer status);

    void onError(Throwable throwable);

}
