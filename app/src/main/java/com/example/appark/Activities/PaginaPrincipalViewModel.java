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

    public static final String TAG = "ViewModel";

    //Constructor
    public PaginaPrincipalViewModel(Application application){
        super(application);

        mUbicacions = new MutableLiveData<>();
        PaginaPrincipalAdapter da = new PaginaPrincipalAdapter(this);
        da.getCollection();
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<Location>> getUbicacions(){
        return mUbicacions;
    }

    public Location getUbicacio(LatLng idx) throws Exception {
        for (Location l : mUbicacions.getValue()) {
            if (l.getLatLng() == idx) {
                return l;
            }
        }
        throw new Exception("No s'ha trobat la ubicació");
    }

    //communicates user inputs and updates the result in the viewModel
    @Override
    public void updateCollection(ArrayList<Location> l) {
        mUbicacions.setValue(l);
    }
}
