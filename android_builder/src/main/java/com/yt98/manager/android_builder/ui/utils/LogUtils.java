package com.yt98.manager.android_builder.ui.utils;

import android.util.Log;

import com.yt98.manager.android_builder.utils.LogLevel;

public class LogUtils {

    public static void log(LogLevel logLevel, String message) {
        if (logLevel == LogLevel.ERROR) {
            Log.e("Android MVVM TAG", message);
        } else if (logLevel == LogLevel.DEBUG) {
            Log.d("Android MVVM TAG", message);
        }
    }
}
