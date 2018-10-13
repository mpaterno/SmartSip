package com.hacksd.h2o.smartsip;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DailyWater {
    /*
    UUID id;
    String userEmail;
    String userPassword;
    WaterBottle bottle;

    User(){};
    */
    //Firebase  data base
    private final FirebaseFirestore db;

    DailyWater(FirebaseFirestore database)
    {
        this.db = database;
    }

    public void create(int dailySuggested, int consumed, int time){
        Map<String,Object> water = new HashMap<>();
        water.put("dailyIntake", dailySuggested);
        water.put("consumedWater", consumed);
        water.put("timeStamp", time);

        db.collection("water")
                .add(water)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("dailyWater", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("dailyWater", "Error adding document", e);
                    }
                });
    }

}
