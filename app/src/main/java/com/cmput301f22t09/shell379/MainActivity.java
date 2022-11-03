package com.cmput301f22t09.shell379;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;

import com.cmput301f22t09.shell379.data.util.DatabaseManager;
import com.cmput301f22t09.shell379.data.vm.Environment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseManager dbm = new DatabaseManager(this);
        dbm.pull(this);
    }
}