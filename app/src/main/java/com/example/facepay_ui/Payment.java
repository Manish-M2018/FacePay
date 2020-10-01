package com.example.facepay_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Payment extends AppCompatActivity {

    private EditText amountToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button submit = findViewById(R.id.submit);

        amountToPay = findViewById(R.id.amount);

        amountToPay.requestFocus();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amountToPay == null || amountToPay.getText().toString().equals("")) {
                    Toast.makeText(Payment.this,"Please enter an amount",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Payment.this,"Payment of " + amountToPay.getText().toString()+" was successful!",Toast.LENGTH_SHORT).show();
                    amountToPay.setText("");
                    startActivity(new Intent(Payment.this,Transaction_complete.class));
                }
            }
        });



    }
}
