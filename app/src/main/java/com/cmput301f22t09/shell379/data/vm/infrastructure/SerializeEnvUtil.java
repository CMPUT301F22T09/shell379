package com.cmput301f22t09.shell379.data.vm.infrastructure;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.util.SerializeUtil;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SerializeEnvUtil {
    //serialize/deserialize
    //https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static HashMap<String, String> serialize(Environment env) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("ingredients", SerializeUtil.serialize(env.getIngredients().getList()));
        data.put("recipes", SerializeUtil.serialize(env.getRecipes().getList()));
        data.put("cart", SerializeUtil.serialize(env.getCart().getList()));
        data.put("ingredient_categories", SerializeUtil.serialize(env.getIngredientCategories()));
        data.put("recipes_categories", SerializeUtil.serialize(env.getRecipeCategories()));
        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Environment deserialize(HashMap<String, String> data) {
        Environment env = new Environment();
        try {
            if (data.get("ingredients").length()>0)
                env.getIngredients().setList((ArrayList<Ingredient>) SerializeUtil.deserialize(data.get("ingredients")));
            if (data.get("recipes").length()>0)
                env.getRecipes().setList((ArrayList<Recipe>) SerializeUtil.deserialize(data.get("recipes")));
            if (data.get("cart").length()>0)
                env.getCart().setList((ArrayList<CartIngredientWrapper>) SerializeUtil.deserialize(data.get("cart")));
            if (data.get("ingredient_categories").length()>0)
                env.getIngredientCategories().setCategories((HashSet<String>) SerializeUtil.deserialize(data.get("ingredient_categories")));
            if (data.get("recipes_categories").length()>0)
                env.getRecipeCategories().setCategories((HashSet<String>) SerializeUtil.deserialize(data.get("recipes_categories")));
        }
        catch (NullPointerException e) {
        }
        return env;
    }
}
