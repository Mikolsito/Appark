package com.example.appark.Activities.src;

import android.util.Log;

import com.example.appark.Activities.MainActivity;

import java.util.UUID;

public class User {
    private String name;
    private String mail;
    private String pwd;


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


    public void updateUser(String oldPwd, String newPwd, String oldMail, String newMail){
        if (oldMail.equals(this.mail) && oldPwd.equals(this.pwd)){
            this.mail = newMail;
            this.pwd = newPwd;
            adapter.updateUser(name, newMail, newPwd);
        }



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
