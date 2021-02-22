package com.example.infinitycropapp.ui.pantalla_carga;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;

public class SplashScreen extends AppCompatActivity {

    Animation topAnim;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pantalla_de_carga_splash_screen);

        //Declaramos animación hacia arriba
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation_delay);

        //Declaramos imagen
        logo = findViewById(R.id.logoImageView);

        //Adjuntamos animación
        logo.setAnimation(topAnim);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(), MainListActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        thread.start();
    }

}