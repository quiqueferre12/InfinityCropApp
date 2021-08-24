package com.example.infinitycropapp.ui.main.climas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    //rvs
    private RecyclerView rv_climasInfinity; //Climas de InfinityCrop
    //lists
    private List<ItemClimate> itemClimatesInfinity=new ArrayList<>(); //infinity
    //adapters
    private AdapterItemClimatesUser adapterItemClimatesInfinity;
    //firebase
    private FirebaseFirestore db;

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


        //config rv
        initRvInf();
        getItemInf();
        setRvInfDatos();

        //onclicks imgv
        /*im_climasUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ActivityMisClimas.class);
                startActivity(i);
            }
        });*/

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
        //creo un adaptador pasandole los elementos al contructor
        adapterItemClimatesInfinity=new AdapterItemClimatesUser(itemClimatesInfinity ,getContext());
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
        //dismiss loader
        adapterItemClimatesInfinity.showShimmer= false;
        adapterItemClimatesInfinity.notifyDataSetChanged();
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
}