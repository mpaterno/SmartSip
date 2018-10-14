package com.hacksd.h2o.smartsip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    FirebaseFirestore mFireStore;
    TextView nickName;
    TextView nheight;
    String currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFireStore = FirebaseFirestore.getInstance();

        nickName = (TextView) findViewById(R.id.wb_nickname);
        nheight = (TextView) findViewById(R.id.nheight);

        //Intent intent = getIntent();
       // currentUserId = intent.getStringExtra("id");
        //Log.d("Home",currentUserId);

       // getUsers();


    }

    public void getUsers(){
        //ArrayList<String> list = new ArrayList();
        mFireStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("HOME","TASK IS SUCCESSFUL TASK IS SUCCESSFUL TASK IS SUCCESSFUL");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("HOME HOME !!!!!! ", document.getId() + " HERRRRRRRRRRRRRRRRRRRRRRRRRREEEEEEEe=> " + document.getData());
                                //list.add(document.getData());
                            }
                        } else {
                            Log.w("HOME", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
