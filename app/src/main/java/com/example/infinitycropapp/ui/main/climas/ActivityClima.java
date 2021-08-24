package com.example.infinitycropapp.ui.main.climas;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.infinitycropapp.R;

public class ActivityClima extends AppCompatActivity {


    //-----poner aca attributes etc... -----//

    private ConstraintLayout btn_back;
    private TextView textViewClimateName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climate);
        textViewClimateName=findViewById(R.id.textViewClimateName);

        //findById elements
        btn_back=findViewById(R.id.climate_btn_back);


        //onclicks
        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        setClimateData();
    }

    private void setClimateData(){
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("idClimate");
        textViewClimateName.setText(id);
    }
}