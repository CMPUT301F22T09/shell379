package com.cmput301f22t09.shell379.data.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cmput301f22t09.shell379.data.vm.Environment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class DatabaseManager {
    private FirebaseFirestore db;
    private DocumentReference doc;
    private Environment instance;

    public DatabaseManager(Context context) {
        // SharedPreferences resource used:
        // https://guides.codepath.com/android/Storing-and-Accessing-SharedPreferences
        SharedPreferences pref = context.getSharedPreferences("prefs", context.MODE_PRIVATE);
        Boolean initialized = pref.getBoolean("db_init", false);

        if (!initialized) {
            FirebaseApp.initializeApp(context);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("db_init", true);
        }

        Log.e("SHELL379", initialized.toString());

        db = FirebaseFirestore.getInstance();
        // unique device identifier:
        // https://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id
        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        doc = db.collection("env").document(id);
    }

    public void pull() {
        this.doc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                this = value.getData().get();
                if (value != null && value.getData() != null && value.getData().get("bytes") != null) {
                    byte[] bytes = (byte[]) value.getData().get("bytes");
                    instance = (Environment) SerializeUtil.deserialize(bytes);
                }
            }
        });
    }

    public void push(Environment env) {
        HashMap<String, byte[]> data = new HashMap<>();
        data.put("bytes", SerializeUtil.serialize(env));
        doc.set(data);
    }

    public Environment getInstance() {
        return instance;
    }
}
