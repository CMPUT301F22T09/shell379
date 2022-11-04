package com.cmput301f22t09.shell379.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.IngredientInRecipeAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class EditRecipeFragment extends Fragment {

    protected View rootView;
    private Recipe myRecipe;
    private RecyclerView recipe_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private IngredientInRecipeAdapter ingredientListAdapter;
    private Button choosePhoto;
    private Button catSelect;
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
    private LinearLayout tableText;
    private int recipeIndex;
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

        catSelect = (Button) rootView.findViewById(R.id.select_category);
        catSelect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                CategorySelectPopup.SelectListener listener = new CategorySelectPopup.SelectListener() {
                    @Override
                    public void send(String val) {
                        Log.e("EditRecipe", val);
                        catSelect.setAllCaps(false);
                        catSelect.setText(val);
                        catSelect.setGravity(Gravity.LEFT);
                        catSelect.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                        catSelect.setTypeface(Typeface.SANS_SERIF);
                    }
                };
                RecipeCategorySelectPopup selection = new RecipeCategorySelectPopup(listener,"Category");
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
        tableText = rootView.findViewById(R.id.table_text);

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                onSaveRecipeClicked();
            }
        });

        myRecipe = new Recipe("kongpaochicken",100L,3,"chinese","spicy");
//        myRecipe.addIngredient(new Ingredient("appleesdadadsdawdwadsaszdazawdas",new Date(2023,9,07),"fridge",2,"1lbs","fruit"));
//        myRecipe.addIngredient(new Ingredient("chicken",new Date(2023,9,07),"fridge",2,"1lbs","meat"));
//        myRecipe.addIngredient(new Ingredient("banana",new Date(2023,9,07),"fridge",2,"1lbs","fruit"));

        recipeIndex = getArguments().getInt("recipeIndex");
        if (recipeIndex > -1 && !env.getRecipes().getList().isEmpty()) {
            myRecipe = env.getRecipes().getList().get(recipeIndex);
//            previewPhoto = rootView.findViewById(R.id.photo);
            send(myRecipe.getCategory());
            prepareTimeText.setText(myRecipe.getPreparationTime().toString());
            servingsText.setText(myRecipe.getServings().toString());
            commentText.setText(myRecipe.getComments());
            nameText.setText(myRecipe.getTitle());
            ingredientListAdapter = new IngredientInRecipeAdapter(myRecipe.getIngredients(), this);
        } else {
            ingredientListAdapter = new IngredientInRecipeAdapter(new ArrayList<Ingredient>(), this);
        }

        layoutManager = new LinearLayoutManager(this.getActivity());
        recipe_recyclerView = (RecyclerView) rootView.findViewById(R.id.ingredientsInRep);
        recipe_recyclerView.setLayoutManager(layoutManager);

        if (myRecipe == null || myRecipe.getIngredients().isEmpty()) {
            tableText.setVisibility(View.INVISIBLE);
        }

        recipe_recyclerView.setAdapter(ingredientListAdapter);
        recipe_recyclerView.setItemAnimator(new DefaultItemAnimator());

        deleteIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = ingredientListAdapter.getSelectedPos();
                if (position > -1) {
                    deleteIngredient(position);
                }
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
        ingredientListAdapter.removeIngredient(pos);
        ingredientListAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSaveRecipeClicked() {

        try {
            String name = nameText.getText().toString();
            Long prepareTime = Long.parseLong(prepareTimeText.getText().toString());
            int servings = Integer.parseInt(servingsText.getText().toString());
            String category = cat;
            String comment = commentText.getText().toString();
            Bitmap photo = ((BitmapDrawable) previewPhoto.getDrawable()).getBitmap();
            Recipe newRecipe = new Recipe(name, prepareTime, servings, category, comment, photo);
            int size = ingredientListAdapter.getIngredients().size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    newRecipe.addIngredient(ingredientListAdapter.getIngredients().get(i));
                }
            }


            if (recipeIndex > -1 && !env.getRecipes().getList().isEmpty()) {
                saveEditedRecipe(newRecipe);
            } else {
                env.getRecipes().add(newRecipe);
            }

            env.getRecipes().commit();
            navController.navigate(EditRecipeFragmentDirections.actionEditRecipeToRecipeListFragment());
        } catch(Exception E) {
            showError();
        }
    }

    protected void saveEditedRecipe(Recipe r) {
        env.getRecipes().getList().set(recipeIndex,r);
    }

    public void deleteRecipeAction() {
        if (recipeIndex > -1 && !env.getRecipes().getList().isEmpty()) {
            Log.e("index", Integer.toString(recipeIndex));
            env.getRecipes().getList().remove(recipeIndex);
            env.getRecipes().commit();
        }
        navController.navigate(EditRecipeFragmentDirections.actionEditRecipeToRecipeListFragment());
    }

    public void send(String cat) {
        Log.e("EditRecipe", cat);
        catSelect.setAllCaps(false);
        catSelect.setText(cat);
        catSelect.setGravity(Gravity.LEFT);
        catSelect.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        catSelect.setTypeface(Typeface.SANS_SERIF);
        this.cat = cat;
    }

    private void showError(){
        TextView error = rootView.findViewById(R.id.errorText);
        error.setVisibility(View.VISIBLE);
    }
}