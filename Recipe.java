package com.example.dapurkunaufal;

import java.io.Serializable;

public class Recipe implements Serializable {

    private String name_en, name_id;
    private String description_en, description_id;
    private String ingredients_en, ingredients_id;
    private String instructions_en, instructions_id;
    private String category;
    private String imageUrl;

    public Recipe() {}

    // GANTI "in" MENJADI "id" DISINI
    public String getName(String lang) {
        if ("id".equals(lang)) return name_id != null ? name_id : name_en;
        return name_en != null ? name_en : name_id;
    }

    public String getDescription(String lang) {
        if ("id".equals(lang)) return description_id != null ? description_id : description_en;
        return description_en != null ? description_en : description_id;
    }

    public String getIngredients(String lang) {
        if ("id".equals(lang)) return ingredients_id != null ? ingredients_id : ingredients_en;
        return ingredients_en != null ? ingredients_en : ingredients_id;
    }

    public String getInstructions(String lang) {
        if ("id".equals(lang)) return instructions_id != null ? instructions_id : instructions_en;
        return instructions_en != null ? instructions_en : instructions_id;
    }

    // Getter & Setter standar tetap sama...
    public String getName_en() { return name_en; }
    public void setName_en(String name_en) { this.name_en = name_en; }
    public String getName_id() { return name_id; }
    public void setName_id(String name_id) { this.name_id = name_id; }
    public String getDescription_en() { return description_en; }
    public void setDescription_en(String description_en) { this.description_en = description_en; }
    public String getDescription_id() { return description_id; }
    public void setDescription_id(String description_id) { this.description_id = description_id; }
    public String getIngredients_en() { return ingredients_en; }
    public void setIngredients_en(String ingredients_en) { this.ingredients_en = ingredients_en; }
    public String getIngredients_id() { return ingredients_id; }
    public void setIngredients_id(String ingredients_id) { this.ingredients_id = ingredients_id; }
    public String getInstructions_en() { return instructions_en; }
    public void setInstructions_en(String instructions_en) { this.instructions_en = instructions_en; }
    public String getInstructions_id() { return instructions_id; }
    public void setInstructions_id(String instructions_id) { this.instructions_id = instructions_id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}