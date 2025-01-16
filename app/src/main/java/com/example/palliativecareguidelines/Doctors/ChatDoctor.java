package com.example.palliativecareguidelines.Doctors;


import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.palliativecareguidelines.Adapters.MyAdapter;
import com.example.palliativecareguidelines.Adapters.MyAdapter2;
import com.example.palliativecareguidelines.Pationts.ChatActivity;
import com.example.palliativecareguidelines.Pationts.SettingsPage;
import com.example.palliativecareguidelines.R;
import com.google.android.material.tabs.TabLayout;

public class ChatDoctor extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_doctor);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout2);
        viewPager = (ViewPager) findViewById(R.id.viewPager2);

        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
        tabLayout.addTab(tabLayout.newTab().setText("Contact"));
        tabLayout.addTab(tabLayout.newTab().setText("Request"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter2 adapter = new MyAdapter2(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

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

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu2,menu);

        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.main_find_friends)
        {
            sendUserToFindFriendsActivity();
        }
        if(id==R.id.main_settings)
        {
            sendUserTosettingActivity();
        }



        return true;
    }

    private void sendUserToFindFriendsActivity() {
        Intent friendsintent=new Intent(ChatDoctor.this, FindPationts.class);
        startActivity(friendsintent);
    }

    private void sendUserTosettingActivity() {
        Intent friendsintent=new Intent(ChatDoctor.this, Settings.class);
        startActivity(friendsintent);
    }
}