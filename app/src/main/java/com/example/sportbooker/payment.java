package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
    private String paymentMethod;



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
        dana = (Button) findViewById(R.id.dana);
        ovo = (Button) findViewById(R.id.ovo);
        payNow = (Button) findViewById(R.id.buttonPayNow);

        Intent intent = getIntent();
        day_name = intent.getStringExtra(configuration.SCHEDULE_DAY);
        facility_id = intent.getStringExtra(configuration.FACILITY_ID);
        start_hour = intent.getStringExtra(configuration.START_HOUR);
        finish_hour = intent.getStringExtra(configuration.FINISH_HOUR);

        getFacility();

        // setOnClickListener
        qris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod = qris.getText().toString().trim();
            }
        });

        dana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
                String s = rh.sendGetFacilityRequest(configuration.URL_GET_FACILITY, facility_id, start_hour, day_name);
                return s;
            }
        }
        GetFacility gf = new GetFacility();
        gf.execute();
    }

    private void showFacility(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(configuration.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String date = c.getString(configuration.TAG_SCHEDULE_DATE);
            String facility_name = c.getString(configuration.TAG_FACILITY_FACILITY_NAME);
            String start_hour = c.getString(configuration.TAG_SCHEDULE_START_HOUR);
            String finish_hour = c.getString(configuration.TAG_SCHEDULE_FINISH_HOUR);
            String amount = c.getString(configuration.TAG_FACILITY_PRICE);

            dataDate.setText(date);
            dataFacilityName.setText(facility_name);
            dataStartHour.setText(start_hour);
            dataFinishHour.setText(finish_hour);
            dataAmount.setText(amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}