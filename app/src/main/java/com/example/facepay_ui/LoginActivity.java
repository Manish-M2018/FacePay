package com.example.facepay_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    private String url = "http://" + "10.0.2.2" + ":" + 5000 + "/login"; //Default local address of the android emulator
    private MediaType mediaType;
    private RequestBody requestBody;

    private TextView moveToRegisterActivity;
    private EditText email,psw;
    private Button submit;
    private String email_string, psw_string;

    private String balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        moveToRegisterActivity = (TextView) findViewById(R.id.moveToRegister);
        email = (EditText) findViewById(R.id.et_email);
        psw = (EditText) findViewById(R.id.et_psw);
        submit = (Button) findViewById(R.id.submit);
        

        moveToRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to Register activity
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_string = email.getText().toString();
                psw_string = psw.getText().toString();

                if(psw_string.equals("")) {
                    //If either the email or the psw is empty
                    psw.setError("Enter the password");
                } else {
                    postRequest(email_string,psw_string,url);
                }
            }
        });


    }

    private RequestBody buildRequestBody(String email, String psw) {
        mediaType = MediaType.parse("application/json");
        RequestBody req = new FormBody.Builder()
                .add("email_id",email)
                .add("password",psw)
                .build();
        return req;
    }

    private void postRequest(String email, String psw, String url) {
        requestBody = buildRequestBody(email,psw);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //Toast.makeText(LoginActivity.this, "Something went wrong:" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Errorrrrr", e.getMessage());
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
                            JSONObject res = new JSONObject(jsonData);
                            balance = res.getString("balance");
                            Toast.makeText(LoginActivity.this,"Success! Balance = "+ balance,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, Mobile_number.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
