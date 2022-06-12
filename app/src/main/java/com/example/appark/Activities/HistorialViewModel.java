package com.example.appark.Activities;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appark.Activities.src.DatabaseAdapter;
import com.example.appark.Activities.src.Estacionament;
import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.vmInterfaceEstacionament;

import java.util.ArrayList;

public class HistorialViewModel extends AndroidViewModel implements vmInterfaceEstacionament {

    private MutableLiveData<ArrayList<Location>> mEstacionaments;
    HistorialDBAdapter da;

    public HistorialViewModel(Application application) {
        super(application);

        mEstacionaments = new MutableLiveData<>();
        da = new HistorialDBAdapter(this);
        da.getEstacionaments(MainActivity.currentUser.getMail());
    }

    @Override
    public void updateCollection(ArrayList<Location> l) {
        mEstacionaments.setValue(l);
    }

    public LiveData<ArrayList<Location>> getEstacionaments() {
        return mEstacionaments;
    }
}
