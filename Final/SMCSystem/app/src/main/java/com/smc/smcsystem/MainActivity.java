package com.smc.smcsystem;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView btnuser,btnadmin;
    SharedPreferences sharedPreferences;
    private static final String Shared_pref_name="mypref";
    // private static final String Key_name="name";
     private static final String Key_email="email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences(Shared_pref_name,MODE_PRIVATE);
        String em=sharedPreferences.getString(Key_email,null);
        if(em!=null)
        {
            Intent intent=new Intent(MainActivity.this,DashboardActivity.class);
            startActivity(intent);
        }
        btnuser = findViewById(R.id.fronttxt1);
        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserLogin.class));
            }
        });
        btnadmin = findViewById(R.id.fronttxt2);
        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLogin.class));
            }
        });
    }
}