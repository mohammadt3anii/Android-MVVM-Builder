package com.yt98.manager.android_builder.utils;

import androidx.annotation.IntDef;

@IntDef({
        ResponseStatus.SUCCESS,
        ResponseStatus.BAD_REQUEST,
        ResponseStatus.FORBIDDEN,
        ResponseStatus.NOT_FOUND,
        ResponseStatus.SERVER_ERROR,
        ResponseStatus.CLIENT_ERROR,
        ResponseStatus.JSON_ERROR,
        ResponseStatus.NETWORK_ERROR,
        ResponseStatus.UNAUTHENTICATED_ERROR,
        ResponseStatus.UNEXPECTED_ERROR
})
public @interface ResponseStatus {
    int SUCCESS = 200;
    int BAD_REQUEST = 400;
    int FORBIDDEN = 403;
    int NOT_FOUND = 404;
    int SERVER_ERROR = 500;
    int CLIENT_ERROR = 5;
    int JSON_ERROR = 6;
    int NETWORK_ERROR = 7;
    int UNAUTHENTICATED_ERROR = 8;
    int UNEXPECTED_ERROR = 9;
}
