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
    private String facility_id;
    private String startHour;
    private String finishHour;

    public facilitiesRVAdapter(ArrayList<HashMap<String, String>> facilityList, Context context) {
        this.facilityList = facilityList;
        this.context = context;
    }

    public void setFacilityButtonClick(FacilityButtonClick facilityButtonClick) {
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
        holder.facilityID = facility.get(configuration.TAG_FACILITY_FACILITY_ID);
        holder.facilityName.setText(facility.get(configuration.TAG_FACILITY_FACILITY_NAME));
        holder.facilityType.setText(facility.get(configuration.TAG_FACILITY_FACILITY_TYPE));
        holder.facilityDescription.setText(facility.get(configuration.TAG_FACILITY_DESCRIPTION));
        holder.facilityPrice.setText(facility.get(configuration.TAG_FACILITY_PRICE));

        holder.startHour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                facility_id = holder.facilityID;
                startHour = "08:00:00";
                finishHour = "10:00:00";
                facilityButtonClick.onButtonClick(position, facility_id, startHour, finishHour);
            }
        });

        holder.startHour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                facility_id = holder.facilityID;
                startHour = "10:00:00";
                finishHour = "12:00:00";
                facilityButtonClick.onButtonClick(position, facility_id, startHour, finishHour);
            }
        });

        holder.startHour3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                facility_id = holder.facilityID;
                startHour = "13:00:00";
                finishHour = "15:00:00";
                facilityButtonClick.onButtonClick(position, facility_id, startHour, finishHour);
            }
        });

        holder.startHour4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                facility_id = holder.facilityID;
                startHour = "15:00:00";
                finishHour = "17:00:00";
                facilityButtonClick.onButtonClick(position, facility_id, startHour, finishHour);
            }
        });

        holder.startHour5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                facility_id = holder.facilityID;
                startHour = "17:00:00";
                finishHour = "19:00:00";
                facilityButtonClick.onButtonClick(position, facility_id, startHour, finishHour);
            }
        });

        holder.startHour6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                facility_id = holder.facilityID;
                startHour = "19:00:00";
                finishHour = "21:00:00";
                facilityButtonClick.onButtonClick(position, facility_id, startHour, finishHour);
            }
        });
    }

    @Override
    public int getItemCount() {
        return facilityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        String facilityID;
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
            facilityID = "";
            facilityName = itemView.findViewById(R.id.facilityName);
            facilityType = itemView.findViewById(R.id.facilityType);
            facilityDescription = itemView.findViewById(R.id.facilityDescription);
            facilityPrice = itemView.findViewById(R.id.facilityPrice);
            startHour1 = itemView.findViewById(R.id.startHour1);
            startHour2 = itemView.findViewById(R.id.startHour2);
            startHour3 = itemView.findViewById(R.id.startHour3);
            startHour4 = itemView.findViewById(R.id.startHour4);
            startHour5 = itemView.findViewById(R.id.startHour5);
            startHour6 = itemView.findViewById(R.id.startHour6);
        }
    }

    public interface FacilityButtonClick {
        void onButtonClick(int position, String facilityId, String startHour, String finishHour);
    }
}
