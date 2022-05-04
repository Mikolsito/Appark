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

        currentUser = this;
    }

    public String getName() {
        return name;
    }
    public String getMail() {
        return mail;
    }

    public void setUser(String name, String mail, String pwd){
        this.name = name;
        this.mail = mail;
        this.pwd = pwd;
    }
}
