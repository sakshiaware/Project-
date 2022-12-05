package com.smc.smcsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import android.os.Bundle;
import android.os.Bundle;

public class AdminLogin extends AppCompatActivity {
    public Button button;
    EditText username,pass;
    TextView forgotpass;
    DatabaseReference reference;
    boolean isAllFieldsChecked = false;
    String uname,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
     //   forgotpass= (TextView) findViewById(R.id.adminforgotpass);
        button= (Button) findViewById(R.id.Alogin_button);
        username=(EditText)findViewById(R.id.adminusername);
        pass=(EditText)findViewById(R.id.adminpassword);
        reference = FirebaseDatabase.getInstance().getReference("Admin");
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    uname = username.getText().toString();
                    password = pass.getText().toString();
                    reference.child(uname).addListenerForSingleValueEvent(listener);

                }
            }
        });

        /*forgotpass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLogin.this, Adminsendotp.class);
                startActivity(intent);

            }
        });*/
    }
    private boolean CheckAllFields() {
        if (username.length() == 0) {
            username.setError("Username is required");
            return false;
        }

        if (pass.length() == 0) {
            pass.setError("Password is required");
            return false;
        } else if (pass.length() < 8) {
            pass.setError("Password must be minimum 8 characters");
            return false;
        }

        // after all validation return true.
        return true;
    }
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                String password1 = snapshot.child("pass").getValue(String.class);
                if (password1.equals(password)) {
                    Intent intent = new Intent(AdminLogin.this, AdminDashboard.class);
                    intent.putExtra("Adminusername",username.getText().toString());
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(AdminLogin.this,error.toString(),Toast.LENGTH_SHORT).show();
        }

    };
}
