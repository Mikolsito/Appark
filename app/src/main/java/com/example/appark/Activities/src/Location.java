package com.example.appark.Activities.src;

import com.google.firebase.firestore.GeoPoint;

public class Location {
    private String id;
    private GeoPoint position;
    private User user;
    private String barri;
    private int places;
    private int placeslliures;

    private boolean isOccupied;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;
    public Location(String id, User user, GeoPoint position, String barri, int places, int placeslliures) {
        this.id = id;
        this.user=user;
        this.position=position;
        this.barri=barri;
        this.places=places;
        this.placeslliures=placeslliures;

    }

    public GeoPoint getPosition() {
        return position;
    }

    public String getId() {
        return id;
    }
    public User getUser(){return user;}
    public String getBarri(){return barri;}
    public int getPlaces(){return places;}
    public int getPlaceslliures(){return placeslliures;}

    public void savePosition() {
        adapter.savePosition(this);
    }

    public void occupy(){
        isOccupied = true;
    }

    public void release(){
        isOccupied = false;
    }
}
