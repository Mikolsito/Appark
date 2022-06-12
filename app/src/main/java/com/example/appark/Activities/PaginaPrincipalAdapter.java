package com.example.appark.Activities;

import android.app.Activity;
import android.util.Log;
import androidx.annotation.NonNull;

import com.example.appark.Activities.src.Estacionament;
import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.vmInterfaceUbicacio;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginaPrincipalAdapter extends Activity {
    public static final String TAG = "PaginaPrincipalAdapter";
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;

    public static vmInterfaceUbicacio listener;
    public static PaginaPrincipalAdapter databaseAdapter;

    public PaginaPrincipalAdapter(vmInterfaceUbicacio listener){
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
        }
        else{
            //listener.setToast("Authentication with current user.");

        }
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

    public void createEstacionament(String nomUbicacio){
        Log.d(TAG,"createEstacionament");
        db.collection("Ubicacions")
                .whereEqualTo("nom", nomUbicacio).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> doc= task.getResult().getDocuments();
                            if (doc.size() == 0) {
                                Log.d(TAG, "no se han encontrado ubicaciones");
                            }
                            else{
                                Log.d(TAG, "ubicacio trobada");
                                Map<String, Object> data = doc.get(0).getData();
                                String barri = (String) data.get("barri");
                                String nom = (String) data.get("nom");
                                Long places = (Long) data.get("places");
                                Long placeslliures = (Long) data.get("placeslliures");
                                GeoPoint geoPoint = (GeoPoint) data.get("position");
                                double lat = geoPoint.getLatitude();
                                double lng = geoPoint.getLongitude();
                                LatLng position = new LatLng(lat, lng);

                                DocumentReference locRef = doc.get(0).getReference();
                                Log.d(TAG, "ubicacio ref: " + locRef);

                                Location loc = new Location(nom, position.latitude, position.longitude, Math.toIntExact(places), Math.toIntExact(placeslliures), barri);
                                Estacionament es = new Estacionament(loc);
                                updatePlacesLocation(locRef, placeslliures); //disminuimos el numero de plazas libres
                                saveEstacionamentDB(es, locRef);
                            }
                        }
                    }

                });
    }

    private void saveEstacionamentDB(Estacionament es, DocumentReference locationRef){
        Log.d(TAG, "saveEstacionamentDB");
        Map<String, Object> estacionament = new HashMap<>();
        estacionament.put("Ubicacio", locationRef.getPath());
        estacionament.put("User_mail", es.getUser());
        estacionament.put("dataInici", es.getDataInici().toString());
        estacionament.put("tempsAparcat", es.getTempsAparcat());


        // Add a new document with a generated ID
        db.collection("Estacionaments")
                .add(estacionament)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot estacionament added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document estacionament", e);
                    }
                });
    }

    private void updatePlacesLocation(DocumentReference locRef, Long placeslliures){
        Log.d(TAG,"updatePlacesLocation");
        Map<String, Object> map = new HashMap<>(); //.collection necesita de un HashMap
        map.put("placeslliures", placeslliures - 1);

        db.collection("Ubicacions").document(locRef.getId()).update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "places lliures updated");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "error while updating places lliures ubicacio");
            }
        });


    }

    public void getPlacesUbicacio(String nomUbicacio) {
        Log.d(TAG,"getPlacesUbicacio");
        db.collection("Ubicacions")
                .whereEqualTo("nom", nomUbicacio).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> doc= task.getResult().getDocuments();
                            if (doc.size() == 0) {
                                Log.d(TAG, "no se han encontrado ubicaciones");
                            }
                            else{
                                Log.d(TAG, "ubicacio trobada");
                                Map<String, Object> data = doc.get(0).getData();

                                Long places = (Long) data.get("places");
                                Long placeslliures = (Long) data.get("placeslliures");

                                ArrayList<Integer> placesInfo = new ArrayList<>();
                                placesInfo.add(Math.toIntExact(placeslliures));
                                placesInfo.add(Math.toIntExact(places));

                                listener.getPlacesBD(placesInfo);

                            }
                        }
                    }

                });
    }
}
