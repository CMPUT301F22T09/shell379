package com.cmput301f22t09.shell379.adapters.fragmentadapters;

import android.content.Context;
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

public class CategoriesSelectRecViewAdapter extends RecyclerView.Adapter<CategoriesSelectRecViewAdapter.ViewHolder> {

    private HashSet<String> cats;


    public CategoriesSelectRecViewAdapter(HashSet<String> cats) {
        this.cats = new HashSet<String>(cats);
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
        String category = this.getSortedArrayList().get(position);
        TextView catText = holder.catView;
        catText.setText(category);


    }

    @Override
    public int getItemCount() {
        return this.cats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView catView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            catView = (TextView) view.findViewById(R.id.cat_name);
        }

        public TextView getTextView() {
            return catView;
        }
    }

    public ArrayList<String> getSortedArrayList() {
        ArrayList<String> sortedList = new ArrayList<String>(this.cats);
        Collections.sort(sortedList);
        return sortedList;

    }

}
