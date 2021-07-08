package com.example.infinitycropapp.ui.main.profile;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;
import com.example.infinitycropapp.ui.main.climas.ClimasFragment;
import com.example.infinitycropapp.ui.main.guia.GuiaBotanicaFragment;
import com.example.infinitycropapp.ui.main.home.HomeListMachineFragment;
import com.example.infinitycropapp.ui.main.profile.fotos_profile.FotosProfileFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.Console;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Tabs
        TabLayout tabLayout = null;
        TabItem tab1 = null, tab2 = null, tab3= null;

        ViewPager viewPager= null; //viewpager object

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        tabLayout =(TabLayout) view.findViewById(R.id.profile_tabs);
        tab1 =(TabItem) view.findViewById(R.id.PhotosTabItem);
        tab2 =(TabItem) view.findViewById(R.id.CommunityTabItem);
        tab3 =(TabItem) view.findViewById(R.id.ClimatesTabItem);

        viewPager = (ViewPager) view.findViewById(R.id.wrapContentHeightViewPager);



        //definimos adaptador para viewpager (adaptador esta debajo en una clase aparte)
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        //adapter para los fragments
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        //instancia de los fragments
        final FotosProfileFragment fragment_1 = new FotosProfileFragment();
        final ClimasFragment fragment_2 = new ClimasFragment();
        final GuiaBotanicaFragment fragment_3 = new GuiaBotanicaFragment();
        final ProfileFragment fragment_4 = new ProfileFragment();
        //set fragments into adapter
        adapter.addFragment(fragment_1);
        adapter.addFragment(fragment_2);
        adapter.addFragment(fragment_3);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);//limite de fragments para que no boom

        ViewPager finalViewPager = viewPager;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                finalViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    //clase adaptador para el menu
    class ViewPagerAdapter extends FragmentPagerAdapter {
        //lista donde estan los fragments
        private ArrayList<Fragment> fragments;
        //lista del nombre de los fragments
        private ArrayList<String> titles;
        //constructor
        ViewPagerAdapter (FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();

        }
        //get la posicion del fragment de la lista
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        //get tamaño
        @Override
        public int getCount() {
            return fragments.size();
        }
        //añadir fragment
        public void addFragment(Fragment fragment){
            fragments.add(fragment);

        }

    }
}