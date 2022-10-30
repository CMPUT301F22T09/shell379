package com.cmput301f22t09.shell379.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.net.Uri;
import android.content.Context;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.IngredientAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Edit_recipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Edit_recipe extends Fragment implements CategoriesSelect.OnFragmentInteractionListener {

    Recipe myRecipe;
    RecyclerView recipe_recyclerView;
    RecyclerView.LayoutManager layoutManager;
    IngredientAdapter recipeListAdapter;
    Button choosePhoto;
    ImageView previewPhoto;
    int SELECT_PICTURE = 200;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Edit_recipe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Edit_recipe.
     */
    // TODO: Rename and change types and number of parameters
    public static Edit_recipe newInstance(String param1, String param2) {
        Edit_recipe fragment = new Edit_recipe();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_recipe_9, container, false);

        Button catSelect = (Button) rootView.findViewById(R.id.select_category);
        catSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                CategoriesSelect catselect = new CategoriesSelect();
                Fragment frag = requireActivity().getSupportFragmentManager().findFragmentById(R.id.select_category);
                catselect.setTargetFragment(frag, 1);
                catselect.show(getFragmentManager().beginTransaction(), "cat");
            }
        });

        choosePhoto = rootView.findViewById(R.id.chooseButton);
        previewPhoto = rootView.findViewById(R.id.photo);

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
        myRecipe = new Recipe("kongpaochicken",100L,3,"chinese","spicy");
        myRecipe.addIngredient(new Ingredient("apple",new Date(2023,9,07),"fridge",2,"1lbs","fruit"));
        myRecipe.addIngredient(new Ingredient("chicken",new Date(2023,9,07),"fridge",2,"1lbs","meat"));
        myRecipe.addIngredient(new Ingredient("banana",new Date(2023,9,07),"fridge",2,"1lbs","fruit"));


        layoutManager = new LinearLayoutManager(this.getActivity());
        recipe_recyclerView = (RecyclerView) rootView.findViewById(R.id.ingredientsInRep);
        recipe_recyclerView.setLayoutManager(layoutManager);

        recipeListAdapter = new IngredientAdapter(myRecipe.getIngredients());
        recipe_recyclerView.setAdapter(recipeListAdapter);
        recipe_recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    // this function is triggered when
    // the Select Image Button is clicked
    public void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    previewPhoto.setImageURI(selectedImageUri);
                }
            }
        }
    }


    @Override
    public void onOkPressed(String cat) {
        Log.e("EditRecipe", cat);
    }
}