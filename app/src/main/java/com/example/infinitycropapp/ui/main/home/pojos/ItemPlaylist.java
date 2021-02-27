package com.example.infinitycropapp.ui.main.home.pojos;

//pojo para item de la playlist
public class ItemPlaylist {
    //attributes
    //solo necesito el nombre para filtrar en firebase los elementos que pertenecen a ese nombre
    private String name;

    //contructor
    public ItemPlaylist(String name) {
        this.name = name;
    }
    //getter
    public String getName() {
        return name;
    }
    //setter
    public void setName(String name) {
        this.name = name;
    }
}
