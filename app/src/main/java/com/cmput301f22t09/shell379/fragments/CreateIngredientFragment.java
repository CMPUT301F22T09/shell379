package com.cmput301f22t09.shell379.fragments;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Child class of SaveIngredientFragment that specializes in creating.
 */
public class CreateIngredientFragment extends SaveIngredientFragment {

    public CreateIngredientFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return rootView;
    }

    /**
     * 
     * @param ing
     */
    protected void writeToViewModel(Ingredient ing){
        envViewModel.getIngredients().add(ing);
        envViewModel.getIngredients().commit();
    }

}