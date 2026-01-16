package com.example.dapurkunaufal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class TakeLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_location);

        // Inisialisasi ID sesuai XML (language_button & backbutton9)
        Button takeBtn = findViewById(R.id.language_button);
        ImageView backBtn = findViewById(R.id.backbutton9);

        // Navigasi ke WelcomeActivity
        takeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TakeLocationActivity.this, WelcomeActivity.class);
            startActivity(intent);
            // Matikan halaman ini supaya nggak menumpuk
            finish();
        });

        // Tombol Back
        backBtn.setOnClickListener(v -> finish());
    }
}