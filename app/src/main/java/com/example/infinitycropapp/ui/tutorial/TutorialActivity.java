package com.example.infinitycropapp.ui.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.tutorial.Adapter.SliderAdapter;
import com.example.infinitycropapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private TabLayout dotsTabLayout;

    private SliderAdapter sliderAdapter;

    //Botón hacia delante
    private Button btBack;

    //Botón hacia atrás
    private Button btNext;

    private int currentPage;

    //Id del usuario
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


        dotsTabLayout = findViewById(R.id.dots);
        slideViewPager = findViewById(R.id.viewPager);
        btBack = findViewById(R.id.btBack);
        btNext = findViewById(R.id.btNext);

        Intent intent = getIntent();

        //Sacamos la id del usuario por el intent

        //Descomentar cuando tengamos Firestore
        //Uid = intent.getStringExtra("id");

        //Inicializamos el adapter
        sliderAdapter = new SliderAdapter(this);
        slideViewPager.setAdapter(sliderAdapter);

        //Inicializamos los dots
        dotsTabLayout.setupWithViewPager(slideViewPager);
        setUpDots();

        slideViewPager.addOnPageChangeListener(viewListener);

        // Se pulsa el botón para ir hacia adelante
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                if(currentPage == (sliderAdapter.getCount()-1)){
                    //Si salimos del tutorial, se modifica el usuario en firebase, nuevo = false

                    //Descomentar cuando tengamos Firestore

                    /*DocumentReference user = db.collection("usuarios").document(Uid);
                    user.update("nuevo", false);*/

                    finish();
                }else{
                    //Pasamos de página en el tutorial
                    slideViewPager.setCurrentItem(currentPage + 1);
                }
            }
        });

        // Se pulsa el botón para ir hacia atrás
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideViewPager.setCurrentItem(currentPage - 1);
            }
        });
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //Por esta función, si tocamos un dot, pasamos a la página correspondiente
            currentPage = position;
            if(currentPage == 0){
                //En la primera página, el botón para ir hacia atrás desaparece

                btNext.setEnabled(true);
                btBack.setEnabled(false);
                btBack.setVisibility(View.GONE);
            }else if (position == sliderAdapter.getCount() -1){
                //En la última página, el botón para ir hacia atrás aparece, aquí podríamos modificar la flecha hacia delante

                btNext.setEnabled(true);
                btBack.setEnabled(true);
                btBack.setVisibility(View.VISIBLE);
            }else{
                //En el resto de páginas, el botón para ir hacia atrás aparece

                btNext.setEnabled(true);
                btBack.setEnabled(true);
                btBack.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private void setUpDots(){
        //Actualizamos los botones, poniendo como habilitado el que estamos pulsando
        ViewGroup tabStrip = (ViewGroup) dotsTabLayout.getChildAt(0);
        for (int i = 0; i<tabStrip.getChildCount(); i++){
            View tabView = tabStrip.getChildAt(i);
            if(tabView != null){
                int paddingStart = tabView.getPaddingStart();
                int paddingTop = tabView.getPaddingTop();
                int paddingEnd = tabView.getPaddingEnd();
                int paddingBottom = tabView.getPaddingBottom();
                ViewCompat.setBackground(tabView, AppCompatResources.getDrawable(tabView.getContext(), R.drawable.tab_color));
                ViewCompat.setPaddingRelative(tabView, paddingStart, paddingTop, paddingEnd, paddingBottom);
            }
        }
    }
}
