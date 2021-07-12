package com.example.infinitycropapp.Firebase.Auth;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

//POJO para obtener info del user logueado como su UID
public class User {
    //declaro variable
    private FirebaseAuth firebaseAuth;
    //variable donde guardo el nombre del user
    private String name;
    //variable donde guardo el id
    private String id;
    //variable donde guardo el email
    private String email;
    //varible donde guardo el telefono
    private String phone;
    //varible donde guardo el uri de la foto
    private Uri urlPhoto;

    //instanciar el objeto para usar sus getters
    public User() {
        firebaseAuth=FirebaseAuth.getInstance();
    }

    //devuelve el nombre
    public String getName() {
        name= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getDisplayName();
        return name;
    }
    //devuelve el uid del user logueado
    public String getId() {
        id= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        return id;
    }
    //devuelve el mail
    public String getEmail() {
        email= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
        return email;
    }
    //devuelve el telefono
    public String getPhone() {
        phone= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getPhoneNumber();
        return phone;
    }
    //devuelve el uri de la foto
    public Uri getUrlPhoto() {
        urlPhoto= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getPhotoUrl();
        return urlPhoto;
    }

    public void addNewUser(){

    }
}
