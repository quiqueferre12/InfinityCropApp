package com.example.infinitycropapp.ui.main.climas.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infinitycropapp.Firebase.Firestore.Firestore;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.climas.ActivityClima;
import com.example.infinitycropapp.ui.pojos.ItemClimate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterItemClimatesMachine extends RecyclerView.Adapter<AdapterItemClimatesMachine.ItemClimateMachineHolder> {
    private Context context; //contexto
    public boolean showShimmer=true; //mostrar o no loader
    private Fragment fragment;
    private List<ItemClimate> itemClimates;
    private List<String> uidClimates;
    //filter data
    private List<ItemClimate> itemClimatesFiltered;
    private List<String> uidClimatesFiltered;
    private String idMachine;

    private Boolean isActive;

    //Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();;

    //constructor
    public AdapterItemClimatesMachine (List<ItemClimate> itemClimates, Context context){
        this.context=context;
        this.itemClimates=itemClimates;
        this.itemClimatesFiltered = itemClimates;

    }

    public AdapterItemClimatesMachine(String idMachine , List<ItemClimate> itemClimates, List<String> uidClimates ,Context context) {
        this.idMachine = idMachine;
        this.context = context;
        this.itemClimates = itemClimates;
        this.uidClimates = uidClimates;
        this.itemClimatesFiltered = itemClimates;
        this.uidClimatesFiltered = uidClimates;
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
        //recojo el id que me toca del item
        String idClimate =  uidClimatesFiltered.get(position);

        holder.name_climate.setText(pojoItem.getName());
        //image
        Glide.with(context)
                .load(pojoItem.getImage())
                .into(holder.img_climate);

        //onclick methods
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityClima.class);
                //AQUI IRA EL ID DEL CLIMA
                intent.putExtra("id", idClimate);
                context.startActivity(intent);
            }
        });

        //saber si tiene que ser on o off
        db.collection("Machine").document(idMachine).collection("Clima")
                .whereEqualTo("Climate" , idClimate) //compruba si el clima esta activo
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){ //si esta activo
                            for(DocumentSnapshot document: queryDocumentSnapshots.getDocuments()){
                                //existe un id clima
                                Log.d("pepe", " // -------------------------------------------------");
                                Log.d("pepe", "EXISTE CLIMA ACTIO:  siiiiiiiii");
                                Log.d("pepe", " // -------------------------------------------------");
                                holder.bt_on_climate.setText("OFF");
                                holder.bt_on_climate.setBackgroundTintList(context.getColorStateList(R.color.item_color));
                            }
                        }else{ // si no esta activo
                            // no hay id clima
                            Log.d("pepe", " // -------------------------------------------------");
                            Log.d("pepe", "EXISTE CLIMA ACTIO:  nonnnnnnnnn");
                            Log.d("pepe", " // -------------------------------------------------");
                            holder.bt_on_climate.setText("ON");
                            holder.bt_on_climate.setBackgroundTintList(context.getColorStateList(R.color.button_color));
                        }
                    }
                });

        //btn on / off
        holder.bt_on_climate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("pepe", " // -------------------------------------------------");
                Log.d("pepe", "activooo: " + isActive);
                Log.d("pepe", " // -------------------------------------------------");
                //comprobar si esta en on o off
                if(holder.bt_on_climate.getText().equals("OFF")){ // si esta activo

                    db.collection("Machine").document(idMachine).collection("Clima")
                            .document("active").delete();

                    notifyDataSetChanged();

                }else if(holder.bt_on_climate.getText().equals("ON")){ // si no esta activo -> activarlo

                    Map<String, Object> saved = new HashMap<>();
                    saved.put("Climate", idClimate);
                    db.collection("Machine").document(idMachine).collection("Clima")
                            .document("active").set(saved);
                    notifyDataSetChanged();

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return itemClimatesFiltered.size();
    }

    public static class ItemClimateMachineHolder extends RecyclerView.ViewHolder{
        protected ConstraintLayout constraintLayout;
        protected TextView name_climate;
        protected TextView name_creator;
        protected ImageView img_climate;
        protected Button bt_on_climate;
        public ItemClimateMachineHolder(@NonNull View itemView) {
            super(itemView);
            //defino aca los findById
            name_climate=itemView.findViewById(R.id.textView19);
            name_creator=itemView.findViewById(R.id.textView43);
            img_climate=itemView.findViewById(R.id.imageView10);
            constraintLayout=itemView.findViewById(R.id.constraintItemMachine);
            bt_on_climate=itemView.findViewById(R.id.bt_on_climate);
        }
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String key= constraint.toString(); //get the char of the search
                if(key.isEmpty()){ //si esta vacio rellenar con all
                    itemClimatesFiltered = itemClimates;
                    uidClimatesFiltered = uidClimates;
                }else{ //sino
                    List<ItemClimate> itemsFiltered = new ArrayList<>();
                    List<String> uidFilteres = new ArrayList<>();
                    int cont =0;
                    for(ItemClimate row: itemClimates){
                        //all minusculas y comprobamos si existe cada secuencia de chars
                        if(row.getName().toLowerCase().contains(key.toLowerCase())){
                            itemsFiltered.add(row); //add los climas que se filtran
                            uidFilteres.add(uidClimates.get(cont));
                        }
                        cont++;
                    }
                    //set final array
                    itemClimatesFiltered = itemsFiltered;
                    uidClimatesFiltered = uidFilteres;
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
