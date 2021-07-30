package com.example.infinitycropapp.ui.main.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.pojos.ItemPlaylist;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

public class AdapterItemPlaylistAddList extends RecyclerView.Adapter<AdapterItemPlaylistAddList.ItemPlaylistAddListHolder> {

    //atributes
    private List<ItemPlaylist> itemPlaylists; //lista de pojos
    private Context context; //contexto
    public boolean showShimmer=true; //mostrar o no loader

    public AdapterItemPlaylistAddList(List<ItemPlaylist> itemPlaylists, Context context) {
        this.itemPlaylists = itemPlaylists;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemPlaylistAddListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_playlist_add_list,parent,false);

        return new ItemPlaylistAddListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPlaylistAddListHolder holder, int position) {
        if(showShimmer){ //modo carga
            holder.shimmerText.startShimmer();
            holder.shimmerNumMachinetext.startShimmer();
            holder.namePlaylist.setText("");
            //COMO el num de maquinas no puede estar vacio porque sino no se ve al no estar pegado a algo como el nombre
            //quitamos el shimmer directamente
            holder.shimmerNumMachinetext.setVisibility(View.GONE);
        }else{
            holder.shimmerText.stopShimmer();
            holder.shimmerNumMachinetext.stopShimmer();
            holder.shimmerText.setShimmer(null); //remove shimmer
            holder.shimmerNumMachinetext.setShimmer(null); //remove shimmer
            holder.numPlaylist.setVisibility(View.VISIBLE);
            //quitar fondo gris de carga del item
            holder.namePlaylist.setBackground(null);
            holder.numPlaylist.setBackground(null);

            //get the item
            ItemPlaylist pojoItem= itemPlaylists.get(position);

            if(pojoItem != null){
                if(pojoItem.getName() != null){
                    holder.namePlaylist.setText(pojoItem.getName());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        int itemLoading=4; //numero de items animacion de cargar
        if(showShimmer){
            return itemLoading;
        }else{
            return itemPlaylists.size();
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
