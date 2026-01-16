package com.example.dapurkunaufal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegisterLink;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // 1. Cek: Jika pengguna sudah login, langsung lewati ke LanguageActivity
        if (mAuth.getCurrentUser() != null) {
            navigateToLanguageActivity(); // <-- Perubahan di sini
            return;
        }

        // 2. Inisialisasi Views
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegisterLink = findViewById(R.id.tv_register_link);

        // 3. Setup Listeners
        btnLogin.setOnClickListener(v -> attemptLogin());

        // Asumsi kamu membuat kelas RegisterActivity.java
        tvRegisterLink.setOnClickListener(v -> navigateToRegisterActivity());
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Email dan Password harus diisi.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proses Login Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login Berhasil
                        Toast.makeText(LoginActivity.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
                        navigateToLanguageActivity(); // <-- Perubahan di sini
                    } else {
                        // Login Gagal
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Login gagal.";
                        Toast.makeText(LoginActivity.this, "Gagal Login: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * Method untuk navigasi ke LanguageActivity.
     * Menggantikan navigateToHomeActivity.
     */
    private void navigateToLanguageActivity() {
        // PERUBAHAN UTAMA: Target diubah menjadi LanguageActivity.class
        Intent intent = new Intent(LoginActivity.this, LanguageActivity.class);

        // Flag ini mencegah user kembali ke halaman login dengan tombol back
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void navigateToRegisterActivity() {
        // Ganti RegisterActivity.class jika kamu menggunakan nama lain
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}