package com.victor.spot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.victor.spot.Modelo.Coordenada;

import java.util.ArrayList;
import java.util.List;

public class MainIngresarGeoActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Coordenada> listaCoordenadas = new ArrayList<>();
    ArrayAdapter<Coordenada> arrayAdapterCoordenadas;

    EditText latitud, longitud;
    ListView listaView_coordenadas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ingresar_geo);

        latitud = findViewById(R.id.txtLatitud);
        longitud = findViewById(R.id.txtLongitud);

        listaView_coordenadas = findViewById(R.id.lv_datosCoordenadas);
        iniciarFirebase();
        listarDatos();

        Button bGuardar = (Button) findViewById(R.id.btnGuardar);
        bGuardar.setOnClickListener();

    }

    private void listarDatos() {

        databaseReference.child("coordenadas").addValueEventListener() {

            public void onDataChange (DataSnapshot dataSnapshot) {

                listaCoordenadas.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {

                    Coordenada u = objSnaptshot.getValue(Coordenada.class);
                    listaCoordenadas.add(u);
                    arrayAdapterCoordenadas = new ArrayAdapter<Coordenada>(MainIngresarGeoActivity.this, android.R.layout.simple_list_item_1, listaCoordenadas);

                    listaView_coordenadas.setAdapter(arrayAdapterCoordenadas);
                }

            }

            public void onCancelled(DatabaseError dabaseError) {


            }

        }

    }

}