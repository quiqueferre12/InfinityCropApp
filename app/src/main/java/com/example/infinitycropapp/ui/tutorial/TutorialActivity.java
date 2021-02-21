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
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private TabLayout dotsTabLayout;

    private SliderAdapter sliderAdapter;

    //Botón Skip
    private Button btSkip;

    private int currentPage;

    //Id del usuario
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


        dotsTabLayout = findViewById(R.id.dots);
        slideViewPager = findViewById(R.id.viewPager);
        /*btBack = findViewById(R.id.btBack);*/
        btSkip = findViewById(R.id.btSkip);

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
        btSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                    finish();
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
            if (position == sliderAdapter.getCount() -1){
                //En la última página, el botón para ir hacia atrás aparece, aquí podríamos modificar la flecha hacia delante

                btSkip.setText(getString(R.string.boton_skip_final));
            }else{
                //En el resto de páginas, el botón para ir hacia atrás aparece

                btSkip.setText(getString(R.string.boton_skip));

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
