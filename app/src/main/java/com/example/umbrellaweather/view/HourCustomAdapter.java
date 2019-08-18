package com.example.umbrellaweather.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umbrellaweather.R;
import com.example.umbrellaweather.model.HourCard;

import java.util.List;

public class HourCustomAdapter extends RecyclerView.Adapter<HourCustomViewHolder> {
    private List<HourCard> hourlyData;
    private String hour;
    private int degree;
    private Context context;

    public HourCustomAdapter(Context context){
        this.context = context;
    }

    public void setDataset(List<HourCard> hourlyData){
        this.hourlyData = hourlyData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HourCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HourCustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_weather_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HourCustomViewHolder holder, int position) {
        holder.hourTime.setText(hourlyData.get(position).time);
        holder.hourTemp.setText(hourlyData.get(position).degree);
    }

    @Override
    public int getItemCount() {
        return hourlyData != null ? hourlyData.size() : 0;
    }
}
