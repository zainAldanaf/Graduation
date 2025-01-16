package com.example.palliativecareguidelines.Doctors;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palliativecareguidelines.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorProfile extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ;private FirebaseUser firebaseAuth;
    private FirebaseAuth Auth;
    TextView fullname;
    TextView birthdate;
    TextView address1;
    TextView emailaddress;
    TextView phone_number;
    String uid;
    TextView pass;
    Button logout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        Auth=FirebaseAuth.getInstance();
        uid=firebaseAuth.getUid().toString();
        Log.e(TAG, uid);
        fullname=findViewById(R.id.profilename1);

        birthdate= findViewById(R.id.birth_dateprofile1);
        address1= findViewById(R.id.addressprofile1);
        emailaddress=  findViewById(R.id.emailprofile1);
        phone_number=  findViewById(R.id.phoneprofile1);
        pass= findViewById(R.id.password_profile1);
        logout=findViewById(R.id.logout1);

        getProfile();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.signOut();
                startActivity(new Intent(DoctorProfile.this, Doctorlogin.class));

            }
        });
    }
    public  void getProfile(){

        db.collection("Doctors").document(uid.toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name=documentSnapshot.getString("fullname");
                    String email=documentSnapshot.getString("email");
                    String address=documentSnapshot.getString("address");
                    String birth_date=documentSnapshot.getString("birthdate");
                    String password=documentSnapshot.getString("password");
                    String phonenumber=documentSnapshot.getString("phone");
//
                    fullname.setText(name);
                    address1.setText(address);
                    emailaddress.setText(email);
                    birthdate.setText(birth_date);
                    pass.setText(password);
                    phone_number.setText(phonenumber);

                }





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DoctorProfile.this, "failed!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}