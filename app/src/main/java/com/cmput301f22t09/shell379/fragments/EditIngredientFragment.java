package com.cmput301f22t09.shell379.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Unit;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
/**
 * Child class of SaveIngredientFragment that specializes in creating.
 */
public class EditIngredientFragment extends SaveIngredientFragment {
    private int ingredientIndex;
    private Ingredient ingredient;

    public EditIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        ingredientIndex = getArguments().getInt("ingredientIndex");
        ingredient = Environment.of((AppCompatActivity) getActivity())
                .getIngredients().getList().get(ingredientIndex);

        ((EditText)rootView.findViewById(R.id.editDescription)).setText(ingredient.getDescription());
        // date extraction from https://stackoverflow.com/questions/9474121/i-want-to-get-year-month-day-etc-from-java-date-to-compare-with-gregorian-cal
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(ingredient.getBestBefore());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        ((DatePicker)rootView.findViewById(R.id.editBestBeforeDate)).updateDate(
                year,
                month - 1,
                day);
        ((EditText)rootView.findViewById(R.id.editLocation)).setText(String.valueOf(ingredient.getLocation()));
        ((EditText)rootView.findViewById(R.id.editAmount)).setText(String.valueOf(ingredient.getAmount()));
        ((EditText)rootView.findViewById(R.id.editCategory)).setText(String.valueOf(ingredient.getCategory()));
        ArrayList<Unit> units = new ArrayList<Unit>(Arrays.asList(Unit.values()));
        int unitSelectionIndex = units.indexOf(Unit.getFromString(ingredient.getUnit()));
        ((Spinner)rootView.findViewById(R.id.editUnit)).setSelection(unitSelectionIndex);

        return rootView;
    }
    protected void writeToViewModel(Ingredient ing){
        envViewModel.getIngredients().getList().set(ingredientIndex,ing);
        envViewModel.getIngredients().commit();
    }

}