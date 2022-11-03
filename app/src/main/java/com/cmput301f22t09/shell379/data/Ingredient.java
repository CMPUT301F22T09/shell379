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
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
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
     *
     * @param bestBefore
     */
    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    /**
     *
     * @return
     */
    public String getBestBeforeFormatted() {
        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");
        return simpleDate.format(getBestBefore());
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     */
    public String getUnit() {
        return unit;
    }

    /**
     *
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     *
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     *
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
    public boolean getFullEqual(Ingredient a){
        if (a.getDescription() == a.description){
            if (a.getCategory()==a.category){
                if (a.getLocation() == a.location){
                    if (a.getBestBefore().equals(bestBefore)){
                        if (a.getAmount()==a.amount){
                            if (a.getUnit()==a.unit){
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
