package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class payment extends AppCompatActivity {
    private TextView dataDate;
    private TextView dataFacilityName;
    private TextView dataStartHour;
    private TextView dataFinishHour;
    private TextView dataAmount;
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

        Intent intent = getIntent();
        day_name = intent.getStringExtra(configuration.SCHEDULE_DAY);
        facility_id = intent.getStringExtra(configuration.FACILITY_ID);
        start_hour = intent.getStringExtra(configuration.START_HOUR);
        finish_hour = intent.getStringExtra(configuration.FINISH_HOUR);

        dataStartHour.setText(start_hour);
        dataFinishHour.setText(finish_hour);
    }
}