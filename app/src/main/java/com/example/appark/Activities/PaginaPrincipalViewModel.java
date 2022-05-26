package com.example.appark.Activities;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.vmInterfaceUbicacio;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class PaginaPrincipalViewModel extends AndroidViewModel implements vmInterfaceUbicacio {

    private MutableLiveData<HashMap<LatLng, Location>> mUbicacions;
    private final MutableLiveData<String> mToast;

    public static final String TAG = "ViewModel";

    //Constructor
    public PaginaPrincipalViewModel(Application application){
        super(application);

        mUbicacions = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        PaginaPrincipalAdapter da = new PaginaPrincipalAdapter(this);
        da.getCollection();
    }

    //public getter. Not mutable , read-only
    public LiveData<HashMap<LatLng, Location>> getUbicacions(){
        return mUbicacions;
    }

    public Location getUbicacio(LatLng idx){
        return mUbicacions.getValue().get(idx);
    }

    public void addUbicacio(String nom, double lat, double lon, int placesTotals, int placesLliures, String barri){
        Location u = new Location(nom, lat, lon, placesTotals, placesLliures, barri);
        if (u != null) {
            mUbicacions.getValue().put(u.getLatLng(), u);
            // Inform observer.
            mUbicacions.setValue(mUbicacions.getValue());
            u.saveUbi();
        }
    }

    public LiveData<String> getToast(){
        return mToast;
    }

    //communicates user inputs and updates the result in the viewModel
    @Override
    public void setCollection(HashMap<LatLng, Location> map) {
        mUbicacions.setValue(map);
    }
}
