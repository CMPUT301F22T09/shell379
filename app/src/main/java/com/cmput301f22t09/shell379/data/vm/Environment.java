package com.cmput301f22t09.shell379.data.vm;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.cmput301f22t09.shell379.data.vm.collections.IngredientCollection;
import com.cmput301f22t09.shell379.data.vm.collections.RecipeCollection;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

public class Environment extends ViewModel {
    private IngredientCollection ingredients;
    private RecipeCollection recipes;
    private LiveCart cart;
    private Boolean commitNeeded;

    public static Environment of(AppCompatActivity owner) {
        Environment env = new ViewModelProvider(owner).get(Environment.class);
        setupObservers(owner, env);
        return env;
    }

    private static void setupObservers(AppCompatActivity owner, Environment env) {
        observeForCommits(owner, env.getIngredients());
        observeForCommits(owner, env.getRecipes());
        observeForCommits(owner, env.getCart());
    }

    private static void observeForCommits(AppCompatActivity owner, Commitable commitable) {
        commitable.isCommitNeeded().observe(owner, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean commitNeeded) {
                if (commitNeeded==true) commitNeeded = true;
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
}