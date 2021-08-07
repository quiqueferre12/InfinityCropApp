package com.example.infinitycropapp.ui.main.home.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinitycropapp.Firebase.Firestore.Firestore;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.home.HomeListMachineFragment;
import com.example.infinitycropapp.ui.pojos.ItemMachine;
import com.example.infinitycropapp.ui.pojos.ItemPlaylist;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdapterItemMachine extends RecyclerView.Adapter<AdapterItemMachine.ItemMachineHolder> {

    //attributes
    private List<ItemMachine> itemMachines; //lista de pojos
    private List<String> itemIDs; // list of id string
    private Context context; //contexto
    public boolean showShimmer=true; //mostrar o no loader
    private Fragment fragment;
    //add list rv playlist
    private List<ItemPlaylist> itemPlaylistsAddList=new ArrayList<>(); //lista de pojos
    private List<String> playlistAddListString= new ArrayList<>();
    private AdapterItemPlaylistAddList adapterItemPlaylistAddList;
    private FirebaseFirestore db2= FirebaseFirestore.getInstance();//firebase
    private Firestore firestore=new Firestore();
    private String idUser= firestore.GetIdUser(); //get the user id


    public AdapterItemMachine(List<ItemMachine> itemMachines, List<String> itemIDs , Context context, Fragment fragment) {
        this.itemMachines = itemMachines;
        this.context = context;
        this.fragment = fragment;
        this.itemIDs = itemIDs;
    }

    @NonNull
    @Override
    public ItemMachineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_machine,parent,false);

        return new ItemMachineHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemMachineHolder holder, int position) {
        if(showShimmer){ //if la animacion tiene que cargar
            holder.shimmerFrameLayout.startShimmer(); //start animation
            holder.name_machine.setVisibility(View.INVISIBLE);
            holder.model_machine.setVisibility(View.INVISIBLE);
            holder.enter_machine.setVisibility(View.INVISIBLE);
            holder.options_machine.setVisibility(View.INVISIBLE);
        }else{
            holder.shimmerFrameLayout.stopShimmer(); //stop animation
            holder.shimmerFrameLayout.setShimmer(null); //remove shimmer

            holder.name_machine.setVisibility(View.VISIBLE);
            holder.model_machine.setVisibility(View.VISIBLE);
            holder.enter_machine.setVisibility(View.VISIBLE);
            holder.options_machine.setVisibility(View.VISIBLE);
            holder.item_machine.setBackgroundTintList(null); //quitar el tinte (color) gris del loader

            //get the item
            ItemMachine pojoItem= itemMachines.get(position); // get the item pojo
            String idDocument= itemIDs.get(position); //get the id of the current document

            //set the attributes
            holder.name_machine.setText(pojoItem.getName()); //set name
            holder.model_machine.setText(pojoItem.getModel()); //set model

            //onclick methods
            holder.options_machine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //set the bottom sheet
                    BottomSheetDialog optionsBottomSheet = new BottomSheetDialog(context);
                    //set the layout of the bottom sheet
                    optionsBottomSheet.setContentView(R.layout.item_machine_bottom_sheet);
                    //findByid
                    ConstraintLayout editMachine= optionsBottomSheet.findViewById(R.id.edit_machineItem);
                    ConstraintLayout deleteMachine= optionsBottomSheet.findViewById(R.id.delete_machineItem);
                    //ConstraintLayout addListMachine= optionsBottomSheet.findViewById(R.id.addList_machineItem);
                    ConstraintLayout addFavoriteMachine= optionsBottomSheet.findViewById(R.id.favorite_bs_machineItem);
                    ImageView image_favoriteMachine=optionsBottomSheet.findViewById(R.id.favorite_bs_image);
                    TextView text_favoriteMachine=optionsBottomSheet.findViewById(R.id.favorite_bs_text);
                    //si la maquina esta en favoritos
                    if(pojoItem.isFavorite()){
                        //poner texto quitar de favoritos
                        image_favoriteMachine.setImageResource(R.drawable.icons_quit_favorites);
                        text_favoriteMachine.setText(R.string.quitFavorite_item);
                    }

                    //onclick methods
                    addFavoriteMachine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //si ya esta en favorios
                            if(pojoItem.isFavorite()){
                                //quitarlo de favoritos
                                firestore.setFavoriteMachine("Machine",pojoItem, idDocument, false);
                            }else{ //si no esta en favoritos
                                //ponerlo como favorito
                                firestore.setFavoriteMachine("Machine",pojoItem, idDocument, true);
                            }
                            notifyItemChanged(position);
                            notifyDataSetChanged();
                            //refrescar los rv del fragment
                            ((HomeListMachineFragment) fragment).RefreshRv();
                            optionsBottomSheet.dismiss();
                            //snackbar se ha puesto o no a favoritos
                            String message="";
                            if(pojoItem.isFavorite()){
                                //llamar a la funcion que hace el snackbar
                                message= context.getString(R.string.snack_machine_favorite) ;
                                ((HomeListMachineFragment) fragment).setSnackbar(message);
                            }else{
                                //llamar a la funcion que hace el snackbar
                                message= context.getString(R.string.snack_machine_quit_favorite) ;
                                ((HomeListMachineFragment) fragment).setSnackbar(message);
                            }

                        }
                    });
                    deleteMachine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MaterialAlertDialogBuilder alertDialogBuilder= new MaterialAlertDialogBuilder(context);
                            alertDialogBuilder.setMessage(R.string.deleteText_dialog);
                            //alertDialogBuilder.setMessage("");
                            alertDialogBuilder.setNegativeButton(R.string.cancelButton_dialog, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            alertDialogBuilder.setPositiveButton(R.string.deleteConfirmButton_dialog, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    firestore.DeleteMachine("Machine",pojoItem, idDocument);
                                    optionsBottomSheet.dismiss();
                                    //actualizamos el rv
                                    itemMachines.remove(position);
                                    notifyItemRemoved(position);
                                    notifyDataSetChanged();
                                    //snackbar se ha eliminado
                                    String message= context.getString(R.string.snack_machine_delete);
                                    ((HomeListMachineFragment) fragment).setSnackbar(message);
                                }
                            });
                            alertDialogBuilder.show();
                        }
                    });
                    //playlist add / create
                    //add list machine bottom sheet button
                    /*
                    addListMachine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //cerrar el bottom sheet al pulsar add list
                            optionsBottomSheet.dismiss();

                            Dialog dialog= new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(true); //al pulsar fuera del dialog se quita
                            dialog.setContentView(R.layout.add_list_dialog);
                            //set the correct width
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            //findById
                            ImageView back=dialog.findViewById(R.id.back_add_list_dialog);
                            Button btn_create= dialog.findViewById(R.id.create_add_list_dialog);
                            RecyclerView rv_playlist=dialog.findViewById(R.id.rv_playlist_item_add_list);
                            //methods

                            //defino que el rv no tenga fixed size
                            rv_playlist.setHasFixedSize(true);
                            //manejador para declarar la direccion de los items del rv
                            rv_playlist.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            adapterItemPlaylistAddList=new AdapterItemPlaylistAddList(itemPlaylistsAddList,playlistAddListString,context);
                            //declaro que cual es el adaptador el rv
                            rv_playlist.setAdapter(adapterItemPlaylistAddList);
                            //get data for rv
                            itemPlaylistsAddList.clear();
                            playlistAddListString.clear();
                            itemPlaylistsAddList.add(new ItemPlaylist(context.getResources().getString(R.string.step3_favorite)));
                            //get the playlist of the user
                            getPlaylistUser();
                            //FIN ->methods

                            //onlclick methods
                            //onclick item rv playlist add list
                            adapterItemPlaylistAddList.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int position=rv_playlist.getChildLayoutPosition(v);
                                    ItemPlaylist playlistClick=itemPlaylistsAddList.get(position);
                                    final String[] message = {""};
                                    //si es la playlist de favoritos
                                    if(playlistClick.getName().equals(context.getString(R.string.step3_favorite))){
                                        //si ya esta en favoritos
                                        if(pojoItem.isFavorite()){
                                            //texto para snackbar de que ya esta en favoritos
                                            message[0] = context.getString(R.string.snack_playlist_already_favorite) ;
                                        }else{ //add to favorites
                                            message[0] = context.getString(R.string.snack_machine_favorite) ;
                                            //ponerlo como favorito en firestore
                                            firestore.setFavoriteMachine("Machine",pojoItem, idDocument, true);
                                        }
                                        //cerrar dialog
                                        dialog.dismiss();
                                        //llamar a la funcion que hace el snackbar
                                        ((HomeListMachineFragment) fragment).setSnackbar(message[0]);
                                    }else{
                                        //get the id of the clicked docuemnt (es -1 por la playlist por defecto favorites)
                                        String idPlaylistClick=playlistAddListString.get(position-1);

                                        //comprobar si esa maquina ya esta en la palylist o no
                                        db2.collection("Playlist machine").document(idPlaylistClick)
                                                .collection("Machines").document(idDocument)
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) { //si es doc existe
                                                        String listMessage= " "+"'"+playlistClick.getName()+"'."; //mensaje con el nombre de la lista
                                                        message[0] = context.getString(R.string.snack_playlist_document_playlist_exists)+listMessage;
                                                    } else { //sino existe
                                                        //set message for snackbar
                                                        String listMessage= " "+"'"+playlistClick.getName()+"'."; //mensaje con el nombre de la lista
                                                        message[0] = context.getString(R.string.snack_playlist_add)+listMessage;
                                                        //add the machine into the collection playlist -> doc -> collection -> add
                                                        firestore.addMachineToPlaylist("Playlist machine", idDocument , idPlaylistClick);
                                                    }
                                                    //cerrar dialog
                                                    dialog.dismiss();
                                                    //llamar a la funcion que hace el snackbar
                                                    ((HomeListMachineFragment) fragment).setSnackbar(message[0]);
                                                } else { }
                                            }
                                        });
                                    }
                                }
                            });
                            //onclick close dialog
                            back.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //cerrar el anterior dialog
                                    dialog.dismiss();
                                }
                            });
                            //btn nueva lista
                            btn_create.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //cerrar el anterior dialog
                                    dialog.dismiss();

                                    Dialog createList_dialog= new Dialog(context);
                                    createList_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    createList_dialog.setCancelable(true); //al pulsar fuera del dialog se quita
                                    createList_dialog.setContentView(R.layout.create_list_dialog);
                                    //set the correct width
                                    createList_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    //findById
                                    ImageView back= createList_dialog.findViewById(R.id.create_list_btn_back);
                                    ImageView check= createList_dialog.findViewById(R.id.create_list_btn_check);
                                    TextInputEditText nameList= createList_dialog.findViewById(R.id.create_list_name_input_dialog);
                                    TextInputLayout layout_nameList= createList_dialog.findViewById(R.id.create_list_layout_input_dialog);
                                    //methods
                                    // FIN -> methods
                                    //onclick methods
                                    back.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            createList_dialog.dismiss();
                                            dialog.show();
                                        }
                                    });

                                    check.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String textInput=nameList.getText().toString(); //get the texto of the input
                                            //if is empty
                                            if(textInput.equals("")){
                                                layout_nameList.setErrorEnabled(true); // activate error
                                                //text error
                                                layout_nameList.setError(context.getString(R.string.error_a_lot_text_editext_list));
                                            }else if(layout_nameList.getCounterMaxLength() < textInput.length()) { //error length max
                                                layout_nameList.setErrorEnabled(true); // activate error
                                                //text error
                                                layout_nameList.setError(context.getString(R.string.error_a_lot_text_editext_list));
                                            }else{ //comprobar si el nombre existe
                                                if(idUser != null){
                                                    db2.collection("Playlist machine") //busco en la collection
                                                            .whereEqualTo("creatorID", idUser ) //saco todas las listas del user
                                                            .get()
                                                            .addOnCompleteListener(task -> {
                                                                if(task.isSuccessful()){ //si all gucci
                                                                    QuerySnapshot document = task.getResult(); //get result
                                                                    int cont=0;
                                                                    for (QueryDocumentSnapshot doc : document) {
                                                                        if(doc.getString("name").equals(textInput)){ //si ya existe ese nombre
                                                                            cont++; // ++
                                                                            break; //como solo puede existir 1 nombre por user , salimos del for
                                                                        }
                                                                    }

                                                                    if (cont >= 1) { //ese campo ya existe
                                                                        layout_nameList.setErrorEnabled(true); // activate error
                                                                        //text error
                                                                        layout_nameList.setError(context.getString(R.string.error_name_exists_list));
                                                                    } else { //no existe
                                                                        // all gucci
                                                                        layout_nameList.setErrorEnabled(false);
                                                                        //convert data to pojo
                                                                        ItemPlaylist playlist= new ItemPlaylist(textInput);
                                                                        // call firestore to create new list
                                                                        firestore.AddPlaylist("Playlist machine", playlist, idDocument);
                                                                        //exit dialog
                                                                        createList_dialog.dismiss();
                                                                        //set snackbar action
                                                                        String listMessage= " "+"'"+textInput+"'."; //mensaje con el nombre de la lista
                                                                        String snackBarMessage= context.getString(R.string.snack_playlist_add)+listMessage;
                                                                        ((HomeListMachineFragment) fragment).setSnackbar(snackBarMessage);
                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        }
                                    });
                                    // FIN -> onclick methods

                                    createList_dialog.show();
                                }
                            });

                            // FIN -> onlclick methods
                            dialog.show();
                        }
                    });
                     */
                    //edit machine bottom sheet button -> abre un dialog
                    editMachine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //cerrar el bottom sheet al pulsar add list
                            optionsBottomSheet.dismiss();

                            Dialog dialogEditName= new Dialog(context);
                            dialogEditName.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialogEditName.setCancelable(true); //al pulsar fuera del dialog se quita
                            dialogEditName.setContentView(R.layout.edit_machine_dialog);
                            //set the correct width
                            dialogEditName.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            //findById
                            ImageView back=dialogEditName.findViewById(R.id.edit_name_machine_btn_back);
                            ImageView check=dialogEditName.findViewById(R.id.edit_name_machine_btn_check);
                            TextInputLayout layoutInput=dialogEditName.findViewById(R.id.edit_name_machine_layout_input);
                            TextInputEditText input=dialogEditName.findViewById(R.id.edit_name_machine_input);

                            //methods & Attributes
                            input.setText(pojoItem.getName());

                            // FIN -> methods & Attributes

                            //onclick
                            back.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogEditName.dismiss();
                                    optionsBottomSheet.show();
                                }
                            });
                            check.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String textInput = input.getText().toString(); //get the texto of the input
                                    if (textInput.equals("")) { //si no hay nombre en el editext
                                        layoutInput.setErrorEnabled(true); //activar error
                                        layoutInput.setError(context.getString(R.string.error_empty_editext)); //set texto error
                                    } else //si la longitud del nombre es mayor de la maxima permitida
                                        if (layoutInput.getCounterMaxLength() < textInput.length()) {
                                            layoutInput.setErrorEnabled(true);
                                            layoutInput.setError(context.getString(R.string.error_a_lot_text_editext));
                                        } else { //si all gucci
                                            layoutInput.setErrorEnabled(false); //quitar lo rojo del error
                                            //call to method in firestore class to update the name of the machine
                                            pojoItem.setName(textInput);
                                            firestore.UpdateNameMachine("Machine" , pojoItem , idDocument);
                                            //close dialog
                                            dialogEditName.dismiss();
                                            // notify rv are changed
                                            notifyDataSetChanged();
                                            //set snackbar
                                            String message= context.getString(R.string.snack_machine_edit_name);
                                            ((HomeListMachineFragment) fragment).setSnackbar(message);
                                        }
                                }
                            });
                            //FIN -> onclick
                            dialogEditName.show();
                        }
                    });

                    optionsBottomSheet.show();
                }
            });
        }
    }
    //metodo para rellenar la lista de playlist add list
    /*
    private void getPlaylistUser(){
        db2.collection("Playlist machine")
                .whereEqualTo("creatorID", idUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemPlaylist itemPlaylist=document.toObject(ItemPlaylist.class);
                                itemPlaylistsAddList.add(itemPlaylist);
                                playlistAddListString.add(document.getId()); //guardo los ids de los doc en otra lista
                            }
                            //quitamos el shimmer effect
                            adapterItemPlaylistAddList.showShimmer=false;
                            adapterItemPlaylistAddList.notifyDataSetChanged();
                        } else {

                        }
                    }
                });
    }     */
    @Override
    public int getItemCount() {
        int itemLoading=6; //numero de items animacion de cargar
        if(showShimmer){
            return itemLoading;
        }else{
            return itemMachines.size();
        }
    }

    public static class ItemMachineHolder extends RecyclerView.ViewHolder{

        protected ImageView enter_machine;
        protected TextView name_machine;
        protected TextView model_machine;
        protected FloatingActionButton options_machine;
        protected ShimmerFrameLayout shimmerFrameLayout;
        protected MaterialCardView item_machine;
        public ItemMachineHolder(@NonNull View itemView) {
            super(itemView);
            //defino aca los findById
            name_machine=itemView.findViewById(R.id.name_machineItem);
            model_machine=itemView.findViewById(R.id.model_machineItem);
            shimmerFrameLayout=itemView.findViewById(R.id.shimmer_machine_item);
            item_machine=itemView.findViewById(R.id.machine_item);
            enter_machine=itemView.findViewById(R.id.enter_machineItem);
            options_machine=itemView.findViewById(R.id.options_machineItem);
        }
    }

}
