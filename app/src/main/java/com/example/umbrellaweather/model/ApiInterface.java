package com.example.umbrellaweather.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //http://ziptasticapi.com/95014
    @GET("/{zipcode}")
    Call<ZipPojo> getLocation(@Path("zipcode") String zipCode);

    //https://api.openweathermap.org/data/2.5/weather?zip=30030&units=metric&appid=7490975c5103a3dd7f328b5dcf195458
    @GET("/data/2.5/weather?")
    Call<WeatherPojo> getWeather(@Query("zip")String zipcode,
                                 @Query("units")String unit,
                                 @Query("appid")String apikey);

    //https://api.openweathermap.org/data/2.5/forecast?zip=30030&units=metric&appid=7490975c5103a3dd7f328b5dcf195458
    @GET("/data/2.5/forecast?")
    Call<ForecastPojo> getForecast(@Query("zip")String zipcode,
                                 @Query("units")String unit,
                                 @Query("appid")String apikey);
}
