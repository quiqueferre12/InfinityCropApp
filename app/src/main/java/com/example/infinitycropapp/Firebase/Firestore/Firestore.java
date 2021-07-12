package com.example.infinitycropapp.Firebase.Firestore;

import com.example.infinitycropapp.Firebase.Auth.User;
import com.example.infinitycropapp.ui.pojos.ItemUser;
import com.google.firebase.firestore.FirebaseFirestore;

//clase en la que estan todos los metodos que interacciones con la base de datos
public class Firestore {

    private FirebaseFirestore db;

    //constructor , siempre llamar antes de hacer las operaciones
    public Firestore() {
        db= FirebaseFirestore.getInstance();
    }

    public void AddNewUser(){
        User user = new User(); //recuperamos los datos del user Auth.
        ItemUser newUser = user.getCurrentUserData();
        if(newUser != null){ //si existe
            //add to collection User un documento nombre : id del user -> sus datos en los campos del firebase
            db.collection("User").document(user.getId()).set(newUser);
        }

    }

}
