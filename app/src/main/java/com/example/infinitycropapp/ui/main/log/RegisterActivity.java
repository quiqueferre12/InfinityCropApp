package com.example.infinitycropapp.ui.main.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinitycropapp.Firebase.Firestore.Firestore;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;
import com.example.infinitycropapp.ui.main.tutorial.TutorialActivity;
import com.example.infinitycropapp.ui.pojos.ItemUser;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    //palette
    private CircleImageView profileImageView;
    private Button btnRegistrar;
    private TextView banner;
    private TextInputEditText mailEditText, nameEditText, passwordEditText, usernameEditText;
    private TextInputLayout layEmail, layName, layPassword, layUsername;
    private ConstraintLayout btnBack;
    private MaterialButton btn_google;
    //pojo firebase
    private Firestore db;
    //firebase
    private FirebaseAuth mAuth;
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;
    //google
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient googleApiClient;
    //atributos
    private Uri imageUri;
    private String myUri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //findById
        //edit texts
        nameEditText = (TextInputEditText) findViewById(R.id.txtName);
        mailEditText = (TextInputEditText) findViewById(R.id.mail);
        passwordEditText = (TextInputEditText) findViewById(R.id.password);
        usernameEditText =(TextInputEditText) findViewById(R.id.username);
        //inputslayouts
        layName = (TextInputLayout) findViewById(R.id.reginame);
        layUsername = (TextInputLayout) findViewById(R.id.regiusername);
        layEmail = (TextInputLayout) findViewById(R.id.regimail);
        layPassword = (TextInputLayout) findViewById(R.id.registPassword);
        //boton atras
        btnBack =findViewById(R.id.back_log);
        banner = findViewById(R.id.banner);
        btnRegistrar = findViewById(R.id.btnregist);
        btn_google= findViewById(R.id.sign_in_button_register);
        //otros
        profileImageView = findViewById(R.id.imgPerfil);
        //pojo firebase
        db=new Firestore();
        //firebase
        mAuth = FirebaseAuth.getInstance();
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("User");
        //onlicks
        //btn google
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //btn google
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        //btn iniciar sesion
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, EmailActivityL.class));
            }
        });
        //btn back
        btnBack.setOnClickListener(new View.OnClickListener() { //btn volver atras
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //boton de registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        //image onclick
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abrimos el plugin para set una foto
                CropImage.activity().setAspectRatio(1,1).start(RegisterActivity.this);
            }
        });
    }

    private void registerUser() {
        String email = mailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        //comprobaciones
        //name
        if(name.isEmpty()){
            layName.setErrorEnabled(true);
            layName.setError(getText(R.string.login_introduce_nombre));
        }else{
            layName.setErrorEnabled(false);
        }
        //username
        if(username.isEmpty()){
            layUsername.setErrorEnabled(true);
            layUsername.setError(getText(R.string.login_introduce_username));
        }else{
            layUsername.setErrorEnabled(false);
        }
        //mail
        if(email.isEmpty()){
            layEmail.setErrorEnabled(true);
            layEmail.setError(getText(R.string.login_introduce_correo_ok));
        }else{
            layEmail.setErrorEnabled(false);
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            layEmail.setErrorEnabled(true);
            layEmail.setError(getText(R.string.login_introduce_correo_ok));
        }else{
            layEmail.setErrorEnabled(false);
        }
        //password
        if(password.isEmpty()){
            layPassword.setErrorEnabled(true);
            layPassword.setError(getText(R.string.login_introduce_contra));
        }else{
            layPassword.setErrorEnabled(false);
        }
        if(password.length() < 6){
            layPassword.setErrorEnabled(true);
            layPassword.setError(getText(R.string.login_introduce_contra_ok));
        }else{
            layPassword.setErrorEnabled(false);
        }

        if(!name.isEmpty() && !username.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if(imageUri != null){
                //exist or not var
                FirebaseFirestore db2= FirebaseFirestore.getInstance();
                db2.collection("User") //busco en la collection
                        .get()
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){ //si all gucci
                                QuerySnapshot document = task.getResult(); //get result
                                int cont=0;
                                for (QueryDocumentSnapshot doc : document) {
                                    if(doc.getString("lastname").equals(username)){ //si ya existe esa username
                                        cont++; // ++
                                    }
                                }

                                if (cont >= 1) { //ese campo ya existe
                                    //error ya existe
                                    layUsername.setErrorEnabled(true);
                                    layUsername.setError(getText(R.string.login_introduce_username_exists));
                                } else { //no existe ese username
                                    //registrar usuario
                                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {

                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            //comprovar si el resultado ha sido resuelto correctamente
                                            if (task.isSuccessful()) {

                                                //enviar coorreo de confirmación
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                if(user != null){
                                                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            //set pojo
                                                            ItemUser user= new ItemUser(
                                                                    name,
                                                                    username,
                                                                    email,
                                                                    "",
                                                                    0,
                                                                    0,
                                                                    0,
                                                                    0,
                                                                    0,
                                                                    0,
                                                                    true);
                                                            setSnackbar((String) getText(R.string.login_verifica_correo));
                                                            uploadProfileImage(user , mAuth.getUid());

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            setSnackbar((String) getText(R.string.login_error_envio_correo));
                                                        }
                                                    });
                                                }
                                            } else {
                                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                                    setSnackbar((String) getText(R.string.login_usuario_ya_existente));
                                                } else {
                                                    setSnackbar((String) getText(R.string.login_no_se_puede_registrar));
                                                }

                                            }
                                        }

                                    });


                                }
                            }
                        });
            }else{
                setSnackbar((String) getText(R.string.login_error_imagen));
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            //recogemos el resultado
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            //guardamos el link en una variable
            imageUri=result.getUri();
            //set in the Circular ImageView
            profileImageView.setImageURI(imageUri);
        }
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private void uploadProfileImage(ItemUser user,String idUser) {
        if(imageUri!=null){ //si el user ha seleccionado una foto propia
            //storage User -> Profile photo -> id.jpg (para que se sustituya)
            final StorageReference fileRef = storageProfilePicsRef
                    .child(mAuth.getCurrentUser().getUid())
                    .child("Profile photo")
                    .child(mAuth.getCurrentUser().getUid()+ ".jpg");
            //set image in storage
            uploadTask = fileRef.putFile(imageUri);
            //sacar el link de la imagen subida a firebase
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw  task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() { //cuando saca el link
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){ //si all gucci
                        Uri downloadUrl = task.getResult(); //el link es un Uri
                        myUri = downloadUrl.toString(); //Uri en string
                        //back end method
                        //set pojo user image
                        user.setPhoto(myUri);
                        //call back end method
                        db.AddNewUser( user, idUser);

                        //change activity
                        startActivity(new Intent(RegisterActivity.this, EmailActivityL.class));
                    }
                }
            });
        }
    }

    //set snackbar method
    public void setSnackbar(String snackBarText){
        Snackbar snackBar = Snackbar.make( findViewById(R.id.layout_register) , snackBarText,Snackbar.LENGTH_LONG);
        snackBar.setActionTextColor(Color.CYAN);
        snackBar.setAction(getText(R.string.snack_close), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            //saber si el user es nuevo o no
                            boolean newUser= task.getResult().getAdditionalUserInfo().isNewUser();
                            Firestore firestore = new Firestore();
                            if(newUser){ //si el usario es nuevo
                                //add new user
                                firestore.AddNewGoogleUser();
                                Intent intent = new Intent(RegisterActivity.this, TutorialActivity.class);
                                startActivity(intent);
                            }else{ //si ya esta registrado en nuestra base de datos
                                Intent intent = new Intent(RegisterActivity.this, MainListActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }
}