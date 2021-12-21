package com.example.infinitycropapp.ui.main.climas;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.infinitycropapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashSet;

import static android.content.ContentValues.TAG;

public class ActivityClima extends AppCompatActivity {


    //-----poner aca attributes etc... -----//

    //Layouts
    private ConstraintLayout btn_back;
    //Tx
    private TextView textViewClimateName;
    private TextView textViewDesc;
    private TextView textViewNumberHum;
    private TextView textViewNumberTemp;
    private TextView textViewNumberLight;
    private TextView textViewLikes;
    private TextView textViewNumberTimes;
    private TextView textViewNumberDays;
    private TextView textViewCreator;
    private ImageView image_climate;
    //Firebase
    private FirebaseFirestore db;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //firestore
        db= FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_climate);
        textViewClimateName=findViewById(R.id.textViewClimateName);
        textViewDesc=findViewById(R.id.textViewDesc);
        textViewNumberHum=findViewById(R.id.textViewNumberHum);
        textViewNumberTemp=findViewById(R.id.textViewNumberTemp);
        textViewNumberLight=findViewById(R.id.textViewNumberLight);
        textViewNumberTimes=findViewById(R.id.textViewNumberTimes);
        textViewLikes=findViewById(R.id.textViewLikes);
        textViewNumberDays=findViewById(R.id.textViewNumberDays);
        textViewCreator=findViewById(R.id.textViewCreator);
        image_climate = findViewById(R.id.image_climate);
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
        HashSet<String> numDias=new HashSet<>();
        final int[] numRiegos = {0};
        final String[]  TempMin = new String[1];
        final String[]  TempMax = new String[1];
        final String[]  HumMin = new String[1];
        final String[]  HumMax = new String[1];
        final String[]  LuzMin = new String[1];
        final String[] LuzMax = new String[1];
        final String[]  idCreator = new String[1];
        final int[] NumGuardados= new int[1];
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        DocumentReference docRef = db.collection("Climate").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //get data del clima
                        Picasso.get().load(document.get("image").toString()).into(image_climate);
                        textViewClimateName.setText((String) document.get("name"));
                        textViewDesc.setText(document.get("description").toString());
                        /*LuzMax[0] = (String) document.get("LuminosityMax").toString();
                        LuzMin[0] = (String) document.get("Luminosity").toString();
                        HumMax[0] = (String) document.get("HumidityMax").toString();
                        HumMin[0] = (String) document.get("Humidity").toString();*/
                        TempMax[0] = (String) document.get("maxtemperature").toString();
                        TempMin[0] = (String) document.get("minTemperature").toString();
                        /*textViewNumberHum.setText(HumMin[0] + " - " + HumMax[0]);
                        textViewNumberLight.setText(LuzMin[0] + " - " + LuzMax[0]);*/
                        textViewNumberTemp.setText(TempMin[0] + " - " + TempMax[0]);
                        /*NumGuardados[0] = (int) Integer.parseInt(document.get("Guardados").toString()) ;*/
                        /*String formattedNumGuardados = String.format("%04d", NumGuardados[0]);//Formato a 4 cifras*/
                        /*textViewLikes.setText(formattedNumGuardados);*/
                        idCreator[0] = (String) document.get("creatorId").toString();
                        DocumentReference docRef2 = db.collection("User").document(idCreator[0]);

                        //Get data del creador
                        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        textViewCreator.setText(document.get("username").toString());
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        //get data del riego
        /*CollectionReference docRefRiego = db.collection("Climate").document(id).collection("Riego");
        docRefRiego.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                numRiegos[0]++;
                                numDias.add(document.get("Día").toString());
                            }
                            textViewNumberTimes.setText(numRiegos[0] + " veces por semana");
                            if(numDias.size()==1){
                                textViewNumberDays.setText("1 día");

                            }else {
                                textViewNumberDays.setText(numDias.size() + "días");
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*/
        textViewClimateName.setText(id);
    }
}
