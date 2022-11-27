package com.cmput301f22t09.shell379.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.util.DatabaseManager;

/**
 *  Home page fragment
 */
public class MainMenuFragment extends Fragment {
    private NavController navController;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_main_menu_1, container, false);

        DatabaseManager dbm = new DatabaseManager((AppCompatActivity) requireActivity());
        dbm.pull((AppCompatActivity) requireActivity());
        blockNavUntilDownloaded(dbm, rootView);

        // navigation snippet from https://developer.android.com/guide/navigation/navigation-navigate#groovy
        ((Button)rootView.findViewById(R.id.ingredients_list_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToIngredientListFragment());
                    }
                }
        );
        ((Button)rootView.findViewById(R.id.recipes_list_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToRecipeListFragment());
                    }
                }
        );
        ((Button)rootView.findViewById(R.id.meal_plans_list_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToMealPlanListFragment());
                    }
                }
        );
        ((Button)rootView.findViewById(R.id.shopping_list_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToShoppingListFragment());
                    }
                }
        );
        return rootView;
    }

    /**
     * Fetches data base data and prevents actions until the data is loaded.
     * @param dbm database to fetch from
     * @param rootView the main menu view
     */
    private void blockNavUntilDownloaded(DatabaseManager dbm, View rootView) {
        TextView loadingTxt = (TextView) rootView.findViewById(R.id.loadingText);
        loadingTxt.setVisibility(View.VISIBLE);

        Button recipesLstBtn = (Button)rootView.findViewById(R.id.recipes_list_button);
        Button mealPlanLstBtn = (Button)rootView.findViewById(R.id.meal_plans_list_button);
        Button shoppingCartBtn = (Button)rootView.findViewById(R.id.shopping_list_button);
        Button ingrLstBtn = (Button)rootView.findViewById(R.id.ingredients_list_button);

        recipesLstBtn.setVisibility(View.GONE);
        mealPlanLstBtn.setVisibility(View.GONE);
        shoppingCartBtn.setVisibility(View.GONE);
        ingrLstBtn.setVisibility(View.GONE);
        Log.d("MAIN_MENU", String.valueOf(dbm.getLoaded().getValue()));

        dbm.getLoaded().observe(requireActivity(), new Observer<Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(Boolean loaded) {
                if (loaded==true) {
                    Log.d("MAIN_MENU", String.valueOf(loaded));
                    loadingTxt.setVisibility(View.GONE);
                    recipesLstBtn.setVisibility(View.VISIBLE);
                    mealPlanLstBtn.setVisibility(View.VISIBLE);
                    shoppingCartBtn.setVisibility(View.VISIBLE);
                    ingrLstBtn.setVisibility(View.VISIBLE);
                };
            }
        });
    }
}