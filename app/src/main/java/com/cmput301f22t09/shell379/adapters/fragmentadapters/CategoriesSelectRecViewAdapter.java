package com.cmput301f22t09.shell379.adapters.fragmentadapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
/**
 * Adapter for choosing a category in the select category popup
 */
public class CategoriesSelectRecViewAdapter extends RecyclerView.Adapter<CategoriesSelectRecViewAdapter.ViewHolder> {
    private ArrayList<String> cats;
    private static ClickListener clickListener;

    public CategoriesSelectRecViewAdapter(HashSet<String> cats) {
        this.cats = new ArrayList<String>(cats);
        Collections.sort(this.cats);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View categoryContent = inflater.inflate(R.layout.category_content, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(categoryContent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String category = this.cats.get(position);
        TextView catText = holder.catView;
        catText.setText(category);
    }

    @Override
    public int getItemCount() {
        return this.cats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private final TextView catView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            catView = (TextView) view.findViewById(R.id.cat_name);
        }

        public TextView getTextView() {
            return catView;
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;
        }
    }

    /**
     * interface for functions that will trigger on item clicks
     */
    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        CategoriesSelectRecViewAdapter.clickListener = clickListener;
    }

    public static String getCategory(String cat) {
        return cat;
    }

    public String get(int position) {
        return this.cats.get(position);
    }
}
