package com.example.nipunac.bakingapp_v1.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

//"quantity": 500,
//"measure": "G",
//"ingredient": "Mascapone Cheese(room temperature)"

    @SerializedName("quantity")
    private float quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
