package com.hilfritz.mvp.ui.psi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.SupportMapFragment;
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
