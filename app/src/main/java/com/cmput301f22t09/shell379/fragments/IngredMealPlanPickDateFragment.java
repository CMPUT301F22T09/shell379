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
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

//public class IngredMealPlanPickDateFragment {


public class IngredMealPlanPickDateFragment extends Fragment{
    protected View rootView;
    protected NavController navController;
    protected MealPlanViewModel mealPlanViewModel;
    private MealPlanWrapper<Ingredient> ingredient;
    private int ingredIdx;
    private Ingredient newIngredient;

    public IngredMealPlanPickDateFragment() {

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
        rootView = inflater.inflate(R.layout.mealplan_ingredient_date_19, container, false);


        ingredIdx = getArguments().getInt("index");
        newIngredient = Environment.of((AppCompatActivity) getActivity())
                .getIngredients().getList().get(ingredIdx);

        ((TextView) rootView.findViewById(R.id.Ingredient_name))
                .setText(newIngredient.getDescription());
        
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
        // date extraction from https://stackoverflow.com/questions/9474121/i-want-to-get-year-month-day-etc-from-java-date-to-compare-with-gregorian-cal


        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }



    protected void writeToViewModel(MealPlanWrapper<Ingredient> ingredient) {
        mealPlanViewModel.addIngredient(ingredient);
    }

    private void save(){
//        we dont have these 2 in the UI screen 17; those 2 are edited/save in previous screen
        String obj = ((TextView) rootView.findViewById(R.id.Ingredient_name)).getText().toString();
        String serving = ((EditText) rootView.findViewById(R.id.serving_edittext)).getText().toString();

        DatePicker IngredDatePicker = rootView.findViewById(R.id.editIngredientDate);
        Date ingredDate = new GregorianCalendar(
                IngredDatePicker.getYear(),
                IngredDatePicker.getMonth(),
                IngredDatePicker.getDayOfMonth()).getTime();
//        MealPlanWrapper<Recipe>(ingredient1, recipeDate, serving1);
        MealPlanWrapper<Ingredient> WrapperIngredient = new MealPlanWrapper<Ingredient>(newIngredient,ingredDate,Integer.parseInt(serving));

        writeToViewModel(WrapperIngredient);
        navController.popBackStack();
        navController.popBackStack();


    }

}