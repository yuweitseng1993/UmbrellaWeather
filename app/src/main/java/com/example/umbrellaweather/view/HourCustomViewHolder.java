package com.example.umbrellaweather.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umbrellaweather.R;

public class HourCustomViewHolder extends RecyclerView.ViewHolder {
    TextView hourTemp, hourTime;
    ImageView hourIcon;

    public HourCustomViewHolder(@NonNull View itemView) {
        super(itemView);
        hourTemp = itemView.findViewById(R.id.hour_card_degree);
        hourTime = itemView.findViewById(R.id.hour_card_time);
        hourIcon = itemView.findViewById(R.id.hour_card_icon);
    }
}
