package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class booking extends AppCompatActivity {
    // deklarasi variabel
    private TextView textUserUsername;
    private ImageView profilePicture;
    private TextView textSportType;
    private String user_id;
    private String sport_type;
    private String JSON_STRING;
    private ListView listView;
    private RecyclerView listDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // inisialisasi variabel
        textUserUsername = (TextView) findViewById(R.id.textUserUsername);
        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        textSportType = (TextView) findViewById(R.id.textSportType);
        listView = (ListView) findViewById(R.id.listFacility);
        listDay = (RecyclerView) findViewById(R.id.dayList);

        // mengambil user_id dan sport_type dari sports
        Intent intent = getIntent();
        user_id = intent.getStringExtra(configuration.USER_ID);
        sport_type = intent.getStringExtra(configuration.SPORT_TYPE);

        getUsername();
        textSportType.setText(sport_type);
        getScheduleDay();
        getFacilities();

        // setOnClickListener
        textUserUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(booking.this, profil.class);
                intent1.putExtra(configuration.USER_ID, user_id);
                startActivity(intent1);
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(booking.this, profil.class);
                intent2.putExtra(configuration.USER_ID, user_id);
                startActivity(intent2);
            }
        });
    }

    private void getUsername() {
        class GetUsername extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(booking.this, "Get...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showUserUsername(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(configuration.URL_GET_USERNAME_USER, user_id);
                return s;
            }
        }
        GetUsername gu = new GetUsername();
        gu.execute();
    }

    private void showUserUsername(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(configuration.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String username = c.getString(configuration.TAG_USER_USERNAME);

            textUserUsername.setText(username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getFacilities() {
        class GetFacilities extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(booking.this, "Retrieving Data...", "Please Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showFacilities();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(configuration.URL_GET_FACILITY, sport_type);
                return s;
            }
        }
        GetFacilities gf = new GetFacilities();
        gf.execute();
    }

    private void showFacilities() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(configuration.TAG_JSON_ARRAY);

            for(int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String facility_name = jo.getString(configuration.TAG_FACILITY_FACILITY_NAME);
                String facility_type = jo.getString(configuration.TAG_FACILITY_FACILITY_TYPE);
                String description = jo.getString(configuration.TAG_FACILITY_DESCRIPTION);
                String price = jo.getString(configuration.TAG_FACILITY_PRICE);

                HashMap<String, String> facilities = new HashMap<>();
                facilities.put(configuration.TAG_FACILITY_FACILITY_NAME, facility_name);
                facilities.put(configuration.TAG_FACILITY_FACILITY_TYPE, facility_type);
                facilities.put(configuration.TAG_FACILITY_DESCRIPTION, description);
                facilities.put(configuration.TAG_FACILITY_PRICE, price);
                list.add(facilities);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(booking.this, list, R.layout.booking_list, new String[]{configuration.TAG_FACILITY_FACILITY_NAME, configuration.TAG_FACILITY_FACILITY_TYPE, configuration.TAG_FACILITY_DESCRIPTION, configuration.TAG_FACILITY_PRICE}, new int[]{R.id.facilityName, R.id.facilityType, R.id.facilityDescription, R.id.facilityPrice});
        listView.setAdapter(adapter);
    }

    private void getScheduleDay() {
        class GetScheduleDay extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(booking.this, "Retrieving Data...", "Please Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showScheduleDay(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(configuration.URL_GET_SCHEDULE_DAY);
                return s;
            }
        }
        GetScheduleDay gsd = new GetScheduleDay();
        gsd.execute();
    }

    private void showScheduleDay(String JSON_STRING) {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(configuration.TAG_JSON_ARRAY);

            for(int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String day = jo.getString(configuration.TAG_SCHEDULE_DAY);

                HashMap<String, String> scheduleDay = new HashMap<>();
                scheduleDay.put(configuration.TAG_SCHEDULE_DAY, day);
                list.add(scheduleDay);
            }

            scheduleRVAdapter adapter = new scheduleRVAdapter(list, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            listDay.setLayoutManager(layoutManager);
            listDay.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}