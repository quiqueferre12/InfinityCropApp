package com.example.infinitycropapp.ui.pojos;

import android.graphics.Bitmap;
import android.media.Image;

public class ItemPhotoPost {
    private String id;
    private Bitmap model;

    public ItemPhotoPost(String id, Bitmap model) {
        this.id = id;
        this.model = model;
    }

    public String getName() {
        return id;
    }

    public void setName(String id) {
        this.id = id;
    }

    public Bitmap getModel() {
        return model;
    }

    public void setModel(Bitmap model) {
        this.model = model;
    }
}
