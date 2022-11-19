package com.cmput301f22t09.shell379.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.ShoppingCart;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;
import com.cmput301f22t09.shell379.fragments.ShoppingListFragment;

import java.util.ArrayList;

/**
 * This class is a custom RecyclerView adapter which helps display Shopping List data.
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private ShoppingCart shoppingList;
    private ShoppingListFragment shoppingListFragment;
    // private NavController navController;

    /**
     * Custom ViewHolder to describe each shopping list item's view.
     */
    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        TextView category;
        TextView amount;
        TextView detailsCompleteMsg;
        TextView fillOutDetailsMsg;
        CheckBox checkbox;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.description = itemView.findViewById(R.id.shop_description);
            this.category = itemView.findViewById(R.id.shop_category);
            this.amount = itemView.findViewById(R.id.shop_amount);
            this.detailsCompleteMsg = itemView.findViewById(R.id.shop_complete_msg);
            this.fillOutDetailsMsg = itemView.findViewById(R.id.shop_incomplete_msg);
            this.checkbox = itemView.findViewById(R.id.shop_checkBox);
        }

    }

    public ShoppingListAdapter(ShoppingCart shoppingList, ShoppingListFragment shoppingListFragment) {
        this.shoppingList = shoppingList;
        this.shoppingListFragment = shoppingListFragment;
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_content_22, parent, false);
        ShoppingListViewHolder shoppingListViewHolder = new ShoppingListViewHolder(view);
        return shoppingListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        TextView description = holder.description;
        TextView category = holder.category;
        TextView amount = holder.amount;
        TextView detailsCompleteMsg = holder.detailsCompleteMsg;
        TextView fillOutDetailsMsg = holder.fillOutDetailsMsg;
        CheckBox checkbox = holder.checkbox;

        CartIngredient cartIngredient = shoppingList.getList().get(holder.getAdapterPosition());

        description.setText(cartIngredient.getIngredient().getDescription());
        category.setText(cartIngredient.getCategory());
        amount.setText(cartIngredient.getAmount().toString() + " " + cartIngredient.getIngredient().getUnit());
        detailsCompleteMsg.setVisibility(View.GONE);
        fillOutDetailsMsg.setVisibility(View.GONE);

        // TODO: need both an if/else and an onCheckListener

        // TODO: create getter (and setter?) for CartIngredient isPickedUp and detailsFilled
        //***
         if (cartIngredient.getPickedUp() && !cartIngredient.getDetailsFilled()) {
             detailsCompleteMsg.setVisibility(View.GONE);
             fillOutDetailsMsg.setVisibility(View.VISIBLE);
         }
         else if (cartIngredient.getPickedUp() && cartIngredient.getDetailsFilled()) {
             detailsCompleteMsg.setVisibility(View.VISIBLE);
             fillOutDetailsMsg.setVisibility(View.GONE);
         }

        setCheckBoxOnClickListener(holder, position);
    }

    @Override
    public int getItemCount() {
        return shoppingList.getList().size();
    }

    public void setCheckBoxOnClickListener(ShoppingListViewHolder holder, int position) {
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // Checkbox is clicked
                if (b) {
                    // TODO: set isPickedUp to true, and display the incomplete message
                    shoppingList.getList().get(holder.getAdapterPosition()).setPickedUp(true);
                    holder.fillOutDetailsMsg.setVisibility(View.VISIBLE);
                    holder.detailsCompleteMsg.setVisibility(View.GONE);
                }
                // Checkbox is un-checked
                else {
                    // TODO: set isPickedUp to false, and hide both messages
                    shoppingList.getList().get(holder.getAdapterPosition()).setPickedUp(false);
                    holder.fillOutDetailsMsg.setVisibility(View.GONE);
                    holder.detailsCompleteMsg.setVisibility(View.GONE);
                }
            }
        });
    }

}
