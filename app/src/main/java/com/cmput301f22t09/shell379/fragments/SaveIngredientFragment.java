package com.cmput301f22t09.shell379.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaveIngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public abstract class SaveIngredientFragment extends Fragment {
    protected View rootView;
    private NavController navController;
    private Ingredient ingredient;
    protected Environment envViewModel;

    public SaveIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    // TO DO
    // Connect units, category and location to sets
    // connect dialog

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_save_ingredient, container, false);

        envViewModel = Environment.of((AppCompatActivity) requireActivity());

        // Populate spinner options.
        // Spinner template code from https://developer.android.com/develop/ui/views/components/spinner
        Spinner spinner = (Spinner) rootView.findViewById(R.id.editUnit);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                Arrays.asList("testUnit","testUnit") // NEEDS TO BE CHANGED
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ((ImageView)rootView.findViewById(R.id.back)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );
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
//        ((EditText)rootView.findViewById(R.id.editLocation)).setOnClickListener(
//                new View.OnClickListener() {
//
//                    public void onClick(View v) {
//
//                    }
//                }
//        );
        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void save(){
        try{
            // Load data from Views
            String description = ((EditText)rootView.findViewById(R.id.editDescription)).getText().toString();
            DatePicker bestBeforeDatePicker = rootView.findViewById(R.id.editBestBeforeDate);
            Date bestBeforeDate = new GregorianCalendar(
                    bestBeforeDatePicker.getYear(),
                    bestBeforeDatePicker.getMonth() + 1,
                    bestBeforeDatePicker.getDayOfMonth()).getTime();
            String location = ((EditText)rootView.findViewById(R.id.editLocation)).getText().toString();
            int amount = Integer.parseInt(((EditText)rootView.findViewById(R.id.editAmount)).getText().toString());
            String category = ((EditText)rootView.findViewById(R.id.editCategory)).getText().toString();
            String unit = ((Spinner)rootView.findViewById(R.id.editUnit)).getSelectedItem().toString();

            // validate
            writeToViewModel(new Ingredient(description,bestBeforeDate,location,amount,unit,category));
            navController.popBackStack();

        }catch (Exception e){
            showError();
        }
    }

    private void showError(){
        TextView error = rootView.findViewById(R.id.errorText);
        error.setVisibility(View.VISIBLE);
    }

  protected abstract void writeToViewModel(Ingredient ing);

}