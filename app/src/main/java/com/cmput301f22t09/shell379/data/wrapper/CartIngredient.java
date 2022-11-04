package com.cmput301f22t09.shell379.data.wrapper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.cmput301f22t09.shell379.data.Ingredient;

import java.io.Serializable;
/**
 * Ingredient that resides in the cart.
 */
public class CartIngredient extends Ingredient implements Serializable {
    private Boolean isPickedUp;
    private Boolean detailsFilled;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CartIngredient(Ingredient ingredient) {
        super(ingredient.getDescription(), ingredient.getBestBefore(), ingredient.getLocation(), ingredient.getAmount(), ingredient.getUnit(), ingredient.getCategory());
        this.isPickedUp = false;
        this.detailsFilled = false;
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
}
