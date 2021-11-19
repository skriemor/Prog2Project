package com.example.prog2project;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends FragmentActivity /*AppCompatActivity */{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavController navController = Navigation.findNavController(findViewById(R.id.nav_host_fragment));
        NavigationUI.setupWithNavController(bottomNavigationView,navController);





    }




}