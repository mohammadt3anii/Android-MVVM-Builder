package com.yt98.manager.android_builder.network;

public interface CommonHttpCodes {
    int FIRST_SUCCESS_CODE = 200;
    int LAST_SUCCESS_CODE = 299;

    int BAD_REQUEST_CODE = 400;
    int UNAUTHENTICATED_CODE = 401;
    int FORBIDDEN_CODE = 403;
    int NOT_FOUND_CODE = 404;
    int UNKNOWN_ERROR = 700;

    int FIRST_INTERNAL_SERVER_ERROR_CODE = 500;
    int LAST_INTERNAL_SERVER_ERROR_CODE = 600;
}
