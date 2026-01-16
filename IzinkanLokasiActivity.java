package com.example.dapurkunaufal; // Ganti dengan nama package proyek kamu

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class IzinkanLokasiActivity extends AppCompatActivity {

    private Button izinkanLokasiButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pastikan nama file layout ini sesuai (R.layout.activity_izinkan_lokasi jika nama file XML berbeda)
        setContentView(R.layout.activity_izinkan_lokasi);

        // 1. Inisialisasi Komponen
        // ID tombol di XML adalah 'language_button'
        izinkanLokasiButton = findViewById(R.id.language_button);
        // ID tombol back di XML adalah 'back_button2'
        backButton = findViewById(R.id.back_button2);

        // 2. Menambahkan Listener untuk Tombol "Izinkan Lokasi"
        izinkanLokasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil method untuk navigasi ke AmbilLokasiActivity
                navigateToAmbilLokasi();
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
     * Method untuk menangani navigasi ke AmbilLokasiActivity.
     */
    private void navigateToAmbilLokasi() {
        // Buat Intent baru, menuju ke Activity AmbilLokasiActivity
        // Kamu harus memastikan AmbilLokasiActivity sudah dibuat.
        Intent intent = new Intent(IzinkanLokasiActivity.this, AmbilLokasiActivity.class);

        // Memulai Activity baru
        startActivity(intent);
    }
}