package com.example.umbrellaweather.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umbrellaweather.R;
import com.example.umbrellaweather.model.DayCard;

import java.util.List;

public class DayCustomAdapter extends RecyclerView.Adapter<DayCustomViewHolder> {
    private List<DayCard> dailyData;
    private Context context;
//    RecyclerView hourRecyclerView;
    HourCustomAdapter hourCustomAdapter;
    private static final String TAG = "DayCustomAdapter";

    public DayCustomAdapter(Context context){
        this.context = context;
    }

    public void setDataset(List<DayCard> dailyData){
        this.dailyData = dailyData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DayCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayCustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.day_weather_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayCustomViewHolder holder, int position) {
        holder.textView.setText(dailyData.get(position).date);
        hourCustomAdapter = new HourCustomAdapter(context);
        holder.hourRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        holder.hourRecyclerView.setAdapter(hourCustomAdapter);
        hourCustomAdapter.setDataset(dailyData.get(position).hourData);
    }

    @Override
    public int getItemCount() {
        return dailyData != null ? dailyData.size() : 0;
    }
}
