package com.example.dapurkunaufal; // Ganti dengan nama package proyek kamu

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AmbilLokasiActivity extends AppCompatActivity {

    private Button ambilLokasiButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pastikan nama file layout ini sesuai (R.layout.activity_ambil_lokasi)
        setContentView(R.layout.activity_ambil_lokasi);

        // 1. Inisialisasi Komponen
        // ID tombol di XML adalah 'language_button'
        ambilLokasiButton = findViewById(R.id.language_button);
        // ID tombol back di XML adalah 'back_button4'
        backButton = findViewById(R.id.back_button4);

        // 2. Menambahkan Listener untuk Tombol "Ambil Lokasi"
        ambilLokasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil method untuk navigasi ke LokasiActivity
                navigateToLokasiActivity();
            }
        });

        // 3. Menambahkan Listener untuk Tombol Kembali (←)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fungsi untuk kembali ke Activity sebelumnya
                onBackPressed();
            }
        });
    }

    /**
     * Method untuk menangani navigasi ke LokasiActivity.
     */
    private void navigateToLokasiActivity() {
        // Buat Intent baru, menuju ke Activity LokasiActivity
        // Kamu harus memastikan LokasiActivity sudah dibuat.
        Intent intent = new Intent(AmbilLokasiActivity.this, LokasiActivity.class);

        // Memulai Activity baru
        startActivity(intent);
    }
}