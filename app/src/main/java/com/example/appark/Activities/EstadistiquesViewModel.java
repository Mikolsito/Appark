package com.example.appark.Activities;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appark.Activities.src.DatabaseAdapter;
import com.example.appark.Activities.src.EstacioEst;
import com.example.appark.Activities.src.Estacionament;
import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.User;

import com.example.appark.Activities.src.vmInterface;
import com.example.appark.Activities.src.vmInterfaceEstacionaments;
import com.example.appark.Activities.src.vmInterfaceUbicacio;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class EstadistiquesViewModel extends AndroidViewModel implements vmInterfaceEstacionaments {

    private final MutableLiveData<ArrayList<Pair<String, Long>>> mplacesbarris;
    private MutableLiveData<ArrayList<Location>> mUbicacions;
    private MutableLiveData<ArrayList<EstacioEst>> mEstacionaments;

    public static final String TAG = "ViewModel";

    public EstadistiquesViewModel(@NonNull Application application) {
        super(application);
        mUbicacions = new MutableLiveData<>();
        mEstacionaments=new MutableLiveData<>();
        //PaginaPrincipalAdapter da = new PaginaPrincipalAdapter(this);
        EstacionamentsAdapter da=new EstacionamentsAdapter(this);
        da.getCollection();
        da.getCollection2();
        mplacesbarris=new MutableLiveData<>();
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<Location>> getUbicacions(){
        return mUbicacions;
    }
    public LiveData<ArrayList<EstacioEst>> getEstacionaments(){return mEstacionaments;}
    public Location getUbicacio(LatLng idx) throws Exception {
        for (Location l : mUbicacions.getValue()) {
            if (l.getLatLng() == idx) {
                return l;
            }
        }
        throw new Exception("No s'ha trobat la ubicació");
    }


    public LiveData<ArrayList<Pair<String, Long>>> getPlacesBarrisDB(){
        return mplacesbarris;
    }

    @Override
    public void updateCollection2(ArrayList<EstacioEst> e) {
        mEstacionaments.setValue(e);
    }

    //communicates user inputs and updates the result in the viewModel
    @Override
    public void updateCollection(ArrayList<Location> l) {
        mUbicacions.setValue(l);
    }
}