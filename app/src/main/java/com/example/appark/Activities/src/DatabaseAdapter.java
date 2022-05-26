package com.example.appark.Activities.src;

import android.app.Activity;
import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appark.Activities.MainActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseAdapter extends Activity {
    public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static FirebaseStorage storage = FirebaseStorage.getInstance();

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


        db.collection("Usuarios")
                .whereEqualTo("mail", oldEmail).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "user finded in DB");

                        String id = queryDocumentSnapshots.getDocuments().get(0).getId();
                        // Update an existing document
                        db.collection("Usuarios").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "user updated on DB");
                                User retrieved_User = new User(name, newEmail, pwd);
                                listener.getInfoUser(retrieved_User);
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



    }

    public void getUser(String mail){
        Log.d(TAG,"getUser method DatabaseAdapter");

        db.collection("Usuarios")
                .whereEqualTo("mail", mail).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User finded");

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

    public void saveImage(String email, String path){
        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg")); //TODO: pasar el path correcto
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = imgRef.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imgRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    saveURLImage(email, downloadUri.toString()); //asignamos la URL de la imagen al usuario
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    public void saveURLImage(String email, String url){
        Log.d(TAG,"saveURLImage");
        Map<String, Object> map = new HashMap<>(); //.collection necesita de un HashMap
        map.put("url", url);


        db.collection("Usuarios")
                .whereEqualTo("mail", email).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "user finded in DB");

                        String id = queryDocumentSnapshots.getDocuments().get(0).getId();
                        // Update an existing document
                        db.collection("Usuarios").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "url image saved in db");
                                listener.getURLImage(url);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "error while saving url image");
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "error while serching the user on DB");
            }
        });



    }



}
