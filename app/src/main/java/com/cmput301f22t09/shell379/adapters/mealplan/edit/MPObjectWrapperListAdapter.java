package com.cmput301f22t09.shell379.adapters.mealplan.edit;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
 * Base class Adapter for recycler view in view selected ingredients page for recipes.
 */
public abstract class MPObjectWrapperListAdapter extends RecyclerView.Adapter<MPObjectWrapperListAdapter.MPEditIngredientListViewHolder> {
    protected MealPlanViewModel viewModel;

    /**
     * Custom view holder for describing each ingredient's view
     */
    public class MPEditIngredientListViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView amount;
        TextView remove;
        Button addServingsButton;
        Button subServingsButton;
        DatePicker editDatePicker;
        TextView amountLabel;

        public MPEditIngredientListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.mpv_name);
            this.amount = (TextView) itemView.findViewById(R.id.mpv_servings_val);
            this.remove = (TextView) itemView.findViewById(R.id.mpv_remove);
            this.addServingsButton = (Button) itemView.findViewById(R.id.mpe_add_btn);
            this.subServingsButton = (Button) itemView.findViewById(R.id.mpe_sub_btn);
            this.editDatePicker = (DatePicker) itemView.findViewById(R.id.edit_mpv_date);
            this.amountLabel = (TextView)  itemView.findViewById(R.id.mpv_servings_label);
        }

        public View getItemView(){
            return itemView;
        }
    }

    public MPObjectWrapperListAdapter(MealPlanViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public MPEditIngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mealplan_edit_list_item_15, parent, false);
        return new MPEditIngredientListViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MPEditIngredientListViewHolder holder, int position) {
        TextView name = holder.name;
        TextView amount = holder.amount;
        TextView remove = holder.remove;
        Button subServingButton = holder.subServingsButton;
        Button addServingButton = holder.addServingsButton;
        DatePicker editDate = holder.editDatePicker;

        subServingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subServings(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        addServingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addServings(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        editDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                Date newDate = new GregorianCalendar(
                        editDate.getYear(),
                        editDate.getMonth(),
                        editDate.getDayOfMonth()).getTime();
                updateDate(holder.getAdapterPosition(),newDate);
                try{
                    notifyDataSetChanged();
                }catch(Exception e){

                }
            }
        });

        MealPlanWrapper item = getItemAtIndex(position);

        name.setText(item.getName());
        amount.setText(item.getServings().toString());
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(item.getDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        editDate.updateDate(
                year,
                month,
                day);
    }


    @Override
    public int getItemCount() {
        return getSizeInternal();
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

    protected abstract int getSizeInternal();
}
