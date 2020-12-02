package com.victor.spot;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private DatabaseReference databaseReference;

    private ArrayList<Marker> tmpRealTimeMarkers = new ArrayList<>();

    private ArrayList<Marker> realTimeMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.animateCamera(CameraUpdateFactory.zoomTo( 17.0f));

        databaseReference.child("coordenadas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (Marker marker:realTimeMarkers){
                    marker.remove();
                }

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Marcadores mk = snapshot.getValue(Marcadores.class);

                    Double latitud = mk.getLatitud();
                    Double longitud = mk.getLongitud();

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(latitud,longitud));
                    tmpRealTimeMarkers.add(mMap.addMarker(markerOptions));
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    LatLng coordinate = new LatLng(latitud,longitud);
                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
                    mMap.animateCamera(location);

                }
                realTimeMarkers.clear();
                realTimeMarkers.addAll(tmpRealTimeMarkers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}


