package com.example.appark.Activities.src;

import com.google.firebase.Timestamp;
import com.google.type.Date;

public class Estacionament {
    private Date data;
    private Date dataSortida;
    private Timestamp dataInici;
    private Timestamp dataFinal;
    private User user;
    private Location ubicacio;
    private double tempsAparcat;

    public Estacionament(Timestamp dataInici, Location ubicacio) {    //No afegim el tempsAparcat com a paràmetre
        //ja que només sabem el temps un cop marxem
        this.dataInici = dataInici;
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
