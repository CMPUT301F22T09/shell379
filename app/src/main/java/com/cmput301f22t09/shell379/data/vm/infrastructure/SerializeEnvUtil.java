package com.cmput301f22t09.shell379.data.vm.infrastructure;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.ShoppingCart;
import com.cmput301f22t09.shell379.data.util.SerializeUtil;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.IngredientCategories;
import com.cmput301f22t09.shell379.data.vm.collections.RecipeCategories;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SerializeEnvUtil {
    //serialize/deserialize
    //https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static HashMap<String, String> serialize(Environment env) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("ingredients", SerializeUtil.serialize(env.getIngredients().getIngredients()));
        data.put("recipes", SerializeUtil.serialize(env.getRecipes().getRecipes()));
        data.put("cart", SerializeUtil.serialize(env.getCart().getCart()));
        data.put("ingredient_categories", SerializeUtil.serialize(env.getIngredientCategories().getIngredCategories()));
        data.put("recipes_categories", SerializeUtil.serialize(env.getRecipeCategories().getRecipeCategories()));
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Environment deserialize(HashMap<String, String> data) {
        Environment env = new Environment();
        env.getIngredients().setIngredients((ArrayList<Ingredient>) SerializeUtil.deserialize(data.get("ingredients")));
        env.getRecipes().setRecipes((ArrayList<Recipe>) SerializeUtil.deserialize(data.get("recipes")));
        env.getCart().setCart((ShoppingCart) SerializeUtil.deserialize(data.get("cart")));
        env.getIngredientCategories().setIngredientCategories((HashSet<String>) SerializeUtil.deserialize(data.get("ingredient_categories")));
        env.getRecipeCategories().setRecipeCategories((HashSet<String>) SerializeUtil.deserialize(data.get("recipes_categories")));
        return env;
    }
}
