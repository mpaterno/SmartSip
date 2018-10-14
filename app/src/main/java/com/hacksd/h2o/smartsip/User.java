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

    String myid;
    User(FirebaseFirestore database)
    {
        this.db = database;
        myid = "abc";
    }

   public void create(String userName, String wbName , String height, String weight, String age, int intesity) {

       Map<String, Object> personalInfo = new HashMap<>();
       personalInfo.put("height", Integer.parseInt(height));
       personalInfo.put("weight", Integer.parseInt(weight));
       personalInfo.put("age", Integer.parseInt(age));
       personalInfo.put("intensity", intesity);
       /*
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
       user.put("PersonalInfo", personalInfo);
       Log.d(TAG, "name: " + userName);


       db.collection("users").add(user)
               .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                   @Override
                   public void onSuccess(DocumentReference documentReference) {
                       Log.d(TAG, "DocuentSnapshot ADDED with ID:" + documentReference.getId());
                       myid = documentReference.getId().toString();
                       Log.d(TAG, "               **** 7777 ****  IDDDDDDDDDDDDD:" + myid);
                   }
               }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Log.w(TAG, "ERROR adding document", e);

           }
       });

       /*
       user.put("id", UUID.randomUUID());
       user.put("history", waterHistory);
        */

   }

   public String getMyId(){
        Log.d(TAG, "GET MY ID GET MY ID GET MY ID GET MY ID IS CALLED AND EXECUTED MY ID: -> " + myid);
        return myid;
   }

   }

