package com.example.infinitycropapp.ui.main.home.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.Firebase.Firestore.Firestore;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.home.HomeListMachineFragment;
import com.example.infinitycropapp.ui.pojos.ItemMachine;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AdapterItemMachine extends RecyclerView.Adapter<AdapterItemMachine.ItemMachineHolder> {

    //attributes
    private List<ItemMachine> itemMachines; //lista de pojos
    private List<String> itemIDs= new ArrayList<>(); // list of id string
    private Context context; //contexto
    public boolean showShimmer=true; //mostrar o no loader
    private Fragment fragment;

    public AdapterItemMachine(List<ItemMachine> itemMachines, List<String> itemIDs , Context context, Fragment fragment) {
        this.itemMachines = itemMachines;
        this.context = context;
        this.fragment = fragment;
        this.itemIDs = itemIDs;
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
            String idDocument= itemIDs.get(position); //get the id of the current document

            //set the attributes
            holder.name_machine.setText(pojoItem.getName()); //set name
            holder.model_machine.setText(pojoItem.getModel()); //set model

            //onclick methods
            holder.options_machine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Firestore firestore=new Firestore();

                    //set the bottom sheet
                    BottomSheetDialog optionsBottomSheet = new BottomSheetDialog(context);
                    //set the layout of the bottom sheet
                    optionsBottomSheet.setContentView(R.layout.item_machine_bottom_sheet);
                    //findByid
                    ConstraintLayout editMachine= optionsBottomSheet.findViewById(R.id.edit_machineItem);
                    ConstraintLayout deleteMachine= optionsBottomSheet.findViewById(R.id.delete_machineItem);
                    ConstraintLayout addListMachine= optionsBottomSheet.findViewById(R.id.addList_machineItem);
                    ConstraintLayout addFavoriteMachine= optionsBottomSheet.findViewById(R.id.favorite_bs_machineItem);
                    ImageView image_favoriteMachine=optionsBottomSheet.findViewById(R.id.favorite_bs_image);
                    TextView text_favoriteMachine=optionsBottomSheet.findViewById(R.id.favorite_bs_text);
                    //si la maquina esta en favoritos
                    if(pojoItem.isFavorite()){
                        //poner texto quitar de favoritos
                        image_favoriteMachine.setImageResource(R.drawable.icons_quit_favorites);
                        text_favoriteMachine.setText(R.string.quitFavorite_item);
                    }

                    //onclick methods
                    addFavoriteMachine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //si ya esta en favorios
                            if(pojoItem.isFavorite()){
                                //quitarlo de favoritos
                                firestore.setFavoriteMachine("Machine",pojoItem, idDocument, false);
                            }else{ //si no esta en favoritos
                                //ponerlo como favorito
                                firestore.setFavoriteMachine("Machine",pojoItem, idDocument, true);
                            }
                            notifyItemInserted(position);
                            notifyDataSetChanged();
                            //refrescar los rv del fragment
                            ((HomeListMachineFragment) fragment).RefreshRv();
                            optionsBottomSheet.dismiss();
                            //snackbar se ha puesto o no a favoritos
                            String message="";
                            if(pojoItem.isFavorite()){
                                //llamar a la funcion que hace el snackbar
                                message= context.getString(R.string.snack_machine_favorite) ;
                                ((HomeListMachineFragment) fragment).setSnackbar(message);
                            }else{
                                //llamar a la funcion que hace el snackbar
                                message= context.getString(R.string.snack_machine_quit_favorite) ;
                                ((HomeListMachineFragment) fragment).setSnackbar(message);
                            }

                        }
                    });
                    deleteMachine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MaterialAlertDialogBuilder alertDialogBuilder= new MaterialAlertDialogBuilder(context);
                            alertDialogBuilder.setMessage(R.string.deleteText_dialog);
                            //alertDialogBuilder.setMessage("");
                            alertDialogBuilder.setNegativeButton(R.string.cancelButton_dialog, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            alertDialogBuilder.setPositiveButton(R.string.deleteConfirmButton_dialog, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    firestore.DeleteMachine("Machine",pojoItem, idDocument);
                                    optionsBottomSheet.dismiss();
                                    itemMachines.remove(position);
                                    notifyItemRemoved(position);
                                    notifyDataSetChanged();
                                    //snackbar se ha eliminado
                                    String message= context.getString(R.string.snack_machine_delete);
                                    ((HomeListMachineFragment) fragment).setSnackbar(message);
                                }
                            });
                            alertDialogBuilder.show();
                        }
                    });

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
