package com.example.infinitycropapp.ui.pojos;

public class ItemClimate {
    private String creatorId;
    private String humidity;
    private String image;
    private boolean InfinityCropClimate;
    private String luminosity;
    private String Name;
    private String temperature;
    private int numberShared;

    public ItemClimate(String creatorId, String humidity, String image, boolean infinityCropClimate, String luminosity, String name, String temperature, int numberShared) {
        this.creatorId = creatorId;
        this.humidity = humidity;
        this.image = image;
        InfinityCropClimate = infinityCropClimate;
        this.luminosity = luminosity;
        Name = name;
        this.temperature = temperature;
        this.numberShared = numberShared;
    }

    public ItemClimate(){

    }


    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(String luminosity) {
        this.luminosity = luminosity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isInfinityCropClimate() {
        return InfinityCropClimate;
    }
}
