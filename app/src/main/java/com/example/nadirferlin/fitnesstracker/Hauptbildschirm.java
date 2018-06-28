package com.example.nadirferlin.fitnesstracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

/*
Siehe auch: https://stackoverflow.com/questions/27609442/how-to-get-the-sha-1-fingerprint-certificate-in-android-studio-for-debug-mode
 */

public class Hauptbildschirm extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = Hauptbildschirm.class.getSimpleName();
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private String provider;
    private LocationManager locationManager;
    private double lat1;
    private double lon1;
    private boolean isRunning;
    private String point;
    private List<LatLng> polygon;
    private Marker mZH;
    private double steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptbildschirm);
        addListenerToButtons();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        polygon = new ArrayList<>();
    }

    public void startRoute(View view) {

        if (isRunning) {
            Button btnGo = (Button) findViewById(R.id.btnGo);
            btnGo.setText("LOSLEGEN!");
            Toast.makeText(Hauptbildschirm.this, "Calculating...", Toast.LENGTH_SHORT).show();
            point = "Ende";

            steps = SphericalUtil.computeLength(polygon);

            calculateCalories(steps);

            isRunning = false;
        } else {
            polygon.clear();
            mMap.clear();
            point = "Start";
            Button btnGo = (Button) findViewById(R.id.btnGo);
            Toast.makeText(Hauptbildschirm.this, "Tracking...", Toast.LENGTH_SHORT).show();
            btnGo.setText("STOP!");
            isRunning = true;
        }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(provider);

        if (myLocation == null) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            provider = locationManager.getBestProvider(criteria, false);
            myLocation = locationManager.getLastKnownLocation(provider);
        }

        if (myLocation != null) {
            LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

            lat1 = myLocation.getLatitude();
            lon1 = myLocation.getLongitude();

            mMap.addMarker(new MarkerOptions()
                    .position(userLocation)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .title(point)
                    .snippet("Latitude:" + lat1 + ", Longtitude:" + lon1)
            );

            Log.v(TAG, "Lat1=" + lat1);
            Log.v(TAG, "Long1=" + lon1);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location myLocation) {

                    if (!isRunning) {

                        locationManager.removeUpdates(this);
                        locationManager = null;

                    }

                    // Getting latitude of the current location
                    double latitude = myLocation.getLatitude();

                    // Getting longitude of the current location
                    double longitude = myLocation.getLongitude();

                    // Creating a LatLng object for the current location
                    LatLng latLng = new LatLng(latitude, longitude);

                    // Showing the current location in Google Map
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    // Zoom in the Google Map
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

                    //Draw polyline
                    drawPolygon(latitude, longitude);

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) { }
                @Override
                public void onProviderEnabled(String s) { }
                @Override
                public void onProviderDisabled(String s) { }

            });

        }

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    private void calculateCalories(Double steps) {
        TextView tvCountSteps = (TextView) findViewById(R.id.tvCountSteps);
        TextView tvCountBurnt = (TextView) findViewById(R.id.tvCountBurnt);

        tvCountSteps.setText(steps.toString());
    }

    public void openActivityProfile(View v) {
        Intent thisIntent = new Intent(this, profilActivity.class);
        startActivity(thisIntent);
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

        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        enableMyLocationIfPermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(10);

        // Add a marker in Zurich and move the camera
        LatLng zh = new LatLng(47.378107, 8.539725);
        mZH = mMap.addMarker(new MarkerOptions().position(zh).title("Zürcher Hauptbahnhof"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(zh));

    }

    private void drawPolygon( double latitude, double longitude) {

        //old lat and long
        polygon.add(new LatLng(lat1, lon1));
        //new lat and long
        polygon.add(new LatLng(latitude,longitude));

        mMap.addPolygon(new PolygonOptions()
                .addAll(polygon)
                .strokeColor(Color.YELLOW)
                .strokeWidth(10)
                .fillColor(Color.YELLOW)
        );

        lat1=latitude;
        lon1=longitude;
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(12);
                    mMap.setMaxZoomPreference(19);
                    return false;
                }
            };

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);

        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    public void addListenerToButtons() {
        final Button btnProfile = (Button) findViewById(R.id.btnProfil);

        btnProfile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    btnProfile.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundShadow));
                    openActivityProfile(btnProfile);
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    btnProfile.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundColor));
                    return true;
                }
                return false;
            }
        });

        final Button btnGo = (Button) findViewById(R.id.btnGo);

        btnGo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    btnGo.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundShadow));
                    startRoute(btnGo);
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    btnGo.setBackgroundColor(getResources().getColor(R.color.sonicBackgroundColor));
                    return true;
                }
                return false;
            }
        });
    }

}
