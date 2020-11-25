package com.victor.spot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.victor.spot.Modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MainUsuariosActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Usuario> listaUsuario = new ArrayList<>();
    ArrayAdapter<Usuario> arrayAdapterUsuarios;

    EditText nombre, apellido, correo, password;
    ListView listaView_usuarios;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Usuario usuariosSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_usuarios);

        nombre= findViewById(R.id.txtNombre);
        apellido=findViewById(R.id.txtApellido);
        correo=findViewById(R.id.txtCorreo);
        password=findViewById(R.id.txtPassword);

        listaView_usuarios=findViewById(R.id.lv_datosUsuarios);

        IniciarFirebase();
        ListarDatos();


        //Acciones de los botones.
        Button bEditar= (Button) findViewById(R.id.btnEditar);
        bEditar.setOnClickListener(this);

        Button bEliminar= (Button) findViewById(R.id.btnEliminar);
        bEliminar.setOnClickListener(this);

        Button bGuardar= (Button) findViewById(R.id.btnGuardar);
        bGuardar.setOnClickListener(this);

        listaView_usuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuariosSelected = (Usuario)parent.getItemAtPosition(position);
                nombre.setText(usuariosSelected.getNombre());
                apellido.setText(usuariosSelected.getApellido());
                correo.setText(usuariosSelected.getCorreo());
                password.setText(usuariosSelected.getPassword());
            }
        });
    }
    private void ListarDatos(){

        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaUsuario.clear();
                for (DataSnapshot objSnaptshop: dataSnapshot.getChildren()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //comentario
        //xdxd
    }
    private void IniciarFirebase() {
    }

    @Override
    public void onClick(View view) {

    }
}