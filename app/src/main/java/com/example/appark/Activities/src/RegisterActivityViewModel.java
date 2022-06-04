package com.example.appark.Activities.src;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appark.Activities.MainActivity;

import java.util.ArrayList;

public class RegisterActivityViewModel extends AndroidViewModel implements vmInterface {

    public RegisterActivityViewModel(@NonNull Application application) {
        super(application);
        DatabaseAdapter da = new DatabaseAdapter(this);
    }

    public void createUserDB(String name, String mail, String pwd) {
        User user = new User(name, mail, pwd);
        user.saveUser();
        MainActivity.currentUser = user; //el currentUser es el usuario que se ha creado desde loggin
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public void setBarris(ArrayList<Pair<String, Long>> placesbarri) {

    }
}
