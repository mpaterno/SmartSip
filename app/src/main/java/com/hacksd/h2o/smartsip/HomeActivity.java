package com.hacksd.h2o.smartsip;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    String docPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFireStore = FirebaseFirestore.getInstance();

        nickName = (TextView) findViewById(R.id.wb_nickname);
        nheight = (TextView) findViewById(R.id.nheight);

        docPath = getIntent().getExtras().getString("path");
        Log.d("Home","BEEEEEEEEEEEEEEEEEPPPPPPPPPPPPPPP " + docPath);

       getUsers();


    }

    public void getUsers(){
        String documentPath = "users/"+ docPath;
        DocumentReference mDocRef = FirebaseFirestore.getInstance().document(documentPath);

        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String waterNickName = documentSnapshot.getString("WaterName");
                    Log.d("DEMO", "OKKKKKRRRRRRR:" + waterNickName);
                    nickName.setText(waterNickName);


                    //Map<String,Object> mData = documentSnapshot.getData();
                    //InspiringQuote myQuote = documentSnapshot.toObject(InspiringQuote.class); takes data and creates an object
                }
            }
        });
    }
}
