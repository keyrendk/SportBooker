package com.example.sportbooker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class scheduleRVAdapter extends RecyclerView.Adapter<scheduleRVAdapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> scheduleDayArrayList;
    private Context context;

    public scheduleRVAdapter(ArrayList<HashMap<String, String>> scheduleDayArrayList, Context context) {
        this.scheduleDayArrayList = scheduleDayArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_list, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, String> day = scheduleDayArrayList.get(position);
        holder.day.setText(day.get(configuration.TAG_SCHEDULE_DAY));
    }

    @Override
    public int getItemCount() {
        return scheduleDayArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button day;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
        }
    }
}
