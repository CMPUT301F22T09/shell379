package com.cmput301f22t09.shell379.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;

import java.util.ArrayList;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.MealPlanViewHolder>{

    private ArrayList<MealPlan> mealPlans;
    private Environment envViewModel;
    private MealPlanAdapter.AdaptorListener mealPlanListener;

    public interface AdaptorListener {
        public void navigateToViewMealPlan(int index);
    }
//    private int mealPlanIndex;

    public class MealPlanViewHolder extends RecyclerView.ViewHolder {
        public View getItemView(){
            return itemView;
        }

        private TextView mealPlantName;
        private TextView startDate;
        private TextView endDate;

        /**
         * Construct the MealPlanViewHolder class
         * @param itemView
         */
        public MealPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mealPlantName = (TextView) itemView.findViewById(R.id.meal_name);
            this.endDate = (TextView) itemView.findViewById(R.id.end_date);
            this.startDate = (TextView) itemView.findViewById(R.id.start_date);

        }
    }

    public MealPlanAdapter(ArrayList<MealPlan> data, Environment envViewModel, AdaptorListener mealPlanListener){
        this.envViewModel = envViewModel;
        this.mealPlans = data;
        this.mealPlanListener = mealPlanListener;
    }


    @NonNull
    @Override
    public MealPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meal_plan_content_13, parent, false);
        MealPlanViewHolder mealPlanViewHolder = new MealPlanViewHolder(view);
        return mealPlanViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanViewHolder holder, int position) {
        TextView mealPlanName = holder.mealPlantName;
        TextView  startDate = holder.startDate;
        TextView endDate = holder.endDate;


        mealPlanName.setText(mealPlans.get(position).getMealPlanName());
//        startDate.setText(mealPlans.get(position).getStartDate());
        startDate.setText(mealPlans.get(position).getStartDateFormatted());
        endDate.setText(mealPlans.get(position).getEndDateFormatted());


        View itemView = holder.getItemView();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealPlanOnClick(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mealPlans.size();
    }

    public void updateMealPlan(ArrayList<MealPlan> newMealPlan){
        mealPlans = newMealPlan;
        notifyDataSetChanged();
    }


    public void mealPlanOnClick(int i) {
        MealPlan m = mealPlans.get(i);
        LiveCollection<MealPlan> mealPlanCollection = envViewModel.getMealPlans();
        mealPlanCollection.getList().indexOf(m);
        mealPlanListener.navigateToViewMealPlan(i);
    }

// maybe dont need this (only for testing purpose)
//    public MealPlan getMealPlan(int index){
//        MealPlan mealPlan = mealPlans.get(index);
//        return mealPlan;
//    }
}


