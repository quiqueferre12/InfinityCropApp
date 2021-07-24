package com.example.infinitycropapp.ui.pojos;

//pojo para item de la playlist
public class ItemPlaylist {
    //attributes
    private String name;
    private String creatorID;

    //contructor
    public ItemPlaylist() { }
    public ItemPlaylist(String name) {
        this.name = name;
    }

    public ItemPlaylist(String name, String creatorID) {
        this.name = name;
        this.creatorID = creatorID;
    }

    //getter
    public String getName() {
        return name;
    }
    //setter
    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }
}
