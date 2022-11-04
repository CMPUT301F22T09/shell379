package com.cmput301f22t09.shell379.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.fragmentadapters.CategoriesSelectRecViewAdapter;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.CategorySet;

import java.util.HashSet;


public abstract class CategorySelectPopup extends DialogFragment {
    public interface SelectListener {
        void send(String val);
    }
    private  SelectListener csl;
    private   String title;

    public CategorySelectPopup() {
        // Required empty public constructor
    }

    public CategorySelectPopup(SelectListener listener,String title) {
        this.title = title;
        csl  = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categories_select, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.categories_list);
        EditText enterCat = view.findViewById(R.id.textInputEditText);
        Button addCat = view.findViewById(R.id.addButton);

        // Initialize contacts

        Environment env = Environment.of((AppCompatActivity) getActivity());
        CategorySet categorySet = getCollection(env);
        HashSet<String> hashset = categorySet.getCategories();

        ((TextView)view.findViewById(R.id.title_text)).setText(title);


        // Create adapter passing in the sample user data
        CategoriesSelectRecViewAdapter adapter = new CategoriesSelectRecViewAdapter(hashset);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener(new CategoriesSelectRecViewAdapter.ClickListener(){

            @Override
            public void onItemClick(int position, View v) {
                Log.e("CatSelect", String.valueOf(position));
                csl.send(adapter.get(position));
                dismiss();
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.e("CatSelect", String.valueOf(position));
                dismiss();
            }
        });

        addCat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String entered = enterCat.getText().toString();
                if (!hashset.contains(entered)) {
                    hashset.add(enterCat.getText().toString());
                    categorySet.addCategory(enterCat.getText().toString());
                    csl.send(enterCat.getText().toString());
                    categorySet.commit();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), entered + " already exists", Toast.LENGTH_LONG).show();
                    csl.send(entered);
                    dismiss();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @NonNull
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_categories_select, null);
    }

    protected abstract CategorySet getCollection(Environment env);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}