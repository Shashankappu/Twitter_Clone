package com.example.twittercc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(PreferenceManager.getDefaultSharedPreferences(Splash.this).getBoolean("is_user_logged",true)){
                    Intent intent = new Intent(Splash.this,Home.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(Splash.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },1000);

    }
}