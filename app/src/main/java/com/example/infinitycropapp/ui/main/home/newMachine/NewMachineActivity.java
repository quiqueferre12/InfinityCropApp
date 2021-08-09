package com.example.infinitycropapp.ui.main.home.newMachine;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infinitycropapp.Firebase.Firestore.Firestore;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;
import com.example.infinitycropapp.ui.pojos.ItemMachine;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class NewMachineActivity extends AppCompatActivity {
    //-----poner aca attributes etc... -----//
    //firestore
    private Firestore db;
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
    private  boolean isStep1Done = false;
    private  boolean isStep2Done = false;
    private  boolean isStep3Done = false;
    //back button
    private ConstraintLayout backButton;
    //confirm create machine button
    private ConstraintLayout confirmButton;
    //step 2 bottom sheet
    private TextInputEditText name_machineInput;
    private ConstraintLayout quit_step2_bottom_sheet;
    private ConstraintLayout confirm_step2_bottom_sheet;
    private CheckBox checkbox_step2_bottom_sheet;
    private TextInputLayout layout_name_machineInput;
    //step 3 bottom sheet
    private ConstraintLayout quit_step3_bottom_sheet;
    private ConstraintLayout confirm_step3_bottom_sheet;
    private TextView step3_model_text;
    private TextView step3_name_text;
    private ImageView step3_1_state;
    private ImageView step3_2_state;
    private TextView step3_favorite_state;
    //string , int ....
    private String machineModel;
    private String machineCode;
    private String nameMachine;
    private boolean isFavorite;

    //--FIN-> poner aca attributes etc... -----//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_machine);

        //firestore
        //llamamos al back end service
        db=new Firestore();

        //findById elements
        step1Card=findViewById(R.id.step1_card);
        step2Card=findViewById(R.id.step2_card);
        step3Card=findViewById(R.id.step3_card);
        step1State=findViewById(R.id.step1_state);
        step2State=findViewById(R.id.step2_state);
        step3State=findViewById(R.id.step3_state);
        backButton=findViewById(R.id.back_newMachine);
        confirmButton=findViewById(R.id.confirm_create_machine);

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
                initStep3BottomSheet();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStep1Done && isStep2Done && isStep3Done){ //si todos los pasos estas terminados
                    //guardamos los datos en el objeto machine
                    ItemMachine newMachine= new ItemMachine(nameMachine, machineModel, isFavorite);
                    //llamamos al metodo corresponiente
                    db.AddMachine("Machine",newMachine,machineCode, true);

                    //cambio de actividad
                    Intent intent = new Intent(getApplicationContext(), MainListActivity.class);
                    startActivity(intent);
                }else{ //faltan pasos por hacer
                    setSnackbar(findViewById(R.id.general_layout_activity_new_machine), getString(R.string.snack_steps_remain));
                }
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
                machineModel ="";
                machineCode="";
                step1StateInactive();
            } else {
                machineModel =result.getContents().toString(); //get the machine code

                String[] strings= machineModel.split("-");
                if(strings.length > 1){ //si es un qr de infinity crop
                    machineModel = strings[1];
                    machineCode = strings[0];
                    //comprobar si la maquina ya esta registrada o no
                    ExistDocByIdDocument("Machine",machineCode); //call back end method
                }else{ //si no es nuestro qr
                    setSnackbar(findViewById(R.id.general_layout_activity_new_machine), getString(R.string.snack_qr_error));
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void step1StateActive(){
        step1State.setImageResource(R.drawable.icons_active_state); //change icon to active
        step1State.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color))); //change color to active
        isStep1Done=true;
    }
    private void step1StateInactive(){
        step1State.setImageResource(R.drawable.icons_inactive_state); //change icon to Inactive
        step1State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
        isStep1Done=false;
        //cuando hay 1 paso incompleto el paso 3 automaticamente pasa a incompleto
        step3StateInactive();
    }
    private void step2StateActive(){
        step2State.setImageResource(R.drawable.icons_active_state); //change icon to active
        step2State.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color))); //change color to active
        isStep2Done=true;
    }
    private void step2StateInactive(){
        step2State.setImageResource(R.drawable.icons_inactive_state); //change icon to Inactive
        step2State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
        isStep2Done=false;
        //cuando hay 1 paso incompleto el paso 3 automaticamente pasa a incompleto
        step3StateInactive();
    }
    private void step3StateActive(){
        step3State.setImageResource(R.drawable.icons_active_state); //change icon to active
        step3State.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color))); //change color to active
        isStep3Done=true;
    }
    private void step3StateInactive(){
        step3State.setImageResource(R.drawable.icons_inactive_state); //change icon to Inactive
        step3State.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
        isStep3Done=false;
    }

    private void ClearAllState(){
        machineModel =""; //clear el codigo escaneado
        machineCode =""; //clear el codigo escaneado
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
                    layout_name_machineInput.setError(getString(R.string.error_empty_editext)); //set texto error
                }else //si la longitud del nombre es mayor de la maxima permitida
                    if(layout_name_machineInput.getCounterMaxLength() < textInput.length()){
                    layout_name_machineInput.setErrorEnabled(true);
                    layout_name_machineInput.setError(getString(R.string.error_a_lot_text_editext));
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
    private void initStep3BottomSheet(){
        //set the bottom sheet
        BottomSheetDialog verificationBottomSheet = new BottomSheetDialog(NewMachineActivity.this);
        //set the layout of the bottom sheet
        verificationBottomSheet.setContentView(R.layout.activity_new_machine_step3_bottom_sheet);
        //findbyid del bottom sheet
        quit_step3_bottom_sheet=verificationBottomSheet.findViewById(R.id.quit_step3_bottom_sheet);
        confirm_step3_bottom_sheet=verificationBottomSheet.findViewById(R.id.confirm_step3_bottom_sheet);
        step3_model_text=verificationBottomSheet.findViewById(R.id.qr_code_step3);
        step3_name_text=verificationBottomSheet.findViewById(R.id.name_machine_step3);
        step3_1_state=verificationBottomSheet.findViewById(R.id.state_1_step3);
        step3_2_state=verificationBottomSheet.findViewById(R.id.state_2_step3);
        step3_favorite_state=verificationBottomSheet.findViewById(R.id.step3_favorite_state);
        //onclick methods
        //quit bottomsheet
        quit_step3_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationBottomSheet.dismiss();
            }
        });
        //confirm check button onclick
        confirm_step3_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStep1Done && isStep2Done){ //si estan hechos los pasos 1 y 2
                    step3StateActive(); //call function
                }else{ //sino
                    step3StateInactive();
                }
                verificationBottomSheet.dismiss(); //cerramos bottom sheet
            }
        });
        //si el paso 1 esta completado
        if(isStep1Done){
            String modelResult=getString(R.string.step3_modelText_newMachine) + " "+ machineModel;
            //inserto su string en el textview
            step3_model_text.setText(modelResult);
            //hacer el texto bold
            step3_model_text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            //cambio de color
            step3_model_text.setTextColor(getColor(R.color.black));
            //set activate img state
            step3_1_state.setImageResource(R.drawable.icons_active_state);
            //change color
            step3_1_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color)));
        }else{
            //inserto string default en el textview
            step3_model_text.setText(getString(R.string.step3_empty));
            //hacer el texto normal
            step3_model_text.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            //set inactive img state
            step3_1_state.setImageResource(R.drawable.icons_inactive_state);
            //change color
            step3_1_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
        }
        //si el paso 2 esta completado
        if(isStep2Done){
            //inserto su string en el textview
            step3_name_text.setText(nameMachine);
            //hacer el texto bold
            step3_name_text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            //cambio de color
            step3_name_text.setTextColor(getColor(R.color.black));
            //set activate img state
            step3_2_state.setImageResource(R.drawable.icons_active_state);
            //change color
            step3_2_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color)));
        }else{
            //inserto string default en el textview
            step3_name_text.setText(getString(R.string.step3_empty));
            //hacer el texto normal
            step3_name_text.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            //set inactive img state
            step3_2_state.setImageResource(R.drawable.icons_inactive_state);
            //change color
            step3_2_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
        }
        //si la maquina se ha anyadido a la lista de favoritos
        if(isFavorite){
            //inserto su string en el textview
            step3_favorite_state.setText(getString(R.string.step3_favorite_yes));
        }else{
            //inserto su string default en el textview
            step3_favorite_state.setText(getString(R.string.step3_favorite_no));
        }
        verificationBottomSheet.show();
    }

    //comprobar si existe documento/campo dentro de una collecion conociendo el id del usario
    public void ExistDocByIdDocument(String collection, String scannedCode){
        //exist or not var
        FirebaseFirestore db2= FirebaseFirestore.getInstance();
        db2.collection(collection) //busco en la collection
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){ //si all gucci
                        QuerySnapshot document = task.getResult(); //get result
                        int cont=0;
                        for (QueryDocumentSnapshot doc : document) {
                           if(doc.getId().equals(scannedCode)){ //si ya existe esa maquina
                               cont++; // ++
                           }
                        }

                        if (cont >= 1) { //ese campo ya existe
                            step1StateInactive();
                            setSnackbar(findViewById(R.id.general_layout_activity_new_machine), (String) getText(R.string.snack_machine_exists));
                        } else { //no existe
                            step1StateActive();
                        }
                    }
                });
    }
    //set snackbar method
    public void setSnackbar(View actualActivity, String snackBarText){
        Snackbar snackBar = Snackbar.make(actualActivity, snackBarText,Snackbar.LENGTH_LONG);
        snackBar.setActionTextColor(Color.CYAN);
        snackBar.setAction(getText(R.string.snack_close), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }
}