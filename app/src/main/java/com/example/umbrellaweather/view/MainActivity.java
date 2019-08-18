package com.example.umbrellaweather.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umbrellaweather.R;
import com.example.umbrellaweather.model.DayCard;
import com.example.umbrellaweather.model.ForecastPojo;
import com.example.umbrellaweather.model.WeatherPojo;
import com.example.umbrellaweather.model.ZipPojo;
import com.example.umbrellaweather.presenter.Presenter;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewContract, SettingsDialogFragment.OnDataPass {
    public static String zipcode;
    public static String units;
    private static final String TAG = "MainActivity";
    Presenter presenter;
    ImageView settingsButton;
    RecyclerView dayRecyclerView;
    TextView curWeatherArea, curWeatherDegree, curWeatherCondition;
    RelativeLayout curWeatherBg;
    DayCustomAdapter dayCustomAdapter;
    public static RecyclerView hourRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsButton = findViewById(R.id.iv_setting_btn);

        Intent intent = getIntent();
        zipcode = intent.getStringExtra("zipcode");
        units = intent.getStringExtra("unit");

        initUI();
        onBindPresenter();
        initNetworkCall();

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettingsDialog();
            }
        });
    }

    private void showSettingsDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SettingsDialogFragment settingsDialogFragment = SettingsDialogFragment.newInstance();
        settingsDialogFragment.show(fm, "fragment_configure");
    }

    @Override
    public void onBindPresenter() {
        presenter = new Presenter();
        presenter.onBindView(this);
    }

    @Override
    public void initUI() {
        curWeatherArea = findViewById(R.id.tv_weather_area);
        curWeatherDegree = findViewById(R.id.tv_weather_degree);
        curWeatherCondition = findViewById(R.id.tv_weather_condition);
        curWeatherBg = findViewById(R.id.rl_cur_weather);
        dayRecyclerView = findViewById(R.id.day_recycler_view);

        dayRecyclerView = findViewById(R.id.day_recycler_view);
        dayRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dayCustomAdapter = new DayCustomAdapter(this);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(dayRecyclerView);
        dayRecyclerView.setAdapter(dayCustomAdapter);
//        hourRecyclerView = findViewById(R.id.hour_recycler_view);
    }

    @Override
    public void initNetworkCall() {
        presenter.retrofitGetArea();
        presenter.retrofitGetWeather();
        presenter.retrofitGetForecast();
    }

    @Override
    public void getAreaData(String area) {
        presentAreaData(area);
    }

    private void presentAreaData(String area){
        curWeatherArea.setText(area);
    }

    @Override
    public void getWeatherData(int degree, String condition, String bg) {
        presentWeatherData(degree, condition, bg);
    }

    private void presentWeatherData(int degree, String condition, String bg){
        if(units.equals("\u2103")){
            curWeatherDegree.setText(degree + " \u2103");
        }
        else{
            curWeatherDegree.setText(degree + " \u2109");
        }
        curWeatherCondition.setText(condition);
        if(bg.equals("cold")){
            curWeatherBg.setBackgroundColor(getResources().getColor(R.color.coldWeather));
        }
        else{
            curWeatherBg.setBackgroundColor(getResources().getColor(R.color.hotWeather));
        }
    }

    @Override
    public void getForecastData(List<DayCard> forecastData) {
        Log.d(TAG, "getForecastData: " + Arrays.toString(forecastData.toArray()));
        presentForecastData(forecastData);
    }

    private void presentForecastData(List<DayCard> forecastData){
        dayCustomAdapter.setDataset(forecastData);
    }

    @Override
    public void getFailureMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDataPass(String zip, String unit) {
        zipcode = zip;
        units = unit;
        initNetworkCall();
    }

    public static String getZipcode(){
        return zipcode;
    }

    public static String getUnits(){
        return units;
    }
}
