package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPhoneNumber;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private TextView textSignIn;
    private Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextRegisterEmail);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        textSignIn = (TextView) findViewById(R.id.textSignIn);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(this);
        textSignIn.setOnClickListener(this);
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
        if(v == buttonSignUp) {
            String username = editTextUsername.getText().toString();
            String first_name = editTextFirstName.getText().toString();
            String last_name = editTextLastName.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();
            String phone_number = editTextPhoneNumber.getText().toString();

            if(username.isEmpty() || first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone_number.isEmpty()) {
                Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if(!editTextConfirmPassword.getText().toString().equals(editTextPassword.getText().toString())) {
                Toast.makeText(SignUp.this, "Password is not the same", Toast.LENGTH_SHORT).show();
            } else {
                addUser();
                startActivity(new Intent(this, MainActivity.class));
            }
        }

        if(v == textSignIn) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}