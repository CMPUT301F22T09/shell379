package com.cmput301f22t09.shell379.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.Toast;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.IngredientInRecipeAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.EditRecipeViewModel;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.PartiallyEquableLiveCollection;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * This class has all the functionalities for showing selected recipe information, creating a new
 * recipe, editing existing recipe, delete recipe. It also leads users to the next fragment to add
 * ingredients to the recipe.
 */
public class EditRecipeFragment extends Fragment {

    protected View rootView;
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
    private Button takePhotoButton;
    private FloatingActionButton backButton;
    private EditText prepareTimeText;
    private EditText servingsText;
    private EditText commentText;
    private EditText nameText;
    private NavController navController;
    private LinearLayout tableText;
    private int recipeIndex;
    private String cat;
    private Environment env;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private ArrayList<Ingredient> selectedIngredients;
    private static final int CAMERA_REQUEST = 1888;
    private static final int PICK_FROM_GALLERY = 1;
    private EditRecipeViewModel editRecipeViewModel;

    public EditRecipeFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.navController = NavHostFragment.findNavController(this);
        this.selectedIngredients = new ArrayList<>();
        editRecipeViewModel =  new ViewModelProvider(requireActivity()).get(EditRecipeViewModel.class);
        recipeIndex = getArguments().getInt("recipeIndex");
        env = Environment.of((AppCompatActivity) requireActivity());

        final Observer<Recipe> recipeObs = new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable final Recipe recipe) {
                // update the recipe draft.
                restoreRecipe(recipe);
            }
        };
        editRecipeViewModel.liveRecipe().observe(this, recipeObs);
        if (recipeIndex > -1 && !env.getRecipes().getList().isEmpty()) {
            // grab existing recipe if updating

            Recipe ogRecipe = env.getRecipes().getList().get(recipeIndex);

            Recipe newRecipe = new Recipe(
                    ogRecipe.getTitle(),
                    ogRecipe.getPreparationTime(),
                    ogRecipe.getServings(),
                    ogRecipe.getCategory(),
                    ogRecipe.getComments()
            );
            newRecipe.setPhotograph(ogRecipe.getPhotograph());
            newRecipe.setIngredients(new ArrayList<Ingredient>(ogRecipe.getIngredients()));
            editRecipeViewModel.liveRecipe().setValue(newRecipe);
        }else {
            editRecipeViewModel.liveRecipe().setValue(new Recipe(null,null,null,null,null));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_edit_recipe, container, false);

        catSelect = rootView.findViewById(R.id.select_category);
        choosePhoto = rootView.findViewById(R.id.gallery_button);
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
        takePhotoButton = rootView.findViewById(R.id.take_photo_button);

        getAdapter();

        layoutManager = new LinearLayoutManager(this.getActivity());
        recipe_recyclerView = rootView.findViewById(R.id.ingredientsInRep);
        recipe_recyclerView.setLayoutManager(layoutManager);

        recipe_recyclerView.setAdapter(ingredientListAdapter);
        recipe_recyclerView.setItemAnimator(new DefaultItemAnimator());

        onClickMethods();

        restoreRecipe(editRecipeViewModel.getRecipe());

        return rootView;
    }

    /**
     * Upon creating this fragment, this method either sets all the texts if user selects existing
     * ingredient, or creating an empty adapter for creating a new recipe
     */
    public void getAdapter() {
        recipeIndex = getArguments().getInt("recipeIndex");
        if (recipeIndex > -1 && !env.getRecipes().getList().isEmpty()) {
            setCategory(editRecipeViewModel.getRecipe().getCategory());
            prepareTimeText.setText(editRecipeViewModel.getRecipe().getPreparationTime().toString());
            servingsText.setText(editRecipeViewModel.getRecipe().getServings().toString());
            commentText.setText(editRecipeViewModel.getRecipe().getComments());
            nameText.setText(editRecipeViewModel.getRecipe().getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && editRecipeViewModel.getRecipe().getPhotograph() != null) {
                previewPhoto.setImageBitmap(editRecipeViewModel.getRecipe().getPhotograph());
            }
            ingredientListAdapter = new IngredientInRecipeAdapter(editRecipeViewModel.getRecipe().getIngredients(), this);
        } else {
            ingredientListAdapter = new IngredientInRecipeAdapter(new ArrayList<Ingredient>(), this);
        }
    }

    /**
     * This method gives all the onClick methods to this fragment's buttons
     */
    public void onClickMethods() {
        catSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CategorySelectPopup.SelectListener listener = new CategorySelectPopup.SelectListener() {
                    /**
                     * This method displays the selected category
                     * @param val, the string is the selected category
                     */
                    @Override
                    public void send(String val) {
                        setCategory(val);
                    }
                };
                RecipeCategorySelectPopup selection = new RecipeCategorySelectPopup(listener,"Category");
                selection.show(getFragmentManager(), "");
                selection.setTargetFragment(EditRecipeFragment.this, 1);

            }
        });

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                saveDraft();
                navController.navigate(EditRecipeFragmentDirections.actionEditRecipeToRecipeSelectIngredientFragment());
            }
        });

        deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecipeAction();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(EditRecipeFragmentDirections.actionEditRecipeToRecipeListFragment());
            }
        });

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                }
                else
                {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                }
            }
        });

        saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                onSaveRecipeClicked();
            }
        });

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    /**
     * This function is triggered when user selects the image, and it displays the image
     * @param requestCode, identifies which request is made
     * @param resultCode, result code
     * @param data
     * @see *source: https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // selected photo from gallery
        if (resultCode == RESULT_OK && requestCode == PICK_FROM_GALLERY) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    previewPhoto.setImageURI(selectedImageUri);
                }
        }

        // take photo from camera
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            previewPhoto.setImageBitmap(photo);
        }
    }

    /**
     * This function checks for the permission status. It either pops up a denial message or proceeds
     * to action upon permission.
     * @param requestCode, identifies which request is made
     * @param permissions, permission string
     * @param grantResults, granted permission results by user
     * @see *source: https://stackoverflow.com/questions/5991319/capture-image-from-camera-and-display-in-activity/5991757#5991757
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Camera permission granted", Toast.LENGTH_SHORT).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getActivity(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PICK_FROM_GALLERY) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Gallery permission granted", Toast.LENGTH_SHORT).show();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
            } else {
                Toast.makeText(getActivity(), "Gallery permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void deleteIngredient(int pos) {
        ingredientListAdapter.removeIngredient(pos);
        ingredientListAdapter.notifyDataSetChanged();
    }

    /**
     * This function handles the exception of not all the required fields are saved.
     * It either saves the recipe or replace the old recipe with edited version, or show error
     * if not all the fields are filled.
     */
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
            // check if there is any ingredient
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    newRecipe.addIngredient(ingredientListAdapter.getIngredients().get(i));
                }
            }
            newRecipe.setPhotograph(photo);

            // save or replace with edited
            if (recipeIndex > -1 && !env.getRecipes().getList().isEmpty()) {
                saveEditedRecipe(newRecipe);
            } else {
                env.getRecipes().add(newRecipe);
            }

            env.getRecipes().commit();
            navController.navigate(EditRecipeFragmentDirections.actionEditRecipeToRecipeListFragment());
        } catch(Exception E) {
            showError(E);
        }
    }

    /**
     * This function saves a draft of a recipe so it can be returned to after selecting ingredients
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveDraft() {
        try {
            String name = nameText.getText().toString();
            Long prepareTime = prepareTimeText.getText().toString().isEmpty()?
                    null: Long.parseLong(prepareTimeText.getText().toString());
            int servings = servingsText.getText().toString().isEmpty()?
                    null: Integer.parseInt(servingsText.getText().toString());
            String category = cat;
            String comment = commentText.getText().toString();
            Bitmap photo = ((BitmapDrawable) previewPhoto.getDrawable()).getBitmap();
            Recipe newRecipe = new Recipe(name, prepareTime, servings, category, comment, photo);

            int size = ingredientListAdapter.getIngredients().size();
            // check if there is any ingredient
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    newRecipe.addIngredient(ingredientListAdapter.getIngredients().get(i));
                }
            }
            newRecipe.setPhotograph(photo);

            editRecipeViewModel.liveRecipe().setValue(newRecipe);
        } catch(Exception E) {
            showError(E);
        }
    }

    /**
     * restores a saved draft recipe
     */
    private void restoreRecipe(Recipe recipe){
        if(recipe.getCategory() != null && !recipe.getCategory().isEmpty()){
            setCategory(recipe.getCategory());
        }
        if(recipe.getPreparationTime() != null){
            prepareTimeText.setText(recipe.getPreparationTime().toString());
        }
        if(recipe.getServings() != null){
            servingsText.setText(recipe.getServings().toString());
        }
        if(recipe.getComments() != null && !recipe.getComments().isEmpty()){
            commentText.setText(recipe.getComments());
        }
        if(recipe.getTitle() != null && !recipe.getTitle().isEmpty()){
            nameText.setText(recipe.getTitle());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && recipe.getPhotograph() != null) {
            previewPhoto.setImageBitmap(recipe.getPhotograph());
        }
        if(recipe.getIngredients() != null){
            ingredientListAdapter.setIngredients(recipe.getIngredients());
        }

        if (editRecipeViewModel.getRecipe() == null || editRecipeViewModel.getRecipe().getIngredients().isEmpty()) {
            tableText.setVisibility(View.INVISIBLE);
        }
        if (!selectedIngredients.isEmpty()) {
            tableText.setVisibility(View.VISIBLE);
        }
    }

    protected void saveEditedRecipe(Recipe r) {
        env.getRecipes().getList().set(recipeIndex,r);
    }

    public void deleteRecipeAction() {
        if (recipeIndex > -1 && !env.getRecipes().getList().isEmpty()) {
            env.getRecipes().getList().remove(recipeIndex);
            env.getRecipes().commit();
        }
        navController.navigate(EditRecipeFragmentDirections.actionEditRecipeToRecipeListFragment());
    }

    /**
     * This method displays the selected category
     * @param cat, the string is the selected category
     */
    public void setCategory(String cat) {
        catSelect.setAllCaps(false);
        catSelect.setText(cat);
        catSelect.setGravity(Gravity.LEFT);
        catSelect.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        catSelect.setTypeface(Typeface.SANS_SERIF);
        this.cat = cat;
    }

    private void showError(Exception e){
        TextView error = rootView.findViewById(R.id.errorText);
        error.setText(e.getMessage());
        error.setVisibility(View.VISIBLE);
    }

}