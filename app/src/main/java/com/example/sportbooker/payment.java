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
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

public class payment extends AppCompatActivity {
    private TextView dataDate, dataFacilityName, dataStartHour, dataFinishHour, dataAmount;
    private Button qris, dana, ovo, payNow;
    private String user_id;
    private String schedule_id, day_name, booking_id, facility_id, start_hour, finish_hour, paymentMethod, booking_status;

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
                addBooking();
                getBooking();
                updateScheduleStatus();

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
            String facility = c.getString(configuration.TAG_FACILITY_FACILITY_ID);
            String date = c.getString(configuration.TAG_SCHEDULE_DATE);
            String facility_name = c.getString(configuration.TAG_FACILITY_FACILITY_NAME);
            String start_hour = c.getString(configuration.TAG_SCHEDULE_START_HOUR);
            String finish_hour = c.getString(configuration.TAG_SCHEDULE_FINISH_HOUR);
            String amount = c.getString(configuration.TAG_FACILITY_PRICE);

            schedule_id = schedule;
            facility_id = facility;
            dataDate.setText(date);
            dataFacilityName.setText(facility_name);
            dataStartHour.setText(start_hour);
            dataFinishHour.setText(finish_hour);
            dataAmount.setText(amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addBooking() {
        final String userId = user_id;
        final String scheduleId = schedule_id;
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String booking_date = dateFormat.format(currentDate);
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
                params.put(configuration.KEY_USER_ID, userId);
                params.put(configuration.KEY_SCHEDULE_ID, scheduleId);
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
                    String bookingId = c.getString(configuration.TAG_BOOKING_ID);
                    booking_id = bookingId;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                addTransaction();
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
        final String userId = user_id;
        final String facilityId = facility_id;
        final String bookingId = booking_id;
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
                params.put(configuration.KEY_USER_ID, userId);
                params.put(configuration.KEY_FACILITY_ID, facilityId);
                params.put(configuration.KEY_BOOKING_ID, bookingId);
                params.put(configuration.KEY_TRANSACTION_STATUS, transaction_status);
                params.put(configuration.KEY_AMOUNT, amount);
                params.put(configuration.KEY_PAYMENT_METHOD, paymentMethod);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(configuration.URL_ADD_TRANSACTION, params);
                return res;
            }
        }
        AddTransaction at = new AddTransaction();
        at.execute();
    }

    private void updateScheduleStatus() {
        final String scheduleId = schedule_id;
        final String facilityId = facility_id;
        final String schedule_date = dataDate.getText().toString().trim();
        final String startHour = start_hour;
        final String finishHour = finish_hour;
        final String new_schedule_status = "Not Available";
        final String dayName = day_name;

        class UpdateScheduleStatus extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(payment.this, "Update...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(configuration.KEY_SCHEDULE_ID, scheduleId);
                hashMap.put(configuration.KEY_FACILITY_ID, facilityId);
                hashMap.put(configuration.KEY_SCHEDULE_DATE, schedule_date);
                hashMap.put(configuration.KEY_START_HOUR, startHour);
                hashMap.put(configuration.KEY_FINISH_HOUR, finishHour);
                hashMap.put(configuration.KEY_SCHEDULE_STATUS, new_schedule_status);
                hashMap.put(configuration.KEY_SCHEDULE_DAY, dayName);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(configuration.URL_UPDATE_SCHEDULE, hashMap);
                return s;
            }
        }
        UpdateScheduleStatus uss = new UpdateScheduleStatus();
        uss.execute();
    }
}