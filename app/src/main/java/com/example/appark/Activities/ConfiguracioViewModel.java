package com.example.appark.Activities;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.appark.Activities.src.DatabaseAdapter;
import com.example.appark.Activities.src.User;

public class ConfiguracioViewModel extends AndroidViewModel {

    //Constructor
    public ConfiguracioViewModel(Application application){
        super(application);
    }

    public void updateUser(String oldPwd, String newPwd, String oldMail, String newMail){
        User.currentUser.updateUser(oldPwd, newPwd, oldMail, newMail);

    }


}
