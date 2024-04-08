package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
    private String user_id;
    private String schedule_id;
    private String booking_id;
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
        user_id = intent.getStringExtra(configuration.USER_ID);
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
                paymentMethod = dana.getText().toString().trim();
            }
        });

        ovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod = ovo.getText().toString().trim();
            }
        });

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addBooking();
//                addTransaction();

                AlertDialog.Builder builder = new AlertDialog.Builder(payment.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_dialog_payment, null);
                builder.setView(dialogView);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final Button buttonDone = dialogView.findViewById(R.id.buttonDone);
                buttonDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(payment.this, sports.class);
                        startActivity(intent);
                    }
                });
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
                String s = rh.sendGetFacilityRequest(configuration.URL_GET_SCHEDULE_DETAIL, facility_id, start_hour, day_name);
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
            String schedule = c.getString(configuration.TAG_SCHEDULE_ID);
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
            schedule_id = schedule;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addBooking() {
        final String booking_date = dataDate.getText().toString().trim();
        final String booking_status = "Confirmed";

        class AddBooking extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(payment.this, "Add...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(configuration.KEY_USER_ID, user_id);
                params.put(configuration.KEY_SCHEDULE_ID, schedule_id);
                params.put(configuration.KEY_BOOKING_DATE, booking_date);
                params.put(configuration.KEY_BOOKING_STATUS, booking_status);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(configuration.URL_ADD_BOOKING, params);
                return res;
            }
        }
        AddBooking ab = new AddBooking();
        ab.execute();
    }

    private void getBooking() {
        class GetBooking extends AsyncTask<Void, Void, String> {
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
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray(configuration.TAG_JSON_ARRAY);
                    JSONObject c = result.getJSONObject(0);
                    booking_id = c.getString(configuration.TAG_BOOKING_ID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... v) {
                RequestHandler rh = new RequestHandler();
                String res = rh.sendGetRequestParam(configuration.URL_GET_BOOKING_ID, schedule_id);
                return res;
            }
        }
        GetBooking gb = new GetBooking();
        gb.execute();
    }

    private void addTransaction() {
        final String transaction_status = "Confirmed";
        final String amount = dataAmount.getText().toString().trim();
        class AddTransaction extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(payment.this, "Add...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(configuration.KEY_USER_ID, user_id);
                params.put(configuration.KEY_FACILITY_ID, facility_id);
                params.put(configuration.KEY_BOOKING_ID, booking_id);
                params.put(configuration.KEY_TRANSACTION_STATUS, transaction_status);
                params.put(configuration.KEY_AMOUNT, amount);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(configuration.URL_ADD_TRANSACTION, params);
                return res;
            }
        }
        AddTransaction at = new AddTransaction();
        at.execute();
    }
}