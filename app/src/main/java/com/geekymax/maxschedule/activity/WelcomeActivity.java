package com.geekymax.maxschedule.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.geekymax.maxschedule.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putBoolean("used",true);
        editor.apply();
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        if(preferences.getBoolean("login",false)){
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        }else {
            editor.remove("userId");
            editor.apply();
            startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
        }
    }
}
