package com.example.dapurkunaufal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity {

    private Button startButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 1. Inisialisasi Komponen sesuai ID di XML
        startButton = findViewById(R.id.btn_start_welcome);
        backButton = findViewById(R.id.back_button10);

        // 2. Klik Mulai -> Set Bahasa ke Inggris & Pindah ke Home
        if (startButton != null) {
            startButton.setOnClickListener(v -> {
                setLanguageToEnglish();
                navigateToHome();
            });
        }

        // 3. Klik Kembali -> Tutup halaman ini
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }
    }

    /**
     * Memaksa aplikasi menggunakan Bahasa Inggris ("en")
     * dan menyimpannya ke SharedPreferences.
     */
    private void setLanguageToEnglish() {
        String langCode = "en";

        // Simpan ke SharedPreferences agar dibaca oleh HomeActivity & RecipeAdapter
        SharedPreferences pref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("My_Lang", langCode);
        editor.apply();

        // Update konfigurasi Locale sistem saat ini
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    /**
     * Navigasi ke HomeActivity.
     */
    private void navigateToHome() {
        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);

        // Memastikan transisi berjalan lancar
        startActivity(intent);

        // Menutup WelcomeActivity agar tidak bisa di-back kembali ke sini
        finish();
    }
}