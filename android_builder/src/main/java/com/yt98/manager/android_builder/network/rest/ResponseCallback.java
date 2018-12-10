package com.yt98.manager.android_builder.network.rest;

import android.os.Parcelable;

/**
 * BaseCallback for Any Request
 * @param <Model>
 */
public interface ResponseCallback<Model extends Parcelable> {

    void onSuccess(Model content , Integer code);

    void onError(Throwable throwable);

}
