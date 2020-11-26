package com.victor.spot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainLoginActivity extends AppCompatActivity {
    private EditText email, password;
    private CardView login;
    private CardView register;

FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        firebaseAuth= FirebaseAuth.getInstance();

        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        login= (CardView) findViewById(R.id.login);
        register=(CardView) findViewById(R.id.register);

        //Acciones de botones
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userE= email.getText().toString();
                String passE= password.getText().toString();
                //validar si algun campo esta vacio
                if(TextUtils.isEmpty(userE)){
                    Toast.makeText(MainLoginActivity.this, "Inserte un correo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passE)){
                    Toast.makeText(MainLoginActivity.this, "Inserte una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }
                //usamos el firebaseAutch para loguearse
                firebaseAuth.signInWithEmailAndPassword(userE, passE)
                        .addOnCompleteListener(MainLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(MainLoginActivity.this, "Usuario o contraseña no son validas", Toast.LENGTH_SHORT).show();
                                }else {
                                    Intent intent = new Intent (MainLoginActivity.this, MainUsuariosActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                Intent intent = new Intent(MainLoginActivity.this,MainRegistroActivity.class);
                startActivity(intent);
            }
        });







    }

}