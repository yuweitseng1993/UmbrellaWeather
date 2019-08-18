package com.example.umbrellaweather.view;

import com.example.umbrellaweather.model.DayCard;

import java.util.List;

public interface ViewContract {
    void onBindPresenter();
    void initUI();
    void initNetworkCall();
    void getAreaData(String area);
    void getWeatherData(int degree, String condition, String bg);
    void getForecastData(List<DayCard> forecastData);
    void getFailureMessage(String errorMessage);
}
