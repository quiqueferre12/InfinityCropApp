package com.example.infinitycropapp.ui.main.climas;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;
import com.example.infinitycropapp.ui.main.guia.GuiaBotanicaFragment;
import com.example.infinitycropapp.ui.main.home.HomeListMachineFragment;
import com.example.infinitycropapp.ui.main.profile.ProfileFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

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

    //rvs
    private RecyclerView rv_climasUser; //Mis Climas
    private RecyclerView rv_climasInfinity; //Climas de InfinityCrop
    private RecyclerView rv_climasCommunity; //Climas de la comunidad


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //INICIO ----------- Create View
        View view = inflater.inflate(R.layout.fragment_climas, container, false);

        //findById elements
        rv_climasUser=view.findViewById(R.id.rvClimasUser); //rv Climas User
        rv_climasInfinity=view.findViewById(R.id.rvClimasInfinity); //rv Climas Infinity
        rv_climasCommunity=view.findViewById(R.id.rvClimasCommunity); //rv Climas Community



        //FINAL ------------- Inflate the layout for this fragment
        return view;

    }

    // init Rvs
    private void initRvUser(){
        //defino que el rv no tenga fixed size
        rv_climasUser.setHasFixedSize(false);
        //manejador para declarar la direccion de los items del rv
        rv_climasUser.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void initRvInf() {
        //defino que el rv no tenga fixed size
        rv_climasInfinity.setHasFixedSize(false);
        //manejador para declarar la direccion de los items del rv
        rv_climasInfinity.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
    private void initRvCom(){
        //defino que el rv no tenga fixed size
        rv_climasCommunity.setHasFixedSize(false);
        //manejador para declarar la direccion de los items del rv
        rv_climasCommunity.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

    // FIN -> init Rvs
}