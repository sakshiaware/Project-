package com.smc.smcsystem;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnuser,btnadmin;
    //SharedPreferences sharedPreferences;
    // private static final String Shared_pref_name="mypref";
    // private static final String Key_name="name";
    // private static final String Key_email="email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnuser = findViewById(R.id.front_btn1);
        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserLogin.class));
            }
        });
        btnadmin = findViewById(R.id.front_btn2);
        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLogin.class));
            }
        });
    }
}