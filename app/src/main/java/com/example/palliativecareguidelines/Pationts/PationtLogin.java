package com.example.palliativecareguidelines.Pationts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palliativecareguidelines.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PationtLogin extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    EditText email1;
    EditText pass;
    private FirebaseAnalytics mFirebaseAnalytics;

    Button btn_login;
    TextView dont_have_account;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pationt_login);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        email1 = findViewById(R.id.emailLogin);
        pass = findViewById(R.id.passLogin);
        btn_login = findViewById(R.id.login);
        dont_have_account = findViewById(R.id.donthaveAccount);

        progressDialog=new ProgressDialog(this);
        btn_login.setOnClickListener(view -> {
            loginUser();
            finish();

        });

       dont_have_account.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(PationtLogin.this, PationtSign.class));
                finish();

           }
       });
        if (firebaseAuth.getCurrentUser()!= null){
            startActivity(new Intent(PationtLogin.this, HomeScreen.class));
            finish();

        }

    }

    private void loginUser(){
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
                        btnEvent("login","Pationt","Button");
                        Toast.makeText(PationtLogin.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PationtLogin.this, HomeScreen.class));
                        progressDialog.cancel();
                        finish();

                    }else{
                        Toast.makeText(PationtLogin.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                }
            });
        }
    }

    public  void btnEvent(String id,String name,String content){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}