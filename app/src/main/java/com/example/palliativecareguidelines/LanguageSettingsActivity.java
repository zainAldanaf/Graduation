package com.example.palliativecareguidelines;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.service.chooser.ChooserAction;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.palliativecareguidelines.Pationts.chooseScreen;

import java.util.Locale;


public class LanguageSettingsActivity extends AppCompatActivity {

    private Button btnEnglish, btnArabic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_language_settings);

        btnEnglish = findViewById(R.id.btn_english);
        btnArabic = findViewById(R.id.btn_arabic);

        // Set English language
        btnEnglish.setOnClickListener(v -> setLocale("en"));

        // Set Arabic language
        btnArabic.setOnClickListener(v -> setLocale("ar"));
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Save selected language in SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

        // Restart activity to apply changes
        Intent intent = new Intent(LanguageSettingsActivity.this, chooseScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Load saved language
    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }
}
