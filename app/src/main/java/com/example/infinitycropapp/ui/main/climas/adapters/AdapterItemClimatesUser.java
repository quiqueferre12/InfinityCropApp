package com.example.infinitycropapp.ui.main.climas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.climas.ActivityClima;
import com.example.infinitycropapp.ui.pojos.ItemClimate;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;

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

        if(showShimmer){ //if la animacion tiene que cargar
            holder.shimmerFrameLayout.startShimmer(); //start animation
            holder.name_climate.setVisibility(View.INVISIBLE);
            holder.img_climate.setVisibility(View.INVISIBLE);
            holder.container_save.setVisibility(View.INVISIBLE);
        }else{
            holder.shimmerFrameLayout.stopShimmer(); //stop animation
            holder.shimmerFrameLayout.setShimmer(null); //remove shimmer

            holder.name_climate.setVisibility(View.VISIBLE);
            holder.img_climate.setVisibility(View.VISIBLE);
            holder.container_save.setVisibility(View.VISIBLE);
            holder.card_climate.setBackgroundTintList(null); //quitar el tinte (color) gris del loader
            holder.container_txt_name.setBackground(null); //quitar el tinte (color) gris del loader

            //get the item
            final ItemClimate pojoItem= itemClimates.get(position);
            holder.name_climate.setText(pojoItem.getName());
            //onclick methods
            //entrar en la activity del clima
            holder.card_climate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityClima.class);
                    intent.putExtra("idClimate",itemClimates.get(position).getName());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int itemLoading=6; //numero de items animacion de cargar
        if(showShimmer){
            return itemLoading;
        }else {
            return itemClimates.size();
        }
    }


    public static class ItemClimateHolder extends RecyclerView.ViewHolder{

        protected TextView name_climate;
        protected ShimmerFrameLayout shimmerFrameLayout;
        protected MaterialCardView card_climate;
        protected ImageView img_climate;
        protected ConstraintLayout container_txt_name;
        protected ConstraintLayout container_save;
        protected ImageView save_climate;
        public ItemClimateHolder(@NonNull View itemView) {
            super(itemView);
            //defino aca los findById
            name_climate=itemView.findViewById(R.id.textViewNameClimate);
            card_climate =itemView.findViewById(R.id.climate_item_card);
            img_climate = itemView.findViewById(R.id.climate_item_image);
            container_txt_name = itemView.findViewById(R.id.climate_item_container_name);
            container_save = itemView.findViewById(R.id.climate_item_circle_save);
            save_climate = itemView.findViewById(R.id.climate_item_save_img);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer_climate_item);
        }
    }
}
