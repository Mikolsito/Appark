package com.example.appark.Activities.src;

import com.google.type.Date;

public class Estacionament {
    private Date data;
    private Location ubicacio;
    private double tempsAparcat;

    public Estacionament(Date data, Location ubicacio) {    //No afegim el tempsAparcat com a paràmetre
        //ja que només sabem el temps un cop marxem
        this.data = data;
        this.ubicacio = ubicacio;
        tempsAparcat = 0;
    }

    public void setTempsAparcat(double temps) {
        tempsAparcat = temps;
    }

    public Date getData() {
        return data;
    }

    public Location getUbicacio() {
        return ubicacio;
    }
}
