package com.example.infinitycropapp.ui.main.profile;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infinitycropapp.Firebase.Auth.User;
import com.example.infinitycropapp.Firebase.Firestore.Firestore;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;
import com.example.infinitycropapp.ui.main.climas.ClimasFragment;
import com.example.infinitycropapp.ui.main.guia.GuiaBotanicaFragment;
import com.example.infinitycropapp.ui.main.home.HomeListMachineFragment;
import com.example.infinitycropapp.ui.main.profile.fotos_profile.FotosProfileFragment;
import com.example.infinitycropapp.ui.pojos.ItemPlaylist;
import com.example.infinitycropapp.ui.pojos.ItemUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Console;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Firebase
    private FirebaseFirestore db;




    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //TextViews
        TextView userNametx=null;

        userNametx = (TextView) view.findViewById(R.id.UserNameTx);



        //Firebase Firestore
        db= FirebaseFirestore.getInstance();

        setUserData(userNametx);//Llamamos a setUserData para rellenar los tx con datos del usuario


        // Inflate the layout for this fragment
        return view;
    }

    private void setUserData(TextView userNametx){
        //Declaramos variables
        Firestore firestore=new Firestore();
        final ItemUser[] itemUser = {new ItemUser()};

        db.collection("User")
                .document(firestore.GetIdUser())
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    itemUser[0] = document.toObject(ItemUser.class);// here

                    //Rellenamos los tx
                    userNametx.setText(itemUser[0].getName());
                    return;
                }

            } else {
                Log.d("FragNotif", "get failed with ", task.getException());
            }
        });


    }

}