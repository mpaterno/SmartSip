package com.hacksd.h2o.smartsip;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Random;

public class User {
    public static final String TAG = "UserDoc";
    /*
            UUID id;
            String userEmail;
            String userPassword;
            WaterBottle bottle;

            User(){};
            */
    //Firebase  data base
    private final FirebaseFirestore db;

    User(FirebaseFirestore database)
    {
        this.db = database;
    }

   public void create(String userName, String wbName){ //, int height, int weight, int age,
                      //int urineColor, int sweat, int activity, int time, int intensity,
                      //int weather, int temp ) {
/*
       Map<String, Object> waterIntake = new HashMap<>();
       waterIntake.put("height", height);
       waterIntake.put("weight", weight);
       waterIntake.put("age", age);
       waterIntake.put("urineColor", urineColor);
       waterIntake.put("sweat", sweat);
       waterIntake.put("activity", activity);
       waterIntake.put("time", time);
       waterIntake.put("intensity", intensity);
       waterIntake.put("weather", weather);
       waterIntake.put("temp", temp);

       Map<String, Object> waterHistory = new HashMap<>();
*/
       Map<String, Object> user = new HashMap<>();
       user.put("name", userName);
       user.put("WaterName", wbName);
       /*
       user.put("userFitness", waterIntake);
       user.put("id", UUID.randomUUID());
       user.put("history", waterHistory);
        */
       db.collection("users").add(user)
               .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                   @Override
                   public void onSuccess(DocumentReference documentReference) {
                       Log.d(TAG, "DocuentSnapshot added with ID:" + documentReference.getId());
                   }
               }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Log.w(TAG, "Error adding document", e);
           }
       });

   }


        }

