package com.example.appark.Activities.src;

import com.example.appark.Activities.MainActivity;

import java.util.Date;

public class Estacionament {
    private Date dataInici;
    private Date dataFinal;
    private User user;
    private Location ubicacio;

    public Estacionament(Location ubicacio) {
        this.dataInici = new Date();
        this.ubicacio = ubicacio;
        this.user = MainActivity.currentUser;
    }

    public void setDataFinal() {
        this.dataFinal = new Date();
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

    public User getUser() {
        return user;
    }

    public float getTempsAparcat(){
        float temps = ((dataFinal.getTime()-dataInici.getTime())/1000*60*60)%24;
        return temps; //devuelve el tiempo en horas
    }
}
