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
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.victor.spot.Modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                for (DataSnapshot objSnaptshop : dataSnapshot.getChildren()) {
                    Usuario u = objSnaptshop.getValue(Usuario.class);
                    listaUsuario.add(u);
                    arrayAdapterUsuarios = new ArrayAdapter<Usuario>(MainUsuariosActivity.this, android.R.layout.simple_list_item_1, listaUsuario);

                    listaView_usuarios.setAdapter(arrayAdapterUsuarios);
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
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void limpiar() {

        nombre.setText("");
        apellido.setText("");
        correo.setText("");
        password.setText("");

    }

    private void validacion(){
        String nombreP = nombre.getText().toString();
        String apellidoP = apellido.getText().toString();
        String correoP = correo.getText().toString();
        String passwordP = password.getText().toString();

        if (nombreP.equals("")){
            nombre.setError("Required");
        }
        else if(apellidoP.equals("")){
            apellido.setError("Required");
        }
        else if(correoP.equals("")){
            correo.setError("Required");
        }
        else if(passwordP.equals("")){
            password.setError("Required");
        }

    }
    @Override
    public void onClick(View v) {
        String nombreP= nombre.getText().toString();
        String apellidoP=apellido.getText().toString();
        String correoP=correo.getText().toString();
        String passwordP=password.getText().toString();

        switch (v.getId()){
            case R.id.btnGuardar:{
                if(nombreP.equals("") || apellidoP.equals("") || correoP.equals("") || passwordP.equals("")) {
                    validacion();
                } else {
                    Usuario u = new Usuario();
                    u.setId(UUID.randomUUID().toString());
                    u.setNombre(nombreP);
                    u.setApellido(apellidoP);
                    u.setCorreo(correoP);
                    u.setPassword(passwordP);
                    databaseReference.child("Usuario").child(u.getId()).setValue(u);
                    Toast.makeText(this, "Usuario Almacenado", Toast.LENGTH_SHORT).show();
                    limpiar();
                }
                break;
            }
            case R.id.btnEditar: {
                Usuario u =new Usuario();
                u.setId(usuariosSelected.getId());
                u.setNombre(nombre.getText().toString().trim());
                u.setApellido(apellido.getText().toString().trim());
                u.setCorreo(correo.getText().toString().trim());
                u.setPassword(password.getText().toString().trim());
                databaseReference.child("Usuario").child(u.getId()).setValue(u);
                Toast.makeText(this, "Actualizado",Toast.LENGTH_LONG).show();
                limpiar();
                break;
            }
                //jelouuuuuuuuuu
            case R.id.btnEliminar: {

                Usuario u = new Usuario();
                u.setId(usuariosSelected.getId());
                databaseReference.child("Usuario").child(u.getId()).removeValue();
                Toast.makeText(this, "Usuario Eliminado", Toast.LENGTH_LONG).show();
                limpiar();
                break;

            }

        }
    }
}