package com.cmput301f22t09.shell379.data.vm;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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

    public Environment() {
        ingredients = new LiveCollection<Ingredient>();
        recipes = new LiveCollection<Recipe>();
        cart = new ShoppingCart();
        ingredientCategories = new CategorySet();
        recipeCategories = new CategorySet();
    }

    public static Environment of(AppCompatActivity owner, Environment envPulled) {
        Environment env = new ViewModelProvider(owner).get(Environment.class);;
        try {
            env.ingredients = envPulled.ingredients;
            env.cart = envPulled.cart;
            env.recipes = envPulled.recipes;
            env.ingredientCategories = envPulled.ingredientCategories;
            env.recipeCategories = envPulled.recipeCategories;

            setupObservers(owner, env);
        } catch (NullPointerException e) {

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
}