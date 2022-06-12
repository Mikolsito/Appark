package com.example.appark.Activities;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.vmInterfaceUbicacio;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaginaPrincipalViewModel extends AndroidViewModel implements vmInterfaceUbicacio {

    private MutableLiveData<ArrayList<Location>> mUbicacions;
    private MutableLiveData<ArrayList<Integer>> mInfoPlaces;

    public static final String TAG = "ViewModel";
    PaginaPrincipalAdapter da;

    //Constructor
    public PaginaPrincipalViewModel(Application application){
        super(application);

        mUbicacions = new MutableLiveData<>();
        da = new PaginaPrincipalAdapter(this);
        da.getCollection();
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<Location>> getUbicacions(){
        return mUbicacions;
    }
    public LiveData<ArrayList<Integer>> getInfoPlaces(){
        return mInfoPlaces;
    }

    public Location getUbicacio(LatLng idx) throws Exception {
        for (Location l : mUbicacions.getValue()) {
            if (l.getLatLng() == idx) {
                return l;
            }
        }
        throw new Exception("No s'ha trobat la ubicació");
    }

    public void createEstacionament(String nom){
        da.createEstacionament(nom);

    }

    public void getInfoPlacesDB(String nom){
        da.getPlacesUbicacio(nom);
    }

    //communicates user inputs and updates the result in the viewModel
    @Override
    public void updateCollection(ArrayList<Location> l) {
        mUbicacions.setValue(l);
    }

    @Override
    public void getPlacesBD(ArrayList<Integer> places) {
        mInfoPlaces.setValue(places);
    }
}
