package com.moworks.eqinfo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    NavController navController ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_EQInfo);
        setContentView(R.layout.activity_main);

        navController  = Navigation.findNavController(this , R.id.navHost);

        NavigationUI.setupActionBarWithNavController(MainActivity.this , navController );

    }


    @Override
    public boolean onSupportNavigateUp() {
        return  navController.navigateUp();
    }
}