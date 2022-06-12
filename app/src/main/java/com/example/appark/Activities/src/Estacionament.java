package com.example.appark.Activities.src;

import java.util.Date;

public class Estacionament {
    private Date dataInici;
    private Date dataFinal;
    private String userEmail;
    private String idUbicacio;
    private float tempsAparcat;

    public Estacionament(Date dataInici, String idUbicacio) {    //No afegim el tempsAparcat com a paràmetre
                                                            //ja que només sabem el temps un cop marxem
        this.dataInici = dataInici;
        this.idUbicacio = idUbicacio;
        tempsAparcat = 0;
    }

    public void setTempsAparcat(float temps) {
        tempsAparcat = temps;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public float getTempsAparcat() {
        return tempsAparcat;
    }

    public Date getDataInici() {
        return dataInici;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }



    public String getIdUbicacio() {
        return idUbicacio;
    }

}
