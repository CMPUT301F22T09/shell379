package com.cmput301f22t09.shell379.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.net.Uri;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.IngredientInRecipeAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class EditRecipeFragment extends Fragment implements CategoriesSelect.CatSelectListener {

    protected View rootView;
    private Recipe myRecipe;
    private RecyclerView recipe_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private IngredientInRecipeAdapter recipeListAdapter;
    private Button choosePhoto;
    private ImageView previewPhoto;
    private Button saveRecipeButton;
    private Button deleteIngredientButton;
    private Button addIngredientButton;
    private Button deleteRecipeButton;
    private FloatingActionButton backButton;
    private EditText prepareTimeText;
    private EditText servingsText;
    private EditText commentText;
    private EditText nameText;
    private NavController navController;
    private int SELECT_PICTURE = 200;
    private String cat;
    Environment env;

    public EditRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.navController = NavHostFragment.findNavController(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_edit_recipe_9, container, false);

        Button catSelect = (Button) rootView.findViewById(R.id.select_category);
        catSelect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                CategoriesSelect selection = new CategoriesSelect();
                selection.show(getFragmentManager(), "");
                selection.setTargetFragment(EditRecipeFragment.this, 1);

            }
        });

        env = Environment.of((AppCompatActivity) requireActivity());
        choosePhoto = rootView.findViewById(R.id.choose_button);
        previewPhoto = rootView.findViewById(R.id.photo);
        saveRecipeButton = rootView.findViewById(R.id.save_recipe);
        addIngredientButton = rootView.findViewById(R.id.add_ingredient);
        deleteIngredientButton = rootView.findViewById(R.id.delete_ingredient);
        prepareTimeText = rootView.findViewById(R.id.prepare_text);
        servingsText = rootView.findViewById(R.id.serving_text);
        commentText = rootView.findViewById(R.id.comment_text);
        nameText = rootView.findViewById(R.id.recipe_name);
        deleteRecipeButton = rootView.findViewById(R.id.delete_recipe);
        backButton = rootView.findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(EditRecipeFragmentDirections.actionEditRecipeToRecipeListFragment());
            }
        });

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveRecipeClicked();
            }
        });

//        myRecipe = new Recipe("kongpaochicken",100L,3,"chinese","spicy");
//        myRecipe.addIngredient(new Ingredient("appleesdadadsdawdwadsaszdazawdas",new Date(2023,9,07),"fridge",2,"1lbs","fruit"));
//        myRecipe.addIngredient(new Ingredient("chicken",new Date(2023,9,07),"fridge",2,"1lbs","meat"));
//        myRecipe.addIngredient(new Ingredient("banana",new Date(2023,9,07),"fridge",2,"1lbs","fruit"));

        layoutManager = new LinearLayoutManager(this.getActivity());
        recipe_recyclerView = (RecyclerView) rootView.findViewById(R.id.ingredientsInRep);
        recipe_recyclerView.setLayoutManager(layoutManager);

        recipeListAdapter = new IngredientInRecipeAdapter(myRecipe.getIngredients());
        recipe_recyclerView.setAdapter(recipeListAdapter);
        recipe_recyclerView.setItemAnimator(new DefaultItemAnimator());

        deleteIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteIngredient(recipeListAdapter.getSelectedPos());
            }
        });

        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(EditRecipeFragmentDirections.actionEditRecipeToRecipeSelectIngredientFragment());
            }
        });

        deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecipeAction();
            }
        });

        return rootView;
    }

    // this function is triggered when
    // the Select Image Button is clicked
    public void imageChooser() {
        // SOURCE: https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/
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

    public void deleteIngredient(int pos) {
        recipeListAdapter.removeIngredient(pos);
        recipeListAdapter.notifyDataSetChanged();
    }

    public void onSaveRecipeClicked() {
        try {
            String name = nameText.getText().toString();
            Long prepareTime = Long.parseLong(prepareTimeText.getText().toString());
            int servings = Integer.parseInt(servingsText.getText().toString());
            String category = cat;
            String comment = commentText.getText().toString();
            Bitmap photo = ((BitmapDrawable) previewPhoto.getDrawable()).getBitmap();
            myRecipe = new Recipe(name, prepareTime, servings, category, comment, photo);
            int size = recipeListAdapter.getIngredients().size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    myRecipe.addIngredient(recipeListAdapter.getIngredients().get(i));
                }
            }
            env.getRecipes().add(myRecipe);
            env.getRecipes().commit();
            navController.navigate(EditRecipeFragmentDirections.actionEditRecipeToRecipeListFragment());
            // TODO: SAVE RECIPE TO GLOBAL RECIPE
        } catch(Exception E) {
            showError();
        }
//        for (int i = 0; i < size; i++) {
//            Log.e("debug", newRecipe.getIngredients().get(i).getDescription());
//        }
        // TODO: ADD TO THE LIST OF RECIPES
    }

    public void deleteRecipeAction() {
        navController.navigate(EditRecipeFragmentDirections.actionEditRecipeToRecipeListFragment());
        // TODO: implement fully equals 
    }

    @Override
    public void onAddClicked(String cat) {
        Log.e("EditRecipe", cat);
        this.cat = cat;
    }

    private void showError(){
        TextView error = rootView.findViewById(R.id.errorText);
        error.setVisibility(View.VISIBLE);
    }
}