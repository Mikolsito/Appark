package com.example.appark.Activities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appark.Activities.src.DatabaseAdapter;
import com.example.appark.Activities.src.Estacionament;
import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.vmInterfaceEstacionament;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class HistorialDBAdapter {

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static vmInterfaceEstacionament listener;
    public static HistorialDBAdapter databaseAdapter;
    private static String barri;
    private static String nom;
    private static int places;
    private static int placesLliures;
    private static Location ubicacio;
    private static LatLng l;
    private static Date inici;
    private static Date acaba;

    private final String TAG = "HistorialDBAdapter";


    public HistorialDBAdapter(vmInterfaceEstacionament listener) {
        this.listener = listener;
        databaseAdapter = this;
    }

    public void getEstacionaments(String mailUsuari) {
        Log.d(TAG, "Inici getEstacionaments");
        DatabaseAdapter.db.collection("Estacionaments").whereEqualTo("User_mail",
                        mailUsuari)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "task Succesful");
                            ArrayList<Location> estacionaments = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                Log.d(TAG, "Get_data");
                                DocumentReference ubicacio_id = (DocumentReference) data.get("Ubicacio");
                                Log.d(TAG, "Ubicacio_id = " + ubicacio_id.getId());

                                db.collection("Ubicacions").document(ubicacio_id.getId()).get().
                                        addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()) {
                                                    Log.d(TAG, "second task Successful");
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    if(documentSnapshot.exists()) {
                                                        Log.d(TAG, documentSnapshot.toString());
                                                        Map<String, Object> data = documentSnapshot.getData();
                                                        String barri = (String) data.get("barri");
                                                        Log.d(TAG, barri);
                                                        String nom = (String) data.get("nom");
                                                        Log.d(TAG, nom);
                                                        Long places = (Long) data.get("places");
                                                        Long placesLliures = (Long) data.get("placeslliures");
                                                        GeoPoint l = (GeoPoint) data.get("position");
                                                        Location u = new Location(nom, l.getLatitude(), l.getLongitude(), places.intValue(), placesLliures.intValue(), barri);
                                                        estacionaments.add(u);
                                                        Log.d(TAG, "listener");
                                                        listener.updateCollection(estacionaments);
                                                        /*
                                                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                                        formatter.setTimeZone(TimeZone.getTimeZone("Spain/Barcelona"));
                                                        try {
                                                            Date date = formatter.parse(inici);
                                                            Date date2 = formatter.parse(acaba);
                                                            e.setDataInici(date);
                                                            e.setDataFinal(date2);
                                                            estacionaments.add(e);
                                                            //Crear estacionament
                                                        } catch (ParseException ex) {
                                                            ex.printStackTrace();
                                                        }*/
                                                    }
                                                }
                                            }
                                        });

                            }

                            //Log.d(TAG, "listener");
                            //listener.updateCollection(estacionaments);
                        }
                    }
                });
    }
}
