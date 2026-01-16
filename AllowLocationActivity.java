package com.example.dapurkunaufal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class AllowLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_location);

        // Inisialisasi ID sesuai XML
        Button allowBtn = findViewById(R.id.language_button);
        ImageView backBtn = findViewById(R.id.back_button3);

        // Navigasi ke TakeLocationActivity
        allowBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AllowLocationActivity.this, TakeLocationActivity.class);
            startActivity(intent);
            // Matikan halaman ini (Always close logic)
            finish();
        });

        // Tombol Back
        backBtn.setOnClickListener(v -> finish());
    }
}