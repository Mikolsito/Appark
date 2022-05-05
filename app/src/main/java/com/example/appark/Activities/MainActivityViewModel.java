package com.example.appark.Activities;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appark.Activities.src.DatabaseAdapter;
import com.example.appark.Activities.src.User;
import com.example.appark.Activities.src.vmInterface;

public class MainActivityViewModel extends AndroidViewModel implements vmInterface {

    private final MutableLiveData<User> user;

    public MainActivityViewModel(Application application) {
        super(application);
        user = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
        da.getUser();

    }

    //public getter. Not mutable , read-only
    public LiveData<User> getUser(){
        return user;
    }

    @Override
    public void setUser(User us) {
        user.setValue(us);
    }
}
