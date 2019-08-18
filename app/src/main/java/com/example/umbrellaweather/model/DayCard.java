package com.example.umbrellaweather.model;

import java.util.List;

public class DayCard {
    public List<HourCard> hourData;
    public String date;

    public DayCard(List<HourCard> hd, String d){
        this.hourData = hd;
        this.date = d;
    }
}
