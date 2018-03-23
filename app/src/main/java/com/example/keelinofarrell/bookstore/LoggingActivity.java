package com.example.keelinofarrell.bookstore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by keelin.ofarrell on 23/03/2018.
 */

public abstract class LoggingActivity extends AppCompatActivity {

    protected final String TAG = "ACTIVITY_" + getClass().getSimpleName();

    private String createLogMessage(String methodName, String suffix) {
        return String.format("%s() [%d] %s", methodName, hashCode(), suffix);
    }

    private String createLogMessage(String methodName) {
        return createLogMessage(methodName, "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, createLogMessage("onCreate", String.format(": savedInstanceState is%s null",
                savedInstanceState != null ? " not" : "")));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, createLogMessage("onStart"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, createLogMessage("onResume"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, createLogMessage("onRestart"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG, createLogMessage("onSaveInstanceState"));
    }

    @Override
    protected void onPause() {
        Log.v(TAG, createLogMessage("onPause"));
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.v(TAG, createLogMessage("onStop"));
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, createLogMessage("onDestroy"));
        super.onDestroy();
    }

}
