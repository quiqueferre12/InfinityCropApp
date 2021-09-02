package com.example.infinitycropapp.ui.main.climas;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.climas.adapters.AdapterItemClimatesUser;
import com.example.infinitycropapp.ui.pojos.ItemClimate;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ActivityClimasGuardados extends AppCompatActivity {


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
    //adapters
    private AdapterItemClimatesUser adapterItemClimatesGuardados;
    //search input
    private EditText search_input;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climas_guardados);


        //findById elements
        btn_back=findViewById(R.id.constraintLayout18);
        rv_climates= findViewById(R.id.rv_climas_guardados_activity);
        refreshLayout= findViewById(R.id.refresh_climates_guardados_layout);
        empty_rv= findViewById(R.id.empty_climates_guardados_rv);
        search_input= findViewById(R.id.editTextTextPersonName2);

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
        //creo un adaptador pasandole los elementos al contructor
        adapterItemClimatesGuardados =new AdapterItemClimatesUser(itemClimatesInfinity , itemClimatesInfinitySaved ,this, findViewById(R.id.general_layout_climas_guardados), false);
        //declaro que cual es el adaptador el rv
        rv_climates.setAdapter(adapterItemClimatesGuardados);
    }
    private void setRvInfDatos(){
        adapterItemClimatesGuardados.showShimmer=true;
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        itemClimatesInfinity.add(new ItemClimate("Pepe"));
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        itemClimatesInfinity.add(new ItemClimate("Maria"));
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        //saved list
        itemClimatesInfinitySaved.add(true);
        itemClimatesInfinitySaved.add(true);
        itemClimatesInfinitySaved.add(true);
        itemClimatesInfinitySaved.add(true);
        itemClimatesInfinitySaved.add(true);
        //dismiss loader
        adapterItemClimatesGuardados.showShimmer= false;
        adapterItemClimatesGuardados.notifyDataSetChanged();
        //quitar la animacion de recarga
        refreshLayout.setRefreshing(false);
        //lo ponemos despues del bool porque siempre que esta true devuelve 6 elemntos
        //si el recyclerView esta vacio
        if(adapterItemClimatesGuardados.getItemCount() == 0 ){
            rv_climates.setVisibility(View.GONE); //ocultar rv
            empty_rv.setVisibility(View.VISIBLE); //mostrar elementos
        }else{ //sino
            rv_climates.setVisibility(View.VISIBLE); //mostras rv con sus items
            empty_rv.setVisibility(View.GONE); //ocultar container con info
        }
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
                adapterItemClimatesGuardados.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    // FIN -> search method
}
