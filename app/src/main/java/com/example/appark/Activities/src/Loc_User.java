package com.example.appark.Activities.src;

import java.time.LocalDateTime;

public class Loc_User {
    private Integer idLoc;
    private Integer idUser;
    private double tempsAparcat;

    public Loc_User(Integer idLoc, Integer idUser, double time){
        this.idLoc = idLoc;
        this.idUser = idUser;
        this.tempsAparcat = time;
    }

    public Integer getIdLoc() {
        return idLoc;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public double getTempsAparcat() {
        return tempsAparcat;
    }
}
