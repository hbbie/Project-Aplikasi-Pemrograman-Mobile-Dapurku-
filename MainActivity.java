package com.example.dapurkunaufal; // Ganti dengan nama package proyek kamu

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Pastikan ini sesuai dengan nama file XML kamu

        // 1. Inisialisasi (Menghubungkan variabel Java dengan komponen XML menggunakan ID)
        getStartedButton = findViewById(R.id.get_started_button);

        // 2. Menambahkan Listener (Pendengar) pada tombol
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil method untuk navigasi
                navigateToLoginPage(); // Nama method diubah agar lebih jelas
            }
        });
    }

    /**
     * Method untuk menangani navigasi ke LoginActivity.
     */
    private void navigateToLoginPage() {
        // Membuat Intent baru:
        // Tujuan diganti dari LanguageActivity.class menjadi LoginActivity.class
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        // Memulai Activity baru
        startActivity(intent);

        // Opsional: Tutup MainActivity agar pengguna tidak bisa kembali ke halaman ini
        finish();
    }
}