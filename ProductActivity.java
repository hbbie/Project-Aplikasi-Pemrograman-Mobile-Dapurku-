package com.example.dapurkunaufal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductActivity extends AppCompatActivity {

    private ImageView backArrow, recipeImage;
    private TextView tvTitle, tvDescription, tvIngredients, tvInstructions;
    private TextView labelIngredients, labelInstructions;
    private Button btnFavorite;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Recipe selectedRecipe;
    private boolean isFavorite = false;
    private String currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // 1. Ambil settingan bahasa aktif
        SharedPreferences pref = getSharedPreferences("Settings", MODE_PRIVATE);
        currentLang = pref.getString("My_Lang", "en");

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // 2. Inisialisasi Views
        backArrow = findViewById(R.id.back_arrow);
        recipeImage = findViewById(R.id.recipe_image_detail);
        tvTitle = findViewById(R.id.tv_recipe_title);
        tvDescription = findViewById(R.id.tv_recipe_description);
        tvIngredients = findViewById(R.id.tv_recipe_ingredients);
        tvInstructions = findViewById(R.id.tv_recipe_instructions);
        btnFavorite = findViewById(R.id.add_to_favorites_button);
        labelIngredients = findViewById(R.id.label_ingredients);
        labelInstructions = findViewById(R.id.label_instructions);

        // 3. Ambil data resep
        selectedRecipe = (Recipe) getIntent().getSerializableExtra("selected_recipe");

        if (selectedRecipe != null) {
            displayRecipeData(selectedRecipe);
            checkFavoriteStatus();
        }

        // 4. Listeners
        if (backArrow != null) {
            backArrow.setOnClickListener(v -> onBackPressed());
        }

        if (btnFavorite != null) {
            btnFavorite.setOnClickListener(v -> {
                if (isFavorite) {
                    removeFromFavorites();
                } else {
                    addToFavorites();
                }
            });
        }
    }

    private void checkFavoriteStatus() {
        String userId = mAuth.getUid();
        if (userId == null || selectedRecipe == null) return;

        DocumentReference docRef = db.collection("users").document(userId)
                .collection("favorites").document(selectedRecipe.getName_en());

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            isFavorite = documentSnapshot.exists();
            updateFavoriteButtonUI();
        });
    }

    private void updateFavoriteButtonUI() {
        if (btnFavorite == null) return;

        // Logika teks berdasarkan status isFavorite dan Bahasa
        if (isFavorite) {
            btnFavorite.setText(currentLang.equals("id") ? "Hapus dari Favorit" : "Remove from Favorites");
            btnFavorite.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_dark));
        } else {
            btnFavorite.setText(currentLang.equals("id") ? "Tambah ke Favorit" : "Add to Favorites");
            btnFavorite.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimaryDark));
        }
    }

    private void addToFavorites() {
        String userId = mAuth.getUid();
        if (userId == null) {
            String loginMsg = currentLang.equals("id") ? "Silakan login terlebih dahulu" : "Please login first";
            Toast.makeText(this, loginMsg, Toast.LENGTH_SHORT).show();
            return;
        }

        // --- UPDATE UI INSTAN (Optimistic Update) ---
        isFavorite = true;
        updateFavoriteButtonUI();

        db.collection("users").document(userId)
                .collection("favorites").document(selectedRecipe.getName_en())
                .set(selectedRecipe)
                .addOnSuccessListener(aVoid -> {
                    String successMsg = currentLang.equals("id") ? "Berhasil ditambah ke favorit!" : "Added to favorites!";
                    Toast.makeText(this, successMsg, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Balikkan UI jika gagal
                    isFavorite = false;
                    updateFavoriteButtonUI();
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void removeFromFavorites() {
        String userId = mAuth.getUid();
        if (userId == null) return;

        // --- UPDATE UI INSTAN (Optimistic Update) ---
        isFavorite = false;
        updateFavoriteButtonUI();

        db.collection("users").document(userId)
                .collection("favorites").document(selectedRecipe.getName_en())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    String removeMsg = currentLang.equals("id") ? "Dihapus dari favorit" : "Removed from favorites";
                    Toast.makeText(this, removeMsg, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Balikkan UI jika gagal
                    isFavorite = true;
                    updateFavoriteButtonUI();
                    Toast.makeText(this, "Failed to remove", Toast.LENGTH_SHORT).show();
                });
    }

    private void displayRecipeData(Recipe recipe) {
        tvTitle.setText(recipe.getName(currentLang));
        tvDescription.setText(recipe.getDescription(currentLang));
        tvIngredients.setText(recipe.getIngredients(currentLang));
        tvInstructions.setText(recipe.getInstructions(currentLang));

        // Menggunakan R.string agar label (Bahan/Instruksi) mengikuti sistem
        if (labelIngredients != null) labelIngredients.setText(getString(R.string.label_ingredients));
        if (labelInstructions != null) labelInstructions.setText(getString(R.string.label_instructions));

        Glide.with(this).load(recipe.getImageUrl())
                .placeholder(R.drawable.nasi_goreng_image)
                .error(R.drawable.nasi_goreng_image)
                .into(recipeImage);
    }
}