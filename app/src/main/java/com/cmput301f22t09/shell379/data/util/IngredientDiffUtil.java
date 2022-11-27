package com.cmput301f22t09.shell379.data.util;


import androidx.core.util.Pair;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.ShoppingCart;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IngredientDiffUtil {
    /**
     * Flattens ingredients in meal plans, using them to compute the items needed in the shopping cart
     * @param mealPlans list of meal plans
     * @return map of ingredient names to cart items
     */
    public static Map<String, CartIngredient> flattenMealPlans(ArrayList<MealPlan> mealPlans) {
        Map<String, CartIngredient> ingredientMap = mealPlans.stream()
                .map(MealPlan::getRecipes)
                .flatMap(ArrayList::stream)
                .map(MealPlanWrapper::convertRecipeToIngredientList)
                .flatMap(ArrayList::stream)
                .map(CartIngredient::convertIngredientStub)
                .collect(Collectors.toMap(CartIngredient::getDescription, Function.identity()));
        mealPlans.stream()
                .map(MealPlan::getIngredients)
                .flatMap(ArrayList::stream)
                .map(MealPlanWrapper::convertToIngredient)
                .map(CartIngredient::convertIngredient)
                .forEach(e -> {
                    if (ingredientMap.containsKey(e.getDescription())) {
                        CartIngredient cartIngredient = ingredientMap.get(e.getDescription());
                        cartIngredient.setAmount(e.getAmount()+cartIngredient.getAmount());
                    } else {
                        ingredientMap.put(e.getDescription(),e);
                    }
                });
        return ingredientMap;
    }

    /**
     * Subtracts ingredients in ingredient storage from computed shopping cart
     * @param ingredientMap map of ingredient names to cart items
     * @param ingredients ingredient storage
     * @return map of ingredient names to cart items
     */
    public static Map<String, CartIngredient> subtractIngredientStorage(Map<String, CartIngredient> ingredientMap, ArrayList<Ingredient> ingredients) {
        ingredients.stream()
            .map(CartIngredient::convertIngredient)
            .forEach(e -> {
                if (ingredientMap.containsKey(e.getDescription())) {
                    CartIngredient cartIngredient = ingredientMap.get(e.getDescription());
                    cartIngredient.setAmount(cartIngredient.getAmount()-e.getAmount());
                } else {
                    ingredientMap.put(e.getDescription(),e);
                }
            });
        return ingredientMap;
    }

    /**
     * Transfers pre-existing cart data to newly computed cart
     * @param ingredientMap map of ingredient names to cart items
     * @param cleanedCart cleaned cart
     * @return list of cart items
     */
    public static ArrayList<CartIngredient> transferCartData(Map<String, CartIngredient> ingredientMap, ArrayList<CartIngredient> cleanedCart) {
        cleanedCart.forEach(e -> {
            if (ingredientMap.containsKey(e.getDescription())) {
                CartIngredient cartIngredient = ingredientMap.get(e.getDescription());
                cartIngredient.setIngredient(e.getIngredient());
                cartIngredient.setCategory(e.getCategory());
                cartIngredient.setUnit(e.getUnit());
                cartIngredient.setDetailsFilled(e.getDetailsFilled());
                cartIngredient.setPickedUp(e.getPickedUp());
            }
        });
        return new ArrayList<>(ingredientMap.values());
    }

    /**
     * Removes ingredients which have not been picked up from the cart
     * @param cart the list of items in the cart
     * @return filtered list of items in cart
     */
    public static ArrayList<CartIngredient> cleanCart(ArrayList<CartIngredient> cart) {
        return cart.stream()
            .filter(CartIngredient::getPickedUp)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Computes items which need to be passed to the cart and updates it.
     * @param env Environment/state of the program
     */
    public static void prepareCart(Environment env) {
        ArrayList<CartIngredient> cleanedCart = cleanCart(env.getCart().getList());
        if (cleanedCart==null)
            cleanedCart = new ArrayList<>();
        Map<String, CartIngredient> ingredientMap = flattenMealPlans(env.getMealPlans().getList());
        ingredientMap = subtractIngredientStorage(ingredientMap, env.getIngredients().getList());
        ArrayList<CartIngredient> cartList = transferCartData(ingredientMap, cleanedCart);
        cartList = cartList.stream()
            .filter(e->e.getAmount()>0)
            .collect(Collectors.toCollection(ArrayList::new));
        env.getCart().setList(cartList);
        if (env.getMealPlans().getList().size()==0) env.getCart().setList(new ArrayList<>());
    }
}
