package com.cmput301f22t09.shell379.data.util;


import androidx.core.util.Pair;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.ShoppingCart;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class IngredientDiffUtil {

    /**
     * Gets all required ingredients from the MealPlan.
     * Ingredients in this hashmap will have the following properties:
     *      description = No change
     *      bestbefore = MealPlan.endDate
     *      location = "placeholder"
     *      amount = sum of all amounts
     *              from all ingredients
     *              with the same description in this MealPlan
     *      unit = No change
     *      category = "placeholder"
     * TODO: Testing Donw
     * @param mp: MealPlan object
     * @return: A HashMap of ingredients, unique with the description (lower case) being the key.
     */
    public static HashMap<String, Ingredient> getIngredientsNeeded(MealPlan mp) {
        HashMap<String, Ingredient> totalIngs = new HashMap<String, Ingredient>();
        ArrayList<Recipe> allRecipes = mp.getRecipes().stream()
                .map(MealPlanWrapper::convertToRecipe)
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Ingredient> ingsFromMP = mp.getIngredients().stream()
                .map(MealPlanWrapper::convertToIngredient)
                .collect(Collectors.toCollection(ArrayList::new));

        // Loop through all recipes, get all ingredients
        for (int i = 0; i < allRecipes.size(); i++) {
            ArrayList<IngredientStub> ingStubFromRec = allRecipes.get(i).getIngredients();

            // Loop through all required ingredients in each recipe
            for (int j = 0; j < ingStubFromRec.size(); j++) {
                String ingDescription = ingStubFromRec.get(j).getDescription();
                int amount = ingStubFromRec.get(j).getAmount();
                String unit = ingStubFromRec.get(j).getUnit();

                // put ingredients into hashmap if not previously existing.
                // Two ingredients are the same (in this case) if the descriptions match
                if (!totalIngs.containsKey(ingDescription.toLowerCase())) {
                    totalIngs.put(ingDescription.toLowerCase(), new Ingredient(
                            ingDescription,
                            mp.getEndDate(),
                            "Placeholder",
                            amount,
                            unit,
                            "Placeholder"));
                } else { // if ingredient exists, simply update the amount
                    Ingredient temp = totalIngs.get(ingDescription.toLowerCase());
                    temp.setAmount(temp.getAmount() + amount);
                    totalIngs.put(ingDescription.toLowerCase(), temp);
                }
            }
        }

        // Loop through all standalone ingredients
        for (int j = 0; j < ingsFromMP.size(); j++) {
            String ingDescription = ingsFromMP.get(j).getDescription();
            int amount = ingsFromMP.get(j).getAmount();
            String unit = ingsFromMP.get(j).getUnit();

            // put ingredients into hashmap if not previously existing.
            // Two ingredients are the same (in this case) if the descriptions match
            if (!totalIngs.containsKey(ingDescription.toLowerCase())) {
                totalIngs.put(ingDescription, new Ingredient(
                        ingDescription,
                        mp.getEndDate(),
                        "Placeholder",
                        amount,
                        unit,
                        "Placeholder"));
            } else {
                Ingredient temp = totalIngs.get(ingDescription.toLowerCase());
                assert temp != null;
                temp.setAmount(temp.getAmount() + amount);
                totalIngs.put(ingDescription.toLowerCase(), temp);
            }
        }

        // All members of totalIngs should be unique by description...
        return totalIngs;
    }

    /**
     * Gets all ingredients we already have from the environment.
     * Unlike getIngredientsNeeded, all ingredients will have their attributes unchanged.
     * @param env: Environment object
     * @return: A HashMap of ArrayList<ingredients>, unique with the description (lower case) being the key.
     *          Each ArrayList contains ingredients with the same name, different best before dates.
     */
    public static HashMap<String, ArrayList<Ingredient> > getIngredientsInStock(Environment env) {
        ArrayList<Ingredient> freezeState =  env.getIngredients().getList();
        HashMap<String, ArrayList<Ingredient> > totalIngs = new HashMap<String, ArrayList<Ingredient> >();
        for (int i = 0; i < freezeState.size(); i++) {
            String key = freezeState.get(i).getDescription().toLowerCase();

            // add to hashmap if key does not exist
            if (!totalIngs.containsKey(key)) {
                ArrayList<Ingredient> entry = new ArrayList<Ingredient>();
                entry.add(freezeState.get(i));
                totalIngs.put(key, entry);
            } else { // otherwise, add to array entry
                ArrayList<Ingredient> entry = totalIngs.get(key);
                entry.add(freezeState.get(i));
                totalIngs.put(key, entry);
            }
        }
        return totalIngs;
    }


    /**
     * Coalesces the ingredients you have by combining then into a single
     * Ingredient object, omitting the ones that are expired.
     * Assumes that inputs are of the same ingredient.
     * @param need
     * @param have
     * @return
     */
    public static Ingredient coalesce(Ingredient need, ArrayList<Ingredient> have) {
        Ingredient validIng = new Ingredient(
                need.getDescription(),
                need.getBestBefore(),
                need.getLocation(),
                0,
                need.getUnit(),
                need.getCategory());

        for (int i = 0; i < have.size(); i++) {

            // if what we have has best before at a later date than
            // what we need, then put it into validIngs
            if (have.get(i).getBestBefore().getTime() > need.getBestBefore().getTime()) {
                validIng.setAmount(validIng.getAmount() + have.get(i).getAmount());
            }
        }
        return validIng;
    }




    /**
     * Computes ingredients that we can remove our inventory, as well as
     * ingredients to put into the shopping cart based on what the meal plan needs
     * and what we have.
     *
     * @param needed: A hashmap of Ingredients that we need for the meal plan. These ingredients
     *              have their best before date set to the end date of the meal plan,
     *              and will be used to filter out all expired ingredients from
     *              our inventory
     * @param have: A hashmap of Ingredients we have. HashMap groups ingredients with the
     *              same description, with members of the ArrayList differing by best before
     *              date and amount.
     * @return: ArrayList of ingredients to remove from our inventory, as well as ArrayList
     *              of ingredients to add to shopping cart.
     */
    public static Pair<ArrayList<Ingredient>, ArrayList<Ingredient> > computeSubtractAndBuyList (
            HashMap<String, Ingredient> needed,
            HashMap<String, ArrayList<Ingredient>> have) {

        ArrayList<Ingredient> subtractFromInventory = new ArrayList<Ingredient>();
        ArrayList<Ingredient> putIntoShoppingCart = new ArrayList<Ingredient>();

        // for all in need, match with the objects in have
        for (String need : needed.keySet()) {
            if (have.containsKey(need)) {

                // This is the ingredient that we have, guaranteed to be
                // within the expiry date.
                Ingredient haveValid = IngredientDiffUtil.coalesce(needed.get(need), have.get(need));


                // need_amount - have_amount
                int need_amount = needed.get(need).getAmount();
                int have_amount = haveValid.getAmount();
                int diff = need_amount - have_amount;

                // if there's more need than we have,
                // then subtract all from inventory,
                // and add the remaining we need to shopping cart.

                if (diff > 0) {
                    subtractFromInventory.add(haveValid);

                    putIntoShoppingCart.add(new Ingredient(
                            needed.get(need).getDescription(),
                            new Date(),
                            needed.get(need).getLocation(),
                            diff, // Amount
                            needed.get(need).getUnit(),
                            needed.get(need).getCategory()
                    ));
                }
                // if we have more than we need,
                // then subtract what we need from
                // the inventory.
                else {
                    subtractFromInventory.add(needed.get(need));
                }


            }
            // if we don't have the needed ingredient, we will
            // put it into the shopping cart
            else {
                putIntoShoppingCart.add(needed.get(need));
            }

        }
        Pair<ArrayList<Ingredient>, ArrayList<Ingredient> > res = Pair.create(subtractFromInventory, putIntoShoppingCart);
        return res;
    }

    /**
     * IMPORTANT: Ingredient attributes bestbefore, location, categories are modified and therefore should not be
     *          used for storage in firebase. These ingredients are used for matching purposes. Safe alterations to
     *          the firebase database is performed by IngredientDiffUtil.commit()
     * @param mp: MealPlan to outline what ingredients we need
     * @param env: Environment representing ingredients we have
     * @return ArrayList<Ingredient> SubtractFromInventory, ArrayList<Ingredient> AddToShoppingCart
     */
    public static Pair<ArrayList<Ingredient>, ArrayList<Ingredient> > computeDiff(MealPlan mp, Environment env) {
        HashMap<String, Ingredient> needed =  getIngredientsNeeded(mp);
        HashMap<String, ArrayList<Ingredient> > have =  getIngredientsInStock(env);
        Pair<ArrayList<Ingredient>, ArrayList<Ingredient> > res =  computeSubtractAndBuyList(needed,have);
        return res;
    }


    public static void commitShoppingCart(Environment env, ArrayList<Ingredient> addToShoppingCart) {
//        HashMap<String, Integer> inCart = new HashMap<>();
//        ShoppingCart sc = env.getCart();
//        ArrayList<CartIngredient> cartIngs = sc.getList();
//        for (int i = 0; i < cartIngs.size(); i++) {
//            inCart.put(cartIngs.get(i).getDescription().toLowerCase(), cartIngs.get(i).getAmount());
//        }
//        for (int i = 0; i < addToShoppingCart.size(); i++) {
//            // if not in shopping cart, add the whole ingredient
//            if (!inCart.containsKey(addToShoppingCart.get(i).getDescription().toLowerCase())) {
//                sc.add(new CartIngredient(
//                        addToShoppingCart.get(i).getDescription(),
//                        addToShoppingCart.get(i).getCategory(),
//                        addToShoppingCart.get(i).getAmount(),
//                        addToShoppingCart.get(i).getUnit()
//                ));
//            }
//            // otherwise, if the ingredient is in shopping cart...
//            else {
//                // if what we need to add is more than what is already in the cart
//                if (addToShoppingCart.get(i).getAmount() > inCart.get(addToShoppingCart.get(i).getDescription().toLowerCase())) {
//                    // compute the difference (to_add - in_cart)
//                    int diff = addToShoppingCart.get(i).getAmount() - inCart.get(addToShoppingCart.get(i).getDescription().toLowerCase());
//                    sc.add(new CartIngredient(
//                            addToShoppingCart.get(i).getDescription(),
//                            addToShoppingCart.get(i).getCategory(),
//                            diff,
//                            addToShoppingCart.get(i).getUnit()));
//
//                }
//            }
//        }

        // naive algo
        LiveCollection<CartIngredient> sc = env.getCart();
        for (int i = 0; i < addToShoppingCart.size(); i++) {

            sc.add(new CartIngredient(
                addToShoppingCart.get(i).getDescription(),
                addToShoppingCart.get(i).getCategory(),
                addToShoppingCart.get(i).getAmount(),
                addToShoppingCart.get(i).getUnit()
            ));

        }
        // end naive algo
        sc.commit();
    }

}
