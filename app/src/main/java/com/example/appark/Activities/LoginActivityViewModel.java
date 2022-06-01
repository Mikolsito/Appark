package com.example.appark.Activities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appark.Activities.src.DatabaseAdapter;
import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.User;
import com.example.appark.Activities.src.vmInterface;

public class LoginActivityViewModel extends AndroidViewModel implements vmInterface {

    private final MutableLiveData<User> mUser;
    DatabaseAdapter adapter;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        adapter = new DatabaseAdapter(this);
        mUser = new MutableLiveData<>();
    }

    public LiveData<User> getUser(){
        return mUser;
    }

    public void getRegisteredUserDB(String mail) {
        /*if(adapter.getUser(mail)){
            User user = MainActivity.currentUser;
            return user.getMail().equals(mail) && user.getPwd().equals(pwd);
        }*/
        adapter.getUser(mail);
    }

    @Override
    public void getInfoUser(User us) {
        mUser.setValue(us);
    }
}