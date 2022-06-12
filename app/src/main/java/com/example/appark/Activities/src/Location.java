package com.example.appark.Activities.src;

import com.example.appark.Activities.PaginaPrincipalAdapter;
import com.google.android.gms.maps.model.LatLng;

public class Location {
    private String nom;
    private LatLng ubi;
    private String barri;
    private int placesLliures;
    private int places;
    private final PaginaPrincipalAdapter adapter = PaginaPrincipalAdapter.databaseAdapter;

    public Location(String nom, double lat, double lon, int placesTotals, int placesLliures, String barri) {
        this.nom = nom;
        ubi = new LatLng(lat, lon);
        places = placesTotals;
        this.placesLliures = placesLliures;
        this.barri = barri;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLatitude(double lat) {
        ubi = new LatLng(lat, ubi.longitude);
    }

    public void setLongitude(double lon) {
        ubi = new LatLng(ubi.latitude, lon);
    }

    public void setLatLon(LatLng l) {
        ubi = l;
    }

    public void updatePlacesLliures(int n) {
        placesLliures = n;
    }

    public void updatePlaces(int n) {
        places = n;
    }

    public String getNom() {
        return nom;
    }

    public double getLatitude() {
        return ubi.latitude;
    }

    public double getLongitude() {
        return ubi.longitude;
    }

    public LatLng getLatLng() {
        return ubi;
    }

    public int getPlacesLliures() {
        return placesLliures;
    }

    public int getPlaces() {
        return places;
    }

    public String getBarri(){
        return barri;
    }

}