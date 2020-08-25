package com.example.facepay_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.media.FaceDetector;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;

public class FaceRecognition extends AppCompatActivity {

    String[] descriptionData = {"Mobile","Unique Pin", "Fingerprint", "Face"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);

        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);

        AppCompatImageView face_scan = findViewById(R.id.face_scan);
        face_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FaceRecognition.this,"Authenticated successfully!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FaceRecognition.this,Payment.class));
            }
        });
    }
}
