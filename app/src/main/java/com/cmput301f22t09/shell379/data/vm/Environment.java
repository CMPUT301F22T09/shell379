package com.cmput301f22t09.shell379.data.vm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cmput301f22t09.shell379.data.util.DatabaseManager;
import com.cmput301f22t09.shell379.data.vm.collections.IngredientCategories;
import com.cmput301f22t09.shell379.data.vm.collections.IngredientCollection;
import com.cmput301f22t09.shell379.data.vm.collections.RecipeCategories;
import com.cmput301f22t09.shell379.data.vm.collections.RecipeCollection;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.io.Serializable;
import java.util.HashSet;

public class Environment extends ViewModel implements Serializable {
    private IngredientCollection ingredients;
    private RecipeCollection recipes;
    private LiveCart cart;
    private IngredientCategories ingredientCategories;
    private RecipeCategories recipeCategories;

    public Environment() {
        ingredients = new IngredientCollection();
        recipes = new RecipeCollection();
        cart = new LiveCart();
        ingredientCategories = new IngredientCategories();
        recipeCategories = new RecipeCategories();
    }

    public static Environment of(AppCompatActivity owner, Environment envPulled) {
        Environment env = new ViewModelProvider(owner).get(Environment.class);
        env.ingredients = envPulled.ingredients;
        env.cart = envPulled.cart;
        env.recipes = envPulled.recipes;

        setupObservers(owner, env);
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
            @Override
            public void onChanged(Boolean commitNeeded) {
                if (commitNeeded==true) {
                    new DatabaseManager(owner).push(env);
                };
            }
        });
    }

    public IngredientCollection getIngredients() {
        return ingredients;
    }

    public RecipeCollection getRecipes() {
        return recipes;
    }

    public LiveCart getCart() {
        return cart;
    }

    public IngredientCategories getIngredientCategories() {
        return ingredientCategories;
    }

    public RecipeCategories getRecipeCategories() {
        return recipeCategories;
    }
}