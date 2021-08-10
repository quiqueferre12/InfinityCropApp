package com.example.infinitycropapp.ui.main.home.models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import com.example.infinitycropapp.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

//clase principal del modelo IC6 donde estara lo principal del control de esta.
public class IC6Activity extends AppCompatActivity {

    //palette
    ConstraintLayout btn_back;
    MaterialButton btn_action_machine;
    MaterialButton btn_clima;
    MaterialButton btn_extraction;
    // FIN -> palette

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ic6);

        //findById
        btn_back=findViewById(R.id.btn_back_ic6_model);
        btn_action_machine=findViewById(R.id.ic6_model_btn_action_machine);
        btn_extraction=findViewById(R.id.ic6_model_btn_extraction);
        btn_clima=findViewById(R.id.ic6_model_btn_clima);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        //onclick methods
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //btn action machine
        btn_action_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set the bottom sheet
                BottomSheetDialog optionsBottomSheet = new BottomSheetDialog(IC6Activity.this);
                //set the layout of the bottom sheet
                optionsBottomSheet.setContentView(R.layout.machine_action_bottom_sheet);
                //findById
                CoordinatorLayout layout= optionsBottomSheet.findViewById(R.id.ic6_model_layout_machine_bs);
                ConstraintLayout btn_back= optionsBottomSheet.findViewById(R.id.ic6_model_close_machine_bs);
                //actions
                layout.setMinimumHeight(height);
                //hacer que cuando se muestre ocupe toda la pantalla
                optionsBottomSheet.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        BottomSheetDialog d = (BottomSheetDialog) dialog;
                        FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
                        BottomSheetBehavior.from(bottomSheet)
                                .setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                });
                //onclick
                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsBottomSheet.dismiss();
                    }
                });
                optionsBottomSheet.show();

            }
        });

    }
}