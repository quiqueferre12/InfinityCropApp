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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class EmailActivityL extends AppCompatActivity {
    //objetos visibles
    private EditText Textmail;
    private EditText Textpassword;
    private Button Btnlogin;
    private TextView BtnRegistro;
    private ProgressDialog progressDialog;
    //objeto firebase
    private FirebaseAuth firebaseAuth;


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
        Textmail = (EditText) findViewById(R.id.inputEmail);
        Textpassword = (EditText) findViewById(R.id.inputPassword);

        Btnlogin = (Button) findViewById(R.id.btnLogin);
        progressDialog =new ProgressDialog(this);


        Btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsuario();
            }
        });

    }
    private void loginUsuario() {
        //obtendremos el email y la contraseña desde la caja de texto
        String email = Textmail.getText().toString().trim();
        String password = Textpassword.getText().toString().trim();

        //verificamos si las cajas estan vacias o no
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Debes introducir tu correo", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Debes introducir tu contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Obteniendo contenido en línea...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //comprovar si el resultado ha sido resuelto correctamente
                if (task.isSuccessful()) {

                    Toast.makeText(EmailActivityL.this, "Iniciando sesión", Toast.LENGTH_LONG).show();
                    //continuar con el dashboard aqui
                    Intent intencion = new Intent(getApplication(), MainListActivity.class);
                    startActivity(intencion);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                        Toast.makeText(EmailActivityL.this, "Este Usuario ya existe", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EmailActivityL.this, "No se puede registrar este usuario", Toast.LENGTH_SHORT).show();
                    }

                }
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Los datos introducidos no son correctos",
                            Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();

            }
        });
    }
}