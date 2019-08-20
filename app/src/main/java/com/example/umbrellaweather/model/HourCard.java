package com.example.umbrellaweather.model;

public class HourCard {
    public String time;
    public String condition;
    public boolean highestTemp = false;
    public boolean lowestTemp = false;
    public int degree;
    public String iconId;

    public HourCard(String t, String c, int d, String i){
        this.time = t;
        this.condition = c;
        this.degree = d;
        this.iconId = i;
    }
}
