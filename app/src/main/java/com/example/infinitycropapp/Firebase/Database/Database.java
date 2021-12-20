package com.example.infinitycropapp.Firebase.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    private FirebaseDatabase databaseReference;

    public Database() {
        databaseReference= FirebaseDatabase.getInstance();
    }

    public void UpdateStateMachine(String child1, String child2, String child3 , int state, String machineId){
        //comprobamos que all esta gucci
        if(child1 != null && child2 != null && child3 != null && machineId != null){
            //update field in child1 -> child2 -> child3 -> int stateMachine
            databaseReference.getReference().child(child1).child(child2).child(child3).setValue(state);
        }
    }

    public void AddFieldsNewMachine(String child1 , String child2){

        ////si all esta gucci
        if(child1 != null && child2 != null){
            //datos generales
            databaseReference.getReference().child(child1).child(child2) //modelo -> id maquina
                    .child("datos generales").child("deposito").setValue(0);
            databaseReference.getReference().child(child1).child(child2) //modelo -> id maquina
                    .child("datos generales").child("humedad").setValue(0);
            databaseReference.getReference().child(child1).child(child2) //modelo -> id maquina
                    .child("datos generales").child("luminosidad").setValue(0);
            databaseReference.getReference().child(child1).child(child2) //modelo -> id maquina
                    .child("datos generales").child("temperatura").setValue(0);
            //parte inferior
            databaseReference.getReference().child(child1).child(child2) //modelo -> id maquina
                    .child("parte inferior").child("humedad").setValue(0);
            databaseReference.getReference().child(child1).child(child2) //modelo -> id maquina
                    .child("parte inferior").child("luminosidad").setValue(0);
            databaseReference.getReference().child(child1).child(child2) //modelo -> id maquina
                    .child("parte inferior").child("temperatura").setValue(0);
            //parte superior
            databaseReference.getReference().child(child1).child(child2) //modelo -> id maquina
                    .child("parte superior").child("humedad").setValue(0);
            databaseReference.getReference().child(child1).child(child2) //modelo -> id maquina
                    .child("parte superior").child("luminosidad").setValue(0);
            databaseReference.getReference().child(child1).child(child2) //modelo -> id maquina
                    .child("parte superior").child("temperatura").setValue(0);
            //state extraction
            databaseReference.getReference().child(child1).child(child2).child("state extraction").setValue(0);
            // state machine
            databaseReference.getReference().child(child1).child(child2).child("state machine").setValue(0);
            // state riego
            databaseReference.getReference().child(child1).child(child2).child("state riego").setValue(0);

        }

    }
}
