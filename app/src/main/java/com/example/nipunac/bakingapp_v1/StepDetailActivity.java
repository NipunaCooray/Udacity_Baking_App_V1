package com.example.nipunac.bakingapp_v1;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nipunac.bakingapp_v1.model.Step;

public class StepDetailActivity extends AppCompatActivity {

    String mStepDescription;
    Step mStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Bundle selected_step_bundle = getIntent().getBundleExtra("selected_step");
        mStepDescription = selected_step_bundle.getString("step_description");
        getSupportActionBar().setTitle(mStepDescription);
        mStep = selected_step_bundle.getParcelable("step");

        StepDetailFragment stepDetailsFragment = new StepDetailFragment();
        stepDetailsFragment.setArguments(selected_step_bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.step_details_fragment_container,stepDetailsFragment)
                .commit();


    }
}
