package com.example.infinitycropapp.ui.main.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinitycropapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    //palette
    private CircleImageView profileImageView;
    private Button btnRegistrar;
    private TextView banner;
    private TextInputEditText mailEditText, nameEditText, passwordEditText, usernameEditText;
    private TextInputLayout layEmail, layName, layPassword, layUsername;
    private ConstraintLayout btnBack;
    //firebase
    private FirebaseFirestore fStore;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;

    //atributos
    private Uri imageUri;
    private String myUri = "";
    private ProgressDialog progressDialog;

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
        //otros
        profileImageView = findViewById(R.id.imgPerfil);
        //progress
        progressDialog =new ProgressDialog(this);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Profile pic");
        fStore=FirebaseFirestore.getInstance();
        //onlicks
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
        String username = nameEditText.getText().toString().trim();
        if(username.isEmpty()){
            layName.setError("You need to enter a name and surname");
           return;
        }else{
            layName.setError(null);
        }
        if(email.isEmpty()){
            layUsername.setError("You need to enter a username");
            return;
        }else{
        layName.setError(null);
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            layEmail.setError(getText(R.string.login_introduce_correo_ok));
            return;
        }else{
            layName.setError(null);
        }
        if(password.isEmpty()){
            layPassword.setError(getText(R.string.login_introduce_contra));
            passwordEditText.requestFocus();
            return;
        }else{
            layName.setError(null);
        }
        if(password.length() < 6){
            layPassword.setError(getText(R.string.login_introduce_contra_ok));
            return;
        }else{
            layName.setError(null);
        }


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //comprovar si el resultado ha sido resuelto correctamente
                if (task.isSuccessful()) {

                    //enviar coorreo de confirmación
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterActivity.this,getText(R.string.login_verifica_correo),Toast.LENGTH_SHORT);
                            uploadProfileImage();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(RegisterActivity.this,getText(R.string.login_error_envio_correo),Toast.LENGTH_SHORT);
                        }
                    });
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                        Toast.makeText(RegisterActivity.this, getText(R.string.login_usuario_ya_existente), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, getText(R.string.login_no_se_puede_registrar), Toast.LENGTH_SHORT).show();
                    }

                }
                progressDialog.dismiss();
            }

        });


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
        }else{
            Toast.makeText(this, getText(R.string.login_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfileImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getText(R.string.login_error_select_profile));
        progressDialog.setMessage(getText(R.string.login_actualizando_profile));
        if(imageUri!=null){
            final StorageReference fileRef = storageProfilePicsRef
                    .child(mAuth.getCurrentUser().getUid()+ ".jpg");
            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw  task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUrl = task.getResult();
                        myUri = downloadUrl.toString();
                        HashMap<String,Object> userMap = new HashMap<>();
                        userMap.put("image",myUri);
                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
                        progressDialog.dismiss();
                    }
                }
            });
        }else{
            progressDialog.dismiss();
            Toast.makeText(this, getText(R.string.login_error_imagen), Toast.LENGTH_SHORT).show();
        }
    }
}