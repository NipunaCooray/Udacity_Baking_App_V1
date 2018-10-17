package com.example.nipunac.bakingapp_v1.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nipunac.bakingapp_v1.R;
import com.example.nipunac.bakingapp_v1.model.Ingredient;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientListViewHolder>{

    private List<Ingredient> mIngredientList;

    public void setIngredients(List <Ingredient> ingredientList) {
        mIngredientList = ingredientList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredients_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new IngredientListAdapter.IngredientListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientListViewHolder ingredientListViewHolder, int position) {
        Ingredient selectedIngredient = mIngredientList.get(position);

        ingredientListViewHolder.mMeasure.setText(selectedIngredient.getMeasure());
        ingredientListViewHolder.mQuantity.setText(String.valueOf(selectedIngredient.getQuantity()));
        ingredientListViewHolder.mIngredients.setText(selectedIngredient.getIngredient());

    }

    @Override
    public int getItemCount() {
        if (null == mIngredientList) return 0;
        return mIngredientList.size();
    }



    public  class IngredientListViewHolder extends RecyclerView.ViewHolder {

        public final TextView mQuantity;
        public final TextView mMeasure;
        public final TextView mIngredients;

        public IngredientListViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuantity = (TextView) itemView.findViewById(R.id.quantity_text_view);
            mMeasure = (TextView) itemView.findViewById(R.id.measure_text_view);
            mIngredients = (TextView) itemView.findViewById(R.id.ingredient_text_view);

        }


    }
}
