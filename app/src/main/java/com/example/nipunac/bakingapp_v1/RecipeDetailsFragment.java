package com.example.nipunac.bakingapp_v1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nipunac.bakingapp_v1.adapters.IngredientListAdapter;
import com.example.nipunac.bakingapp_v1.adapters.MainScreenAdapter;
import com.example.nipunac.bakingapp_v1.adapters.StepListAdapter;
import com.example.nipunac.bakingapp_v1.model.Ingredient;
import com.example.nipunac.bakingapp_v1.model.Recipe;
import com.example.nipunac.bakingapp_v1.model.Step;

import java.util.ArrayList;

public class RecipeDetailsFragment extends Fragment implements StepListAdapter.StepItemClickHandler{

    ArrayList<Step> mSteps = null;
    ArrayList<Ingredient> mIngredients = null;

    private RecyclerView mRecyclerViewSteps;
    private StepListAdapter mStepListAdapter;

    private RecyclerView mRecyclerViewIngredients;
    private IngredientListAdapter mIngredientListAdapter;

    OnStepClickListener mCallBack;



    public interface OnStepClickListener{
        void onStepClicked(int position);
    }

    public RecipeDetailsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_details,container,false);

        Bundle extra = getArguments();
        mSteps = extra.getParcelableArrayList("steps");

        mIngredients = extra.getParcelableArrayList("ingredients");

        //Setting up steps recycler view

        mRecyclerViewSteps =  rootView.findViewById(R.id.recipe_steps_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());

        mRecyclerViewSteps.setLayoutManager(layoutManager);
        mRecyclerViewSteps.setHasFixedSize(true);

        mStepListAdapter = new StepListAdapter(this);

        mStepListAdapter.setSteps(mSteps);

        mRecyclerViewSteps.setAdapter(mStepListAdapter);


        //Setting up ingredients recycler view

        mRecyclerViewIngredients =  rootView.findViewById(R.id.ingredient_steps_recycler_view);
        LinearLayoutManager layoutManagerHorizontol = new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.HORIZONTAL, false);

        mRecyclerViewIngredients.setLayoutManager(layoutManagerHorizontol);
        mRecyclerViewIngredients.setHasFixedSize(true);

        mIngredientListAdapter = new IngredientListAdapter();

        mIngredientListAdapter.setIngredients(mIngredients);

        mRecyclerViewIngredients.setAdapter(mIngredientListAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallBack = (OnStepClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement OnStepClickListener");
        }
    }

    @Override
    public void onClick(int position) {
        mCallBack.onStepClicked(position);
    }


}
