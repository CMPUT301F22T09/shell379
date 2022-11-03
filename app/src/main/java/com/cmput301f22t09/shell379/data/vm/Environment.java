package com.cmput301f22t09.shell379.data.vm;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.ShoppingCart;
import com.cmput301f22t09.shell379.data.util.DatabaseManager;
import com.cmput301f22t09.shell379.data.vm.collections.CategorySet;
import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.io.Serializable;

public class Environment extends ViewModel implements Serializable {
    private LiveCollection<Ingredient> ingredients;
    private LiveCollection<Recipe> recipes;
    private ShoppingCart cart;
    private CategorySet ingredientCategories;
    private CategorySet recipeCategories;
    private CategorySet locationCategories;

    public Environment() {
        ingredients = new LiveCollection<Ingredient>();
        recipes = new LiveCollection<Recipe>();
        cart = new ShoppingCart();
        ingredientCategories = new CategorySet();
        recipeCategories = new CategorySet();
        locationCategories = new CategorySet();
    }

    public static Environment of(AppCompatActivity owner, Environment envPulled) {
        Environment env = new ViewModelProvider(owner).get(Environment.class);
        try {
            env.ingredients.setList(envPulled.ingredients.getList());
            env.cart.setList(envPulled.cart.getList());
            env.cart.setActiveDays(envPulled.cart.getActiveDays());
            env.recipes.setList(envPulled.recipes.getList());
            env.ingredientCategories.setCategories(envPulled.ingredientCategories.getCategories());
            env.recipeCategories.setCategories(envPulled.recipeCategories.getCategories());
            env.locationCategories.setCategories(envPulled.locationCategories.getCategories());

            setupObservers(owner, env);
        } catch (NullPointerException e) {
            Log.e("ENV","env failed to update data from pull" + e.getMessage());
        }
        return env;
    }

    public static Environment of(AppCompatActivity owner) {
        Environment env = new ViewModelProvider(owner).get(Environment.class);
        setupObservers(owner, env);
        return env;
    }

    private static void setupObservers(AppCompatActivity owner, Environment env) {
        observeForCommits(owner, env, env.getIngredients());
        observeForCommits(owner, env, env.getRecipes());
        observeForCommits(owner, env, env.getCart());
        observeForCommits(owner, env, env.getIngredientCategories());
        observeForCommits(owner, env, env.getRecipeCategories());
    }

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

    public ShoppingCart getCart() {
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
}