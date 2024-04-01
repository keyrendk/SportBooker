package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class profil extends AppCompatActivity {
    private TextView textUserUsernameProfile;
    private EditText textFirstNameProfile;
    private EditText textLastNameProfile;
    private EditText textEmailProfile;
    private EditText textPhoneNumberProfile;
    private String user_id;
    private String JSON_STRING;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        textUserUsernameProfile = (TextView) findViewById(R.id.textUserUsernameProfile);
        textFirstNameProfile = (EditText) findViewById(R.id.textFirstNameProfile);
        textLastNameProfile = (EditText) findViewById(R.id.textLastNameProfile);
        textEmailProfile = (EditText) findViewById(R.id.textEmailProfile);
        textPhoneNumberProfile = (EditText) findViewById(R.id.textPhoneNumberProfile);
        listView = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        user_id = intent.getStringExtra(configuration.USER_ID);

        getUserProfile();
        getJSON();
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
                loading = ProgressDialog.show(profil.this, "Retrieving Data", "Please Wait...", false, false);
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
}