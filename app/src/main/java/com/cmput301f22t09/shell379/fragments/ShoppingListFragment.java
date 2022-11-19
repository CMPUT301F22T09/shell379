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
        CartIngredient testCartIngredient = new CartIngredient("Milk", "Dairy", 2, "L");
        testCartIngredient.setIngredient(new Ingredient("Milk","Fridge", 2, "L", "Dairy"));
        tempList.add(testCartIngredient);
//        tempList.add(new CartIngredient());
//        tempList.add(new CartIngredient());
//        tempList.add(new CartIngredient());
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