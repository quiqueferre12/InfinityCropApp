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
    public boolean showShimmer=false; //mostrar o no loader

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
            holder.shimmer.startShimmer();
            holder.namePlaylist.setVisibility(View.INVISIBLE);
            holder.numPlaylist.setVisibility(View.INVISIBLE);
        }else{
            holder.shimmer.stopShimmer();
            holder.shimmer.setShimmer(null); //remove shimmer
            holder.namePlaylist.setVisibility(View.VISIBLE);
            holder.numPlaylist.setVisibility(View.VISIBLE);

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
        int itemLoading=6; //numero de items animacion de cargar
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
        protected ShimmerFrameLayout shimmer;
        public ItemPlaylistAddListHolder(@NonNull View itemView) {
            super(itemView);

            shimmer=itemView.findViewById(R.id.shimmer_playlist_item_add_list);
            itemLayout=itemView.findViewById(R.id.add_list_layout_playlist_item);
            namePlaylist=itemView.findViewById(R.id.add_list_name_playlist_item);
            numPlaylist=itemView.findViewById(R.id.add_list_num_playlist_item);

        }
    }
}
