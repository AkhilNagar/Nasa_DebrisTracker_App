package com.example.poseidon;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DebrisMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debris_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        this.mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {


            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
                        (FrameLayout) findViewById(R.id.map), false);

                TextView title = infoWindow.findViewById(R.id.title);
                title.setText(marker.getTitle());

                TextView snippet = infoWindow.findViewById(R.id.snippet);
                snippet.setText(marker.getSnippet());

                return infoWindow;

            }
        });
            double lat = getIntent().getDoubleExtra("lat", 18.92);
        double lon = getIntent().getDoubleExtra("lon", 72.80);
        double lat0 = getIntent().getDoubleExtra("lat0", 18.92);
        double lon0 = getIntent().getDoubleExtra("lon0", 72.80);
        double mssf = getIntent().getDoubleExtra("mssf", 1);
        double msssf = getIntent().getDoubleExtra("msssf", 1);
        String message="";
        if(msssf<0.25){
            message="Extremely Polluted";
        }
        else if(msssf<0.50){
            message="Polluted";
        }
        else if(msssf<0.25){
            message="Less Polluted";
        }
        else{
            message="Least Polluted";
        }
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lon);

        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Lat"+lat+"Lon"+lon)
                .snippet(message+" Scaled Anomaly Value" + msssf)
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_garbage_round))
        );

        LatLng userloc = new LatLng(lat0, lon0);
        mMap.addMarker(new MarkerOptions()
                .position(userloc)
                .title("Lat"+lat0+"Lon"+lon0)
                .snippet("Location chosen by user")
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_garbage_round))
        );


        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}