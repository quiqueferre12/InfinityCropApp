package com.example.infinitycropapp.ui.main.climas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.home.adapters.AdapterItemMachine;
import com.example.infinitycropapp.ui.main.home.adapters.AdapterItemPlaylist;
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

        if(showShimmer){
            holder.shimmerFrameLayout.startShimmer(); //start animation
            holder.name_climate.setVisibility(View.INVISIBLE);
            holder.img_climate.setVisibility(View.INVISIBLE);
            holder.item_climate.setVisibility(View.INVISIBLE);
            holder.item_climate2.setVisibility(View.INVISIBLE);
        }else{
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);
            holder.item_climate2.setBackgroundTintList(null);
            holder.item_climate.setBackgroundTintList(null);

            holder.name_climate.setVisibility(View.VISIBLE);
            holder.img_climate.setVisibility(View.VISIBLE);
            holder.item_climate.setVisibility(View.VISIBLE);
            holder.item_climate2.setVisibility(View.VISIBLE);

            final ItemClimate pojoItem= itemClimates.get(position);
            holder.name_climate.setText(pojoItem.getName());
        }
    }

    @Override
    public int getItemCount() {
        int itemLoading=6; //numero de items animacion de cargar
        if(showShimmer){
            return itemLoading;
        }else{
            return itemClimates.size();
        }
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
            shimmerFrameLayout=itemView.findViewById(R.id.shimmer_climate_item);
            item_climate=itemView.findViewById(R.id.climate_item);
            item_climate2=itemView.findViewById(R.id.climate_item2);
            img_climate=itemView.findViewById(R.id.imageViewClimate);

        }
    }
}
