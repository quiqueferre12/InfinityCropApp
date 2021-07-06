package com.example.infinitycropapp.ui.main.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 //elementos de prueba
                 itemPlaylists.add(new ItemPlaylist(getResources().getString(R.string.playlist_all)));
                 itemPlaylists.add(new ItemPlaylist("Favoritos"));
                 itemMachines.add(new ItemMachine("Favoritos","asd"));
                 itemMachines.add(new ItemMachine("Favoritos","ds"));

                 adapterItemPlaylist.showShimmer= false;
                 adapterItemPlaylist.notifyDataSetChanged();
                 adapterItemMachine.notifyDataSetChanged();
             }
         },5000);

        return v;
    }
    // rv playlist
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
    // FIN -> rv playlist
}