package com.example.infinitycropapp.ui.main.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinitycropapp.Firebase.Auth.User;
import com.example.infinitycropapp.R;
import com.example.infinitycropapp.ui.main.MainListActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private CircleImageView profileImageView;
    private Button btnRegistrar;
    private TextView banner;
    private EditText txtEmail, txtname, txtPasword;


    private FirebaseFirestore fStore;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //edit texts
        txtname = (EditText) findViewById(R.id.txtName);
        txtEmail = (EditText) findViewById(R.id.mail);
        txtPasword = (EditText) findViewById(R.id.password);
        banner = findViewById(R.id.banner);
        progressDialog =new ProgressDialog(this);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, EmailActivityL.class));
            }
        });

        //foto
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Profile pic");

        profileImageView = findViewById(R.id.imgPerfil);

        //boton de registrar
        fStore=FirebaseFirestore.getInstance();
        btnRegistrar = findViewById(R.id.btnregist);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               uploadProfileImage();
                registerUser();
            }
        });
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(RegisterActivity.this);

            }
        });
        /*getUserinfo();*/
    }

    private void registerUser() {
        String email = txtEmail.getText().toString().trim();
        String password =txtPasword.getText().toString().trim();
        String username =txtname.getText().toString().trim();
        if(username.isEmpty()){
           txtname.setError("Introduce un nombre de usuario");
           txtname.requestFocus();
           return;
        }
        if(email.isEmpty()){
            txtEmail.setError("Introduce un correo");
            txtEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError("Introduce un correo valido");
            txtEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            txtPasword.setError("Introduce una contraseña");
            txtPasword.requestFocus();
            return;
        }
        if(password.length() < 6){
            txtPasword.setError("La contraseña debe contener al menos 6 carácteres");
            txtPasword.requestFocus();
            return;
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
                            Toast.makeText(RegisterActivity.this,"Verification has been send",Toast.LENGTH_SHORT);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(RegisterActivity.this,"Error en el envío de correo",Toast.LENGTH_SHORT);
                        }
                    });

                    String userID= mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference=fStore.collection("Usuarios").document(userID);
                    Map<String,Object> datauser=new HashMap<>();
                    datauser.put("username",username);
                    datauser.put("mail",email);
                    documentReference.set(datauser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterActivity.this, "Iniciando sesión", Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent (getApplication(), MainListActivity.class);
                            startActivity(intencion);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });



                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                        Toast.makeText(RegisterActivity.this, "Este Usuario ya existe", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "No se puede registrar este usuario", Toast.LENGTH_SHORT).show();
                    }

                }
                progressDialog.dismiss();
            }

        });


    }

    private void getUserinfo() {
         databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                     if(dataSnapshot.hasChild("image")){
                         String image = dataSnapshot.child("image").getValue().toString();
                         Picasso.get().load(image).into(profileImageView);
                     }
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseerror) {

             }
         });
     }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri=result.getUri();

            profileImageView.setImageURI(imageUri);

        }else{
            Toast.makeText(this, "Error, prueba de nuevo", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfileImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Selecciona tu perfil");
        progressDialog.setMessage("Actualizando tu foto de perfil");
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
            Toast.makeText(this, "Imagen no seleccionada", Toast.LENGTH_SHORT).show();
        }
    }
}