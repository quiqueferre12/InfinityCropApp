package com.example.infinitycropapp.ui.main.profile.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.pojos.ItemPhotoPost;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class PhotoPostProfileAdapter extends RecyclerView.Adapter<PhotoPostProfileAdapter.ViewHolder>{

    private Context context; //contexto
    public boolean showShimmer=true; //mostrar o no loader
    private List<ItemPhotoPost> itemPhotoPosts; //lista de pojos


    public PhotoPostProfileAdapter(List<ItemPhotoPost> itemPhotoPosts, Context context) {
        this.itemPhotoPosts = itemPhotoPosts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView post_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            post_image.findViewById(R.id.post_image_profile);
        }
    }

    public void onBindViewHolder(@NonNull PhotoPostProfileAdapter.ItemMachineHolder holder, int position) {
        if(showShimmer){ //if la animacion tiene que cargar
            holder.shimmerFrameLayout.startShimmer(); //start animation
        }else{

            holder.shimmerFrameLayout.stopShimmer(); //stop animation
            holder.shimmerFrameLayout.setShimmer(null); //remove shimmer
            holder.photo_item.setBackgroundTintList(null); //quitar el tinte (color) gris del loader

            holder.model.setVisibility(View.VISIBLE);


            final ItemPhotoPost pojoItem= itemPhotoPosts.get(position); // get the item pojo

            //set the attributes
            Glide.with(context).load(pojoItem.getModel()).into(holder.model);
        }
    }

    public static class ItemMachineHolder extends RecyclerView.ViewHolder{

        protected ImageView model;
        protected ShimmerFrameLayout shimmerFrameLayout;
        protected MaterialCardView photo_item;
        public ItemMachineHolder(@NonNull View itemView) {
            super(itemView);
            //defino aca los findById
            photo_item.findViewById(R.id.photo_item);
            shimmerFrameLayout.findViewById(R.id.shimmer_photo_item);
            model=itemView.findViewById(R.id.post_image_profile);
        }
    }
}
