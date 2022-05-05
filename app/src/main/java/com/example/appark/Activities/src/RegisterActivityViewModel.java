package com.example.appark.Activities.src;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appark.Activities.MainActivity;

public class RegisterActivityViewModel extends AndroidViewModel implements vmInterface {
    DatabaseAdapter db;

    public RegisterActivityViewModel(@NonNull Application application) {
        super(application);
        db = new DatabaseAdapter(this);
    }

    public void insertUserDB(String name, String mail, String pwd) {
        User user = new User(name, mail, pwd);
        user.saveUser();
        MainActivity.currentUser = user; //el currentUser es el usuario que se ha creado desde loggin
    }

    @Override
    public void setUser(User user) {

    }
}
