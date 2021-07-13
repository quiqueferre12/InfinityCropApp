package com.example.infinitycropapp.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.climas.ClimasFragment;
import com.example.infinitycropapp.ui.main.guia.GuiaBotanicaFragment;
import com.example.infinitycropapp.ui.main.home.HomeListMachineFragment;
import com.example.infinitycropapp.ui.main.log.activity_log_start;
import com.example.infinitycropapp.ui.main.profile.ProfileFragment;
import com.example.infinitycropapp.ui.tutorial.TutorialActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class MainListActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //objeto menu
    private ChipNavigationBar chipNavigationBar;
    private ViewPager viewPager; //viewpager object
    private Fragment fragment=null; //init var fragment
    private int index = 0; //cont que usaremos para cambiar de fragment

    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);
        //prueba del tutorial
        //Intent intent = new Intent(this, TutorialActivity.class);
        //startActivity(intent);

        //definiciones
        chipNavigationBar=findViewById(R.id.list_menuActivity);
        viewPager = findViewById(R.id.viewPager_listActivity);

        //el boton del inicio estara marcado por defecto
        chipNavigationBar.setItemSelected(R.id.item_list_home,true);

        //definimos adaptador para viewpager (adaptador esta debajo en una clase aparte)
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        //adapter para los fragments
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //instancia de los fragments
        final HomeListMachineFragment fragment_1 = new HomeListMachineFragment();
        final ClimasFragment fragment_2 = new ClimasFragment();
        final GuiaBotanicaFragment fragment_3 = new GuiaBotanicaFragment();
        final ProfileFragment fragment_4 = new ProfileFragment();
        //set fragments into adapter
        adapter.addFragment(fragment_1);
        adapter.addFragment(fragment_2);
        adapter.addFragment(fragment_3);
        adapter.addFragment(fragment_4);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);//limite de fragments para que no boom

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        index = 1; //1 -> inicio
        //set menu
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch(i){
                    case R.id.item_list_home: //inicio
                        viewPager.setCurrentItem(0); //posicion 0
                        break;
                    case R.id.item_list_climas: //climas
                        viewPager.setCurrentItem(1);//posicion 1
                        break;
                    case R.id.item_list_guia_botanica: //guia
                        viewPager.setCurrentItem(2);//posicion 2
                        break;
                    case R.id.item_list_perfil: //perfil
                        viewPager.setCurrentItem(3);//posicion 3
                        break;
                }


            }
        });
        //cambio de pestañas
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) { //cuando se selecciona una pestaña

                if (position == 0){ //cuando se selcciona inicio
                    //cambio de hover item
                    chipNavigationBar.setItemSelected(R.id.item_list_home, true);
                    chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(int id) { //instancia otra vez el menu

                            switch(id){
                                case R.id.item_list_home:
                                    viewPager.setCurrentItem(0);
                                    break;
                                case R.id.item_list_climas:
                                    viewPager.setCurrentItem(1);
                                    break;
                                case R.id.item_list_guia_botanica:
                                    viewPager.setCurrentItem(2);
                                    break;
                                case R.id.item_list_perfil:
                                    viewPager.setCurrentItem(3);
                                    break;
                            }
                        }
                    });

                    index = 0;

                }else if (position == 1){ //cuando se selcciona climas

                    chipNavigationBar.setItemSelected(R.id.item_list_climas, true);
                    index = 1;
                }else if (position == 2){ //cuando se selcciona guia

                    chipNavigationBar.setItemSelected(R.id.item_list_guia_botanica, true);
                    chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(int id) {

                            switch(id){
                                case R.id.item_list_home:
                                    viewPager.setCurrentItem(0);
                                    break;
                                case R.id.item_list_climas:
                                    viewPager.setCurrentItem(1);
                                    break;
                                case R.id.item_list_guia_botanica:
                                    viewPager.setCurrentItem(2);
                                    break;
                                case R.id.item_list_perfil:
                                    viewPager.setCurrentItem(3);
                                    break;
                            }
                        }
                    });

                    index = 2;

                }else if (position == 3){ //cuando se selcciona perfil

                    chipNavigationBar.setItemSelected(R.id.item_list_perfil, true);
                    chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(int id) {

                            switch(id){
                                case R.id.item_list_home:
                                    viewPager.setCurrentItem(0);
                                    break;
                                case R.id.item_list_climas:
                                    viewPager.setCurrentItem(1);
                                    break;
                                case R.id.item_list_guia_botanica:
                                    viewPager.setCurrentItem(2);
                                    break;
                                case R.id.item_list_perfil:
                                    viewPager.setCurrentItem(3);
                                    break;
                            }
                        }
                    });

                    index = 3;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account= result.getSignInAccount();
        }else{
            //goLoginScreen();
        }
    }

    private void goLoginScreen() {
        Intent intent= new Intent(this, activity_log_start.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLoginScreen();
                }else{
                    Toast.makeText(getApplicationContext(), "Cerrando sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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