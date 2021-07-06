package com.example.infinitycropapp.ui.main.home.pojos;

public class ItemMachine {

    private String name;
    private String model;

    public ItemMachine(String name, String model) {
        this.name = name;
        this.model = model;
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
}
