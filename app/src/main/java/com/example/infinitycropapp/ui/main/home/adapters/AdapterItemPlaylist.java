package com.example.infinitycropapp.ui.main.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.pojos.ItemPlaylist;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
//adaptadot para recyclerView playlist (rv_playlist)
public class AdapterItemPlaylist extends RecyclerView.Adapter<AdapterItemPlaylist.ItemPlaylistHolder> {

    //attributes
    private List<ItemPlaylist> itemPlaylists; //lista de pojos
    private Context context; //contexto
    private int cont=0; //variable para comprabar que item esta pulsado
    public boolean showShimmer=true; //mostrar o no loader
    //contructor
    public AdapterItemPlaylist(List<ItemPlaylist> itemPlaylists, Context context) {
        this.itemPlaylists = itemPlaylists;
        this.context = context;
    }

    //metodo que define que xml es del item y que holder tiene que usar con ese xml
    @NonNull
    @Override
    public ItemPlaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.playlist_item,parent,false);

        return new ItemPlaylistHolder(v);
    }

    //metodo para definir el aspecto y los textos que tiene un item del recyclerView
    @Override
    public void onBindViewHolder(@NonNull ItemPlaylistHolder holder, int position) {
        if(showShimmer){ //if la animacion tiene que cargar
            holder.shimmerFrameLayout.startShimmer(); //start animation
        }else{ //si ya no tiene que cargar
            holder.shimmerFrameLayout.stopShimmer(); //stop animation
            holder.shimmerFrameLayout.setShimmer(null); //remove shimmer

            holder.item_playlist.setBackground(null); //quitar el fondo gris del item loader
            holder.item_playlist.setBackgroundTintList(null); //quitar el tinte (color)

            //encuentro el item en la lista y lo declaro en una nueva varible
            final ItemPlaylist pojoItem=itemPlaylists.get(position);
            //declaro el texto del button
            holder.item_playlist.setText(pojoItem.getName());

            //cuando  se pulsa el item se guarda la posicion en una variable
            holder.item_playlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cont=position;
                    notifyDataSetChanged();
                }
            });
            //este metodo recorre todos los items , entonces cuando encuentre lo que he pulsado se cambiara su color
            if(cont==position){
                holder.item_playlist.setBackgroundResource(R.drawable.button_primary);
            }else{
                holder.item_playlist.setBackgroundResource(R.drawable.button_secondary);
            }

        }
    }

    //metodo que devuelve el size
    @Override
    public int getItemCount() {
        int itemLoading=5; //numero de items animacion de cargar
        if(showShimmer){
            return itemLoading;
        }else{
            return itemPlaylists.size();
        }

    }

    //clase holder para instanciar los elementos del playlist_list.xml layout
    public static class ItemPlaylistHolder extends RecyclerView.ViewHolder{

        //atributes of playlist_item.xml
        protected Button item_playlist;
        protected ShimmerFrameLayout shimmerFrameLayout;
        //contructor
        private ItemPlaylistHolder(@NonNull View itemView) {
            super(itemView);
            //defino aca los findById
            item_playlist=itemView.findViewById(R.id.button_item_playlist);
            shimmerFrameLayout=itemView.findViewById(R.id.shimmer_playlist_item);
        }
    }
}
