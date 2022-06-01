package com.eliteinfotech.vewash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    Bundle bb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);
        bb = getIntent().getExtras();
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Map");
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager( )
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng schoolocation = new LatLng(Double.parseDouble(bb.getString("lat")), Double.parseDouble(bb.getString("long")));
        mMap.addMarker(new MarkerOptions( ).position(schoolocation).title(bb.getString("address")));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(schoolocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}