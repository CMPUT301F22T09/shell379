package com.cmput301f22t09.shell379;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.Navigation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.cmput301f22t09.shell379.data.util.DatabaseManager;
import com.cmput301f22t09.shell379.data.vm.Environment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disabling night mode from ali Rezaiyan, July 24 2019
        // https://stackoverflow.com/questions/57175226/how-to-disable-night-mode-in-my-application-even-if-night-mode-is-enable-in-andr
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Disabling rotation from Pacific Regmi , January 10 2016
        // https://stackoverflow.com/questions/57175226/how-to-disable-night-mode-in-my-application-even-if-night-mode-is-enable-in-andr
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);
    }
}