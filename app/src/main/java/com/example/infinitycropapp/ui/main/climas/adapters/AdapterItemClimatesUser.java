package com.example.infinitycropapp.ui.main.climas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.climas.ActivityClima;
import com.example.infinitycropapp.ui.main.climas.ActivityMisClimas;
import com.example.infinitycropapp.ui.main.climas.ClimasFragment;
import com.example.infinitycropapp.ui.main.home.HomeListMachineFragment;
import com.example.infinitycropapp.ui.pojos.ItemClimate;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class AdapterItemClimatesUser extends RecyclerView.Adapter<AdapterItemClimatesUser.ItemClimateHolder> implements Filterable {
    private Context context; //contexto
    public boolean showShimmer=true; //mostrar o no loader
    private Fragment fragment;
    private List<ItemClimate> itemClimates;
    private List<Boolean> itemClimatesSaved;
    //filter data
    private List<ItemClimate> itemClimatesFiltered;
    private List<Boolean> itemClimatesSavedFiltered;
    View view;
    //constructor


    //para fragment
    public AdapterItemClimatesUser(List<ItemClimate> itemClimates, List<Boolean> itemClimatesSaved , Context context, Fragment fragment, View view) {
        this.context = context;
        this.fragment = fragment;
        this.itemClimates = itemClimates;
        this.itemClimatesSaved = itemClimatesSaved;
        this.itemClimatesFiltered = itemClimates;
        this.itemClimatesSavedFiltered = itemClimatesSaved;
        this.view = view;
    }
    //para activity
    public AdapterItemClimatesUser(List<ItemClimate> itemClimates, List<Boolean> itemClimatesSaved, Context context, View view) {
        this.context = context;
        this.itemClimates = itemClimates;
        this.itemClimatesSaved = itemClimatesSaved;
        this.itemClimatesFiltered = itemClimates;
        this.itemClimatesSavedFiltered = itemClimatesSaved;
        this.view = view;
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
            final ItemClimate pojoItem= itemClimatesFiltered.get(position);
            Boolean savedItem= itemClimatesSavedFiltered.get(position);
            holder.name_climate.setText(pojoItem.getName());
            if(savedItem){
                holder.save_climate.setImageResource(R.drawable.ic_heart_full);
                holder.save_climate.setColorFilter(ContextCompat.getColor(context, R.color.red));
            }else{
                holder.save_climate.setImageResource(R.drawable.ic_heart_empty);
                holder.save_climate.setColorFilter(ContextCompat.getColor(context, R.color.white));
            }
            //onclick methods
            //entrar en la activity del clima
            holder.card_climate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityClima.class);
                    //AQUI IRA EL ID DEL CLIMA
                    intent.putExtra("id", "Rnw2WvyzpSsT6GO35eX1");
                    context.startActivity(intent);
                }
            });
            //guardar clima
            holder.container_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean changed = itemClimatesSaved.get(position);
                    changed= !changed;
                    itemClimatesSaved.set(position, changed);
                    notifyItemChanged(position);
                    //snackbar se ha puesto o no a favoritos
                    String message="";
                    if(changed){
                        //llamar a la funcion que hace el snackbar
                        message= context.getString(R.string.snack_clima_favorite) ;
                    }else{
                        //llamar a la funcion que hace el snackbar
                        message= context.getString(R.string.snack_clima_quit_favorite);
                    }
                    if(fragment != null){ //si es adapter en un fragment
                        ((ClimasFragment) fragment).setSnackbar(message);
                    }else{ //si esta en un activiyu
                        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
                    }
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
            return itemClimatesFiltered.size();
        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String key= constraint.toString(); //get the char of the search
                if(key.isEmpty()){ //si esta vacio rellenar con all
                    itemClimatesFiltered = itemClimates;
                }else{ //sino
                    List<ItemClimate> itemsFiltered = new ArrayList<>();
                    for(ItemClimate row: itemClimates){
                        //all minusculas y comprobamos si existe cada secuencia de chars
                        if(row.getName().toLowerCase().contains(key.toLowerCase())){
                            itemsFiltered.add(row); //add los climas que se filtran
                        }

                    }
                    //set final array
                    itemClimatesFiltered = itemsFiltered;
                }

                FilterResults results = new FilterResults();
                results.values = itemClimatesFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemClimatesFiltered = (List<ItemClimate>) results.values;
                notifyDataSetChanged();
            }
        };

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
