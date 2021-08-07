package com.example.infinitycropapp.ui.main.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.infinitycropapp.Firebase.Firestore.Firestore;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.home.adapters.AdapterItemMachine;
import com.example.infinitycropapp.ui.main.home.adapters.AdapterItemPlaylist;
import com.example.infinitycropapp.ui.main.home.newMachine.NewMachineActivity;
import com.example.infinitycropapp.ui.pojos.ItemMachine;
import com.example.infinitycropapp.ui.pojos.ItemPlaylist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    private List<String> itemIDs= new ArrayList<>(); // list of id string
    //firebase
    private FirebaseFirestore db;
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

        //firestore
        //llamamos al back end service
        db= FirebaseFirestore.getInstance();

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
                Intent intent = new Intent(getContext(), NewMachineActivity.class);
                startActivity(intent);
            }
        });

        //rv playlist
        initRvPlaylist(); //init el rv playlists
        initRvMachines(); //init rv machines
        getItemPlaylist(); //get data to rv playlist
        setPlaylistDatos(); //set playlist data
        setMachineDatos("All"); //set all machines
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

        rv_playlist.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    if (child != null) {
                        //get the position
                        int position = recyclerView.getChildAdapterPosition(child);
                        //cuando  se pulsa el item se guarda la posicion en una variable
                        adapterItemPlaylist.cont=position;
                        adapterItemPlaylist.notifyDataSetChanged();
                        //get the pojo oh the item
                        ItemPlaylist itemPlaylist= itemPlaylists.get(position);
                        String nameCurrent=itemPlaylist.getName();
                        //Toast.makeText(getContext(),itemPlaylist.getName() ,Toast.LENGTH_SHORT).show();
                        if(nameCurrent.equals("All") || nameCurrent.equals("Todas")){
                            setMachineDatos("All");
                        }else
                            if (nameCurrent.equals("Favorites") || nameCurrent.equals("Favoritos")){
                                setMachineDatos("Favorites");
                        }
                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
    private void getItemMachines() {
        itemMachines.clear(); //clear la list para que no se duplique
        itemIDs.clear();
        //creo un adaptador pasandole los elementos al contructor
        adapterItemMachine=new AdapterItemMachine(itemMachines, itemIDs ,getContext(), this);
        //declaro que cual es el adaptador el rv
        rv_machine.setAdapter(adapterItemMachine);
    }
    // FIN -> data to rvs

    //actualizar al deslizar hacia abajo method
    private void swipeDownToRefresh(){
        refreshRv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshRv();
            }
        });
    }

    public void RefreshRv(){
        //comienza la carga de la animacion
        adapterItemMachine.showShimmer=true;
        adapterItemPlaylist.showShimmer=true;
        adapterItemMachine.notifyDataSetChanged();
        adapterItemPlaylist.notifyDataSetChanged();
        getItemPlaylist(); //get data to rv playlist
        setPlaylistDatos();
        setMachineDatos("All");
    }

    private void setPlaylistDatos(){
        itemPlaylists.clear();
        //elementos de prueba
        try { //sin el try catch al volver atras al tutorial y entrar otra vez muere to fuerte
            itemPlaylists.add(new ItemPlaylist(getResources().getString(R.string.playlist_all)));
        }catch (Exception e){

        }
        itemPlaylists.add(new ItemPlaylist(getResources().getString(R.string.step3_favorite)));
        //dismiss loader
        adapterItemPlaylist.showShimmer= false;
        adapterItemPlaylist.notifyDataSetChanged();
        refreshRv.setRefreshing(false);
    }

    private void setMachineDatos(String filtro){
        getItemMachines();
        adapterItemMachine.showShimmer= true;
        adapterItemMachine.notifyDataSetChanged();
        Firestore firestore=new Firestore();
        String id=firestore.GetIdUser();
        //si es una playlist
        db.collection("Machine")
                .whereEqualTo("creatorID", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemMachine itemMachine=document.toObject(ItemMachine.class);
                                if(filtro.equals("All")){
                                    itemIDs.add(document.getId());
                                    itemMachines.add(itemMachine);
                                }else if(filtro.equals("Favorites")){
                                    if(itemMachine.isFavorite()){
                                        itemIDs.add(document.getId());
                                        itemMachines.add(itemMachine);
                                    }
                                }
                            }
                            //dismiss loader
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
                        } else {

                        }
                    }
                });

    }

    //set snackbar method
    public void setSnackbar(String snackBarText){
        Snackbar snackBar = Snackbar.make( getActivity().findViewById(R.id.general_layout_fragment_listmachine), snackBarText,Snackbar.LENGTH_LONG);
        snackBar.setActionTextColor(Color.CYAN);
        snackBar.setAnchorView(R.id.button_add_machine);
        snackBar.setAction(getText(R.string.snack_close), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }

}