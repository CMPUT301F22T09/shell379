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

    public CartIngredient(String category, Integer amount) {
        this.isPickedUp = false;
        this.detailsFilled = false;
        this.category = category;
        this.amount = amount;
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
}
