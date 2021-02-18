package com.example.infinitycropapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.tutorial.TutorialActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //prueba del tutorial
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);

    }
}