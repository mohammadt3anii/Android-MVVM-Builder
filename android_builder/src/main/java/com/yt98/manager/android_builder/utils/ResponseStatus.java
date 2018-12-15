package com.yt98.manager.android_builder.utils;

import androidx.annotation.IntDef;

@IntDef({
        ResponseStatus.SUCCESS,
        ResponseStatus.BAD_REQUEST,
        ResponseStatus.FORBIDDEN,
        ResponseStatus.NOT_FOUND,
        ResponseStatus.SERVER_ERROR,
        ResponseStatus.UNEXPECTED_ERROR
})
public @interface ResponseStatus {
    int SUCCESS = 200;
    int BAD_REQUEST = 400;
    int FORBIDDEN = 403;
    int NOT_FOUND = 404;
    int SERVER_ERROR = 500;
    int UNEXPECTED_ERROR = 9;
}
