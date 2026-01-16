package com.example.dapurkunaufal; // Ganti dengan nama package proyek kamu

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectLanguageActivity extends AppCompatActivity {

    private Button englishButton;
    private Button indonesiaButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pastikan nama file layout ini sesuai (R.layout.activity_select_language jika nama file XML berbeda)
        setContentView(R.layout.activity_select_language);

        // 1. Inisialisasi Komponen
        englishButton = findViewById(R.id.english_button);
        indonesiaButton = findViewById(R.id.indonesia_button);
        backButton = findViewById(R.id.back_button8); // Menggunakan ID yang ada di XML kamu

        // 2. Listener untuk Tombol "English"
        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil method untuk navigasi ke AllowLocationActivity
                navigateToEnglishLocation();
            }
        });

        // 3. Listener untuk Tombol "Indonesia"
        indonesiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil method untuk navigasi ke IzinkanLokasiActivity
                navigateToIndonesianLocation();
            }
        });

        // 4. Listener untuk Tombol Kembali
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Navigasi ke Activity lokasi dalam bahasa Inggris (AllowLocationActivity).
     */
    private void navigateToEnglishLocation() {
        // Ganti dengan nama Activity tujuan yang benar
        Intent intent = new Intent(SelectLanguageActivity.this, AllowLocationActivity.class);
        startActivity(intent);
        // finish(); // Opsional: tutup SelectLanguageActivity jika sudah tidak diperlukan
    }

    /**
     * Navigasi ke Activity lokasi dalam bahasa Indonesia (IzinkanLokasiActivity).
     */
    private void navigateToIndonesianLocation() {
        // Ganti dengan nama Activity tujuan yang benar
        Intent intent = new Intent(SelectLanguageActivity.this, IzinkanLokasiActivity.class);
        startActivity(intent);
        // finish(); // Opsional
    }
}