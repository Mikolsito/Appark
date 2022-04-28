package com.example.appark.Activities.src;

public class User {
    private Integer id;
    private String name;
    private String mail;
    private char[] pwd;

    public User(String name, String mail, char[] pwd){
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.pwd = pwd;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPwd(char[] pwd) {
        this.pwd = pwd;
    }
}
