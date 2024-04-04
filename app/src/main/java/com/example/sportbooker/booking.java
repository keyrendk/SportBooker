package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class booking extends AppCompatActivity {
    // deklarasi variabel
    private TextView textUserUsername;
    private ImageView profilePicture;
    private TextView textSportType;
    private String user_id;
    private String sport_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // inisialisasi variabel
        textUserUsername = (TextView) findViewById(R.id.textUserUsername);
        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        textSportType = (TextView) findViewById(R.id.textSportType);

        // mengambil user_id dan sport_type dari sports
        Intent intent = getIntent();
        user_id = intent.getStringExtra(configuration.USER_ID);
        sport_type = intent.getStringExtra(configuration.SPORT_TYPE);

        // menjalankan method getUsername untuk menampilkan username
        getUsername();
        textSportType.setText(sport_type);

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
}