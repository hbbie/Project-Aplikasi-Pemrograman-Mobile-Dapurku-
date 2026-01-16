package com.example.dapurkunaufal;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context context;
    private List<Recipe> recipeList;
    private final OnRecipeClickListener listener;
    private final String currentLang;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public RecipeAdapter(Context context, List<Recipe> recipeList, OnRecipeClickListener listener) {
        this.context = context;
        // Inisialisasi list agar tidak NullPointerException
        this.recipeList = (recipeList != null) ? recipeList : new ArrayList<>();
        this.listener = listener;

        SharedPreferences pref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        this.currentLang = pref.getString("My_Lang", "en");
    }

    public void updateList(List<Recipe> newList) {
        this.recipeList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.component_recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe currentRecipe = recipeList.get(position);

        if (currentRecipe != null) {
            // PROTEKSI: Cek null sebelum setText agar tidak mental/crash
            String name = currentRecipe.getName(currentLang);
            String desc = currentRecipe.getDescription(currentLang);

            holder.title.setText(name != null ? name : "No Name");
            holder.description.setText(desc != null ? desc : "");

            // Memuat Gambar
            Glide.with(context)
                    .load(currentRecipe.getImageUrl())
                    .placeholder(R.drawable.nasi_goreng_image)
                    .error(R.drawable.nasi_goreng_image)
                    .into(holder.image);

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRecipeClick(currentRecipe);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, description;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recipe_image);
            title = itemView.findViewById(R.id.recipe_title);
            description = itemView.findViewById(R.id.recipe_description);
        }
    }
}