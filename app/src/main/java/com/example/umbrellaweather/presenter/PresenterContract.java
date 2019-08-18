package com.example.umbrellaweather.presenter;

import com.example.umbrellaweather.model.ForecastPojo;
import com.example.umbrellaweather.model.WeatherPojo;
import com.example.umbrellaweather.model.ZipPojo;
import com.example.umbrellaweather.view.ViewContract;

import java.util.List;

public interface PresenterContract {
    void onBindView(ViewContract view);
    void unBind();
    void initRetrofit();
    void retrofitGetArea();
    void retrofitGetWeather();
    void retrofitGetForecast();
    void onAreaDataSuccess(ZipPojo area);
    void onAreaDataFailure(String errorMessage);
    void onWeatherDataSuccess(WeatherPojo item);
    void onWeatherDataFailure(String errorMessage);
    void onForecastDataSuccess(ForecastPojo dataSet);
    void onForecastDataFailure(String errorMessage);
}
