package com.example.palliativecareguidelines.Pationts;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.palliativecareguidelines.Adapters.MyAdapter;
import com.example.palliativecareguidelines.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private String currentUserId;

    private DatabaseReference databaseReference;
   FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
        tabLayout.addTab(tabLayout.newTab().setText("Contact"));
        tabLayout.addTab(tabLayout.newTab().setText("Request"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser==null)
        {
            sendUserToLoginActivity();
        }
        else
        {
            updateUserStatus("online");
            VerifyUserexistance();
        }
    }
    private void updateUserStatus(String state)
    {
        String savecurrentTime,savecurrentDate;
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("dd/MM/yyyy");
        savecurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
        savecurrentTime=currentTime.format(calendar.getTime());

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("time",savecurrentTime);
        hashMap.put("date",savecurrentDate);
        hashMap.put("state",state);

        currentUserId=firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Users").child(currentUserId).child("userState").updateChildren(hashMap);

    }
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null)
        {
            updateUserStatus("offline");
        }
    }

    private void VerifyUserexistance() {
        String currentUserID=firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("name").exists())
                {
                    //Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent loginintent=new Intent(ChatActivity.this,SettingsPage.class);
                    startActivity(loginintent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu,menu);

        return true;
    }
    private void sendUserToLoginActivity() {
        Intent loginintent=new Intent(ChatActivity.this,PationtLogin.class);
        loginintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginintent);
        finish();
    }


    private void sendUserToFindFriendsActivity() {
        Intent friendsintent=new Intent(ChatActivity.this,FindActivity.class);
        startActivity(friendsintent);
    }
    private void sendUserTosettingActivity() {
        Intent friendsintent=new Intent(ChatActivity.this,SettingsPage.class);
        startActivity(friendsintent);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.main_find_friends_menu)
        {
            sendUserToFindFriendsActivity();
        }
        if(id==R.id.main_settings_menu)
        {
            sendUserTosettingActivity();
        }

        else if(id==R.id.main_logout_menu)
        {
            firebaseAuth.signOut();
            sendUserToLoginActivity();
            Toast.makeText(ChatActivity.this,"User logged out succuessfully...",Toast.LENGTH_SHORT).show();
        }

        return true;
    }

}