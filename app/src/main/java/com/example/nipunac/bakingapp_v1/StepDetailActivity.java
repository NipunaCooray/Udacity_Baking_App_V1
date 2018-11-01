package com.example.nipunac.bakingapp_v1;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nipunac.bakingapp_v1.model.Step;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {

    int mClickedPosition;
    private ArrayList<Step> mSteps;

    String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if(savedInstanceState == null){
            Bundle selected_step_bundle = getIntent().getBundleExtra("selected_step");


            mSteps = selected_step_bundle.getParcelableArrayList("steps");

            mClickedPosition = selected_step_bundle.getInt("clicked_position");

            mName = selected_step_bundle.getString("recipe_name");

            getSupportActionBar().setTitle(mName);


            StepDetailFragment stepDetailsFragment = new StepDetailFragment();
            stepDetailsFragment.setArguments(selected_step_bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_details_fragment_container,stepDetailsFragment)
                    .commit();
        }




    }
}
