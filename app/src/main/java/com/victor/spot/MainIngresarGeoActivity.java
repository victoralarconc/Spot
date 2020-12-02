package com.victor.spot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.victor.spot.Modelo.Coordenada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainIngresarGeoActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Coordenada> listaCoordenadas = new ArrayList<>() ;
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
        bGuardar.setOnClickListener(this);

    }

    private void listarDatos() {

        databaseReference.child("coordenadas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaCoordenadas.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Coordenada u = objSnaptshot.getValue(Coordenada.class);
                    listaCoordenadas.add(u);
                    arrayAdapterCoordenadas = new ArrayAdapter<Coordenada>(MainIngresarGeoActivity.this, android.R.layout.simple_list_item_1, listaCoordenadas);
                    listaView_coordenadas.setAdapter(arrayAdapterCoordenadas);
            }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

private void iniciarFirebase(){
    FirebaseApp.initializeApp(this);
    firebaseDatabase = FirebaseDatabase.getInstance();
    databaseReference = firebaseDatabase.getReference();
}

private void limpiar (){
        latitud.setText("");
        longitud.setText("");
}
private void validacion(){
        Double latitudP = Double.parseDouble(latitud.getText().toString());
        Double longitudP = Double.parseDouble(longitud.getText().toString());


    if (latitudP.equals("")){

        latitud.setError("Required");

    }else if (longitudP.equals("")){
        longitud.setError("Required");

    }
}
@Override
    public void onClick(View v){
        Double latitudP = Double.parseDouble(latitud.getText().toString());
        Double longitudP = Double.parseDouble(longitud.getText().toString());

    switch(v.getId()){
        case R.id.btnGuardar: {
            if (latitudP.equals("") || longitudP.equals("")){
                validacion();
            }else{
                Coordenada u = new Coordenada();
                u.setLatitud(latitudP);
                u.setLongitud(longitudP);
                    Map<String, Object> latlong = new HashMap<>();
                    latlong.put("latitud", u.getLatitud());
                    latlong.put("longitud", u.getLongitud());
                    databaseReference.child("coordenadas").push().setValue(latlong);
                    Toast.makeText(this, "Coordenadas Agregadas", Toast.LENGTH_LONG).show();
                    limpiar();

                }
            break;
        }
    }
    }



























}