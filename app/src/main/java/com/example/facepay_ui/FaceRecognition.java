package com.example.facepay_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.media.FaceDetector;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class FaceRecognition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);

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
