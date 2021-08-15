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
}
