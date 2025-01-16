package com.example.palliativecareguidelines.Doctors;

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
import com.example.palliativecareguidelines.modules.PationtsModule;
import com.example.palliativecareguidelines.modules.doctorModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

public class Doctorsign extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    Button Sign_btn;
    EditText fullname;
    EditText birth_date;
    EditText address1;
    EditText emailaddress;
    TextView haveAccount;

    EditText phone_number;
    EditText pass;
    EditText confirmpass;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorsign);
        Sign_btn = findViewById(R.id.doctorregister);
        fullname = findViewById(R.id.doctorname);
        birth_date = findViewById(R.id.doctorbirth);
        haveAccount=findViewById(R.id.haveAccountlink);
        address1 = findViewById(R.id.doctoraddress);
        emailaddress = findViewById(R.id.doctoremailsign);
        phone_number = findViewById(R.id.doctorphone);
        pass = findViewById(R.id.doctorpassword);
        confirmpass = findViewById(R.id.doctorconfirmPass);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Doctors");
        progressDialog = new ProgressDialog(this);

        Sign_btn.setOnClickListener(view ->{
            createdoctorUser();
        });



        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doctorsign.this, Doctorlogin.class));

            }
        });
    }

    private void createdoctorUser() {
        String email = emailaddress.getText().toString();
        String password = pass.getText().toString();
        String address = address1.getText().toString();
        String birthdate= birth_date.getText().toString();
        String name = fullname.getText().toString();
        String confrimpassword = confirmpass.getText().toString();
        String phone = phone_number.getText().toString();

        if (TextUtils.isEmpty(email)){
            emailaddress.setError("Email cannot be empty");
            emailaddress.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            pass.setError("Password cannot be empty");
            pass.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            fullname.setError("Password cannot be empty");
            fullname.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            address1.setError("Password cannot be empty");
            address1.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            phone_number.setError("Password cannot be empty");
            phone_number.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            confirmpass.setError("Password cannot be empty");
            confirmpass.requestFocus();
        }else{
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        startActivity(new Intent(Doctorsign.this, DoctorHome.class));
                        finish();
                        doctorModule pationtsModule = new doctorModule(name, address, birthdate, email, phone, password, confrimpassword);
                        databaseReference.child(name).setValue(pationtsModule);
                        String deviceToken= FirebaseInstanceId.getInstance().getToken();
                        String currentUserID=firebaseAuth.getCurrentUser().getUid();
                        databaseReference.child("Doctors").child(currentUserID).setValue("");
                        databaseReference.child("Doctors").child(currentUserID).child("device_token").setValue(deviceToken);
                        firebaseFirestore.collection("Doctors").document(FirebaseAuth.getInstance().getUid()).set(
                                new PationtsModule(name,address,birthdate,email,phone,password,confrimpassword)
                        );
                        progressDialog.cancel();
                    }else{
                        Toast.makeText(Doctorsign.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                }
            });
        }
    }
}