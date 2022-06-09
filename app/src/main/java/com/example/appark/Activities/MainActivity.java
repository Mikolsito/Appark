package com.example.appark.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.appark.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.appark.Activities.src.User;
import com.example.appark.R;
import com.example.appark.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static User currentUser = new User("dummyCurrentUser", "dummy@gmail.com", "dummyPwd");

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public Snackbar arrived;


    private MainActivityViewModel mainActVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLiveDataObservers(); //Inicializa los observers de esta Activity

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        Snackbar arrived = Snackbar.make(findViewById(R.id.app_bar_main),"Log-In Successful", Snackbar.LENGTH_SHORT);

        arrived.show();
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_paginaprincipal,
                R.id.nav_configuracio, R.id.nav_historialubis, R.id.nav_social, R.id.nav_estadistiques, R.id.nav_tancasessio)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        mainActVM = new ViewModelProvider(this).get(MainActivityViewModel.class);

        final Observer<User> observer = new Observer<User>() {
            @Override
            public void onChanged(User us) {
                NavigationView navigationView = findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView navUsername = headerView.findViewById(R.id.textViewUserName);
                TextView navUsermail = headerView.findViewById(R.id.textViewMail);

                navUsername.setText(us.getName());
                navUsermail.setText(us.getMail());
            }
        };

        mainActVM.getUser().observe(this, observer);
    }
}