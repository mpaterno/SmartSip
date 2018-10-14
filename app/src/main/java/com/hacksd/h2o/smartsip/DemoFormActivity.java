package com.hacksd.h2o.smartsip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

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
                User currentUser = new User(mFirestore);
                currentUser.create(nameForm.getText().toString(), wbForm.getText().toString(),
                        heightForm.getText().toString(), weightForm.getText().toString(),
                        ageForm.getText().toString(), intensityForm.getProgress());

                /*Log.d("Demo", "NOW ID IS HERE:  ??" );
                String id = currentUser.getMyId();
                Log.d("Demo", "NOW ID IS HERE:      " + id);*/
                Intent i = new Intent(DemoFormActivity.this, HomeActivity.class);
                //i.putExtra("myid",nameForm.getText().toString() );
                startActivity(i);
                Log.d("Demo", "NOW ID IS HERE:  ??" );
                String id = currentUser.getMyId();
                Log.d("Demo", "NOW ID IS HERE:      " + id);
            }
        });
    }
}
