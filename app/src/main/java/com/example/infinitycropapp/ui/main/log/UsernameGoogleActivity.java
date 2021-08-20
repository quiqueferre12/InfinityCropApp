package com.example.infinitycropapp.ui.main.log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinitycropapp.Firebase.Firestore.Firestore;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;
import com.example.infinitycropapp.ui.main.tutorial.TutorialActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsernameGoogleActivity extends AppCompatActivity {

    //palette
    private CircleImageView img_google;
    private TextInputLayout layout_username;
    private TextInputEditText editext_username;
    private TextView name_google;
    private ConstraintLayout btn_back;
    private TextView btn_register;
    private TextView mail_google;
    private Button btn_enter;
    //account from intent
    GoogleSignInAccount account;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    //atributes
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username_google);

        //get google user account from extra intent
        account = (GoogleSignInAccount) getIntent().getExtras().get("googleUser");

        //findById
        img_google = findViewById(R.id.image_access_google);
        layout_username= findViewById(R.id.layout_username_access_google);
        editext_username= findViewById(R.id.editext_username_access_google);
        name_google= findViewById(R.id.name_access_google);
        mail_google= findViewById(R.id.mail_access_google);
        btn_back= findViewById(R.id.btn_back__access_google);
        btn_register= findViewById(R.id.txt_register_access_google);
        btn_enter= findViewById(R.id.btn_enter_access_google);
        //firebase
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        //onclicks
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change activity
                startActivity(new Intent(UsernameGoogleActivity.this, RegisterActivity.class));
            }
        });
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegistrarseConGoogle();
            }
        });

        //methods
        setGoogleUserData();

    }

    private void btnRegistrarseConGoogle(){
        username= editext_username.getText().toString().trim(); //trim porque no tiene que haber espacions despues

        if(username.isEmpty()){
            layout_username.setErrorEnabled(true);
            layout_username.setError(getText(R.string.login_introduce_username));
        }else{
            layout_username.setErrorEnabled(false);

            //comprobar si existe ese username
            db.collection("User")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                int cont =0;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(document.getString("username") != null){
                                        if(document.getString("username").equals(username)){
                                            cont++;
                                        }
                                    }
                                }
                                //comrpobar el contador
                                if(cont == 0){ //sino existe
                                    //registrar el user en firebase auth
                                    firebaseAuthWithGoogle(account);
                                }else{// si existe
                                    if(cont >= 1){ //activar error
                                        layout_username.setErrorEnabled(true);
                                        layout_username.setError(getText(R.string.login_introduce_username_exists));
                                    }else{ //desactivar error
                                        layout_username.setErrorEnabled(false);
                                    }
                                }
                            }
                        }
                    });
        }
    }

    private void setGoogleUserData(){
        try{
            Picasso.get().load(account.getPhotoUrl()).into(img_google);
        }catch(Exception e){}
        try{
            name_google.setText(account.getDisplayName());
        }catch(Exception e){}
        try{
            mail_google.setText(account.getEmail());
        }catch(Exception e){}
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            //saber si el user es nuevo o no
                            boolean newUser= task.getResult().getAdditionalUserInfo().isNewUser();
                            String userUid = task.getResult().getUser().getUid();
                            Firestore firestore = new Firestore();
                            if(newUser){ //si el usario es nuevo
                                //add new user
                                firestore.AddNewGoogleUser();
                                Intent intent = new Intent(UsernameGoogleActivity.this, TutorialActivity.class);
                                startActivity(intent);
                                finish();
                            }else{ //si ya esta registrado en nuestra base de datos
                                db.collection("User").document(userUid).update("username", username)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(UsernameGoogleActivity.this, MainListActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

}