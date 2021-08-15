package com.example.infinitycropapp.ui.main.home.models;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.infinitycropapp.Firebase.Database.Database;
import com.example.infinitycropapp.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//clase principal del modelo IC6 donde estara lo principal del control de esta.
public class IC6Activity extends AppCompatActivity {

    //palette
    ConstraintLayout btn_back;
    MaterialButton btn_action_machine;
    MaterialButton btn_clima;
    MaterialButton btn_extraction;
    TextView txt_action_machine;
    TextView txt_clima;
    TextView txt_extraction;
    //palette extraction bottom sheet
    Button btn_extraction_action;
    //palette machine action
    Button btn_machine_action;
    // FIN -> palette
    //btn on/of bools
    private boolean isMachineOn;
    private boolean isExtractionOn;
    private boolean isClimaOn;
    //firebase
    private DatabaseReference databaseReference;
    private Database database;
    //txt data of the machine
    //general
    private TextView general_temp;
    private TextView general_luz;
    private TextView general_hum;
    private TextView general_depos;
    //parte superior
    private TextView superior_temp;
    private TextView superior_luz;
    private TextView superior_hum;
    //parte inferior
    private TextView inferior_temp;
    private TextView inferior_luz;
    private TextView inferior_hum;
    //riego
    private TextView riego_txt;
    //strings, int ...
    private String machineId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ic6);

        //firebase
        databaseReference= FirebaseDatabase.getInstance().getReference();
        database = new Database();

        //findById
        btn_back=findViewById(R.id.btn_back_ic6_model);
        btn_action_machine=findViewById(R.id.ic6_model_btn_action_machine);
        btn_extraction=findViewById(R.id.ic6_model_btn_extraction);
        btn_clima=findViewById(R.id.ic6_model_btn_clima);
        txt_action_machine=findViewById(R.id.ic6_model_txt_action_machine);
        txt_extraction=findViewById(R.id.ic6_model_txt_extraction);
        txt_clima=findViewById(R.id.ic6_model_txt_clima);
        general_temp= findViewById(R.id.ic6_model_temp_general);
        general_luz= findViewById(R.id.ic6_model_luz_general);
        general_hum= findViewById(R.id.ic6_model_hum_general);
        general_depos= findViewById(R.id.ic6_model_depos_general);
        superior_temp= findViewById(R.id.ic6_model_temp_superior);
        superior_hum= findViewById(R.id.ic6_model_hum_superior);
        superior_luz= findViewById(R.id.ic6_model_luz_superior);
        inferior_temp= findViewById(R.id.ic6_model_temp_inferior);
        inferior_hum= findViewById(R.id.ic6_model_hum_inferior);
        inferior_luz= findViewById(R.id.ic6_model_luz_inferior);
        riego_txt=findViewById(R.id.ic6_model_riego);
        //get los pixeles del dispositivo actual
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        //get the id of the machine
        machineId= getIntent().getExtras().getString("machineId");

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
                BottomSheetDialog machineBottomSheet = new BottomSheetDialog(IC6Activity.this);
                //set the layout of the bottom sheet
                machineBottomSheet.setContentView(R.layout.machine_action_bottom_sheet);
                //findById
                CoordinatorLayout layout= machineBottomSheet.findViewById(R.id.ic6_model_layout_machine_bs);
                ConstraintLayout btn_back= machineBottomSheet.findViewById(R.id.ic6_model_close_machine_bs);
                btn_machine_action= machineBottomSheet.findViewById(R.id.ic6_model_btn_machine_action);
                //actions
                layout.setMinimumHeight(height);
                //hacer que cuando se muestre ocupe toda la pantalla
                machineBottomSheet.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        BottomSheetDialog d = (BottomSheetDialog) dialog;
                        FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
                        BottomSheetBehavior.from(bottomSheet)
                                .setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                });
                //actions
                //actions
                if(isMachineOn){
                    btn_machine_action.setText(getString(R.string.machine_control_txt_engine_off));
                }else{
                    btn_machine_action.setText(getString(R.string.machine_control_txt_engine_on));
                }
                //onclick
                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        machineBottomSheet.dismiss();
                    }
                });
                btn_machine_action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //cuando se pulse el boton cambiar el bool correspondiente
                        //si es true -> false , si es false -> true
                        isMachineOn = !isMachineOn; //cambiar el valor del bool
                        //si es true = 1 actualizar el child de la database como 1
                        //sino al reves
                        int stateMachine;
                        if(isMachineOn){
                            stateMachine= 1;
                        }else{
                            stateMachine= 0;
                        }
                        //update field in database
                        database.UpdateStateMachine("IC6 DUAL", machineId , "state machine", stateMachine , machineId);
                        //call to method
                        setButtonMode();
                    }
                });
                machineBottomSheet.show();

            }
        });

        //btn extraction mode
        btn_extraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set the bottom sheet
                BottomSheetDialog extractionBottomSheet = new BottomSheetDialog(IC6Activity.this);
                //set the layout of the bottom sheet
                extractionBottomSheet.setContentView(R.layout.machine_extraction_bottom_sheet);
                //findById
                CoordinatorLayout layout= extractionBottomSheet.findViewById(R.id.ic6_model_layout_extraction_bs);
                ConstraintLayout btn_back= extractionBottomSheet.findViewById(R.id.ic6_model_close_extraction_bs);
                btn_extraction_action = extractionBottomSheet.findViewById(R.id.ic6_model_btn_extraction_action);
                //actions
                layout.setMinimumHeight(height);
                //hacer que cuando se muestre ocupe toda la pantalla
                extractionBottomSheet.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        BottomSheetDialog d = (BottomSheetDialog) dialog;
                        FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
                        BottomSheetBehavior.from(bottomSheet)
                                .setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                });

                //actions
                if(isExtractionOn){
                    btn_extraction_action.setText(getString(R.string.machine_control_txt_extraccion_off));
                }else{
                    btn_extraction_action.setText(getString(R.string.machine_control_txt_extraccion_on));
                }
                //onclick
                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        extractionBottomSheet.dismiss();
                    }
                });
                btn_extraction_action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //cuando se pulse el boton cambiar el bool correspondiente
                        //si es true -> false , si es false -> true
                        isExtractionOn = !isExtractionOn; //cambiar el valor del bool
                        //si es true = 1 actualizar el child de la database como 1
                        //sino al reves
                        int stateMachine;
                        if(isExtractionOn){
                            stateMachine= 1;
                        }else{
                            stateMachine= 0;
                        }
                        //update field in database
                        database.UpdateStateMachine("IC6 DUAL", machineId , "state extraction", stateMachine , machineId);
                        //call to method
                        setButtonMode();
                    }
                });

                extractionBottomSheet.show();
            }
        });
        //get the state of the machine
        getStateOfTheMachine();
        //get data of the machine
        getDataOfTheMachine();

    }

    //metodo que dependiendo del on/off de los botones , les cambia el aspecto y el texto
    private void setButtonMode(){
        //btn machine on/off
        if(btn_machine_action != null){
            if(isMachineOn){
                //texto que apagar maquina (porque si es true esta encendida)
                txt_action_machine.setText(getString(R.string.machine_control_txt_engine_off));
                btn_machine_action.setText(getString(R.string.machine_control_txt_engine_off));
                //cambiar el color del boton del activity_ic6
                btn_action_machine.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.button_color));
                btn_action_machine.setIconTint(ContextCompat.getColorStateList(getApplicationContext(),R.color.white));
            }else{
                //texto por defecto encender maquina
                txt_action_machine.setText(getString(R.string.machine_control_txt_engine_on));
                btn_machine_action.setText(getString(R.string.machine_control_txt_engine_on));
                //cambiar el color del boton del activity_ic6
                btn_action_machine.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.white));
                btn_action_machine.setIconTint(ContextCompat.getColorStateList(getApplicationContext(),R.color.darker_gray));
            }
        }
        //btn extraction on/off
        if(btn_extraction_action != null){
            if(isExtractionOn){
                //texto apagar extraccion porque esta encendida
                txt_extraction.setText(getString(R.string.machine_control_txt_extraccion_off));
                btn_extraction_action.setText(getString(R.string.machine_control_txt_extraccion_off));
                //cambiar el color del boton del activity_ic6
                btn_extraction.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.button_color));
                btn_extraction.setIconTint(ContextCompat.getColorStateList(getApplicationContext(),R.color.white));
            }else{
                //texto por defecto encender maquina
                txt_extraction.setText(getString(R.string.machine_control_txt_extraccion_on));
                btn_extraction_action.setText(getString(R.string.machine_control_txt_extraccion_on));
                //cambiar el color del boton del activity_ic6
                btn_extraction.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.white));
                btn_extraction.setIconTint(ContextCompat.getColorStateList(getApplicationContext(),R.color.darker_gray));
            }
        }
        //btn clima on/off
        if(btn_clima != null){
            if(isClimaOn){
                //texto quitar clima porque tiene uno encendido
                txt_clima.setText(R.string.machine_control_txt_clima_of);
                //cambiar el color del boton del activity_ic6
                btn_clima.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.button_color));
                btn_clima.setIconTint(ContextCompat.getColorStateList(getApplicationContext(),R.color.white));

            }else{
                //texto por defecto activar clima
                txt_clima.setText(R.string.machine_control_txt_clima_on);
                btn_clima.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.white));
                btn_clima.setIconTint(ContextCompat.getColorStateList(getApplicationContext(),R.color.darker_gray));
            }
        }
    }

    //get los estados de la maquina , si esta encendida o apagada
    private void getStateOfTheMachine(){
        //lectura real-time database
        databaseReference.child("IC6 DUAL")
                .child(machineId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int stateMachine= snapshot.child("state machine").getValue(int.class);
                    int stateExtraction= snapshot.child("state extraction").getValue(int.class);
                    if(stateMachine == 0){
                        //cambiar el color del boton del activity_ic6
                        txt_action_machine.setText(getString(R.string.machine_control_txt_engine_on));
                        btn_action_machine.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.white));
                        btn_action_machine.setIconTint(ContextCompat.getColorStateList(getApplicationContext(),R.color.darker_gray));
                        isMachineOn = false;
                    }else{
                        //cambiar el color del boton del activity_ic6
                        txt_action_machine.setText(getString(R.string.machine_control_txt_engine_off));
                        btn_action_machine.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.button_color));
                        btn_action_machine.setIconTint(ContextCompat.getColorStateList(getApplicationContext(),R.color.white));
                        isMachineOn = true;
                    }
                    if(stateExtraction == 0){
                        //cambiar el color del boton del activity_ic6
                        txt_extraction.setText(getString(R.string.machine_control_txt_engine_on));
                        btn_extraction.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.white));
                        btn_extraction.setIconTint(ContextCompat.getColorStateList(getApplicationContext(),R.color.darker_gray));
                        isExtractionOn = false;
                    }else{
                        //cambiar el color del boton del activity_ic6
                        txt_extraction.setText(getString(R.string.machine_control_txt_engine_off));
                        btn_extraction.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.button_color));
                        btn_extraction.setIconTint(ContextCompat.getColorStateList(getApplicationContext(),R.color.white));
                        isExtractionOn = true;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getDataOfTheMachine(){
        //lectura real-time database
        databaseReference.child("IC6 DUAL")
                .child(machineId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //general
                    String temperaturaGeneral= snapshot.child("datos generales").child("temperatura").getValue(int.class).toString();
                    String humedadGeneral= snapshot.child("datos generales").child("humedad").getValue(int.class).toString();
                    String luminosidadGeneral= snapshot.child("datos generales").child("luminosidad").getValue(int.class).toString();
                    String depositoGeneral= snapshot.child("datos generales").child("deposito").getValue(int.class).toString();

                    //parte superior
                    String temperaturaSuperior= snapshot.child("parte superior").child("temperatura").getValue(int.class).toString();
                    String humedadSuperior= snapshot.child("parte superior").child("humedad").getValue(int.class).toString();
                    String luminosidadSuperior= snapshot.child("parte superior").child("luminosidad").getValue(int.class).toString();
                    //parte inferior
                    String temperaturaInferior= snapshot.child("parte inferior").child("temperatura").getValue(int.class).toString();
                    String humedadInferior= snapshot.child("parte inferior").child("humedad").getValue(int.class).toString();
                    String luminosidadInferior= snapshot.child("parte inferior").child("luminosidad").getValue(int.class).toString();
                    //riego
                    int riego= snapshot.child("state riego").getValue(int.class);
                    //set data
                    temperaturaGeneral=temperaturaGeneral+"°";
                    general_temp.setText(temperaturaGeneral);
                    humedadGeneral=humedadGeneral+"%";
                    general_hum.setText(humedadGeneral);
                    luminosidadGeneral=luminosidadGeneral+"%";
                    general_luz.setText(luminosidadGeneral);
                    depositoGeneral=depositoGeneral+"%";
                    general_depos.setText(depositoGeneral);
                    temperaturaSuperior=temperaturaSuperior+"°";
                    superior_temp.setText(temperaturaSuperior);
                    luminosidadSuperior=luminosidadSuperior+"%";
                    superior_luz.setText(luminosidadSuperior);
                    humedadSuperior=humedadSuperior+"%";
                    superior_hum.setText(humedadSuperior);
                    temperaturaInferior=temperaturaInferior+"°";
                    inferior_temp.setText(temperaturaInferior);
                    luminosidadInferior=luminosidadInferior+"%";
                    inferior_luz.setText(luminosidadInferior);
                    humedadInferior=humedadInferior+"%";
                    inferior_hum.setText(humedadInferior);
                    //riego
                    if(riego == 1){
                        riego_txt.setText(getString(R.string.machine_control_txt_riego_activo));
                    }else{
                        riego_txt.setText(getString(R.string.machine_control_txt_riego_inactivo));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}