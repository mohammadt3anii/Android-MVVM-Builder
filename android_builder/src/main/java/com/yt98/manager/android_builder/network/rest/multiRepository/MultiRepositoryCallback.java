package com.yt98.manager.android_builder.network.rest.multiRepository;

public interface MultiRepositoryCallback<Body> {

    void onSuccess(Body content, Integer code);

    void onError(Throwable throwable);

    void onComplete();

}
