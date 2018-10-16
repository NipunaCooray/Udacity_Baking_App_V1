package com.example.nipunac.bakingapp_v1.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nipunac.bakingapp_v1.R;
import com.example.nipunac.bakingapp_v1.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainScreenAdapter extends RecyclerView.Adapter<MainScreenAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList;

    private final RecepiItemOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface RecepiItemOnClickHandler {
        void onClick(Recipe selectedMovie);
    }

    public MainScreenAdapter(RecepiItemOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public void setRecipes(List <Recipe> recipeList) {
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {
        Recipe selectedRecipe = mRecipeList.get(position);


        if(selectedRecipe.getImage() != null && !selectedRecipe.getImage().isEmpty()) {
            //This is in hypothetical context
            Picasso.get().load(selectedRecipe.getImage()).into(recipeViewHolder.mRecipeImage);
        }else{


        }

        recipeViewHolder.mRecipeName.setText(selectedRecipe.getName());
    }

    @Override
    public int getItemCount() {
        if (null == mRecipeList) return 0;
        return mRecipeList.size();
    }



    public  class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mRecipeImage;
        public final TextView mRecipeName;


        public RecipeViewHolder(@NonNull View view) {
            super(view);

            mRecipeImage = (ImageView) view.findViewById(R.id.recipe_image);
            mRecipeName = (TextView) view.findViewById(R.id.recipe_name);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe currentRecipe = mRecipeList.get(adapterPosition);
            mClickHandler.onClick(currentRecipe);
        }
    }

}
