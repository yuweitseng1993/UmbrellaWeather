package com.example.umbrellaweather.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.umbrellaweather.R;

public class SplashActivity extends AppCompatActivity implements SettingsDialogFragment.OnDataPass {
    private static final String TAG = "SplashActivity";
    private int progressStatus = 0;
    private ProgressBar pbStart;
    private String zipcode= "";
    private String units = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pbStart = findViewById(R.id.pb_start);
        showSettingsDialog();
//        Log.d(TAG, "onCreate: came back from fragment -> " + zipcode + " " + units);
    }

    private void showSettingsDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SettingsDialogFragment settingsDialogFragment = SettingsDialogFragment.newInstance();
        settingsDialogFragment.show(fm, "fragment_configure");
    }

    private void goToMain(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100){
                    progressStatus += 1;
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    pbStart.setProgress(progressStatus);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("zipcode", zipcode);
                intent.putExtra("unit", units);
                startActivity(intent);
            }
        }).start();
    }

    @Override
    public void onDataPass(String zip, String unit) {
        zipcode = zip;
        units = unit;
        goToMain();
    }
}
