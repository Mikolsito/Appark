package com.example.appark.Activities.src;

import android.app.Activity;
import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appark.Activities.MainActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCanceledListener;
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

    public void updateUser(String name, String oldEmail, String newEmail, String pwd) { //Configuració
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
                        Log.d(TAG, "user found in DB");

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

    public void getUser(String mail){ //Login
        Log.d(TAG,"getUser method DatabaseAdapter");
        db.collection("Usuarios")
                .whereEqualTo("mail", mail).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> list = task.getResult().getDocuments();
                            if(list.size() > 0){
                                Log.d(TAG, "User found");
                                Map<String, Object> data = task.getResult().getDocuments().get(0).getData();
                                String name = (String) data.get("name");
                                String mail = (String) data.get("mail");
                                String pwd = (String) data.get("pwd");
                                String url = (String) data.get("url");

                                User retrieved_User = new User(name, mail, pwd);
                                if(url != null){
                                    retrieved_User.setUrl(url);
                                }
                                listener.getInfoUser(retrieved_User);
                            }
                            else{
                                Log.d(TAG, "User not found");
                                listener.getInfoUser(null);
                            }


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void saveUser(String name, String mail, String pwd, String url) {
        Map<String, Object> usuari = new HashMap<>();
        usuari.put("name", name);
        usuari.put("mail", mail);
        usuari.put("pwd", pwd);
        usuari.put("url", url);

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

    public void uploadProfImage(String mail, Uri imgUri){
        Log.d(TAG, "uploadProfImage");
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child("images"+File.separator+mail+"_profImg.jpg");
        UploadTask uploadTask = imgRef.putFile(imgUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                Log.d(TAG, "uploadTask");
                return imgRef.getDownloadUrl(); //esto es la url que tiene que guardar el usuario en firebase
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "successful upload task");
                    Uri downloadUri = task.getResult();
                    saveProfImageUser(mail, downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
    public void saveProfImageUser(String email, String url){
        Log.d(TAG, "saveProfImageUser");
        Map<String, Object> map = new HashMap<>();
        map.put("url", url);

        db.collection("Usuarios")
                .whereEqualTo("mail", email).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "user found in DB");

                        String id = queryDocumentSnapshots.getDocuments().get(0).getId();
                        // Update an existing document
                        db.collection("Usuarios").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "profile image assigned to user on DB");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "error assigning image to user");
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "error while searching the user on DB");
            }
        });
    }

}
