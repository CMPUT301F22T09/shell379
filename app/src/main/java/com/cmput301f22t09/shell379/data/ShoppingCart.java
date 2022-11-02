package com.cmput301f22t09.shell379.data;

import androidx.lifecycle.MutableLiveData;

import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredientWrapper;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingCart extends LiveCollection<CartIngredientWrapper> implements Serializable {
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