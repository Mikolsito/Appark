package com.example.appark.Activities.src;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appark.Activities.MainActivity;

public class LoginActivityViewModel extends AndroidViewModel implements vmInterface {
    DatabaseAdapter db;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        db = new DatabaseAdapter(this);
    }

    public boolean searchUserDB(String mail, String pwd) {
        db.searchUser(mail);
        User user = MainActivity.currentUser;
        return user.getMail().equals(mail) && user.getPwd().equals(pwd);
    }

    @Override
    public void setUser(User user) {
        MainActivity.currentUser = user;
    }
}
