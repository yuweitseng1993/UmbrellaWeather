package com.example.umbrellaweather.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.umbrellaweather.model.ApiInterface;
import com.example.umbrellaweather.model.DayCard;
import com.example.umbrellaweather.model.ForecastPojo;
import com.example.umbrellaweather.model.HourCard;
import com.example.umbrellaweather.model.WeatherPojo;
import com.example.umbrellaweather.model.ZipPojo;
import com.example.umbrellaweather.view.MainActivity;
import com.example.umbrellaweather.view.ViewContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Presenter implements PresenterContract {
    private ViewContract view;
    private static final String TAG = "Presenter";

    @Override
    public void onBindView(ViewContract view) {
        this.view = view;
    }

    @Override
    public void unBind() {
        view = null;
    }

    @Override
    public void initRetrofit() {
        retrofitGetArea();
        retrofitGetWeather();
        retrofitGetForecast();
    }

    @Override
    public void retrofitGetArea() {
        Log.d(TAG, "retrofitGetArea: ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ziptasticapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(ApiInterface.class).getLocation(MainActivity.getZipcode()).enqueue(new Callback<ZipPojo>() {
            @Override
            public void onResponse(Call<ZipPojo> call, Response<ZipPojo> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body().state + " " + response.body().city);
                    onAreaDataSuccess(response.body());
                }

            }
            @Override
            public void onFailure(Call<ZipPojo> call, Throwable t) {
                onAreaDataFailure(t.getMessage());
            }
        });
    }

    @Override
    public void retrofitGetWeather() {
        Log.d(TAG, "retrofitGetWeather: ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String unit = MainActivity.getUnits();
        if(unit.equals("\u2103")){
            unit = "metric";
        }
        else{
            unit = "imperial";
        }
        retrofit.create(ApiInterface.class).getWeather(MainActivity.getZipcode(), unit, "7490975c5103a3dd7f328b5dcf195458").enqueue(new Callback<WeatherPojo>() {
            @Override
            public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: temp -> " + response.body().main.temp +
                            " temp_min -> " + response.body().main.temp_min +
                            " temp_max -> " + response.body().main.temp_max +
                            " condition -> " + response.body().weather.get(0).main);
                    onWeatherDataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherPojo> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onWeatherDataFailure(t.getMessage());
            }
        });
    }

    @Override
    public void retrofitGetForecast() {
        Log.d(TAG, "retrofitGetForecast: ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String unit = MainActivity.getUnits();
        if(unit.equals("\u2103")){
            unit = "metric";
        }
        else{
            unit = "imperial";
        }
        retrofit.create(ApiInterface.class).getForecast(MainActivity.getZipcode(), unit, "7490975c5103a3dd7f328b5dcf195458").enqueue(new Callback<ForecastPojo>() {
            @Override
            public void onResponse(Call<ForecastPojo> call, Response<ForecastPojo> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse:  response.body().size()-> " + response.body().list.size() +
                            " temp -> " + response.body().list.get(0).main.temp +
                            " temp_min -> " + response.body().list.get(0).main.temp_min +
                            " temp_max -> " + response.body().list.get(0).main.temp_max +
                            " dt_txt -> " + response.body().list.get(0).dt_txt);
                    onForecastDataSuccess(response.body());
                }

            }

            @Override
            public void onFailure(Call<ForecastPojo> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onForecastDataFailure(t.getMessage());
            }
        });
    }

    @Override
    public void onAreaDataSuccess(ZipPojo area) {
        String cityString = area.city;
        String userLoc = cityString.substring(0, 1).toUpperCase() + cityString.substring(1).toLowerCase() + ", " + area.state;
        Log.d(TAG, "onAreaDataSuccess: userLoc -> " + userLoc);
        view.getAreaData(userLoc);
    }

    @Override
    public void onAreaDataFailure(String errorMessage) {
        view.getFailureMessage(errorMessage);
    }

    @Override
    public void onWeatherDataSuccess(WeatherPojo item) {
        int curTemp = (int)item.main.temp;
        String condition = item.weather.get(0).main;
        String bgColor;
        if(MainActivity.getUnits().equals("\u2103")){
            if(curTemp < 15){
                bgColor = "cold";
            }
            else{
                bgColor = "warm";
            }
        }
        else{
            if(curTemp < 60){
                bgColor = "cold";
            }
            else{
                bgColor = "warm";
            }
        }
        view.getWeatherData(curTemp, condition, bgColor);
    }

    @Override
    public void onWeatherDataFailure(String errorMessage) {
        view.getFailureMessage(errorMessage);
    }

    @Override
    public void onForecastDataSuccess(ForecastPojo dataSet) {
        List<DayCard> forecastDataSet = new ArrayList<>();
        List<HourCard> hourDataSet = new ArrayList<>();
        String curDate = dataSet.list.get(0).dt_txt.split(" ")[0];
        int dayNum = 1;
        int dayLowestTemp = (int)dataSet.list.get(0).main.temp;;
        int dayHighestTemp = (int)dataSet.list.get(0).main.temp;;
        for(int i = 0; i < dataSet.list.size(); i++){
            if(dataSet.list.get(i).dt_txt.split(" ")[0].equals(curDate)){
                int timeNum = Integer.parseInt((dataSet.list.get(i).dt_txt.split(" ")[1]).split(":")[0]);
                String time;
                if(timeNum > 12){
                    time = (timeNum - 12) + ":00 PM";
                }
                else if(timeNum == 12){
                    time = timeNum + ":00 PM";
                }
                else{
                    time = timeNum + ":00 AM";
                }
                int degree = (int)dataSet.list.get(i).main.temp;
                if(degree < dayLowestTemp){
                    dayLowestTemp = degree;
                }
                if(degree > dayHighestTemp){
                    dayHighestTemp = degree;
                }
                hourDataSet.add(new HourCard(time, dataSet.list.get(i).weather.get(0).main, degree));
            }
            else{
                sortForecastDataset(hourDataSet, dayLowestTemp, dayHighestTemp);
                if(dayNum == 1){
                    forecastDataSet.add(new DayCard(hourDataSet, "Today"));
                }
                else if(dayNum == 2){
                    forecastDataSet.add(new DayCard(hourDataSet, "Tomorrow"));
                }
                else{
                    String date = curDate.split("-")[1] + "/" + curDate.split("-")[2];
                    forecastDataSet.add(new DayCard(hourDataSet, date));
                }
                dayNum += 1;
                curDate = dataSet.list.get(i).dt_txt.split(" ")[0];
                hourDataSet = new ArrayList<>();
                int timeNum = Integer.parseInt((dataSet.list.get(i).dt_txt.split(" ")[1]).split(":")[0]);
                String time;
                if(timeNum > 12){
                    time = (timeNum - 12) + ":00 PM";
                }
                else if(timeNum == 12){
                    time = timeNum + ":00 PM";
                }
                else{
                    time = timeNum + ":00 AM";
                }
                int degree = (int)dataSet.list.get(i).main.temp;
                hourDataSet.add(new HourCard(time, dataSet.list.get(i).weather.get(0).main, degree));
            }
        }
        view.getForecastData(forecastDataSet);
    }

    private void sortForecastDataset(List<HourCard> dataSet, int lowest, int highest){
        for(int i = 0; i < dataSet.size(); i++){
            if(dataSet.get(i).degree == lowest){
                dataSet.get(i).lowestTemp = true;
            }
            if(dataSet.get(i).degree == highest){
                dataSet.get(i).highestTemp = true;
            }
        }
    }

    @Override
    public void onForecastDataFailure(String errorMessage) {
        view.getFailureMessage(errorMessage);
    }
}
