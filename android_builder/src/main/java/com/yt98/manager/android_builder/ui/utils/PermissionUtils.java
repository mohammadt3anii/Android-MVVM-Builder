package com.yt98.manager.android_builder.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static int requestCodePermission;

    public interface PermissionCallback {
        void onSuccessPermission();

        void onErrorPermission();
    }

    private PermissionUtils() {
        // Utility Class
    }

    public static void checkPermission(Context context, int requestCode, String permission, PermissionCallback callback) {
        requestCodePermission = requestCode;
        if (ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (callback != null) {
                callback.onErrorPermission();
            }
        } else {
            if (callback != null) {
                callback.onSuccessPermission();
            }
        }
    }

    public static void requestPermission(Context context, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
    }

    public static void onRequestPermissionsResult(int requestCode, int[] grantResults, PermissionCallback callback) {
        if (requestCode == requestCodePermission) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (callback != null) {
                    callback.onSuccessPermission();
                }
            } else {
                if (callback != null) {
                    callback.onErrorPermission();
                }
            }
        }
    }

}
