package com.example.palliativecareguidelines.Doctors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.palliativecareguidelines.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.HashMap;

public class Settings extends AppCompatActivity {

    private Button UpdateAccountSettings;
    private EditText username,userstatus;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference Refdatabase;
    private String currentUserrID;
    private String photoUri;

    private StorageReference userprofilestoragereference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        firebaseAuth=FirebaseAuth.getInstance();
        currentUserrID=firebaseAuth.getCurrentUser().getUid();
        Refdatabase=FirebaseDatabase.getInstance().getReference();

        userprofilestoragereference= FirebaseStorage.getInstance().getReference().child("Profile name");

        UpdateAccountSettings=findViewById(R.id.update_settings_button);
        username=findViewById(R.id.set_user_name);
        userstatus=findViewById(R.id.set_profile_status);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Account Settings");

        UpdateAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSettings();
            }
        });

        RetrieveUserInfo();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




    }

    private void UpdateSettings() {

        String setusername=username.getText().toString();
        String setuserstatus=userstatus.getText().toString();

        if(TextUtils.isEmpty(setusername))
        {
            Toast.makeText(Settings.this,"Please write your user name first...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(setuserstatus))
        {
            Toast.makeText(Settings.this,"Please write your status first...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String,Object> profileMap= new HashMap<>();
            profileMap.put("uid",currentUserrID);
            profileMap.put("name",setusername);
            profileMap.put("status",setuserstatus);
            profileMap.put("image",photoUri);
            Refdatabase.child("Doctors").child(currentUserrID).updateChildren(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Settings.this,"Your profile has been updated...",Toast.LENGTH_SHORT).show();
                        sendUserToMainActivity();
                    }
                    else
                    {
                        String errormessage=task.getException().toString();
                        Toast.makeText(Settings.this,"Error :"+errormessage,Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
    private void sendUserToMainActivity() {
        Intent mainIntent=new Intent(Settings.this,ChatDoctor.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
///////////////////////////
    private void RetrieveUserInfo() {

        Refdatabase.child("Doctors").child(currentUserrID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists() && dataSnapshot.hasChild("name"))
                {
                    String retrieveusername=dataSnapshot.child("name").getValue().toString();

                    username.setText(retrieveusername);
                }
                else if(dataSnapshot.exists() && dataSnapshot.hasChild("name"))
                {
                    String retrieveusername=dataSnapshot.child("name").getValue().toString();


                    username.setText(retrieveusername);
                }
                else
                {
                    Toast.makeText(Settings.this,"Please set and update your profile information...",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
