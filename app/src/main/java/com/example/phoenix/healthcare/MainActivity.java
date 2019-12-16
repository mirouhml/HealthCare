package com.example.phoenix.healthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the SlidePane-Activity. */
                Intent mainIntent = new Intent(MainActivity.this,Login.class);
                startActivity(mainIntent);
                finish();
            }
        }, 1800);
    }

}
