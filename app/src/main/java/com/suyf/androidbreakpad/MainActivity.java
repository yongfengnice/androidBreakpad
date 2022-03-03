package com.suyf.androidbreakpad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.suyf.androidbreakpad.databinding.ActivityMainBinding;
import com.suyf.libbreakpad.TestBreakPad;
import com.suyf.libbreakpad.TestJavaCrash;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'androidbreakpad' library on application startup.
    static {
        System.loadLibrary("androidbreakpad");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TestJavaCrash.init();
        TestBreakPad.init(getApplicationContext());
    }

    public void doThreadCrash(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                binding.btn.setText(0);
            }
        }).start();
    }

    public void doCrash(View view) {
        int aa = 10 / 0;
    }

    public void doNativeCrash(View view) {
        nativeCrash();
    }

    public void doThreadNativeCrash(View view) {
        threadNativeCrash();
    }

    private native void nativeCrash();

    private native void threadNativeCrash();
}