package com.example.pakoandrade.alertamapa;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String lat = "";
    String lon = "";


    int latI;
    int lonI;
    Double latD,lonD,latD2,lonD2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent i = getIntent();
        Bundle extra = i.getExtras();

        if(extra != null) {
            lat = (String) extra.get("latitud");
            lon = (String) extra.get("longitud");
            //Toast.makeText(this, "Se recibieron los valores", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, lat + lon, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No se reciben valores", Toast.LENGTH_LONG).show();
        }

/*
        latI = Integer.parseInt(lat);
        lonI = Integer.parseInt(lon);
        latD = (Double) Double.parseDouble(String.valueOf(latI));
        lonD = (Double) Double.parseDouble(String.valueOf(latI));*/

        latD2 = Double.parseDouble(lat);
        lonD2 = Double.parseDouble(lon);
        //Toast.makeText(this, latD2.toString(), Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, lat, Toast.LENGTH_SHORT).show();
        Log.d("Latitud", lat);
        Log.d("longitud", lon);
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

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
        LatLng sydney = new LatLng(latD2, lonD2);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }
}
