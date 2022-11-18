package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class ViewMealPlanFragment extends Fragment {
    private RecyclerView mpIngredientRecycler;
    private RecyclerView mpRecipeRecycler;
    private MPIngredientsViewListAdapter ingredientsAdapter;
    private MPRecipesViewListAdapter recipesAdapter;
    private Environment envViewModel;
    private NavController navController;
    private MealPlanViewModel mpViewModel;
//    private FloatingActionButton backButton;


    public ViewMealPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        envViewModel = Environment.of((AppCompatActivity) requireActivity());
        mpViewModel = MealPlanViewModel.of(testMP(), requireActivity());
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

        mpIngredientRecycler = (RecyclerView) rootView.findViewById(R.id.plan_ingredients);
        mpRecipeRecycler = (RecyclerView) rootView.findViewById(R.id.plan_recipes);

        ingredientsAdapter = new MPIngredientsViewListAdapter(mpViewModel);
        mpIngredientRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mpIngredientRecycler.setAdapter(ingredientsAdapter);
        mpIngredientRecycler.setItemAnimator(new DefaultItemAnimator());

        recipesAdapter = new MPRecipesViewListAdapter(mpViewModel);
        mpRecipeRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mpRecipeRecycler.setAdapter(recipesAdapter);
        mpRecipeRecycler.setItemAnimator(new DefaultItemAnimator());

        Log.e("MP_ADAPTER", ingredientsAdapter.getIngredients().toString());
        navListeners(rootView);

        return rootView;
    }

    /**
     * Navigate the screen back to the previous one
     */
    private void back(){
        navController.popBackStack();
    }

    private void navListeners(View rootView){
        rootView.findViewById(R.id.edit_plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(ViewMealPlanFragmentDirections.actionMealPlanFragmentToViewMealPlanFragment());
            }
        });
    }

    private void fillFields(View rootView) {
//        rootView.findViewById(R.id.mpv_comments_data).set;
    }

    private MealPlan testMP() {
        ArrayList<Ingredient> ingList = new ArrayList<Ingredient>();
        Date date = new Date((new Date()).getTime()+1000000);
        ingList.add(new Ingredient("test1", date, "yes", 1, "kg", "yes"));
        ingList.add(new Ingredient("test2", date, "yes", 1, "kg", "yes"));
        ingList.add(new Ingredient("test3", date, "yes", 1, "kg", "yes"));
        ingList.add(new Ingredient("test4", date, "yes", 1, "kg", "yes"));
        ingList.add(new Ingredient("test5", date, "yes", 1, "kg", "yes"));
        ingList.add(new Ingredient("test6", date, "yes", 1, "kg", "yes"));

        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
        recipeList.add(new Recipe("test1", 100L, 1, "yes", "yes"));
        recipeList.add(new Recipe("test2", 100L, 1, "yes", "yes"));
        recipeList.add(new Recipe("test3", 100L, 1, "yes", "yes"));
        recipeList.add(new Recipe("test4", 100L, 1, "yes", "yes"));
        recipeList.add(new Recipe("test5", 100L, 1, "yes", "yes"));
        recipeList.add(new Recipe("test6", 100L, 1, "yes", "yes"));

        return new MealPlan("testPlan", recipeList, ingList, date, date, 10);
    }
}
