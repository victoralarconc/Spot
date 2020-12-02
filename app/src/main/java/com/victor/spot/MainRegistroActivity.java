package com.victor.spot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.os.BuildCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainRegistroActivity extends AppCompatActivity {

    private EditText email, password;
    private Button register;
    private ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro);
        progressDialog= new ProgressDialog(this );

        firebaseAuth = FirebaseAuth.getInstance();

        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        register=(Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userE= email.getText().toString();
                String passE= password.getText().toString();
                //validar si algun campo esta vacio
                if(TextUtils.isEmpty(userE)){
                    Toast.makeText(MainRegistroActivity.this, "Inserte un correo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passE)){
                    Toast.makeText(MainRegistroActivity.this, "Inserte una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("En Proceso de registro");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(userE,passE)
                        .addOnCompleteListener(MainRegistroActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainRegistroActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();

                                if(!task.isSuccessful()){
                                    Toast.makeText(MainRegistroActivity.this, "No se registro", Toast.LENGTH_SHORT).show();
                        }
                                Intent intent = new Intent (MainRegistroActivity.this, MainLoginActivity.class);
                                startActivity(intent);
            }
                        });
            }
        });
    }
}