package com.example.infinitycropapp.ui.main.log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class EmailActivityL extends AppCompatActivity {
    //objetos visibles
    private TextInputEditText Textmail, Textpassword ;
    private TextInputLayout layEmail, layPassword;


    private TextView BtnCambioPass;
    private Button Btnlogin;
    private TextView BtnRegistro;
    private ProgressDialog progressDialog;
    //objeto firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_l);
        TextView btnlogin = findViewById(R.id.gotoRegister);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(EmailActivityL.this, RegisterActivity.class);
                startActivity(register);
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        //referenciamos los views
        Textmail = (TextInputEditText) findViewById(R.id.editText_loguser);
        Textpassword = (TextInputEditText) findViewById(R.id.editText_logpassword);
        // los layouts de los text
        layEmail = (TextInputLayout) findViewById(R.id.loguser);
        layPassword = (TextInputLayout) findViewById(R.id.inputPassword);

        //botones
        BtnCambioPass= findViewById(R.id.forgotPassword);

        Btnlogin = (Button) findViewById(R.id.btnLogin);
        progressDialog =new ProgressDialog(this);

        BtnCambioPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarContrasenya();
            }
        });

        Btnlogin.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(EmailActivityL.this, getText(R.string.login_iniciando_sesion), Toast.LENGTH_LONG).show();

                        Intent intencion = new Intent(getApplication(), MainListActivity.class);
                        intencion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intencion);
                    }
                }

            }
        };
    }
    private void loginUsuario() {
        //obtendremos el email y la contraseña desde la caja de texto
        String email = Textmail.getText().toString().trim();
        String password = Textpassword.getText().toString().trim();

        //verificamos si las cajas estan vacias o no
        if (TextUtils.isEmpty(email)) {
            layEmail.setError(getText(R.string.login_introduce_correo_ok));
            return;
        }else{
            layEmail.setError(null);
        }
        if (TextUtils.isEmpty(password)) {
            layPassword.setError( getText(R.string.login_introduce_contra));
            return;
        }else{
            layPassword.setError(null);
        }

        progressDialog.setMessage("Obteniendo contenido en línea...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //comprovar si el resultado ha sido resuelto correctamente
                if (task.isSuccessful()) {
                    user = firebaseAuth.getCurrentUser();
                    if (!user.isEmailVerified()) {
                        Toast.makeText(EmailActivityL.this, getText(R.string.login_verifica_correo), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EmailActivityL.this, getText(R.string.login_iniciando_sesion), Toast.LENGTH_LONG).show();
                        Intent intencion = new Intent(getApplication(), MainListActivity.class);
                        intencion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intencion);
                    }
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                        Toast.makeText(EmailActivityL.this, getText(R.string.login_usuario_ya_existente), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EmailActivityL.this, getText(R.string.login_no_se_puede_registrar), Toast.LENGTH_SHORT).show();
                        layEmail.setError(getText(R.string.login_introduce_correo_ok));
                        layPassword.setError( getText(R.string.login_introduce_contra));
                    }

                }
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), getText(R.string.login_datos_incorrectos),
                            Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();

            }
        });
    }

    private  void recuperarContrasenya(){
        String email = Textmail.getText().toString().trim();

        //verificamos si las cajas estan vacias o no
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, getText(R.string.login_introduce_correo_pass), Toast.LENGTH_LONG).show();

        }else{
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(EmailActivityL.this, getText(R.string.login_revisa_correo), Toast.LENGTH_LONG).show();
                        }
                    });
        }
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
}