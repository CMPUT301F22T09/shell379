package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.ShoppingListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;

import com.cmput301f22t09.shell379.data.MealPlan;
//import com.cmput301f22t09.shell379.data.ShoppingCart;
import com.cmput301f22t09.shell379.data.util.ArraySortUtil;
import com.cmput301f22t09.shell379.data.util.IngredientDiffUtil;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Fragment to display the ingredients required by your current meal plans that you do
 * not have in your storage.
 */
public class ShoppingListFragment extends Fragment {

    // data variables
    private Environment env;
    private NavController navController;
    private LiveCollection<CartIngredient> shoppingList;

    // UI variables
    private RecyclerView shoppingList_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ShoppingListAdapter shoppingListAdapter;
    private FloatingActionButton backButton;
    private Button submitButton;
    private int selectedSortIndex;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        env = Environment.of((AppCompatActivity) requireActivity());
        navController = NavHostFragment.findNavController(this);
        IngredientDiffUtil.prepareCart(env);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shopping_list_20, container, false);

        backButton = rootView.findViewById(R.id.back);
        submitButton = rootView.findViewById(R.id.submit_button);
        shoppingList = env.getCart();

        // Implement the spinner option to sort the ingredient list
        Spinner spinner = (Spinner) rootView.findViewById(R.id.shopping_list_sort_spinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                CartIngredient.getSortableProps()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        selectedSortIndex = 0;

        // setting spinner events from khaled ben aissa, dec 21 2011
        // https://stackoverflow.com/questions/8597582/get-the-position-of-a-spinner-in-android
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // resorts the recycler view of ingredients
                selectedSortIndex = ((Spinner) rootView.findViewById(R.id.shopping_list_sort_spinner)).getSelectedItemPosition();
                shoppingListAdapter.updateShoppingList(
                        ArraySortUtil.sortByStringProp(shoppingListAdapter.getShoppingList(),
                                CartIngredient.getStringPropGetter(selectedSortIndex))
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        // Set up recycler view for displaying shopping list items
        layoutManager = new LinearLayoutManager(this.getActivity());
        shoppingList_recyclerView = rootView.findViewById(R.id.shopping_list_recyclerView);
        shoppingList_recyclerView.setLayoutManager(layoutManager);

        shoppingListAdapter = new ShoppingListAdapter(shoppingList, this);
        shoppingList_recyclerView.setAdapter(shoppingListAdapter);
        shoppingList_recyclerView.setItemAnimator(new DefaultItemAnimator());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToMainMenuFragment());
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        return rootView;
    }

    /**
     * Affect the environment and reflect the picked up ingredients
     */
    private void submit() {
        ArrayList<CartIngredient> shoppingArray = shoppingList.getList();
        ArrayList<Integer> toBeRemoved = new ArrayList<>();
        for (int i = 0; i < shoppingArray.size(); i++) {
            CartIngredient neededIngredient = shoppingArray.get(i);
            if (neededIngredient.getPickedUp() && neededIngredient.getDetailsFilled()) {
                int neededAmount = neededIngredient.getAmount();
                int amount = neededIngredient.getIngredient().getAmount();

                // if amount is less than needed, keep the cartIngredient in the shopping list but
                // change the amount
                if (neededAmount > amount) {
                    neededIngredient.setAmount(neededAmount-amount);
                    neededIngredient.setPickedUp(false);
                    neededIngredient.setIngredient(null);
                    neededIngredient.setDetailsFilled(false);
                } else {
                    toBeRemoved.add(i);
                }
                env.getIngredients().add(neededIngredient.getIngredient());
            }
        }

        for (int i = 0; i < toBeRemoved.size(); i++) {
            shoppingArray.remove(toBeRemoved.get(i).intValue());
        }
        env.getCart().setList(shoppingArray);
        
        env.getIngredients().commit();
        env.getCart().commit();
        navController.navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToShoppingListSuccessFragment());
    }
}