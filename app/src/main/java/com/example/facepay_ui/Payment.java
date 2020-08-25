package com.example.facepay_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final EditText amount = findViewById(R.id.amount);
        Button submit = findViewById(R.id.submit);

        amount.requestFocus();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Payment.this,"Payment was successful!",Toast.LENGTH_SHORT).show();
                amount.setText("");
                startActivity(new Intent(Payment.this,Transaction_complete.class));
            }
        });



    }
}
