package com.example.appark.Activities.src;

public class Location {
    private Integer id;
    private double []position;
    private boolean isOccupied;

    public Location(int id, double latitude, double longitude) {
        this.id = id;
        position[0] = latitude;
        position[1] = longitude;
        isOccupied = false;
    }

    public double[] getPosition() {
        return position;
    }

    public Integer getId() {
        return id;
    }

    public void occupy(){
        isOccupied = true;
    }

    public void release(){
        isOccupied = false;
    }
}
