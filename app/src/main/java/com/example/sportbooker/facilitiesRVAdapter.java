package com.example.sportbooker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class facilitiesRVAdapter extends RecyclerView.Adapter<facilitiesRVAdapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> facilityList;
    private Context context;
    private FacilityButtonClick facilityButtonClick;
    private String startHour;

    public facilitiesRVAdapter(ArrayList<HashMap<String, String>> facilityList, Context context) {
        this.facilityList = facilityList;
        this.context = context;
    }

    public void setButtonOnClickListener(FacilityButtonClick facilityButtonClick) {
        this.facilityButtonClick = facilityButtonClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, String> facility = facilityList.get(position);
        holder.facilityName.setText(facility.get(configuration.TAG_FACILITY_FACILITY_NAME));
        holder.facilityType.setText(facility.get(configuration.TAG_FACILITY_FACILITY_TYPE));
        holder.facilityDescription.setText(facility.get(configuration.TAG_FACILITY_DESCRIPTION));
        holder.facilityPrice.setText(facility.get(configuration.TAG_FACILITY_PRICE));

        holder.startHour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHour = holder.startHour1.getText().toString();
                Toast.makeText(context,"Start Hour : " + startHour, Toast.LENGTH_SHORT).show();
            }
        });

        holder.startHour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHour = holder.startHour2.getText().toString();
                Toast.makeText(context, "Start Hour : " + startHour, Toast.LENGTH_SHORT).show();
            }
        });

        holder.startHour3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHour = holder.startHour3.getText().toString();
                Toast.makeText(context, "Start Hour : " + startHour, Toast.LENGTH_SHORT).show();
            }
        });

        holder.startHour4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHour = holder.startHour4.getText().toString();
                Toast.makeText(context,"Start Hour : " + startHour, Toast.LENGTH_SHORT).show();
            }
        });

        holder.startHour5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHour = holder.startHour5.getText().toString();
                Toast.makeText(context, "Start Hour : " + startHour, Toast.LENGTH_SHORT).show();
            }
        });

        holder.startHour6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHour = holder.startHour6.getText().toString();
                Toast.makeText(context, "Start Hour : " + startHour, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return facilityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView facilityName;
        TextView facilityType;
        TextView facilityPrice;
        TextView facilityDescription;
        Button startHour1;
        Button startHour2;
        Button startHour3;
        Button startHour4;
        Button startHour5;
        Button startHour6;


        public ViewHolder(View itemView) {
            super(itemView);
            facilityName = itemView.findViewById(R.id.facilityName);
            facilityType = itemView.findViewById(R.id.facilityType);
            facilityDescription = itemView.findViewById(R.id.facilityDescription);
            facilityPrice = itemView.findViewById(R.id.facilityPrice);
            startHour1 = itemView.findViewById(R.id.startHour1);
            startHour2 = itemView.findViewById(R.id.startHour2);
        }
    }

    public interface FacilityButtonClick {
        void onButtonClick(int position, String buttonText);
    }
}
