package com.cmput301f22t09.shell379.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.IngredientAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.ArrayList;
import java.util.Date;
//import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientListFragment extends Fragment implements IngredientAdapter.AdaptorListener{
    ArrayList<Ingredient> ingredientList;
    RecyclerView ingredient_recyclerView;
    RecyclerView.LayoutManager layoutManager;
    IngredientAdapter ingredientListAdapter;



    private NavController navController;
    private Environment envViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public IngredientListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngredientListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientListFragment newInstance(String param1, String param2) {
        IngredientListFragment fragment = new IngredientListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        envViewModel = Environment.of((AppCompatActivity) requireActivity());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        final Observer<ArrayList<Ingredient>> ingredientObserver = new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Ingredient> ingredient) {
                // Update the UI, in this case, a TextView.
//                ingredient_recyclerView.setText(newName);
                if (ingredientListAdapter != null){
                    ingredientListAdapter.updateIngredient(ingredient);
                }
            }
        };
        envViewModel.getIngredients().getListLive().observe(this, ingredientObserver);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        ((Button)rootView.findViewById(R.id.new_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(IngredientListFragmentDirections.actionIngredientListFragmentToCreateIngredientFragment());
                    }
                }
        );





        ingredientList = new ArrayList<Ingredient>();
//        testList.add(new Ingredient("Milk", new Date(2023,9,10), "Fridge",222,"1L","Diary"));
//        testList.add(new Ingredient("Water", new Date(2023,9,11),"Counter",22,"2L","Liquid"));

        Ingredient i1 = new Ingredient("Milk", new Date(2023,9,10), "Fridge",222,"1L","Diary");

        layoutManager = new LinearLayoutManager(this.getActivity());
        ingredient_recyclerView = (RecyclerView) rootView.findViewById(R.id.ingredient_list_recyclerView);
        ingredient_recyclerView.setLayoutManager(layoutManager);


        ingredientListAdapter = new IngredientAdapter(ingredientList, envViewModel,this);
        ingredient_recyclerView.setAdapter(ingredientListAdapter);
        ingredient_recyclerView.setItemAnimator(new DefaultItemAnimator());

        envViewModel.getIngredients().add(i1);
        envViewModel.getIngredients().commit();
//        Log.e("print",String.valueOf(ingredientList.size()));
        return rootView;


    }

    public void navigateToViewIngredient(int index){
        IngredientListFragmentDirections.ActionIngredientListFragmentToViewIngredientFragment action
                = IngredientListFragmentDirections.actionIngredientListFragmentToViewIngredientFragment(index);
//        action.setIndex(index);
        navController.navigate(action);

    }
}