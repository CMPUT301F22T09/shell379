package com.cmput301f22t09.shell379.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.cmput301f22t09.shell379.data.util.ArraySortUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Partial ingredient that is missing date and location. Otherwise quite similar.
 */
public class IngredientStub implements Serializable {
    private String description;
    private Integer amount;
    private String unit;
    private String category;
    private static List<String> sortOptions = Arrays.asList("Description", "Category");

    /**
     * Construct the ingredient class
     * @param description
     * @param amount
     * @param unit
     * @param category
     * @throws IllegalArgumentException
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public IngredientStub(String description, Integer amount, String unit, String category) throws IllegalArgumentException {
        this.description = description;

        this.amount = amount;
        if (amount!= null && amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.unit = unit;
        this.category = category;
    }

    /**
     * Get the description of ingredient
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of ingredient
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the amount of ingredient
     * @return
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Set the amount of ingredient
     * @param amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Get the unit of ingredient
     * @return
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Set the unit of ingredient
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Get the category of ingredient
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set the category of ingredient
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

     /**
     * overrides Java's equals method
     * @param o ingredient to compare to
     * @return true if the ingredient is equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        IngredientStub ing = (IngredientStub) o;
        if (this.description.equals( ing.description)){
            if (this.category.equals(ing.category)){
                if (this.unit.equals(ing.unit)){
                    if (this.amount.equals(ing.amount)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * looser equals method that does not compare amount
     * @param o ingredient to compare to
     * @return true if the ingredient is equal, false otherwise
     */
    public boolean looseEquals(Object o){
        IngredientStub ing = (IngredientStub) o;
        if (this.description.equals( ing.description)){
            if (this.category.equals(ing.category)){
                if (this.unit.equals(ing.unit)){
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * returns the proper way to get the property we want to sort on
     * based on what the user has selected as the sort.
     */
    public static ArraySortUtil.StringPropGetter getStringPropGetter(int selectedSortIndex){
        if( selectedSortIndex == 0){
            return new ArraySortUtil.StringPropGetter() {
                @Override
                public String getString(Object object) {
                    return ((IngredientStub)object).getDescription();
                }
            };
        }
        else if( selectedSortIndex == 1){
            return new ArraySortUtil.StringPropGetter() {
                @Override
                public String getString(Object object) {
                    return ((IngredientStub)object).getCategory();
                }
            };
        }
        else{
            throw new IllegalArgumentException( "No property to sort on is selected");
        }
    }

    /**
     *
     * @return a list of sortable properties. Each index of each option corresponds
     * to a StringPropGetter returned from the method "getStringPropGetter()"
     */
    public static List<String> getSortableProps(){
        return sortOptions;
    }

}
