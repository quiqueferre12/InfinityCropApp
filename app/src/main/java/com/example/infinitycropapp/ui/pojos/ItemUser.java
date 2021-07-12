package com.example.infinitycropapp.ui.pojos;

//clase para almacenar los documentos de la colleccion User
public class ItemUser {

    private String id;
    private String name;
    private String lastname;
    private String mail;
    private String photo;
    private int communities;
    private int posts;
    private int likes;
    private int machine;
    private int sharedPost;
    private int playlists;
    private Boolean seeTutorial;

    public ItemUser() { } //CONSTRUCTOR VACIO OBLIGATORIO PONER

    //contructor para nuevos usarios o los que no sabemos el ID del documento.
    public ItemUser(String name, String lastname, String mail, String photo, int communities, int posts, int likes, int machine, int sharedPost, int playlists, Boolean seeTutorial) {
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.photo = photo;
        this.communities = communities;
        this.posts = posts;
        this.likes = likes;
        this.machine = machine;
        this.sharedPost = sharedPost;
        this.playlists = playlists;
        this.seeTutorial = seeTutorial;
    }

    //contructor PARA almacenar los documentos del firebase o para users que conocemos el ID.
    public ItemUser(String id, String name, String lastname, String mail, String photo, int communities, int posts, int likes, int machine, int sharedPost, int playlists, Boolean seeTutorial) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.photo = photo;
        this.communities = communities;
        this.posts = posts;
        this.likes = likes;
        this.machine = machine;
        this.sharedPost = sharedPost;
        this.playlists = playlists;
        this.seeTutorial = seeTutorial;
    }

    //GETTERS AND SETTERS -> SIN EL ID PORQUE NO SE PUEDE MODIFICAR , solo se puede conseguien en la clase Auth/User

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getCommunities() {
        return communities;
    }

    public void setCommunities(int communities) {
        this.communities = communities;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getMachine() {
        return machine;
    }

    public void setMachine(int machine) {
        this.machine = machine;
    }

    public int getSharedPost() {
        return sharedPost;
    }

    public void setSharedPost(int sharedPost) {
        this.sharedPost = sharedPost;
    }

    public int getPlaylists() {
        return playlists;
    }

    public void setPlaylists(int playlists) {
        this.playlists = playlists;
    }

    public Boolean getSeeTutorial() {
        return seeTutorial;
    }

    public void setSeeTutorial(Boolean seeTutorial) {
        this.seeTutorial = seeTutorial;
    }
}
