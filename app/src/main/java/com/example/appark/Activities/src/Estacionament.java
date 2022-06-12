package com.example.appark.Activities.src;

import com.example.appark.Activities.MainActivity;

import java.util.Date;

public class Estacionament {
    private Date dataInici;
    private Date dataFinal;
    private String userEmail;
    private Location ubicacio;
    private float tempsAparcat;

    public Estacionament(Location ubicacio) {
        this.dataInici = new Date();
        this.ubicacio = ubicacio;
        this.userEmail = MainActivity.currentUser.getMail();
        this.tempsAparcat = 0;
    }

    public void setDataFinal() {
        this.dataFinal = new Date();
    }

    public void setDataFinal(Date d) {
        dataFinal = d;
    }

    public void setDataInici(Date d) {
        dataInici = d;
    }

    public Date getDataInici() {
        return dataInici;
    }

    public Date getDataFinal(){
        return dataFinal;
    }

    public Location getUbicacio() {
        return ubicacio;
    }

    public String getUser() {
        return userEmail;
    }

    public float getTempsAparcat() {
        return tempsAparcat;
    }

    public void setTempsAparcat(){
        this.tempsAparcat = ((dataFinal.getTime()-dataInici.getTime())/1000*60*60)%24;
    }
}
