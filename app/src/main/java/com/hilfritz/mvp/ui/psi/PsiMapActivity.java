package com.hilfritz.mvp.ui.psi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hilfritz.mvp.R;

public class PsiMapActivity extends AppCompatActivity {

    //SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psi_map);

        //Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.map);
        //mapFragment = (SupportMapFragment) fragmentById;

    }

    public SupportMapFragment getMapFragment() {
        Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentById;
        return mapFragment;
    }
}
