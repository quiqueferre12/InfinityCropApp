package com.example.infinitycropapp.ui.main.climas;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.infinitycropapp.Firebase.Auth.User;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.climas.adapters.AdapterItemClimatesUser;
import com.example.infinitycropapp.ui.pojos.ItemClimate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityClimasInfinityCrop  extends AppCompatActivity {


    //-----poner aca attributes etc... -----//
    //btn back
    private ConstraintLayout btn_back;
    //rv
    private RecyclerView rv_climates;
    //empty recyclerView contenedor
    private ConstraintLayout empty_rv;
    //swipe refresh
    private SwipeRefreshLayout refreshLayout;
    //lists
    private List<ItemClimate> itemClimatesInfinity=new ArrayList<>(); //infinity
    private List<Boolean> itemClimatesInfinitySaved=new ArrayList<>(); //infinity
    private List<String> itemClimatesUIDInfinitySaved=new ArrayList<>(); //infinity
    private List<String> uidClimates = new ArrayList<>(); //infinity
    //adapters
    private AdapterItemClimatesUser adapterItemClimatesInfinity;
    //search input
    private EditText search_input;
    //firestore
    private FirebaseFirestore db;
    //auth
    User user;
    //getId
    private String idUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climas_infinitycrop);

        //firestore
        //llamamos al back end service
        db= FirebaseFirestore.getInstance();
        user = new User();
        //getId
        idUser = user.getId();

        //findById elements
        btn_back=findViewById(R.id.constraintLayoutic);
        rv_climates= findViewById(R.id.rv_climas_infinityCrop_activity);
        refreshLayout= findViewById(R.id.refresh_climates_ic_layout);
        empty_rv= findViewById(R.id.empty_climates_Ic_rv);
        search_input= findViewById(R.id.editTextTextPersonName3);

        //config rv
        initRvInf();
        getItemInf();
        setRvInfDatos();
        //config swipe to refresh
        swipeToRefresh();
        //search logic
        searchMethod();

        //onclicks
        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
    // init Rvs
    private void initRvInf() {
        //manejador para declarar la direccion de los items del rv
        rv_climates.setLayoutManager(new GridLayoutManager(this, 2));
        //defino que el rv no tenga fixed size
        rv_climates.setHasFixedSize(false);
        rv_climates.setNestedScrollingEnabled(false);
    }
    private void getItemInf(){
        itemClimatesInfinity.clear(); //clear la list para que no se duplique
        itemClimatesInfinitySaved.clear(); //clear la list
        itemClimatesUIDInfinitySaved.clear();
        uidClimates.clear();
        //creo un adaptador pasandole los elementos al contructor
        adapterItemClimatesInfinity =new AdapterItemClimatesUser(itemClimatesInfinity , itemClimatesUIDInfinitySaved, uidClimates ,this,  findViewById(R.id.general_layout_climas_ic), false);
        //declaro que cual es el adaptador el rv
        rv_climates.setAdapter(adapterItemClimatesInfinity);
    }
    private void setRvInfDatos(){
        adapterItemClimatesInfinity.showShimmer=true;

        db.collection("Climate")
                .whereEqualTo("infinityCropClimate", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemClimate itemClimate = document.toObject(ItemClimate.class);

                                itemClimatesInfinity.add(itemClimate);
                                uidClimates.add(document.getId());

                            }


                            //dismiss loader
                            adapterItemClimatesInfinity.showShimmer= false;
                            adapterItemClimatesInfinity.notifyDataSetChanged();
                            //quitar la animacion de recarga
                            refreshLayout.setRefreshing(false);
                            //lo ponemos despues del bool porque siempre que esta true devuelve 6 elemntos
                            //si el recyclerView esta vacio
                            if(adapterItemClimatesInfinity.getItemCount() == 0 ){
                                rv_climates.setVisibility(View.GONE); //ocultar rv
                                empty_rv.setVisibility(View.VISIBLE); //mostrar elementos
                            }else{ //sino
                                rv_climates.setVisibility(View.VISIBLE); //mostras rv con sus items
                                empty_rv.setVisibility(View.GONE); //ocultar container con info
                            }
                        }
                    }
                });
    }
    // FIN -> init Rvs
    //refresh methods
    private void swipeToRefresh(){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getItemInf();
                setRvInfDatos();
                searchMethod();
            }
        });
    }
    //FIN -> refresh methods
    //snackbar methods
    //set snackbar method
    public void setSnackbar(String snackBarText){
        Snackbar snackBar = Snackbar.make( findViewById(R.id.general_layout_fragment_mis_climas), snackBarText,Snackbar.LENGTH_LONG);
        snackBar.setActionTextColor(Color.CYAN);
        snackBar.setAnchorView(R.id.button_add_climate);
        snackBar.setAction(getText(R.string.snack_close), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }
    //FIN -> snackbar methods

    // search method
    private void searchMethod(){
        search_input.setText("");
        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterItemClimatesInfinity.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    // FIN -> search method
}