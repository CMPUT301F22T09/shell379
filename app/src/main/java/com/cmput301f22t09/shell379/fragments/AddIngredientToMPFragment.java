package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.AddIngredMealPlanAdapter;
import com.cmput301f22t09.shell379.adapters.AddRecipeMealPlanAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;

public class AddIngredientToMPFragment extends DialogFragment implements AddIngredMealPlanAdapter.IngredInMealPlanListener {
    private Environment env;
    private NavController navController;
    private MealPlanViewModel mealPlanViewModel;
    AddIngredMealPlanAdapter addIngredMealPlanAdapter;
    ArrayList<MealPlanWrapper<Ingredient>> IngredinMealplan;

    public AddIngredientToMPFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mealPlanViewModel = new ViewModelProvider(requireActivity()).get(MealPlanViewModel.class);

        navController = NavHostFragment.findNavController(this);
        env = Environment.of((AppCompatActivity) requireActivity());
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

        RecyclerView ingredRecyclerView = (rootView.findViewById(R.id.add_ingredient_to_mealPlan_recyclerView));

        addIngredMealPlanAdapter = new AddIngredMealPlanAdapter(env.getIngredients().getList(), this, mealPlanViewModel);
        ingredRecyclerView.setAdapter(addIngredMealPlanAdapter);
        ingredRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //        To-do sorting

        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }


    @Override
    public void editIngredInMP(int index) {

    }
}
