package com.example.appark.Activities;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appark.Activities.src.DatabaseAdapter;
import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.User;
import com.example.appark.Activities.src.vmInterface;

public class MainActivityViewModel extends AndroidViewModel implements vmInterface {

    private final MutableLiveData<User> mUser;

    public MainActivityViewModel(Application application) {
        super(application);
        mUser = new MutableLiveData<>();
        DatabaseAdapter db = new DatabaseAdapter(this);
        db.getUser(MainActivity.currentUser.getMail());
    }

    //public getter. Not mutable , read-only
    public LiveData<User> getUser(){
        return mUser;
    }

    @Override
    public void getInfoUser(User us) {
        mUser.setValue(us);
    }

    @Override
    public void getInfoLocation(Location loc) {

    }
}
