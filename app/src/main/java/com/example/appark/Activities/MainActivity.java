package com.example.appark.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.util.Log;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    public static User currentUser = new User("dummyCurrentUser", "dummy@gmail.com", "dummyPwd");
    public static NavController navController;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public Snackbar arrived;
    private CircleImageView profileImg;
    public Uri profImgUri;


    private MainActivityViewModel mainActVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLiveDataObservers(); //Inicializa los observers de esta Activity
        ActionBar actionBar = getSupportActionBar();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_paginaprincipal,
                R.id.nav_configuracio, R.id.nav_historialubis, R.id.nav_estadistiques, R.id.nav_tancasessio)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Encontramos la imagen de perfil y a??adimos onClickListener para escoger foto de la galeria
        View headerView = navigationView.getHeaderView(0);
        profileImg = headerView.findViewById(R.id.profileImage);
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

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

    private void setLiveDataObservers() {
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

                if(us.getUrl() != null){
                    Picasso.with(getApplicationContext()).load(us.getUrl()).into(profileImg);
                }
            }
        };
        mainActVM.getUser().observe(this, observer);
    }

    private void choosePicture(){
        Log.d(TAG, "choosePicture");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1); //aqui llamaremos a onActivityResult
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode==RESULT_OK && data != null && data.getData() != null){
            profImgUri = data.getData();
            profileImg.setImageURI(profImgUri);
        }
        if (profImgUri != null) {
            mainActVM.uploadProfileImage(profImgUri);
        }
    }
}