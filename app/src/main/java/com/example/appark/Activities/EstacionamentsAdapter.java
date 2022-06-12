package com.example.appark.Activities;

import android.app.Activity;
import android.util.Log;
import androidx.annotation.NonNull;

import com.example.appark.Activities.src.EstacioEst;
import com.example.appark.Activities.src.Estacionament;
import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.vmInterfaceEstacionaments;
import com.example.appark.Activities.src.vmInterfaceUbicacio;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class EstacionamentsAdapter extends Activity {
    public static final String TAG = "DatabaseAdapter";
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;

    public static vmInterfaceEstacionaments listener;
    public static EstacionamentsAdapter databaseAdapter;

    public EstacionamentsAdapter(vmInterfaceEstacionaments listener){
        this.listener = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }

    public void initFirebase(){

        user = mAuth.getCurrentUser();

        if (user == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInAnonymously:success");
                                //listener.setToast("Authentication successful.");
                                user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                                //listener.setToast("Authentication failed.");

                            }
                        }
                    });
        } /*else{
            listener.setToast("Authentication with current user.");

        }*/
    }

    public void getCollection(){
        Log.d(TAG,"getAllUbicacions");
        PaginaPrincipalAdapter.db.collection("Ubicacions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Location> ubicacions= new ArrayList<>();
                            Location ubicacio;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                double latitud = document.getGeoPoint("position").getLatitude();
                                double longitud = document.getGeoPoint("position").getLongitude();
                                String nom = document.getString("nom");
                                int places = document.getDouble("places").intValue();
                                int placesLliures = document.getDouble("placeslliures").intValue();
                                String barri = document.getString("barri");
                                ubicacio = new Location(nom, latitud, longitud, places, placesLliures, barri);
                                ubicacions.add(ubicacio);
                                /*
                                ubicacioHashMap.put(new com.google.android.gms.maps.model.LatLng(
                                        document.getGeoPoint("Ubicacio").getLatitude(), document.getGeoPoint("Ubicacio").getLongitude()),
                                        new Location(document.getString("Nom"),document.getGeoPoint("Ubicacio").getLatitude(),
                                                document.getGeoPoint("Ubicacio").getLongitude(), document.getDouble("PlacesTotals").intValue(),
                                                document.getDouble("PlacesLliures").intValue(), document.getString("Barri")));
                                */
                            }
                            listener.updateCollection(ubicacions);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getCollection2(){
        Log.d(TAG,"getAllAparcaments");
        PaginaPrincipalAdapter.db.collection("Estacionaments")//.whereEqualTo("User_mail", MainActivity.currentUser.getMail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<EstacioEst> aparcaments= new ArrayList<>();
                            EstacioEst aparcament;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //double latitud = document.getGeoPoint("position").getLatitude();
                                //double longitud = document.getGeoPoint("position").getLongitude();
                                String nom = document.getString("User_mail");
                                float temps = document.getDouble("tempsAparcat").floatValue();
                                //int placesLliures = document.getDouble("placeslliures").intValue();
                                //String barri = document.getString("barri");

                                Date data=new Date(document.getString("dataInici"));

                                aparcament = new EstacioEst(data, nom);
                                aparcament.setTempsAparcat(temps);
                                aparcament.setUserEmail(nom);
                                aparcaments.add(aparcament);
                                /*
                                ubicacioHashMap.put(new com.google.android.gms.maps.model.LatLng(
                                        document.getGeoPoint("Ubicacio").getLatitude(), document.getGeoPoint("Ubicacio").getLongitude()),
                                        new Location(document.getString("Nom"),document.getGeoPoint("Ubicacio").getLatitude(),
                                                document.getGeoPoint("Ubicacio").getLongitude(), document.getDouble("PlacesTotals").intValue(),
                                                document.getDouble("PlacesLliures").intValue(), document.getString("Barri")));
                                */
                            }
                            listener.updateCollection2(aparcaments);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}