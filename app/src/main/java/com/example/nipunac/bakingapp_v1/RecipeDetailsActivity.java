package com.example.nipunac.bakingapp_v1;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.nipunac.bakingapp_v1.model.Ingredient;
import com.example.nipunac.bakingapp_v1.model.Step;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClickListener {

    private ArrayList<Step> mSteps;
    private ArrayList<Ingredient> mIngredients;


    String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Bundle selected_recipe = getIntent().getBundleExtra("selected_recipe");
        mName = selected_recipe.getString("recipe_name");
        getSupportActionBar().setTitle(mName);
        mIngredients = selected_recipe.getParcelableArrayList("ingredients");
        mSteps = selected_recipe.getParcelableArrayList("steps");


        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setArguments(selected_recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_details_fragment_container,recipeDetailsFragment)
                .commit();

    }

    @Override
    public void onStepClicked(int position) {
        //Toast.makeText(this,"Clicked "+position,Toast.LENGTH_SHORT).show();

        Intent StepDetailsActivityIntent = new Intent(RecipeDetailsActivity.this, StepDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("step_description",mSteps.get(position).getShortDescription());
        bundle.putParcelable("step",mSteps.get(position));

        StepDetailsActivityIntent.putExtra("selected_step",bundle);

        startActivity(StepDetailsActivityIntent);
    }
}
