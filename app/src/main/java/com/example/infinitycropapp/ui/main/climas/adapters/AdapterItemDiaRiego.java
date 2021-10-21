package com.example.infinitycropapp.ui.main.climas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.pojos.ItemDiaRiego;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class AdapterItemDiaRiego extends RecyclerView.Adapter<AdapterItemDiaRiego.ItemRiegoDiaHolder> {

    private List<ItemDiaRiego> itemDiaRiegoList= new ArrayList<>();
    private Context context;

    public AdapterItemDiaRiego(List<ItemDiaRiego> itemDiaRiegoList, Context context) {
        this.itemDiaRiegoList = itemDiaRiegoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemRiegoDiaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.dia_riego_item,parent,false);
        return new ItemRiegoDiaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRiegoDiaHolder holder, int position) {
        ItemDiaRiego itemDiaRiego= itemDiaRiegoList.get(position);

        //set data
        holder.img_dia.setImageDrawable(itemDiaRiego.getImagenDia());
        holder.name_dia.setText(itemDiaRiego.getNombreDia());
        holder.num_riegos_dia.setText(itemDiaRiego.getNumRiegos());

        //onclick
        holder.item_dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemDiaRiegoList.size();
    }

    public static class ItemRiegoDiaHolder extends RecyclerView.ViewHolder{

        protected ImageView img_dia;
        protected TextView name_dia;
        protected TextView num_riegos_dia;
        protected MaterialCardView item_dia;

        public ItemRiegoDiaHolder(@NonNull View itemView) {
            super(itemView);

            img_dia=itemView.findViewById(R.id.dia_riego_photo);
            name_dia=itemView.findViewById(R.id.dia_riego_txt);
            num_riegos_dia=itemView.findViewById(R.id.dia_riego_num);
            item_dia=itemView.findViewById(R.id.dia_riego_card);
        }
    }
}
