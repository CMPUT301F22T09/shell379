package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.Environment;

public class AddRecipeToMPFragment extends Fragment {
    private Environment envViewModel;
    private NavController navController;
    RecyclerView addRecipeToMP_recyclerView;
    RecyclerView.LayoutManager layoutManager;
    int selectedSortIndex;

    public AddRecipeToMPFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_add_recipe_meal_plan_16, container, false);

//        To-do
        // Implement the spinner option to sort the ingredient list
//        Spinner spinner = (Spinner) rootView.findViewById(R.id.add_recipe_toMP_sort_spinner);
//        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(
//                getActivity(),
//                android.R.layout.simple_spinner_item,
////                MealPlan.getSortableProps()
//        );
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setSelection(0);
//        selectedSortIndex = 0;

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }
}
