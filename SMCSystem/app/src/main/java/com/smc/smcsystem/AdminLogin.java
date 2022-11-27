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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import android.os.Bundle;
import android.os.Bundle;

public class AdminLogin extends AppCompatActivity {
    private EditText email, password, name;
    private Button mlogin;
    private TextView newdnewaccount, reocverpass;
    FirebaseUser currentUser;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //shared variable
        //  sharedPreferences=getSharedPreferences(Shared_pref_name,MODE_PRIVATE);
        // initialising the layout items
        //  String semail=sharedPreferences.getString(Key_email,null);
       /* if(semail!=null)
        {
            Intent intent=new Intent(MainActivity.this,DashboardActivity.class);
            startActivity(intent);
        }*/
        email = findViewById(R.id.Alogin_email);
        password = findViewById(R.id.Alogin_password);
       // newdnewaccount = findViewById(R.id.Aneeds_new_account);
        reocverpass = findViewById(R.id.Aforgetp);
        mAuth = FirebaseAuth.getInstance();
        mlogin = findViewById(R.id.Alogin_button);
        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        // checking if user is null or not
        if (mAuth != null) {
            currentUser = mAuth.getCurrentUser();
        }

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaill = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                // if format of email doesn't matches return null
                if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()) {
                    email.setError("Invalid Email");
                    email.setFocusable(true);

                } else {
                    loginUser(emaill, pass);
                }
            }
        });

        // If new account then move to Registration Activity
      /*  newdnewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, Registration.class));
            }
        });
*/
        // Recover Your Password using email
        reocverpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout = new LinearLayout(this);
        final EditText emailet = new EditText(this);//write your registered email
        emailet.setText("Email");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10, 10, 10, 10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emaill = emailet.getText().toString().trim();
                beginRecovery(emaill);//send a mail on the mail to recover password
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void beginRecovery(String emaill) {
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        // send reset password email
        mAuth.sendPasswordResetEmail(emaill).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(AdminLogin.this, "Done sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AdminLogin.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(AdminLogin.this, "Error Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginUser(String emaill, String pass) {
        loadingBar.setMessage("Logging In....");
        loadingBar.show();

        // sign in with email and password after authenticating
        mAuth.signInWithEmailAndPassword(emaill, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    loadingBar.dismiss();
                    FirebaseUser user = mAuth.getCurrentUser();

                    if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                        String email = user.getEmail();
                        String uid = user.getUid();
                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("email", email);
                        hashMap.put("uid", uid);
                        hashMap.put("name", "");
                        // hashMap.put("onlineStatus", "online");
                        //hashMap.put("typingTo", "noOne");
                       /* hashMap.put("phone", "");
                        hashMap.put("image", "");
                        hashMap.put("cover", "");*/
                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        // store the value in Database in "Users" Node
                        DatabaseReference reference = database.getReference("Admin");

                        // storing the value in Firebase
                        reference.child(uid).setValue(hashMap);
                    }
                    //  SharedPreferences.Editor editor= sharedPreferences.edit();
                    // editor.putString(Key_email,email.getText().toString());
                    //  editor.putString(Key_name,name);
                    //  editor.apply();
                    Toast.makeText(AdminLogin.this, "Registered Admin " + user.getEmail(), Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(AdminLogin.this, AdminDashboard.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(AdminLogin.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(AdminLogin.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

