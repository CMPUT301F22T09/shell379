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

public class CreateIngredientStubFragment extends Fragment{
    protected View rootView;
    private NavController navController;
    protected Environment envViewModel;
    private EditText category;
    private EditText location;
    private int recipeIndex;
    private ArrayList<Ingredient> selectedIngredients;

    public CreateIngredientStubFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        recipeIndex = getArguments().getInt("recipeIndex");
        Bundle temp = getArguments().getParcelable("existingSelections");
        if (temp != null) {
            selectedIngredients = (ArrayList<Ingredient>) CreateIngredientStubFragmentArgs.fromBundle(getArguments()).getExistingSelections().get("selectedIngredients");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_create_ingredient_stub, container, false);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void save(){
        try{
            // Load data from Views
            String description = ((EditText)rootView.findViewById(R.id.editDescription)).getText().toString();
            int amount = Integer.parseInt(((EditText)rootView.findViewById(R.id.editAmount)).getText().toString());
            String category = ((EditText)rootView.findViewById(R.id.editCategory)).getText().toString();
            String unit = ((Spinner)rootView.findViewById(R.id.editUnit)).getSelectedItem().toString();

            if(description.isEmpty() ||
                    category.isEmpty()
            ){
                throw new IllegalArgumentException("All fields not filled");
            }

            Ingredient ing =  new Ingredient(description,null,null,amount,unit,category);
            selectedIngredients.add(ing);
            envViewModel.getIngredients().add(ing);
            envViewModel.getIngredients().commit();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedIngredients", selectedIngredients);
            CreateIngredientStubFragmentDirections.ActionCreateIngredientStubFragment3ToEditRecipe action =
                CreateIngredientStubFragmentDirections.actionCreateIngredientStubFragment3ToEditRecipe (recipeIndex);
            action.setSelectedIngredients(bundle);
            navController.navigate(action);


        }catch (Exception e){
            showError(e);
        }
    }

    private void showError(Exception e){
        TextView error = rootView.findViewById(R.id.errorText);
        error.setText(e.getMessage());
        error.setVisibility(View.VISIBLE);
    }

    private void onIngCategoryClick(){
        CategorySelectPopup.SelectListener listener = new CategorySelectPopup.SelectListener() {
            @Override
            public void send(String val) {
                category.setText(val);
            }
        };
        IngredientCategorySelectPopup selection = new IngredientCategorySelectPopup(listener,"Category");
        selection.show(getFragmentManager(), "");
        selection.setTargetFragment(CreateIngredientStubFragment.this, 1);
    }

    public void writeToViewModel(Ingredient ing){
        envViewModel.getIngredients().add(ing);
        envViewModel.getIngredients().commit();
    };

}