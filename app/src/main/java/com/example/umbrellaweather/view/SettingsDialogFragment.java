package com.example.umbrellaweather.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.umbrellaweather.R;
import com.google.android.material.textfield.TextInputEditText;

public class SettingsDialogFragment extends DialogFragment {
    private TextInputEditText userZipcode;
    private ToggleButton tbtnC, tbtnF;
    private Button btn_save;
    private String zipcode = "";
    private String unit = "";
    OnDataPass dataPasser;

    public interface OnDataPass {
        void onDataPass(String zipcode, String units);
    }

    public SettingsDialogFragment(){}

    public static SettingsDialogFragment newInstance(){
        SettingsDialogFragment frag = new SettingsDialogFragment();
        Bundle args= new Bundle();
        args.putString("title", "Settings");
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(R.layout.settings_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userZipcode = view.findViewById(R.id.et_zipcode);
        tbtnC = view.findViewById(R.id.tb_celsius);
        tbtnF = view.findViewById(R.id.tb_fahrenheit);
        btn_save = view.findViewById(R.id.btn_config_save);
        String title = getArguments().getString("title");
        getDialog().setTitle(title);
        tbtnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getResources().getColor(R.color.coldWeather));
                tbtnF.setBackgroundColor(getResources().getColor(R.color.splashBackground));
                unit = "\u2103";
            }
        });
        tbtnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getResources().getColor(R.color.coldWeather));
                tbtnC.setBackgroundColor(getResources().getColor(R.color.splashBackground));
                unit = "\u2109";
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipcode = userZipcode.getText().toString();
                if(checkFields()){
                    dataPasser.onDataPass(zipcode, unit);
                    dismiss();
                }
            }
        });
    }

    private boolean checkFields(){
        if(zipcode.length() > 0 && unit.length() > 0){
            return true;
        }
        else{
            if(zipcode.length() == 0){
                Toast.makeText(getContext(), "Please enter your zip code.", Toast.LENGTH_SHORT).show();
            }
            if(unit.length() == 0){
                Toast.makeText(getContext(), "Please choose a unit for temperature display.", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }
}
