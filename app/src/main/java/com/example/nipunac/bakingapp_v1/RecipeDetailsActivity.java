package com.example.nipunac.bakingapp_v1;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.nipunac.bakingapp_v1.model.Ingredient;
import com.example.nipunac.bakingapp_v1.model.Step;
import com.example.nipunac.bakingapp_v1.widget.RecipeListWidget;
import com.example.nipunac.bakingapp_v1.widget.WidgetUpdateService;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClickListener {

    private ArrayList<Step> mSteps;
    private ArrayList<Ingredient> mIngredients;

    boolean isTablet;

    String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle selected_recipe = getIntent().getBundleExtra("selected_recipe");
        mName = selected_recipe.getString("recipe_name");
        getSupportActionBar().setTitle(mName);
        mIngredients = selected_recipe.getParcelableArrayList("ingredients");
        mSteps = selected_recipe.getParcelableArrayList("steps");

        updateWidgetService();

        if(findViewById(R.id.master_detail_view) != null){
            isTablet = true;

            if(savedInstanceState == null){

                RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
                recipeDetailsFragment.setArguments(selected_recipe);

                Bundle bundle = new Bundle();
                bundle.putParcelable("step",mSteps.get(0));

                StepDetailFragment stepDetailsFragment = new StepDetailFragment();
                stepDetailsFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_details_fragment_container,recipeDetailsFragment)
                        .add(R.id.step_details_fragment_container,stepDetailsFragment)
                        .commit();

            }


        }else{
            isTablet = false;

            if(savedInstanceState == null){
                RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
                recipeDetailsFragment.setArguments(selected_recipe);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_details_fragment_container,recipeDetailsFragment)
                        .commit();
            }

        }

    }

    @Override
    public void onStepClicked(int position) {
        //Toast.makeText(this,"Clicked "+position,Toast.LENGTH_SHORT).show();

        if(isTablet){

            Bundle bundle = new Bundle();
            bundle.putParcelable("step",mSteps.get(position));

            StepDetailFragment stepDetailsFragment = new StepDetailFragment();
            stepDetailsFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_fragment_container,stepDetailsFragment)
                    .commit();


        }else{
            Intent StepDetailsActivityIntent = new Intent(RecipeDetailsActivity.this, StepDetailActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("step_description",mSteps.get(position).getShortDescription());
            bundle.putParcelable("step",mSteps.get(position));

            StepDetailsActivityIntent.putExtra("selected_step",bundle);

            startActivity(StepDetailsActivityIntent);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    void updateWidgetService()
    {
        Intent i = new Intent(this, WidgetUpdateService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredients", mIngredients);
        i.putExtra("bundle", bundle);
        i.setAction(WidgetUpdateService.WIDGET_UPDATE_ACTION);
        startService(i);


    }


}
