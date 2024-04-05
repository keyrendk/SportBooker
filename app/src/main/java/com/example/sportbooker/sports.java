package com.example.sportbooker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class sports extends AppCompatActivity {
    // deklarasi variabel yang digunakan
    private TextView textUserUsername;
    private ImageView profilePicture;
    private Button btnFootball;
    private Button btnVolleyball;
    private Button btnFutsal;
    private Button btnBasketball;
    private Button btnBadminton;

    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        // inisialisasi variabel
        textUserUsername = (TextView) findViewById(R.id.textUserUsername);
        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        btnFootball = (Button) findViewById(R.id.btnFootball);
        btnVolleyball = (Button) findViewById(R.id.btnVolleyball);
        btnFutsal = (Button) findViewById(R.id.btnFutsal);
        btnBasketball = (Button) findViewById(R.id.btnBasketball);
        btnBadminton = (Button) findViewById(R.id.btnBadminton);

        // mengambil user_id yang dikirimkan dari MainActivity
        Intent intent = getIntent();
        user_id = intent.getStringExtra(configuration.USER_ID);

        // menjalankan method getUsername untuk menampilkan username
        getUsername();


        textUserUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(sports.this, profil.class);
                intent1.putExtra(configuration.USER_ID, user_id);
                startActivity(intent1);
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(sports.this, profil.class);
                intent2.putExtra(configuration.USER_ID, user_id);
                startActivity(intent2);
            }
        });

        btnFootball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBookingFootball = getIntent();
                intentBookingFootball = new Intent(sports.this, booking.class);
                intentBookingFootball.putExtra(configuration.USER_ID, user_id);
                intentBookingFootball.putExtra(configuration.SPORT_TYPE, "Football");
                startActivity(intentBookingFootball);
            }
        });
        btnBadminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBookingBadminton = getIntent();
                intentBookingBadminton = new Intent(sports.this, booking.class);
                intentBookingBadminton.putExtra(configuration.USER_ID, user_id);
                intentBookingBadminton.putExtra(configuration.SPORT_TYPE, "Badminton");
                startActivity(intentBookingBadminton);
            }
        });
        btnBasketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBookingBasketball = getIntent();
                intentBookingBasketball = new Intent(sports.this, booking.class);
                intentBookingBasketball.putExtra(configuration.USER_ID, user_id);
                intentBookingBasketball.putExtra(configuration.SPORT_TYPE, "Basketball");
                startActivity(intentBookingBasketball);
            }
        });
        btnFutsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBookingFutsal = getIntent();
                intentBookingFutsal = new Intent(sports.this, booking.class);
                intentBookingFutsal.putExtra(configuration.USER_ID, user_id);
                intentBookingFutsal.putExtra(configuration.SPORT_TYPE, "Futsal");
                startActivity(intentBookingFutsal);
            }
        });
        btnVolleyball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBookingVolleyball = getIntent();
                intentBookingVolleyball = new Intent(sports.this, booking.class);
                intentBookingVolleyball.putExtra(configuration.USER_ID, user_id);
                intentBookingVolleyball.putExtra(configuration.SPORT_TYPE, "Volleyball");
                startActivity(intentBookingVolleyball);
            }
        });
    }

    private void getUsername() {
        class GetUsername extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(sports.this, "Get...", "Wait...", false, false);
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