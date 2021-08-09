package com.example.infinitycropapp.ui.main.home.models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;

import com.example.infinitycropapp.R;
//clase principal del modelo IC6 donde estara lo principal del control de esta.
public class IC6Activity extends AppCompatActivity {

    //palette
    ConstraintLayout btn_back;
    // FIN -> palette

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ic6);

        //findById
        btn_back=findViewById(R.id.btn_back_ic6_model);

        //onclick methods
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}