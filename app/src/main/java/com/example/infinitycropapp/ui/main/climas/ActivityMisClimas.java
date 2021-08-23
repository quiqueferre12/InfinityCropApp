package com.example.infinitycropapp.ui.main.climas;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.infinitycropapp.R;

public class ActivityMisClimas extends AppCompatActivity {


    //-----poner aca attributes etc... -----//

    private ConstraintLayout btn_back;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_climas);


        //findById elements
        btn_back=findViewById(R.id.back_lay);


        //onclicks
        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
