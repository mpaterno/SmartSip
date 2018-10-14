package com.hacksd.h2o.smartsip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DailyWater {

    //Firebase  data base
    private final FirebaseFirestore db;

    DocumentReference mDocRef;

    String docPath;

    DailyWater(FirebaseFirestore database, String mName)
    {
        this.db = database;
        docPath = "waterIntake/" +mName;
    }

    public void create(double dailySuggested, int consumed, long time){
        Map<String,Object> water = new HashMap<>();
        water.put("dailyIntake", dailySuggested);
        water.put("consumedWater", consumed);
        water.put("timeStamp", time);

        DocumentReference mDocRef = FirebaseFirestore.getInstance().document(docPath);
        mDocRef = FirebaseFirestore.getInstance().document(docPath);
        mDocRef.set(water).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Demo", "Document has been saved! dailywater");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Demo", "Document not saved!! dailywater", e);
            }
        });

    }








}
