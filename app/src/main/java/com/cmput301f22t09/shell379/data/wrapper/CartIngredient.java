package com.cmput301f22t09.shell379.data.wrapper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.cmput301f22t09.shell379.data.Ingredient;

import java.io.Serializable;
/**
 * Ingredient that resides in the cart.
 */
public class CartIngredient implements Serializable {
    private Boolean isPickedUp;
    private Boolean detailsFilled;
    private String category;
    private Integer amount;
    private Ingredient ingredient;
    private String description;
    private String unit;

    public CartIngredient(String description, String category, Integer amount, String unit) {
        this.isPickedUp = false;
        this.detailsFilled = false;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.unit = unit;
    }

    public Boolean getPickedUp() {
        return isPickedUp;
    }

    public Boolean getDetailsFilled() {
        return detailsFilled;
    }

    public void setPickedUp(Boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    public void setDetailsFilled(Boolean areDetailsFilled) {
        this.detailsFilled = areDetailsFilled;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        this.detailsFilled = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
