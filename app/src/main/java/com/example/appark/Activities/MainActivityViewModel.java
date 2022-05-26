package com.example.appark.Activities;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appark.Activities.src.DatabaseAdapter;
import com.example.appark.Activities.src.User;
import com.example.appark.Activities.src.vmInterface;

public class MainActivityViewModel extends AndroidViewModel implements vmInterface {

    private final MutableLiveData<User> mUser;
    private final MutableLiveData<String> mURL;

    public MainActivityViewModel(Application application) {
        super(application);
        mUser = new MutableLiveData<>();
        mURL = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
        da.getUser(MainActivity.currentUser.getMail());
    }

    //public getter. Not mutable , read-only
    public LiveData<User> getUser(){
        return mUser;
    }

    public LiveData<String> getURL(){
        return mURL;
    }

    @Override
    public void getInfoUser(User us) {
        mUser.setValue(us);
    }

    @Override
    public void getURLImage(String url) {
        mURL.setValue(url);
    }
}
