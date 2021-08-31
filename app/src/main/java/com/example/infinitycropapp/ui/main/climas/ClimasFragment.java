package com.example.infinitycropapp.ui.main.climas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.infinitycropapp.Firebase.Firestore.Firestore;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.climas.adapters.AdapterItemClimatesUser;
import com.example.infinitycropapp.ui.pojos.ItemClimate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClimasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClimasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public ClimasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClimasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClimasFragment newInstance(String param1, String param2) {
        ClimasFragment fragment = new ClimasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*---FIN -> datos necesarios para el fragment -> no cambiar ni rellenar ---*/

    //-----poner aca attributes etc... -----//
    //palette
    private ConstraintLayout empty_recyclerView;
    //swipe to refresh
    private SwipeRefreshLayout refreshLayout;
    //rvs
    private RecyclerView rv_climasInfinity; //Climas de InfinityCrop
    //lists
    private List<ItemClimate> itemClimatesInfinity=new ArrayList<>(); //infinity
    private List<Boolean> itemClimatesInfinitySaved=new ArrayList<>(); //infinity
    //adapters
    private AdapterItemClimatesUser adapterItemClimatesInfinity;
    //firebase
    private FirebaseFirestore db;
    //btn
    private ConstraintLayout btn_my_climates;
    private ConstraintLayout btn_climates_Ic;
    private ConstraintLayout btn_climas_guardados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //INICIO ----------- Create View
        View view = inflater.inflate(R.layout.fragment_climas, container, false);

        //firestore
        db= FirebaseFirestore.getInstance();

        //findById elements
        rv_climasInfinity=view.findViewById(R.id.rvClimasInfinity); //rv Climas Infinity
        empty_recyclerView= view.findViewById(R.id.empty_infinity_crop_climates_rv); //container empty rv
        refreshLayout = view.findViewById(R.id.refresh_fragment_climas);//refresh layout
        btn_my_climates= view.findViewById(R.id.btn_mis_climas_fragment);//mis climas
        btn_climas_guardados= view.findViewById(R.id.constraintLayout12);//climas guardados
        btn_climates_Ic= view.findViewById(R.id.constraintLayout9);//climas InfinityCrop

        //config rv
        initRvInf();
        getItemInf();
        setRvInfDatos();
        //config swipe to refresh
        swipeToRefresh();

        //onclicks

        //btn my climates
        btn_my_climates.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ActivityMisClimas.class);
                startActivity(i);
            }
        });

        //btn climas guardados
        btn_climas_guardados.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ActivityClimasGuardados.class);
                startActivity(i);
            }
        });


        //btn climas infinityCrop
        btn_climates_Ic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ActivityClimasInfinityCrop.class);
                startActivity(i);
            }
        });

        //FINAL ------------- Inflate the layout for this fragment
        return view;

    }

    // init Rvs
    private void initRvInf() {
        //manejador para declarar la direccion de los items del rv
        rv_climasInfinity.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //defino que el rv no tenga fixed size
        rv_climasInfinity.setHasFixedSize(false);
        rv_climasInfinity.setNestedScrollingEnabled(false);
    }
    private void getItemInf(){
        itemClimatesInfinity.clear(); //clear la list para que no se duplique
        itemClimatesInfinitySaved.clear(); //clear la list
        //creo un adaptador pasandole los elementos al contructor
        adapterItemClimatesInfinity=new AdapterItemClimatesUser(itemClimatesInfinity , itemClimatesInfinitySaved ,getContext(), this);
        //declaro que cual es el adaptador el rv
        rv_climasInfinity.setAdapter(adapterItemClimatesInfinity);
    }
    private void setRvInfDatos(){
        adapterItemClimatesInfinity.showShimmer=true;
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        itemClimatesInfinity.add(new ItemClimate("Tomates"));
        //saved list
        itemClimatesInfinitySaved.add(true);
        itemClimatesInfinitySaved.add(false);
        itemClimatesInfinitySaved.add(false);
        itemClimatesInfinitySaved.add(true);
        itemClimatesInfinitySaved.add(true);
        itemClimatesInfinitySaved.add(false);
        itemClimatesInfinitySaved.add(true);
        itemClimatesInfinitySaved.add(false);
        itemClimatesInfinitySaved.add(true);
        itemClimatesInfinitySaved.add(false);
        //dismiss loader
        adapterItemClimatesInfinity.showShimmer= false;
        adapterItemClimatesInfinity.notifyDataSetChanged();
        //quitar la animacion de recarga
        refreshLayout.setRefreshing(false);
        //lo ponemos despues del bool porque siempre que esta true devuelve 6 elemntos
        //si el recyclerView esta vacio
        if(adapterItemClimatesInfinity.getItemCount() == 0 ){
            rv_climasInfinity.setVisibility(View.GONE); //ocultar rv
            empty_recyclerView.setVisibility(View.VISIBLE); //mostrar elementos
        }else{ //sino
            rv_climasInfinity.setVisibility(View.VISIBLE); //mostras rv con sus items
            empty_recyclerView.setVisibility(View.GONE); //ocultar container con info
        }
        /*db.collection("Climate")
                .whereEqualTo("InfinityCropClimate", true)
                .limit(5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemClimate itemClimateInf=document.toObject(ItemClimate.class);
                                itemClimatesInfinity.add(itemClimateInf);
                            }

                            //dismiss loader
                            adapterItemClimatesInfinity.showShimmer= false;
                            adapterItemClimatesInfinity.notifyDataSetChanged();
                        } else {

                        }
                    }
                });*/

    }
    // FIN -> init Rvs
    //refresh methods
    private void swipeToRefresh(){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getItemInf();
                setRvInfDatos();
            }
        });
    }
    //FIN -> refresh methods
    //snackbar methods
    //set snackbar method
    public void setSnackbar(String snackBarText){
        Snackbar snackBar = Snackbar.make( getActivity().findViewById(R.id.general_layout_fragment_climas), snackBarText,Snackbar.LENGTH_LONG);
        snackBar.setActionTextColor(Color.CYAN);
        snackBar.setAnchorView(R.id.list_menuActivity);
        snackBar.setAction(getText(R.string.snack_close), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }
    //FIN -> snackbar methods
}