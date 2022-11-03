package com.cmput301f22t09.shell379.adapters;

import android.graphics.Color;
import android.os.Build;
//import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>  {
    public interface AdaptorListener{
        public void navigateToViewIngredient(int index);
    }

    private ArrayList<Ingredient> ingredients;
    private Environment envViewModel;
    private AdaptorListener ingredientListener;

    /**
     *
     */
    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        public View getItemView(){
            return itemView;
        }

        TextView ingredientName;
        TextView serving;
        TextView bestBefore;
        TextView location;
        TextView unit;
        TextView amount;
        TextView category;

        /**
         *
         * @param itemView
         */
        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ingredientName = (TextView) itemView.findViewById(R.id.ingredient_name);
            this.bestBefore = (TextView) itemView.findViewById(R.id.best_before_date_textView);
            this.category = (TextView) itemView.findViewById(R.id.category_textView);
            this.location = (TextView) itemView.findViewById(R.id.location_textView);

        }
    }

    /**
     * Construct the IngredientAdapter class
     * @param data
     * @param envViewModel
     * @param ingredientListener
     */
    public IngredientAdapter(ArrayList<Ingredient> data, Environment envViewModel, AdaptorListener ingredientListener){
        this.envViewModel = envViewModel;
        this.ingredients = data;
        this.ingredientListener = ingredientListener;
    }

//    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.ingredients_in_recipe_9, parent, false);
//        IngredientAdapter.IngredientViewHolder ingredientViewHolder = new IngredientAdapter.IngredientViewHolder(view);
//        return ingredientViewHolder;
//    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_content, parent, false);
        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(view);
        return ingredientViewHolder;
    }

    /**
     *
     * @param holder
     * @param position
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientViewHolder holder, int position) {
        TextView ingredientName = holder.ingredientName;
        TextView bestBefore = holder.bestBefore;
        TextView location = holder.location;
        TextView category = holder.category;

//        TextView serving = holder.serving;
//        TextView unit = holder.unit;
//        TextView amount = holder.amount;


        ingredientName.setText(ingredients.get(position).getDescription());
        bestBefore.setText(ingredients.get(position).getBestBeforeFormatted());
        category.setText(ingredients.get(position).getCategory());
        location.setText(ingredients.get(position).getLocation());
//        serving.setText(ingredients.get(position).getAmount().toString());
//        unit.setText(ingredients.get(position).getUnit());
//        amount.setText(ingredients.get(position).getAmount().toString());
//        holder.bind(position,this);

        View itemView = holder.getItemView();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientOnClick(holder.getAdapterPosition());
            }
        });
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    /**
     *
     * @param newIngredient
     */
    public void updateIngredient(ArrayList<Ingredient> newIngredient){
        ingredients = newIngredient;
        notifyDataSetChanged();
    }

    /**
     *
     * @param i
     */
    public void ingredientOnClick(int i) {
       Ingredient a = ingredients.get(i);
       LiveCollection<Ingredient> ingredientCollection = envViewModel.getIngredients();
       ingredientCollection.getIndexByFullEquals(a);
       ingredientListener.navigateToViewIngredient(i);
    }
}


