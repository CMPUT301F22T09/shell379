package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.fragmentadapters.CategoriesSelectRecViewAdapter;

import java.util.HashSet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesSelect extends Fragment {

    public CategoriesSelect() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categories_select, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.categories_list);

        // Initialize contacts
        HashSet<String> hashset = new HashSet<String>();
        hashset.add("Ajslkfjslkfj");
        hashset.add("Bjslkfjslkfj");
        hashset.add("Cjslkfjslkfj");
        hashset.add("Djslkfjslkfj");
        hashset.add("Ejslkfjslkfj");
        hashset.add("Fjslkfjslkfj");

        // Create adapter passing in the sample user data
        CategoriesSelectRecViewAdapter adapter = new CategoriesSelectRecViewAdapter(hashset);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Inflate the layout for this fragment
        return view;
    }

    @NonNull
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_categories_select, null);



    }
}