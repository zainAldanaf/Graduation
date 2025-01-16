package com.example.palliativecareguidelines.Pationts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.palliativecareguidelines.Doctors.Doctorlogin;
import com.example.palliativecareguidelines.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class chooseScreen extends AppCompatActivity {
    CardView patientCard;
    CardView doctorCard;
    FirebaseAnalytics firebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_screen);

        patientCard = findViewById(R.id.patientCard);
        doctorCard = findViewById(R.id.doctorCard);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        doctorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEvent("Doctorcard","doctor","CardView");
                startActivity(new Intent(chooseScreen.this, Doctorlogin.class));
                finish();

            }
        });

        patientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEvent("Doctorpationts","pationts","CardView");
                startActivity(new Intent(chooseScreen.this, PationtLogin.class));
                finish();
            }
        });
    }
    public  void btnEvent(String id,String name,String content){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}