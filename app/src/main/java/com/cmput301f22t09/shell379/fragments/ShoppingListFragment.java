package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.ShoppingListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.ShoppingCart;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;

import java.util.ArrayList;
import java.util.Date;

public class ShoppingListFragment extends Fragment {

    // data variables
    private Environment env;
    private NavController navController;
    private ShoppingCart shoppingList;

    // UI variables
    private RecyclerView shoppingList_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ShoppingListAdapter shoppingListAdapter;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shopping_list_20, container, false);

        env = Environment.of((AppCompatActivity) requireActivity());
        // shoppingList = env.getCart();

        // TEMPORARY TESTING DATA
        shoppingList = new ShoppingCart();
        ArrayList<CartIngredient> tempList = new ArrayList<>();
        tempList.add(new CartIngredient( new Ingredient("Milk", new Date(), "pantry", 10, "mL", "Dairy")));
        tempList.add(new CartIngredient( new Ingredient("Milk2", new Date(), "pantry", 10, "mL", "Dairy")));
        tempList.add(new CartIngredient( new Ingredient("Milk3", new Date(), "pantry", 10, "mL", "Dairy")));
        tempList.add(new CartIngredient( new Ingredient("Milk4", new Date(), "pantry", 10, "mL", "Dairy")));
        shoppingList.setList(tempList);
        // END OF TEMPORARY TESTING DATA

        // Set up recycler view for displaying shopping list items
        layoutManager = new LinearLayoutManager(this.getActivity());
        shoppingList_recyclerView = rootView.findViewById(R.id.shopping_list_recyclerView);
        shoppingList_recyclerView.setLayoutManager(layoutManager);

        shoppingListAdapter = new ShoppingListAdapter(shoppingList, this);
        shoppingList_recyclerView.setAdapter(shoppingListAdapter);
        shoppingList_recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }
}