package com.example.appark.Activities.src;

import android.util.Log;

import java.util.UUID;

public class User {
    private String userId;
    private String name;
    private String mail;
    private String pwd;

    public static User currentUser;

    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public User(String name, String mail, String pwd){
        this.name = name;
        this.mail = mail;
        this.pwd = pwd;

        UUID uuid = UUID.randomUUID();
        this.userId = uuid.toString();
        currentUser.userId = "user2";
        currentUser.name = "Blanca";
        currentUser.mail = "blanca@gmail.com";
        currentUser.pwd = "pwd2";
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
