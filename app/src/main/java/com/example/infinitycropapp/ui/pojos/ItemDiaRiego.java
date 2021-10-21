package com.example.infinitycropapp.ui.pojos;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

//clase dia de riego
public class ItemDiaRiego {

    private String nombreDia;
    private Drawable imagenDia;
    private String numRiegos;

    public ItemDiaRiego(String nombreDia, Drawable imagenDia, String numRiegos) {
        this.nombreDia = nombreDia;
        this.imagenDia = imagenDia;
        this.numRiegos = numRiegos;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public Drawable getImagenDia() {
        return imagenDia;
    }

    public void setImagenDia(Drawable imagenDia) {
        this.imagenDia = imagenDia;
    }

    public String getNumRiegos() {
        return numRiegos;
    }

    public void setNumRiegos(String numRiegos) {
        this.numRiegos = numRiegos;
    }
}
