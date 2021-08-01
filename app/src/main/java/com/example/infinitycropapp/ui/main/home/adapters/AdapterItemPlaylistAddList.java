package com.example.infinitycropapp.ui.main.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.Firebase.Firestore.Firestore;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.pojos.ItemMachine;
import com.example.infinitycropapp.ui.pojos.ItemPlaylist;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdapterItemPlaylistAddList extends RecyclerView.Adapter<AdapterItemPlaylistAddList.ItemPlaylistAddListHolder> implements View.OnClickListener {

    //atributes
    private List<ItemPlaylist> itemPlaylists; //lista de pojos
    private List<String> playlistAddListString;
    private Context context; //contexto
    public boolean showShimmer=true; //mostrar o no loader
    private FirebaseFirestore db2= FirebaseFirestore.getInstance();//firebase
    private Firestore firestore= new Firestore();
    private String idUser= firestore.GetIdUser(); //get the user id
    private View.OnClickListener listener;
    int numMachines;

    public AdapterItemPlaylistAddList(List<ItemPlaylist> itemPlaylists, List<String> playlistAddListString, Context context) {
        this.itemPlaylists = itemPlaylists;
        this.playlistAddListString = playlistAddListString;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemPlaylistAddListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_playlist_add_list,parent,false);
        v.setOnClickListener(this);
        return new ItemPlaylistAddListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPlaylistAddListHolder holder, int position) {
        if(showShimmer){ //modo carga
            holder.shimmerText.startShimmer();
            holder.shimmerNumMachinetext.startShimmer();
            holder.namePlaylist.setText("");
            holder.numPlaylist.setText("");
            //COMO el num de maquinas no puede estar vacio porque sino no se ve al no estar pegado a algo como el nombre
            //quitamos el shimmer directamente
            holder.shimmerNumMachinetext.setVisibility(View.GONE);
        }else{
            holder.shimmerText.stopShimmer();
            holder.shimmerNumMachinetext.stopShimmer();
            holder.shimmerText.setShimmer(null); //remove shimmer
            holder.shimmerNumMachinetext.setShimmer(null); //remove shimmer
            holder.shimmerNumMachinetext.setVisibility(View.VISIBLE);
            //quitar fondo gris de carga del item
            holder.namePlaylist.setBackground(null);
            holder.numPlaylist.setBackground(null);

            //get the item
            ItemPlaylist pojoItem= itemPlaylists.get(position);
            String idPlaylist="";
            //si no es la lista de favoritos
            if(!pojoItem.getName().equals(context.getString(R.string.step3_favorite))){
                //es -1 porque la lista de favoritos no tiene ID.
                idPlaylist = playlistAddListString.get(position-1);
            }
            numMachines=0;

            if(pojoItem != null){
                if(pojoItem.getName() != null){
                    holder.namePlaylist.setText(pojoItem.getName());

                    //si es la playlist de favoritos
                    if(holder.namePlaylist.getText().equals(context.getString(R.string.step3_favorite))){
                        db2.collection("Machine")
                                .whereEqualTo("creatorID", idUser)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            numMachines=0; //sino porgo esto aca el cont se duplica XD
                                            //for que recorre cada docuento que ha sacado
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                //set el docuemnto como objeto
                                                ItemMachine itemMachine=document.toObject(ItemMachine.class);
                                                if(itemMachine.isFavorite()){ //si esta en favoritos
                                                    numMachines++; // +1
                                                }
                                            }
                                            if(numMachines == 0){ //si no hay maquinas en la lista
                                                //string esta vacio
                                                holder.numPlaylist.setText(context.getString(R.string.add_list_dialog_empty_playlist));
                                            }else{
                                                String res="";
                                                if(numMachines == 1){ //si solo hay 1 maquina
                                                    res= numMachines+" "+context.getString(R.string.add_list_dialog_one_playlist);
                                                }else{ //si hay mas de 1
                                                    res= numMachines+" "+context.getString(R.string.add_list_dialog_more_playlist);
                                                }
                                                //set string
                                                holder.numPlaylist.setText(res);
                                            }
                                        } else {

                                        }
                                    }
                                });
                    }else{ //sino es la playlist por default de favoritos

                        //buscar en dentro de esa playlist (no favoritos)
                        if(!idPlaylist.equals("")){
                            db2.collection("Playlist machine").document(idPlaylist)
                                    .collection("Machines")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                numMachines=0;//sino porgo esto aca el cont se duplica XD
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    numMachines++; // +1
                                                }
                                                if(numMachines == 0){ //si no hay maquinas en la lista
                                                    //string esta vacio
                                                    holder.numPlaylist.setText(context.getString(R.string.add_list_dialog_empty_playlist));
                                                }else{
                                                    String res="";
                                                    if(numMachines == 1){ //si solo hay 1 maquina
                                                        res= numMachines+" "+context.getString(R.string.add_list_dialog_one_playlist);
                                                    }else{ //si hay mas de 1
                                                        res= numMachines+" "+context.getString(R.string.add_list_dialog_more_playlist);
                                                    }
                                                    //set string
                                                    holder.numPlaylist.setText(res);
                                                }
                                            }
                                        }
                                    });
                        }

                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        int itemLoading=3; //numero de items animacion de cargar
        if(showShimmer){
            return itemLoading;
        }else{
            return itemPlaylists.size();
        }
    }
    //onclick
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }


    public static class ItemPlaylistAddListHolder extends RecyclerView.ViewHolder{

        protected ConstraintLayout itemLayout;
        protected TextView namePlaylist;
        protected TextView numPlaylist;
        protected ShimmerFrameLayout shimmerText;
        protected ShimmerFrameLayout shimmerNumMachinetext;
        public ItemPlaylistAddListHolder(@NonNull View itemView) {
            super(itemView);

            shimmerText=itemView.findViewById(R.id.shimmer_text_add_list_item);
            shimmerNumMachinetext=itemView.findViewById(R.id.shimmer_text2_add_list_item);
            itemLayout=itemView.findViewById(R.id.add_list_layout_playlist_item);
            namePlaylist=itemView.findViewById(R.id.add_list_name_playlist_item);
            numPlaylist=itemView.findViewById(R.id.add_list_num_playlist_item);

        }
    }
}
