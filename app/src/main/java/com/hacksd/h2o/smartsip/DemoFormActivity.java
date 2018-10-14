package com.hacksd.h2o.smartsip;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DemoFormActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    EditText nameForm;
    EditText wbForm;
    EditText heightForm;
    EditText weightForm;
    EditText ageForm;
    SeekBar intensityForm;
    Button submitForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_form);

        mFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        nameForm = (EditText) findViewById(R.id.form_name);
        wbForm = (EditText) findViewById(R.id.form_wb);
        heightForm = (EditText) findViewById(R.id.form_height);
        weightForm = (EditText) findViewById(R.id.form_weight);
        ageForm = (EditText) findViewById(R.id.form_age);
        intensityForm = (SeekBar) findViewById(R.id.form_sweat);
        submitForm = (Button) findViewById(R.id.submit_form);






        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User current = new User(mFirestore);
                current.create(nameForm.getText().toString(),wbForm.getText().toString(),
                        heightForm.getText().toString(), weightForm.getText().toString(), ageForm.getText().toString(),
                        intensityForm.getProgress());


                String documentPath = "users/"+ nameForm.getText().toString();
                DocumentReference mDocRef = FirebaseFirestore.getInstance().document(documentPath);

                mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String waterNickName = documentSnapshot.getString("WaterName");
                            Log.d("DEMO", "OKKKKKRRRRRRR:" + waterNickName);
                            //Map<String,Object> mData = documentSnapshot.getData();
                            //InspiringQuote myQuote = documentSnapshot.toObject(InspiringQuote.class); takes data and creates an object
                        }
                    }
                });


                Intent i = new Intent(DemoFormActivity.this, Scan.class);
                i.putExtra("path", nameForm.getText().toString());
                i.putExtra("NickName", wbForm.getText().toString());
                startActivity(i);
            }
        });


    }
}
