package com.cmput301f22t09.shell379.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.MealPlanAdapter;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class RecipeMealPlanPickDateFragment extends Fragment{
    protected View rootView;
    protected NavController navController;
    protected MealPlanViewModel mealPlanViewModel;
    private int recipeIndex;
    private Recipe newRecipe;
    private TextView servingText;
    private TextView recipeNameText;

    public RecipeMealPlanPickDateFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        mealPlanViewModel = new ViewModelProvider(requireActivity()).get(MealPlanViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.mealplan_recipe_date_17, container, false);

        recipeIndex = getArguments().getInt("index");
        newRecipe = Environment.of((AppCompatActivity) getActivity())
                .getRecipes().getList().get(recipeIndex);
        recipeNameText =(TextView) rootView.findViewById(R.id.recipe_name);
        recipeNameText.setText(newRecipe.getTitle());
        servingText = (TextView) rootView.findViewById(R.id.mpar_servings_val);
        servingText.setText(newRecipe.getServings().toString());


        // back button to go back
        ((ImageView)rootView.findViewById(R.id.back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );
        // cancel button to go back
        ((Button)rootView.findViewById(R.id.cancel_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        ((Button)rootView.findViewById(R.id.save_button)).setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(View v) {
                        save();
                    }
                }
        );
        Button subServingButton = rootView.findViewById(R.id.mpar_sub_btn);
        Button addServingButton = rootView.findViewById(R.id.mpar_add_btn);
        subServingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer servings = Integer.parseInt((servingText).getText().toString());
                if(servings/newRecipe.getServings() >= 1){
                    servingText.setText(Integer.toString(newRecipe.getServings()*((servings/newRecipe.getServings())-1)));
                }
            }
        });
        addServingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer servings = Integer.parseInt((servingText).getText().toString());
                servingText.setText(Integer.toString(newRecipe.getServings()*((servings/newRecipe.getServings())+1)));
            }
        });
        // date extraction from https://stackoverflow.com/questions/9474121/i-want-to-get-year-month-day-etc-from-java-date-to-compare-with-gregorian-cal


        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }



    protected void writeToViewModel(MealPlanWrapper<Recipe> recipe) {
//        envViewModel.getIngredients().getList().set(ingredientIndex, ing);
//        envViewModel.getIngredients().commit();
        mealPlanViewModel.addRecipe(recipe);
    }

    private void save(){
//        we dont have these 2 in the UI screen 17; those 2 are edited/save in previous screen
        String obj = ((TextView) rootView.findViewById(R.id.recipe_name)).getText().toString();
        String serving = ((TextView) rootView.findViewById(R.id.mpar_servings_val)).getText().toString();



        DatePicker RecipeDatePicker = rootView.findViewById(R.id.editRecipeDate);
        Date recipeDate = new GregorianCalendar(
                RecipeDatePicker.getYear(),
                RecipeDatePicker.getMonth(),
                RecipeDatePicker.getDayOfMonth()).getTime();

        MealPlanWrapper<Recipe> WrapperRecipe
                = new MealPlanWrapper<Recipe>(newRecipe, recipeDate, Integer.parseInt(serving)/newRecipe.getServings());

        writeToViewModel(WrapperRecipe);
        navController.popBackStack();
        navController.popBackStack();

    }


}