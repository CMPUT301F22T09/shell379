package com.cmput301f22t09.shell379.data;

import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;

import java.io.Serializable;

public class ShoppingCart extends LiveCollection<CartIngredient> implements Serializable {
    private Integer activeDays;

    public ShoppingCart() {
        super();
        activeDays = 0;
    }

    public void setActiveDays(Integer activeDays) {
        this.activeDays = activeDays;
    }

    public Integer getActiveDays() {
        return activeDays;
    }
}