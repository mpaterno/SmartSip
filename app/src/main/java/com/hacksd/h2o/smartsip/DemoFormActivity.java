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
import java.util.Date;
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
    Date date = new Date();

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


                DailyWater waterIntake = new DailyWater(mFirestore, nameForm.getText().toString());
                double rec = recWaterIntake(Integer.parseInt(ageForm.getText().toString()),
                        (int)(Integer.parseInt(heightForm.getText().toString()) * 2.54),
                        Integer.parseInt(weightForm.getText().toString()), intensityForm.getProgress() );
                waterIntake.create(rec,0,date.getTime());


                Intent i = new Intent(DemoFormActivity.this, Select.class);
                i.putExtra("path", nameForm.getText().toString());
                i.putExtra("nickName", wbForm.getText().toString());
                startActivity(i);
            }
        });



    }

    // Input age, height, weight, exerRating
    // Return cups of water to drink for the day!
    public double recWaterIntake(int age, int height, int weight, int exerRating) {
        double bmi = (weight / (height * height));
        double recWaterL = (bmi * 0.062) + (exerRating / 3) + (age / 100);
        return (recWaterL * 4.22675); // Return cups of water
    }
}
