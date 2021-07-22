package com.example.infinitycropapp.Firebase.Firestore;

import com.example.infinitycropapp.Firebase.Auth.User;
import com.example.infinitycropapp.ui.pojos.ItemMachine;
import com.example.infinitycropapp.ui.pojos.ItemUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

//clase en la que estan todos los metodos que interacciones con la base de datos
public class Firestore {

    private final FirebaseFirestore db;

    //constructor , siempre llamar antes de hacer las operaciones
    public Firestore() {
        db= FirebaseFirestore.getInstance();
    }

    //get the id of the user
    public String GetIdUser(){
        User user=new User(); //declaramos objeto
        return user.getId();
    }

    public void AddNewUser(){
        User user = new User(); //recuperamos los datos del user Auth.
        ItemUser newUser = user.getCurrentUserData();
        if(newUser != null){ //si existe
            //add to collection User un documento nombre : id del user -> sus datos en los campos del firebase
            db.collection("User").document(user.getId()).set(newUser);
        }
    }

    //add new documento to a collection
    public void AddMachine(String collection, ItemMachine machine, String idDocument, boolean getCreatorID){
        if(collection != null && machine != null){ //si todos los datos existen

            if(getCreatorID){ //si se necesita obtener el id del usuario logueado
                String id= GetIdUser();
                if(id != null){
                    machine.setCreatorID(id); //set the id to the itemMachine object
                }
            }
            db.collection(collection).document(idDocument).set(machine);
        }
    }
}
