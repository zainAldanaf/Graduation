package com.example.palliativecareguidelines;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.palliativecareguidelines.Doctors.DoctorProfile;
import com.example.palliativecareguidelines.Doctors.Doctorlogin;

public class AppSettingPage extends AppCompatActivity {

    private Switch switchDarkMode;
    private Button btnChangeLanguage, btnLogout, btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_setting_page);

        // Initialize UI elements
        switchDarkMode = findViewById(R.id.switch_dark_mode);
        btnChangeLanguage = findViewById(R.id.btn_change_language);
        btnLogout = findViewById(R.id.btn_logout);
        btnEditProfile = findViewById(R.id.btn_edit_profile);

        // Handle Dark Mode Toggle
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Enable Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(this, "Dark mode enabled", Toast.LENGTH_SHORT).show();
            } else {
                // Disable Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(this, "Dark mode disabled", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Language Change
        btnChangeLanguage.setOnClickListener(v -> {
            Intent intent = new Intent(AppSettingPage.this, LanguageSettingsActivity.class);
            startActivity(intent);
        });

        // Handle Logout
        btnLogout.setOnClickListener(v -> {
            // Clear user session (example)
            SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            // Redirect to login screen
            Intent intent = new Intent(AppSettingPage.this, Doctorlogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Handle Profile Editing
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(AppSettingPage.this, DoctorProfile.class);
            startActivity(intent);
        });
    }
}
