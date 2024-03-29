package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.nio.channels.AsynchronousChannelGroup;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        EditText editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        TextView textRegister = findViewById(R.id.textRegister);
        Button buttonSignIn = findViewById(R.id.buttonSignIn);

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextLoginEmail.getText().toString().trim();
                String password = editTextLoginPassword.getText().toString().trim();
                login(email, password);
            }
        });
    }

    private void login(final String email, final String password) {
        class userLogin extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(!s.equals("Login failed")) {
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, sports.class);
                    intent.putExtra(configuration.USER_ID, s);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(configuration.URL_LOGIN, params);
                return res;
            }
        }
        userLogin ul = new userLogin();
        ul.execute();
    }
}