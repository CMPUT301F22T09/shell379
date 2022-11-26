package com.cmput301f22t09.shell379.data.vm.collections;

import androidx.lifecycle.MutableLiveData;

import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Collection of T entity objects stored in a MutableLiveData object.
 */
public class ShoppingCart extends LiveCollection<CartIngredient> {
    public ShoppingCart() {
        super();
    }

    public void addToCart(CartIngredient ingredient) {
        ArrayList<CartIngredient> cartLst = this.lst.getValue();
        Optional<CartIngredient> existingIngredient = cartLst.stream()
                .filter(e-> e.getDescription().equals(ingredient.getDescription()))
                .findFirst();
        existingIngredient
                .ifPresent(i->i.setAmount(i.getAmount()+ ingredient.getAmount()));
        if (!existingIngredient.isPresent())
            this.add(ingredient);
    }
}
