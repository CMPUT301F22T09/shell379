package com.cmput301f22t09.shell379.data;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
/**
 *
 */
public class Ingredient implements Serializable {
    private String description;
    private Date bestBefore;
    private String location;
    private Integer amount;
    private String unit;
    private String category;

//    public Ingredient(String description, Date bestBefore, String location, Integer amount, String unit, String category) {
//        this.description = description;
//        this.bestBefore = bestBefore;
//        this.location = location;
//        this.amount = amount;
//        this.unit = unit;
//        this.category = category;
//    }

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
        if (bestBefore.before(new Date())) {
            throw new IllegalArgumentException("Best Before Date shall not be before today upon construction!");
        }

        this.location = location;

        this.amount = amount;
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.unit = unit;
//        if (unit.replaceAll("[^0-9]", "").equals("")) {
//            throw new IllegalArgumentException("Unit must contain numeric values.");
//        }
//        if (Integer.parseInt(unit.replaceAll("[^0-9]", "")) <= 1e-10)  {
//            throw new IllegalArgumentException("Unit must be non-zero");
//        }
        this.category = category;
    }

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

    /**
     *
     * @return
     */
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
     */
    public String getBestBeforeFormatted() {
        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");
        return simpleDate.format(getBestBefore());
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
     *
     * @param a
     * @return
     */
    public boolean equals(Object o){
        Ingredient ing = (Ingredient) o;
        if (ing.getDescription() == ing.description){
            if (ing.getCategory()==ing.category){
                if (ing.getLocation() == ing.location){
                    if (ing.getBestBefore().equals(bestBefore)){
                        if (ing.getAmount()==ing.amount){
                            if (ing.getUnit()==ing.unit){
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
