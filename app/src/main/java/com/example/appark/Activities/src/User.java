package com.example.appark.Activities.src;

import android.util.Log;

import com.example.appark.Activities.MainActivity;

import java.util.UUID;

public class User {
    private String userId;
    private String name;
    private String mail;
    private String pwd;


    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public User(String name, String mail, String pwd){
        this.name = name;
        this.mail = mail;
        this.pwd = pwd;

        UUID uuid = UUID.randomUUID();
        this.userId = uuid.toString();

        MainActivity.currentUser = this; //el currentUser es el usuario que se ha creado desde loggin

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
    public String getUserId() {
        return userId;
    }


    public void updateUser(String oldPwd, String newPwd, String oldMail, String newMail){
        if (oldMail.equals(this.mail)){
            mail = newMail;
        }
        if (oldPwd.equals(this.pwd)){
            pwd = newPwd;
        }
        adapter.updateUser();
    }
}
