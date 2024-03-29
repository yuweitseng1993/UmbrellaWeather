package com.example.umbrellaweather.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umbrellaweather.R;

public class DayCustomViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    RecyclerView hourRecyclerView;

    public DayCustomViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.day_card_title);
        hourRecyclerView = itemView.findViewById(R.id.hour_recycler_view);
    }
}
