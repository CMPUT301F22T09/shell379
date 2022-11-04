package com.cmput301f22t09.shell379.data.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.infrastructure.SerializeEnvUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.List;
/**
 * Connects to and interfaces with the firebase database
 */
public class DatabaseManager {
    private FirebaseFirestore db;
    private DocumentReference doc;
    private Environment instance;
    private MutableLiveData<Boolean> loaded = new MutableLiveData<Boolean>();

    public DatabaseManager(Context context) {
        loaded.setValue(false);

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
        doc = db.collection(id).document("ENV");
    }

    /**
     * Syncs local environment to the database
     * @param owner the owner of the environment view model scope.
     */
    public void pull(AppCompatActivity owner) {
        this.doc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                try {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("ingredients", (String) value.get("ingredients"));
                    data.put("recipes", (String) value.get("recipes"));
                    data.put("cart", (String) value.get("cart"));
                    data.put("ingredient_categories", (String) value.get("ingredient_categories"));
                    data.put("recipes_categories", (String) value.get("recipes_categories"));
                    data.put("loc_categories", (String) value.get("loc_categories"));
                    instance = SerializeEnvUtil.deserialize(data);
                    Environment.of(owner, instance);
                    loaded.setValue(true);
                }
                catch (NullPointerException e) {
                    instance = new Environment();
                    loaded.setValue(true);
                }
            }
        });
    }


    /**
     * Pushes local environment to the database
     * @param env the environment Environment to push to the database.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void push(Environment env) {
        doc.set(SerializeEnvUtil.serialize(env)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }

    public Environment getInstance() {
        return instance;
    }

    public MutableLiveData<Boolean> getLoaded() {
        return loaded;
    }
}
