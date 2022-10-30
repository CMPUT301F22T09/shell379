package com.cmput301f22t09.shell379.data.vm;

import androidx.lifecycle.MutableLiveData;

import com.cmput301f22t09.shell379.data.ShoppingCart;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredientWrapper;

import java.io.Serializable;
import java.util.ArrayList;

public class LiveCart extends Commitable implements Serializable {
    private final MutableLiveData<ShoppingCart> cart = new MutableLiveData<>();

    public LiveCart() {
        cart.setValue(new ShoppingCart());
    }

    public MutableLiveData<ShoppingCart> getCartLive() {
        return cart;
    }

    public ShoppingCart getCart() {
        return cart.getValue();
    }

    public ArrayList<CartIngredientWrapper> getIngredients() {
        return cart.getValue().getIngredients();
    }

    public void setCart(ShoppingCart cart) {
        this.cart.setValue(cart);
    }
}