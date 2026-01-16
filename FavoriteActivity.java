package com.example.dapurkunaufal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> favoriteList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private LinearLayout bottomNavigationBar;

    // Tambahkan variabel bahasa
    private String currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // 1. Ambil settingan bahasa aktif
        SharedPreferences pref = getSharedPreferences("Settings", MODE_PRIVATE);
        currentLang = pref.getString("My_Lang", "en");

        // 2. Inisialisasi Firebase & List
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        favoriteList = new ArrayList<>();

        // 3. Setup RecyclerView
        recyclerView = findViewById(R.id.rv_favorites);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        // 4. Ambil data favorit
        fetchFavoritesFromFirestore();

        // 5. Setup Listener
        setupListeners();
    }

    private void fetchFavoritesFromFirestore() {
        String userId = mAuth.getUid();
        if (userId == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users")
                .document(userId)
                .collection("favorites")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        favoriteList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Recipe recipe = document.toObject(Recipe.class);
                            favoriteList.add(recipe);
                        }

                        // Kirim currentLang ke adapter agar adapter tahu bahasa apa yang ditampilkan
                        adapter = new RecipeAdapter(this, favoriteList, this);
                        recyclerView.setAdapter(adapter);

                        if (favoriteList.isEmpty()) {
                            String msg = currentLang.equals("in") ? "Belum ada resep favorit" : "No favorite recipes yet";
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(FavoriteActivity.this, ProductActivity.class);
        intent.putExtra("selected_recipe", recipe);
        startActivity(intent);
    }

    private void setupListeners() {
        // --- HEADER ---
        ImageView backArrow = findViewById(R.id.back_arrow);
        if (backArrow != null) backArrow.setOnClickListener(v -> finish());

        ImageView profileIcon = findViewById(R.id.notification_icon);
        if (profileIcon != null) {
            profileIcon.setOnClickListener(v ->
                    startActivity(new Intent(FavoriteActivity.this, ProfileActivity.class)));
        }

        ImageView heartIconFilled = findViewById(R.id.heart_icon_filled);
        if (heartIconFilled != null) {
            heartIconFilled.setOnClickListener(v -> {
                String msg = currentLang.equals("in") ? "Anda di halaman favorit" : "Already in Favorites";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            });
        }

        // --- BOTTOM NAVIGATION ---
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        if (bottomNavigationBar != null && bottomNavigationBar.getChildCount() >= 3) {

            // Home
            bottomNavigationBar.getChildAt(0).setOnClickListener(v -> {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });

            // Language
            bottomNavigationBar.getChildAt(1).setOnClickListener(v ->
                    startActivity(new Intent(this, SelectLanguageActivity.class)));

            // Location
            bottomNavigationBar.getChildAt(2).setOnClickListener(v ->
                    startActivity(new Intent(this, TakeLocationActivity.class)));
        }
    }
}