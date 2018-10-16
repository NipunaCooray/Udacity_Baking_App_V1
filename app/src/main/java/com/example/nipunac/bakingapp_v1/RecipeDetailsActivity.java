package com.example.nipunac.bakingapp_v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nipunac.bakingapp_v1.model.Ingredient;
import com.example.nipunac.bakingapp_v1.model.Step;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {

    private ArrayList<Step> steps;
    private ArrayList<Ingredient> ingredients;


    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Bundle selected_recipe = getIntent().getBundleExtra("selected_recipe");
        name = selected_recipe.getString("recipe_name");
        getSupportActionBar().setTitle(name);
        ingredients = selected_recipe.getParcelableArrayList("ingredients");
        steps = selected_recipe.getParcelableArrayList("steps");

    }
}
