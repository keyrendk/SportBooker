package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class payment extends AppCompatActivity {
    private TextView dataDate;
    private TextView dataFacilityName;
    private TextView dataStartHour;
    private TextView dataFinishHour;
    private TextView dataAmount;
    private Button qris;
    private Button dana;
    private Button ovo;
    private Button payNow;
    private String day_name;
    private String facility_id;
    private String start_hour;
    private String finish_hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        dataDate = (TextView) findViewById(R.id.dataDate);
        dataFacilityName = (TextView) findViewById(R.id.dataFacilityName);
        dataStartHour = (TextView) findViewById(R.id.dataStartHour);
        dataFinishHour = (TextView) findViewById(R.id.dataFinishHour);
        dataAmount = (TextView) findViewById(R.id.dataAmount);
        qris = (Button) findViewById(R.id.qris);

        Intent intent = getIntent();
        day_name = intent.getStringExtra(configuration.SCHEDULE_DAY);
        facility_id = intent.getStringExtra(configuration.FACILITY_ID);
        start_hour = intent.getStringExtra(configuration.START_HOUR);
        finish_hour = intent.getStringExtra(configuration.FINISH_HOUR);

        getFacility();
    }

    private void getFacility() {
        class GetFacility extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(payment.this, "Get...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showFacility(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestTwoParam(configuration.URL_GET_FACILITY, facility_id, start_hour);
                return s;
            }
        }
    }

    private void showFacility(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(configuration.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}