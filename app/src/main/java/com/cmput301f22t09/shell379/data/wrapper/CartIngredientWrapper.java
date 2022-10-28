package com.cmput301f22t09.shell379.data.wrapper;

import com.cmput301f22t09.shell379.data.Ingredient;

import java.io.Serializable;

public class CartIngredientWrapper implements Serializable {
    private Ingredient ingredient;
    private Boolean isPickedUp;
    private Boolean areDetailsFilled;

    public CartIngredientWrapper(Ingredient ingredient) {
        this.ingredient = ingredient;
        this.isPickedUp = false;
        this.areDetailsFilled = false;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Boolean getPickedUp() {
        return isPickedUp;
    }

    public Boolean getAreDetailsFilled() {
        return areDetailsFilled;
    }

    public void setPickedUp(Boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    public void setAreDetailsFilled(Boolean areDetailsFilled) {
        this.areDetailsFilled = areDetailsFilled;
    }
}
