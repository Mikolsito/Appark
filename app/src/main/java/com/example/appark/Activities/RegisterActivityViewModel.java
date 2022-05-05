package com.example.appark.Activities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appark.Activities.MainActivity;
import com.example.appark.Activities.src.DatabaseAdapter;
import com.example.appark.Activities.src.User;
import com.example.appark.Activities.src.vmInterface;

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
    public void getInfoUser(User user) {

    }
}
