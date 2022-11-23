package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.AddRecipeMealPlanAdapter;
import com.cmput301f22t09.shell379.adapters.RecipeIngredientsListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;

public class AddRecipeToMPFragment extends DialogFragment implements AddRecipeMealPlanAdapter.RecipeInMealPlanListener {
    private Environment env;
    private NavController navController;
    private MealPlanViewModel mealPlanViewModel;
    AddRecipeMealPlanAdapter addRecipeMealPlanAdapter;
    ArrayList<MealPlanWrapper<Recipe>> RecipeinMealPlan;
 //    RecyclerView addRecipeToMP_recyclerView;
//    RecyclerView.LayoutManager layoutManager;
//    int selectedSortIndex;


    public AddRecipeToMPFragment(){

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
        View rootView =  inflater.inflate(R.layout.fragment_add_recipe_meal_plan_16, container, false);

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        RecyclerView recipeRecyclerView =(rootView.findViewById(R.id.add_recipe_to_mealPlan_recyclerView));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recipeRecyclerView.setLayoutManager(layoutManager);

        addRecipeMealPlanAdapter = new AddRecipeMealPlanAdapter(env.getRecipes().getList(), this, mealPlanViewModel);
        recipeRecyclerView.setAdapter(addRecipeMealPlanAdapter);
        recipeRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //        To-do sorting

        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }

    /**
     *  Moves to the edit recipe in meal plan fragment
     */
    public void editRecipeInMP(int index){
//        navController.navigate(AddRecipeToMPFragment);
    };
}
