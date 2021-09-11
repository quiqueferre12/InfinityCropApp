package com.example.infinitycropapp.ui.main.climas.newClimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;

import com.example.infinitycropapp.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewClimateActivity extends AppCompatActivity {

    //palette
    private ConstraintLayout btn_back;
    private ConstraintLayout btn_check;
    private MaterialCardView step1_card;
    private MaterialCardView step2_card;
    private MaterialCardView step3_card;
    private MaterialCardView step4_card;
    private FloatingActionButton step1_state;
    private FloatingActionButton step2_state;
    private FloatingActionButton step3_state;
    private FloatingActionButton step4_state;

    //bools
    private boolean isStep1Done = false;
    private boolean isStep2Done = false;
    private boolean isStep3Done = false;
    private boolean isStep4Done = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_climate);

        //findById
        btn_back=findViewById(R.id.back_newClima);
        btn_check=findViewById(R.id.confirm_create_clima);
        step1_card=findViewById(R.id.step1_card);
        step2_card=findViewById(R.id.step2_card);
        step3_card=findViewById(R.id.step3_card);
        step4_card=findViewById(R.id.step4_card);
        step1_state=findViewById(R.id.step1_state);
        step2_state=findViewById(R.id.step2_state);
        step3_state=findViewById(R.id.step3_state);
        step4_state=findViewById(R.id.step4_state);


        //onclick
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}