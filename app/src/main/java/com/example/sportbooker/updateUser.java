package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class updateUser extends AppCompatActivity {
    // deklarasi variabel
    private EditText editUpdateUsername;
    private EditText editUpdateTextFirstName;
    private EditText editUpdateTextLastName;
    private EditText editUpdateTextRegistrasiEmail;
    private EditText editUpdateTextPhoneNumber;
    private EditText editUpdateTextPassword;
    private EditText editUpdateTextConfirmPassword;
    private Button buttonUpdate;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // mengambil intent
        Intent intent = getIntent();
        user_id = intent.getStringExtra(configuration.USER_ID);

        // inisialisasi variabel
        editUpdateUsername = (EditText) findViewById(R.id.editUpdateUsername);
        editUpdateTextFirstName = (EditText) findViewById(R.id.editUpdateTextFirstName);
        editUpdateTextLastName = (EditText) findViewById(R.id.editUpdateTextLastName);
        editUpdateTextRegistrasiEmail = (EditText) findViewById(R.id.editUpdateTextRegistrasiEmail);
        editUpdateTextPhoneNumber = (EditText) findViewById(R.id.editUpdateTextPhoneNumber);
        editUpdateTextPassword = (EditText) findViewById(R.id.editUpdateTextPassword);
        editUpdateTextConfirmPassword = (EditText) findViewById(R.id.editUpdateTextConfirmPassword);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

        // menjalankan method untuk mengambil data user
        getUser();

        // setOnClick untuk buttonUpdate
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUpdateUsername.getText().toString();
                String first_name = editUpdateTextFirstName.getText().toString();
                String last_name = editUpdateTextLastName.getText().toString();
                String email = editUpdateTextRegistrasiEmail.getText().toString();
                String phone_number = editUpdateTextPhoneNumber.getText().toString();
                String password = editUpdateTextPassword.getText().toString();
                String confirm_password = editUpdateTextConfirmPassword.getText().toString();

                if(username.isEmpty() || first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || phone_number.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
                    Toast.makeText(updateUser.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if(!editUpdateTextConfirmPassword.getText().toString().equals(editUpdateTextPassword.getText().toString())) {
                    Toast.makeText(updateUser.this, "Password is not the same", Toast.LENGTH_SHORT).show();
                } else {
                    updateUser();
                    Intent profile = new Intent(updateUser.this, profil.class);
                    profile.putExtra(configuration.USER_ID, user_id);
                    startActivity(profile);
                }
            }
        });
    }

    private void getUser() {
        class GetUser extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(updateUser.this, "Retrieve...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showUser(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(configuration.URL_GET_USER, user_id);
                return s;
            }
        }
        GetUser gu = new GetUser();
        gu.execute();
    }

    private void showUser(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(configuration.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String username = c.getString(configuration.TAG_USER_USERNAME);
            String first_name = c.getString(configuration.TAG_USER_FIRST_NAME);
            String last_name = c.getString(configuration.TAG_USER_LAST_NAME);
            String email = c.getString(configuration.TAG_USER_EMAIL);
            String phone_number = c.getString(configuration.TAG_USER_PHONE_NUMBER);
            String password = c.getString(configuration.TAG_USER_PASSWORD);
            String confirm_password = c.getString(configuration.TAG_USER_PASSWORD);

            editUpdateUsername.setText(username);
            editUpdateTextFirstName.setText(first_name);
            editUpdateTextLastName.setText(last_name);
            editUpdateTextRegistrasiEmail.setText(email);
            editUpdateTextPhoneNumber.setText(phone_number);
            editUpdateTextPassword.setText(password);
            editUpdateTextConfirmPassword.setText(confirm_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateUser() {
        final String username = editUpdateUsername.getText().toString().trim();
        final String first_name = editUpdateTextFirstName.getText().toString().trim();
        final String last_name = editUpdateTextLastName.getText().toString().trim();
        final String email = editUpdateTextRegistrasiEmail.getText().toString().trim();
        final String phone_number = editUpdateTextPhoneNumber.getText().toString().trim();
        final String password = editUpdateTextPassword.getText().toString().trim();
        final String confirm_password = editUpdateTextConfirmPassword.getText().toString().trim();

        class UpdateUser extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(updateUser.this, "Update...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(updateUser.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(configuration.KEY_USER_ID, user_id);
                hashMap.put(configuration.KEY_USER_USERNAME, username);
                hashMap.put(configuration.KEY_USER_FIRST_NAME, first_name);
                hashMap.put(configuration.KEY_USER_LAST_NAME, last_name);
                hashMap.put(configuration.KEY_USER_EMAIL, email);
                hashMap.put(configuration.KEY_USER_PHONE_NUMBER, phone_number);
                hashMap.put(configuration.KEY_USER_PASSWORD, password);
                hashMap.put(configuration.KEY_USER_CONFIRM_PASSWORD, confirm_password);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(configuration.URL_UPDATE_USER, hashMap);
                return s;
            }
        }
        UpdateUser uu = new UpdateUser();
        uu.execute();
    }
}