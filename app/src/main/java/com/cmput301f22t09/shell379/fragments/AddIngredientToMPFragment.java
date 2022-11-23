package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.AddRecipeMealPlanAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;

public class AddIngredientToMPFragment extends Fragment {
    private Environment env;
    private NavController navController;
    private MealPlanViewModel mealPlanViewModel;
    AddRecipeMealPlanAdapter addRecipeMealPlanAdapter;
    ArrayList<MealPlanWrapper<Ingredient>> IngredinMealplan;

    public AddIngredientToMPFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_add_ingredient_meal_plan_18, container, false);

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        //        To-do sorting

        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }


}
