package com.example.dapurkunaufal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SelamatDatangActivity extends AppCompatActivity {

    private Button mulaiButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selamat_datang);

        mulaiButton = findViewById(R.id.language_button2);
        backButton = findViewById(R.id.back_button7);

        if (mulaiButton != null) {
            mulaiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1. PAKSA SET BAHASA KE INDONESIA SEBELUM PINDAH
                    saveLanguageToSharedPrefs("id");

                    // 2. PINDAH KE HOME
                    navigateToHome();
                }
            });
        }

        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }
    }

    // Fungsi untuk mengunci pilihan bahasa ke Indonesia
    private void saveLanguageToSharedPrefs(String langCode) {
        SharedPreferences pref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("My_Lang", langCode);
        editor.apply();
    }

    private void navigateToHome() {
        Intent intent = new Intent(SelamatDatangActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}