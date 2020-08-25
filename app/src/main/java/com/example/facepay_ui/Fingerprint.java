package com.example.facepay_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Fingerprint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        AppCompatImageView fingerImage = findViewById(R.id.finger_scan);
        fingerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Fingerprint.this,"SUCCESS",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Fingerprint.this,FaceRecognition.class));
            }
        });
    }
}
