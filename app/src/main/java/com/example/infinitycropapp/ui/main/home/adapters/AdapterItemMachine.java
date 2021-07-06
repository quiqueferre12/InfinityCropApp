package com.example.infinitycropapp.ui.main.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.home.pojos.ItemMachine;
import com.example.infinitycropapp.ui.main.home.pojos.ItemPlaylist;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterItemMachine extends RecyclerView.Adapter<AdapterItemMachine.ItemMachineHolder> {

    //attributes
    private List<ItemMachine> itemMachines; //lista de pojos
    private Context context; //contexto
    public boolean showShimmer=true; //mostrar o no loader

    public AdapterItemMachine(List<ItemMachine> itemMachines, Context context) {
        this.itemMachines = itemMachines;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemMachineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_machine,parent,false);

        return new ItemMachineHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMachineHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemMachines.size();
    }

    public static class ItemMachineHolder extends RecyclerView.ViewHolder{

        protected TextView name_machine;
        protected TextView model_machine;
        public ItemMachineHolder(@NonNull View itemView) {
            super(itemView);
            //defino aca los findById
            name_machine=itemView.findViewById(R.id.name_machineItem);
            model_machine=itemView.findViewById(R.id.model_machineItem);
        }
    }
}
