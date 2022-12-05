package com.smc.smcsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AdminDashboard extends AppCompatActivity
{
    RecyclerView recview;
    AdminAdapterPost adapter;
    TextView report;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        recview=(RecyclerView)findViewById(R.id.adminrecycleview);
        report=findViewById(R.id.reportbtn);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, AdminReport.class));
            }
        });
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ModelPost> options =
                new FirebaseRecyclerOptions.Builder<ModelPost>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts"), ModelPost.class)
                        .build();

        adapter=new AdminAdapterPost(options);
        recview.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}