package com.example.infinitycropapp.ui.main.profile.fotos_profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.home.adapters.PhotoPostProfileAdapter;
import com.example.infinitycropapp.ui.main.home.pojos.ItemPhotoPost;

import java.util.ArrayList;
import java.util.List;

public class FotosProfileFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public RecyclerView recyclerView;
    List<ItemPhotoPost> itemPhotoPosts=new ArrayList<>();
    PhotoPostProfileAdapter photoPostProfileAdapter = new PhotoPostProfileAdapter(itemPhotoPosts, getContext());

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public FotosProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClimasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FotosProfileFragment newInstance(String param1, String param2) {
        FotosProfileFragment fragment = new FotosProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prof_fotos, container, false);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.icons_user);
        itemPhotoPosts.add(new ItemPhotoPost("1", bm));
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView = view.findViewById(R.id.recyclerPhotoProfile);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(photoPostProfileAdapter);
        // Inflate the layout for this fragment
        return view;

    }
}
