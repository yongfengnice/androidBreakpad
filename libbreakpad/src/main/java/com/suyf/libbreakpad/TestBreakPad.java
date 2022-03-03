package com.suyf.libbreakpad;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * @author Created by suyongfeng on 2022/1/10
 */
public class TestBreakPad {

    static {
        System.loadLibrary("breakpad-client");
    }

    public static void init(Context context) {
        String breakpad_dump = context.getExternalCacheDir().getAbsolutePath() + "breakpad_dump";
        File file = new File(breakpad_dump);
        if (!file.exists()) {
            file.mkdirs();
        }
        Log.d("Suyf", "init--breakpad_dump:" + file.getAbsolutePath());
        nativeInit(file.getAbsolutePath());
    }

    private native static void nativeInit(String absolutePath);
}
