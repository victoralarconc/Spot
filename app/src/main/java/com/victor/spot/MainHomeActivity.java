package com.victor.spot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);


        CardView btn2 = (CardView) findViewById(R.id.cardMapa);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (v.getContext(), MapsActivity.class);
                startActivityForResult(intent2, 0);
            }
        });


        CardView btn3 = (CardView) findViewById(R.id.cardCor);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (v.getContext(), MainIngresarGeoActivity.class);
                startActivityForResult(intent2, 0);
            }
        });

        CardView btn4 = (CardView) findViewById(R.id.cardLogin);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (v.getContext(), MainLoginActivity.class);
                startActivityForResult(intent2, 0);
            }
        });

        CardView btn5 = (CardView) findViewById(R.id.cardAcercaDe);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (v.getContext(), MainAcerca.class);
                startActivityForResult(intent2, 0);
            }
        });

    }

}