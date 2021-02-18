package com.example.infinitycropapp.ui.tutorial.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo

    };

    public Integer[] slide_title = {
            R.string.prueba_tutorial,
            R.string.prueba_tutorial2,
            R.string.prueba_tutorial,
            R.string.prueba_tutorial,
            R.string.prueba_tutorial2
    };

    public Integer[] slide_parrafo = {
            R.string.prueba_tutorial,
            R.string.prueba_tutorial2,
            R.string.prueba_tutorial,
            R.string.prueba_tutorial,
            R.string.prueba_tutorial2
    };

    public Integer[] slide_parrafo2 = {
            R.string.prueba_tutorial,
            R.string.prueba_tutorial2,
            R.string.prueba_tutorial,
            R.string.prueba_tutorial,
            R.string.prueba_tutorial2
    };

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.imageView);
        TextView slideTitle = view.findViewById(R.id.tvTitulo);
        TextView slideParrafo = view.findViewById(R.id.tvParrafo);
        TextView slideParrafo2 = view.findViewById(R.id.tvParrafo2);

        slideImageView.setImageResource(slide_images[position]);
        slideTitle.setText(context.getText(slide_title[position]));
        slideParrafo.setText(context.getText(slide_parrafo[position]));
        slideParrafo2.setText(context.getText(slide_parrafo2[position]));

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
