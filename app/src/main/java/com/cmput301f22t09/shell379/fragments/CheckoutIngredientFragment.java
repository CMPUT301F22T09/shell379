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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Unit;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

public class CheckoutIngredientFragment extends Fragment {
    protected View rootView;
    private NavController navController;
    protected Environment envViewModel;
    private TextView category;
    private EditText location;
    private int ingredientIndex;
    private TextView description;
    private CartIngredient theCartIngredient;
    private EditText amount;
    private TextView unit;

    public CheckoutIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        envViewModel = Environment.of((AppCompatActivity) requireActivity());
        ingredientIndex = getArguments().getInt("ingredientIndex");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_shopping_list_checkout_22, container, false);
        category = ((TextView) rootView.findViewById(R.id.editCategory));
        location = ((EditText) rootView.findViewById(R.id.editLocation));
        description = ((TextView) rootView.findViewById(R.id.editDescription));
        amount = ((EditText) rootView.findViewById(R.id.editAmount));
        unit = ((TextView) rootView.findViewById(R.id.editUnit));

        theCartIngredient = envViewModel.getCart().getList().get(ingredientIndex);
        description.setText(theCartIngredient.getDescription());
        category.setText(theCartIngredient.getCategory());
        amount.setText(theCartIngredient.getAmount().toString());
        unit.setText(theCartIngredient.getUnit());

        ((ImageView) rootView.findViewById(R.id.back)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        ((Button) rootView.findViewById(R.id.save_button)).setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(View v) {
                        save();
                    }
                }
        );

        // Implement the onclick location to enter the text
        location.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(View v) {
                        onLocationCategoryClick();
                    }
                }
        );

        return rootView;
    }

    private void back(){
        navController.navigate(CheckoutIngredientFragmentDirections.actionCheckoutIngredientToShoppingListFragment());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void save(){
        try {

            // Load data from Views
            String description = ((TextView) rootView.findViewById(R.id.editDescription)).getText().toString();
            String location = ((EditText) rootView.findViewById(R.id.editLocation)).getText().toString();
            String strAmount = ((EditText) rootView.findViewById(R.id.editAmount)).getText().toString();
            String category = ((TextView) rootView.findViewById(R.id.editCategory)).getText().toString();
            String unit = ((TextView) rootView.findViewById(R.id.editUnit)).toString();

            // validate
            if (description.isEmpty() ||
                    location.isEmpty() ||
                    category.isEmpty() ||
                    unit.isEmpty() ||
                    strAmount.isEmpty()
            ) {
                showError("All fields need to be filled");
                return;
            }

            int amount = 0;
            if (strAmount.isEmpty()) {
                showError("Amount not filled");
                return;
            } else {
                amount = Integer.parseInt(strAmount);
            }

            DatePicker bestBeforeDatePicker = rootView.findViewById(R.id.editBestBeforeDate);
            Date bestBeforeDate = new GregorianCalendar(
                    bestBeforeDatePicker.getYear(),
                    bestBeforeDatePicker.getMonth(),
                    bestBeforeDatePicker.getDayOfMonth()).getTime();

            Ingredient newIng = new Ingredient(description, bestBeforeDate, location, amount, unit, category);

            writeToViewModel(newIng);
            navController.popBackStack();
        } catch(Exception e){
            showError(e.getMessage());
            return;
        }
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    /**
     * Implement the ingredient location dialog
     */
    private void onLocationCategoryClick(){
        CategorySelectPopup.SelectListener listener = new CategorySelectPopup.SelectListener() {
            @Override
            public void send(String val) {
                location.setText(val);
            }
        };
        LocationCategorySelectPopup selection = new LocationCategorySelectPopup(listener, "Location");
        selection.show(getFragmentManager(), "");
        selection.setTargetFragment(CheckoutIngredientFragment.this, 1);
    }

    /**
     * Pass in the data into view model
     * @param ing
     */
    protected void writeToViewModel(Ingredient ing){
        theCartIngredient.setIngredient(ing);
        envViewModel.getCart().getList().set(ingredientIndex, theCartIngredient);
        envViewModel.getCart().commit();
    }
}

