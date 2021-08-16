package com.example.infinitycropapp.ui.main.tutorial.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.infinitycropapp.R;

/**
 * Created by albertopalomarrobledo on 9/11/18.
 */

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;


    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.infinity_logo_black,
            R.drawable.tutorial_qr,
            R.drawable.tutorial_control,
            R.drawable.tutorial_clima,
            R.drawable.ourteam

    };

    public Integer[] slide_title = {
            R.string.tutorial_titulo1,
            R.string.tutorial_titulo2,
            R.string.tutorial_titulo3,
            R.string.tutorial_titulo4,
            R.string.tutorial_titulo5
    };

    public Integer[] slide_parrafo = {
            R.string.tutorial_parrafo1,
            R.string.tutorial_parrafo2,
            R.string.tutorial_parrafo3,
            R.string.tutorial_parrafo4,
            R.string.tutorial_parrafo5
    };

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.imageView);
        TextView slideTitle = view.findViewById(R.id.tvTitulo);
        TextView slideParrafo = view.findViewById(R.id.tvParrafo);

        slideImageView.setImageResource(slide_images[position]);
        slideTitle.setText(context.getText(slide_title[position]));
        slideParrafo.setText(context.getText(slide_parrafo[position]));

        //Declaramos animación hacia arriba
        Animation bottomAnim = AnimationUtils.loadAnimation(context, R.anim.bottom_animation);

        //Declaramos animación hacia abajo
        Animation topAnim = AnimationUtils.loadAnimation(context, R.anim.top_animation);

        //Adjuntamos animación
        slideImageView.setAnimation(bottomAnim);
        slideTitle.setAnimation(topAnim);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (ConstraintLayout) object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
