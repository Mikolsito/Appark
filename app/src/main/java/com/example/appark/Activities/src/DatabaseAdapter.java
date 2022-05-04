package com.example.appark.Activities.src;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

public class DatabaseAdapter extends Activity {
    public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static vmInterface listener;
    public static DatabaseAdapter databaseAdapter;

    public DatabaseAdapter(vmInterface listener){
        this.listener = listener;
        databaseAdapter = this;
    }


    public interface vmInterface{
        void setUser(User user);
    }

    public void getUser(){
        Log.d(TAG,"updateUsers");
        DatabaseAdapter.db.collection("Usuarios").document("user2").get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user;
                        if(documentSnapshot.exists()){
                            String name = documentSnapshot.getString("name");
                            String mail = documentSnapshot.getString("mail");
                            String pwd = documentSnapshot.getString("password");

                            user = new User(name, mail, pwd);

                            listener.setUser(user);
                        }
                        else {
                            Log.d(TAG, "Error getting documents: the user with name .... does not exists");
                        }
                    }
                });

    }





}
