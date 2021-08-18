package com.example.infinitycropapp.ui.main.log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinitycropapp.Firebase.Firestore.Firestore;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;
import com.example.infinitycropapp.ui.main.tutorial.TutorialActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class EmailActivityL extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    //objetos visibles
    private TextInputEditText mailEditText, passwordEditText;
    private TextInputLayout layEmail, layPassword;
    private ConstraintLayout btn_back;
    private TextView btn_change_password;
    private Button btn_login;
    private ProgressDialog progressDialog;
    private TextView btn_registerActivity;
    //google
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private GoogleApiClient googleApiClient;
    private MaterialButton btn_google;
    //objeto firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_l);
        
        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        //findById
        //botones
        btn_change_password = findViewById(R.id.forgotPassword);
        btn_back = findViewById(R.id.btn_back_login); //btn back
        btn_login = (Button) findViewById(R.id.btnLogin);
        progressDialog =new ProgressDialog(this);
        btn_registerActivity = findViewById(R.id.gotoRegister);
        //edit text de los inputs
        mailEditText = (TextInputEditText) findViewById(R.id.editText_loguser);
        passwordEditText = (TextInputEditText) findViewById(R.id.editText_logpassword);
        // los layouts de los inputs
        layEmail = (TextInputLayout) findViewById(R.id.loguser);
        layPassword = (TextInputLayout) findViewById(R.id.inputPassword);
        //google
        btn_google = findViewById(R.id.sign_in_button_register);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //onclick
        //btn google
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        //btn back activity
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //btn go to register activity
        btn_registerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(EmailActivityL.this, RegisterActivity.class);
                startActivity(register);
            }
        });
        //btn change password
        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarContrasenya();
            }
        });
        //btn acces to main aplication
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsuario();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    if (!user.isEmailVerified()) {
                        Toast.makeText(EmailActivityL.this, getText(R.string.login_verifica_correo), Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(EmailActivityL.this, getText(R.string.login_iniciando_sesion), Toast.LENGTH_LONG).show();

                        Intent intencion = new Intent(getApplication(), MainListActivity.class);
                        intencion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intencion);
                    }
                }

            }
        };
    }
    //logging method
    private void loginUsuario() {
        //obtendremos el email y la contraseña desde los inputs
        String email = mailEditText.getText().toString().trim();
        String password =passwordEditText.getText().toString().trim();

        //verificamos si los inputs estan vacias o no
        if (email.isEmpty()) { //si el input del mail esta vacio
            layEmail.setErrorEnabled(true);
            layEmail.setError(getText(R.string.login_introduce_correo_ok));
            //return;
        }else{
            layEmail.setErrorEnabled(false);
        }
        if (password.isEmpty()) { //si el input del password esta vacio
            layEmail.setErrorEnabled(true);
            layPassword.setError( getText(R.string.login_introduce_contra));
            //return;
        }else{
            layPassword.setErrorEnabled(false);
        }

        //si en los dos inputs hay data
        if(!email.isEmpty() && !password.isEmpty()) {
            //progressDialog.setMessage("Obteniendo contenido en línea...");
            //progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //comprovar si el resultado ha sido resuelto correctamente
                    if (task.isSuccessful()) {
                        user = firebaseAuth.getCurrentUser();
                        if (!user.isEmailVerified()) { //sino esta verificado
                            Toast.makeText(EmailActivityL.this, getText(R.string.login_verifica_correo), Toast.LENGTH_LONG).show();
                            //verificar correo
                            //Dialog verification

                        } else { //si all gucci
                            Intent intencion = new Intent(getApplication(), MainListActivity.class);
                            intencion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intencion);

                        }
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                            setSnackbar((String) getText(R.string.login_usuario_ya_existente));
                        } else {
                            //snackbar error + layout error
                            setSnackbar((String) getText(R.string.login_no_se_puede_registrar));
                        }

                    }
                    if (!task.isSuccessful()) {
                        //snackbar los datos son incorrectos
                        setSnackbar((String) getText(R.string.login_datos_incorrectos));
                    }
                    //progressDialog.dismiss();

                }
            });

        }
    }

    //metodo de recuperacion de password
    private  void recuperarContrasenya(){
        String email = mailEditText.getText().toString().trim();
        //verificamos si los inputs estan vacias o no
        if (email.isEmpty()) {
            //make snackbar
            setSnackbar((String) getText(R.string.login_introduce_correo_pass));
        }else{ //si ha puesto el mail
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                //make snackbar all gucci, revisa correo
                                setSnackbar((String) getText(R.string.login_revisa_correo));
                            }else{
                                //el correo no esta registrado en Infinity Crop
                                setSnackbar((String) getText(R.string.login_mail_not_registered));
                            }
                        }
                    });
        }
    }

    //set snackbar method
    public void setSnackbar(String snackBarText){
        Snackbar snackBar = Snackbar.make( findViewById(R.id.layout_login) , snackBarText,Snackbar.LENGTH_LONG);
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
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);


    }

    @Override
    protected void onStop() {
        super.onStop();

        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //saber si el user es nuevo o no
                            boolean newUser= task.getResult().getAdditionalUserInfo().isNewUser();
                            Firestore firestore = new Firestore();
                            if(newUser){ //si el usario es nuevo
                                //add new user
                                firestore.AddNewGoogleUser();
                                Intent intent = new Intent(getApplicationContext(),TutorialActivity.class);
                                startActivity(intent);
                            }else{ //si ya esta registrado en nuestra base de datos
                                Intent intent = new Intent(getApplicationContext(),MainListActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}