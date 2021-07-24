package com.example.infinitycropapp.ui.pojos;

public class ItemMachine {

    private String name;
    private String model;
    private String creatorID;
    private boolean favorite;

    public ItemMachine() { }

    public ItemMachine(String name, String model, boolean favorite) {
        this.name = name;
        this.model = model;
        this.favorite = favorite;
    }

    public ItemMachine(String name, String model, String creatorID) {
        this.name = name;
        this.model = model;
        this.creatorID = creatorID;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
