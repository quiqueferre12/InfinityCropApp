package com.example.infinitycropapp.ui.main.home.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;

public class PhotoPostProfileAdapter extends RecyclerView.Adapter<PhotoPostProfileAdapter.ViewHolder>{

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
}
