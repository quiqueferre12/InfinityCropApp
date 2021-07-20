package com.example.infinitycropapp.ui.main.home.newMachine;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;
import com.example.infinitycropapp.ui.main.home.HomeListMachineFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Objects;

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
    //step 2 bottom sheet
    private TextInputEditText name_machineInput;
    private ImageView quit_step2_bottom_sheet;
    private ImageView confirm_step2_bottom_sheet;
    private CheckBox checkbox_step2_bottom_sheet;
    private TextInputLayout layout_name_machineInput;
    //string , int ....
    private String machineCode;
    private String nameMachine;
    private boolean isFavorite;

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

        //call methods
        ClearAllState(); //all to default

        //onclick methods
        backButton.setOnClickListener(new View.OnClickListener() { //btn volver atras
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        step1Card.setOnClickListener(new View.OnClickListener() { //step1 card click
            @Override
            public void onClick(View v) {
                QrScan();
            }
        });
        step2Card.setOnClickListener(new View.OnClickListener() { //step1 card click
            @Override
            public void onClick(View v) {
                initStep2BottomSheet();
            }
        });
        step3Card.setOnClickListener(new View.OnClickListener() { //step1 card click
            @Override
            public void onClick(View v) {

            }
        });
    }

    //init qr scan
    private void QrScan(){
        IntentIntegrator integrator= new IntentIntegrator(NewMachineActivity.this);
        integrator.setBeepEnabled(false);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt(getResources().getString(R.string.step1Text_newMachine));
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                machineCode="";
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                step1StateInactive();
            } else {
                machineCode=result.getContents().toString();
                Toast.makeText(this, "Scanned: " + machineCode, Toast.LENGTH_LONG).show();
                step1StateActive();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void step1StateActive(){
        step1State.setImageResource(R.drawable.icons_active_state); //change icon to active
        step1State.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color))); //change color to active
    }
    private void step1StateInactive(){
        step1State.setImageResource(R.drawable.icons_inactive_state); //change icon to Inactive
        step1State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
    }
    private void step2StateActive(){
        step2State.setImageResource(R.drawable.icons_active_state); //change icon to active
        step2State.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color))); //change color to active
    }
    private void step2StateInactive(){
        step2State.setImageResource(R.drawable.icons_inactive_state); //change icon to Inactive
        step2State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
    }
    private void step3StateActive(){
        step3State.setImageResource(R.drawable.icons_active_state); //change icon to active
        step3State.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color))); //change color to active
    }
    private void step3StateInactive(){
        step3State.setImageResource(R.drawable.icons_inactive_state); //change icon to Inactive
        step3State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
    }

    private void ClearAllState(){
        machineCode=""; //clear el codigo escaneado
        nameMachine=""; //clear el nombre puesto
        //clear todos los iconos a incompleto
        step1State.setImageResource(R.drawable.icons_inactive_state);
        step1State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
        step2State.setImageResource(R.drawable.icons_inactive_state);
        step2State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
        step3State.setImageResource(R.drawable.icons_inactive_state);
        step3State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
    }



    private void initStep2BottomSheet(){
        //set the bottom sheet
        BottomSheetDialog optionsBottomSheet = new BottomSheetDialog(NewMachineActivity.this);
        //set the layout of the bottom sheet
        optionsBottomSheet.setContentView(R.layout.activity_new_machine_step2_bottom_sheet);
        //findbyid del bottom sheet
        name_machineInput=optionsBottomSheet.findViewById(R.id.step2_editText_new_machine);
        quit_step2_bottom_sheet=optionsBottomSheet.findViewById(R.id.quit_step2_bottom_sheet);
        confirm_step2_bottom_sheet=optionsBottomSheet.findViewById(R.id.confirm_step2_bottom_sheet);
        checkbox_step2_bottom_sheet=optionsBottomSheet.findViewById(R.id.step2_checkbox_new_machine);
        layout_name_machineInput=optionsBottomSheet.findViewById(R.id.step2_textField_new_machine);
        //onclick methods
        //si ya ha insertado el nombre
        if(!nameMachine.equals("")){
            name_machineInput.setText(nameMachine); //mostrarlo
        }
        //si ya lo ha anyadido a favoritos
        if(isFavorite){
            checkbox_step2_bottom_sheet.setChecked(true);
        }
        //quit bottomsheet
        quit_step2_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsBottomSheet.dismiss();
            }
        });
        //confirm data of the bottom sheet
        confirm_step2_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textInput=name_machineInput.getText().toString(); //get the texto of the input
                if(textInput.equals("")){ //si no hay nombre en el editext
                    layout_name_machineInput.setErrorEnabled(true); //activar error
                    layout_name_machineInput.setError("QUIEOR MORIR"); //set texto error
                }else //si la longitud del nombre es mayor de la maxima permitida
                    if(layout_name_machineInput.getCounterMaxLength() < textInput.length()){
                    layout_name_machineInput.setErrorEnabled(true);
                    layout_name_machineInput.setError("mucho texto");
                }else //si existe el nombre de la maquina
                    if(textInput.equals("")){

                }else{ //si all gucci
                    layout_name_machineInput.setErrorEnabled(false);
                    nameMachine=textInput;
                    isFavorite= checkbox_step2_bottom_sheet.isChecked();

                    step2StateActive();
                    optionsBottomSheet.dismiss();
                }
            }
        });

        optionsBottomSheet.show();
    }
}