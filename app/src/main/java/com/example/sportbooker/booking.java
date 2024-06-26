package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private String dayName;
    private String facility_id;
    private String start_hour;
    private String finish_hour;
    private String schedule_status;
    private RecyclerView listView;
    private RecyclerView listDay;
    private Button cancel;
    private Button payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // inisialisasi variabel
        textUserUsername = (TextView) findViewById(R.id.textUserUsername);
        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        textSportType = (TextView) findViewById(R.id.textSportType);
        listView = (RecyclerView) findViewById(R.id.listFacility);
        listDay = (RecyclerView) findViewById(R.id.dayList);
        cancel = (Button) findViewById(R.id.buttonCancel);
        payment = (Button) findViewById(R.id.buttonPayment);

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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCancel = new Intent(booking.this, sports.class);
                startActivity(intentCancel);
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkScheduleStatus();
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
                String s = rh.sendGetRequestParam(configuration.URL_GET_FACILITIES, sport_type);
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
                String facility_id = jo.getString(configuration.TAG_FACILITY_FACILITY_ID);
                String facility_name = jo.getString(configuration.TAG_FACILITY_FACILITY_NAME);
                String facility_type = jo.getString(configuration.TAG_FACILITY_FACILITY_TYPE);
                String description = jo.getString(configuration.TAG_FACILITY_DESCRIPTION);
                String price = jo.getString(configuration.TAG_FACILITY_PRICE);

                HashMap<String, String> facilities = new HashMap<>();
                facilities.put(configuration.TAG_FACILITY_FACILITY_ID, facility_id);
                facilities.put(configuration.TAG_FACILITY_FACILITY_NAME, facility_name);
                facilities.put(configuration.TAG_FACILITY_FACILITY_TYPE, facility_type);
                facilities.put(configuration.TAG_FACILITY_DESCRIPTION, description);
                facilities.put(configuration.TAG_FACILITY_PRICE, price);
                list.add(facilities);
            }

            facilitiesRVAdapter adapter = new facilitiesRVAdapter(list, this);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adapter);
            adapter.setFacilityButtonClick(new facilitiesRVAdapter.FacilityButtonClick() {
                @Override
                public void onButtonClick(int position, String facilityId, String startHour, String finishHour) {
                    facility_id = facilityId;
                    start_hour = startHour;
                    finish_hour = finishHour;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            adapter.setButtonClickListener(new scheduleRVAdapter.DayButtonClick() {
                @Override
                public void onButtonClick(int position, String buttonText) {
                    dayName = buttonText;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkScheduleStatus() {
        class CheckScheduleStatus extends AsyncTask<Void, Void, String> {
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
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray(configuration.TAG_JSON_ARRAY);
                    JSONObject c = result.getJSONObject(0);
                    String status = c.getString(configuration.TAG_SCHEDULE_STATUS);
                    schedule_status = status;
                    if(schedule_status != null && schedule_status.equals("Available")) {
                        Intent intentPayment = new Intent(booking.this, payment.class);
                        intentPayment.putExtra(configuration.USER_ID, user_id);
                        intentPayment.putExtra(configuration.SCHEDULE_DAY, dayName);
                        intentPayment.putExtra(configuration.FACILITY_ID, facility_id);
                        intentPayment.putExtra(configuration.START_HOUR, start_hour);
                        intentPayment.putExtra(configuration.FINISH_HOUR, finish_hour);
                        startActivity(intentPayment);
                    } else {
                        Toast.makeText(booking.this,"Schedule Not Available", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetFacilityRequest(configuration.URL_GET_SCHEDULE_DETAIL, facility_id, start_hour, dayName);
                return s;
            }
        }
        CheckScheduleStatus gf = new CheckScheduleStatus();
        gf.execute();
    }
}