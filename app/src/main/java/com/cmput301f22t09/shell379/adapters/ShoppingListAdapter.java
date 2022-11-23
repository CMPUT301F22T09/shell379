package com.cmput301f22t09.shell379.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;
import com.cmput301f22t09.shell379.fragments.ShoppingListFragment;
import com.cmput301f22t09.shell379.fragments.ShoppingListFragmentDirections;

/**
 * This class is a custom RecyclerView adapter which helps display Shopping List data.
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private LiveCollection<CartIngredient> shoppingList;
    private ShoppingListFragment shoppingListFragment;
    private NavController navController;

    /**
     * Custom ViewHolder to describe each shopping list item's view.
     */
    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        TextView category;
        TextView amount_required;
        TextView amount_purchased_label;
        TextView amount_purchased;
        TextView detailsCompleteMsg;
        TextView fillOutDetailsMsg;
        CheckBox checkbox;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.description = itemView.findViewById(R.id.shop_description);
            this.category = itemView.findViewById(R.id.shop_category);
            this.amount_required = itemView.findViewById(R.id.shop_amount_req);
            this.amount_purchased_label = itemView.findViewById(R.id.shop_amount_pur_label);
            this.amount_purchased = itemView.findViewById(R.id.shop_amount_pur);
            this.detailsCompleteMsg = itemView.findViewById(R.id.shop_complete_msg);
            this.fillOutDetailsMsg = itemView.findViewById(R.id.shop_incomplete_msg);
            this.checkbox = itemView.findViewById(R.id.shop_checkBox);
        }

    }

    public ShoppingListAdapter(LiveCollection<CartIngredient> shoppingList, ShoppingListFragment shoppingListFragment) {
        this.shoppingList = shoppingList;
        this.shoppingListFragment = shoppingListFragment;
        this.navController = NavHostFragment.findNavController(shoppingListFragment);
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_content_20, parent, false);
        ShoppingListViewHolder shoppingListViewHolder = new ShoppingListViewHolder(view);
        return shoppingListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        TextView description = holder.description;
        TextView category = holder.category;
        TextView amount_required = holder.amount_required;
        TextView amount_purchased_label = holder.amount_purchased_label;
        TextView amount_purchased = holder.amount_purchased;
        TextView detailsCompleteMsg = holder.detailsCompleteMsg;
        TextView fillOutDetailsMsg = holder.fillOutDetailsMsg;
        CheckBox checkbox = holder.checkbox;

        CartIngredient cartIngredient = shoppingList.getList().get(holder.getAdapterPosition());

        description.setText(cartIngredient.getDescription());
        category.setText(cartIngredient.getCategory());
        amount_required.setText(cartIngredient.getAmount().toString() + " " + cartIngredient.getUnit());

        detailsCompleteMsg.setVisibility(View.GONE);
        fillOutDetailsMsg.setVisibility(View.GONE);
        amount_purchased_label.setVisibility(View.GONE);
        amount_purchased.setVisibility(View.GONE);

        if (cartIngredient.getIngredient() != null) {
            amount_purchased.setText(cartIngredient.getIngredient().getAmount().toString() + " " + cartIngredient.getUnit());
            amount_purchased_label.setVisibility(View.VISIBLE);
            amount_purchased.setVisibility(View.VISIBLE);
        }

        // TODO: need both an if/else and an onCheckListener

        if (cartIngredient.getPickedUp() && !cartIngredient.getDetailsFilled()) {
            detailsCompleteMsg.setVisibility(View.GONE);
            fillOutDetailsMsg.setVisibility(View.VISIBLE);
        }
        else if (cartIngredient.getPickedUp() && cartIngredient.getDetailsFilled()) {
            checkbox.setChecked(true);
            detailsCompleteMsg.setVisibility(View.VISIBLE);
            fillOutDetailsMsg.setVisibility(View.VISIBLE);
            fillOutDetailsMsg.setText("");
        }

        setItemOnClickListener(holder);

        setCheckBoxOnClickListener(holder, position);
    }

    @Override
    public int getItemCount() {
        return shoppingList.getList().size();
    }

    public void setItemOnClickListener(ShoppingListViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ingredientIndex = holder.getAdapterPosition();
                if (shoppingList.getList().get(ingredientIndex).getPickedUp() == true) {
                    Log.e("debug", "clicked ingredient in shopping list");
                    navController.navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToCheckoutIngredient(ingredientIndex));
                }
            }
        });
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
                    if (holder.fillOutDetailsMsg.getText().equals("")) {
                        holder.fillOutDetailsMsg.setText("Click on this card to fill out details");
                    }
                }
                // Checkbox is un-checked
                else {
                    // TODO: set isPickedUp to false, and hide both messages
                    shoppingList.getList().get(holder.getAdapterPosition()).setPickedUp(false);
                    shoppingList.getList().get(holder.getAdapterPosition()).setIngredient(null);
                    holder.fillOutDetailsMsg.setVisibility(View.GONE);
                    holder.detailsCompleteMsg.setVisibility(View.GONE);
                    holder.amount_purchased_label.setVisibility(View.GONE);
                    holder.amount_purchased.setVisibility(View.GONE);
                }
            }
        });
    }

}
