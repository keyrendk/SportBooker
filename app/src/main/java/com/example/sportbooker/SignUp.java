package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername = findViewById(R.id.editTextUsername);
    private EditText editTextFirstName = findViewById(R.id.editTextFirstName);
    private EditText editTextLastName = findViewById(R.id.editTextLastName);
    private EditText editTextEmail = findViewById(R.id.editTextRegisterEmail);
    private EditText editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
    private EditText editTextPassword = findViewById(R.id.editTextPassword);
    private EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
    private TextView textSignIn = findViewById(R.id.textSignIn);
    private Button buttonSignUp = findViewById(R.id.buttonSignUp);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonSignUp.setOnClickListener(this);
    }

    private void addUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String first_name = editTextFirstName.getText().toString().trim();
        final String last_name = editTextLastName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String phone_number = editTextPhoneNumber.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        class AddUser extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignUp.this, "Add...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(SignUp.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(configuration.KEY_USER_USERNAME, username);
                params.put(configuration.KEY_USER_FIRST_NAME, first_name);
                params.put(configuration.KEY_USER_LAST_NAME, last_name);
                params.put(configuration.KEY_USER_EMAIL, email);
                params.put(configuration.KEY_USER_PASSWORD, password);
                params.put(configuration.KEY_USER_PHONE_NUMBER, phone_number);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(configuration.URL_ADD_USER, params);
                return res;
            }
        }
        AddUser au = new AddUser();
        au.execute();
    }

    @Override
    public void onClick(View v) {
        addUser();
    }
}