package com.dogukanaras.gunluk_projesi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Settings extends AppCompatActivity {

    private Switch darkModeSwitch;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Karanlık mod ayarını uygulamanın başlangıcında yükle
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("dark_mode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        // Switch bileşenini id ile bul
        darkModeSwitch = findViewById(R.id.dark_mode_switch);

        // Switch'i kaydedilmiş tercihe göre ayarla
        darkModeSwitch.setChecked(sharedPreferences.getBoolean("dark_mode", false));

        // Switch'in durumunu dinle ve değişiklikleri uygula
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = sharedPreferences.edit();
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("dark_mode", true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("dark_mode", false);
                }
                editor.apply();
                // Tema değişikliğinin hemen uygulanması için Activity'yi yeniden başlat
                recreate();
            }
        });
    }
}