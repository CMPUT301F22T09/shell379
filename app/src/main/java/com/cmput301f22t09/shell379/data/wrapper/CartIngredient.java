package com.cmput301f22t09.shell379.data.wrapper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.util.ArraySortUtil;

import java.io.Serializable;
import java.util.Objects;
import java.util.Arrays;
import java.util.List;

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

    private static List<String> sortOptions = Arrays.asList("Description","Category");

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

    public static CartIngredient convertIngredient(Ingredient ingredient) {
        return new CartIngredient(
                ingredient.getDescription(),
                ingredient.getCategory(),
                ingredient.getAmount(),
                ingredient.getUnit());
    }

    public static CartIngredient convertIngredientStub(IngredientStub ingredient) {
        return new CartIngredient(
                ingredient.getDescription(),
                ingredient.getCategory(),
                ingredient.getAmount(),
                ingredient.getUnit());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartIngredient)) return false;
        CartIngredient that = (CartIngredient) o;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPickedUp, detailsFilled, description);
    }
    
    /**
     * returns the proper way to get the property we want to sort on
     * based on what the user has selected as the sort.
     */
    public static ArraySortUtil.StringPropGetter getStringPropGetter(int selectedSortIndex){
        if( selectedSortIndex == 0){
            return new ArraySortUtil.StringPropGetter() {
                @Override
                public String getString(Object object) {
                    return ((CartIngredient)object).getDescription();
                }
            };
        }
        else if( selectedSortIndex == 1){
            return new ArraySortUtil.StringPropGetter() {
                @Override
                public String getString(Object object) {
                    return ((CartIngredient)object).getCategory();
                }
            };
        }
        else{
            throw new IllegalArgumentException( "No property to sort on is selected");
        }
    }

    /**
     *
     * @return a list of sortable properties. Each index of each option corresponds
     * to a StringPropGetter returned from the method "getStringPropGetter()"
     */
    public static List<String> getSortableProps(){
        return sortOptions;
    }
}
