package com.yt98.manager.android_builder.network.callback;

import com.yt98.manager.android_builder.utils.ResponseStatus;

public interface MultiRepositoryCallback<Body> {

    void onSuccess(Body content, @ResponseStatus Integer status);

    void onError(Throwable throwable);

    void onComplete();

}
