package com.example.umbrellaweather.model;

public class HourCard {
    public String time;
    public String condition;
    public boolean highestTemp = false;
    public boolean lowestTemp = false;
    public int degree;

    public HourCard(String t, String c, int d){
        this.time = t;
        this.condition = c;
        this.degree = d;
    }
}
