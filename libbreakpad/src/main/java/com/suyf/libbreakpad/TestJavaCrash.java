package com.suyf.libbreakpad;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * @author Created by suyongfeng on 2022/3/3
 */
public class TestJavaCrash {

    public static void init() {

        Thread.UncaughtExceptionHandler exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                Log.e("Suyf", "uncaughtException: " + Log.getStackTraceString(e));
                if (exceptionHandler != null) {
                    exceptionHandler.uncaughtException(t, e);
                }
            }
        });
    }
}
