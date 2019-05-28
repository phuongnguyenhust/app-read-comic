package com.example.androidcomicreader;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // no need setcontentview because we use Theme

        new Handler().postDelayed(new Runnable(){
            public void run(){
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();
            }
        }, 3000);

    }
}
