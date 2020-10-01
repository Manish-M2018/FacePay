package com.example.facepay_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;

public class Mobile_number extends AppCompatActivity {

    private EditText mobile_no;
    String[] descriptionData = {"Mobile","Unique Pin", "Fingerprint", "Face"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);

        mobile_no = (EditText) findViewById(R.id.et_mobile);


        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);

        Button submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mob = mobile_no.getText().toString();
                if(mob.length() != 10) {
                    // Mobile number validation
                    Toast.makeText(Mobile_number.this,"Please enter a valid mobile number",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Mobile_number.this,"Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Mobile_number.this,MainActivity.class));
                }
            }
        });
    }
}
