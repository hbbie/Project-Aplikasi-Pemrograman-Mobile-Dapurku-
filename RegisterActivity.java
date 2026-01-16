package com.example.dapurkunaufal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private Button btnRegister;
    private TextView tvLoginLink;
    private ImageView backArrow;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inisialisasi Views
        backArrow = findViewById(R.id.back_arrow);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email_register);
        etPassword = findViewById(R.id.et_password_register);
        btnRegister = findViewById(R.id.btn_register);
        tvLoginLink = findViewById(R.id.tv_login_link);

        // Setup Listeners
        btnRegister.setOnClickListener(v -> attemptRegistration());
        // Kembali ke LoginActivity saat link diklik (menutup RegisterActivity)
        tvLoginLink.setOnClickListener(v -> finish());
        backArrow.setOnClickListener(v -> onBackPressed());
    }

    private void attemptRegistration() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validasi Input
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Nama harus diisi.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Email dan Password harus diisi.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password minimal 6 karakter.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proses Pendaftaran Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registrasi Berhasil
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Opsional: Simpan Nama Pengguna (Display Name)
                        if (user != null) {
                            updateUserProfile(user, name);
                        }

                        Toast.makeText(RegisterActivity.this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();
                        // GANTI: Navigasi ke LanguageActivity
                        navigateToLanguageActivity();
                    } else {
                        // Registrasi Gagal
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Pendaftaran gagal.";
                        Toast.makeText(RegisterActivity.this, "Gagal Daftar: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void updateUserProfile(FirebaseUser user, String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        System.err.println("Gagal update display name.");
                    }
                });
    }

    /**
     * Method baru untuk navigasi ke LanguageActivity setelah registrasi berhasil.
     */
    private void navigateToLanguageActivity() {
        // PERUBAHAN UTAMA: Target diubah menjadi LanguageActivity.class
        Intent intent = new Intent(RegisterActivity.this, LanguageActivity.class);

        // Membersihkan stack activity agar pengguna tidak bisa kembali ke halaman register/login
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}