package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.mealplan.edit.MPIngredientsEditListAdapter;
import com.cmput301f22t09.shell379.adapters.mealplan.edit.MPRecipesEditListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;

import java.util.ArrayList;

public class EditMealPlanFragment extends Fragment {
    private ArrayList<Ingredient> ingredientsList;
    private ArrayList<Recipe> recipesList;
    private RecyclerView mpIngredientRecycler;
    private RecyclerView mpRecipeRecycler;
    private MPIngredientsEditListAdapter ingredientsAdapter;
    private MPRecipesEditListAdapter recipesAdapter;
    private Environment envViewModel;
    private NavController navController;
    private MealPlanViewModel mpViewModel;
//    private FloatingActionButton backButton;


    public EditMealPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        envViewModel = Environment.of((AppCompatActivity) requireActivity());
        mpViewModel = MealPlanViewModel.of(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_edit_meal_plan_15, container, false);

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.mpe_back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

//        layoutManager = new LinearLayoutManager(this.getActivity());

        mpIngredientRecycler = (RecyclerView) rootView.findViewById(R.id.plan_edit_ingredients);
        mpRecipeRecycler = (RecyclerView) rootView.findViewById(R.id.plan_edit_recipes);

        ingredientsAdapter = new MPIngredientsEditListAdapter(mpViewModel);
        mpIngredientRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mpIngredientRecycler.setAdapter(ingredientsAdapter);
        mpIngredientRecycler.setItemAnimator(new DefaultItemAnimator());

        recipesAdapter = new MPRecipesEditListAdapter(mpViewModel);
        mpRecipeRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mpRecipeRecycler.setAdapter(recipesAdapter);
        mpRecipeRecycler.setItemAnimator(new DefaultItemAnimator());

        Log.e("MP_ADAPTER", ingredientsAdapter.getIngredients().toString());
        return rootView;
    }

    /**
     * Navigate the screen back to the previous one
     */
    private void back(){
        navController.popBackStack();
    }

    public void navigateToViewMealPlan(int index){
        //    to-do
//        MealPlanListFragmentDirections.

//        IngredientListFragmentDirections.ActionIngredientListFragmentToViewIngredientFragment action
//                = IngredientListFragmentDirections.actionIngredientListFragmentToViewIngredientFragment(index);
//        navController.navigate(action);
    }
}
