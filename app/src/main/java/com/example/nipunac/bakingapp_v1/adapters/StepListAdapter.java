package com.example.nipunac.bakingapp_v1.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.nipunac.bakingapp_v1.R;
import com.example.nipunac.bakingapp_v1.model.Step;

import java.util.List;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepListViewHolder> {

    private static final String TAG = "StepListAdapter";

    private List<Step> mStepList;

    private final StepListAdapter.StepItemClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface StepItemClickHandler {
        //void onClick(Step selectedStep);
        void onClick(int position);
    }

    public StepListAdapter(StepItemClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public void setSteps(List <Step> stepList) {
        mStepList = stepList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.steps_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new StepListAdapter.StepListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepListViewHolder stepListViewHolder, int position) {
        Step selectedStep = mStepList.get(position);

        stepListViewHolder.mStepShortDescription.setText(selectedStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null == mStepList) return 0;
        return mStepList.size();
    }

    public  class StepListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mStepShortDescription;

        public StepListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mStepShortDescription = (TextView) itemView.findViewById(R.id.short_step_description);

        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            //Step currentStep = mStepList.get(adapterPosition);
            mClickHandler.onClick(adapterPosition);
            Log.d(TAG,"Inside Adapter On Click");
        }
    }
}
