package com.cmput301f22t09.shell379.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 *  Fragment for choosing the day you will eat an ingredient and how much of it.
 */
public class IngredMealPlanPickDateFragment extends Fragment{
    protected View rootView;
    protected NavController navController;
    protected MealPlanViewModel mealPlanViewModel;
    private int ingredIdx;
    private Ingredient newIngredient;
    private TextView ingredNameText;

    public IngredMealPlanPickDateFragment() {
        // required empty constructor
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

        ingredNameText = (TextView) rootView.findViewById(R.id.Ingredient_name);
        ingredNameText.setText(newIngredient.getDescription());
        
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
        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }

    /**
     *  Saves to view model
     * @param ingredient
     */
    protected void writeToViewModel(MealPlanWrapper<Ingredient> ingredient) {
        mealPlanViewModel.addIngredient(ingredient);
    }

    /**
     * Toasts user with error message
     * @param message
     */
    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * This method save the date and serving enter by users
     * and navigate back to the edit meal plan page
     */
    private void save(){
        String serving = ((EditText) rootView.findViewById(R.id.serving_edittext)).getText().toString();

        if(serving.isEmpty() || serving == null){
            showError("Please fill all fields");
            return;
        }


        DatePicker IngredDatePicker = rootView.findViewById(R.id.editIngredientDate);
        Date ingredDate = new GregorianCalendar(
                IngredDatePicker.getYear(),
                IngredDatePicker.getMonth(),
                IngredDatePicker.getDayOfMonth()).getTime();

        // check if the date user picked is less than today
        Calendar todayDate = Calendar.getInstance();
        todayDate.set(Calendar.MILLISECOND, 0);
        todayDate.set(Calendar.SECOND, 0);
        todayDate.set(Calendar.MINUTE, 0);
        todayDate.set(Calendar.HOUR_OF_DAY, 0);
        Date today = todayDate.getTime();
        if (ingredDate.compareTo(today)<0){
            showError("Please choose a date greater than or equal to today");
            return;
        }

        MealPlanWrapper<Ingredient> WrapperIngredient = new MealPlanWrapper<Ingredient>(newIngredient,ingredDate,Integer.parseInt(serving));

        writeToViewModel(WrapperIngredient);
        navController.popBackStack();
        navController.popBackStack();
    }
}