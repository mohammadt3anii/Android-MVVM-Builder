package com.yt98.manager.android_builder.network;

import com.yt98.manager.android_builder.utils.ResponseStatus;


public abstract class HttpHandleResponseCode {

    private static Integer responseStatus;

    public static Integer handleResultStatus(@ResponseStatus Integer status) {
        if (status >= CommonHttpCodes.FIRST_SUCCESS_CODE && status <= CommonHttpCodes.LAST_SUCCESS_CODE) {
            responseStatus = ResponseStatus.SUCCESS;
        } else if (status == CommonHttpCodes.BAD_REQUEST_CODE) {
            responseStatus = ResponseStatus.BAD_REQUEST;
        } else if (status == CommonHttpCodes.UNAUTHENTICATED_CODE) {
            responseStatus = ResponseStatus.UNAUTHENTICATED_ERROR;
        } else if (status == CommonHttpCodes.FORBIDDEN_CODE) {
            responseStatus = ResponseStatus.FORBIDDEN;
        } else if (status == CommonHttpCodes.NOT_FOUND_CODE) {
            responseStatus = ResponseStatus.NOT_FOUND;
        } else if (status >= CommonHttpCodes.FIRST_INTERNAL_SERVER_ERROR_CODE &&
                status <= CommonHttpCodes.LAST_INTERNAL_SERVER_ERROR_CODE) {
            responseStatus = ResponseStatus.SERVER_ERROR;
        } else {
            responseStatus = CommonHttpCodes.UNKNOWN_ERROR;
        }

        return responseStatus;
    }

}
