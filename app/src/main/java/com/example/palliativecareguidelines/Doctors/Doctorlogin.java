package com.example.palliativecareguidelines.Doctors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palliativecareguidelines.Pationts.HomeScreen;
import com.example.palliativecareguidelines.Pationts.PationtLogin;
import com.example.palliativecareguidelines.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Doctorlogin extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    EditText email1;
    EditText pass;
    Button btn_login;
    TextView dont_have_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlogin);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        email1 = findViewById(R.id.doctoremail);
        pass = findViewById(R.id.doctorpass);
        btn_login = findViewById(R.id.doctorlogin);
        dont_have_account = findViewById(R.id.donthaveAccountlink);

        progressDialog=new ProgressDialog(this);
        btn_login.setOnClickListener(view -> {
            logindoctorUser();
        });

        dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doctorlogin.this, Doctorsign.class));


            }
        });
        if (firebaseAuth.getCurrentUser()!= null){
            startActivity(new Intent(Doctorlogin.this, DoctorHome.class));

        }
    }

    private void logindoctorUser() {
        String email = email1.getText().toString();
        String password = pass.getText().toString();

        if (TextUtils.isEmpty(email)){
            email1.setError("Email cannot be empty");
            email1.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            pass.setError("Password cannot be empty");
            pass.requestFocus();
        }else{
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Doctorlogin.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Doctorlogin.this, DoctorHome.class));
                        finish();
                        progressDialog.cancel();
                    }else{
                        Toast.makeText(Doctorlogin.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                }
            });
        }
    }

}