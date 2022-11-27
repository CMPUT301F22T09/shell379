package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.mealplan.view.MPIngredientsViewListAdapter;
import com.cmput301f22t09.shell379.adapters.mealplan.view.MPRecipesViewListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.EditRecipeViewModel;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Fragment to view an already created meal plan's fields.
 */
public class ViewMealPlanFragment extends Fragment {
    private RecyclerView mpIngredientRecycler;
    private RecyclerView mpRecipeRecycler;
    private MPIngredientsViewListAdapter ingredientsAdapter;
    private MPRecipesViewListAdapter recipesAdapter;
    private Environment envViewModel;
    private NavController navController;
    private MealPlanViewModel mpViewModel;


    public ViewMealPlanFragment() {
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
        View rootView =  inflater.inflate(R.layout.fragment_view_meal_plan_14, container, false);

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.mpv_back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        // setup recycler views for viewing ingredients and recipes in meal plan
        mpIngredientRecycler = (RecyclerView) rootView.findViewById(R.id.plan_ingredients);
        ingredientsAdapter = new MPIngredientsViewListAdapter(mpViewModel);
        mpIngredientRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mpIngredientRecycler.setAdapter(ingredientsAdapter);
        mpIngredientRecycler.setItemAnimator(new DefaultItemAnimator());

        mpRecipeRecycler = (RecyclerView) rootView.findViewById(R.id.plan_recipes);
        recipesAdapter = new MPRecipesViewListAdapter(mpViewModel);
        mpRecipeRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mpRecipeRecycler.setAdapter(recipesAdapter);
        mpRecipeRecycler.setItemAnimator(new DefaultItemAnimator());

        navListeners(rootView);
        fillFields(rootView);

        return rootView;
    }

    /**
     * Navigate the screen back to the previous one
     */
    private void back(){
        navController.popBackStack();
    }

    /**
     * Setup navigation buttons
     * @param rootView view meal plan fragment root view.
     */
    private void navListeners(View rootView){
        rootView.findViewById(R.id.edit_plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(ViewMealPlanFragmentDirections.actionMealPlanFragmentToViewMealPlanFragment());
            }
        });
        rootView.findViewById(R.id.delete_plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                envViewModel.getMealPlans().removeAtIdx(mpViewModel.getIdx());
                navController.popBackStack();
            }
        });
    }

    /**
     *  Populate data into the layout from the passed meal plan
     * @param rootView view meal plan fragment root view.
     */
    private void fillFields(View rootView) {
        Log.d("FBK_DATE_END_VIEW", mpViewModel.getMealPlan().getEndDateFormatted());
        ((TextView) rootView.findViewById(R.id.mpv_comments_data))
                .setText(mpViewModel.getMealPlan().getComments());
        ((TextView) rootView.findViewById(R.id.mpv_start_data))
                .setText(mpViewModel.getMealPlan().getStartDateFormatted());
        ((TextView) rootView.findViewById(R.id.mpv_end_data))
                .setText(mpViewModel.getMealPlan().getEndDateFormatted());
        ((TextView) rootView.findViewById(R.id.plan_name))
                .setText(mpViewModel.getMealPlan().getMealPlanName());
    }
}
