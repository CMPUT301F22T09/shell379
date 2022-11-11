package com.cmput301f22t09.shell379.data;

import android.os.Build;
import androidx.annotation.RequiresApi;

import com.cmput301f22t09.shell379.data.util.ArraySortUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Ingredient class is a model for the ingredient used in the app
 */
public class Ingredient implements Serializable{
    private String description;
    private Date bestBefore;
    private String location;
    private Integer amount;
    private String unit;
    private String category;
    private static List<String> sortOptions = Arrays.asList("Description","Best Before Date", "Location", "Category");

    /**
     * Construct the ingredient class
     * @param description
     * @param bestBefore
     * @param location
     * @param amount
     * @param unit
     * @param category
     * @throws IllegalArgumentException
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Ingredient(String description, Date bestBefore, String location, Integer amount, String unit, String category) throws IllegalArgumentException {
        this.description = description;

        this.bestBefore = bestBefore;
        if (bestBefore != null && bestBefore.before(new Date())) {
            throw new IllegalArgumentException("Best Before Date shall not be before today upon construction!");
        }

        this.location = location;

        this.amount = amount;
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.unit = unit;
        this.category = category;
    }

    /**
     * Creates Ingredient object with an image.
     * @param description
     * @param location
     * @param amount
     * @param unit
     * @param category
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Ingredient(String description, String location, Integer amount, String unit, String category) {
        this.description = description;
        this.bestBefore = null;
        this.location = location;
        this.amount = amount;
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

    public Date getBestBefore() {
        return bestBefore;
    }

    /**
     * Set the best before date of ingredient
     * @param bestBefore
     */
    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    /**
     * Get the best before date of ingredient
     * @return
     * get best before date in the format dd/MM/yyyy
     * @return best before date in a string as dd/mm/yyy
     */
    public String getBestBeforeFormatted() {
        if(bestBefore != null){
            SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");
            return simpleDate.format(getBestBefore());
        }else{
            return "Date not set";
        }
    }

    /**
     * Get the best before date of ingredient inverted
     * @return
     * get best before date in the format yyyy/MM/dd
     * @return best before date in a string as yyyy/MM/dd
     */
    public String getBestBeforeFormattedInverted() {
        if(bestBefore != null){
            SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyy/MM/dd");
            return simpleDate.format(getBestBefore());
        }else{
            return "Date not set";
        }
    }

    /**
     * Get the location of ingredient
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the location of ingredient
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
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
     * returns the proper way to get the property we want to sort on
     * based on what the user has selected as the sort.
     */
    public static ArraySortUtil.StringPropGetter getStringPropGetter(int selectedSortIndex){
        if( selectedSortIndex == 0){
            return new ArraySortUtil.StringPropGetter() {
                @Override
                public String getString(Object object) {
                    return ((Ingredient)object).getDescription();
                }
            };
        }
        else if( selectedSortIndex == 1){
            return new ArraySortUtil.StringPropGetter() {
                @Override
                public String getString(Object object) {
                    return ((Ingredient)object).getBestBeforeFormattedInverted();
                }
            };
        }
        else if( selectedSortIndex == 2){
            return new ArraySortUtil.StringPropGetter() {
                @Override
                public String getString(Object object) {
                    return ((Ingredient)object).getLocation();
                }
            };
        }
        else if( selectedSortIndex == 3){
            return new ArraySortUtil.StringPropGetter() {
                @Override
                public String getString(Object object) {
                    return ((Ingredient)object).getCategory();
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


    /**
     * overrides Java's equals method
     * @param o ingredient to compare to
     * @return true if the ingredient is equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        Ingredient ing = (Ingredient) o;
        if (this.description.equals( ing.description)){
            if (this.category.equals(ing.category)){
                if (this.unit.equals(ing.unit)){
                    if (this.amount.equals(ing.amount)){
                        if ((this.bestBefore == null && ing.bestBefore == null) || (this.bestBefore != null && this.getBestBefore().equals(ing.bestBefore))){
                            if ((this.location== null && ing.location == null)  || (this.location != null && this.location.equals(ing.location))){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
