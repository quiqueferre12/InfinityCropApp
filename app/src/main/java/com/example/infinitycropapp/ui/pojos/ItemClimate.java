package com.example.infinitycropapp.ui.pojos;

import com.google.firebase.Timestamp;

public class ItemClimate {
    private String creatorId; //id of creator
    //info climate
    private String name;
    private String image;
    private String description;
    private Timestamp date;
    // data climate
    private String maxtemperature;
    private String minTemperature;
    //is our o no
    private boolean infinityCropClimate;
    //num of user that shared the climate
    private int numberShared;



    public ItemClimate() {}

    public ItemClimate(String name) {
        this.name = name;
    }

    //create climate constructor
    public ItemClimate(String name, String image, String description,String maxtemperature, String minTemperature, boolean infinityCropClimate, int numberShared, Timestamp date) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.maxtemperature = maxtemperature;
        this.minTemperature = minTemperature;
        this.infinityCropClimate = infinityCropClimate;
        this.numberShared = numberShared;
        this.date = date;
    }

    //getters & setters

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isInfinityCropClimate() {
        return infinityCropClimate;
    }

    public void setInfinityCropClimate(boolean infinityCropClimate) {
        this.infinityCropClimate = infinityCropClimate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxtemperature() {
        return maxtemperature;
    }

    public void setMaxtemperature(String maxtemperature) {
        this.maxtemperature = maxtemperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getNumberShared() {
        return numberShared;
    }

    public void setNumberShared(int numberShared) {
        this.numberShared = numberShared;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
