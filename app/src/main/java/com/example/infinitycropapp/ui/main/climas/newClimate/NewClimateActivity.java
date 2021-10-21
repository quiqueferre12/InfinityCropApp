package com.example.infinitycropapp.ui.main.climas.newClimate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.climas.adapters.AdapterItemDiaRiego;
import com.example.infinitycropapp.ui.pojos.ItemDiaRiego;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;

public class NewClimateActivity extends AppCompatActivity {

    //palette
    private ConstraintLayout btn_back;
    private ConstraintLayout btn_check;
    private MaterialCardView step1_card;
    private MaterialCardView step2_card;
    private MaterialCardView step3_card;
    private MaterialCardView step4_card;
    private MaterialCardView step5_card;
    private FloatingActionButton step1_state;
    private FloatingActionButton step2_state;
    private FloatingActionButton step3_state;
    private FloatingActionButton step4_state;
    private FloatingActionButton step5_state;

    //bools
    private boolean isStep1Done = false;
    private boolean isStep2Done = false;
    private boolean isStep3Done = false;
    private boolean isStep4Done = false;
    private boolean isStep5Done = false;

    //climate data
    //step 1 data
    private Uri photo;
    //step 2 data
    private String name;
    private String description;
    private boolean isFavorite;
    //step 3 data
    //umbrales var
    private int temp_min=0; private int temp_max=40;
    private int lumin_min=0; private int lumin_max=100;
    private int value=0;

    private String final_temp_min=""; private String final_temp_max="";
    private String final_lumin_min=""; private String final_lumin_max="";
    //step 4 data
    private List<ItemDiaRiego> itemDiaRiegos= new ArrayList<>();
    //step 5 data

    //step 2 Attributes
    private TextInputEditText name_climateInput;
    private TextInputEditText desc_climateInput;
    private ConstraintLayout quit_step2_bottom_sheet;
    private ConstraintLayout confirm_step2_bottom_sheet;
    private CheckBox checkbox_step2_bottom_sheet;
    private TextInputLayout layout_name_climateInput;
    private TextInputLayout layout_desc_climateInput;

    //step 3 Attributes
    private ConstraintLayout quit_step3_bottom_sheet;
    private ConstraintLayout confirm_step3_bottom_sheet;
    private ConstraintLayout max_temp_layout;
    private ConstraintLayout min_temp_layout;
    private ConstraintLayout max_lumin_layout;
    private ConstraintLayout min_lumin_layout;
    private TextInputEditText editext_min_temp;
    private TextInputEditText editext_max_temp;
    private TextInputEditText editext_min_lumin;
    private TextInputEditText editext_max_lumin;

    //dimensiones dispositivo actual
    private int height;
    private int width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_climate);

        //get los pixeles del dispositivo actual
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        //findById
        btn_back=findViewById(R.id.back_newClima);
        btn_check=findViewById(R.id.confirm_create_clima);
        step1_card=findViewById(R.id.step1_card);
        step2_card=findViewById(R.id.step2_card);
        step3_card=findViewById(R.id.step3_card);
        step4_card=findViewById(R.id.step4_card);
        step5_card=findViewById(R.id.step5_card);
        step1_state=findViewById(R.id.step1_state);
        step2_state=findViewById(R.id.step2_state);
        step3_state=findViewById(R.id.step3_state);
        step4_state=findViewById(R.id.step4_state);
        step5_state=findViewById(R.id.step5_state);

        //call methods
        ClearAllState(); //all to default

        //onclick
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //step1card onclick -> add photo (photo var string)
        step1_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrimos el plugin para set una foto
                CropImage.activity().setAspectRatio(1,1).start(NewClimateActivity.this);
            }
        });

        //step2card onclick -> add info (name climate, description)
        step2_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initStep2BottomSheet();
            }
        });
        //step3card onclick ->
        step3_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initStep3BottomSheet();
            }
        });
        //step4card onclick ->
        step4_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSteo4BottomSheet();
            }
        });
        //step5card onclick ->
        step5_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //check new climate -> confirm if all the bools are true
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStep1Done && isStep2Done && isStep3Done && isStep4Done && isStep5Done) { //si todos los pasos estas terminados

                }else{ //faltan pasos por hacer
                    setSnackbar(findViewById(R.id.general_layout_activity_new_climate), getString(R.string.snack_steps_remain));
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            //recogemos el resultado
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            //guardamos el link en una variable
            photo=result.getUri();
            //set in the Circular ImageView
            //profileImageView.setImageURI(photo);

            //activate state icon card step 1
            step1StateActive();
        }

    }

    //set iconos en activo o inactivo
    private void step1StateActive(){
        step1_state.setImageResource(R.drawable.icons_active_state); //change icon to active
        step1_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color))); //change color to active
        isStep1Done=true;
    }
    private void step1StateInactive(){
        step1_state.setImageResource(R.drawable.icons_inactive_state); //change icon to Inactive
        step1_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
        isStep1Done=false;
        //cuando hay 1 paso incompleto el paso 4 automaticamente pasa a incompleto
        step5StateInactive();
    }
    private void step2StateActive(){
        step2_state.setImageResource(R.drawable.icons_active_state); //change icon to active
        step2_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color))); //change color to active
        isStep2Done=true;
    }
    private void step2StateInactive(){
        step2_state.setImageResource(R.drawable.icons_inactive_state); //change icon to Inactive
        step2_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
        isStep2Done=false;
        //cuando hay 1 paso incompleto el paso 4 automaticamente pasa a incompleto
        step5StateInactive();
    }
    private void step3StateActive(){
        step3_state.setImageResource(R.drawable.icons_active_state); //change icon to active
        step3_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color))); //change color to active
        isStep3Done=true;
    }
    private void step3StateInactive(){
        step3_state.setImageResource(R.drawable.icons_inactive_state); //change icon to Inactive
        step3_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
        isStep3Done=false;
        //cuando hay 1 paso incompleto el paso 4 automaticamente pasa a incompleto
        step5StateInactive();
    }
    private void step4StateActive(){
        step4_state.setImageResource(R.drawable.icons_active_state); //change icon to active
        step4_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color))); //change color to active
        isStep4Done=true;
    }
    private void step4StateInactive(){
        step4_state.setImageResource(R.drawable.icons_inactive_state); //change icon to Inactive
        step4_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
        isStep4Done=false;
        //cuando hay 1 paso incompleto el paso 4 automaticamente pasa a incompleto
        step5StateInactive();
    }
    private void step5StateActive(){
        step5_state.setImageResource(R.drawable.icons_active_state); //change icon to active
        step5_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.button_color))); //change color to active
        isStep5Done=true;
    }
    private void step5StateInactive(){
        step5_state.setImageResource(R.drawable.icons_inactive_state); //change icon to Inactive
        step5_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black))); //change color to Inactive
        isStep5Done=false;
    }
    // FIN -> set iconos en activo o inactivo

    //clear todos las variables del nuevo clima y set imagenes en default
    private void ClearAllState(){
        //clear all data
        //data step 1
        photo = null;
        //data step 2
        name ="";
        description ="";
        //data step 3
        temp_min=0; temp_max=40;
        lumin_min=0; lumin_max=100;
        value=0;

        final_temp_min=""; final_temp_max="";
        final_lumin_min=""; final_lumin_max="";
        //data step 4
        //data step 5
        //clear todos los iconos a incompleto
        step1_state.setImageResource(R.drawable.icons_inactive_state);
        step1_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
        step2_state.setImageResource(R.drawable.icons_inactive_state);
        step2_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
        step3_state.setImageResource(R.drawable.icons_inactive_state);
        step3_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
        step4_state.setImageResource(R.drawable.icons_inactive_state);
        step4_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
        step5_state.setImageResource(R.drawable.icons_inactive_state);
        step5_state.setImageTintList(ColorStateList.valueOf(getColor(R.color.black)));
    }
    // FIN -> clear todos las variables del nuevo clima y set imagenes en default

    // steps bottom sheets
    private void initStep2BottomSheet(){
        //set the bottom sheet
        BottomSheetDialog step2BottomSheet = new BottomSheetDialog(NewClimateActivity.this);
        //set the layout of the bottom sheet
        step2BottomSheet.setContentView(R.layout.activity_new_climate_step2_bottom_sheet);
        //findbyid del bottom sheet
        name_climateInput=step2BottomSheet.findViewById(R.id.step2_editText_new_climate);
        desc_climateInput=step2BottomSheet.findViewById(R.id.step2_editText_desc_new_climate);
        quit_step2_bottom_sheet=step2BottomSheet.findViewById(R.id.quit_step2_bottom_sheet_new_climate);
        confirm_step2_bottom_sheet=step2BottomSheet.findViewById(R.id.confirm_step2_bottom_sheet_new_climate);
        checkbox_step2_bottom_sheet=step2BottomSheet.findViewById(R.id.step2_checkbox_new_climate);
        layout_name_climateInput=step2BottomSheet.findViewById(R.id.step2_textField_new_climate);
        layout_desc_climateInput=step2BottomSheet.findViewById(R.id.step2_textField_desc_new_climate);
        //onclick methods
        //si ya ha insertado el nombre
        if(!name.equals("")){
            name_climateInput.setText(name); //mostrarlo
        }
        //si ya ha insertado la desc
        if(!description.equals("")){
            desc_climateInput.setText(description); //mostrarlo
        }
        //si ya lo ha anyadido a favoritos
        if(isFavorite){
            checkbox_step2_bottom_sheet.setChecked(true);
        }
        //quit bottomsheet
        quit_step2_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step2BottomSheet.dismiss();
            }
        });
        //confirm data of the bottom sheet
        confirm_step2_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textInput=name_climateInput.getText().toString().trim(); //get the texto of the input
                String descInput=desc_climateInput.getText().toString().trim(); //get the texto of the input
                if(textInput.equals("")){ //si no hay nombre en el editext
                    layout_name_climateInput.setErrorEnabled(true); //activar error
                    layout_name_climateInput.setError(getString(R.string.error_empty_editext)); //set texto error
                }else //si la longitud del nombre es mayor de la maxima permitida
                    if(layout_name_climateInput.getCounterMaxLength() < textInput.length()){
                        layout_name_climateInput.setErrorEnabled(true);
                        layout_name_climateInput.setError(getString(R.string.error_a_lot_text_editext));
                    }else{ //si all gucci
                        layout_name_climateInput.setErrorEnabled(false);
                        name=textInput;
                        if(!descInput.isEmpty()){ //si desc no esta vacio
                            description=descInput;
                        }
                        isFavorite= checkbox_step2_bottom_sheet.isChecked();

                        step2StateActive();
                        step2BottomSheet.dismiss();
                    }
            }
        });

        step2BottomSheet.show();
    }

    private void initStep3BottomSheet(){
        //set the bottom sheet
        BottomSheetDialog step3BottomSheet = new BottomSheetDialog(NewClimateActivity.this);
        //set the layout of the bottom sheet
        step3BottomSheet.setContentView(R.layout.activity_new_climate_step3_bottom_sheet);
        //findbyid del bottom sheet
        quit_step3_bottom_sheet= step3BottomSheet.findViewById(R.id.quit_step3_bottom_sheet_new_climate);
        confirm_step3_bottom_sheet= step3BottomSheet.findViewById(R.id.confirm_step3_bottom_sheet_new_climate);
        min_temp_layout = step3BottomSheet.findViewById(R.id.btn_min_temp_new_climate);
        max_temp_layout = step3BottomSheet.findViewById(R.id.btn_max_temp_new_climate);
        max_lumin_layout = step3BottomSheet.findViewById(R.id.btn_max_lumin_new_climate);
        min_lumin_layout = step3BottomSheet.findViewById(R.id.btn_min_lumin_new_climate);
        editext_min_temp= step3BottomSheet.findViewById(R.id.editext_min_temp_new_climate);
        editext_max_temp= step3BottomSheet.findViewById(R.id.editext_max_temp_new_climate);
        editext_min_lumin= step3BottomSheet.findViewById(R.id.editext_min_lumin_new_climate);
        editext_max_lumin= step3BottomSheet.findViewById(R.id.editext_max_lumin_new_climate);

        //insertar datos si existen
        //lumin
        if(!final_lumin_max.equals("")){
            editext_max_lumin.setText(final_lumin_max);
        }
        if(!final_lumin_min.equals("")){
            editext_min_lumin.setText(final_lumin_min);
        }
        //temp
        if(!final_temp_max.equals("")){
            editext_max_temp.setText(final_temp_max);
        }
        if(!final_temp_min.equals("")){
            editext_min_temp.setText(final_temp_min);
        }

        //onclick
        //btn back
        quit_step3_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                step3BottomSheet.dismiss();
            }
        });

        //btn check step 3
        confirm_step3_bottom_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final_lumin_min=editext_min_lumin.getText().toString();
                final_lumin_max=editext_max_lumin.getText().toString();
                final_temp_min=editext_min_temp.getText().toString();
                final_temp_max=editext_max_temp.getText().toString();
                if(!final_lumin_max.equals("") && !final_lumin_min.equals("")
                 && !final_temp_min.equals("") && !final_temp_max.equals("")){
                    step3StateActive();
                }else{
                    step3StateInactive();
                }

                step3BottomSheet.dismiss();
            }
        });

        //DIALOG donde se elige el umbral min y max
        Dialog dialog= new Dialog(NewClimateActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true); //al pulsar fuera del dialog se quita
        dialog.setContentView(R.layout.umbral_climate_dialog);
        //set the correct width
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //findById
        ConstraintLayout btn_back=dialog.findViewById(R.id.btn_back_umbral_dialog);
        ConstraintLayout btn_check=dialog.findViewById(R.id.btn_check_umbral_dialog);
        NumberPicker numberPicker=dialog.findViewById(R.id.number_picker_umbral_dialog);
        TextView title_dialog=dialog.findViewById(R.id.title_umbral_dialog);
        //methods
        //btn back DIALOG
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                step3BottomSheet.show();
                dialog.dismiss();
            }
        });

        //onclick

        //cada vez que se pulsa un umbral se le pasa el nombre de ese umbral y su valor max y min dependiendo
        // de si ya ha establecido esos valores max y min
        min_temp_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_dialog.setText(getText(R.string.step4_tittle_new_climate_4_temp_min));
                numberPicker.setValue(temp_min);
                numberPicker.setMinValue(10);
                numberPicker.setMaxValue(temp_max);
                dialog.show();
            }
        });
        max_temp_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_dialog.setText(getText(R.string.step4_tittle_new_climate_3_temp_max));
                numberPicker.setValue(temp_max);
                numberPicker.setMinValue(temp_min);
                numberPicker.setMaxValue(40);
                dialog.show();
            }
        });
        max_lumin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set el titulo del dialog
                title_dialog.setText(getText(R.string.step4_tittle_new_climate_1_lumin_max));
                //le establecemos el valor actual que tiene elegido el user para la luz maxima
                numberPicker.setValue(lumin_max);
                //valor minimo que tiene puesto
                numberPicker.setMinValue(lumin_min);
                //como va a elegir el vamos maximo le ponemos el mas grande que hay
                numberPicker.setMaxValue(100);
                dialog.show();
            }
        });
        min_lumin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set el titulo del dialog
                title_dialog.setText(getText(R.string.step4_tittle_new_climate_2_lumin_min));
                //le establecemos el valor actual que tiene elegido el user para la luz minima
                numberPicker.setValue(lumin_min);
                //como elige un vamos minimo le establecemos el valor mas pequenyo que pueda elegir
                numberPicker.setMinValue(0);
                //valor maximo que ha elegido
                numberPicker.setMaxValue(lumin_max);
                dialog.show();
            }
        });

        //btn check DIALOG
        // se comprueba en cada dialog del number picker, que umbral es y se configura los max y min de los valores recogidos
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                step3BottomSheet.show();
                value = numberPicker.getValue();

                if(title_dialog.getText().equals(getText(R.string.step4_tittle_new_climate_2_lumin_min))){
                    lumin_min=value;
                    String res= String.valueOf(lumin_min);
                    editext_min_lumin.setText(res);

                }else if(title_dialog.getText().equals(getText(R.string.step4_tittle_new_climate_1_lumin_max))){
                    lumin_max=value;
                    String res= String.valueOf(lumin_max);
                    editext_max_lumin.setText(res);
                }else if(title_dialog.getText().equals(getText(R.string.step4_tittle_new_climate_4_temp_min))){
                    temp_min=value;
                    String res= String.valueOf(temp_min);
                    editext_min_temp.setText(res);
                }else if(title_dialog.getText().equals(getText(R.string.step4_tittle_new_climate_3_temp_max))){
                    temp_max=value;
                    String res= String.valueOf(temp_max);
                    editext_max_temp.setText(res);
                }

                dialog.dismiss();

            }
        });
        // FIN -> dialog donde se elige el umbral min y max

        step3BottomSheet.show();
    }

    //step 4
    private void initSteo4BottomSheet(){
        BottomSheetDialog step4BottomSheet = new BottomSheetDialog(NewClimateActivity.this);
        //set the layout of the bottom sheet
        step4BottomSheet.setContentView(R.layout.activity_new_climate_step4_bottom_sheet);
        //findbyid del bottom sheet
        ConstraintLayout btn_back = step4BottomSheet.findViewById(R.id.quit_step4_bottom_sheet_new_climate);
        ConstraintLayout btn_check = step4BottomSheet.findViewById(R.id.confirm_step4_bottom_sheet_new_climate);
        RecyclerView rv_dias = step4BottomSheet.findViewById(R.id.rv_riego_dias);
        ConstraintLayout layout=step4BottomSheet.findViewById(R.id.step_4_riego_semana_layout);
        //hacer que cuando se muestre ocupe toda la pantalla
        layout.setMinimumHeight(height);
        step4BottomSheet.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        //rv methods
        Context context=NewClimateActivity.this; //contexto
        //defino que el rv no tenga fixed size

        //manejador para declarar la direccion de los items del rv
        rv_dias.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        AdapterItemDiaRiego adapterItemDiaRiego=new AdapterItemDiaRiego(itemDiaRiegos,context);
        //get data for rv
        itemDiaRiegos.clear();
        itemDiaRiegos.add(new ItemDiaRiego("Lunes", getDrawable(R.drawable.icons_lunes) , "5 riegos"));
        itemDiaRiegos.add(new ItemDiaRiego("Martes", getDrawable(R.drawable.icons_martes) , "1 riegos"));
        itemDiaRiegos.add(new ItemDiaRiego("Miercoles", getDrawable(R.drawable.icons_miercoles) , "3 riegos"));
        itemDiaRiegos.add(new ItemDiaRiego("Jueves", getDrawable(R.drawable.icons_jueves) , "2 riegos"));
        itemDiaRiegos.add(new ItemDiaRiego("Viernes", getDrawable(R.drawable.icons_viernes) , "0 riegos"));
        itemDiaRiegos.add(new ItemDiaRiego("Sabado", getDrawable(R.drawable.icons_sabado) , "0 riegos"));
        itemDiaRiegos.add(new ItemDiaRiego("Domingo", getDrawable(R.drawable.icons_domingo) , "1 riegos"));
        //declaro que cual es el adaptador el rv
        rv_dias.setAdapter(adapterItemDiaRiego);



        //FIN ->methods


        //onclick
        // dismiss bottomhshett
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                step4BottomSheet.dismiss();
            }
        });
        //btn check
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                step4StateActive();
                step4BottomSheet.dismiss();
            }
        });


        /*
        //dialog al pulsar un dia
        Dialog dialog= new Dialog(NewClimateActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true); //al pulsar fuera del dialog se quita
        dialog.setContentView(R.layout.add_list_dialog);
        //set the correct width
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //findById


        //show bottomSheet
        dialog.show();
         */


        //show bottomSheet
        step4BottomSheet.show();
    }
    // FIN -> steps bottom sheets

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