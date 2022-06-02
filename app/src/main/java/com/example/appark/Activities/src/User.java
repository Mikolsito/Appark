package com.example.appark.Activities.src;

import android.util.Log;

import com.example.appark.Activities.MainActivity;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class User {
    private String name;
    private String mail;
    private String pwd;
    private ArrayList<Estacionament> estacionaments;      //Aixo es nou


    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public User(String name, String mail, String pwd){
        this.name = name;
        this.mail = mail;
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }
    public String getMail() {
        return mail;
    }
    public String getPwd() {
        return pwd;
    }


    public boolean updateUser(String oldPwd, String newPwd, String oldMail, String newMail){
        if (oldMail.equals(this.mail) && oldPwd.equals(this.pwd)){
            this.mail = newMail;
            this.pwd = newPwd;
            adapter.updateUser(name, oldMail, newMail, newPwd);
            return true;
        }
        return false;
    }

    public void saveUser() {
        adapter.saveUser(this.name, this.mail, this.pwd);
    }

    public void setUser(String name, String mail, String pwd) {
        this.name = name;
        this.mail = mail;
        this.pwd = pwd;
    }
}
