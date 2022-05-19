package com.example.appark.Activities.src;

import android.app.Activity;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appark.Activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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

    public void updateUser(String name, String oldEmail, String newEmail, String pwd) {
        Log.d(TAG,"updateUser");
        Map<String, Object> map = new HashMap<>(); //.collection necesita de un HashMap
        map.put("name", name);
        map.put("mail", newEmail);
        map.put("pwd", pwd);


        /*Task<QuerySnapshot> t = db.collection("Usuarios").whereEqualTo("mail", oldEmail).get();
        List<DocumentSnapshot> docs = t.getResult().getDocuments();
        DocumentSnapshot doc = docs.get(0);
        String id = doc.getId();*/

        db.collection("Usuarios").whereEqualTo("mail", oldEmail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d(TAG, "user finded in DB");

                String id = queryDocumentSnapshots.getDocuments().get(0).getId();
                // Update an existing document
                db.collection("Usuarios").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "user updated on DB");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "error while uPdating user");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "error while serching the user on DB");
            }
        });



        //User retrieved_User = new User(name, mail, pwd);

        //listener.getInfoUser(retrieved_User);

    }

    public void getUser(String mail){
        Log.d(TAG,"getUser method DatabaseAdapter");
        db.collection("Usuarios")
                .whereEqualTo("mail", mail).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> data = task.getResult().getDocuments().get(0).getData();
                            String name = (String) data.get("name");
                            String mail = (String) data.get("mail");
                            String pwd = (String) data.get("pwd");

                            User retrieved_User = new User(name, mail, pwd);

                            listener.getInfoUser(retrieved_User);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void saveUser(String name, String mail, String pwd) {
        Map<String, Object> usuari = new HashMap<>();
        usuari.put("name", name);
        usuari.put("mail", mail);
        usuari.put("pwd", pwd);

        Log.d(TAG, "saveUserDB");
        // Add a new document with a generated ID
        db.collection("Usuarios")
                .add(usuari)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                        User newUser = new User(name, mail, pwd);
                        listener.getInfoUser(newUser);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public boolean searchUserDB(String mail){
        Log.d(TAG,"searchUser method DatabaseAdapter");

        Task<QuerySnapshot> t = db.collection("Usuarios").whereEqualTo("mail", mail).get();
        List<DocumentSnapshot> docs = t.getResult().getDocuments();
        DocumentSnapshot doc = docs.get(0);
        Map<String, Object> data = doc.getData();

        if(data != null){
            MainActivity.currentUser.setUser(data.get("name").toString(), data.get("mail").toString(), data.get("pwd").toString());
            return true;
        }
        else{
            return false;
        }

    }

}
