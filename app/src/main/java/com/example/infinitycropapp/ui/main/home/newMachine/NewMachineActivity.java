package com.example.infinitycropapp.ui.main.home.newMachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;
import com.example.infinitycropapp.ui.main.home.HomeListMachineFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewMachineActivity extends AppCompatActivity {
    //-----poner aca attributes etc... -----//

    //cards
    private MaterialCardView step1Card;
    private MaterialCardView step2Card;
    private MaterialCardView step3Card;
    //state buttons cards
    private FloatingActionButton step1State;
    private FloatingActionButton step2State;
    private FloatingActionButton step3State;
    //save check button

    //bools
    private final boolean isStep1Done = false;
    private final boolean isStep2Done = false;
    private final boolean isStep3Done = false;
    //back button
    private ImageView backButton;

    //--FIN-> poner aca attributes etc... -----//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_machine);

        //findById elements
        step1Card=findViewById(R.id.step1_card);
        step2Card=findViewById(R.id.step2_card);
        step3Card=findViewById(R.id.step3_card);
        step1State=findViewById(R.id.step1_state);
        step2State=findViewById(R.id.step2_state);
        step3State=findViewById(R.id.step3_state);
        backButton=findViewById(R.id.back_newMachine);

        //onclick methods
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}