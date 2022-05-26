package com.example.appark.Activities;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;

import com.example.appark.Activities.src.Location;
import com.example.appark.Activities.src.vmInterfaceUbicacio;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class PaginaPrincipalAdapter extends Activity {
    public static final String TAG = "DatabaseAdapter";
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
        PaginaPrincipalAdapter.db.collection("Locations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            HashMap<LatLng, Location> ubicacioHashMap= new HashMap<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ubicacioHashMap.put(new com.google.android.gms.maps.model.LatLng(
                                        document.getGeoPoint("Ubicacio").getLatitude(), document.getGeoPoint("Ubicacio").getLongitude()),
                                        new Location(document.getString("Nom"),document.getGeoPoint("Ubicacio").getLatitude(),
                                                document.getGeoPoint("Ubicacio").getLongitude(), document.getDouble("PlacesTotals").intValue(),
                                                document.getDouble("PlacesLliures").intValue(), document.getString("Barri")));
                            }
                            listener.setCollection(ubicacioHashMap);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void saveDocument (String nom, LatLng latlon, int placesTotals, int placesLliures, String barri, String url) {

        // Create a new ubicació
        Location ubicacio = new Location(nom, latlon.latitude, latlon.longitude, placesTotals, placesLliures, barri);

        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        db.collection("Ubicacions")
                .add(ubicacio)
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

    public void saveDocumentWithFile (String nom, LatLng latlon, int placesTotals, int placesLliures, String barri) {

        Uri file = Uri.fromFile(new File(""));
        StorageReference storageRef = storage.getReference();
        StorageReference ubiRef = storageRef.child("audio"+File.separator+file.getLastPathSegment());
        UploadTask uploadTask = ubiRef.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ubiRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    saveDocument(nom, latlon, placesTotals, placesLliures, barri, downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });


        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d(TAG, "Upload is " + progress + "% done");
            }
        });
    }
}
