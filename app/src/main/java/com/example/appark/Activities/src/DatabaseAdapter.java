package com.example.appark.Activities.src;

import android.app.Activity;
import android.util.Log;

import com.example.appark.Activities.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
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

        db.collection("Usuarios").document(MainActivity.currentUser.getUserId()).update(map);

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
