package com.yt98.manager.android_builder.network.rest;

import android.os.Parcelable;

public abstract class BaseSingleRepository<Model extends Parcelable , Api> extends BaseRepository<Model , Api> {

    public BaseSingleRepository(String baseUrl) {
        super(baseUrl);
    }

}
