package com.example.infinitycropapp.ui.main.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.pojos.ItemMachine;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        if(showShimmer){ //if la animacion tiene que cargar
            holder.shimmerFrameLayout.startShimmer(); //start animation
            holder.name_machine.setVisibility(View.INVISIBLE);
            holder.model_machine.setVisibility(View.INVISIBLE);
            holder.enter_machine.setVisibility(View.INVISIBLE);
            holder.options_machine.setVisibility(View.INVISIBLE);
        }else{
            holder.shimmerFrameLayout.stopShimmer(); //stop animation
            holder.shimmerFrameLayout.setShimmer(null); //remove shimmer

            holder.name_machine.setVisibility(View.VISIBLE);
            holder.model_machine.setVisibility(View.VISIBLE);
            holder.enter_machine.setVisibility(View.VISIBLE);
            holder.options_machine.setVisibility(View.VISIBLE);
            holder.item_machine.setBackgroundTintList(null); //quitar el tinte (color) gris del loader

            //get the item
            final ItemMachine pojoItem= itemMachines.get(position); // get the item pojo

            //set the attributes
            holder.name_machine.setText(pojoItem.getName()); //set name
            holder.model_machine.setText(pojoItem.getModel()); //set model

            //onclick methods
            holder.options_machine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //set the bottom sheet
                    BottomSheetDialog optionsBottomSheet = new BottomSheetDialog(context);
                    //set the layout of the bottom sheet
                    optionsBottomSheet.setContentView(R.layout.item_machine_bottom_sheet);
                    optionsBottomSheet.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int itemLoading=6; //numero de items animacion de cargar
        if(showShimmer){
            return itemLoading;
        }else{
            return itemMachines.size();
        }
    }

    public static class ItemMachineHolder extends RecyclerView.ViewHolder{

        protected ImageView enter_machine;
        protected TextView name_machine;
        protected TextView model_machine;
        protected FloatingActionButton options_machine;
        protected ShimmerFrameLayout shimmerFrameLayout;
        protected MaterialCardView item_machine;
        public ItemMachineHolder(@NonNull View itemView) {
            super(itemView);
            //defino aca los findById
            name_machine=itemView.findViewById(R.id.name_machineItem);
            model_machine=itemView.findViewById(R.id.model_machineItem);
            shimmerFrameLayout=itemView.findViewById(R.id.shimmer_machine_item);
            item_machine=itemView.findViewById(R.id.machine_item);
            enter_machine=itemView.findViewById(R.id.enter_machineItem);
            options_machine=itemView.findViewById(R.id.options_machineItem);
        }
    }
}
