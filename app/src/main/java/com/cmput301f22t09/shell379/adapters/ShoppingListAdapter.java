package com.cmput301f22t09.shell379.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.description = itemView.findViewById(R.id.shop_description);
            this.category = itemView.findViewById(R.id.shop_category);
            this.amount = itemView.findViewById(R.id.shop_amount);
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

        description.setText(shoppingList.getList().get(holder.getAdapterPosition()).getDescription());
        category.setText(shoppingList.getList().get(holder.getAdapterPosition()).getCategory());
        amount.setText(shoppingList.getList().get(holder.getAdapterPosition()).getAmount().toString() + " "
                       + shoppingList.getList().get(holder.getAdapterPosition()).getUnit());
    }

    @Override
    public int getItemCount() {
        return shoppingList.getList().size();
    }

}
