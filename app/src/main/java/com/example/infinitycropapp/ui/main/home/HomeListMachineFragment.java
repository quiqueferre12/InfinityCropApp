package com.example.infinitycropapp.ui.main.home;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.home.adapters.AdapterItemMachine;
import com.example.infinitycropapp.ui.main.home.adapters.AdapterItemPlaylist;
import com.example.infinitycropapp.ui.main.home.pojos.ItemMachine;
import com.example.infinitycropapp.ui.main.home.pojos.ItemPlaylist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeListMachineFragment extends Fragment {
    /*---datos necesarios para el fragment -> no cambiar ni rellenar ---*/
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public HomeListMachineFragment() {
        // Required empty public constructor
    }
    public static HomeListMachineFragment newInstance(String param1, String param2) {
        HomeListMachineFragment fragment = new HomeListMachineFragment();
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

    //rvs
    private RecyclerView rv_playlist; //playlist
    private RecyclerView rv_machine; //machine
    //adapters
    private AdapterItemPlaylist adapterItemPlaylist; //playlist
    private AdapterItemMachine adapterItemMachine; //machine
    //lists
    private List<ItemPlaylist> itemPlaylists=new ArrayList<>(); //playlist
    private List<ItemMachine> itemMachines=new ArrayList<>(); //machines
    //firebase

    //buttons,images && elements ...
    private FloatingActionButton btn_addMachine;
    private ConstraintLayout empty_recyclerView;
    private SwipeRefreshLayout refreshRv; //actualizar recyclerViews

    //--FIN-> poner aca attributes etc... -----//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home_list_machine, container, false);

        //findById elements
        rv_playlist=v.findViewById(R.id.rv_playlist); //rv playlisdt
        rv_machine=v.findViewById(R.id.rv_machines); //rv machine
        btn_addMachine=v.findViewById(R.id.button_add_machine); //btn add
        empty_recyclerView=v.findViewById(R.id.empty_recyclerView); //container con img de emty rv
        refreshRv=v.findViewById(R.id.refresh_list_machine); //actualizar todos los rv

        //onclick methods
        btn_addMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //rv playlist
        initRvPlaylist(); //init el rv playlists
        initRvMachines(); //init rv machines
        getItemPlaylist(); //get data to rv playlist
        getItemMachines(); //get dato to rv machines
        setDatosRv(); //set all rv content
        swipeDownToRefresh(); //swipe down to refresh


        return v;
    }
    // init Rvs
    private void initRvPlaylist(){
        //defino que el rv no tenga fixed size
        rv_playlist.setHasFixedSize(false);
        //manejador para declarar la direccion de los items del rv
        rv_playlist.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void initRvMachines() {
        //defino que el rv no tenga fixed size
        rv_machine.setHasFixedSize(false);
        //manejador para declarar la direccion de los items del rv
        rv_machine.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }
    // FIN -> init Rvs

    // get data to rvs

    //metodo que rellena la lista de elementos
    private void getItemPlaylist(){
        itemPlaylists.clear(); //clear la list para que no se duplique
        //creo un adaptador pasandole los elementos al contructor
        adapterItemPlaylist=new AdapterItemPlaylist(itemPlaylists,getContext());
        //declaro que cual es el adaptador el rv
        rv_playlist.setAdapter(adapterItemPlaylist);
    }
    private void getItemMachines() {
        itemMachines.clear(); //clear la list para que no se duplique
        //creo un adaptador pasandole los elementos al contructor
        adapterItemMachine=new AdapterItemMachine(itemMachines,getContext());
        //declaro que cual es el adaptador el rv
        rv_machine.setAdapter(adapterItemMachine);
    }
    // FIN -> data to rvs

    // rellenar recyclerViews
    private void setDatosRv(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //elementos de prueba
                try { //sin el try catch al volver atras al tutorial y entrar otra vez muere to fuerte
                    itemPlaylists.add(new ItemPlaylist(getResources().getString(R.string.playlist_all)));
                }catch (Exception e){

                }

                itemPlaylists.add(new ItemPlaylist("Favoritos"));
                itemMachines.add(new ItemMachine("Garden number B","Modelo: C90"));
                //itemMachines.add(new ItemMachine("Critical Jimbo","Modelo: IC6"));

                adapterItemPlaylist.showShimmer= false;
                adapterItemPlaylist.notifyDataSetChanged();
                adapterItemMachine.showShimmer= false;
                //lo ponemos despues del bool porque siempre que esta true devuelve 6 elemntos
                //si el recyclerView esta vacio
                if(adapterItemMachine.getItemCount() == 0 ){
                    rv_machine.setVisibility(View.GONE); //ocultar rv
                    empty_recyclerView.setVisibility(View.VISIBLE); //mostrar elementos
                }else{ //sino
                    rv_machine.setVisibility(View.VISIBLE); //mostras rv con sus items
                    empty_recyclerView.setVisibility(View.GONE); //ocultar container con info
                }
                adapterItemMachine.notifyDataSetChanged();
                refreshRv.setRefreshing(false);
            }
        },5000);
    }

    //actualizar al deslizar hacia abajo method
    private void swipeDownToRefresh(){
        refreshRv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //comienza la carga de la animacion
                adapterItemMachine.showShimmer=true;
                adapterItemPlaylist.showShimmer=true;
                adapterItemMachine.notifyDataSetChanged();
                adapterItemPlaylist.notifyDataSetChanged();
                getItemPlaylist(); //get data to rv playlist
                getItemMachines();
                setDatosRv();

            }
        });
    }

}