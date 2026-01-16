package com.example.dapurkunaufal; // Pastikan package ini sesuai

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

// Pastikan kamu sudah membuat SelectLanguageActivity.java dan mendaftarkannya di AndroidManifest
public class LanguageActivity extends AppCompatActivity {

    private Button languageButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pastikan nama file layout sesuai dengan yang kamu gunakan (di sini, R.layout.activity_language)
        setContentView(R.layout.activity_language);

        // 1. Inisialisasi Komponen (Menghubungkan variabel Java dengan ID XML)
        languageButton = findViewById(R.id.language_button);
        backButton = findViewById(R.id.back_button);

        // 2. Menambahkan Listener untuk Tombol "Language"
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil method untuk navigasi ke Select Language Page
                navigateToSelectLanguagePage();
            }
        });

        // 3. Menambahkan Listener untuk Tombol Kembali (←)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fungsi untuk kembali ke Activity sebelumnya
                onBackPressed();
                // Atau: finish();
            }
        });
    }

    /**
     * Method untuk menangani navigasi ke SelectLanguageActivity.
     */
    private void navigateToSelectLanguagePage() {
        // Ganti SelectLanguageActivity.class dengan nama Activity tujuan yang benar
        Intent intent = new Intent(LanguageActivity.this, SelectLanguageActivity.class);

        // Memulai Activity baru
        startActivity(intent);
    }
}