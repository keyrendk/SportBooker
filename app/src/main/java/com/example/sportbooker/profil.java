package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class profil extends AppCompatActivity {
    private TextView textUserUsernameProfile;
    private TextView textFirstNameProfile;
    private TextView textLastNameProfile;
    private TextView textEmailProfile;
    private TextView textPhoneNumberProfile;
    private String user_id;
    private String JSON_STRING;
    private ListView listView;
    private Button buttonUpdateProfile;
    private Button buttonDeleteProfile;
    private Button buttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        textUserUsernameProfile = (TextView) findViewById(R.id.textUserUsernameProfile);
        textFirstNameProfile = (TextView) findViewById(R.id.textFirstNameProfile);
        textLastNameProfile = (TextView) findViewById(R.id.textLastNameProfile);
        textEmailProfile = (TextView) findViewById(R.id.textEmailProfile);
        textPhoneNumberProfile = (TextView) findViewById(R.id.textPhoneNumberProfile);
        listView = (ListView) findViewById(R.id.listView);
        buttonUpdateProfile = (Button) findViewById(R.id.buttonUpdateProfile);
        buttonDeleteProfile = (Button) findViewById(R.id.buttonDeleteProfile);
        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);

        Intent intent = getIntent();
        user_id = intent.getStringExtra(configuration.USER_ID);

        getUserProfile();
        getJSON();

        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUpdate = new Intent(profil.this, updateUser.class);
                intentUpdate.putExtra(configuration.USER_ID, user_id);
                startActivity(intentUpdate);
            }
        });

        buttonDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confrimDeleteProfile();
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void getUserProfile() {
        class GetUserProfile extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(profil.this, "Get...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showUserProfile(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(configuration.URL_GET_USER, user_id);
                return s;
            }
        }
        GetUserProfile gup = new GetUserProfile();
        gup.execute();
    }

    private void showUserProfile(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(configuration.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String username = c.getString(configuration.TAG_USER_USERNAME);
            String first_name = c.getString(configuration.TAG_USER_FIRST_NAME);
            String last_name = c.getString(configuration.TAG_USER_LAST_NAME);
            String email = c.getString(configuration.TAG_USER_EMAIL);
            String phone_number = c.getString(configuration.TAG_USER_PHONE_NUMBER);

            textUserUsernameProfile.setText(username);
            textFirstNameProfile.setText(first_name);
            textLastNameProfile.setText(last_name);
            textEmailProfile.setText(email);
            textPhoneNumberProfile.setText(phone_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(profil.this, "Retrieving Data...", "Please Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showOrderHistory();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(configuration.URL_GET_ORDER_HISTORY, user_id);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showOrderHistory() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(configuration.TAG_JSON_ARRAY);

            for(int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String date = jo.getString(configuration.TAG_BOOKING_DATE);
                String booking_id = jo.getString(configuration.TAG_BOOKING_ID);
                String amount = jo.getString(configuration.TAG_AMOUNT);

                HashMap<String, String> orderHistory = new HashMap<>();
                orderHistory.put(configuration.TAG_BOOKING_DATE, date);
                orderHistory.put(configuration.TAG_BOOKING_ID, booking_id);
                orderHistory.put(configuration.TAG_AMOUNT, amount);
                list.add(orderHistory);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(profil.this, list, R.layout.booking_history_list, new String[]{configuration.TAG_BOOKING_DATE, configuration.TAG_BOOKING_ID, configuration.TAG_AMOUNT}, new int[]{R.id.date, R.id.booking_id, R.id.amount});
        listView.setAdapter(adapter);
    }

    private void confrimDeleteProfile() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this account?");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                deleteProfile();
                startActivity(new Intent(profil.this, MainActivity.class));
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteProfile() {
        class DeleteProfile extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(profil.this, "Delete...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(profil.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(configuration.URL_DELETE_USER, user_id);
                return s;
            }
        }
        DeleteProfile dp = new DeleteProfile();
        dp.execute();
    }

    private void logout() {
        class Logout extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(profil.this, "Logout...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(profil.this, s, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(profil.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(configuration.URL_LOGOUT);
                return s;
            }
        }
        Logout l = new Logout();
        l.execute();
    }
}