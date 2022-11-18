package com.cmput301f22t09.shell379.adapters.mealplan.edit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Adapter for recycler view in view selected ingredients page for recipes.
 */
public abstract class MPObjectWrapperListAdapter extends RecyclerView.Adapter<MPObjectWrapperListAdapter.MPEditIngredientListViewHolder> {
    protected MealPlanViewModel viewModel;

    /**
     * Custom view holder for describing each ingredient's view
     */
    public class MPEditIngredientListViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView amount;
        TextView date;
        TextView remove;
        Button addServingsButton;
        Button subServingsButton;
        DatePicker editDatePicker;

        public MPEditIngredientListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.mpv_name);
            this.amount = (TextView) itemView.findViewById(R.id.mpv_servings_textView);
            this.date = (TextView) itemView.findViewById(R.id.mpv_date_textView);
            this.remove = (TextView) itemView.findViewById(R.id.mpv_remove);
            this.addServingsButton = (Button) itemView.findViewById(R.id.mpe_add_btn);
            this.subServingsButton = (Button) itemView.findViewById(R.id.mpe_sub_btn);
            this.editDatePicker = (DatePicker) itemView.findViewById(R.id.edit_mpv_date);
        }

        public View getItemView(){
            return itemView;
        }
    }

    public MPObjectWrapperListAdapter(MealPlanViewModel viewModel) {
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
    public MPEditIngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mealplan_view_list_item_14, parent, false);
        return new MPEditIngredientListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MPEditIngredientListViewHolder holder, int position) {
        TextView name = holder.name;
        TextView amount = holder.amount;
        TextView date = holder.date;
        TextView remove = holder.remove;
        Button subServingButton = holder.subServingsButton;
        Button addServingButton = holder.addServingsButton;
        DatePicker editDate = holder.editDatePicker;

        subServingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subServings(position);
                notifyDataSetChanged();
            }
        });
        addServingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addServings(position);
                notifyDataSetChanged();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
                notifyDataSetChanged();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date newDate = new GregorianCalendar(
                        editDate.getYear(),
                        editDate.getMonth(),
                        editDate.getDayOfMonth()).getTime();
                updateDate(position,newDate);
                notifyDataSetChanged();
            }
        });

        MealPlanWrapper item = getItemAtIndex(position);

        name.setText(item.getName());
        amount.setText(item.getServings());
        date.setText(item.getDisplayDate());
    }


    @Override
    public int getItemCount() {
        return viewModel.getIngredients().size();
    }

    public ArrayList<MealPlanWrapper<Ingredient>> getIngredients(){
        return viewModel.getIngredients();
    }

    /**
     * removes item from meal plan
     * @param index index of the item
     */
    protected abstract void removeItem(int index);

    /**
     * Add servings to the item.
     * @param index index of the item
     */
    protected abstract void addServings(int index);

    /**
     * Remove servings to the item.
     * @param index index of the item
     */
    protected abstract void subServings(int index);

    /**
     * Update item date in viewmodel
     * @param index index of item
     * @param newDate new date
     */
    protected abstract void updateDate(int index, Date newDate);

    /**
     * gets item at index
     * @param index index of item
     * @return item at index
     */
    protected abstract MealPlanWrapper getItemAtIndex(int index);
}
