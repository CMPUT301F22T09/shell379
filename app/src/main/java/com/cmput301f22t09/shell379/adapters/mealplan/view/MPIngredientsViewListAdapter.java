package com.cmput301f22t09.shell379.adapters.mealplan.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;

/**
 * Adapter for recycler view in view selected ingredients page for recipes.
 */
public class MPIngredientsViewListAdapter extends RecyclerView.Adapter<MPIngredientsViewListAdapter.MPViewIngredientListViewHolder> {
    private MealPlanViewModel viewModel;

    /**
     * Custom view holder for describing each ingredient's view
     */
    public class MPViewIngredientListViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView amount;
        TextView date;

        public MPViewIngredientListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.mpv_name);
            this.amount = (TextView) itemView.findViewById(R.id.mpv_servings_textView);
            this.date = (TextView) itemView.findViewById(R.id.mpv_date_textView);
        }

        public View getItemView(){
            return itemView;
        }
    }

    public MPIngredientsViewListAdapter(MealPlanViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Updates the recycler view and re-renders it
     * @param newIngredients the new list of ingredients for the recycler view
     */
    public void updateIngredients(ArrayList<MealPlanWrapper<Ingredient>> newIngredients){
        viewModel.setIngredientsRaw(newIngredients);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MPViewIngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mealplan_view_list_item_14, parent, false);
        return new MPViewIngredientListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MPViewIngredientListViewHolder holder, int position) {
        TextView name = holder.name;
        TextView amount = holder.amount;
        TextView date = holder.date;

        name.setText(viewModel.getIngredientAtIdx(position).getName());
        amount.setText(viewModel.getIngredientAtIdx(position).getServings());
        date.setText(viewModel.getIngredientAtIdx(position).getDisplayDate());
    }

    @Override
    public int getItemCount() {
        return viewModel.getIngredients().size();
    }

    public ArrayList<MealPlanWrapper<Ingredient>> getIngredients(){
        return viewModel.getIngredients();
    }
}
