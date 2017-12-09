package com.holomedia.holomedia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class activity_launch extends AppCompatActivity {
    private static final String TAG = "TEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(activity_launch.this,MainActivity.class);
                startActivity(i);
                finish();
                Log.i(TAG, "run: ");
            }
        },3000); }



    }

