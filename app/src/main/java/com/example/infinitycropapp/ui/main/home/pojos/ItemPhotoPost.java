package com.example.infinitycropapp.ui.main.home.pojos;

import android.media.Image;
import android.widget.ImageView;

public class ItemPhotoPost {
    private String id;
    private Image model;

    public ItemPhotoPost(String id, Image model) {
        this.id = id;
        this.model = model;
    }

    public String getName() {
        return id;
    }

    public void setName(String id) {
        this.id = id;
    }

    public Image getModel() {
        return model;
    }

    public void setModel(Image model) {
        this.model = model;
    }
}
