package com.example.appark.Activities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appark.Activities.src.DatabaseAdapter;
import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.User;

import com.example.appark.Activities.src.vmInterface;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*public class EstadistiquesViewModel extends ViewModel {
    public LiveData<Location> getLocation(){
        final MutableLiveData<Location> locationMutableLiveData=new MutableLiveData<>();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference docRef=db.collection("Locations").document();
    }
    private final MutableLiveData<Location> location;
    //Constructor
    public EstadistiquesViewModel(){

        location = new MutableLiveData<>();
        LocDatabaseAdapter da = new LocDatabaseAdapter(this);
        da.getLocation();
    }

    public void updateStatistics(String oldPwd, String newPwd, String oldMail, String newMail){
        MainActivity.currentUser.updateUser(oldPwd, newPwd, oldMail, newMail);

    }


    public void setLocation(Location lc) {
        location.setValue(lc);
    }
}

    */
public class EstadistiquesViewModel extends AndroidViewModel implements vmInterface {

    public EstadistiquesViewModel(@NonNull Application application) {
        super(application);
        DatabaseAdapter da = new DatabaseAdapter(this);
    }



    public void createPositionDB(String id, User us, double latitude, double longitude) {
        GeoPoint pos=new GeoPoint(latitude,longitude);
        Location location = new Location(id, us, pos, "Eixample", 23, 9);
        location.savePosition();
        //MainActivity.currentUser = user; //el currentUser es el usuario actual
    }

    public void getPlacesBarrisDB(){
        DatabaseAdapter da = new DatabaseAdapter(this);
        List<String> barris=new ArrayList<String>();
        barris.addAll(Arrays.asList("Eixample", "Sarrià", "Gracia", "Horta", "Sagrada Familia", "Sant Gervasi", "Poblenou", "Raval", "Sant Marti"));
        for (int i=0;i<8;i++) {
            String barri = barris.get(i);
            da.getPlacesBarri(barri);
        }


    }

    @Override
    public void setUser(User user) {

    }
}