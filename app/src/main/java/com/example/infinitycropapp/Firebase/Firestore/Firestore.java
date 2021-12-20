package com.example.infinitycropapp.Firebase.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.infinitycropapp.Firebase.Auth.User;
import com.example.infinitycropapp.ui.pojos.ItemClimate;
import com.example.infinitycropapp.ui.pojos.ItemMachine;
import com.example.infinitycropapp.ui.pojos.ItemPlaylist;
import com.example.infinitycropapp.ui.pojos.ItemUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

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


    public void AddNewGoogleUser(){
        User user = new User(); //recuperamos los datos del user Auth.
        ItemUser newUser = user.getCurrentUserData();
        if(newUser != null){ //si existe
            //add to collection User un documento nombre : id del user -> sus datos en los campos del firebase
            db.collection("User").document(user.getId()).set(newUser);
        }
    }

    public void AddNewUser(ItemUser newUser, String idUser){
        if(newUser != null && idUser != null){ //si existe
            //add User -> document id user -> fields data
            db.collection("User").document(idUser).set(newUser);
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

    //delete machine
    public void DeleteMachine(String collection, ItemMachine machine, String idDocument){
        if(collection != null && machine != null) { //si todos los datos existen
            //borrar todos los elementos de la colleccion machine -> id -> clima
            db.collection(collection).document(idDocument).collection("Clima")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            db.collection(collection).document(idDocument)
                                    .collection("Clima")
                                    .document(document.getId()).delete();
                        }
                    }
                }
            });
            //borrar la maquina
            db.collection(collection).document(idDocument).delete();
        }
    }

    public void UpdateNameMachine(String collection, ItemMachine machine , String idDocument){
        if(collection != null && machine != null && idDocument != null) { //si todos los datos existen
            db.collection(collection).document(idDocument).update("name",machine.getName());
        }
    }

    public void setFavoriteMachine(String collection, ItemMachine machine, String idDocument, boolean favorite){
        if(collection != null && machine != null) { //si todos los datos existen

            if(favorite){ //si queremos add to favorite
                machine.setFavorite(true);
            }else{ //si queremos quitarlo
                machine.setFavorite(false);
            }
            db.collection(collection).document(idDocument).update("favorite", machine.isFavorite());
        }
    }

    //playlist method
    public void AddPlaylist(String collection, ItemPlaylist playlist, String idMachine){
        if(collection != null && playlist != null && idMachine != null) { //si todos los datos existen
            String id= GetIdUser(); //get the id of the auth user
            if(id != null){
                playlist.setCreatorID(id); //set the id to the itemMachine object
                //collection -> add docuement
                db.collection(collection).add(playlist)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                //cuando creamos la playlist la maquina que queriamos anyadir a una playlist
                                //se anyade a una subcollecion del documento que se acaba de crear
                                String id=documentReference.getId();
                                addMachineToPlaylist(collection ,idMachine , id);

                            }
                        });
            }
        }
    }

    public void addMachineToPlaylist(String collection,String idMachine , String idPlaylist){
        //necesitamos anyadir un campo a un docuemnto
        //ya que no puede estar vacio sin campos
        Map<String, Object> machine = new HashMap<>();
        machine.put("idMachine", idMachine);
        // collection -> document -> collection -> add document
        db.collection(collection).document(idPlaylist)
                .collection("Machines").document(idMachine).set(machine);
    }
    // FIN -> playlist method

    // add climate to cloud firestore
    public void AddClimate(String collection, ItemClimate climate, boolean getCreatorID , boolean isFavorite){
        if(collection != null && climate != null){ //si todos los datos existen

            if(getCreatorID){ //si se necesita obtener el id del usuario logueado
                String id= GetIdUser();
                if(id != null){
                    climate.setCreatorId(id); //set the id to the itemMachine object
                }
            }
            db.collection(collection).add(climate)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    if(isFavorite){ // si lo quiere guardar en favoritos
                        String id = GetIdUser();
                        String idNewClimate = documentReference.getId();
                        //Guardar el id de la maquina en una collection dentro del user
                        Map<String, Object> saved = new HashMap<>();
                        saved.put("Climate", idNewClimate);
                        db.collection("User").document(id)
                                .collection("Saved Climas")
                                .document(idNewClimate).set(saved);
                    }
                }
            });

        }
    }
}
