package com.example.infinitycropapp.ui.main.home.models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.infinitycropapp.R;

public class ListDevicesActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {


    public ConstraintLayout bt_back;
    public String idMachine;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_devices);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, new DevicesFragment(), "devices").commit();
        bt_back=findViewById(R.id.back_nis);
        Intent intent= getIntent();
        idMachine = intent.getStringExtra("id");
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackStackChanged() {

    }
}