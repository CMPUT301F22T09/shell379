package com.cmput301f22t09.shell379.data.wrapper;

import android.util.Log;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.Recipe;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Ingredient that resides in the cart.
 */
public class MealPlanWrapper<T> implements Serializable {
    private Date date;
    private Integer servings;
    private String name;
    private T obj;

    public MealPlanWrapper(T obj, Date date, Integer servings) {
        this.obj = obj;
        this.date = date;
        this.servings = servings;
        this.name = "";

        if (obj instanceof Ingredient) {
            this.name = ((Ingredient) obj).getDescription();
        } else if (obj instanceof Recipe) {
            this.name = ((Recipe) obj).getTitle();
        }
    }

    public String getDisplayDate() {
        if(date != null){
            SimpleDateFormat simpleDate =  new SimpleDateFormat("YYYY-MM-dd");
            return simpleDate.format(date);
        }else{
            return "Date not set";
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public static ArrayList<IngredientStub> convertRecipeToIngredientList(MealPlanWrapper<Recipe> wrapper) {
        Recipe r = wrapper.getObj();
        ArrayList<IngredientStub> ings = r.getIngredients();
        ArrayList<IngredientStub> newIngrs = new ArrayList<>();
        for (int i = 0; i < ings.size(); i++) {
            IngredientStub is = ings.get(i).clone();
            Log.d("FDBK_CONVERSIONS_REC", wrapper.getName()+": "+ r.getServings()+" "+wrapper.getServings()+" " +(is.getAmount() * wrapper.getServings()));
            is.setAmount(r.getServings() * is.getAmount() * wrapper.getServings());
            newIngrs.add(is);
        }
        return newIngrs;
    }

    public static Ingredient convertToIngredient(MealPlanWrapper<Ingredient> wrapper) {
        Ingredient r = wrapper.getObj();
        r.setAmount(wrapper.getServings());
        Log.d("FDBK_CONVERSIONS_INGR", wrapper.getName()+": " + r.getAmount()+" "+wrapper.getServings()+" "+(r.getAmount() * wrapper.getServings()));
        return r;
    }
}
