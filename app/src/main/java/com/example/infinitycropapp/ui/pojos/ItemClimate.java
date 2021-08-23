package com.example.infinitycropapp.ui.pojos;

public class ItemClimate {
    private String id;
    private String creatorId;
    private String maxHumidity;
    private String minHumidity;
    private String image;
    private boolean InfinityCropClimate;
    private String maxLuminosity;
    private String minLuminosity;
    private String Name;
    private String maxtemperature;
    private String minTemperature;
    private int numberShared;

    public ItemClimate(String id, String creatorId, String maxHumidity, String minHumidity, String image, boolean infinityCropClimate, String maxLuminosity, String minLuminosity, String name, String maxtemperature, String minTemperature, int numberShared) {
        this.id=id;
        this.creatorId = creatorId;
        this.maxHumidity = maxHumidity;
        this.minHumidity = minHumidity;
        this.image = image;
        InfinityCropClimate = infinityCropClimate;
        this.maxLuminosity = maxLuminosity;
        this.minLuminosity = minLuminosity;
        Name = name;
        this.maxtemperature = maxtemperature;
        this.minTemperature = minTemperature;
        this.numberShared = numberShared;
    }

    public ItemClimate() {
    }

    public ItemClimate(String name) {
        Name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(String maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public String getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(String minHumidity) {
        this.minHumidity = minHumidity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isInfinityCropClimate() {
        return InfinityCropClimate;
    }

    public void setInfinityCropClimate(boolean infinityCropClimate) {
        InfinityCropClimate = infinityCropClimate;
    }

    public String getMaxLuminosity() {
        return maxLuminosity;
    }

    public void setMaxLuminosity(String maxLuminosity) {
        this.maxLuminosity = maxLuminosity;
    }

    public String getMinLuminosity() {
        return minLuminosity;
    }

    public void setMinLuminosity(String minLuminosity) {
        this.minLuminosity = minLuminosity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
}
