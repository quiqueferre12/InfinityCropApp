package com.example.infinitycropapp.ui.pojos;

public class ItemClimate {
    private String id; //id of the document
    private String creatorId; //id of creator
    //info climate
    private String name;
    private String image;
    private String description;
    // data climate
    private String maxHumidity;
    private String minHumidity;
    private String maxLuminosity;
    private String minLuminosity;
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

    public ItemClimate(String id, String creatorId, String name, String image, String description, String maxHumidity, String minHumidity, String maxLuminosity, String minLuminosity, String maxtemperature, String minTemperature, boolean infinityCropClimate, int numberShared) {
        this.id = id;
        this.creatorId = creatorId;
        this.name = name;
        this.image = image;
        this.description = description;
        this.maxHumidity = maxHumidity;
        this.minHumidity = minHumidity;
        this.maxLuminosity = maxLuminosity;
        this.minLuminosity = minLuminosity;
        this.maxtemperature = maxtemperature;
        this.minTemperature = minTemperature;
        this.infinityCropClimate = infinityCropClimate;
        this.numberShared = numberShared;
    }

    //getters & setters
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
        return infinityCropClimate;
    }

    public void setInfinityCropClimate(boolean infinityCropClimate) {
        this.infinityCropClimate = infinityCropClimate;
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
}
