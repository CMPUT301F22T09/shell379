package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *  Fragment to create and edit meal plan
 */
public class EditMealPlanFragment extends Fragment {
    private RecyclerView mpIngredientRecycler;
    private RecyclerView mpRecipeRecycler;
    private MPIngredientsEditListAdapter ingredientsAdapter;
    private MPRecipesEditListAdapter recipesAdapter;
    private Environment envViewModel;
    private NavController navController;
    private MealPlanViewModel mpViewModel;


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

        rootView.findViewById(R.id.plan_edit_add_recipes_btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        navController.navigate(EditMealPlanFragmentDirections.actionEditMealPlanFragmentToAddRecipetoMealPlanFragment());
                    }
                }
        );

        rootView.findViewById(R.id.plan_edit_add_ingr_btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        navController.navigate(EditMealPlanFragmentDirections.actionEditMealPlanFragmentToAddIngredtoMealPlanFragment());
                    }
                }
        );

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

        fillFields(rootView);

        rootView.findViewById(R.id.mpe_save_plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectData(rootView);
            }
        });

        rootView.findViewById(R.id.mpe_cancel_plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });
        return rootView;
    }

    /**
     * Navigate the screen back to the previous one
     */
    private void back(){
        navController.popBackStack();
    }

    /**
     *  populates data for the meal plan if updating
     * @param rootView root view
     */
    private void fillFields(View rootView) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(mpViewModel.getMealPlan().getStartDate());

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(mpViewModel.getMealPlan().getEndDate());

        ((TextView) rootView.findViewById(R.id.plan_edit_comment_txt))
                .setText(mpViewModel.getMealPlan().getComments());

        ((TextView) rootView.findViewById(R.id.plan_edit_name))
                .setText(mpViewModel.getMealPlan().getMealPlanName());
    }

    /**
     * Save data from inputs to environment and navigate back
     * @param rootView
     */
    private void collectData(View rootView) {
        if ( ((TextView) rootView.findViewById(R.id.plan_edit_comment_txt)).getText().toString().isEmpty()){
            showError("Comments not filled");
            return;
        }else if( ((TextView) rootView.findViewById(R.id.plan_edit_name)).getText().toString().isEmpty()){
            showError("Name not filled");
            return;
        }

        if (mpViewModel.getIngredients().size() <= 0 && mpViewModel.getRecipes().size() <= 0) {
            showError("Choose some recipes or ingredients!");
            return;
        }

        mpViewModel.getMealPlan().setMealPlanName(
                ((TextView) rootView.findViewById(R.id.plan_edit_name)).getText().toString()
        );
        mpViewModel.getMealPlan().setComments(
                ((TextView) rootView.findViewById(R.id.plan_edit_comment_txt)).getText().toString()
        );

        Calendar cal = Calendar.getInstance();
        DatePicker picker = rootView.findViewById(R.id.editPlanStart);
        cal.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
        mpViewModel.getMealPlan().setStartDate(cal.getTime());

        picker = rootView.findViewById(R.id.editPlanEnd);
        cal.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());

        Log.d("FBK_DATE_END", cal.getTime().toString());
        mpViewModel.getMealPlan().setEndDate(cal.getTime());
        Log.d("FBK_DATE_END", cal.getTime().toString());
        Log.d("FBK_DATE_END_MP", mpViewModel.getMealPlan().getEndDateFormatted());

        envViewModel.getMealPlans().setAtIdxOrAdd(mpViewModel.getIdx(), mpViewModel.getMealPlan());
        Log.d("FBK_DATE_END_END", mpViewModel.getMealPlan().getEndDateFormatted());
        envViewModel.getMealPlans().commit();

        navController.popBackStack();
    }
    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
