package com.hacksd.h2o.smartsip;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Random;

import javax.annotation.Nullable;

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
       Map<String, Object> waterHistory = new HashMap<>();
*/
       Map<String, Object> user = new HashMap<>();
       user.put("name", userName);
       user.put("WaterName", wbName);
       user.put("PersonalInfo", personalInfo);
       Log.d(TAG, "name: " + userName);

       String documentPath = "users/"+userName;



       DocumentReference mDocRef = FirebaseFirestore.getInstance().document(documentPath);

       mDocRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               Log.d("SAVEME", "Document has been saved!");
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Log.w(TAG, "Document not saved!!", e);
           }
       });
   }


   }

