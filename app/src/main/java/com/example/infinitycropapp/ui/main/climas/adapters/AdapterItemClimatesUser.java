package com.example.infinitycropapp.ui.main.climas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.climas.ActivityClima;
import com.example.infinitycropapp.ui.main.home.adapters.AdapterItemMachine;
import com.example.infinitycropapp.ui.main.home.adapters.AdapterItemPlaylist;
import com.example.infinitycropapp.ui.main.home.models.IC6Activity;
import com.example.infinitycropapp.ui.pojos.ItemClimate;
import com.example.infinitycropapp.ui.pojos.ItemMachine;
import com.example.infinitycropapp.ui.pojos.ItemPlaylist;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AdapterItemClimatesUser extends RecyclerView.Adapter<AdapterItemClimatesUser.ItemClimateHolder> {
    private Context context; //contexto
    public boolean showShimmer=true; //mostrar o no loader
    private Fragment fragment;
    private List<ItemClimate> itemClimates;
    //constructor
    public AdapterItemClimatesUser (List<ItemClimate> itemClimates, Context context){
        this.context=context;
        this.itemClimates=itemClimates;
    }

    @NonNull
    @Override
    public ItemClimateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.climate_item,parent,false);

        return new ItemClimateHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemClimateHolder holder, int position) {

        final ItemClimate pojoItem= itemClimates.get(position);
        holder.name_climate.setText(pojoItem.getName());
        //onclick methods
        //entrar en el panel de control de la maquina
        /*holder.img_climate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //comprobar que modelo es y entrar en el activity adecuado
                Intent intent = new Intent(context, ActivityClima.class);
                intent.putExtra("idClimate",itemClimates.get(position).getName());
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return itemClimates.size();
    }


    public static class ItemClimateHolder extends RecyclerView.ViewHolder{

        protected TextView name_climate;
        protected ShimmerFrameLayout shimmerFrameLayout;
        protected MaterialCardView item_climate;
        protected MaterialCardView item_climate2;
        protected ImageView img_climate;
        public ItemClimateHolder(@NonNull View itemView) {
            super(itemView);
            //defino aca los findById
            name_climate=itemView.findViewById(R.id.textViewNameClimate);
        }
    }
}
