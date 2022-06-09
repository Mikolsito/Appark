package com.example.appark.Activities.src;

import android.app.Activity;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.example.appark.Activities.EstadistiquesFragment;
import com.example.appark.Activities.EstadistiquesViewModel;
import com.example.appark.Activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseAdapter extends Activity {
    public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static vmInterface listener;
    public static DatabaseAdapter databaseAdapter;

    public DatabaseAdapter(vmInterface listener){
        this.listener = listener;
        databaseAdapter = this;
    }

    public void updateUser() {
        Map<String, Object> map = new HashMap<>(); //.collection necesita de un HashMap
        map.put("name", MainActivity.currentUser.getName());
        map.put("mail", MainActivity.currentUser.getMail());
        map.put("pwd", MainActivity.currentUser.getPwd());

        //TODO: cambiar la isuginete linea y no buscar usuario por ID sino por documento
        /*db.collection("Usuarios").whereEqualTo("mail", MainActivity.currentUser.
                getMail()).limit(1).get().getResult().getDocuments().get(0).upd
        Map<String, Object> data = task.getResult().getDocuments().get(0).getData();*/
    }

    public void getUser(){
        Log.d(TAG,"getUser method DatabaseAdapter");
        db.collection("Usuarios")
                .whereEqualTo("mail", MainActivity.currentUser.getMail()).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> data = task.getResult().getDocuments().get(0).getData();
                            String name = (String) data.get("name");
                            String mail = (String) data.get("mail");
                            String pwd = (String) data.get("password");

                            MainActivity.currentUser.setUser(name, mail, pwd);

                            listener.setUser(MainActivity.currentUser);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void saveUser(User u) {
        Map<String, Object> usuari = new HashMap<>();
        usuari.put("name", u.getName());
        usuari.put("mail", u.getMail());
        usuari.put("pwd", u.getPwd());

        Log.d(TAG, "saveUserDB");
        // Add a new document with a generated ID
        db.collection("Usuarios")
                .add(usuari)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

   /* public void savePosition(Location l) {
        Map<String, Object> localitzacio = new HashMap<>();
        localitzacio.put("id", l.getId());
        localitzacio.put("position", l.getPosition());
        localitzacio.put("barri", l.getBarri());
        localitzacio.put("places", l.getPlaces());
        localitzacio.put("placeslliures", l.getPlaceslliures());

        Log.d(TAG, "savePositionDB");
        // Add a new document with a generated ID
        db.collection("Ubicacions")
                .add(localitzacio)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void getPlacesBarri(String barri){

        //List<String> barris=new ArrayList<String>();
        //barris.addAll(Arrays.asList("Eixample", "Sarrià", "Gracia", "Horta", "Sagrada Familia", "Sant Gervasi", "Poblenou", "Raval", "Sant Marti"));
        //for (int i=0;i<8;i++) {
            //String barri=barris.get(i);

            Log.d(TAG, "getPosition method DatabaseAdapter");
            db.collection("Ubicacions")
                    .whereEqualTo("barri", barri)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Logging the ID of your desired document & the document data itself
                                    Log.d(TAG, document.get("places") + " => " + document.get("placeslliures"));
                                    long places=0;
                                    long placeslliures=0;
                                    places=(long) document.get("places");
                                    placeslliures=(long) document.get("placeslliures");
                                    Pair<String, Long> pair = new Pair<String, Long>(barri, (places - placeslliures));
                                    EstadistiquesFragment.PlacesBarri.add(pair);
                                }
                                //Map<String, Object> data = task.getResult().getDocuments().iterator().next().getData();
                                //int places = (int) data.get("places");
                                //int placeslliures = (int) data.get("placeslliures");



                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

    }

    //}
*/
}
