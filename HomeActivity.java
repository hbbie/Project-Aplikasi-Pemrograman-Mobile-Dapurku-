package com.example.dapurkunaufal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    private LinearLayout bottomNavigationBar;
    private RecyclerView recipeRecyclerView;
    private RecipeAdapter recipeAdapter;
    private EditText searchEditText;

    // Pastikan inisialisasi di awal untuk menghindari NullPointer
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Recipe> allRecipesList = new ArrayList<>();

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private Button btnAll, btnFoods, btnDrinks;
    private String currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 1. SET BAHASA HARUS SUPER DETAIL
        applyLanguage();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inisialisasi Firebase
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Inisialisasi View
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        recipeRecyclerView = findViewById(R.id.recipe_recycler_view);
        searchEditText = findViewById(R.id.search_bar);
        btnAll = findViewById(R.id.btn_all);
        btnFoods = findViewById(R.id.btn_foods);
        btnDrinks = findViewById(R.id.btn_drinks);

        // Setup RecyclerView & Adapter (Harus langsung dipasang)
        if (recipeRecyclerView != null) {
            recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recipeAdapter = new RecipeAdapter(this, recipeList, this);
            recipeRecyclerView.setAdapter(recipeAdapter);

            // Panggil data dari Firestore
            fetchRecipesFromFirestore();
        }

        setupSearch();
        setupCategoryListeners();
        setupHeaderListeners();
        setupBottomNavigationListeners();
    }

    private void applyLanguage() {
        SharedPreferences pref = getSharedPreferences("Settings", MODE_PRIVATE);
        currentLang = pref.getString("My_Lang", "en");

        Locale locale = new Locale(currentLang);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);

        // Menggunakan updateConfiguration yang lebih kompatibel
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void fetchRecipesFromFirestore() {
        if (db == null) return;

        db.collection("recipes").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                recipeList.clear();
                allRecipesList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    try {
                        Recipe recipe = document.toObject(Recipe.class);
                        if (recipe != null) {
                            recipeList.add(recipe);
                            allRecipesList.add(recipe);
                        }
                    } catch (Exception e) {
                        // Mencegah satu data rusak merusak seluruh aplikasi
                        e.printStackTrace();
                    }
                }
                if (recipeAdapter != null) {
                    recipeAdapter.updateList(new ArrayList<>(recipeList));
                }
            } else {
                Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // --- Logika Search & Filter ---
    private void setupSearch() {
        if (searchEditText != null) {
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterByName(s.toString());
                }
                @Override public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void filterByName(String text) {
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe item : allRecipesList) {
            String name = item.getName(currentLang);
            if (name != null && name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (recipeAdapter != null) recipeAdapter.updateList(filteredList);
    }

    private void setupCategoryListeners() {
        if (btnAll != null) btnAll.setOnClickListener(v -> {
            setActiveButton(btnAll);
            if (recipeAdapter != null) recipeAdapter.updateList(new ArrayList<>(allRecipesList));
        });
        if (btnFoods != null) btnFoods.setOnClickListener(v -> {
            setActiveButton(btnFoods);
            filterByCategory("Foods");
        });
        if (btnDrinks != null) btnDrinks.setOnClickListener(v -> {
            setActiveButton(btnDrinks);
            filterByCategory("Drinks");
        });
    }

    private void filterByCategory(String category) {
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe item : allRecipesList) {
            if (item.getCategory() != null && item.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(item);
            }
        }
        if (recipeAdapter != null) recipeAdapter.updateList(filteredList);
    }

    private void setActiveButton(Button activeBtn) {
        resetButtonUI(btnAll);
        resetButtonUI(btnFoods);
        resetButtonUI(btnDrinks);
        if (activeBtn != null) {
            activeBtn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimaryDark));
            activeBtn.setTextColor(ContextCompat.getColor(this, R.color.colorBackgroundLight));
        }
    }

    private void resetButtonUI(Button btn) {
        if (btn != null) {
            btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorSurfaceLight));
            btn.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("selected_recipe", recipe);
        startActivity(intent);
    }

    private void setupHeaderListeners() {
        android.view.View notif = findViewById(R.id.notification_icon);
        android.view.View heart = findViewById(R.id.heart_icon);
        if (notif != null) notif.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        if (heart != null) heart.setOnClickListener(v -> startActivity(new Intent(this, FavoriteActivity.class)));
    }

    private void setupBottomNavigationListeners() {
        if (bottomNavigationBar != null && bottomNavigationBar.getChildCount() >= 3) {
            bottomNavigationBar.getChildAt(0).setOnClickListener(v ->
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show());
            bottomNavigationBar.getChildAt(1).setOnClickListener(v ->
                    startActivity(new Intent(this, SelectLanguageActivity.class)));
            bottomNavigationBar.getChildAt(2).setOnClickListener(v ->
                    startActivity(new Intent(this, TakeLocationActivity.class)));
        }
    }
}