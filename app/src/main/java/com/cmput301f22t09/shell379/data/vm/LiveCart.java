package com.cmput301f22t09.shell379.data.vm;

import androidx.lifecycle.MutableLiveData;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.ShoppingCart;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.util.ArrayList;

public class LiveCart extends Commitable {
    private final MutableLiveData<ShoppingCart> cart = new MutableLiveData<>();

    public LiveCart() {
        cart.setValue(new ShoppingCart());
    }

    public MutableLiveData<ShoppingCart> getIngredients() {
        return cart;
    }

    public void commit(ShoppingCart cart) {
        this.cart.setValue(cart);
        readyForCommit();
    }
}