package com.example.facepay_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddAmount extends AppCompatActivity {

    private EditText email,amount;
    private Button submit;
    private String email_string, amount_string, new_balance;

    private String url = "http://" + "10.0.2.2" + ":" + 5000 + "/add_amount"; //Default local address of the android emulator
    private MediaType mediaType;
    private RequestBody requestBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amount);

        email = (EditText) findViewById(R.id.et_email);
        amount = (EditText) findViewById(R.id.et_amount);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount.equals("")) {
                    amount.setError("Enter the amount");
                } else {
                    email_string = email.getText().toString().trim();
                    amount_string = amount.getText().toString().trim();
                    addAmount(email_string,amount_string);
                }
            }
        });
    }

    private RequestBody buildRequestBody(String email, String amount) {
        mediaType = MediaType.parse("application/json");
        RequestBody req = new FormBody.Builder()
                .add("email_id",email)
                .add("amount",amount)
                .build();
        return req;
    }

    private void addAmount(String email, String amount) {
        requestBody = buildRequestBody(email,amount);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(AddAmount.this,"An error occurred",Toast.LENGTH_SHORT).show();
                //Log.d("Errorrrrr", e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Toast.makeText(LoginActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
                            String jsonData = response.body().string();
                            Log.d("Response addAmount: ",jsonData);
                            JSONObject res = new JSONObject(jsonData);
                            if(res.getString("success").equals("true")) {
                                new_balance = res.getString("balance");
                                Toast.makeText(AddAmount.this,"Success! New Balance = "+ new_balance,Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddAmount.this, Payment.class));
                            }else {
                                Toast.makeText(AddAmount.this,"Invalid email address",Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }
}
