package com.hacksd.h2o.smartsip;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
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

   public void create(String userName, String wbName, int height, int weight, int age,
                      int urineColor, int sweat, int activity, int time, int intensity,
                      int weather, int temp ){
       Map<String,Object> user = new HashMap<>();
       user.put("name", userName);
       user.put("WaterName", wbName);

       Map<String,Object> waterIntake = new HashMap<>();
       waterIntake.put("height", height);
       waterIntake.put("weight", weight);

   }
}
