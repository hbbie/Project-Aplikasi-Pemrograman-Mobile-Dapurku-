package com.example.dapurkunaufal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail;
    private Button btnLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // 1. Cek: Pastikan pengguna sudah login
        if (mAuth.getCurrentUser() == null) {
            navigateToLogin();
            return;
        }

        // 2. Inisialisasi Views
        tvName = findViewById(R.id.tv_profile_name);
        tvEmail = findViewById(R.id.tv_profile_email);
        btnLogout = findViewById(R.id.btn_logout);

        // 3. Muat data profil
        loadUserProfile();

        // 4. Setup Listener Logout
        btnLogout.setOnClickListener(v -> handleLogout());
    }

    private void loadUserProfile() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            // Mengambil nama dari Firebase Auth
            String userName = user.getDisplayName();

            // Menggunakan string resource untuk "Pengguna Dapurku" jika nama null
            // Supaya "Pengguna" bisa berubah jadi "User" saat ganti bahasa
            if (userName != null && !userName.isEmpty()) {
                tvName.setText(userName);
            } else {
                tvName.setText(getString(R.string.title_profile)); // Menggunakan label Profile sebagai fallback
            }

            tvEmail.setText(user.getEmail());
        }
    }

    private void handleLogout() {
        // Proses Logout Firebase
        mAuth.signOut();

        // MENGGUNAKAN STRING RESOURCE UNTUK TOAST
        Toast.makeText(this, getString(R.string.msg_logout_success), Toast.LENGTH_SHORT).show();

        // Arahkan ke halaman Login
        navigateToLogin();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        // Clear semua activity yang ada di stack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}