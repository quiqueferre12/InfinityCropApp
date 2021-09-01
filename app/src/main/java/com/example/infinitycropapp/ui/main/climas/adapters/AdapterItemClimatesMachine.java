package com.example.infinitycropapp.ui.main.climas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.climas.ActivityClima;
import com.example.infinitycropapp.ui.pojos.ItemClimate;

import java.util.ArrayList;
import java.util.List;

public class AdapterItemClimatesMachine extends RecyclerView.Adapter<AdapterItemClimatesMachine.ItemClimateMachineHolder> {
    private Context context; //contexto
    public boolean showShimmer=true; //mostrar o no loader
    private Fragment fragment;
    private List<ItemClimate> itemClimates;
    //filter data
    private List<ItemClimate> itemClimatesFiltered;

    //constructor
    public AdapterItemClimatesMachine (List<ItemClimate> itemClimates, Context context){
        this.context=context;
        this.itemClimates=itemClimates;
        this.itemClimatesFiltered = itemClimates;

    }

    @NonNull
    @Override
    public ItemClimateMachineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.climate_machine_item,parent,false);

        return new ItemClimateMachineHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemClimateMachineHolder holder, int position) {
        final ItemClimate pojoItem= itemClimatesFiltered.get(position);
        holder.name_climate.setText(pojoItem.getName());
        //onclick methods


    }


    @Override
    public int getItemCount() {
        return itemClimatesFiltered.size();
    }

    public static class ItemClimateMachineHolder extends RecyclerView.ViewHolder{
        protected TextView name_climate;
        protected TextView name_creator;
        protected ImageView img_climate;
        public ItemClimateMachineHolder(@NonNull View itemView) {
            super(itemView);
            //defino aca los findById
            name_climate=itemView.findViewById(R.id.textView19);
            name_creator=itemView.findViewById(R.id.textView43);
            img_climate=itemView.findViewById(R.id.imageView10);
        }
    }

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
}
