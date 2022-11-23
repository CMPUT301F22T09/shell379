package com.cmput301f22t09.shell379.data.vm;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.ShoppingCart;
import com.cmput301f22t09.shell379.data.util.DatabaseManager;
import com.cmput301f22t09.shell379.data.vm.collections.CategorySet;
import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;

import java.io.Serializable;

/**
 * ViewModel object that acts as the central source of truth of data
 * in the application. All stateful data manipulation must go through this class.
 * The class is accessible from all fragments and does not change between fragments.
 */
public class Environment extends ViewModel implements Serializable {
    private LiveCollection<Ingredient> ingredients;
    private LiveCollection<Recipe> recipes;
    private LiveCollection<MealPlan> mealPlans;
    private LiveCollection<CartIngredient> cart;
    private CategorySet ingredientCategories;
    private CategorySet recipeCategories;
    private CategorySet locationCategories;

    public Environment() {
        ingredients = new LiveCollection<Ingredient>();
        recipes = new LiveCollection<Recipe>();
        cart = new LiveCollection<CartIngredient>();
        mealPlans = new LiveCollection<MealPlan>();
        ingredientCategories = new CategorySet();
        recipeCategories = new CategorySet();
        locationCategories = new CategorySet();
    }

    /**
     * Returns the Environment viewModel and sets it to an existing Environment object
     * @param owner activity requesting the environment viewModel
     * @param envPulled is the environment to apply to the environment viewModel
     */
    public static Environment of(AppCompatActivity owner, Environment envPulled) {
        Environment env = new ViewModelProvider(owner).get(Environment.class);
        try {
            env.ingredients.setList(envPulled.ingredients.getList());
            env.cart.setList(envPulled.cart.getList());
            env.recipes.setList(envPulled.recipes.getList());
            env.ingredientCategories.setCategories(envPulled.ingredientCategories.getCategories());
            env.recipeCategories.setCategories(envPulled.recipeCategories.getCategories());
            env.locationCategories.setCategories(envPulled.locationCategories.getCategories());
            env.mealPlans.setList(envPulled.mealPlans.getList());
            env.cart.setList(envPulled.cart.getList());

            setupObservers(owner, env);
        } catch (NullPointerException e) {
            Log.e("ENV","env failed to update data from pull" + e.getMessage());
        }
        return env;
    }

    /**
     * Returns the Environment viewModel
     * @param owner activity requesting the environment viewModel
     */
    public static Environment of(AppCompatActivity owner) {
        Environment env = new ViewModelProvider(owner).get(Environment.class);
        if (env == null) env = new Environment();
        setupObservers(owner, env);
        return env;
    }

    /**
     * sets up observers for changes to the environment data.
     * Observers will trigger database pushes
     * @param owner activity requesting the environment viewModel
     * @param env the environment to push to the database
     */
    private static void setupObservers(AppCompatActivity owner, Environment env) {
        observeForCommits(owner, env, env.getIngredients());
        observeForCommits(owner, env, env.getRecipes());
        observeForCommits(owner, env, env.getCart());
        observeForCommits(owner, env, env.getIngredientCategories());
        observeForCommits(owner, env, env.getRecipeCategories());
        observeForCommits(owner, env, env.getLocationCategories());
        observeForCommits(owner, env, env.getMealPlans());
        observeForCommits(owner, env, env.getCart());
    }

    /**
     * Sets a single observer for the data of a Commitable object.
     * When the Commitable needs to be committed the environment will be pushed.
     *
     * Observer will commit even if the commit attribute was previously
     * true and then set to true again.
     *
     * @param owner scope for the observation.
     * @param env env object to push to the database.
     * @param commitable Commitable object to watch for commit triggers.
     */
    private static void observeForCommits(AppCompatActivity owner, Environment env, Commitable commitable) {
        commitable.isCommitNeeded().observe(owner, new Observer<Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(Boolean commitNeeded) {
                if (commitNeeded==true) {
                    new DatabaseManager(owner).push(env);
                };
            }
        });
    }

    public LiveCollection<Ingredient> getIngredients() {
        return ingredients;
    }

    public LiveCollection<Recipe> getRecipes() {
        return recipes;
    }

    public LiveCollection<CartIngredient> getCart() {
        return cart;
    }

    public CategorySet getIngredientCategories() {
        return ingredientCategories;
    }

    public CategorySet getRecipeCategories() {
        return recipeCategories;
    }

    public CategorySet getLocationCategories() {
        return locationCategories;
    }

    public LiveCollection<MealPlan> getMealPlans() {
        return mealPlans;
    }
}