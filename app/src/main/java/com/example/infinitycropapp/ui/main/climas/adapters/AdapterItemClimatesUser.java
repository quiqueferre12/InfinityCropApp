package com.example.infinitycropapp.ui.main.climas.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.ui.main.home.adapters.AdapterItemMachine;
import com.example.infinitycropapp.ui.pojos.ItemMachine;

import java.util.ArrayList;
import java.util.List;

public class AdapterItemClimatesUser extends RecyclerView.Adapter<AdapterItemMachine.ItemMachineHolder> {
    private Context context; //contexto
    public boolean showShimmer=true; //mostrar o no loader
    private Fragment fragment;
    @NonNull
    @Override
    public AdapterItemMachine.ItemMachineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemMachine.ItemMachineHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
