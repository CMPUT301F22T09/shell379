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
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Unit;
import com.cmput301f22t09.shell379.data.vm.EditRecipeViewModel;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.ArrayList;

public class CreateIngredientStubFragment extends SaveIngredientStubFragment{
    public void writeToViewModel(Ingredient ing){
        envViewModel.getIngredients().add(ing);
        envViewModel.getIngredients().commit();

        editRecipeViewModel.getSelectedIngredients().add(ing);
        editRecipeViewModel.forceSignalUpdate();
    };

}