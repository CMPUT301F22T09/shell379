package com.cmput301f22t09.shell379.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.Unit;
import com.cmput301f22t09.shell379.data.vm.EditRecipeViewModel;
import com.cmput301f22t09.shell379.data.vm.Environment;

/**
 * abstract fragment for saving (creating and editing)  an ingredient stub.
 */
public abstract class SaveIngredientStubFragment extends DialogFragment{
    // Full screen dialog strategy from Anubhav Arora , Nov 11 2020
    // https://medium.com/geekculture/android-full-screen-dialogfragment-1410dbd96d37
    @Override
    public int getTheme() {
        return R.style.DialogTheme;
    }

    protected View rootView;
    protected NavController navController;
    protected Environment envViewModel;
    protected EditText category;
    protected EditText location;
    protected EditRecipeViewModel editRecipeViewModel;

    public SaveIngredientStubFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        editRecipeViewModel =  new ViewModelProvider(requireActivity()).get(EditRecipeViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_save_ingredient_stub, container, false);
        category = ((EditText)rootView.findViewById(R.id.editCategory));
        location = ((EditText)rootView.findViewById(R.id.editLocation));

        envViewModel = Environment.of((AppCompatActivity) requireActivity());

        // Populate spinner options.
        // Spinner template code from https://developer.android.com/develop/ui/views/components/spinner
        Spinner spinner = (Spinner) rootView.findViewById(R.id.editUnit);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                Unit.getAllValuesAsStrings()// NEEDS TO BE CHANGED
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
        category.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(View v) {
                        onIngCategoryClick();
                    }
                }
        );

        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }

    /**
     *  Pulls inputs from view objects and saves according to
     *  the abstract method writeToViewModel(Ingredient).
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void save(){

            // Load data from Views
            String description = ((EditText)rootView.findViewById(R.id.editDescription)).getText().toString();
            String category = ((EditText)rootView.findViewById(R.id.editCategory)).getText().toString();
            String unit = ((Spinner)rootView.findViewById(R.id.editUnit)).getSelectedItem().toString();

            // validate
            if(description.isEmpty() ||
                category == null ||
                category.isEmpty() ||
                unit.isEmpty()
            ){
                showError("All fields not filled");
                return;
            }
            int amount = 0;
            if(((EditText)rootView.findViewById(R.id.editAmount)).getText().toString().isEmpty()){
                showError("Amount not filled");
                return;
            }else{
                amount = Integer.parseInt(((EditText)rootView.findViewById(R.id.editAmount)).getText().toString());
            }


            IngredientStub ing =  new IngredientStub(description,amount,unit,category);

            writeToViewModel(ing);
    }

    /**
     * snack bar message
     * @param message error message to show
     */
    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     *  Callback function for category component.
     *  Will update the associated view with the selected category.
     */
    private void onIngCategoryClick(){
        CategorySelectPopup.SelectListener listener = new CategorySelectPopup.SelectListener() {
            @Override
            public void send(String val) {
                category.setText(val);
            }
        };
        IngredientCategorySelectPopup selection = new IngredientCategorySelectPopup(listener,"Category");
        selection.show(getFragmentManager(), "");
        selection.setTargetFragment(SaveIngredientStubFragment.this, 1);
    }

    /**
     * dictates what happens when the fragment saves the ingredient. All writing to outside
     * classes should be done in this method for child classes.
     * @param ing the ingredient stub to write.
     */
    public abstract void writeToViewModel(IngredientStub ing);

}