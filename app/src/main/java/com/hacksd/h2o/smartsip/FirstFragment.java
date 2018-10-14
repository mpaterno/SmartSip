package com.hacksd.h2o.smartsip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hacksd.h2o.smartsip.R;
import com.hacksd.h2o.smartsip.User;

public class FirstFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    FirebaseFirestore mFirestore;
    EditText userName;
    public FirstFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_health_stats, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        mFirestore = FirebaseFirestore.getInstance();

        userName = (EditText) rootView.findViewById(R.id.userName_editText);

        Button enterButton = (Button) rootView.findViewById(R.id.enter_button);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //User currentUser = new User(mFirestore);
                //currentUser.create(userName.getText().toString());
            }
        });


        return rootView;
    }
}