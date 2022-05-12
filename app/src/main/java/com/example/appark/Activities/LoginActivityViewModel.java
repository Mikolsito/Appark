package com.example.appark.Activities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appark.Activities.src.DatabaseAdapter;
import com.example.appark.Activities.src.User;
import com.example.appark.Activities.src.vmInterface;

public class LoginActivityViewModel extends AndroidViewModel implements vmInterface {
    DatabaseAdapter db;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        db = new DatabaseAdapter(this);
    }

    public boolean getRegisteredUserDB(String mail, String pwd) {
        if(db.searchUserDB(mail)){
            User user = MainActivity.currentUser;
            return user.getMail().equals(mail) && user.getPwd().equals(pwd);
        }
        return false;

    }

    @Override
    public void getInfoUser(User user) {
        MainActivity.currentUser = user;
    }
}