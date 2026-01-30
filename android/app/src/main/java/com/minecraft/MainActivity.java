package com.minecraft;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

    static {
        System.loadLibrary("minecraft");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        FrameLayout layout = new FrameLayout(this);
        setContentView(layout);
        
        startGame();
    }

    private native void startGame();
}
