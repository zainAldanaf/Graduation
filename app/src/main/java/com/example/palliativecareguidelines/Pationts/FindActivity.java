package com.example.palliativecareguidelines.Pationts;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.palliativecareguidelines.Fragments.Finddoctors;
import com.example.palliativecareguidelines.Fragments.Findpationt;
import com.example.palliativecareguidelines.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.*;

public class FindActivity extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.pationts);
    }

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId()) {
            case R.id.pationts:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new Findpationt())
                        .commit();
                return true;

            case R.id.doctors:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new Finddoctors())
                        .commit();
                return true;


        }
        return false;
    }
}
