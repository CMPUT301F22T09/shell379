package com.cmput301f22t09.shell379.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewIngredientFragment extends Fragment {
    private NavController navController;
    private Ingredient ingredient;
    private int ingredientIndex;

    public ViewIngredientFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ingredientIndex = getArguments().getInt("index");
        ingredient = Environment.of((AppCompatActivity) getActivity()).getIngredients().getList().get(ingredientIndex);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_ingredient, container, false);
        ((Button)rootView.findViewById(R.id.delete_ingredient_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                       deleteIngredient();
                    }
                }
        );
        ((Button)rootView.findViewById(R.id.edit_ingredient_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navigateToEditIngredient();
                    }
                }
        );
        ((ImageView)rootView.findViewById(R.id.back)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        // Show  brief description, best before date, location, amount, unit, and ingredient category in UI
        // date to string from  https://www.javatpoint.com/java-date-to-string
        ((TextView)rootView.findViewById(R.id.description)).setText( ingredient.getDescription());
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");


        ((TextView)rootView.findViewById(R.id.best_before_date).findViewById(R.id.detail_name)).setText( "Best Before");
        if(ingredient.getBestBefore() != null){
            ((TextView)rootView.findViewById(R.id.best_before_date).findViewById(R.id.detail_value)).setText( (ingredient.getBestBeforeFormatted()));
        }else{
            ((TextView)rootView.findViewById(R.id.best_before_date).findViewById(R.id.detail_value)).setText( "Tap edit below to add");
        }

        ((TextView)rootView.findViewById(R.id.location).findViewById(R.id.detail_name)).setText( "Location");
        if(ingredient.getLocation() != null){
            ((TextView)rootView.findViewById(R.id.location).findViewById(R.id.detail_value)).setText( ingredient.getLocation());
        }else{
            ((TextView)rootView.findViewById(R.id.location).findViewById(R.id.detail_value)).setText( "Tap edit below to add");
        }

        ((TextView)rootView.findViewById(R.id.amount).findViewById(R.id.detail_name)).setText( "Amount");
        if(ingredient.getAmount() != null && ingredient.getUnit() != null){
            ((TextView)rootView.findViewById(R.id.amount).findViewById(R.id.detail_value)).setText( ingredient.getAmount() + ingredient.getUnit() );
        }else{
            ((TextView)rootView.findViewById(R.id.amount).findViewById(R.id.detail_value)).setText( "Tap edit below to add");
        }

        ((TextView)rootView.findViewById(R.id.category).findViewById(R.id.detail_name)).setText( "Category");
        if(ingredient.getCategory() != null){
            ((TextView)rootView.findViewById(R.id.category).findViewById(R.id.detail_value)).setText( ingredient.getCategory());
        }else{
            ((TextView)rootView.findViewById(R.id.category).findViewById(R.id.detail_value)).setText( "Tap edit below to add");
        }

        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }

    private void navigateToEditIngredient(){
        navController.navigate(ViewIngredientFragmentDirections.actionViewIngredientFragmentToEditIngredientFragment(ingredientIndex));
    }

    private void deleteIngredient(){
        Environment.of((AppCompatActivity) getActivity()).getIngredients().removeAtIdx(ingredientIndex);
        Environment.of((AppCompatActivity) getActivity()).getIngredients().commit();
        back();
    }
}